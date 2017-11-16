package pieces;

import pieces.Piece.color;

public class King extends Piece implements Move{
	public King(color pColor) {
		super('K', pColor);
		calculateMoves();
		points = 999;
	}
	
	public void calculateMoves() {
		possibleMoves = new int[][] { {-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1} };
	}
}
