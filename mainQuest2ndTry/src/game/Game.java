package game;

import board.Board;
import pieces.Piece;

public class Game {
	public static void main(String[] args) {
		Board board = new Board();
		int[] test1 = new int[] {0,1};
		int[] test2 = new int[] {0,2};
		board.printBoard(Piece.color.white);
		//board.printBoard(Piece.color.black);
		board.move(test1, test2);
		board.printBoard(Piece.color.white);
	}
}
