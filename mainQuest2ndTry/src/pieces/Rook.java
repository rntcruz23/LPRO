package pieces;

import pieces.Piece.color;

public class Rook extends Piece implements Move{
	public Rook(color pColor) {
		super('R', pColor);
		calculateMoves();
		points = 5;
	}
	
	public void calculateMoves() {
		possibleMoves = new int[][] {	{0,1}, {0,2}, {0,3}, {0,4}, {0,5}, {0,6}, {0,7},
										{0,-1}, {0,-2}, {0,-3}, {0,-4}, {0,-5}, {0,-6}, {0,-7},
										{1,0}, {2,0}, {3,0}, {4,0}, {5,0}, {6,0}, {7,0},
										{-1,0}, {-2,0}, {-3,0}, {-4,0}, {-5,0}, {-6,0}, {-7,0}};
	}
}