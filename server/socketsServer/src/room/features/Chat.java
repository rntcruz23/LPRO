package room.features;

import java.util.LinkedList;

import room.Room;
import users.UserThread;

public class Chat extends Broadcasters{
	/**
	 * 
	 */
	public Chat(LinkedList<UserThread> viewers,LinkedList<UserThread> p,Room room) {
		super(viewers,p,room);
	}
	@Override
	public void broadcast(String input) {
		String output = "c "+input;
		super.broadcast(output);
	}
}