package mainQuest;
import pieces.*;

public class Board {
	private static char [][] board = new char[8][8];
	
	private Knight k1w,k2w,k1b,k2b;
	private Bishop b1w,b2w,b1b,b2b;
	private Rook r1w,r2w,r1b,r2b;
	private King kw,kb;
	private Queen qw,qb;
	private Pawn[] pw = new Pawn[8];
	private Pawn[] pb = new Pawn[8];
	
	public Board() {
		int row, col;
		for (row = 0; row < 8 ; row++) {
			for (col = 0; col < 8; col++) {
				if ((col & 1) == 0)
					board[row][col] = ' ';
				else board[row][col] = 'X';
			}
		}
	}
	public void printBoard(){
		int row, col;
		for (row = 0; row < 8 ; row++) {
			for (col = 0; col < 8; col++) {
				String aux = '|' + String.format("%c",board[row][col]);
				System.out.print(aux);
			}
			System.out.println("|\n");
		}
	}
	public void fillBoard() {
		putKings();
		putQueens();
		putRooks();
		putBishop();
		putKnights();
		putPawns();
	}
	public void refreshBoard() {
			
	}
	private void putPawns() {
		int i;
		for(i = 0; i < 8; i++) {
			int[] pawnPosw = {Peca.p1w[0],Peca.p1w[1] + i};
			int [] pawnPosb = {Peca.p1b[0],Peca.p1b[1] + i};
			pw[i] = new Pawn(pawnPosw);
			pb[i] = new Pawn(pawnPosb);
			board[pw[i].getPos()[0]][pw[i].getPos()[1]] = 'P';
			board[pb[i].getPos()[0]][pb[i].getPos()[1]] = 'P';
		}
	}
	private void putKnights() {		
		k1w = new Knight(Peca.sk1w);
		k2w = new Knight(Peca.sk2w);
		k1b = new Knight(Peca.sk1b);
		k2b = new Knight(Peca.sk2b);
		
		board[k1w.getPos()[0]][k1w.getPos()[1]] = 'K';
		board[k2w.getPos()[0]][k2w.getPos()[1]] = 'K';
		board[k1b.getPos()[0]][k1b.getPos()[1]] = 'K';
		board[k2b.getPos()[0]][k2b.getPos()[1]] = 'K';
	}
	private void putKings(){
		kw = new King(Peca.kw);
		kb = new King(Peca.kb);
		
		board[kw.getPos()[0]][kw.getPos()[1]] = 'K';
		board[kb.getPos()[0]][kb.getPos()[1]] = 'K';
	}
	private void putQueens() {
		qw = new Queen(Peca.qw);
		qb = new Queen(Peca.qb);
		
		board[qw.getPos()[0]][qw.getPos()[1]] = 'Q';
		board[qb.getPos()[0]][qb.getPos()[1]] = 'Q';
	}
	private void putRooks() {
		r1w = new Rook(Peca.sr1w);
		r2w = new Rook(Peca.sr2w);
		r1b = new Rook(Peca.sr1b);
		r2b = new Rook(Peca.sr2b);
		
		board[r1w.getPos()[0]][r1w.getPos()[1]] = 'R';
		board[r2w.getPos()[0]][r2w.getPos()[1]] = 'R';
		board[r1b.getPos()[0]][r1b.getPos()[1]] = 'R';
		board[r2b.getPos()[0]][r2b.getPos()[1]] = 'R';
	}
	private void putBishop() {
		b1w = new Bishop(Peca.sb1w);
		b2w = new Bishop(Peca.sb2w);
		b1b = new Bishop(Peca.sb1b);
		b2b = new Bishop(Peca.sb2b);
		
		board[b1w.getPos()[0]][b1w.getPos()[1]] = 'B';
		board[b2w.getPos()[0]][b2w.getPos()[1]] = 'B';
		board[b1b.getPos()[0]][b1b.getPos()[1]] = 'B';
		board[b2b.getPos()[0]][b2b.getPos()[1]] = 'B';
	}
}