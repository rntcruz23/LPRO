package user;

import socketsClient.Client;
import windows.game.GameView;
import windows.newroom.CreateRoom;

public class Spectator extends User{
	private String username;
	private String password;
	public Spectator(Client client) {
		super(client);
	}
	protected void chat(String message) {
	}
	@Override
	public boolean processCommands(String com) {
		if(super.processCommands(com)) return true;
		char command = com.charAt(0);
		switch(command) {
		case 'n':
			if(ev(com.charAt(2))) {
				System.out.println("Created room");
				String roomname = com.substring(4,com.length());
				getBackWindow().getFrmChess().setVisible(false);
				getRoom().getFrmChess().setVisible(false);
				Player n = new Player(client);
				setType(n,getName(),getPassword(),new GameView(n));
				GameView r = (GameView) n.getRoom();
				r.getLblGameRoom().setText(roomname);
				return true;				
			}
			else {
				CreateRoom c = (CreateRoom) getRoom();
				c.getStatus().setText("Error creating room");
				System.out.println("Error creating");
			}
		case 'p':
			char t = com.charAt(2);
			Player n = new Player(client);
			n.setTurn(t);
			System.out.println("Player left, your turn: "+t);
			setType(n,getName(),getPassword(),getRoom());
			break;
		default:System.out.println("Unknown command");
		}
		return false;	
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}