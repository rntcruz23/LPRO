package dkeep.logic;

import javax.swing.ImageIcon;

public class Dragon extends Element {
	private boolean sleep = false;
	private boolean alive = true;
	private boolean sword = false;
	public int index;
	public Dragon(Map map,ImageIcon icon) {
		super('D',map,icon);
	}
	public Dragon(int[] start,Map map,ImageIcon icon) {
		super(start,'D',map,icon);
	}
	public void changeSleep() {
		sleep = !sleep;
		updateSleep();
	}
	private void updateSleep() {
		if (checkSleep()) {
			if (sword) {
				setSymb('f');
			}
			else {
				setSymb('d');
				setIcon(GameState.dragonS);
			}
		}
		else {
			if(sword) {
				setSymb('F');
				setIcon(GameState.dragonF);
			}
			else {
				setIcon(GameState.dragonI);
				setSymb('D');
			}
		}
	}
	public boolean checkSleep() {
		return sleep;
	}
	public boolean getAlive() {
		return alive;
	}
	public void changeAlive() {
		alive = false;
	}
	public void onSword() {
		sword = !sword;
		if (sword) {
			setSymb('F');
			setIcon(GameState.dragonF);
		}
		else {
			setSymb('D');
			setIcon(GameState.dragonI);
		}
	}
}