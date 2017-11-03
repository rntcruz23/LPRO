package dkeep.logic;
public class Map extends GameState{
	private Element[][]  gameMap = new Element [10][10];
	public Map() {
		startMap();	
		setMap(this);
	}
	private void startMap() {
		int row, column;
		for(row=0;row<10;row++) {
			for(column=0;column<10;column++) {
				int [] newPos = {row,column};
				gameMap[row][column]= new Element(newPos,' ',this,space);
			}
		}
		for(row=0;row<10;row++) {
			gameMap[row][0]=new Element(new int[] {row,0},'X',this,wall);
			gameMap[row][9]=new Element(new int[] {row,9},'X',this,wall);
		}
		for(column=0;column<10;column++) {
			gameMap[0][column]=new Element(new int[] {0,column},'X',this,wall);
			gameMap[9][column]=new Element(new int[] {9,column},'X',this,wall);
		}
		for(row=2;row<8;row++) {
			for(column=2;column<8;column++) {
				if(column == 4 || column == 6 || row == 5) continue;
				else gameMap[row][column] = new Element(new int[] {row,column},'X',this,wall);
			}
		}
		gameMap[5][7] = new Element(new int[] {5,7},'X',this,wall);
		gameMap[8][2] = new Element(new int[] {8,2},'X',this,wall);
		gameMap[8][3] = new Element(new int[] {8,3},'X',this,wall);
	}
	public void printMap() {
		int row, column;
		for(row = 0; row < 10; row++) {
			for(column = 0; column < 10; column++) {
				System.out.print(getChar(new int[] {row,column}).getSymb());
				System.out.print(" ");
			}
			System.out.print("\n");
		}
	}
	public void setChar(Element el) {						//set Element el in coords
		gameMap[el.getCoord()[0]][el.getCoord()[1]] = el;
	}
	public boolean testChar(int[] newCoord,char ...args) {		//Return false for args in newCoord
		char nextPos = getChar(newCoord).getSymb();
		for(char arg : args) {
			if (nextPos == arg)
				return false;
		}
		return true;
	}
	public Element getChar(int[] coords){					//Return Element at coords
		return gameMap[coords[0]][coords[1]];
	}
}