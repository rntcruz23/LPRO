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
	public Broadcasters(LinkedList<UserThread> viewers,LinkedList<UserThread> p,Room room) {
		setPublicText(new LinkedList<String>());
		setPlayers(p);
		setViewers(viewers);
		setRoom(room);
	}
	public void broadcast(String input) {
		addText(input);
		System.out.println("Broadcasting: "+input);
		room.lock();
		ListAPI.writeToList(viewers, input);
		ListAPI.writeToList(players,input);
		room.unlock();
	}
	public LinkedList<UserThread> getViewers() {
		return viewers;
	}
	public void setViewers(LinkedList<UserThread> viewers) {
		this.viewers = viewers;
	}
	public LinkedList<String> getPublicText() {
		return publicText;
	}
	public void setPublicText(LinkedList<String> publicText) {
		this.publicText = publicText;
	}
	public String getLast() {
		return publicText.getLast();
	}
	public void addText(String input) {
		publicText.add(input);
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public LinkedList<UserThread> getPlayers() {
		return players;
	}
	public void setPlayers(LinkedList<UserThread> players) {
		this.players = players;
	}
}