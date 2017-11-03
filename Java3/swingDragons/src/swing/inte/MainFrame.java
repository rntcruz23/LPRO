package swing.inte;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

import dkeep.logic.Map;

public class MainFrame extends JFrame {
	public GamePanel screen;
	public TextPanel text;
	public ButtonsPanel buttons;
	public SetupPanel setup;
	public boolean move;
	public Map game;
	public MainFrame(String title,Map game) {
		super(title);
		move = false;
		this.game = game;
		setLayout(new BorderLayout());

		screen =  new GamePanel();
		text = new TextPanel();
		buttons = new ButtonsPanel(this);
		setup = new SetupPanel();
		
		Container c = getContentPane();
		c.add(screen, BorderLayout.CENTER);
		c.add(text,BorderLayout.SOUTH);
		c.add(buttons,BorderLayout.EAST);
		c.add(setup,BorderLayout.NORTH);

		//////Action Listeners///////
		
		setup.moveDropDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			        String movement = (String)setup.moveDropDown.getSelectedItem();
			        if(game.gameStarted) return ;
			        if(movement.equals("Random")) {
			        	game.setMove(1);
			        	text.status.setText("Movement set to random");
			        }
		        	else if(movement.equals("Sleep")) {
			        	game.setMove(0);
			        	text.status.setText("Movement set to sleep");
		        	}
	        	}
		});	
	}
	public void exitFunc() {
		System.exit(0);
	}
	public void newGameFunc() {
		int number;
		try {
			number =  Integer.parseInt(setup.dragonsField.getText());
		}
		catch(Exception e) {
			text.status.setText("Invalid Input");
			return ;
		}
		if(number > 0 && number < 15) {
			game.resetGame();
			game.newGame(number);
			String movement = (String)setup.moveDropDown.getSelectedItem();
			if(movement.equals("Random"))
				game.setMove(1);
			else game.setMove(0);
			addPlayButtons();
			screen.updateScreen(game);
			text.status.setText("You can now play, good luck!");
			return;
		}
		text.status.setText("Invalid number of dragons: [1 14]");
	}
	public void upFunc() {
		moveFunc("w");
	}
	public void downFunc() {
		moveFunc("s");
	}
	public void rightFunc() {
		moveFunc("d");
	}
	public void leftFunc() {
		moveFunc("a");
	}
	public void moveFunc(String dir) {
		int[] next = game.getPos(dir, game);
		move = game.testMove(game.getHero(),next);
		text.status.setText(game.getOutput());
		if(game.testWin())	removePlayButtons();
		screen.updateScreen(game);
	}
	public void dragonTurn() {
		game.validDragons();
		if(!move) return;
		if(game.testGame()) {
			text.status.setText("You lose!");
			removePlayButtons();			
		}
		game.updateDragons();
		game.validDragons();
		screen.updateScreen(game);
		if(game.testGame()) {
			text.status.setText("You lose!");
			removePlayButtons();
		}
	}
	public void removePlayButtons() {
		buttons.removeUpButton();
		buttons.removeDownButton();
		buttons.removeLeftButton();
		buttons.removeRightButton();
	}
	public void addPlayButtons() {
		buttons.addUpButton();
		buttons.addDownButton();
		buttons.addLeftButton();
		buttons.addRightButton();
		
	}
}