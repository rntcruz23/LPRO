package api;

import board.Cell;
import pieces.Piece;

public class ColorsAPI {
	public static char colorToString(Piece.color color) {
		if(color == Piece.color.white) return 'W';
		if(color == Piece.color.black) return 'B';
		return 'U';
	}
	public static char colorToStringCell(Cell.ccolor color) {
		if(color == Cell.ccolor.white) return 'W';
		if(color == Cell.ccolor.black) return 'B';
		return 'U';
	}
	public static Piece.color getOp(Piece.color color){
		if(color == Piece.color.white) return Piece.color.black;
		if(color == Piece.color.black) return Piece.color.white;
		return null;
	}
}