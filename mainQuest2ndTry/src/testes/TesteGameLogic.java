package testes;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import board.Board;

import pieces.Piece;
import pieces.Queen;
import pieces.Pawn;
import pieces.Bishop;
import pieces.King;
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
		board.move(board.cells[0].showPosition(), finalPos, Piece.color.white);
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
		assertEquals(board.checkMoves(board.cells[8], finalPos), 0); //invalid movement - diagonal movement with no piece to capture
		finalPos[0] = 0;
		finalPos[1] = 1;
		Piece piece1 = new Pawn(Piece.color.black);
		board.cells[0 * 8 + 1].moveInPiece(piece1);
		assertEquals(board.checkMoves(board.cells[8], finalPos), 4); //valid movement - diagonal movement to capture piece
		board.cells[0 * 8 + 1].moveOutPiece();
		Piece piece2 = new Pawn(Piece.color.white);
		board.cells[0 * 8 + 1].moveInPiece(piece2);
		assertEquals(board.checkMoves(board.cells[8], finalPos), 2); //valid movement - diagonal movement to capture piece
		board.cells[0 * 8 + 1].moveOutPiece();
		Piece piece3 = new Queen(Piece.color.black);
		board.cells[1 * 8 + 2].moveInPiece(piece3);
		finalPos[0] = 1;
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
		assertEquals(board.checkMoves(board.cells[4 * 8 + 3], finalPos), 3); //invalid movement - another piece on the way
		board.cells[6 * 8 + 3].moveOutPiece();
		Piece piece3 = new Pawn(Piece.color.black);
		board.cells[4 * 8 + 5].moveInPiece(piece3);
		finalPos[0] = 4;
		finalPos[1] = 6;
		assertEquals(board.checkMoves(board.cells[4 * 8 + 3], finalPos), 3); //invalid movement - another piece on the way
		board.cells[6 * 8 + 3].moveOutPiece();
	}
	
	@Test
	public void checkKingCheck() {
		Piece king = new King(Piece.color.white);
		board.cells[0].moveInPiece(king);
		Piece piece = new Rook(Piece.color.black);
		board.cells[0 * 0 + 5].moveInPiece(piece);
		assertTrue(board.checkCheck(Piece.color.black));
		Piece piece1 = new Rook(Piece.color.white);
		board.cells[5].moveOutPiece();
		board.cells[5].moveInPiece(piece1);
		assertFalse(board.checkCheck(Piece.color.black));
		board.cells[0].moveOutPiece();
		board.cells[5].moveOutPiece();
		Piece king1 = new King(Piece.color.black);
		board.cells[0].moveInPiece(king1);
		board.cells[5].moveInPiece(piece1);
		assertTrue(board.checkCheck(Piece.color.white));
		assertTrue(board.checkCheck(board.cells[0]));
	}

	@Test
	public void checkCastling() {
		Piece wking = new King(Piece.color.white);
		Piece wkrook = new Rook(Piece.color.white);
		Piece wqrook = new Rook(Piece.color.white);
		board.cells[0].moveInPiece(wqrook);
		board.cells[4 * 8 + 0].moveInPiece(wking);
		board.cells[7 * 8 + 0].moveInPiece(wkrook);
		int[] fin = {6, 0};
		assertEquals(board.checkMoves(board.cells[4 * 8 + 0], fin), 5);
		fin[0] = 2;
		assertEquals(board.checkMoves(board.cells[4 * 8 + 0], fin), 6);
	}
	
	@Test
	public void checkMoveCheck() {
		Piece wking = new King(Piece.color.white);
		Piece wqueen = new Queen(Piece.color.white);
		Piece bqueen = new Queen(Piece.color.black);
		board.cells[4 * 8 + 1].moveInPiece(wking);
		board.cells[0 * 8 + 7].moveInPiece(bqueen);
		board.cells[7 * 8 + 2].moveInPiece(wqueen);
		board.printBoard(Piece.color.white);
		int[] init = {0,7};
		int[] fin = {4,7};
		assertTrue(board.move(init, fin, Piece.color.black));		
		board.printBoard(Piece.color.white);
		assertTrue(board.checkCheck(Piece.color.black));
		init[0] = 7;
		init[1] = 2;
		fin[0] = 6;
		fin[1] = 2;
		assertFalse(board.move(init, fin, Piece.color.white));
		board.printBoard(Piece.color.white);
	}
	
	@Test
	public void checkCheckMate() {
		Piece wking = new King(Piece.color.white);
		Piece bqueen = new Queen(Piece.color.black);
		board.cells[0 * 8 + 0].moveInPiece(wking);
		board.cells[0 * 8 + 7].moveInPiece(bqueen);
		assertTrue(board.checkCheck(Piece.color.black));
		assertFalse(board.checkCheckMate(Piece.color.black)); 			//teste checkMate sem mais peças da mesma cor
		Piece wrook = new Rook(Piece.color.white);
		Piece wrook2 = new Rook(Piece.color.white);
		board.cells[1 * 8 + 0].moveInPiece(wrook);
		board.cells[6 * 8 + 1].moveInPiece(wrook2);
		assertTrue(board.checkCheck(Piece.color.black));
		assertFalse(board.checkCheckMate(Piece.color.black)); 			//teste checkMate com mais peças da mesma cor
		Piece wpawn = new Pawn(Piece.color.white);
		board.cells[1 * 8 + 1].moveInPiece(wpawn);
		board.printBoard(Piece.color.white);
		assertTrue(board.checkCheck(Piece.color.black));
		assertTrue(board.checkCheckMate(Piece.color.black)); 			//teste checkMate true
		board.printBoard(Piece.color.white);
		
	}
}
