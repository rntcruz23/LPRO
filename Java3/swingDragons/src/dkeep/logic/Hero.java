package dkeep.logic;
import javax.swing.ImageIcon;
public class Hero extends Element {
	private boolean armed = false;
	private boolean hasKey = false;
	private boolean alive = true;
	public Hero(Map map,ImageIcon icon) {
		super('H',map,icon);
		// TODO Auto-generated constructor stub
	}
	public Hero(int[] start,Map map, ImageIcon icon) {
		super(start,'H',map,icon);
	}
	public void updateArmed() {					//Change symbol if armed
		if(armed) {
			setSymb('A');
			setIcon(GameState.armed);
		}
		else {
			setSymb('H');
			setIcon(GameState.heroI);
		}
	}
	public boolean getArmed() {
		return armed;
	}
	public boolean getKey() {					//Test for key
		return hasKey;
	}
	public void giveKey() {						//Give key to hero
		setHasKey(true);
	}
	public void dead() {
		setAlive(false);
	}
	public boolean getAlive() {
		return alive;
	}
	public void setArmed(boolean arm) {
		armed = arm;
		updateArmed();
	}
	public void setHasKey(boolean key) {
		hasKey =  key;
	}
	public void setAlive(boolean live) {
		alive = live;
	}
}