package board;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

import pieces.Bishop;
import pieces.Knight;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;
import api.ColorsAPI;

public class Board {
	public Cell[] cells = new Cell[64];
	private LinkedList<Piece> capturedPiecesByWhite = new LinkedList<Piece>();
	private LinkedList<Piece> capturedPiecesByBlack = new LinkedList<Piece>();
	private Stack<int[]> lastMovesInit = new Stack();
	private Stack<int[]> lastMovesFin = new Stack();
	private int whitePoints = 0;
	private int blackPoints = 0;
	private boolean flagCheckWhite = false;
	private boolean flagCheckBlack = false;
	private int[] lastMovePos = {0,0};
	private int[] lastMoveInit = {0,0};
	private boolean capturedPieceLastMove = false;
	private Piece.color lastPlayer;
	private boolean lastMoveFirstOfPiece = false;
	
	/**
	 * Board constructor
	 * creates a Board and puts all the pieces in the respective cells
	 * the board is ready to start the game
	 */
	public Board() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(j == 0) { //white back row
					if(i == 0 || i == 7) {
						cells[i * 8 + j] = new Cell(i, j, 'R', Piece.color.white);
					}
					else if(i == 1 || i == 6) {
						cells[i * 8 + j] = new Cell(i, j, 'N', Piece.color.white);
					}
					else if(i == 2 || i == 5) {
						cells[i * 8 + j] = new Cell(i, j, 'B', Piece.color.white);
					}
					else if( i == 3) {
						cells[i * 8 + j] = new Cell(i, j, 'Q', Piece.color.white);
					}
					else if(i == 4) {
						cells[i * 8 + j] = new Cell(i, j, 'K', Piece.color.white);
					}
				}
				else if(j == 1) { //white pawn row
					cells[i * 8 + j] = new Cell(i, j, 'P', Piece.color.white);
				}
				else if(j > 1 && j < 6) {
					cells[i * 8 + j] = new Cell(i, j, ' ', Piece.color.none);
				}
				else if(j == 6) {
					cells[i * 8 + j] = new Cell(i, j, 'P', Piece.color.black);
				}
				else if(j == 7) { //black back row
					if(i == 0 || i == 7) {
						cells[i * 8 + j] = new Cell(i, j, 'R', Piece.color.black);
					}
					else if(i == 1 || i == 6) {
						cells[i * 8 + j] = new Cell(i, j, 'N', Piece.color.black);
					}
					else if(i == 2 || i == 5) {
						cells[i * 8 + j] = new Cell(i, j, 'B', Piece.color.black);
					}
					else if( i == 3) {
						cells[i * 8 + j] = new Cell(i, j, 'Q', Piece.color.black);
					}
					else if(i == 4) {
						cells[i * 8 + j] = new Cell(i, j, 'K', Piece.color.black);
					}
				}
			}
		}
		
	}
	/**
	 * Board constructor
	 * creates an empty board, used in testing
	 * @param teste				any char, not used
	 */
	public Board(char teste) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				cells[i * 8 + j] = new Cell(i, j, ' ', Piece.color.none);
			}
		}
	}
	/**
	 * method move
	 * interaction with the outside
	 * moves the piece in initialPos to finalPos, if valid
	 * @param initialPos 		vetor int[] of the initial position, [0] column, [1] line
	 * @param finalPos 			vetor int[] of the final position, [0] column, [1] line
	 * @param side 				side (Piece.color.white ou Piece.color.black) that is playign
	 * @return 					<code>true</code> the piece was moved
	 * @return 					<code>false</code> the piece was not moved
	 */
	public boolean move(int[] initialPos, int[] finalPos, Piece.color side) {
		Piece piece = cells[initialPos[0] * 8 + initialPos[1]].getPiece();
		Piece aux;
		if(piece == null) return false;
		if(piece.showColor() != side) return false;
		boolean flagCheck = checkCheck(side);
		System.out.println(flagCheck);
		int check = checkMoves(cells[initialPos[0] * 8 + initialPos[1]], finalPos);
		System.out.print("CheckMovement: ");
		System.out.println(check);
		System.out.print("lastMoveInit ");
		System.out.print(lastMoveInit[0]);
		System.out.println(lastMoveInit[1]);
		System.out.print("lastMovePos ");
		System.out.print(lastMovePos[0]);
		System.out.println(lastMovePos[1]);
		System.out.print("initialPos ");
		System.out.print(initialPos[0]);
		System.out.println(initialPos[1]);
		System.out.print("finalPos ");
		System.out.print(finalPos[0]);
		System.out.println(finalPos[1]);
		switch(check) {
		case 0:
			return false;
		case 1:
			if(piece.showName() == 'P' && (finalPos[1] == 7 || finalPos[1] == 0)) { //pawn promotion
				switch(getPromotion()) {
				case 'Q':
					aux = new Queen(piece.showColor());
					break;
				case 'R':
					aux = new Rook(piece.showColor());
					break;
				case 'N':
					aux = new Knight(piece.showColor());
					break;
				case 'B':
					aux = new Bishop(piece.showColor());
					break;
				default:
					return false;
				}
				cells[initialPos[0] * 8 + initialPos[1]].moveOutPiece();
				cells[finalPos[0] * 8 + finalPos[1]].moveInPiece(aux);
				aux.movePiece();
			}
			lastMoveFirstOfPiece = piece.showNeverMoved();
			cells[initialPos[0] * 8 + initialPos[1]].moveOutPiece();
			cells[finalPos[0] * 8 + finalPos[1]].moveInPiece(piece);
			if(side == Piece.color.white && checkCheck(Piece.color.black)) {
				cells[initialPos[0] * 8 + initialPos[1]].moveInPiece(piece);
				cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
				if(lastMoveFirstOfPiece == true) piece.neverMoved();
				return false;
			}
			else if(side == Piece.color.black && checkCheck(Piece.color.white)) {
				cells[initialPos[0] * 8 + initialPos[1]].moveInPiece(piece);
				cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
				if(lastMoveFirstOfPiece == true) piece.neverMoved();
				return false;
			}
			storeLastMoves(initialPos, finalPos);
			flagCheck = checkCheck(side);
			System.out.println(flagCheck);
			capturedPieceLastMove = false;
			lastPlayer = side;
			return true;
		case 2: 
			return false;
		case 3: 
			return false;
		case 4: 
			cells[initialPos[0] * 8 + initialPos[1]].moveOutPiece();
			Piece capturedPiece = cells[finalPos[0] * 8 + finalPos[1]].getPiece();
			Piece aux1;
			lastMoveFirstOfPiece = piece.showNeverMoved();
			if(piece.showName() == 'P' && (finalPos[1] == 7 || finalPos[1] == 0)) { //pawn promotion
				switch(getPromotion()) {
				case 'Q':
					aux1 = new Queen(piece.showColor());
					break;
				case 'R':
					aux1 = new Rook(piece.showColor());
					break;
				case 'N':
					aux1 = new Knight(piece.showColor());
					break;
				case 'B':
					aux1 = new Bishop(piece.showColor());
					break;
				default:
						return false;
				}
				cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
				cells[finalPos[0] * 8 + finalPos[1]].moveInPiece(aux1);
				aux1.movePiece();
			}
			else {
				cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
				cells[finalPos[0] * 8 + finalPos[1]].moveInPiece(piece);
			}
			if(side == Piece.color.white && checkCheck(Piece.color.black)) {
				cells[initialPos[0] * 8 + initialPos[1]].moveInPiece(piece);
				cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
				cells[finalPos[0] * 8 + finalPos[1]].moveInPiece(capturedPiece);
				if(lastMoveFirstOfPiece == true) piece.neverMoved();
				return false;
			}
			else if(side == Piece.color.black && checkCheck(Piece.color.white)) {
				cells[initialPos[0] * 8 + initialPos[1]].moveInPiece(piece);
				cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
				cells[finalPos[0] * 8 + finalPos[1]].moveInPiece(capturedPiece);
				if(lastMoveFirstOfPiece == true) piece.neverMoved();
				return false;
			}
			if(cells[finalPos[0] * 8 + finalPos[1]].showPieceColor() == Piece.color.white) {
				whitePoints += capturedPiece.getPoints();
				capturedPiecesByWhite.add(capturedPiece);
			}
			else {
				blackPoints += capturedPiece.getPoints();
				capturedPiecesByBlack.add(capturedPiece);
			}
			storeLastMoves(initialPos, finalPos);
			flagCheck = checkCheck(side);
			System.out.println(flagCheck);
			capturedPieceLastMove = true;
			lastPlayer = side;
			return true;
		case 5:
			int i = 0;
			lastMoveFirstOfPiece = piece.showNeverMoved();
			if(cells[initialPos[0] * 8 + initialPos[1]].showPieceColor() == Piece.color.white) i = 0;
			else if(cells[initialPos[0] * 8 + initialPos[1]].showPieceColor() == Piece.color.black) i = 7;
			Piece auxking = cells[initialPos[0] * 8 + initialPos[1]].getPiece();
			cells[initialPos[0] * 8 + initialPos[1]].moveOutPiece();
			cells[finalPos[0] * 8 + finalPos[1]].moveInPiece(auxking);
			Piece auxrook = cells[7 * 8 + i].getPiece();
			cells[7 * 8 + i].moveOutPiece();
			cells[5 * 8 + i].moveInPiece(auxrook);
			storeLastMoves(initialPos, finalPos);
			capturedPieceLastMove = false;
			lastPlayer = side;
			return true;
		case 6:
			int j = 0;
			lastMoveFirstOfPiece = piece.showNeverMoved();
			if(cells[initialPos[0] * 8 + initialPos[1]].showPieceColor() == Piece.color.white) j = 0;
			else if(cells[initialPos[0] * 8 + initialPos[1]].showPieceColor() == Piece.color.black) j = 7;
			Piece auxking1 = cells[initialPos[0] * 8 + initialPos[1]].getPiece();
			cells[initialPos[0] * 8 + initialPos[1]].moveOutPiece();
			cells[finalPos[0] * 8 + finalPos[1]].moveInPiece(auxking1);
			Piece auxrook1 = cells[0 * 8 + j].getPiece();
			cells[0 * 8 + j].moveOutPiece();
			cells[3 * 8 + j].moveInPiece(auxrook1);
			storeLastMoves(initialPos, finalPos);
			capturedPieceLastMove = false;
			lastPlayer = side;
			return true;
		case 7:
			Piece capturedPiece7;
			lastMoveFirstOfPiece = piece.showNeverMoved();
			if(cells[initialPos[0] * 8 + initialPos[1]].showPieceColor() == Piece.color.white) {
				System.out.print(initialPos[0]);
				System.out.println(initialPos[1]);
				System.out.print(finalPos[0]);
				System.out.println(finalPos[1]);
				System.out.print(finalPos[0]);
				System.out.println(finalPos[1] - 1);
				capturedPiece7 = cells[finalPos[0] * 8 + finalPos[1] - 1].getPiece();
				cells[initialPos[0] * 8 + initialPos[1]].moveOutPiece();
				cells[finalPos[0] * 8 + finalPos[1] - 1].moveOutPiece();
				cells[finalPos[0] * 8 + finalPos[1]].moveInPiece(piece);
				if(side == Piece.color.white && checkCheck(Piece.color.black)) {
					cells[initialPos[0] * 8 + initialPos[1]].moveInPiece(piece);
					cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
					cells[finalPos[0] * 8 + finalPos[1] - 1].moveInPiece(capturedPiece7);
					return false;
				}
				else if(side == Piece.color.black && checkCheck(Piece.color.white)) {
					cells[initialPos[0] * 8 + initialPos[1]].moveInPiece(piece);
					cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
					cells[finalPos[0] * 8 + finalPos[1] - 1].moveInPiece(capturedPiece7);
					return false;
				}
				whitePoints += capturedPiece7.getPoints();
				capturedPiecesByWhite.add(capturedPiece7);
				storeLastMoves(initialPos, finalPos);
			}
			else if(cells[initialPos[0] * 8 + initialPos[1]].showPieceColor() == Piece.color.black) {
				System.out.print(initialPos[0]);
				System.out.println(initialPos[1]);
				System.out.print(finalPos[0]);
				System.out.println(finalPos[1]);
				System.out.print(finalPos[0]);
				System.out.println(finalPos[1] - 1);
				capturedPiece7 = cells[finalPos[0] * 8 + finalPos[1] + 1].getPiece();
				cells[initialPos[0] * 8 + initialPos[1]].moveOutPiece();
				cells[finalPos[0] * 8 + finalPos[1] + 1].moveOutPiece();
				cells[finalPos[0] * 8 + finalPos[1]].moveInPiece(piece);
				if(side == Piece.color.white && checkCheck(Piece.color.black)) {
					cells[initialPos[0] * 8 + initialPos[1]].moveInPiece(piece);
					cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
					cells[finalPos[0] * 8 + finalPos[1] + 1].moveInPiece(capturedPiece7);
					return false;
				}
				else if(side == Piece.color.black && checkCheck(Piece.color.white)) {
					cells[initialPos[0] * 8 + initialPos[1]].moveInPiece(piece);
					cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
					cells[finalPos[0] * 8 + finalPos[1] + 1].moveInPiece(capturedPiece7);
					return false;
				}
				blackPoints += capturedPiece7.getPoints();
				capturedPiecesByBlack.add(capturedPiece7);
				storeLastMoves(initialPos, finalPos);
			}
			capturedPieceLastMove = true;
			lastPlayer = side;
			return true;
		case -1:
			return false;
		case -2:
			return false;
		default:
			return false;
			
		}
	}
	/**
	 * undoes the previous move
	 * only one move can be undone, before another play needs to be made
	 * if a piece was captured in the previous move, the piece is returned to its cell
	 */
	public void undoMove() {
		Piece aux = cells[lastMovePos[0] * 8 + lastMovePos[1]].getPiece();
		if(capturedPieceLastMove) {
			if(lastPlayer == Piece.color.white) {
				Piece auxCap = capturedPiecesByWhite.removeLast();
				cells[lastMovePos[0] * 8 + lastMovePos[1]].moveOutPiece();
				cells[lastMoveInit[0] * 8 + lastMoveInit[1]].moveInPiece(aux);
				cells[lastMovePos[0] * 8 + lastMovePos[1]].moveInPiece(auxCap);
				if(aux.showName() == 'P') {
					if(lastMovePos[1] - lastMoveInit[1] == 2 || lastMovePos[1] - lastMoveInit[1] == -2) {
						aux.neverMoved();
					}
				}
				whitePoints -= auxCap.getPoints();
			}
			else {
				Piece auxCap = capturedPiecesByBlack.removeLast();
				cells[lastMovePos[0] * 8 + lastMovePos[1]].moveOutPiece();
				cells[lastMoveInit[0] * 8 + lastMoveInit[1]].moveInPiece(aux);
				cells[lastMovePos[0] * 8 + lastMovePos[1]].moveInPiece(auxCap);
				if(aux.showName() == 'P') {
					if(lastMovePos[1] - lastMoveInit[1] == 2 || lastMovePos[1] - lastMoveInit[1] == -2) {
						aux.neverMoved();
					}
				}
				blackPoints -= auxCap.getPoints();
			}
		}
		else {
			cells[lastMovePos[0] * 8 + lastMovePos[1]].moveOutPiece();
			cells[lastMoveInit[0] * 8 + lastMoveInit[1]].moveInPiece(aux);
			if(aux.showName() == 'P') {
				if(lastMovePos[1] - lastMoveInit[1] == 2 || lastMovePos[1] - lastMoveInit[1] == -2) {
					aux.neverMoved();
					aux.calculateMoves();
				}
			}
		}
	}
	/**
	 * checks if the move in finalPos is valid for the piece in cell
	 * @param cell 				board cell that contains the piece to be moved
	 * @param finalPos 			position that the piece is to be moved to
	 * @return 0 				invalid movement, the piece can't move like that
	 * @return 1 				valid movement
	 * @return 2 				invalid movement, piece with the same color in finalPos
	 * @return 3 				invalid movement, another piece in the way
	 * @return 4 				valid movement, oponent's piece on the final position, you can capture the piece
	 * @return 5 				valid movement, castling king side (short)
	 * @return 6 				valid movement, castling queen side (long)
	 * @return 7 				valid movement, en passant
	 * @return -1 				error: empty cell
	 * @return -2 				error: invalid finalPos, outside the board
	 */
	public int checkMoves(Cell cell, int[] finalPos) {
		if(cell.isEmpty()) {			//check errors on input values
			return -1;
		}
		if(finalPos[0] < 0 || finalPos[0] > 7 || finalPos[1] < 0 || finalPos[1] > 7) {
			return -2;
		}
		boolean capturePiece = false;
		int[][] possibleMoves = cell.showPiecePossibleMoves();
		for(int[] move: possibleMoves) {
			capturePiece = false;
			if((cell.showPosition()[0] + move[0]) == finalPos[0]) {
				if((cell.showPosition()[1] + move[1]) == finalPos[1]) {
					//you can move there if there are no pieces in the way or pieces of the same color there
					if(cell.showPieceColor() == cells[finalPos[0] * 8 + finalPos[1]].showPieceColor()) {
						return 2; //piece of the same color on the final position
					}
					if(!cells[finalPos[0] * 8 + finalPos[1]].isEmpty()) {
						capturePiece = true;
					}
					if(cell.showPieceName() == 'N') {
						if(capturePiece) {
							return 4;
						}
						return 1; //the knight can jump pieces
					}
					if(cell.showPieceName() == 'K' && cell.showPieceColor() == Piece.color.white) { //white king castling
						if(move[0] == 2 && move[1] == 0) { //castling king side (short)
							if(cell.getPiece().showNeverMoved()) { // king never moved
								if(cells[7 * 8 + 0].showPieceName() == 'R' && cells[7 * 8 + 0].getPiece().showNeverMoved()) { //rook never moved
									if(cells[6 * 8 + 0].isEmpty() && cells[5 * 8 + 0].isEmpty()) { //cells between king and rook are empty
										if(checkCheck(Piece.color.black) == false) { //king is not in check
											if(checkCheck(cells[5 * 8 + 0]) == false) { //king does not pass through a cell that is in check
												if(checkCheck(cells[6 * 8 + 0]) == false) { //king does not go to a cell that puts him in check
													return 5;
												}
											}
										}
									}
								}
							}
						}
						else if(move[0] == -2 && move[1] == 0) { //castling queen side (long)
							if(cell.getPiece().showNeverMoved()) { // king never moved
								if(cells[0 * 8 + 0].showPieceName() == 'R' && cells[0 * 8 + 0].getPiece().showNeverMoved()) { //rook never moved
									if(cells[3 * 8 + 0].isEmpty() && cells[2 * 8 + 0].isEmpty() && cells[1 * 8 + 0].isEmpty()) { //cells between king and rook are empty
										if(checkCheck(Piece.color.black) == false) { //king is not in check
											if(checkCheck(cells[3 * 8 + 0]) == false) { //king does not pass through a cell that is in check
												if(checkCheck(cells[2 * 8 + 0]) == false) { //king does not go to a cell that puts him in check
													return 6;
												}
											}
										}
									}
								}
							}
						}
					}
					if(cell.showPieceName() == 'K' && cell.showPieceColor() == Piece.color.black) { //black king castling
						if(move[0] == 2 && move[1] == 0) { //castling king side (short)
							if(cell.getPiece().showNeverMoved()) { // king never moved
								if(cells[7 * 8 + 7].showPieceName() == 'R' && cells[7 * 8 + 7].getPiece().showNeverMoved()) { //rook never moved
									if(cells[6 * 8 + 7].isEmpty() && cells[5 * 8 + 7].isEmpty()) { //cells between king and rook are empty
										if(checkCheck(Piece.color.white) == false) { //king is not in check
											if(checkCheck(cells[5 * 8 + 7]) == false) { //king does not pass through a cell that is in check
												if(checkCheck(cells[6 * 8 + 7]) == false) { //king does not go to a cell that puts him in check
													return 5;
												}
											}
										}
									}
								}
							}
						}
						else if(move[0] == -2 && move[1] == 0) { //castling queen side (long)
							if(cell.getPiece().showNeverMoved()) { // king never moved
								if(cells[0 * 8 + 7].showPieceName() == 'R' && cells[0 * 8 + 7].getPiece().showNeverMoved()) { //rook never moved
									if(cells[3 * 8 + 7].isEmpty() && cells[2 * 8 + 7].isEmpty() && cells[1 * 8 + 7].isEmpty()) { //cells between king and rook are empty
										if(checkCheck(Piece.color.white) == false) { //king is not in check
											if(checkCheck(cells[3 * 8 + 7]) == false) { //king does not pass through a cell that is in check
												if(checkCheck(cells[2 * 8 + 7]) == false) { //king does not go to a cell that puts him in check
													return 6;
												}
											}
										}
									}
								}
							}
						}
					}
					if((cell.showPosition()[0] - finalPos[0]) == 0) { //vertical movement (same column)
						if((cell.showPosition()[1] - finalPos[1]) > 0) { //movement down
							for(int i = cell.showPosition()[1] - 1; i > finalPos[1]; i --) {
								if(!cells[cell.showPosition()[0] * 8 + i].isEmpty()) {
									return 3; //another piece on the way
								}
							}
							if(capturePiece) {
								if(cell.showPieceName() == 'P') {
									return 0;
								}
								return 4;
							}
							return 1; //valid movement
						}
						else if((cell.showPosition()[1] - finalPos[1]) < 0) { //movement up
							for(int i = cell.showPosition()[1] + 1; i < finalPos[1]; i ++) {
								if(!cells[cell.showPosition()[0] * 8 + i].isEmpty()) {
									return 3; //another piece on the way
								}
							}
							if(capturePiece) {
								if(cell.showPieceName() == 'P') {
									return 0;
								}
								return 4;
							}
							return 1; //valid movement
						}
					}
					if((cell.showPosition()[1] - finalPos[1]) == 0) { //horizontal movement (same row)
						if((cell.showPosition()[0] - finalPos[0]) > 0) { //movement to the left
							for(int i = cell.showPosition()[0] - 1; i > finalPos[0]; i --) {
								if(!cells[i * 8 + cell.showPosition()[1]].isEmpty()) {
									return 3; //another piece on the way
								}
							}
							if(capturePiece) {
								return 4;
							}
							return 1; //valid movement
						}
						else if((cell.showPosition()[0] - finalPos[0]) < 0) { //movement to the right
							for(int i = cell.showPosition()[0] + 1; i < finalPos[0]; i ++) {
								if(!cells[i * 8 + cell.showPosition()[1]].isEmpty()) {
									return 3; //another piece on the way
								}
							}
							if(capturePiece) {
								return 4;
							}
							return 1; //valid movement
						}
					}
					if((cell.showPosition()[0] - finalPos[0]) == (cell.showPosition()[1] - finalPos[1]) ||
						(cell.showPosition()[0] - finalPos[0]) == -(cell.showPosition()[1] - finalPos[1])) { //diagonal movement
						if(cell.showPosition()[0] > finalPos[0]) { //movement to left
							if(cell.showPosition()[1] > finalPos[1]) { //movement down
								for(int i = cell.showPosition()[0] - 1, j = cell.showPosition()[1] - 1 ; i > finalPos[0] || j > finalPos[1]; i--, j--) {
									if(!cells[i * 8 + j].isEmpty()) {
										return 3; //another piece on the way
									}
								}
								if(capturePiece) {
									return 4;
								}
								if(cell.showPieceName() == 'P') { //en passant
									if(cell.showPieceColor() == Piece.color.white) {
										if(cell.showPosition()[1] == 4) {
											if((lastMovePos[0] == lastMoveInit[0]) && (lastMovePos[1] == lastMoveInit[1] - 2)) {
												if((lastMovePos[0] == finalPos[0]) && (lastMovePos[1] == finalPos[1] - 1)) {
													if((cells[lastMovePos[0] * 8 + lastMovePos[1]].showPieceName() == 'P') && 
														(cells[lastMovePos[0] * 8 + lastMovePos[1]].showPieceColor() == Piece.color.black)) {
														return 7; //white pawn en passant
													}
												}
											}
										}
									}
									else if(cell.showPieceColor() == Piece.color.black) {
										if(cell.showPosition()[1] == 3) {
											if((lastMovePos[0] == lastMoveInit[0]) && (lastMovePos[1] == lastMoveInit[1] + 2)) {
												if((lastMovePos[0] == finalPos[0]) && (lastMovePos[1] == finalPos[1] + 1)) {
													if((cells[lastMovePos[0] * 8 + lastMovePos[1]].showPieceName() == 'P') && 
														(cells[lastMovePos[0] * 8 + lastMovePos[1]].showPieceColor() == Piece.color.white)) {
														return 7; //black pawn en passant
													}
												}
											}
										}
									}
								}
								if(!capturePiece && cell.showPieceName() == 'P') {
									return 0;
								}
								return 1; //valid movement
							}
							if(cell.showPosition()[1] < finalPos[1]) { //movement up
								for(int i = cell.showPosition()[0] - 1, j = cell.showPosition()[1] + 1 ; i > finalPos[0] || j < finalPos[1]; i--, j++) {
									if(!cells[i * 8 + j].isEmpty()) {
										return 3; //another piece on the way
									}
								}
								if(capturePiece) {
									return 4;
								}
								if(cell.showPieceName() == 'P') { //en passant
									if(cell.showPieceColor() == Piece.color.white) {
										if(cell.showPosition()[1] == 4) {
											if((lastMovePos[0] == lastMoveInit[0]) && (lastMovePos[1] == lastMoveInit[1] - 2)) {
												if((lastMovePos[0] == finalPos[0]) && (lastMovePos[1] == finalPos[1] - 1)) {
													if((cells[lastMovePos[0] * 8 + lastMovePos[1]].showPieceName() == 'P') && 
														(cells[lastMovePos[0] * 8 + lastMovePos[1]].showPieceColor() == Piece.color.black)) {
														return 7; //white pawn en passant
													}
												}
											}
										}
									}
									else if(cell.showPieceColor() == Piece.color.black) {
										if(cell.showPosition()[1] == 3) {
											if((lastMovePos[0] == lastMoveInit[0]) && (lastMovePos[1] == lastMoveInit[1] + 2)) {
												if((lastMovePos[0] == finalPos[0]) && (lastMovePos[1] == finalPos[1] + 1)) {
													if((cells[lastMovePos[0] * 8 + lastMovePos[1]].showPieceName() == 'P') && 
														(cells[lastMovePos[0] * 8 + lastMovePos[1]].showPieceColor() == Piece.color.white)) {
														return 7; //black pawn en passant
													}
												}
											}
										}
									}
								}
								if(!capturePiece && cell.showPieceName() == 'P') {
									return 0;
								}
								return 1; //valid movement
							}
						}
						else if(cell.showPosition()[0] < finalPos[0]) { //movement to right
							if(cell.showPosition()[1] > finalPos[1]) { //movement down
								for(int i = cell.showPosition()[0] + 1, j = cell.showPosition()[1] - 1 ; i < finalPos[0] || j > finalPos[1]; i++, j--) {
									if(!cells[i * 8 + j].isEmpty()) {
										return 3; //another piece on the way
									}
								}
								if(capturePiece) {
									return 4;
								}
								if(cell.showPieceName() == 'P') { //en passant
									if(cell.showPieceColor() == Piece.color.white) {
										if(cell.showPosition()[1] == 4) {
											if((lastMovePos[0] == lastMoveInit[0]) && (lastMovePos[1] == lastMoveInit[1] - 2)) {
												if((lastMovePos[0] == finalPos[0]) && (lastMovePos[1] == finalPos[1] - 1)) {
													if((cells[lastMovePos[0] * 8 + lastMovePos[1]].showPieceName() == 'P') && 
														(cells[lastMovePos[0] * 8 + lastMovePos[1]].showPieceColor() == Piece.color.black)) {
														return 7; //white pawn en passant
													}
												}
											}
										}
									}
									else if(cell.showPieceColor() == Piece.color.black) {
										if(cell.showPosition()[1] == 3) {
											if((lastMovePos[0] == lastMoveInit[0]) && (lastMovePos[1] == lastMoveInit[1] + 2)) {
												if((lastMovePos[0] == finalPos[0]) && (lastMovePos[1] == finalPos[1] + 1)) {
													if((cells[lastMovePos[0] * 8 + lastMovePos[1]].showPieceName() == 'P') && 
														(cells[lastMovePos[0] * 8 + lastMovePos[1]].showPieceColor() == Piece.color.white)) {
														return 7; //black pawn en passant
													}
												}
											}
										}
									}
								}
								if(!capturePiece && cell.showPieceName() == 'P') {
									return 0;
								}
								return 1; //valid movement
							}
							if(cell.showPosition()[1] < finalPos[1]) { //movement up
								for(int i = cell.showPosition()[0] + 1, j = cell.showPosition()[1] + 1 ; i < finalPos[0] || j < finalPos[1]; i++, j++) {
									System.out.print(i);
									System.out.println(j);
									if(!cells[i * 8 + j].isEmpty()) {
										//System.out.println(cells[j * 8 + i].showPieceName());
										return 3; //another piece on the way
									}
								}
								if(capturePiece) {
									return 4;
								}
								if(cell.showPieceName() == 'P') { //en passant
									if(cell.showPieceColor() == Piece.color.white) {
										if(cell.showPosition()[1] == 4) {
											if((lastMovePos[0] == lastMoveInit[0]) && (lastMovePos[1] == lastMoveInit[1] - 2)) {
												if((lastMovePos[0] == finalPos[0]) && (lastMovePos[1] == finalPos[1] - 1)) {
													if((cells[lastMovePos[0] * 8 + lastMovePos[1]].showPieceName() == 'P') && 
														(cells[lastMovePos[0] * 8 + lastMovePos[1]].showPieceColor() == Piece.color.black)) {
														return 7; //white pawn en passant
													}
												}
											}
										}
									}
									else if(cell.showPieceColor() == Piece.color.black) {
										if(cell.showPosition()[1] == 3) {
											if((lastMovePos[0] == lastMoveInit[0]) && (lastMovePos[1] == lastMoveInit[1] + 2)) {
												if((lastMovePos[0] == finalPos[0]) && (lastMovePos[1] == finalPos[1] + 1)) {
													if((cells[lastMovePos[0] * 8 + lastMovePos[1]].showPieceName() == 'P') && 
														(cells[lastMovePos[0] * 8 + lastMovePos[1]].showPieceColor() == Piece.color.white)) {
														return 7; //black pawn en passant
													}
												}
											}
										}
									}
								}
								if(!capturePiece && cell.showPieceName() == 'P') {
									return 0;
								}
								return 1; //valid movement
							}
						}
					}
				}
			}
		}
	return 0;
	}
	/**
	 * checks if a cell is in check
	 * @param cellCheck 		cell to check
	 * @return 					<code>true</code> the cell is in check
	 * @return 					<code>false</code> the cell is not in check
	 */
	public boolean checkCheck(Cell cellCheck) {
		Piece.color defSide = cellCheck.showPieceColor();
		if(defSide == Piece.color.white) { //black attacking
			for(Cell cell: cells) {
				if(cell.isEmpty()) {
					continue;
				}
				if(cell.showPieceColor() == Piece.color.white) {
					continue;
				}
				if(checkMoves(cell, cellCheck.showPosition()) == 4) {
					flagCheckBlack = true; //black king is in check
					return true;
				}
			}
		}
		else if(defSide == Piece.color.black) { //white attacking
			for(Cell cell: cells) {
				if(cell.isEmpty()) {
					continue;
				}
				if(cell.showPieceColor() == Piece.color.black) {
					continue;
				}
				if(checkMoves(cell, cellCheck.showPosition()) == 4) {
					flagCheckBlack = true; //black king is in check
					return true;
				}
			}
		}
		
		return false;
	}
	/**
	 * checks if the opponent is in check
	 * @param attackingSide		color of the player attacking
	 * @return 					<code>true</code> the opponent is in check
	 * @return 					<code>false</code> the opponent is not in check
	 */
	public boolean checkCheck(Piece.color attackingSide) {
		Cell kingCell = null;
		int ret;
		if(attackingSide == Piece.color.white) {
			for(Cell cell: cells) {
				if(cell.showPieceColor() == Piece.color.black && cell.showPieceName() == 'K') {
					kingCell = cell;
					/*System.out.print(kingCell.showPosition()[0]);
					System.out.println(kingCell.showPosition()[1]);*/
					break;
				}
			}
			if(kingCell == null) {
				return false;
			}
			for(Cell cell: cells) {
				if(cell.isEmpty()) {
					continue;
				}
				if(cell.showPieceColor() == Piece.color.black) {
					continue;
				}
				ret = checkMoves(cell, kingCell.showPosition());
				//System.out.println(ret);
				if(ret == 3) {
					/*System.out.println(cell.showPieceColor());
					System.out.println(kingCell.showPieceColor());*/
				}
				if(ret == 4) {
					flagCheckBlack = true; //black king is in check
					return true;
				}
			}
		}
		else if(attackingSide == Piece.color.black) {
			for(Cell cell: cells) {
				if(cell.showPieceColor() == Piece.color.white && cell.showPieceName() == 'K') {
					kingCell = cell;
					/*System.out.print(kingCell.showPosition()[0]);
					System.out.println(kingCell.showPosition()[1]);*/
					break;
				}
			}
			if(kingCell == null) return false;
			for(Cell cell: cells) {
				if(cell.isEmpty()) {
					continue;
				}
				if(cell.showPieceColor() == Piece.color.white) {
					continue;
				}
				ret = checkMoves(cell, kingCell.showPosition());
				//System.out.println(ret);
				if(ret == 3) {
					/*System.out.println(cell.showPieceColor());
					System.out.println(kingCell.showPieceColor());*/
				}
				if(ret == 4) {
					flagCheckWhite = true; //white king is in check
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * prints the board to the console
	 * Pieces are print using letters:
	 * 		'K' - King
	 * 		'Q' - Queen
	 * 		'B' - Bishop
	 * 		'N' - Knight
	 * 		'R' - Rook
	 * 		'P' - Pawn
	 * 		' ' - white cell
	 * 		'X' - black cell
	 * @param side				side of the board facing the player
	 */
	public void printBoard(Piece.color side) {
		System.out.println("  a b c d e f g h");
		int start = 0;
		int end = 7;
		int offset = 1;
		if(side == Piece.color.white) {
			start = 7;
			end = 0;
			offset = -1;
		}
		while(start != end+offset) {
			System.out.print((start + 1) + " ");
			for(int i = 0; i < 8; i++) {
				if(cells[i * 8 + start].isEmpty()) {
					if(cells[i * 8 + start].showColor() == Cell.ccolor.white) {
						System.out.print("  ");
					}
					else if(cells[i * 8 + start].showColor() == Cell.ccolor.black) {
						System.out.print("X ");
					}
				}
				else {
					System.out.print(cells[i * 8 + start].showPieceName() + " ");
				}
			}
			System.out.println((start + 1));
			start += offset;
		}
		System.out.println("  a b c d e f g h");
	}
	/**
	 * prints the board to a string
	 * @param side				side of the board facing the player
	 * @return					board on a string
	 */
	public String toString(Piece.color side) {
		String output = "";
		int start = 0;
		int end = 7;
		int offset = 1;
		if(side == Piece.color.white) {
			start = 7;
			end = 0;
			offset = -1;
		}
		while(start != end+offset) {
			for(int i = 0; i < 8; i++) {
				String p;
				String v;
				if(cells[i * 8 + start].isEmpty()) {
					p = "n";
					v = ColorsAPI.colorToStringCell(cells[i * 8 + start].showColor())+"";
					output+=p+v;
				}
				else {
					p = cells[i * 8 + start].showPieceName()+"";
					v = ColorsAPI.colorToString(cells[i * 8 + start].showPieceColor())+"";
					output += p+v;
				}
			}
			start += offset;
		}
		return output;
	}
	/**
	 * prints the board with white always facing the player
	 */
	public void printBoardColor() {
		for(int j = 7; j >= 0; j--) {
			System.out.print((j + 1) + " ");
			for(int i = 0; i < 8; i++) {
				if(cells[i * 8 + j].isEmpty()) {
					if(cells[i * 8 + j].showColor() == Cell.ccolor.white) {
						System.out.print("  ");
					}
					else if(cells[i * 8 + j].showColor() == Cell.ccolor.black) {
						System.out.print("X ");
					}
				}
				else {
					System.out.print(cells[i * 8 + j].showPieceColor() + " ");
				}
			}
			System.out.println((j + 1));
		}
	}	
	/**
	 * gets the piece to promote the pawn to
	 * @return					name of the piece to replace
	 */
	public char getPromotion() {
		/*
		Scanner in = new Scanner(System.in);
		System.out.println("What piece do you want? (Q, R, N, B)");
		char inc;
		while(true) {
			inc = in.nextLine().toUpperCase().charAt(0);
			if(inc == 'Q' || inc == 'R' || inc == 'N' || inc == 'B') {
				in.close();
				return inc;
			}
		}*/
		return 'Q';	
	}
	/**
	 * @return					returns white player points
	 */
	public int getWhitePoints() {
		return whitePoints;
	}
	/**
	 * @return					returns black player points
	 */
	public int getBlackPoints() {
		return blackPoints;
	}
	/**
	 * @return					returns how many pieces the white player captured
	 */
	public int numberPiecesCapturedByWhite() {
		return capturedPiecesByWhite.size();
	}
	/**
	 * @return					returns how many pieces the black player captured
	 */
	public int numberPiecesCapturedByBlack() {
		return capturedPiecesByBlack.size();
	}
	/**
	 * stores the last move played
	 * @param init				initial position of the movement
	 * @param fin				final position of the movement
	 */
	private void storeLastMoves(int[] init, int[] fin) {
		this.lastMoveInit[0] = init[0];
		this.lastMoveInit[1] = init[1];
		this.lastMovePos[0] = fin[0];
		this.lastMovePos[1] = fin[1];
		
	}	
	/**
	 * checks if the opponent is in checkmate
	 * @param attackingSide		color of the attacking player
	 * @return					<code>true</code> if the the opponent if in checkmate
	 */
	public boolean checkCheckMate(Piece.color attackingSide) {
		Cell kingCell = null;
		if(attackingSide == Piece.color.white) {
			for(Cell cell: cells) {
				if(cell.showPieceColor() == Piece.color.black && cell.showPieceName() == 'K') {
					kingCell = cell;
					break;
				}
			}
			//verificar se esta em check na posiçao atual
			if(!checkCheck(kingCell)) return false;
			
			//verificar check com todos os movimentos do rei
			int[] finPos = {0,0};
			for(int[] move : kingCell.showPiecePossibleMoves()) {
				finPos[0] = kingCell.showPosition()[0] + move[0];
				finPos[1] = kingCell.showPosition()[1] + move[1];
				if(finPos[0] < 0 || finPos[0] > 7 || finPos[1] < 0 || finPos[1] > 7) {
					continue;
				}
				if(move(kingCell.showPosition(), finPos, kingCell.showPieceColor())) {
					if(!checkCheck(kingCell)) {
						undoMove();
						return false;
					}
					undoMove();
				}
			}
			
			//verificar check com todos os movimentos das restantes peças
			for(Cell cell : cells) { 																//percorrer todas as celulas
				if(!cell.isEmpty()) {																//que nao estao vazias
					if(cell.showPieceColor() == kingCell.showPieceColor()) { 						//da mesma cor que o rei
						for(int[] move : cell.showPiecePossibleMoves()) {							//percorrer todos os movimentos da peça
							finPos[0] = cell.showPosition()[0] + move[0];
							finPos[1] = cell.showPosition()[1] + move[1];
							if(finPos[0] < 0 || finPos[0] > 7 || 
								finPos[1] < 0 || finPos[1] > 7) {									//verificar se o move é para dentro do board
								continue;
							}
							if(move(cell.showPosition(), finPos, kingCell.showPieceColor())) {		//mover a peça
								if(!checkCheck(kingCell)) {											//verificar se o rei ainda está em check
									undoMove();
									return false;
								}
								undoMove();
							}
						}
					}
				}
			}
		}
		else if(attackingSide == Piece.color.black) {
			for(Cell cell: cells) {
				if(cell.showPieceColor() == Piece.color.white && cell.showPieceName() == 'K') {
					kingCell = cell;
					break;
				}
			}
			//verificar se esta em check na posiçao atual
			if(!checkCheck(kingCell)) return false;
			
			//verificar check com todos os movimentos do rei
			int[] finPos = {0,0};
			for(int[] move : kingCell.showPiecePossibleMoves()) {
				finPos[0] = kingCell.showPosition()[0] + move[0];
				finPos[1] = kingCell.showPosition()[1] + move[1];
				if(finPos[0] < 0 || finPos[0] > 7 || finPos[1] < 0 || finPos[1] > 7) {
					continue;
				}
				if(move(kingCell.showPosition(), finPos, kingCell.showPieceColor())) {
					if(!checkCheck(kingCell)) {
						undoMove();
						return false;
					}
					undoMove();
				}
			}
			
			//verificar check com todos os movimentos das restantes peças
			for(Cell cell : cells) { 																//percorrer todas as celulas
				if(!cell.isEmpty()) {																//que nao estao vazias
					if(cell.showPieceColor() == kingCell.showPieceColor()) { 						//da mesma cor que o rei
						for(int[] move : cell.showPiecePossibleMoves()) {							//percorrer todos os movimentos da peça
							finPos[0] = cell.showPosition()[0] + move[0];
							finPos[1] = cell.showPosition()[1] + move[1];
							if(finPos[0] < 0 || finPos[0] > 7 || 
								finPos[1] < 0 || finPos[1] > 7) {									//verificar se o move é para dentro do board
								continue;
							}
							if(move(cell.showPosition(), finPos, kingCell.showPieceColor())) {		//mover a peça
								if(!checkCheck(kingCell)) {											//verificar se o rei ainda está em check
									undoMove();
									return false;
								}
								undoMove();
							}
						}
					}
				}
			}
		}
		return true;
	}
	/**
	 * Call before using the method move(...)
	 * @param move			movement in initialPosition finalPosition (coordinates)
	 * @return				move in algebraic notation
	 */
	public String moveToNotation(String move) {
		int col_i = move.charAt(0) - 'a';
		int row_i = move.charAt(1) - '1';
		int col_f = move.charAt(2) - 'a';
		int row_f = move.charAt(3) - '1';
		int[] finalPos = {col_f, row_f};
		if(cells[col_i * 8 + row_i].isEmpty()) {
			return null;
		}
		char piece = cells[col_i * 8 + row_i].showPieceName();
		int checking = checkMoves(cells[col_i * 8 + row_i], finalPos);
		if(checking == 5) {
			return "O-O";
		}
		else if(checking == 6) {
			return "O-O-O";
		}
		if(piece == 'P') {
			return (checking == 4 ?(move.charAt(0) +  "x") : "") + move.charAt(2) + move.charAt(3);
		}
		else {
			return piece + (checking == 4 ? "x" : "") + move.charAt(2) + move.charAt(3);
		}
	}
	/**
	 * Call after using the method moveToNotation(...) and move(...)
	 * @param notation				algebraic notation previously calculated
	 * @param attackingSide			color of the attacking player
	 * @return						algebraic notation with check and check mate
	 */
	public String notationAddCheck(String notation, Piece.color attackingSide) {
		if(checkCheckMate(attackingSide)) {
			return notation + "#";
		}
		else if(checkCheck(attackingSide)) {
			return notation + "+";
		}
		return notation;
	}
}