package pieces;

public class Knight extends Peca {
	public Knight(int[] startPos,color piece) {
		super(startPos,piece,'N');
		calcMoves = new int[][] {{1,2},{1,-2},{-1,2},{-1,-2},{2,1},{2,-1},{-2,1},{-2,-1}};
	}
}
