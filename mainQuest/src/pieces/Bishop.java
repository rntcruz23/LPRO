package pieces;

public class Bishop extends Peca {

	public Bishop(int[] startPos,color piece) {
		super(startPos,piece,'B');
		calcMoves = new int[14*2][2];
		bishopMoves(calcMoves);
	}
	@Override
	public void calculateMoves() {
		super.calculateMoves();
		setAvailableFirstHalf();
		setAvailableSecondHalf();
	}
	public static void bishopMoves(int[][] vec) {
		int row,col,nextsum = -7,offset = 0;
		for(row = 0; row < 14; row++) {
			vec[row][0] = nextsum + offset;
			vec[row][1] = nextsum + offset;
			if ((++nextsum) == 0) 
				offset = 1;
		}
		nextsum = -7;
		offset = 0;
		for(col = 14; col < 28;col++) {
			int n = nextsum+offset;
			vec[col][0] = n;
			vec[col][1] = -n;
			if((++nextsum) == 0)
				offset = 1;
		}
		
	}
}