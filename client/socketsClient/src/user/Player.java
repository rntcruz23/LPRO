package user;

import client.Client;
import pieces.Piece;
import windows.drawprompt.Draw;

public class Player extends Spectator{
	private Piece.color turn;
	boolean play;
	boolean inputReceived;
	
	/**
	 * Starts new player, empty. Used only for class comparison
	 */
	public Player() {
		super();
	}
	/**
	 * Start new player
	 * @param client				client responsible for user
	 */
	public Player(Client client) {
		super(client);
	}
	/**
	 * Set player color whit color enum
	 * @param turn				color enum
	 */
	public void setTurn(Piece.color turn) {
		this.turn = turn;
	}
	/**
	 * Set player color with char
	 * @param t					color as char [w]hite|[b]lack
	 */
	public void setTurn(char t) {
		if(t == 'w') setTurn(Piece.color.white);
		if(t == 'b') setTurn(Piece.color.black);
	}
	/**
	 * Get player color as a char
	 * @return					[w]hite|[b]lack
	 */
	public char getTurn() {
		if(turn == Piece.color.white) return 'w';
		if(turn == Piece.color.black) return 'b';
		return 'u';
	}
	/**
	 * Set player color
	 * @param input				server command t [color]
	 */
	public void turnString(String input) {
		char c = input.charAt(2);
		setTurn(c);
	}
	/**
	 * Define if player can play
	 * @param input				command from server defining turn: p [color as char]
	 */
	public void myTurn(String input) {
		play = true;
		if(input.length() != 3) play = false;
		char com = input.charAt(0);
		char c;
		if(com != 'p') play = false;
		c = input.charAt(2);
		play &= (c == getTurn());
	}
	/**
	 * Command list:
	 * p [color] 				- inform what turn it is
	 * t [color]				- inform my turn. Used in the beginning of the game
	 * d						- open draw prompt window
	 * @param command			command from server
	 * @return 					<code>true</code> if valid player command
	 * @see user.Spectator#processCommands(java.lang.String)
	 */
	@Override
	public boolean processCommands(String command) {
		if(super.processCommands(command))	return true;
		System.out.println("Player processing");
		char com = command.charAt(0);
		switch(com) {
			case 'p':
				System.out.println("New turn");
				myTurn(command);
				break;
			case 't':
				System.out.println("Setting my turn to "+command.charAt(2));
				turnString(command);
				break;
			case 'd':
				setBackWindow(getRoom());
				setRoom(new Draw(this));
				break;
			default: System.out.println("Unknown player command");
		}
		return false;
	}
}