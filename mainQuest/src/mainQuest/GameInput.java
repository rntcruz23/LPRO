package mainQuest;

import java.util.Scanner;

import pieces.Peca;

public class GameInput {
	public static String getInput(Scanner scan) {
		boolean valid = false;
		String input = "";
		while (!valid) {
			try {
				input = scan.next();
			}
			catch(Exception e) {
				valid = false;
				System.out.println("Invalid input");
				continue;
			}
			input = input.toUpperCase();
			input = algebraicNotToVector(input);
			valid = avaliateInput(input);
		}
		return input;
	}
	private static boolean avaliateInput(String input) {
		int size = input.length();
		boolean valid;
		if(size != 3)
			return false;
			char l = input.charAt(0);
			
			switch (l) {
			case 'N':
			case 'Q':
			case 'R':
			case 'P':
			case 'B':
			case 'K':
				valid = true;
				break;
			default: System.out.println("Unkown piece"); valid = false;
			}
			int[] pos = new int[] {input.charAt(1)-'0'-1,input.charAt(2)-'0'-1};
			valid &= Board.inMap(pos[0],pos[1]);
		return valid;
	}
	public static void play(Board board,Peca.color color,Scanner scan) {
		boolean moved = false;
		while(!moved) {
			String move = getInput(scan);
			if((moved = board.moveP(move,color))) {
				System.out.println("Movement available");
				return ;
			}
			System.out.println("Wrong move");
		}
	}
	public static String algebraicNotToVector(String input) {
		StringBuilder newInput = new StringBuilder(input);
		newInput.setCharAt(1, input.charAt(2));
		newInput.setCharAt(2, (char)(input.charAt(1)-'A'+'1'));
		return newInput.toString();
	}
}
