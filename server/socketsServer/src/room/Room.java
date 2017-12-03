package room;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;

import api.ListAPI;
import api.SocketAPI;
import board.Board;
import pieces.Piece;
import room.features.Chat;
import room.features.History;
import socketsServer.Server;
import users.UserThread;

public class Room extends Window implements Runnable{
	/**
	 * 
	 */
	private LinkedList<UserThread> players;
	private LinkedList<UserThread> spectators;
	public LinkedList<UserThread> getSpectators() {
		return spectators;
	}
	public void setSpectators(LinkedList<UserThread> spectators) {
		this.spectators = spectators;
	}
	private LinkedList<UserThread> guests;
	public LinkedList<UserThread> getGuests() {
		return guests;
	}
	public void setGuests(LinkedList<UserThread> guests) {
		this.guests = guests;
	}
	private LinkedList<UserThread> viewers;
	private String roomName;
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
		setRoomEmpty(false);
		history = new History(viewers,players,this);
		chat = new Chat(viewers,players,this);
		addUser(player1);
	}
	public void remUser(UserThread user) {
		if(players.remove(user) && !spectators.isEmpty()) {
			UserThread nP = spectators.removeFirst();
			players.add(nP);
			char t = colorToString(user.getUser().getTurn());
			SocketAPI.writeToSocket(nP.getUser().getSocket(), "p "+t);
			
		}else {
			spectators.remove(user);
			guests.remove(user);
			viewers.remove(user);
		}
		server.getLob().getUsers().add(user);
		user.setRoom(server.getLob());
	}
	public void addUser(UserThread user) {
		if(user.getUser().getType() == 'g') {
			guests.add(user);
			viewers.add(user);
			SocketAPI.writeToSocket(user.getUser().getSocket(),"j s g");
		}else if(players.size() < 2)
			newPlayer(user);
		else newSpec(user);
		RoomState.sendRoom(user,this);
		SocketAPI.writeToSocket(user.getUser().getSocket(), "b "+board.printBoard(Piece.color.white));
	}
	@Override 
 	public void run(){
		setTurn(Piece.color.white);
		while((players.size() < 2))
			Thread.yield();
		prepPlayers();
		System.out.println("Room going to sleep");
		while((players.size() + viewers.size()) != 0)
			Thread.yield();
		closeRoom();	
	}
	public void closeRoom() {
		lock();
		ListAPI.terminateList(players);
		ListAPI.terminateList(viewers);
		unlock();
	}
	public void newSpec(UserThread user) {
		SocketAPI.writeToSocket(user.getUser().getSocket(),"j s s");
		spectators.add(user);
		viewers.add(user);
	}
	public void newPlayer(UserThread user) {
		SocketAPI.writeToSocket(user.getUser().getSocket(),"j s p");
		players.add(user);
		char t;
		if(players.size() == 1)
			t = 'w';
		else t = 'b';
		SocketAPI.writeToSocket(user.getUser().getSocket(),"t "+t);
	}
	public void prepPlayers() {
		players.get(0).setRoom(this);
		players.get(1).setRoom(this);
		players.get(0).getUser().setTurn(Piece.color.white);
		players.get(1).getUser().setTurn(Piece.color.black);
		sendBoard();
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
			remUser(user);
			break;
		case 'd':
		case 'f':
			informTurn();
			break;
		default: informTurn();
		}
	}
	public void playC(Piece.color color,String move) {
		if(color != turn) return;
		int[][] movement = inputInt(move);
		sendBoard();
		board.printBoard(getTurn());
		if(board.move(movement[0],movement[1])) {
			if(board.checkCheck(getTurn())) System.out.println(colorToString(color)+" king is in check");
			setTurn(getOp(color));
			informTurn();
			history.broadcast(move);
		}
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
	public Piece.color getOp(Piece.color color){
		if(color == Piece.color.white) return Piece.color.black;
		if(color == Piece.color.black) return Piece.color.white;
		return null;
	}
	public void sendBoard() {
		String output = "b "+board.printBoard(Piece.color.white);
		lock();
		ListAPI.writeToList(players,output);
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
	public char colorToString(Piece.color color) {
		if(color == Piece.color.white) return 'w';
		if(color == Piece.color.black) return 'b';
		return 'u';
	}
	public char turnToString() {
		return colorToString(turn);
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
}