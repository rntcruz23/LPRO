package dkeep.logic;

import javax.swing.ImageIcon;

public class Door extends Element {
	public Door(int[] coords, Map map,ImageIcon icon) {
		super(coords,'E',map,icon);
	}
	public void winCond() {
		setIcon(GameState.openD);
	}
}
