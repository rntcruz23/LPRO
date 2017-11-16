package pieces;

public abstract class Piece {
	private char piece;
	private boolean alive;
	protected int[][] possibleMoves;
	public static enum color {white,black,none};
	private color pieceColor;
	private boolean neverMoved;
	protected int points;
	
	public Piece(char piece, color pColor) {
		this.piece = piece;
		this.pieceColor = pColor;
		alive = true;
		neverMoved = true;
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
	public char showName() {
		return piece;
	}
	public boolean showNeverMoved() {
		return neverMoved;
	}
	public void movePiece() {
		neverMoved = false;
	}
	public int getPoints() {
		return points;
	}
}
