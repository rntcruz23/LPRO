package pieces;

public class Pawn extends Piece implements Move{
	public Pawn(color pColor) {
		super('P', pColor);
		calculateMoves();
		points = 1;
	}
	
	public void calculateMoves() {
		possibleMoves = new int[][] {{0,1}, {1,1}, {-1,1}};
	}
}
