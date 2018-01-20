package pieces;

public interface Move {
	/**
	 * calculates the moves that a piece can make
	 * calculates every move possible, by the rules, without considering the board state
	 */
	public abstract void calculateMoves();
	/**
	 * method to be called when a new piece is put into the board
	 */
	public abstract void neverMoved();
}