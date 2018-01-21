package user;

import java.util.StringTokenizer;

import client.Client;
import windows.game.GameView;
import windows.newroom.CreateRoom;
import windows.stats.Stats;

public class Spectator extends User{
	private String username;
	private String password;
	
	/**
	 * Empty spectator, used only for class comparison
	 * 
	 */
	public Spectator() {
	
	}
	/**
	 * Start new spectator
	 * @param client					client responsible
	 */
	public Spectator(Client client) {
		super(client);
	}
	/**
	 * Command list:
	 * n [room name]					- new room created
	 * z [color]						- inform spectator that it is now a player
	 * y [wins] [losses] [draws]		- inform player statistics
	 * @param com							command form server
	 * @return							<code>true</code> if valid spectator command
	 * @see user.User#processCommands(java.lang.String)
	 */
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
			break;
		case 'z':
			char t = com.charAt(2);
			Player n = new Player(client);
			n.setTurn(t);
			System.out.println("Player left, your turn: "+n.getTurn());
			setType(n,getName(),getPassword(),getRoom());
			GameView play = (GameView)getRoom();
			play.enablePlayerButtons();
			play.getTurnLabel().setText("Player left, your turn: "+n.getTurn());
			break;
		case 'y':
			int[] stats = getStatsFromServer(com.substring(2,com.length()));
			Stats statsw = (Stats) getRoom();
			statsw.getWinsValue().setText(""+stats[0]);
			statsw.getLostValue().setText(""+stats[1]);
			statsw.getDrawsValue().setText(""+stats[2]);
			break;
		default:System.out.println("Unknown spectator command");
		}
		return false;	
	}
	/**
	 * Convert input from server to array
	 * @param com						input from server y [wins] [losses] [draws]
	 * @return							array containing statistics
	 */
	public int[] getStatsFromServer(String com) {
		StringTokenizer tok = new StringTokenizer(com," ");
		int[] n = new int[3];
		n[0] = Integer.parseInt(tok.nextToken());
		n[1] = Integer.parseInt(tok.nextToken());
		n[2] = Integer.parseInt(tok.nextToken());
		return  n;
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