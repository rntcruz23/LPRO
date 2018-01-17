package room;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.LinkedList;

import pieces.Piece;
import user.User;

public class RoomState implements Serializable{
	/**
	 * 
	 */
	private Piece.color turn;
	private String roomName;
	private boolean roomEmpty;
	private LinkedList<String> history;
	private String turnStatus;
	private String joinStatus;
	private String whitePlayer;
	private String blackPlayer;
	private String nextPlayer;
	private static final long serialVersionUID = 1L;
	public static RoomState waitroom(User user,ObjectInputStream in) {
		try {
			return (RoomState)in.readObject();
		}
		catch(Exception e) {
			System.out.println("Error loading: "+e.getMessage());
			e.printStackTrace();
			return null;
		}
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
	public String getTurnStatus() {
		return turnStatus;
	}
	public void setTurnStatus(String turnStatus) {
		this.turnStatus = turnStatus;
	}
	public String getJoinStatus() {
		return joinStatus;
	}
	public void setJoinStatus(String joinStatus) {
		this.joinStatus = joinStatus;
	}
	public String getWhitePlayer() {
		return whitePlayer;
	}
	public void setWhitePlayer(String whitePlayer) {
		this.whitePlayer = whitePlayer;
	}
	public String getBlackPlayer() {
		return blackPlayer;
	}
	public void setBlackPlayer(String blackPlayer) {
		this.blackPlayer = blackPlayer;
	}
	public String getNextPlayer() {
		return nextPlayer;
	}
	public void setNextPlayer(String nextPlayer) {
		this.nextPlayer = nextPlayer;
	}
}