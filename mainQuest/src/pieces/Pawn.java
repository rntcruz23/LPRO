package pieces;

import java.util.LinkedList;

import mainQuest.Board;

public class Pawn extends Peca {
	int[][] eat;
	int[][] availableEat;
	public Pawn(int[] startPos,color piece) {
		super(startPos,piece,'P');
		if(piece == Peca.color.white) {
			calcMoves = new int[][] {{1,0}};
			eat = new int[][] {{1,1},{1,-1}};
		}
		else {
			calcMoves = new int[][] {{-1,0}};
			eat = new int[][] {{-1,-1},{-1,1}};
		}
	}
	@Override
	public void calculateMoves() {
		super.calculateMoves();
		int [][] available = getAvailable();
		int[][] finalAvailable;
		int[][] auxStart;
		int[][] auxEat;
		if (startPos()) {
			auxStart = new int[available.length +1][2];
			auxStart[0][0] = available[0][0];
			auxStart[0][1] = available[0][1];
			auxStart[1][0] = available[0][0] + calcMoves[0][0];
			auxStart[1][1] = available[0][1] + calcMoves[0][1];
			
		} else auxStart = available;
		auxEat = new int[2][2];
		int[] currPos = getPos();
		int i;
		for (i = 0;i < eat.length; i++ ) {
			int nextR = currPos[0] + eat[i][0];
			int nextC = currPos[1] + eat[i][1];
			if(!Board.inMap(nextR,nextC)) {
				nextR = -1;
				nextC = -1;
			}
			auxEat[i][0] = nextR;
			auxEat[i][1] = nextC;
		}
		availableEat = auxEat.clone();
		finalAvailable = new int[auxEat.length + auxStart.length][2];
		for(i = 0;i < auxStart.length;i++) {
			finalAvailable[i][0] = auxStart[i][0];
			finalAvailable[i][1] = auxStart[i][1];
		}
		for(i = auxStart.length;i < finalAvailable.length;i++) {
			finalAvailable[i][0] = auxEat[i - auxStart.length][0];
			finalAvailable[i][1] = auxEat[i - auxStart.length][1];
		}
		setAvailable(auxStart);
	}
	private boolean startPos() {
		int sRow;
		if(getColor() == Peca.color.white)
			sRow = 1;
		else sRow = 6;
		return (getPos()[0] == sRow);
	}
	public int pawnMove(Board board,int[] move,LinkedList<Peca> whites,LinkedList<Peca> blacks) {
		int valid = -1;
		LinkedList<Peca> notTeam;
		for(int[] e: availableEat) {
			if((move[0] == e[0]) && (move[1] == e[1])) {
				valid = 1;
				break;
			}else valid = -1;
		}
		if(valid == 1) {
			if(getColor() == Peca.color.black)	notTeam = whites;
			else notTeam = blacks;
			for(Peca b : notTeam) {
				int[] bp = b.getPos();
				if((move[0] == bp[0]) && (move[1] == bp[1])) {
					valid = 2;
					return valid;
				}else valid = -2;
			}
		}
		if(valid == -1)
			for(int[] e:getAvailable()) {
				if((move[0] == e[0]) && (move[1] == e[1])) {
					valid = 3;
					break;
				}
				else valid = -3;
			}
		if(valid == 3) {
			if(getColor() == Peca.color.black)	notTeam = whites;
			else notTeam = blacks;
			for(Peca b : notTeam) {
				int[] bp = b.getPos();
				if((move[0] == bp[0]) && (move[1] == bp[1])) {
					valid = 4;
					return valid;
				}else valid = -4;
			}
		}
		return valid;
	}
	public int[][] getEat(){
		return availableEat;
	}
}
