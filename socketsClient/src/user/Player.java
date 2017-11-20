package user;

import java.util.Scanner;

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
		GameView room = (GameView) window;
		room.run();
		Scanner scan = new Scanner(System.in);	
		scan.useDelimiter(System.getProperty("line.separator"));
		play =  false;
		inputReceived = false;
		while (true) {
			String input = scan.next();
			wait.lock();
			System.out.println("Sending command");
			sendCommand(input);
			wait.unlock();
		}
		//scan.close();
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
			default: return false;
		}
		return false;
	}
}