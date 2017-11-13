package pieces;

public abstract class Piece {
	private char piece;
	private boolean alive;
	protected int[][] possibleMoves;
	public static enum color {white,black,none};
	private color pieceColor;
	
	public Piece(char piece, color pColor) {
		this.piece = piece;
		this.pieceColor = pColor;
		alive = true;
	}
	public char showPiece() {
		return piece;
	}
	public boolean isAlive() {
		return alive;
	}
	public color showColor() {
		return pieceColor;
	}
	public int[][] showPossibleMoves() {
		return possibleMoves;
	}
	public void calculateMoves() {
		
	}
}
