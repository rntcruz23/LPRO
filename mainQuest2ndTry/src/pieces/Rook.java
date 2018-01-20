package pieces;

import pieces.Piece.color;

public class Rook extends Piece implements Move{
	/**
	 * creates a rook piece
	 * @param pColor				rook color
	 * @see pieces.Piece#Piece(char, color)
	 */
	public Rook(color pColor) {
		super('R', pColor);
		calculateMoves();
		points = 5;
	}
	
	/**
	 * @see pieces.Move#calculateMoves()
	 */
	public void calculateMoves() {
		possibleMoves = new int[][] {	{0,1}, {0,2}, {0,3}, {0,4}, {0,5}, {0,6}, {0,7},
										{0,-1}, {0,-2}, {0,-3}, {0,-4}, {0,-5}, {0,-6}, {0,-7},
										{1,0}, {2,0}, {3,0}, {4,0}, {5,0}, {6,0}, {7,0},
										{-1,0}, {-2,0}, {-3,0}, {-4,0}, {-5,0}, {-6,0}, {-7,0}};
	}
}