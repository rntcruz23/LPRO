package board;

import pieces.Piece;
import pieces.Pawn;
import pieces.Rook;
import pieces.Knight;
import pieces.Bishop;
import pieces.Queen;
import pieces.King;

/**
 * Classe da celula/casa do tabuleiro de xadrez
 */
public class Cell {
	private int[] position;
	private Piece piece;
	private boolean empty;
	public static enum ccolor {white, black};
	private ccolor cellColor;
	
	/**
	 * creates a cell with a position, color, and piece (if applicable)
	 * @param posHorizontal				column number (0 to 7)
	 * @param posVertical				row number (0 to 7)
	 * @param pieceName					name of the piece in the cell (' ' for empty cell)
	 * @param pieceColor				color of the piece in the cell. If the cell is empty, it's irrelevant
	 */
	public Cell(int posHorizontal, int posVertical, char pieceName, Piece.color pieceColor) {
		position = new int[] {posHorizontal, posVertical};
		switch(pieceName) {
		case 'P': 
			piece = new Pawn(pieceColor);
			empty = false;
			break;
		case 'R': 
			piece = new Rook(pieceColor);
			empty = false;
			break;
		case 'N': 
			piece = new Knight(pieceColor);
			empty = false;
			break;
		case 'B': 
			piece = new Bishop(pieceColor);
			empty = false;
			break;
		case 'Q': 
			piece = new Queen(pieceColor);
			empty = false;
			break;
		case 'K': 
			piece = new King(pieceColor);
			empty = false;
			break;
		default:
			empty = true;
			break;
		}
		if(((position[0] + position[1]) % 2) == 0) {
			cellColor = ccolor.black;
		}
		else {
			cellColor = ccolor.white;
		}
	}
	/**
	 * @return							<code>true</code> if the cell is empty (no piece inside)
	 */
	public boolean isEmpty() {
		return empty;
	}
	/**
	 * @return							color of the cell (white or black)
	 */
	public Cell.ccolor showColor() {
		return cellColor;
	}
	/**
	 * @return							piece inside the cell (or null, if the cell is empty)
	 */
	public Piece getPiece() {
		if(empty) {
			return null;
		}
		else {
			return piece;
		}
	}
	/**
	 * @return							moves that the piece inside the cell can make (all moves)
	 */
	public int[][] showPiecePossibleMoves() {
		return piece.showPossibleMoves();
	}
	/**
	 * @return							name of the piece inside the cell
	 */
	public char showPieceName() {
		return piece.showName();
	}
	/**
	 * @return							position of the cell
	 */
	public int[] showPosition() {
		return position;
	}
	/**
	 * @return							color of the piece inside the cell (or null, if the cell is empty)
	 */
	public Piece.color showPieceColor() {
		if(empty) {
			return null;
		}
		else {
			return piece.showColor();
		}
	}
	/**
	 * removes the piece from the cell
	 */
	public void moveOutPiece() {
		piece.movePiece();
		empty = true;
		
	}
	/**
	 * puts a piece in the cell
	 * @param piece						piece to move into the cell
	 */
	public void moveInPiece(Piece piece) {
		this.piece = piece;
		empty = false;
	}
}
