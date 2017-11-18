package pieces;

public class Knight extends Piece implements Move{
	public Knight(color pColor) {
		super('N', pColor);
		calculateMoves();
		points = 3;
	}
	
	public void calculateMoves() {
		possibleMoves = new int[][] { {-2,1}, {-1,2}, {1,2}, {2,1}, {2,-1}, {1,-2}, {-1,-2}, {-2,-1}};
	}
}
