package pieces;

import mainQuest.Board;

public abstract class Peca {
	private int points;
	private char piece;
	public static enum color {white,black,none};
	private color pieceColor;
	private int[][] availableMoves;
	private int[] pos;
	private boolean alive;
	protected int [][] calcMoves;
	public static final int[] sk1w = {0,1};
	public static final int[] sk2w = {0,6};
	public static final int[] sk1b = {7,1};
	public static final int[] sk2b = {7,6};
	
	public static final int[] sb1w = {0,2};
	public static final int[] sb2w = {0,5};
	public static final int[] sb1b = {7,2};
	public static final int[] sb2b = {7,5};
	
	public static final int[] sr1w = {0,0};
	public static final int[] sr2w = {0,7};
	public static final int[] sr1b = {7,0};
	public static final int[] sr2b = {7,7};
	
	public static final int[] p1w = {1,0};
	public static final int[] p1b = {6,0};
	
	public static final int[] qw = {0,3};
	public static final int[] qb = {7,3};
	
	public static final int[] kw = {0,4};
	public static final int[] kb = {7,4};
	
	public boolean getState() {
		return alive;
	}
	public void setState(boolean state) {
		alive = state;
	}
	public Peca(int[] startPos,color pieceColor,char piece) {
		setPos(startPos);
		setState(true);
		setColor(pieceColor);
		setPiece(piece);
	}
	public int[] getPos(){
		return pos;
	}
	public void setPos(int[] newPos) {
		if (newPos[0] >= 8 || newPos[0] < 0 || newPos[1] < 0 || newPos[1] >= 8)
			return ;
		pos = newPos.clone();
	}
	public int getPoints(){
		return points;
	}
	public int[][] getAvailable(){
		return availableMoves;
	}
	public void setAvailable(int[][] newMoves) {
		availableMoves = newMoves;
	}
	public char getPiece() {
		return piece;
	}
	public void setPiece(char piece) {
		this.piece = piece;
	}
	public color getColor() {
		return pieceColor;
	}
	public void setColor(color pcolor) {
		this.pieceColor = pcolor;
	}
	public void claculateMoves() {
		int[] currPos = getPos();
		int [][] available;
		int i;
		available = new int[calcMoves.length][2];
		for (i = 0;i < available.length; i++ ) {
			int nextR = currPos[0] + calcMoves[i][0];
			int nextC = currPos[1] + calcMoves[i][1];
			if(!Board.inMap(nextR,nextC)) {
				nextR = 0;
				nextC = 0;
			}
			available[i][0] = nextR;
			available[i][1] = nextC;
		}	
		setAvailable(available);	
	}
}
