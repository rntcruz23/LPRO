package dkeep.logic;

import javax.swing.ImageIcon;

public class Sword extends Element{
	public Sword(Map map,ImageIcon icon) {
		super('S',map,icon);
	}
	public Sword(int[] start,Map map,ImageIcon icon) {
		super(start,'S',map,icon);
	}
}