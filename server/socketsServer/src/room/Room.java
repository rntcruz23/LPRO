package room;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;

import api.ListAPI;
import api.SocketAPI;
import board.Board;
import pieces.Piece;
import room.features.Chat;
import room.features.History;
import users.UserThread;

public class Room extends Window implements Runnable{
	private LinkedList<UserThread> players;
	private LinkedList<UserThread> viewers;
	private String roomName;
	private int ocupation;
	private boolean roomEmpty;
	private Board board;
	private Chat chat;
	private History history;
	private Thread t;
	private Piece.color turn;
	public Room(String name,UserThread player1) {
		super();	
		setT(new Thread(this));
		setPlayers(new LinkedList<UserThread>());
		setViewers(new LinkedList<UserThread>());
		setBoard(new Board());
		setRoomName(name);
		setRoomEmpty(false);
		setOcupation(0);
		history = new History(viewers,players,this);
		chat = new Chat(viewers,players,this);
		players.add(player1);
	}
	public void addUser(UserThread user) {
		if(players.size() < 2) {
			players.add(user);
			char t;
			if(players.size() == 2)
				t = 'b';
			else t = 'w';
			SocketAPI.writeToSocket(user.getUser().getSocket(), "t "+t);
		}
		else {
			viewers.add(user);
			SocketAPI.writeToSocket(user.getUser().getSocket(), "v");
			user.getUser().setTurn(Piece.color.none);
		}
	}
	@Override 
 	public void run(){
		setTurn(Piece.color.white);
		while(players.size() < 2)
			Thread.yield();
		prepPlayers();
		System.out.println("Room going to sleep");
		while(!roomEmpty)
			Thread.yield();
		closeRoom();
		
	}
	public void closeRoom() {
		lock();
		ListAPI.terminateList(players);
		ListAPI.terminateList(viewers);
		unlock();
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
		case 'e':
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
	public int getOcupation() {
		return ocupation;
	}
	public void setOcupation(int ocupation) {
		this.ocupation = ocupation;
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
}