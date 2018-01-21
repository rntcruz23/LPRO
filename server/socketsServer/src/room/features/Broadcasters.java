package room.features;

import java.util.LinkedList;

import api.ListAPI;
import room.Room;
import users.UserThread;

public class Broadcasters {
	private LinkedList<UserThread> viewers;
	private LinkedList<UserThread> players;
	private LinkedList<String> publicText;
	private Room room;
	
	/**
	 * Start new broadcaster object
	 * Broadcasters are not thread safe.
	 * @param room			room on which to broadcast
	 */
	public Broadcasters(Room room) {
		setPublicText(new LinkedList<String>());
		setPlayers(room.getPlayers());
		setViewers(room.getViewers());
		setRoom(room);
	}
	/**
	 * broadcast input to all players/viewers (general use)
	 * @param input			message to broadcast
	 */
	public void broadcast(String input) {
		addText(input);
		System.out.println("Broadcasting: "+input);
		ListAPI.writeToList(viewers, input);
		ListAPI.writeToList(players,input);
	}
	
	/**
	 * @return				returns room's viewers
	 */
	public LinkedList<UserThread> getViewers() {
		return viewers;
	}
	/**
	 * @param viewers		set broadcaster's viewers
	 */
	public void setViewers(LinkedList<UserThread> viewers) {
		this.viewers = viewers;
	}
	/**
	 * @return				returns broadcaster's history
	 */
	public LinkedList<String> getPublicText() {
		return publicText;
	}
	/**
	 * @param publicText	set broadcaster's history
	 */
	public void setPublicText(LinkedList<String> publicText) {
		this.publicText = publicText;
	}
	/**
	 * @return				get broadcaster's last entry
	 */
	public String getLast() {
		return publicText.getLast();
	}
	/**
	 * @param input			add new entry to broadcaster
	 */
	public void addText(String input) {
		publicText.add(input);
	}
	/**
	 * @return				return room being broadcasted to
	 */
	public Room getRoom() {
		return room;
	}
	/**
	 * @param room			set room to broadcast on
	 */
	public void setRoom(Room room) {
		this.room = room;
	}
	/**
	 * @return				get room's players
	 */
	public LinkedList<UserThread> getPlayers() {
		return players;
	}
	/**
	 * @param players		set broadcasters players
	 */
	public void setPlayers(LinkedList<UserThread> players) {
		this.players = players;
	}
}