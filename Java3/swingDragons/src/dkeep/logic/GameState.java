package dkeep.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.ImageIcon;

public class GameState {
	private Map map;
	protected Hero hero;
	protected ArrayList<Dragon> dragons;
	protected Sword sword;
	protected Door door;
	protected int numDragons;
	private boolean gameOver = false;
	private boolean win = false;
	private String outputString;
	private int dragonsKilled;
	private int movement; //1 -> random; 0 -> sleep
	public boolean gameStarted;
	public static ImageIcon openD,closedD,wall,swordI,heroI,armed,dragonI,dragonS,dragonF,space;
	public GameState(){
		outputString = " ";
		gameStarted =  false;
		dragonsKilled = 0;
		movement = 0;
		wall = createImageIcon("images/wall.png");
		swordI =  createImageIcon("images/sword.jpg");
		heroI = createImageIcon("images/heroU.png");
		armed = createImageIcon("images/armedH.jpg");
		dragonI = createImageIcon("images/dragonAwake.jpg");
		dragonS = createImageIcon("images/dragonSleep.jpg");
		space = createImageIcon("images/space.jpg");
		openD = createImageIcon("images/openDoor.jpg");
		closedD = createImageIcon("images/closedDoor.jpg");
		dragonF = createImageIcon("images/dragonF.png");
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map mapa) {
		map = mapa;
	}
	public void newGame(int num) {
		numDragons = num;
		gameStarted = true;
		dragons =  new ArrayList<Dragon>(numDragons);
		for(int i = 0;i < numDragons;i++) {
			Dragon dragAux = new Dragon(map,dragonI); 
			dragAux.index = i;
			dragons.add(dragAux);
			map.setChar(dragAux);
		}
		map.setChar(door = new Door(new int[] {5,9},map,closedD));
		map.setChar(hero = new Hero(map,heroI));
		map.setChar(sword = new Sword(map,swordI));
	}
	public void setHero(Hero hero) {
		this.hero = hero;
	}
	public void setSword(Sword sword) {
		this.sword = sword;
	}
	public void setList(ArrayList<Dragon> list) {
		numDragons = list.size();
		dragons = list;	
	}	
	public Dragon getDrag(Dragon index) {
		return index;
	}
	public ArrayList<Dragon> getList() {
		return dragons;
	}
	public Hero getHero() {
		return hero;
	}
	public void setDoor(Door door){
		this.door = door;
	}
	public Door getDoor() {
		return door;
	}
	public Sword getSword() {
		return sword;
	}
	public boolean testGame() {
		return gameOver;
	}
	public boolean testWin() {
		return win;
	}
	public void changeGame() {
		gameOver = !gameOver;
	}
	public void changeWin() {
		win = !win;
		outputString = "Win!";
		
	}
	private boolean testTop(int[] coords,Dragon drag) {
		return (coords[0] == drag.getCoord()[0]-1) && (coords[1] == drag.getCoord()[1]);
	}
	private boolean testBot(int[] coords,Dragon drag) {
		return (coords[0] == drag.getCoord()[0]+1) && (coords[1] == drag.getCoord()[1]);
	}
	private boolean testLeft(int[] coords,Dragon drag) {
		return (coords[0] == drag.getCoord()[0]) && (coords[1] == drag.getCoord()[1]-1);
	}
	private boolean testRight(int[] coords,Dragon drag) {
		return (coords[0] == drag.getCoord()[0]) && (coords[1] == drag.getCoord()[1]+1);
	}	
	public boolean testDragon(int[] coords,Dragon dragAux) {		//false = adjacent
		return !(testRight(coords,dragAux) || testLeft(coords,dragAux) || testTop(coords,dragAux) || testBot(coords,dragAux));	
	}
	public boolean testMove(Hero myHero,int[] newCoord) { 
		if (!map.testChar(newCoord,'X')){
			outputString = "Wall!";
			return false;
		}
		if(!map.testChar(newCoord,'E')){
			if(!getHero().getKey()) {
				outputString = "Key missing!"; 
				return false;
			}
			changeWin();
		}
		if(!myHero.getArmed() && !map.testChar(newCoord, 'd','f')) {
			outputString = "Don't wake the dragon";
			return false;
		}
		if (!map.testChar(newCoord, 'S')) {
			myHero.setArmed(true);
			myHero.updateArmed();
			outputString = "Sword picked up!";
		}
		moveChar(myHero,newCoord);
		return true;
	}
	public boolean validDragon(Dragon index) {
		int[] pos = getHero().getCoord();
		return testDragon(pos,index);
	}
	public boolean validDragons() {
		for(Dragon index : dragons) {
			if(!index.getAlive()) continue;
			if(!validDragon(index) && !hero.getArmed() && !index.checkSleep()) {	//check if adj,armed,sleep
				hero.dead();
				changeGame();
				return false;
			}
			if(hero.getArmed() && !validDragon(index)) {
				map.setChar(new Element(index.getCoord(),' ',map,space));
				index.changeAlive();
				outputString = "Dragon slain || Key picked up";
				dragonsKilled++;
				checkKey();
			}
		}
		return true;
	}
	public void checkKey() {
		if(dragonsKilled >= numDragons) {
			map.getHero().giveKey();
			door.winCond();
		}
	}
	public void moveChar(Element el,int[] newCoord) {
		int[] oldCoord = el.getCoord();
		Element old = new Element(el.getCoord(),' ',map,space);
		if(el.getSymb() == 'F' ){
			Dragon aux = (Dragon) el;
			if((map.getSword().getCoord()[0] == oldCoord[0]) && (map.getSword().getCoord()[1] == oldCoord[1])){
				old = map.getSword();
				aux.onSword();
			}
		}
		map.setChar(old);										//Clear old position
		el.setCoord(newCoord);									// Set new coord on el
		map.setChar(el);	
	}
	public void dragonMove(Dragon index){
		Random rand = new Random();
		int row;
		int col;
		int []newPos;
		int drag_c = map.getDrag(index).getCoord()[1];
		int drag_r = map.getDrag(index).getCoord()[0];
		int i = 0;
		while (i <= 4) {
			row = rand.nextInt(drag_r + 1 - (drag_r - 1) + 1) + drag_r - 1;
			col = rand.nextInt(drag_c + 1 - (drag_c - 1) + 1) + drag_c - 1;
			if (row != drag_r && col != drag_c) continue;
			newPos = new int[]{row,col};
			if (!map.testChar(newPos,'S')) {
				moveChar(map.getDrag(index),newPos);
				map.getDrag(index).onSword(); 
				break;
			}
			if(!map.testChar(newPos,' ')) {
				moveChar(map.getDrag(index),newPos);
				break;
			}
			i++;
		}
	}
	public void printOut() {
		System.out.println(outputString);
		outputString = " ";
		
	}
	private ImageIcon createImageIcon(String path) {
		java.net.URL imgUrl = getClass().getResource(path);
		if(imgUrl != null)
			return new ImageIcon(imgUrl);
		System.out.println("No file path " + path);
		return null;	
	}
	public void updateDragons() {
		Iterator<Dragon> it = getList().iterator();
		Random rand = new Random();
		for(Dragon index : getList()) {
			index = it.next();
			if(rand.nextInt(99) + 25*movement < 25) 
				index.changeSleep();
			else if (getDrag(index).getAlive() && !getDrag(index).checkSleep()) 				//Move Dragon if alive
				dragonMove(index);
		}
	}
	public int getMove() {
		return movement;
	}
	public void setMove(int m) {
		movement = m;
	}
	public void removeChar() {
		if(hero == null || dragons == null || sword == null) return ;
		map.setChar(new Element(hero.getCoord(), ' ', map, space));
		map.setChar(new Element(sword.getCoord(), ' ', map, space));
		for(Dragon aux : dragons)
			map.setChar(new Element(aux.getCoord(), ' ', map, space));
	}
	public int[] getPos(String com,GameState map) {	
		int next_row = map.getHero().getCoord()[0] ,next_col = map.getHero().getCoord()[1];
		int curr_row = next_row ,curr_col = next_col;
		switch(com) {
		case "W": 
		case "w":	next_row = curr_row - 1;
		break;
		case "S":
		case "s":	next_row = curr_row + 1;
		break;
		case "A":
		case "a":	
			next_col = curr_col - 1;
			break;
		case "D":
		case "d":	next_col = curr_col + 1;
		break;
		default: System.out.println("Unknown"); next_row = -1; next_col = -1;
		}
		return (new int[] {next_row,next_col});
	}
	public void resetGame() {
		dragonsKilled = 0;
		if(testGame()) changeGame();
		if(testWin()) changeWin();
		removeChar();
	}
	public String getOutput() {
		return outputString;
	}
}