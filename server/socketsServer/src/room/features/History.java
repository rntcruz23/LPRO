package room.features;

import java.util.LinkedList;

import room.Room;
import users.UserThread;

public class History extends Broadcasters{
	/**
	 * 
	 */
	public History(LinkedList<UserThread> viewers,LinkedList<UserThread> p,Room room) {
		super(viewers,p,room);
	}
	@Override
	public void broadcast(String input) {
		String output = "h "+input;
		super.broadcast(output);
	}
}