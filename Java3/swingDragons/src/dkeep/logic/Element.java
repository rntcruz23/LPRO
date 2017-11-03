package dkeep.logic;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
public class Element {
	private final int defaultWidth = 50;
	private final int defaultHeigth = 50;
	private int[] coordinates = new int[2];
	private char symb;
	private ImageIcon icon;
	public Element(int[] startCoord,char symbol,Map map,ImageIcon icon) {	//Constructor, set initial coordinates
			setSymb(symbol);
			setCoord(startCoord);
			setIcon(icon);
	}
	public Element (char symbol,Map gameMap,ImageIcon icon) {			//set Element el in random
		Random rand = new Random();
		setSymb(symbol);
		setIcon(icon);
		int row, column;
		Iterator<Dragon> it;
		boolean val = true;
		while(true) { 
			it = gameMap.dragons.iterator();
			row = rand.nextInt(8) + 1; 
			column = rand.nextInt(8) + 1;
			int []newPos = new int[]{row,column};
			while(it.hasNext() && (symbol == 'H')) {
				if(!(val &= gameMap.testDragon(newPos,it.next())))
					break;
			}
			switch (symbol){
				case 'D': val = !gameMap.testChar(newPos,' '); break;
				case 'S': val &= (gameMap.testChar(newPos,'D','X','E','H')); break;
				case 'H': val &= (gameMap.testChar(newPos,'D','X','E')); break;
			}
			if(val){
				setCoord(newPos);  
				break;
			}
			val = true;
		}
	}
	public void setCoord (int[] newCoord) {
		coordinates = newCoord;
	}
	public int[] getCoord() {
		return coordinates;
	}
	public void setSymb(char newSymb) {
		symb = newSymb;
	}
	public char getSymb() {
		return symb;
	}
	public ImageIcon getIcon() {
		return icon;
	}
	public void setIcon(ImageIcon newIcon) {
		newIcon = getScaledImage(newIcon,defaultWidth,defaultHeigth);
		icon = newIcon;
	}
	private ImageIcon getScaledImage(ImageIcon srcImgIcon, int w, int h){
		Image srcImg = srcImgIcon.getImage();
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();
	    
	    return new ImageIcon(resizedImg);
	}
}