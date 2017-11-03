package maze.gui;

import javax.swing.SwingUtilities;
import dkeep.logic.Map;
import main.WindowThread;

public class MazeMain {
	public Map game;
	public MazeMain(Map newGame) {
		game = newGame;
	}
	public void guiPlay() {
		Map game;
		game = new Map();
		WindowThread t = new WindowThread(game);
		SwingUtilities.invokeLater(t);	
	}
}
