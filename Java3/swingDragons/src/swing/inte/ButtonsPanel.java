package swing.inte;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonsPanel extends JPanel{
	JButton upButton;
	JButton downButton;
	JButton leftButton;
	JButton rightButton;
	JButton exit;
	JButton newGame;
	MainFrame mainFrame;
	public ButtonsPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		upButton = new JButton("Up");
		downButton = new JButton("Down");
		leftButton = new JButton("Left");
		rightButton = new JButton("Right");
		exit = new JButton("Exit");
		newGame = new JButton("New Game");
		
		setBackground(SystemColor.inactiveCaptionBorder);
		setLayout(new GridBagLayout());
		
		GridBagConstraints gb = new GridBagConstraints();
		gb.weightx = 0;
		gb.weighty = 0;
		
		////New Game Button/////
		gb.anchor = GridBagConstraints.WEST;
		gb.gridy = 0;
		gb.gridx = 0;
		add(newGame,gb);
	
		
		
		////Exit Button/////
		gb.anchor = GridBagConstraints.WEST;
		gb.fill = GridBagConstraints.BOTH;
		gb.gridy = 6;
		gb.gridx = 0;
		add(exit,gb);
		
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionP(e);
			}
		
		});
		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionP(e);
			}
		
		});
		upButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionP(e);
			}
		
		});
		downButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionP(e);
			}
		
		});
		leftButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionP(e);
			}
		
		});
		rightButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionP(e);
			}
		
		});
	}
	public void actionP(ActionEvent e) {
		JButton source = (JButton)e.getSource();
		if (source.equals(exit))
			mainFrame.exitFunc();	
		else if(source.equals(newGame)) {
			mainFrame.newGameFunc();
		return;
		}
		if(source.equals(upButton))
			mainFrame.upFunc();
		else if(source.equals(downButton))
			mainFrame.downFunc();
		else if(source.equals(leftButton))
			mainFrame.leftFunc();
		else if(source.equals(rightButton))
			mainFrame.rightFunc();
		mainFrame.dragonTurn();
	}
	public void removeUpButton(){
		remove(upButton);
	}
	public void removeDownButton(){
		remove(downButton);
	}
	public void removeRightButton(){
		remove(rightButton);
	}
	public void removeLeftButton(){
		remove(leftButton);
	}
	public void addUpButton() {
	////Up Button/////
		GridBagConstraints gb = new GridBagConstraints();
		gb.weightx = 0;
		gb.weighty = 0;
		gb.anchor = GridBagConstraints.SOUTH;
		gb.gridy = 1;
		gb.gridx = 1;
		add(upButton,gb);
	}
	public void addDownButton() {
		GridBagConstraints gb = new GridBagConstraints();
		gb.weightx = 0;
		gb.weighty = 0;
		////Down Button/////
		gb.anchor = GridBagConstraints.CENTER;
		gb.gridy = 2;
		gb.gridx = 1;
		add(downButton,gb);
		
	}
	public void addLeftButton() {
		GridBagConstraints gb = new GridBagConstraints();
		gb.weightx = 0;
		gb.weighty = 0;
		////Left Button/////
		gb.anchor = GridBagConstraints.EAST;
		gb.gridy = 2;
		gb.gridx = 0;
		add(leftButton,gb);
	}
	public void addRightButton() {
		GridBagConstraints gb = new GridBagConstraints();
		gb.weightx = 0;
		gb.weighty = 0;
		////Right Button/////
		gb.anchor = GridBagConstraints.WEST;
		gb.gridy = 2;
		gb.gridx = 2;
		add(rightButton,gb);
	}
				
}