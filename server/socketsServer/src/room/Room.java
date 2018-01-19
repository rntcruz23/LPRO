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
	/**
	 * 
	 */
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
		history = new History(viewers,players,this);
		chat = new Chat(viewers,players,this);
		setTurn(Piece.color.white);
		setTurnStatus(getTurn()+" turn");
		UsersHandler.addUser(this,player1);
		setRoomEmpty(false);
		setGameRunning(false);
		setGameFinished(false);
	}
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
	public void closeRoom() {
		server.getLob().getRooms().remove(this);
		lock();
		ListAPI.terminateList(players);
		ListAPI.terminateList(viewers);
		unlock();
	}
	@Override
	public void processCommands(String input,UserThread user) {
		Piece.color color = user.getUser().getTurn();
		char com = input.charAt(0);
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
			if(gameRunning) {
				GameResultHandler.playerDefeated(this,user);
				GameResultHandler.playerWin(this,getOpponent(user));
				UsersHandler.broadcastState(this,players,viewers);
				gameRunning = false;
				gameFinished = true;
			}
			break;
		default: informTurn();
		}
	}
	public UserThread getOpponent(UserThread user) {
		int index = players.indexOf(user);
		return (players.size() < 2)?null:players.get(index^1);
	}
	public void playC(Piece.color color,String move) {
		if(!gameRunning) return;
		if(color != turn) return;
		if(turn == Piece.color.white) move = reverseInput(move);
		int[][] movement = inputInt(move);
		sendBoard();
		board.printBoard(getTurn());
		if(board.move(movement[0],movement[1],color)) {
			if(board.checkCheck(getTurn())) {
				System.out.println(ColorsAPI.colorToString(color)+" king is in check");
				setCheck(true);
			} else setCheck(false);
			if(board.checkCheckMate(getTurn())) {
				System.out.println(ColorsAPI.colorToString(color)+" king is in checkmate");
				turnStatus = ColorsAPI.colorToString(color)+" king is in checkmate";
				gameRunning = false;
				setCheckmate(true);
			}
			history.broadcast(ColorsAPI.colorToString(color) + ": " + move);
			setTurn(ColorsAPI.getOp(color));
			String turnStatus = getTurn()+" turn";
			setTurnStatus(turnStatus);
			informTurn();
		} else {
			setJoinStatus("Invalid movement");
		}
		UsersHandler.broadcastState(this,players,viewers);
		setJoinStatus("");
		board.printBoard(getTurn());
		sendBoard();
	}
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
	public void sendBoard() {
		String output = "b " + board.toString(Piece.color.white);
		lock();
		for(UserThread u : players)
			SocketAPI.writeToSocket(u.getUser().getSocket(), "b "+board.toString(u.getUser().getTurn()));
		ListAPI.writeToList(viewers,output);
		unlock();
	}
	public void informTurn() {
		String output = "p "+turnToString();
		System.out.println(output);
		lock();
		ListAPI.writeToList(players,output);
		unlock();
	}
	public char turnToString() {
		return ColorsAPI.colorToString(turn);
	}
	public String reverseInput(String whiteMove) {
		char xi = whiteMove.charAt(0);
		char xf = whiteMove.charAt(2);
		char yi = (char)((char)Math.abs(whiteMove.charAt(1) -'0' - 9) + '0');
		char yf = (char)((char)Math.abs(whiteMove.charAt(3) -'0' - 9) + '0');
		return ""+xi+yi+xf+yf;
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