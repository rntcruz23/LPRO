package pieces;

public class Bishop extends Peca {

	public Bishop(int[] startPos,color piece) {
		super(startPos,piece,'B');
		calcMoves = new int[14*2][2];
		int row,col,nextsum = -calcMoves.length/4,offset = 0;
		for(row = 0; row < calcMoves.length/2; row++) {
			calcMoves[row][0] = nextsum + offset;
			calcMoves[row][1] = nextsum + offset;
			if ((++nextsum) == 0) 
				offset = 1;
		}
		nextsum = -calcMoves.length/4;
		offset = 0;
		for(col = calcMoves.length/2; col < calcMoves.length;col++) {
			int n = nextsum+offset;
			calcMoves[col][0] = n;
			calcMoves[col][1] = -n;
			if((++nextsum) == 0)
				offset = 1;
		}
	}
	@Override
	public void calculateMoves() {
		super.calculateMoves();
		setAvailableFirstHalf();
		setAvailableSecondHalf();
	}
}
