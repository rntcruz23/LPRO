package board;

import java.util.LinkedList;
import java.util.Scanner;

import pieces.Bishop;
import pieces.Knight;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

public class Board {
	public Cell[] cells = new Cell[64];
	private LinkedList<Piece> capturedPiecesByWhite = new LinkedList<Piece>();
	private LinkedList<Piece> capturedPiecesByBlack = new LinkedList<Piece>();
	private int whitePoints = 0;
	private int blackPoints = 0;
	private boolean flagCheckWhite = false;
	private boolean flagCheckBlack = false;
	private int[] lastMovePos = {0,0};
	private int[] lastMoveInit = {0,0};
	
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
	
	public Board(char teste) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				cells[i * 8 + j] = new Cell(i, j, ' ', Piece.color.none);
			}
		}
	}
	
	public boolean move(int[] initialPos, int[] finalPos, Piece.color side) {
		Piece piece = cells[initialPos[0] * 8 + initialPos[1]].getPiece();
		Piece aux;
		if(piece == null) return false;
		if(piece.showColor() != side) return false;
		boolean flagCheck = checkCheck(side);
		System.out.println(flagCheck);
		int check = checkMoves(cells[initialPos[0] * 8 + initialPos[1]], finalPos);
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
			if(piece.showName() == 'P' && finalPos[1] == 7) { //pawn promotion
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
			cells[initialPos[0] * 8 + initialPos[1]].moveOutPiece();
			cells[finalPos[0] * 8 + finalPos[1]].moveInPiece(piece);
			/*if(flagCheck) { //estava em check no inicio da jogada
				if(piece.showColor() == Piece.color.white) {
					if(checkCheck(Piece.color.black)) { //continua em check depois de mover?
						cells[initialPos[0] * 8 + initialPos[1]].moveInPiece(piece);
						cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
						flagCheck = checkCheck(side);
						System.out.println(flagCheck);
						return false;
					}
				}
				else if(piece.showColor() == Piece.color.white) {
					if(checkCheck(Piece.color.black)) { //continua em check depois de mover?
						cells[initialPos[0] * 8 + initialPos[1]].moveInPiece(piece);
						cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
						flagCheck = checkCheck(side);
						System.out.println(flagCheck);
						return false;
					}
				}
			}*/
			if(side == Piece.color.white && checkCheck(Piece.color.black)) {
				cells[initialPos[0] * 8 + initialPos[1]].moveInPiece(piece);
				cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
				return false;
			}
			else if(side == Piece.color.black && checkCheck(Piece.color.white)) {
				cells[initialPos[0] * 8 + initialPos[1]].moveInPiece(piece);
				cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
				return false;
			}
			storeLastMoves(initialPos, finalPos);
			flagCheck = checkCheck(side);
			System.out.println(flagCheck);
			return true;
		case 2: 
			return false;
		case 3: 
			return false;
		case 4: 
			cells[initialPos[0] * 8 + initialPos[1]].moveOutPiece();
			Piece capturedPiece = cells[finalPos[0] * 8 + finalPos[1]].getPiece();
			Piece aux1;
			if(piece.showName() == 'P' && finalPos[1] == 7) { //pawn promotion
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
			if(flagCheck) { //estava em check no inicio da jogada
				if(piece.showColor() == Piece.color.white) {
					if(checkCheck(Piece.color.black)) { //continua em check depois de mover?
						cells[initialPos[0] * 8 + initialPos[1]].moveInPiece(piece);
						cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
						flagCheck = checkCheck(side);
						System.out.println(flagCheck);
						return false;
					}
				}
				else if(piece.showColor() == Piece.color.white) {
					if(checkCheck(Piece.color.black)) { //continua em check depois de mover?
						cells[initialPos[0] * 8 + initialPos[1]].moveInPiece(piece);
						cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
						flagCheck = checkCheck(side);
						System.out.println(flagCheck);
						return false;
					}
				}
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
			return true;
		case 5:
			int i = 0;
			if(cells[initialPos[0] * 8 + initialPos[1]].showPieceColor() == Piece.color.white) i = 0;
			else if(cells[initialPos[0] * 8 + initialPos[1]].showPieceColor() == Piece.color.black) i = 7;
			Piece aux2 = cells[initialPos[0] * 8 + initialPos[1]].getPiece();
			cells[initialPos[0] * 8 + initialPos[1]].moveOutPiece();
			cells[finalPos[0] * 8 + finalPos[1]].moveInPiece(aux2);
			aux2 = cells[7 * 8 + i].getPiece();
			cells[7 * 8 + i].moveOutPiece();
			cells[5 * 8 + i].moveInPiece(aux2);
			storeLastMoves(initialPos, finalPos);
			return true;
		case 6:
			int j = 0;
			if(cells[initialPos[0] * 8 + initialPos[1]].showPieceColor() == Piece.color.white) j = 0;
			else if(cells[initialPos[0] * 8 + initialPos[1]].showPieceColor() == Piece.color.black) j = 7;
			Piece aux3 = cells[initialPos[0] * 8 + initialPos[1]].getPiece();
			cells[initialPos[0] * 8 + initialPos[1]].moveOutPiece();
			cells[finalPos[0] * 8 + finalPos[1]].moveInPiece(aux3);
			aux1 = cells[0 * 8 + j].getPiece();
			cells[0 * 8 + j].moveOutPiece();
			cells[3 * 8 + j].moveInPiece(aux3);
			storeLastMoves(initialPos, finalPos);
			return true;
		case 7:
			Piece capturedPiece7;
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
				if(flagCheck) { //estava em check no inicio da jogada
					if(piece.showColor() == Piece.color.white) {
						if(checkCheck(Piece.color.black)) { //continua em check depois de mover?
							cells[initialPos[0] * 8 + initialPos[1]].moveInPiece(piece);
							cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
							return false;
						}
					}
					else if(piece.showColor() == Piece.color.white) {
						if(checkCheck(Piece.color.black)) { //continua em check depois de mover?
							cells[initialPos[0] * 8 + initialPos[1]].moveInPiece(piece);
							cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
							return false;
						}
					}
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
				if(flagCheck) { //estava em check no inicio da jogada
					if(piece.showColor() == Piece.color.white) {
						if(checkCheck(Piece.color.black)) { //continua em check depois de mover?
							cells[initialPos[0] * 8 + initialPos[1]].moveInPiece(piece);
							cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
							return false;
						}
					}
					else if(piece.showColor() == Piece.color.white) {
						if(checkCheck(Piece.color.black)) { //continua em check depois de mover?
							cells[initialPos[0] * 8 + initialPos[1]].moveInPiece(piece);
							cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
							return false;
						}
					}
				}
				blackPoints += capturedPiece7.getPoints();
				capturedPiecesByBlack.add(capturedPiece7);
				storeLastMoves(initialPos, finalPos);
			}
			return true;
		case -1:
			return false;
		case -2:
			return false;
		default:
			return false;
			
		}
	}
	public int checkMoves(Cell cell, int[] finalPos) {
		/*
		 * verifica a validade do movimento
		 * 
		 * @return:
		 * 		0 - movimento inválido, a peça não pode fazer esse movimento
		 * 		1 - movimento válido
		 * 		2 - movimento inválido, peça da mesma cor na posição final
		 * 		3 - movimento inválido, outra peça no caminho
		 * 		4 - movimento válido, peça da outra cor na posição final, pode capturar
		 * 		5 - movimento válido, castling king side
		 * 		6 - movimento válido, castling queen side
		 * 		7 - movimento válido, en passant
		 * 		-1 - erro: celula vazia (sem peça)
		 * 		-2 - erro: posição final fora do tabuleiro de jogo
		 */
		//check errors on input values
		if(cell.isEmpty()) {
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
														(cells[lastMovePos[0] * 8 + lastMovePos[1]].showPieceColor() == Piece.color.black)) {
														return 7; //black pawn en passant
													}
												}
											}
										}
									}
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
														(cells[lastMovePos[0] * 8 + lastMovePos[1]].showPieceColor() == Piece.color.black)) {
														return 7; //black pawn en passant
													}
												}
											}
										}
									}
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
														(cells[lastMovePos[0] * 8 + lastMovePos[1]].showPieceColor() == Piece.color.black)) {
														return 7; //black pawn en passant
													}
												}
											}
										}
									}
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
														(cells[lastMovePos[0] * 8 + lastMovePos[1]].showPieceColor() == Piece.color.black)) {
														return 7; //black pawn en passant
													}
												}
											}
										}
									}
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
	
	public boolean checkCheck(Piece.color attackingSide) {
		Cell kingCell = null;
		int ret;
		if(attackingSide == Piece.color.white) {
			for(Cell cell: cells) {
				if(cell.showPieceColor() == Piece.color.black && cell.showPieceName() == 'K') {
					kingCell = cell;
					System.out.print(kingCell.showPosition()[0]);
					System.out.println(kingCell.showPosition()[1]);
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
				System.out.println(ret);
				if(ret == 3) {
					System.out.println(cell.showPieceColor());
					System.out.println(kingCell.showPieceColor());
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
					System.out.print(kingCell.showPosition()[0]);
					System.out.println(kingCell.showPosition()[1]);
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
				System.out.println(ret);
				if(ret == 3) {
					System.out.println(cell.showPieceColor());
					System.out.println(kingCell.showPieceColor());
				}
				if(ret == 4) {
					flagCheckWhite = true; //white king is in check
					return true;
				}
			}
		}
		return false;
	}
	public void printBoard(Piece.color side) {
		System.out.println("  a b c d e f g h");
		if(side == Piece.color.white) {
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
						System.out.print(cells[i * 8 + j].showPieceName() + " ");
					}
				}
				System.out.println((j + 1));
			}
		}
		else if(side == Piece.color.black) {
			for(int j = 0; j < 8; j++) {
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
						System.out.print(cells[i * 8 + j].showPieceName() + " ");
					}
				}
				System.out.println((j + 1));
			}
		}
		System.out.println("  a b c d e f g h");
	}
	
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
	
	public char getPromotion() {
		Scanner in = new Scanner(System.in);
		System.out.println("What piece do you want? (Q, R, N, B)");
		char inc;
		while(true) {
			inc = in.nextLine().toUpperCase().charAt(0);
			if(inc == 'Q' || inc == 'R' || inc == 'N' || inc == 'B') {
				in.close();
				return inc;
			}
		}
	}
	
	public int getWhitePoints() {
		return whitePoints;
	}
	
	public int getBlackPoints() {
		return blackPoints;
	}
	
	public int numberPiecesCapturedByWhite() {
		return capturedPiecesByWhite.size();
	}
	
	public int numberPiecesCapturedByBlack() {
		return capturedPiecesByBlack.size();
	}
	
	private void storeLastMoves(int[] init, int[] fin) {
		this.lastMoveInit[0] = init[0];
		this.lastMoveInit[1] = init[1];
		this.lastMovePos[0] = fin[0];
		this.lastMovePos[1] = fin[1];
	}
}