package main;
import java.util.Scanner;

import dkeep.cli.TerminalMain;
import dkeep.logic.Map;
import maze.gui.MazeMain;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Map game = new Map();
		String c;
		System.out.println("Play on Terminal or GUI? <T/G>");
		c = scan.next();
		switch(c) {
			case "T":
			case "t":
				TerminalMain terminal = new TerminalMain(game);
				terminal.terminalPlay();
				break;
			case "G":
			case "g":
				MazeMain gui = new MazeMain(game);
				gui.guiPlay();
				break;
			default: System.out.println("Unkown command");
		}
		scan.close();
	}
}