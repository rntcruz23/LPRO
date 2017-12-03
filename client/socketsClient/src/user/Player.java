package user;

import pieces.Piece;
import socketsClient.Client;
import windows.game.GameView;

public class Player extends Spectator{
	private Piece.color turn;
	boolean play;
	boolean inputReceived;
	public Player(Client client) {
		super(client);
	}
	public void setTurn(Piece.color turn) {
		this.turn = turn;
	}
	public void setTurn(char t) {
		if(t == 'w') setTurn(Piece.color.white);
		if(t == 'b') setTurn(Piece.color.black);
		return;
	}
	public char getTurn() {
		if(turn == Piece.color.white) return 'w';
		if(turn == Piece.color.black) return 'b';
		return 'u';
	}
	public boolean turnString(String input) {
		char com = input.charAt(0);
		if(com != 't') return false;
		char c = input.charAt(2);
		switch(c) {
			case 'w':
				setTurn(Piece.color.white);
			break;
			case 'b':
				setTurn(Piece.color.black);
			break;
			default: return false;
		}
		return true;
	}
	public void myTurn(String input) {
		play = true;
		if(input.length() != 3) play = false;
		char com = input.charAt(0);
		char c;
		if(com != 'p') play = false;
		c = input.charAt(2);
		play &= (c == getTurn());
	}
	public void play() {
		GameView room = (GameView) getRoom();
		room.run();
		while (true) {
			Thread.yield();
		}
	}
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
			default: System.out.println("Unknown player command");;
		}
		return false;
	}
}