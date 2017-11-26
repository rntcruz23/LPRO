package user;

import socketsClient.Client;
import windows.game.GameView;

public class Guest extends User {
	public Guest(Client client) {
		super(client);
	}
	@Override
	public boolean processCommands(String command) {
		if(super.processCommands(command)) return true;
		char com = command.charAt(0);
		switch(com) {
			case 'j':
				GameView g = (GameView)getRoom();
				g.removeUserButtons();
				return true;
			default:return false;
		}
	}
}