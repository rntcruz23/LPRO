package pieces;

public class King extends Peca {
	public King(int[] startPos,color piece) {
		super(startPos,piece,'S');
		calcMoves = new int[][] {{0,1},{1,0},{-1,0},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};
	}
}
