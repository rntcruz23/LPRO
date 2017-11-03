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
			String move = scan.next();
			char piece = move.charAt(0);
			int[] newPlace = new int[] {move.charAt(1)-'0'-1,move.charAt(2)-'0'-1};
			if(board.moveP(piece, newPlace,Peca.color.white)) {
				System.out.println("Movement available");
			}
			else {
				System.out.println("Wrong move");
				continue;
			}
			board.printBoard();
			System.out.println("Black to move: ");
			move = scan.next();
			piece = move.charAt(0);
			newPlace = new int[] {(move.charAt(1)-'0'-1),(move.charAt(2)-'0'-1)};
			if(board.moveP(piece, newPlace,Peca.color.black)) {
				System.out.println("Movement available");
			}
			else {
				System.out.println("Wrong move");
				continue;	
			}
		}
	}
}
