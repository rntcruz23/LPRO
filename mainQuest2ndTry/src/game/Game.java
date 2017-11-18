package game;

import java.util.Scanner;

import board.Board;
import pieces.Piece;

public class Game {
	static boolean flagChange = false;
	public static void main(String[] args) {
		Board board = new Board();
		Scanner input = new Scanner(System.in);
		String move;
		int[][] in;
		Piece.color side = Piece.color.white;
		
		while(true) {
			flagChange = false;
			board.printBoard(side);
			move = input.nextLine();
			in = inputInt(move);
			if(in == null) continue;
			if(board.move(in[0], in[1])) flagChange = !flagChange;
			if(flagChange && side == Piece.color.white) side = Piece.color.black;
			else if(flagChange && side == Piece.color.black) side = Piece.color.white;
		}
	}
	public static int[][] inputInt(String string) {
		if(string.length() != 4) return null;
		
		int[] init = new int[2];
		int[] fin = new int[2];
		init[0] = string.charAt(0) - 'a';
		init[1] = string.charAt(1) - '1';
		fin[0] = string.charAt(2) - 'a';
		fin[1] = string.charAt(3) - '1';		
		return new int[][] {init, fin};
	}
	public static void retHandler(int ret) {
		switch(ret) {
		case -2:
			System.out.println("Posição final inválida!");
			break;
		case -1:
			System.out.println("Posição inicial sem peça!");
			break;
		case 0:
			System.out.println("A peça não pode fazer esse move.");
			break;
		case 1:
			flagChange = true;
			break;
		case 2:
			System.out.println("Não podes ir para cima de uma peça tua.");
			break;
		case 3:
			System.out.println("Não podes saltar peças.");
			break;
		case 4:
			flagChange = true;
			break;
		case 5:
			flagChange = true;
			break;
		case 6:
			flagChange = true;
			break;
		default:
			break;
			
		}
	}
}
