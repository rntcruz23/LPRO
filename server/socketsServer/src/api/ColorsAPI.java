package api;

import board.Cell;
import pieces.Piece;

public class ColorsAPI {
	/**
	 * transforms the color enum into character
	 * @param color				color enum
	 * @return					character representing the color [W]hite|[B]lack
	 */
	public static char colorToString(Piece.color color) {
		if(color == Piece.color.white) return 'W';
		if(color == Piece.color.black) return 'B';
		return 'U';
	}
	/**
	 * transforms the color enum into character
	 * @param color				color enum
	 * @return					character representing the color [W]hite|[B]lack
	 */
	public static char colorToStringCell(Cell.ccolor color) {
		if(color == Cell.ccolor.white) return 'W';
		if(color == Cell.ccolor.black) return 'B';
		return 'U';
	}
	/**
	 * Get opponent of a color
	 * @param color				color enum
	 * @return					opposite color (enum)
	 */
	public static Piece.color getOp(Piece.color color){
		if(color == Piece.color.white) return Piece.color.black;
		if(color == Piece.color.black) return Piece.color.white;
		return null;
	}
}