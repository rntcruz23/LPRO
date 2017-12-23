package room;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;

import api.*;
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
		setTurn(Piece.color.white);
		setTurnStatus(getTurn()+" turn");
		addUser(player1);
		setGameRunning(false);
		setGameFinished(false);
	}
	public void remUser(UserThread user) {
		if(players.remove(user) && !spectators.isEmpty()) {
			UserThread nP = spectators.removeFirst();
			players.add(nP);
			char t = ColorsAPI.colorToString(user.getUser().getTurn());
			SocketAPI.writeToSocket(nP.getUser().getSocket(), "p "+t);
			
		}else {
			spectators.remove(user);
			guests.remove(user);
			viewers.remove(user);
		}
		server.getLob().getUsers().add(user);
		user.setRoom(server.getLob());
		gameRunning = players.size() == 2;
	}
	public void addUser(UserThread user) {
		String newUser = "guest";
		if(user.getUser().getType() == 'g') {
			guests.add(user);
			viewers.add(user);
			SocketAPI.writeToSocket(user.getUser().getSocket(),"j s g");
		}
		else{
			if(players.size() < 2)
				newPlayer(user);
			else newSpec(user);
			newUser = user.getUser().getName();
		}
		setJoinStatus(newUser+" joined");
		RoomState.sendRoom(players,this);
		RoomState.sendRoom(viewers,this);
		SocketAPI.writeToSocket(user.getUser().getSocket(), "b "+board.toString());
		gameRunning = players.size() == 2;
	}
	@Override 
 	public void run(){
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
			UserThread op = getOpponent(user);
			promptDraw(op);
			break;
		case 'a':
			drawGame();
			RoomState.sendRoom(players,this);
			RoomState.sendRoom(viewers,this);
			break;
		case 'f':
			informTurn();
			break;
		default: informTurn();
		}
	}
	public void drawGame() {
		for(UserThread user: players) {
			String username = user.getUser().getName();
			String password = user.getUser().getPassword();
			int[] currStats;
			try {
				currStats = server.getDb().getinfo(username,password);
				server.getDb().changeinfo(username, password, currStats[0],currStats[1], currStats[2]+1);
				setGameRunning(false);
				setGameFinished(true);
				setTurnStatus("Both players agreed to a draw");
			} catch (SQLException e) {
				System.out.println("Error updating stats");
			}
		}
	}
	void promptDraw(UserThread user) {
		SocketAPI.writeToSocket(user.getUser().getSocket(),"d");
	}
	public UserThread getOpponent(UserThread user) {
		int index = players.indexOf(user);
		return players.get(index^1);
	}
	public void playC(Piece.color color,String move) {
		if(!gameRunning) return;
		if(color != turn) return;
		int[][] movement = inputInt(move);
		sendBoard();
		board.printBoard(getTurn());
		if(board.move(movement[0],movement[1],color)) {
			if(board.checkCheck(getTurn())) System.out.println(ColorsAPI.colorToString(color)+" king is in check");
			String turnStatus = getTurn()+" turn";
			if(board.checkCheckMate(getTurn())) {
				System.out.println(ColorsAPI.colorToString(color)+" king is in check mate");
				turnStatus = ColorsAPI.colorToString(color)+" king is in check mate";
				gameRunning = false;
			}
			history.broadcast(ColorsAPI.colorToString(color) + ": " + move);
			setTurn(ColorsAPI.getOp(color));
			setTurnStatus(turnStatus);
			RoomState.sendRoom(players,this);
			RoomState.sendRoom(viewers,this);
			informTurn();
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
	public void sendBoard() {
		String output = "b "+board.toString();
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
}