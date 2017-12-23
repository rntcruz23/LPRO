package windows.game;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.border.MatteBorder;

public class BoardView {
	private ImageIcon[] cell=new ImageIcon[2]; // 0 tem a cell black e 2 a cell white
	private JLabel Label[][] = new JLabel[8][8];
	private JLayeredPane panel;
	private int select_pos[]={0,0};
	private int[] pos= new int[2];
	private int[] size_cell= {65,65};
	private boolean state= false;
	private GameView gameView;
	private JLabel[][] matrix_pieces=new JLabel[8][8];

	public BoardView(JLayeredPane panel, GameView game) {
		this.panel=panel;
		setGameView(game);
	}
	public void create(JFrame frmChess) {
		panel.setBorder(new MatteBorder(5, 5, 5, 5, (Color) Color.BLACK));
		ImageIcon aux1;
		ImageIcon aux2;
		aux1=createImageIcon("black_cell.jpg"); 
		aux2=createImageIcon("white_cell.jpg");
		pos[0]= 5;
		pos[1]= 5;
		int n_cell=0;
		for(int i=0;i<8;i++) {
			if(i%2==0) {
				cell[0] =aux2;
				cell[1] =aux1;		
			}
			else {
				cell[0] =aux1;
				cell[1] =aux2;
			}
			for(int u = 0; u < 8; u++) {
				if(u % 2 == 0) n_cell=0;
				else  n_cell = 1;
				Label[i][u]=new JLabel();
				Label[i][u].setBounds(pos[0], pos[1],size_cell[0], size_cell[1]);
				pos[0]+=65;
				panel.add(Label[i][u],new Integer(1));	
				Label[i][u].setIcon(cell[n_cell]);
			}
			pos[1]+=65;
			pos[0]=5;
		}	
	}
	public void listener() {
		int row,col;
		for(row = 0; row < 8; row++) {
			for(col = 0; col < 8; col++) {
				int rows = row;
				int cols = col;
				Label[row][col].addMouseListener(new MouseAdapter(){
					@Override
					public void mouseClicked(MouseEvent arg0) {			
							if(state == false) clikPiece(rows, cols);
							else if(state == true) clikMove(rows, cols);
					}
				});	
			}
		}
	}
	private void clikPiece(int row, int col){
		Label[row][col].setBorder(new MatteBorder(2, 2, 2, 2, (Color) Color.RED));	
		select_pos[0] = col;
		select_pos[1] = row + 1;
		state=true;
	}
	private void clikMove(int row, int col){
		Label[row][col].setBorder(new MatteBorder(2, 2, 2, 2, (Color) Color.GREEN));
		Label[select_pos[0]][select_pos[1] - 1].setBorder(null);
		state=false;
		char xi = (char)(select_pos[0]+'a');
		char xf = (char)(col+'a');
		row = row + 1;
		String move = "" + xi + select_pos[1] + xf + row;
		gameView.getUser().sendCommand("m " + move);
		Label[row][col].setBorder(null);
	}
	public void initPieces() {  
		int x;
		int y;
		pos[0]= 5;
		pos[1]= 5;
		for(x=0;x<8;x++) {
			for(y=0;y<8;y++) {
				matrix_pieces[x][y]=new JLabel();
				matrix_pieces[x][y].setBounds(pos[0], pos[1], size_cell[0],size_cell[1]);
				panel.add(matrix_pieces[x][y],new Integer(2));
				pos[0]+=65;
			}
			pos[1]+=65;
			pos[0]=5;
		}
	}
	public void putPieces(String board) {
		int i;
		int col = 0;
		int row = 0;
		for(i = 0;i < board.length(); i += 2) {
				col = (i / 2) % 8;
				row = Math.floorDiv(i, 16);
				String piece = board.charAt(i)+"";
				String color = board.charAt(i+1)+"";
				ImageIcon img = piece.equals("n")?null:createImageIcon(piece+color+".png");
				matrix_pieces[row][col].setIcon(img);
		}
	}
	private void movePiece(int inicial_x, int inicial_y, int final_x, int final_y) {
		Icon aux;
		Icon aux_2;
		aux_2=aux= matrix_pieces[final_x][final_y].getIcon();
		if(aux_2==null) {
			aux= matrix_pieces[inicial_x][inicial_y].getIcon();
			matrix_pieces[inicial_x][inicial_y].setIcon(null);
			matrix_pieces[final_x][final_y].setIcon(aux);
			panel.add(matrix_pieces[final_x][final_y],new Integer(2));
		}
	}
	private ImageIcon createImageIcon(String path) {
		java.net.URL imgUrl = getClass().getResource(path);
		if(imgUrl != null)
			return new ImageIcon(imgUrl);
		System.out.println("No file path " + path);
		return null;	
	}
	public GameView getGameView() {
		return gameView;
	}
	public void setGameView(GameView gameView) {
		this.gameView = gameView;
	}
}