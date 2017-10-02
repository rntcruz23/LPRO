package pieces;

public class Knight extends Peca {
	public Knight(int[] startPos) {
		super(startPos);
		// TODO Auto-generated constructor stub
	}
	protected final int [][] kMoves = {{1,3},{1,-3},{-1,3},{-1,-3}};
	public void calculateMoves() {
		int[] currPos = getPos();
		int [][] available;
		int i;
		int nextM = 0;
		if (currPos[0] + 3 < 8)
			nextM++;
		if (currPos[0] - 3 > 0)
			nextM++;
		if (currPos[1] - 3 > 0)
			nextM++;
		if (currPos[1] + 3 < 8)
			nextM++;
		available = new int[nextM][2];
		for (i = 0;i < nextM; i++ ) {
			available[i][0] = currPos[0] + kMoves[i][0];
			available[i][1] = currPos[1] + kMoves[i][1];
		}
	}
}
