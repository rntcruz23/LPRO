package pieces;

public class Pawn extends Piece implements Move{
	private color c;
	public Pawn(color pColor) {
		super('P', pColor);
		c = pColor;
		calculateMoves();
		points = 1;
	}
	
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
	
	@Override
	public void movePiece() {
		neverMoved = false;
		calculateMoves();
	}

}
