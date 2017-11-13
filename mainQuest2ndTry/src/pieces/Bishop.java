package pieces;

import pieces.Piece.color;

public class Bishop extends Piece implements Move{
	public Bishop(color pColor) {
		super('B', pColor);
		calculateMoves();
	}
	
	public void calculateMoves() {
		possibleMoves = new int[][] { 	{1,1}, {2,2}, {3,3}, {4,4}, {5,5}, {6,6}, {7,7},
										{-1,1}, {-2,2}, {-3,3}, {-4,4}, {-5,5}, {-6,6}, {-7,7},
										{1,-1}, {2,-2}, {3,-3}, {4,-4}, {5,-5}, {6,-6}, {7,-7},
										{-1,-1}, {-2,-2}, {-3,-3}, {-4,-4}, {-5,-5}, {-6,-6}, {-7,-7}};
		
	}
}
