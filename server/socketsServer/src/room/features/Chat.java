package room.features;

import room.Room;

public class Chat extends Broadcasters{
	/**
	 * Instantiate new chat broadcaster
	 * @param room			room to broadcast on
	 */
	public Chat(Room room) {
		super(room);
	}
	/** 
	 * broadcast new chat entry
	 * i.e broadcast c + [input]
	 * @see room.features.Broadcasters#broadcast(java.lang.String)
	 */
	@Override
	public void broadcast(String input) {
		String output = "c "+input;
		super.broadcast(output);
	}
}