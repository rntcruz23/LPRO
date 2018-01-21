package user;

import client.Client;

public class Guest extends User {
	
	/**
	 * Start new empty guest, used only for comparison
	 */
	public Guest() {
		
	}
	/**
	 * Start new guest
	 * @param client			client responsible
	 */
	public Guest(Client client) {
		super(client);
	}
	/* (non-Javadoc)
	 * @see user.User#processCommands(java.lang.String)
	 */
	@Override
	public boolean processCommands(String command) {
		if(super.processCommands(command)) return true;
		return false;
	}
}