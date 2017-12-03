package room;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
	private LinkedList<String> history;
	private static final long serialVersionUID = 1L;
	public static void sendRoom(UserThread user,Room room) {
		System.out.println("Sendind room state");
		SocketAPI.writeToSocket(user.getUser().getSocket(), "s");
		RoomState roomstate = getRoomState(room);
		try {
			Thread.sleep(500);
			user.getOut().writeObject(roomstate);
			System.out.println("State sent to user");
		}
		catch(Exception e) {
			System.out.println("Error sending: "+e.getMessage());
		}
	}
	public static RoomState getRoomState(Room room) {
		RoomState roomstate = new RoomState();
		roomstate.setHistory(room.getHistory().getPublicText());
		roomstate.setRoomName(room.getRoomName());
		roomstate.setRoomEmpty(room.isRoomEmpty());
		roomstate.setTurn(room.getTurn());
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
}
