package pieces;

import mainQuest.Board;

public class Queen extends Peca {

	public Queen(int[] startPos,color piece) {
		super(startPos,piece,'Q');
		calcMoves = new int[14*4][2];
		int[][] bM = new int[14*2][2];
		Bishop.bishopMoves(bM);
		int[][] rM = new int[14*2][2];
		Rook.rookMoves(rM);
		int i;
		for(i = 0; i < 28;i++) {
			calcMoves[i][0] = bM[i][0];
			calcMoves[i][1] = bM[i][1];
		}
		for(i = 28; i < 56;i++) {
			calcMoves[i][0] = rM[i - 28][0];
			calcMoves[i][1] = rM[i - 28][1];
		}
	}
	@Override
	public void calculateMoves() {
		super.calculateMoves();
		setQueenAvailableFirstHalf();
		setQueenAvailableSecondHalf();
	}
	private void setQueenAvailableFirstHalf() {
		int[][] available = getAvailable();
		int row;
		int stopPoint = 0;
		boolean flag = false;
		char[][] board = Board.getBoard();
		for(row = available.length/8 - 1;row >= 0;row--) {
			int[] next = available[row];
			if(next[0] == -1)continue;
			char nextP = board[next[0]][next[1]];
			if((nextP != ' ') && (nextP != 'X')){
				stopPoint = row;
				flag = true;
				break;				
			}	
		}
		if(flag)
			for(row = stopPoint-1; row >= 0; row--) {
				available[row][0] = -1;
				available[row][1] = -1;
			}
		flag = false;
		for(row = available.length/8 ; row < available.length/4; row++) {
			int[] next = available[row];
			if(next[0] == -1)continue;
			char nextP = board[next[0]][next[1]];
			if((nextP != ' ') && (nextP != 'X')){
				stopPoint = row;
				flag = true;
				break;				
			}
		}
		if(flag)
			for(row = stopPoint+1; row < available.length/4; row++) {
				available[row][0] = -1;
				available[row][1] = -1;
			}
		for(row = available.length*3/8 - 1;row >= available.length/4;row--) {
			int[] next = available[row];
			if(next[0] == -1)continue;
			char nextP = board[next[0]][next[1]];
			if((nextP != ' ') && (nextP != 'X')){
				stopPoint = row;
				flag = true;
				break;				
			}	
		}
		if(flag)
			for(row = stopPoint-1; row >= available.length/4; row--) {
				available[row][0] = -1;
				available[row][1] = -1;
			}
		flag = false;
		for(row = available.length*3/8 ; row < available.length/2; row++) {
			int[] next = available[row];
			if(next[0] == -1)continue;
			char nextP = board[next[0]][next[1]];
			if((nextP != ' ') && (nextP != 'X')){
				stopPoint = row;
				flag = true;
				break;				
			}
		}
		if(flag)
		for(row = stopPoint+1; row < available.length/2; row++) {
			available[row][0] = -1;
			available[row][1] = -1;
		}
		setAvailable(available);
	}
	private void setQueenAvailableSecondHalf() {
		int[][] available = getAvailable();
		int row;
		int stopPoint = 0;
		boolean flag = false;
		char[][] board = Board.getBoard();
		for(row = available.length/2 - 1;row >= available.length*5/8;row--) {
			int[] next = available[row];
			if(next[0] == -1)continue;
			char nextP = board[next[0]][next[1]];
			if((nextP != ' ') && (nextP != 'X')){
				stopPoint = row;
				flag = true;
				break;				
			}	
		}
		if(flag)
			for(row = stopPoint-1; row >= available.length*5/8; row--) {
				available[row][0] = -1;
				available[row][1] = -1;
			}
		flag = false;
		for(row = available.length*5/8 ; row < available.length*6/8; row++) {
			int[] next = available[row];
			if(next[0] == -1)continue;
			char nextP = board[next[0]][next[1]];
			if((nextP != ' ') && (nextP != 'X')){
				stopPoint = row;
				flag = true;
				break;				
			}
		}
		if(flag)
			for(row = stopPoint+1; row < available.length*6/8; row++) {
				available[row][0] = -1;
				available[row][1] = -1;
			}
		for(row = available.length*6/8 - 1;row >= available.length*7/8;row--) {
			int[] next = available[row];
			if(next[0] == -1)continue;
			char nextP = board[next[0]][next[1]];
			if((nextP != ' ') && (nextP != 'X')){
				stopPoint = row;
				flag = true;
				break;				
			}	
		}
		if(flag)
			for(row = stopPoint-1; row >= available.length*7/8; row--) {
				available[row][0] = -1;
				available[row][1] = -1;
			}
		flag = false;
		for(row = available.length*7/8 ; row < available.length; row++) {
			int[] next = available[row];
			if(next[0] == -1)continue;
			char nextP = board[next[0]][next[1]];
			if((nextP != ' ') && (nextP != 'X')){
				stopPoint = row;
				flag = true;
				break;				
			}
		}
		if(flag)
		for(row = stopPoint+1; row < available.length/2; row++) {
			available[row][0] = -1;
			available[row][1] = -1;
		}
		setAvailable(available);
	}
}
