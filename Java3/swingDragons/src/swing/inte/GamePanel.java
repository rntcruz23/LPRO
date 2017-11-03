package swing.inte;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import dkeep.logic.Map;

public class GamePanel extends JPanel{
	ImageIcon dragonA,dragonS;
	JLabel[][] labels;
	JLabel status;
	JTextArea text; //Boring
	public GamePanel() {
		Dimension size = getPreferredSize();
		setPreferredSize(size);	
		labels = new JLabel[10][10];
		status = new JLabel("Welcome!");
		text = new JTextArea();
		text.setFont(new Font("monospaced", Font.PLAIN, 12));
		
		setLayout(new GridBagLayout());
		setBackground(Color.WHITE);
		setBorder(UIManager.getBorder("DesktopIcon.border"));
		
		GridBagConstraints gb = new GridBagConstraints();
		//////Game Map/////
		gb.anchor = GridBagConstraints.LINE_START;
		gb.weightx = 0;
		gb.weighty = 0;
		int row, column;
		add(text,gb);
		/*for(row = 0; row < 10; row++) {
			for(column = 0; column < 10; column++) {
				labels[row][column] = new JLabel();
				gb.gridx = column;
				gb.gridy = row + 1;
				add(labels[row][column],gb);
			}
		}	*/
		
	}
	public void updateScreen(Map game) {
		int row, column;
		text.selectAll();
		text.setText("");
		for(row = 0; row < 10; row++) {
			for(column = 0; column < 10; column++) {
				int[] coords =  new int[] {row,column};
				String aux = String.format(game.getChar(coords).getSymb() + " ");
				//labels[row][column].setIcon(game.getChar(coords).getIcon());
				text.append(aux);
			}
			text.append("\n");
		}
	}
}
