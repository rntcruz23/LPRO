package pieces;

import pieces.Piece.color;

public class Queen extends Piece implements Move{
	/**
	 * creates a queen piece
	 * @param pColor			queen color
	 * @see pieces.Piece#Piece(char, color)
	 */
	public Queen(color pColor) {
		super('Q', pColor);
		calculateMoves();
		points = 9;
	}
	
	/**
	 * @see pieces.Move#calculateMoves()
	 */
	public void calculateMoves() {
		possibleMoves = new int[][] {	{0,1}, {0,2}, {0,3}, {0,4}, {0,5}, {0,6}, {0,7},
										{0,-1}, {0,-2}, {0,-3}, {0,-4}, {0,-5}, {0,-6}, {0,-7},
										{1,0}, {2,0}, {3,0}, {4,0}, {5,0}, {6,0}, {7,0},
										{-1,0}, {-2,0}, {-3,0}, {-4,0}, {-5,0}, {-6,0}, {-7,0}, //rook like moves
										{1,1}, {2,2}, {3,3}, {4,4}, {5,5}, {6,6}, {7,7},
										{-1,1}, {-2,2}, {-3,3}, {-4,4}, {-5,5}, {-6,6}, {-7,7},
										{1,-1}, {2,-2}, {3,-3}, {4,-4}, {5,-5}, {6,-6}, {7,-7}, //bishop like moves
										{-1,-1}, {-2,-2}, {-3,-3}, {-4,-4}, {-5,-5}, {-6,-6}, {-7,-7}};
	}
}
