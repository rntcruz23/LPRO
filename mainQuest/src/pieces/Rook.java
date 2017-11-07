package pieces;

import mainQuest.Board;

public class Rook extends Peca {

	public Rook(int[] startPos,color piece) {
		super(startPos,piece,'R');
		calcMoves = new int[14*2][2];
		rookMoves(calcMoves);
	}
	@Override
	public void calculateMoves() {
		super.calculateMoves();
		setAvailableFirstHalf();
		setAvailableSecondHalf();
	}
	public static void rookMoves(int[][] vec){
		int row,col,nextsum = -7,offset = 0;
		for(row = 0; row < 14; row++) {
			vec[row][0] = nextsum + offset;
			vec[row][1] = 0;
			if((++nextsum) == 0)
				offset = 1;
		}
		nextsum = - 7;
		offset = 0;
		for(col = 14; col < 28;col++) {
			vec[col][0] = 0;
			vec[col][1] = nextsum + offset;
			if((++nextsum) == 0)
				offset = 1;
		}
		
	}
}