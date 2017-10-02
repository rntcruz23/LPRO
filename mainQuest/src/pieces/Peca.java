package pieces;
 
public class Peca {
	private int points;
	private int[][] availableMoves;
	private int[] pos;
	private boolean alive;
	
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
	public Peca(int[] startPos) {
		setPos(startPos);
		setState(true);
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
	protected void setAvailable(int[][] newMoves) {
		availableMoves = newMoves;
	}
}
