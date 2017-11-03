package mainQuest;

import java.util.Scanner;

import pieces.Peca;

public class Game {
	public static void main(String[] args) {
		Board board = new Board();
		Scanner scan = new Scanner(System.in);
		board.fillBoard();
		while(true) {
			board.printBoard();
			System.out.println("White to move: ");
			GameInput.play(board,Peca.color.white,scan);
			board.printBoard();
			System.out.println("Black to move: ");
			GameInput.play(board,Peca.color.black,scan);
		}
	}
}
