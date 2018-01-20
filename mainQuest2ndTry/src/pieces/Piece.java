package pieces;

public abstract class Piece implements Move{
	private char piece;
	private boolean alive;
	protected int[][] possibleMoves;
	public static enum color {white,black,none};
	private color pieceColor;
	protected boolean neverMoved;
	protected int points;
	
	/**
	 * constructor for the piece
	 * @param piece					name of the piece
	 * @param pColor				color of the piece
	 */
	public Piece(char piece, color pColor) {
		this.piece = piece;
		this.pieceColor = pColor;
		alive = true;
		neverMoved = true;
	}
	/**
	 * @see pieces.Move#neverMoved()
	 */
	public void neverMoved() {
		this.neverMoved = true;
	}
	/**
	 * @return						name of the piece
	 */
	public char showPiece() {
		return piece;
	}
	/**
	 * @return						<code>true</code> if the piece is alive (not captured)
	 */
	public boolean isAlive() {
		return alive;
	}
	/**
	 * @return						color of the piece
	 */
	public color showColor() {
		return pieceColor;
	}
	/**
	 * @return						moves that the piece can make (all moves, without considering the board state)
	 */
	public int[][] showPossibleMoves() {
		return possibleMoves;
	}
	/**
	 * @return						name of the piece
	 */
	public char showName() {
		return piece;
	}
	/**		
	 * @return						<code>true</code> if the piece was never moved
	 */
	public boolean showNeverMoved() {
		return neverMoved;
	}
	/**
	 * flag neverMoved to false, has to be called when moving a piece, at least for the first time
	 */
	public void movePiece() {
		neverMoved = false;
	}
	/**
	 * @return						points that the piece is worth
	 */
	public int getPoints() {
		return points;
	}
}
