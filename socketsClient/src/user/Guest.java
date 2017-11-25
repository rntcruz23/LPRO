package user;

import socketsClient.Client;

public class Guest extends User {
	public Guest(Client client) {
		super(client);
	}
	@Override
	public boolean processCommands(String command) {
		return super.processCommands(command);
	}
}
