package room.features;

import room.Room;

public class History extends Broadcasters{
	/**
	 *  Instantiate new history broadcaster
	 * @param room			room to broadcast on
	 */
	public History(Room room) {
		super(room);
	}
	/** 
	 * broadcast new chat entry
	 * i.e broadcast h + [input]
	 * @see room.features.Broadcasters#broadcast(java.lang.String)
	 */
	@Override
	public void broadcast(String input) {
		String output = "h "+input;
		super.broadcast(output);
	}
}