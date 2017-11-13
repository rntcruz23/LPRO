package board;

import pieces.Piece;
import pieces.Pawn;
import pieces.Rook;
import pieces.Knight;
import pieces.Bishop;
import pieces.Queen;
import pieces.King;

public class Cell {
	private int[] position;
	private Piece piece;
	private boolean empty;
	
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
	}
}
