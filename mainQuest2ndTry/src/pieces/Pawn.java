package pieces;

import pieces.Piece.color;

public class Pawn extends Piece implements Move{
	private color c;
	/**
	 * creates a pawn piece
	 * @param pColor				color of the pawn
	 * @see pieces.Piece#Piece(char, color)
	 */
	public Pawn(color pColor) {
		super('P', pColor);
		c = pColor;
		calculateMoves();
		points = 1;
	}
	
	/**
	 * @see pieces.Move#calculateMoves()
	 */
	@Override
	public void calculateMoves() {
		if(c == color.white) {
			if(neverMoved) {
				possibleMoves = new int[][] {{0,1}, {1,1}, {-1,1}, {0,2}};
			}
			else {
				possibleMoves = new int[][] {{0,1}, {1,1}, {-1,1}};
			}
		}
		else {
			if(neverMoved) {
				possibleMoves = new int[][] {{0,-1}, {1,-1}, {-1,-1}, {0,-2}};
			}
			else {
				possibleMoves = new int[][] {{0,-1}, {1,-1}, {-1,-1}};
			}
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
