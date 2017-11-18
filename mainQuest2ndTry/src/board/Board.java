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
	
	public int move(int[] initialPos, int[] finalPos) {
		Piece piece = cells[initialPos[0] * 8 + initialPos[1]].getPiece();
		Piece aux;
		int check = checkMoves(cells[initialPos[0] * 8 + initialPos[1]], finalPos);
		switch(check) {
		case 0:
			return 0;
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
					return -3;
				}
				cells[initialPos[0] * 8 + initialPos[1]].moveOutPiece();
				cells[finalPos[0] * 8 + finalPos[1]].moveInPiece(aux);
				aux.movePiece();
			}
			cells[initialPos[0] * 8 + initialPos[1]].moveOutPiece();
			cells[finalPos[0] * 8 + finalPos[1]].moveInPiece(piece);
			return 1;
		case 2: 
			return 2;
		case 3: 
			return 3;
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
						return -3;
				}
				cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
				cells[finalPos[0] * 8 + finalPos[1]].moveInPiece(aux1);
				aux1.movePiece();
			}
			else {
				cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
				cells[finalPos[0] * 8 + finalPos[1]].moveInPiece(piece);
			}
			if(cells[finalPos[0] * 8 + finalPos[1]].showPieceColor() == Piece.color.white) {
				whitePoints += capturedPiece.getPoints();
				capturedPiecesByWhite.add(capturedPiece);
			}
			else {
				blackPoints += capturedPiece.getPoints();
				capturedPiecesByBlack.add(capturedPiece);
			}
			return 4;
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
			return 5;
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
			return 6;
		case -1:
			return -1;
		case -2:
			return -2;
		default:
			return check;
			
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
								for(int i = cell.showPosition()[0] - 1; i > finalPos[0]; i--) {
									for(int j = cell.showPosition()[1] - 1; j > finalPos[1]; j--) {
										if(!cells[j * 8 + i].isEmpty()) {
											System.out.print(i);
											System.out.println(j);
											return 3; //another piece on the way
										}
									}
								}
								if(capturePiece) {
									return 4;
								}
								return 1; //valid movement
							}
							if(cell.showPosition()[1] < finalPos[1]) { //movement up
								for(int i = cell.showPosition()[0] - 1; i > finalPos[0]; i--) {
									for(int j = cell.showPosition()[1] + 1; j < finalPos[1]; j++) {
										if(!cells[j * 8 + i].isEmpty()) {
											System.out.print(i);
											System.out.println(j);
											return 3; //another piece on the way
										}
									}
								}
								if(capturePiece) {
									return 4;
								}
								return 1; //valid movement
							}
						}
						else if(cell.showPosition()[0] < finalPos[0]) { //movement to right
							if(cell.showPosition()[1] > finalPos[1]) { //movement down
								for(int i = cell.showPosition()[0] + 1; i < finalPos[0]; i++) {
									for(int j = cell.showPosition()[1] - 1; j > finalPos[1]; j--) {
										if(!cells[j * 8 + i].isEmpty()) {
											System.out.print(i);
											System.out.println(j);
											return 3; //another piece on the way
										}
									}
								}
								if(capturePiece) {
									return 4;
								}
								return 1; //valid movement
							}
							if(cell.showPosition()[1] < finalPos[1]) { //movement up
								for(int i = cell.showPosition()[0] + 1; i < finalPos[0]; i++) {
									for(int j = cell.showPosition()[1] + 1; j < finalPos[1]; j++) {
										if(!cells[j * 8 + i].isEmpty()) {
											System.out.print(i);
											System.out.println(j);
											return 3; //another piece on the way
										}
									}
								}
								if(capturePiece) {
									return 4;
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
		if(attackingSide == Piece.color.white) {
			for(Cell cell: cells) {
				if(cell.showPieceColor() == Piece.color.black && cell.showPieceName() == 'K') {
					kingCell = cell;
					break;
				}
			}
			if(kingCell == null) return false;
			for(Cell cell: cells) {
				if(cell.isEmpty()) {
					continue;
				}
				if(cell.showPieceColor() == Piece.color.black) {
					continue;
				}
				if(checkMoves(cell, kingCell.showPosition()) == 4) {
					flagCheckBlack = true; //black king is in check
					return true;
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
			if(kingCell == null) return false;
			for(Cell cell: cells) {
				if(cell.isEmpty()) {
					continue;
				}
				if(cell.showPieceColor() == Piece.color.white) {
					continue;
				}
				if(checkMoves(cell, kingCell.showPosition()) == 4) {
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
	
	public char getPromotion() {
		Scanner in = new Scanner(System.in);
		System.out.println("What piece do you want? (Q, R, N, B)");
		char inc;
		while(true) {
			inc = in.nextLine().toUpperCase().charAt(0);
			if(inc == 'Q' || inc == 'R' || inc == 'N' || inc == 'B') {
				return inc;
			}
		}
	}
}
