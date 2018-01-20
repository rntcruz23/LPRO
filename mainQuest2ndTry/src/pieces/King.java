package pieces;

import pieces.Piece.color;

public class King extends Piece implements Move{
	/**
	 * creates a king piece
	 * @param pColor				king color
	 * @see pieces.Piece#Piece(char, color)
	 */
	public King(color pColor) {
		super('K', pColor);
		calculateMoves();
		points = 999;
	}
	
	/**
	 * @see pieces.Move#calculateMoves()
	 */
	public void calculateMoves() {
		if(neverMoved) {
			possibleMoves = new int[][] { {-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}, {2,0}, {-2,0} };
		}
		else {
			possibleMoves = new int[][] { {-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1} };
		}
	}
	
	/**
	 * @see pieces.Piece#movePiece()
	 */
	@Override
	public void movePiece() {
		neverMoved = false;
		calculateMoves();
	}
}
