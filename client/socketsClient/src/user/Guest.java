package user;

import client.Client;

public class Guest extends User {
	public Guest() {
		
	}
	public Guest(Client client) {
		super(client);
	}
	@Override
	public boolean processCommands(String command) {
		if(super.processCommands(command)) return true;
		return false;
	}
}