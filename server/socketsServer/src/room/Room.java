package room;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;

import api.ColorsAPI;
import api.ListAPI;
import api.SocketAPI;
import board.Board;
import pieces.Piece;
import room.features.Chat;
import room.features.History;
import server.Server;
import users.UserThread;

public class Room extends Window implements Runnable{
	private LinkedList<UserThread> players;
	private LinkedList<UserThread> spectators;
	private LinkedList<UserThread> guests;
	private LinkedList<UserThread> viewers;
	private boolean gameRunning,gameFinished;
	private String roomName;
	private String joinStatus;
	private String turnStatus;
	private boolean check;
	private boolean checkmate;
	private boolean roomEmpty;
	private Board board;
	private Chat chat;
	private History history;
	private Thread t;
	private Piece.color turn;
	private Server server;
	
	/**
	 * Start new room
	 * @param name			room name
	 * @param player1		player that created the room, i.e first player to join
	 * @param server		server hosting the room
	 */
	public Room(String name,UserThread player1,Server server) {
		super();	
		setServer(server);
		setT(new Thread(this));
		setPlayers(new LinkedList<UserThread>());
		setSpectators(new LinkedList<UserThread>());
		setGuests(new LinkedList<UserThread>());
		setViewers(new LinkedList<UserThread>());
		setBoard(new Board());
		setRoomName(name);
		history = new History(this);
		chat = new Chat(this);
		setTurn(Piece.color.white);
		setTurnStatus(getTurn()+" turn");
		UsersHandler.addUser(this,player1);
		setRoomEmpty(false);
		setGameRunning(false);
		setGameFinished(false);
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override 
 	public void run(){
		while((players.size() < 2))
			Thread.yield();
		setGameRunning(true);
		System.out.println("Room going to sleep");
		while(((players.size() + viewers.size()) > 0) || !isGameFinished())
			Thread.yield();
		System.out.println(String.format("Closing room, players %d; spec: %d",players.size(),viewers.size()));
		closeRoom();	
	}
	/**
	 * Terminate room
	 * Delete it from lobby list room
	 * Warn remaining users room will be closed
	 */
	public void closeRoom() {
		server.getLob().getRooms().remove(this);
	}
	/**
	 * Command list:
	 * c [text] 			- new chat entry
	 * m [chess standard] 	- play
	 * x 					- user left room, user loses game. Game continues
	 * d 					- user attempting to draw the game, prompt opponent
	 * a 					- user accepted to draw match. Game end
	 * f 					- user forfeits current game. Game ends
	 * default 				- unknown command. Re-inform players about current turn
	 * @see room.Window#processCommands(java.lang.String, users.UserThread)
	 */
	@Override
	public void processCommands(String input,UserThread user) {
		Piece.color color = user.getUser().getTurn();
		char com = input.charAt(0);
		lock();
		switch(com) {
		case 'c':
			String name = user.getUser().getName();
			String out = name+": "+input.substring(2,input.length());
			chat.broadcast(out);
			break;
		case 'm':
			playC(color,input.substring(2,input.length()));
			break;
		case 'x':
			UsersHandler.remUser(this,user);
			UsersHandler.broadcastState(this,players,viewers);
			break;
		case 'd':
			UserThread op = getOpponent(user);
			GameResultHandler.promptDraw(op);
			break;
		case 'a':
			GameResultHandler.drawGame(this);
			UsersHandler.broadcastState(this,players,viewers);
			break;
		case 'f':
			if(isGameRunning()) {
				GameResultHandler.playerDefeated(this,user);
				GameResultHandler.playerWin(this,getOpponent(user));
				UsersHandler.broadcastState(this,players,viewers);
				gameRunning = false;
				gameFinished = true;
			}
			break;
		default: informTurn();
		}
		unlock();
	}
	/**
	 * Get user'opponent
	 * @param user
	 * @return			<code>UserThread</code> representing opponent
	 * 					<code>null</code> if room has only 1 player
	 */
	public UserThread getOpponent(UserThread user) {
		int index = players.indexOf(user);
		return (players.size() < 2)?null:players.get(index^1);
	}
	/**
	 * Play
	 * @param color		color representing who is trying the move
	 * @param move		move to make
	 */
	public void playC(Piece.color color,String move) {
		if(!isGameRunning()) return;
		if(color != turn) return;
		if(turn == Piece.color.white) move = reverseInput(move);
		int[][] movement = inputInt(move);
		sendBoard();
		board.printBoard(getTurn());
		String notation = board.moveToNotation(move);
		if(board.move(movement[0],movement[1],color)) {
			setCheck(board.checkCheck(getTurn()));
			setCheckmate(board.checkCheckMate(getTurn()));
			if(isCheck())
				System.out.println(ColorsAPI.colorToString(ColorsAPI.getOp(color))+" king is in check");
			if(isCheckmate()) {
				System.out.println(ColorsAPI.getOp(color)+" king is in check mate");
				turnStatus = ColorsAPI.colorToString(ColorsAPI.getOp(color)) + " king is in check mate";
				gameRunning = false;
				gameFinished = true;
			}
			notation = board.notationAddCheck(notation, getTurn());
			history.broadcast(ColorsAPI.colorToString(color) + ": " + notation);
			turnStatus = isCheckmate()?color + " won!!!":getTurn() + " turn";
			setTurn(ColorsAPI.getOp(color));
			setTurnStatus(turnStatus);
			informTurn();
		} else setJoinStatus("Invalid movement");
		UsersHandler.broadcastState(this,players,viewers);
		setJoinStatus("");
		board.printBoard(getTurn());
		sendBoard();
	}
	/**
	 * Convert standard chess notation to matrix
	 * representing move's initial and final position 
	 * @param string			standard chess notation [[a-h][1-8](initial)][[a-h][1-8](final)]
	 * @return					matrix representing move
	 * 							[0-7][0-7](initial)
	 * 							[0-7][0-7](final)
	 */
	public int[][] inputInt(String string) {
		if(string.length() != 4) return null;
		int[] init = new int[2];
		int[] fin = new int[2];
		init[0] = string.charAt(0) - 'a';
		init[1] = string.charAt(1) - '1';
		fin[0] = string.charAt(2) - 'a';
		fin[1] = string.charAt(3) - '1';		
		return new int[][] {init, fin};
	}
	/**
	 * Send current board state to users
	 * Not thread safe.
	 */
	public void sendBoard() {
		String output = "b " + board.toString(Piece.color.white);
		for(UserThread u : players)
			SocketAPI.writeToSocket(u.getUser().getSocket(), "b "+board.toString(u.getUser().getTurn()));
		ListAPI.writeToList(viewers,output);
	}
	/**
	 * Inform players of who has to play
	 */
	public void informTurn() {
		String output = "p "+turnToString();
		System.out.println(output);
		ListAPI.writeToList(players,output);
	}
	/**
	 * Reverse move's row
	 * Client interface has reversed indexing
	 * Used when receiving move from white player
	 * @param whiteMove				white move received
	 * @return						string with rows reverse
	 * 								i.e 1 becomes 8 and vice-versa
	 * 									2->7
	 * 									...
	 */
	public String reverseInput(String whiteMove) {
		char xi = whiteMove.charAt(0);
		char xf = whiteMove.charAt(2);
		char yi = (char)((char)Math.abs(whiteMove.charAt(1) -'0' - 9) + '0');
		char yf = (char)((char)Math.abs(whiteMove.charAt(3) -'0' - 9) + '0');
		return ""+xi+yi+xf+yf;
	}
	
	public char turnToString() {
		return ColorsAPI.colorToString(turn);
	}
	public LinkedList<UserThread> getPlayers() {
		return players;
	}
	public void setPlayers(LinkedList<UserThread> players) {
		this.players = players;
	}
	public LinkedList<UserThread> getViewers() {
		return viewers;
	}
	public void setViewers(LinkedList<UserThread> viewers) {
		this.viewers = viewers;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public boolean isRoomEmpty() {
		return roomEmpty;
	}
	public void setRoomEmpty(boolean roomEmpty) {
		this.roomEmpty = roomEmpty;
	}
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	public Piece.color getTurn() {
		return turn;
	}
	public void setTurn(Piece.color turn) {
		this.turn = turn;
	}
	public Thread getT() {
		return t;
	}
	public void setT(Thread t) {
		this.t = t;
	}
	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}
	public Server getServer() {
		return server;
	}
	public void setServer(Server server) {
		this.server = server;
	}
	public History getHistory() {
		return history;
	}
	public LinkedList<UserThread> getGuests() {
		return guests;
	}
	public void setGuests(LinkedList<UserThread> guests) {
		this.guests = guests;
	}
	public LinkedList<UserThread> getSpectators() {
		return spectators;
	}
	public void setSpectators(LinkedList<UserThread> spectators) {
		this.spectators = spectators;
	}
	public boolean isGameRunning() {
		return gameRunning;
	}
	public void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
	}
	public boolean isGameFinished() {
		return gameFinished;
	}
	public void setGameFinished(boolean gameFinished) {
		this.gameFinished = gameFinished;
	}
	public String getJoinStatus() {
		return joinStatus;
	}
	public void setJoinStatus(String joinStatus) {
		this.joinStatus = joinStatus;
	}
	public String getTurnStatus() {
		return turnStatus;
	}
	public void setTurnStatus(String turnStatus) {
		this.turnStatus = turnStatus;
	}
	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	public boolean isCheckmate() {
		return checkmate;
	}
	public void setCheckmate(boolean checkmate) {
		this.checkmate = checkmate;
	}
}