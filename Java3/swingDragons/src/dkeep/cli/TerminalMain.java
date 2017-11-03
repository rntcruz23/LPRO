package dkeep.cli;
import java.util.*;
import dkeep.logic.Map;
import dkeep.logic.*;
public class TerminalMain {
	public Map game;
	public TerminalMain(Map newGame) {
		game = newGame;
	}
	public void terminalPlay(){
		int numDragon = getInput();
		game = new Map();
		game.newGame(numDragon);									//Start new game
		String com;
		updateMap();
		Scanner scan = new Scanner(System.in);
		while(true) {
			com = scan.next();								//get user input
			int[] nextPos = game.getPos(com,game);				//process input
			if (nextPos[0] == -1 || nextPos[1] == -1)		//test for invalid commands
				continue;
			if(updateHero(nextPos)) {						//Hero moves
				updateMap();								//refresh screen
				//cicle through
				game.validDragons();								//Test hero for adj dragon
				if(game.testGame() || game.testWin())		//Test win/lose conditions
					break;
				//Cicle through array
				game.updateDragons();		//Move dragons	
				game.validDragons();
			}				
			updateMap();
			game.printOut();
			if(game.testGame() || game.testWin())		   		//Test win/lose conditions
				break;
		}
		endingScreen();
		scan.close();
	}
	private void endingScreen() {
		if (game.testGame()) 
			System.out.println("You lose!");
		if(game.testWin()) {
			final String cup = 
					"  win!\n"  
							+ "______\n"
							+ "\\    /\n"
							+ " \\  /\n"
							+ "  \\/\n"
							+ "  ()\n"
							+ "  ()\n"
							+ " _()_\n"
							+ "[____]";
			System.out.println(cup);
		}
	}
	public void updateMap() {
		game.getMap().printMap();
	}
	public boolean updateHero(int[] nextPos) {
		return game.testMove(game.getMap().getHero(),nextPos);
	}
	public int getInput() {
		Scanner scan = new Scanner(System.in);
		int numDragon = 0;
		while(true) {
			System.out.print("Input number of dragons: ");
			numDragon = scan.nextInt();
			if(numDragon > 0 && numDragon < 15)	break;
			System.out.println("Invalid number of dragons, 15 > dragons > 0");
		}
		return numDragon;
	}
}