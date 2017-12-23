package room;

import java.io.Serializable;
import java.util.LinkedList;

import api.SocketAPI;
import pieces.Piece;
import users.UserThread;

public class RoomState implements Serializable{
	/**
	 * 
	 */
	private Piece.color turn;
	private String roomName;
	private boolean roomEmpty;
	private String joinStatus;
	private String turnStatus;
	private LinkedList<String> history;
	private static final long serialVersionUID = 1L;
	public static void sendRoom(UserThread user,Room room) {
		System.out.println("Sendind room state");
		SocketAPI.writeToSocket(user.getUser().getSocket(), "s");
		RoomState roomstate = getRoomState(room);
		try {
			Thread.sleep(100);
			user.getOut().writeObject(roomstate);
			System.out.println("State sent to user");
		}
		catch(Exception e) {
			System.out.println("Error sending: "+e.getMessage());
		}
	}
	public static void sendRoom(LinkedList<UserThread> user,Room room) {
		for(UserThread u: user)
			sendRoom(u,room);
	}
	public static RoomState getRoomState(Room room) {
		RoomState roomstate = new RoomState();
		roomstate.setHistory(room.getHistory().getPublicText());
		roomstate.setRoomName(room.getRoomName());
		roomstate.setRoomEmpty(room.isRoomEmpty());
		roomstate.setTurn(room.getTurn());
		roomstate.setJoinStatus(room.getJoinStatus());
		roomstate.setTurnStatus(room.getTurnStatus());
		return roomstate;
	}
	public Piece.color getTurn() {
		return turn;
	}
	public void setTurn(Piece.color turn) {
		this.turn = turn;
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
	public LinkedList<String> getHistory() {
		return history;
	}
	public void setHistory(LinkedList<String> history) {
		this.history = history;
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