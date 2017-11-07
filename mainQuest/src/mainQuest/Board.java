package mainQuest;
import java.util.LinkedList;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Peca;
import pieces.Queen;
import pieces.Rook;
public class Board {
	private static char [][] board = new char[8][8];
	private int[] scores; //0 -> white score; 1 -> black score
	private Knight k1w,k2w,k1b,k2b;
	private Bishop b1w,b2w,b1b,b2b;
	private Rook r1w,r2w,r1b,r2b;
	private King kw,kb;
	private Queen qw,qb;
	private Pawn[] pw = new Pawn[8];
	private Pawn[] pb = new Pawn[8];
	private LinkedList<Peca> list;
	private LinkedList<Peca> whites;
	private LinkedList<Peca> blacks;
	public Board() {
		int row, col;
		scores = new int[] {0,0};
		list = new LinkedList<Peca>();
		whites = new LinkedList<Peca>();
		blacks = new LinkedList<Peca>();
		for (row = 0; row < 8 ; row++) {
			for (col = 0; col < 8; col++) {
				if (((col+ (row & 1)) & 1) == 1)
					board[row][col] = 'X';
				else board[row][col] = ' ';
			}
		}
	}
	public void printBoard(){
		String letters = new String(" ||1||2||3||4||5||6||7||8|");
		System.out.println(letters);
		refreshBoard();
	}
	public void fillBoard() {
		putKings();
		putQueens();
		putRooks();
		putBishop();
		putKnights();
		putPawns();
	}
	private void refreshBoard() {
		cleanBoard();
		updateBoard();
		showBoard();
	}
	private void updateBoard() {
		int row,col;
		for(Peca it:list) {
			row = it.getPos()[0];
			col = it.getPos()[1];
			board[row][col] = it.getPiece();
		}
	}
	private void putPawns() {
		int i;
		for(i = 0; i < 8; i++) {
			int[] pawnPosw = {Peca.p1w[0],Peca.p1w[1] + i};
			int [] pawnPosb = {Peca.p1b[0],Peca.p1b[1] + i};
			pw[i] = new Pawn(pawnPosw,Peca.color.white);
			pb[i] = new Pawn(pawnPosb,Peca.color.black);
			list.add(pw[i]);
			list.add(pb[i]);
			whites.add(pw[i]);
			blacks.add(pb[i]);
			board[pw[i].getPos()[0]][pw[i].getPos()[1]] = 'P';
			board[pb[i].getPos()[0]][pb[i].getPos()[1]] = 'P';
		}
	}
	private void putKnights() {		
		k1w = new Knight(Peca.sk1w,Peca.color.white);
		k2w = new Knight(Peca.sk2w,Peca.color.white);
		k1b = new Knight(Peca.sk1b,Peca.color.black);
		k2b = new Knight(Peca.sk2b,Peca.color.black);
		
		board[k1w.getPos()[0]][k1w.getPos()[1]] = 'K';
		board[k2w.getPos()[0]][k2w.getPos()[1]] = 'K';
		board[k1b.getPos()[0]][k1b.getPos()[1]] = 'K';
		board[k2b.getPos()[0]][k2b.getPos()[1]] = 'K';
		list.add(k1w);
		list.add(k2w);
		list.add(k1b);
		list.add(k2b);
		whites.add(k1w);
		whites.add(k2w);
		blacks.add(k1b);
		blacks.add(k2b);
	}
	private void putKings(){
		kw = new King(Peca.kw,Peca.color.white);
		kb = new King(Peca.kb,Peca.color.black);
		
		board[kw.getPos()[0]][kw.getPos()[1]] = 'K';
		board[kb.getPos()[0]][kb.getPos()[1]] = 'K';
		list.add(kw);
		list.add(kb);
		whites.add(kw);
		blacks.add(kb);
	}
	private void putQueens() {
		qw = new Queen(Peca.qw,Peca.color.white);
		qb = new Queen(Peca.qb,Peca.color.black);
		
		board[qw.getPos()[0]][qw.getPos()[1]] = 'Q';
		board[qb.getPos()[0]][qb.getPos()[1]] = 'Q';
		list.add(qw);
		list.add(qb);
		whites.add(qw);
		blacks.add(qb);
	}
	private void putRooks() {
		r1w = new Rook(Peca.sr1w,Peca.color.white);
		r2w = new Rook(Peca.sr2w,Peca.color.white);
		r1b = new Rook(Peca.sr1b,Peca.color.black);
		r2b = new Rook(Peca.sr2b,Peca.color.black);
		
		board[r1w.getPos()[0]][r1w.getPos()[1]] = 'R';
		board[r2w.getPos()[0]][r2w.getPos()[1]] = 'R';
		board[r1b.getPos()[0]][r1b.getPos()[1]] = 'R';
		board[r2b.getPos()[0]][r2b.getPos()[1]] = 'R';
		list.add(r1w);
		list.add(r2w);
		list.add(r1b);
		list.add(r2b);
		whites.add(r1w);
		whites.add(r2w);
		blacks.add(r1b);
		blacks.add(r2b);
	}
	private void putBishop() {
		b1w = new Bishop(Peca.sb1w,Peca.color.white);
		b2w = new Bishop(Peca.sb2w,Peca.color.white);
		b1b = new Bishop(Peca.sb1b,Peca.color.black);
		b2b = new Bishop(Peca.sb2b,Peca.color.black);
		
		board[b1w.getPos()[0]][b1w.getPos()[1]] = 'B';
		board[b2w.getPos()[0]][b2w.getPos()[1]] = 'B';
		board[b1b.getPos()[0]][b1b.getPos()[1]] = 'B';
		board[b2b.getPos()[0]][b2b.getPos()[1]] = 'B';
		list.add(b1w);
		list.add(b2w);
		list.add(b1b);
		list.add(b2b);
		whites.add(b1w);
		whites.add(b2w);
		blacks.add(b1b);
		blacks.add(b2b);
	}
	public int checkMove(char piece,int[] move,Peca.color color) {
		Peca aux = null;
		int e = 10;
		int found = 0,index=-1;
		for(Peca p: list) {
			if((p.getPiece() == piece) && (p.getColor() == color)) {
				aux = p;
				aux.calculateMoves();
				int[][] mAux = aux.getAvailable();
				if(piece == 'P') {
					Pawn aP = (Pawn)p;
					e = aP.pawnMove(this,move,whites,blacks);
				}
				if(e == 2 || e == -4 || e == 10) {  //Move é posiçao adjacente e peça adversaria, ou move é posiçao em frente e nao tem peça adversaria
					int j = 0;
					for(int[] i: mAux) {
						Pawn pa;
						int[]f = i;
						if(e == 2) {
							pa = (Pawn) aux;
							f = pa.getEat()[j++];
						}
						if((move[0] == i[0]) && (move[1] == i[1]) || ((f[0] == move[0]) && (f[1] == move[1]))) {
							if(found > 0) {
								System.out.println("Ambiguous movement");
								return -1;
							}
							index = list.indexOf(p);
							found++;
						}
					}
				}
			}
		}
		return index;
	}
	public static boolean inMap(int nextR,int nextC) {
		return (!((nextR >= 8) || (nextR < 0)) && !((nextC >= 8) || (nextC < 0)));
	}
	public boolean moveP(String input,Peca.color color) {
		char piece = input.charAt(0);
		int flag = 0;
		int[] oldPos;
		int[] move = new int[] {input.charAt(1)-'0'-1,input.charAt(2)-'0'-1};
		Peca toMove,temp = null;
		int index,player;
		index = checkMove(piece,move,color);
		if(index >= 0) {
			toMove = list.get(index);
			LinkedList<Peca> link;
			Peca.color c;
			if(((c = checkColor(toMove,move,color)) != color)) {
				flag++;
				oldPos = toMove.getPos();
				toMove.setPos(move);
				if(c == Peca.color.white) {
					player = 0;
					link = whites;
				} else if (c == Peca.color.black) {
					player = 1;
					link = blacks;
				}
				else return true;
				for(Peca aux : link) {
					if((move[0] == aux.getPos()[0]) && (move[1] == aux.getPos()[1])) {
						temp = aux;
						scores[player]++;
						link.remove(aux);
						list.remove(aux);
						flag++;
						break;
					}
				}
			}
			else {
				System.out.println("Can't move there, a piece is already there");
				return false;
			}
			if(checkKing(color) && (flag > 0)){		//Check if color king is still in check and color piece moved
				if (flag == 2) {					//Check if !color piece was captured and color king still in check -> uncapture piece
					scores[player]--;
					link.add(temp);
					list.add(temp);
				}
				toMove.setPos(oldPos);				//Color piece moved and color king still in check return to old position
				System.out.println("Can't move there, king still in check");
				return false;						//Movement unavailable -> color king still in check
			}
			return true;
		}
		return false;
	}
	public Peca.color checkColor(Peca toMove,int[] move,Peca.color color) {
		LinkedList<Peca> it = list;
		for(Peca i:it) {
			if((move[0] == i.getPos()[0]) && (move[1] == i.getPos()[1]))
				return i.getColor();
		}
		return Peca.color.none;
	}
	private void cleanBoard() {
		int row,col;
		for (row = 0; row < 8 ; row++) {
			for (col = 0; col < 8; col++) {
				if (((col+ (row & 1)) & 1) == 1)
					board[row][col] = 'X';
				else board[row][col] = ' ';
			}
		}
	}
	private void showBoard() {
		int row,col;
		for(row = 0; row < 8;row++) {
			System.out.print(String.format("%d|",row+1));
			for(col = 0; col < 8;col++) {
				System.out.print('|'+String.format("%c|",board[row][col]));
			}
			System.out.println('\n');
		}
	}
	public static char[][] getBoard(){
		return board;
	}
	public boolean checkKing(Peca.color color) {
		cleanBoard();
		updateBoard();
		if(color == Peca.color.white)
			return checkWhiteKing();
		return checkBlackKing();
	}
	public boolean checkBlackKing() {
		int[] kbP = kb.getPos();
		for(Peca p: whites) {
			p.calculateMoves();
			if(existsIn(kbP,p.getAvailable())) {
				return true;
			}
		}
		return false;
	}
	public boolean checkWhiteKing() {
		int[] kwP = kw.getPos();
		for(Peca p: blacks) {
			p.calculateMoves();
			if(existsIn(kwP,p.getAvailable())) {
				return true;
			}
		}
		return false;
	}
	private boolean existsIn(int[] test,int[][] ex) {
		for(int[] a:ex)
			if((a[0] == test[0]) && (a[1] == test[1]))
				return true;
		return false;
	}
}