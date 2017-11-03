package pieces;

import mainQuest.Board;

public class Knight extends Peca {
	public Knight(int[] startPos,color piece) {
		super(startPos,piece,'K');
	}
	protected final int [][] kMoves = {{1,2},{1,-2},{-1,2},{-1,-2},{2,1},{2,-1},{-2,1},{-2,-1}};
	@Override
	public void claculateMoves() {
		int[] currPos = getPos();
		int [][] available;
		int i;
		available = new int[8][2];
		for (i = 0;i < 8; i++ ) {
			int nextR = currPos[0] + kMoves[i][0];
			int nextC = currPos[1] + kMoves[i][1];
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
