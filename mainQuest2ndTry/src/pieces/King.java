package pieces;

public class King extends Piece implements Move{
	public King(color pColor) {
		super('K', pColor);
		calculateMoves();
		points = 999;
	}
	
	public void calculateMoves() {
		if(neverMoved) {
			possibleMoves = new int[][] { {-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}, {2,0}, {-2,0} };
		}
		else {
			possibleMoves = new int[][] { {-1,0}, {-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1} };
		}
	}
	
	@Override
	public void movePiece() {
		neverMoved = false;
		calculateMoves();
	}
}
