package board;

import java.util.LinkedList;

import pieces.Piece;

public class Board {
	public Cell[] cells = new Cell[64];
	private LinkedList<Piece> capturedPiecesByWhite = new LinkedList();
	private LinkedList<Piece> capturedPiecesByBlack = new LinkedList();
	private int whitePoints = 0;
	private int blackPoints = 0;
	
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
		int check = checkMoves(cells[initialPos[0] * 8 + initialPos[1]], finalPos);
		switch(check) {
		case 0:
			return 0;
		case 1:
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
			cells[finalPos[0] * 8 + finalPos[1]].moveOutPiece();
			cells[finalPos[0] * 8 + finalPos[1]].moveInPiece(piece);
			if(cells[finalPos[0] * 8 + finalPos[1]].showPieceColor() == Piece.color.white) {
				whitePoints += capturedPiece.getPoints();
				capturedPiecesByWhite.add(capturedPiece);
			}
			else {
				blackPoints += capturedPiece.getPoints();
				capturedPiecesByBlack.add(capturedPiece);
			}
			return 4;
		case -1:
			return -1;
		case -2:
			return -2;
		default:
			return -3;
			
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
								for(int i = cell.showPosition()[0] + 1; i > finalPos[0]; i--) {
									for(int j = cell.showPosition()[1] + 1; j > finalPos[1]; j--) {
										if(!cells[(cell.showPosition()[1]) * 8 + i].isEmpty()) {
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
								for(int i = cell.showPosition()[0] + 1; i > finalPos[0]; i--) {
									for(int j = cell.showPosition()[1] + 1; j < finalPos[1]; j++) {
										if(!cells[(cell.showPosition()[1]) * 8 + i].isEmpty()) {
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
									for(int j = cell.showPosition()[1] + 1; j > finalPos[1]; j--) {
										if(!cells[(cell.showPosition()[1]) * 8 + i].isEmpty()) {
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
										if(!cells[(cell.showPosition()[1]) * 8 + i].isEmpty()) {
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
	}
}
