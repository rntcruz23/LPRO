package pieces;

import pieces.Piece.color;

public class Knight extends Piece implements Move{
	/**
	 * creates a knight piece
	 * @param pColor				knight color
	 * @see pieces.Piece#Piece(char, color)
	 */
	public Knight(color pColor) {
		super('N', pColor);
		calculateMoves();
		points = 3;
	}
	
	/**
	 * @see pieces.Move#calculateMoves()
	 */
	public void calculateMoves() {
		possibleMoves = new int[][] { {-2,1}, {-1,2}, {1,2}, {2,1}, {2,-1}, {1,-2}, {-1,-2}, {-2,-1}};
	}
}
