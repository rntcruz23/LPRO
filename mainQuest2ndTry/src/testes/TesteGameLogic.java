package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import board.Cell;
import board.Board;

import pieces.Piece;
import pieces.Pawn;
import pieces.Bishop;
import pieces.Rook;

public class TesteGameLogic {

	private Board board;

	@Before
	public void emptyBoard() {
		board = new Board('t');
	}
	
	@Test
	public void emptyCell() {
		assertEquals(board.cells[0].isEmpty(), true);
		Piece piece = new Pawn(Piece.color.white);
		board.cells[0].moveInPiece(piece);
		assertEquals(board.cells[0].isEmpty(), false);
		board.cells[0].moveOutPiece();
		assertEquals(board.cells[0].isEmpty(), true);
	}
	
	@Test
	public void movePieceWithoutCheck() {
		Piece piece = new Pawn(Piece.color.white);
		board.cells[0].moveInPiece(piece);
		int[] finalPos = new int[] {0,1};
		board.move(board.cells[0].showPosition(), finalPos);
		assertEquals(board.cells[0].isEmpty(), true);
		assertEquals(board.cells[1].showPieceName(), 'P');
		assertEquals(board.cells[1].isEmpty(), false);
	}
	
	@Test
	public void checkMovementPawn() {
		Piece piece = new Pawn(Piece.color.white);
		board.cells[8].moveInPiece(piece);
		int[] finalPos = new int[] {1,1};
		//System.out.println(board.checkMoves(board.cells[8], finalPos));
		assertEquals(board.checkMoves(board.cells[8], finalPos), 1); //valid movement
		finalPos[1]--;
		assertEquals(board.checkMoves(board.cells[8], finalPos), 0); //invalid movement - not on movement list
		finalPos[0] = 0;
		finalPos[1] = 2;
		assertEquals(board.checkMoves(board.cells[8], finalPos), 0);
	}
	
	@Test
	public void checkMovementBishop() {
		Piece piece = new Bishop(Piece.color.white);
		board.cells[4 * 8 + 1].moveInPiece(piece);
		int[] finalPos = new int[] {1,4};
		assertEquals(board.checkMoves(board.cells[4 * 8 + 1], finalPos), 1); //valid movement
		finalPos[0] = 3;
		finalPos[1] = 0;
		assertEquals(board.checkMoves(board.cells[4 * 8 + 1], finalPos), 1); //valid movement
		finalPos[0] = 5;
		finalPos[1] = 0;
		assertEquals(board.checkMoves(board.cells[4 * 8 + 1], finalPos), 1); //valid movement
		finalPos[0] = 7;
		finalPos[1] = 4;
		assertEquals(board.checkMoves(board.cells[4 * 8 + 1], finalPos), 1); //valid movement
		finalPos[0] = 1;
		finalPos[1] = 1;
		assertEquals(board.checkMoves(board.cells[4 * 8 + 1], finalPos), 0); //invalid movement - not on movement list
		finalPos[0] = 2;
		finalPos[1] = 0;
		assertEquals(board.checkMoves(board.cells[4 * 8 + 1], finalPos), 0); //invalid movement - not on movement list
		finalPos[0] = 7;
		finalPos[1] = 0;
		assertEquals(board.checkMoves(board.cells[4 * 8 + 1], finalPos), 0); //invalid movement - not on movement list
		finalPos[0] = 6;
		finalPos[1] = 2;
		assertEquals(board.checkMoves(board.cells[4 * 8 + 1], finalPos), 0); //invalid movement - not on movement list
		Piece piece2 = new Pawn(Piece.color.white);
		board.cells[6 * 8 + 3].moveInPiece(piece2);
		finalPos[0] = 6;
		finalPos[1] = 3;
		assertEquals(board.checkMoves(board.cells[4 * 8 + 1], finalPos), 2); //invalid movement - piece of same color on final position
		board.cells[6 * 8 + 3].moveOutPiece();
		Piece piece3 = new Pawn(Piece.color.black);
		board.cells[6 * 8 + 3].moveInPiece(piece3);
		finalPos[0] = 6;
		finalPos[1] = 3;
		assertEquals(board.checkMoves(board.cells[4 * 8 + 1], finalPos), 4); //valid movement - piece of opposite color on final position
		finalPos[0] = 0;
		finalPos[1] = 5;
		board.cells[1 * 8 + 4].moveInPiece(piece3);
		assertEquals(board.checkMoves(board.cells[4 * 8 + 1], finalPos), 3); //invalid movement - another piece on the way
	}
	
	@Test 
	public void checkMovementRook() {
		Piece piece = new Rook(Piece.color.white);
		board.cells[4 * 8 + 3].moveInPiece(piece);
		int[] finalPos = new int[] {4,6};
		assertEquals(board.checkMoves(board.cells[4 * 8 + 3], finalPos), 1); //valid movement
		finalPos[0] = 2;
		finalPos[1] = 3;
		assertEquals(board.checkMoves(board.cells[4 * 8 + 3], finalPos), 1); //valid movement
		finalPos[0] = 4;
		finalPos[1] = 2;
		assertEquals(board.checkMoves(board.cells[4 * 8 + 3], finalPos), 1); //valid movement
		finalPos[0] = 7;
		finalPos[1] = 3;
		assertEquals(board.checkMoves(board.cells[4 * 8 + 3], finalPos), 1); //valid movement
		Piece piece1 = new Pawn(Piece.color.black);
		board.cells[4 * 8 + 2].moveInPiece(piece1);
		finalPos[0] = 4;
		finalPos[1] = 2;
		assertEquals(board.checkMoves(board.cells[4 * 8 + 3], finalPos), 4); //valid movement - piece of opposite color on final position
		board.cells[4 * 8 + 2].moveOutPiece();
		Piece piece2 = new Pawn(Piece.color.black);
		board.cells[6 * 8 + 3].moveInPiece(piece2);
		finalPos[0] = 7;
		finalPos[1] = 3;
		//assertEquals(board.checkMoves(board.cells[4 * 8 + 3], finalPos), 3); //invalid movement - another piece on the way
		board.cells[6 * 8 + 3].moveOutPiece();
		Piece piece3 = new Pawn(Piece.color.black);
		board.cells[4 * 8 + 5].moveInPiece(piece3);
		finalPos[0] = 4;
		finalPos[1] = 6;
		assertEquals(board.checkMoves(board.cells[4 * 8 + 3], finalPos), 3); //invalid movement - another piece on the way
		board.cells[6 * 8 + 3].moveOutPiece();
	}
}
