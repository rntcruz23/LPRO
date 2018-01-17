package windows.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import pieces.Piece;
import server.SocketAPI;
import user.Guest;
import user.Player;
import user.Spectator;
import user.User;
import window.Window;
import windows.lobby.Lobby;

public class GameView extends Window{

	private BoardView board;
	private JTextArea chatArea;
	private JTextArea historyArea;
	private JLabel lblGameRoom;
	private JButton btnSend;
	private JButton drawButton;
	private JButton forfitButton;
	private JLabel joinLabel;
	private JLabel turnLabel;
	private JTextArea speakArea;
	private JLabel lbl_white;
	private JLabel lbl_black;
	
	public JLabel getlbl_white() {
		return lbl_white;
	}
	public JLabel getlbl_black() {
		return lbl_black;
	}
	
	public JLabel getJoinLabel() {
		return joinLabel;
	}
	public void setJoinLabel(JLabel joinLabel) {
		this.joinLabel = joinLabel;
	}
	public JLabel getTurnLabel() {
		return turnLabel;
	}
	public void setTurnLabel(JLabel turnLabel) {
		this.turnLabel = turnLabel;
	}
	public JLabel getLblGameRoom() {
		return lblGameRoom;
	}
	public void setLblGameRoom(JLabel lblGameRoom) {
		this.lblGameRoom = lblGameRoom;
	}
	public JTextArea getChatArea() {
		return chatArea;
	}
	public void setChatArea(JTextArea chatArea) {
		this.chatArea = chatArea;
	}
	public JTextArea getHistoryArea() {
		return historyArea;
	}
	public void setHistoryArea(JTextArea historyArea) {
		this.historyArea = historyArea;
	}
	/**
	 * Create the application.
	 */
	public GameView(User u) {
		super();
		System.out.println("Starting room");
		setUser(u);
		initialize();
		run();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmChess.setVisible(true);
		frmChess.setResizable(false);
		frmChess.setIconImage(Toolkit.getDefaultToolkit().getImage(GameView.class.getResource("FreeChessKing.png")));
		frmChess.setTitle("Chess Game");
		frmChess.setBounds(50, 50, 1024, 700);
		

		JPanel exit = new JPanel();
		getFrmChess().getContentPane().add(exit, BorderLayout.SOUTH);
		GridBagLayout gbl_exit = new GridBagLayout();
		gbl_exit.columnWidths = new int[]{21, 125, 0, 0};
		gbl_exit.rowHeights = new int[]{19, 25, 11, 0};
		gbl_exit.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_exit.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		exit.setLayout(gbl_exit);

		Component verticalStrut_2 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_2.gridx = 1;
		gbc_verticalStrut_2.gridy = 0;
		exit.add(verticalStrut_2, gbc_verticalStrut_2);

		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 1;
		exit.add(verticalStrut, gbc_verticalStrut);

		JButton btnReturnToLobby = new JButton("Return to lobby");
		btnReturnToLobby.setBounds(20, 600, 150, 40);
		GridBagConstraints gbc_btnReturnToLobby = new GridBagConstraints();
		gbc_btnReturnToLobby.insets = new Insets(0, 0, 5, 5);
		gbc_btnReturnToLobby.anchor = GridBagConstraints.WEST;
		gbc_btnReturnToLobby.gridx = 1;
		gbc_btnReturnToLobby.gridy = 1;
		exit.add(btnReturnToLobby, gbc_btnReturnToLobby);
		btnReturnToLobby.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChess.setVisible(false);
				Lobby lob =  new Lobby();
				lob.setUser(getUser());
				getUser().setRoom(lob);
				SocketAPI.writeToSocket(getUser().getClient().getSocket(), "x");
			}
		});

		JPanel history = new JPanel();
		frmChess.getContentPane().add(history, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "History", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		history.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{128, 0};
		gbl_panel.rowHeights = new int[] {484, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panel.add(scrollPane_1, gbc_scrollPane_1);
		historyArea = new JTextArea();
		scrollPane_1.setViewportView(historyArea);
		historyArea.setWrapStyleWord(true);
		historyArea.setLineWrap(true);
		historyArea.setBackground(new Color(192, 192, 192));

		JPanel chat = new JPanel();
		frmChess.getContentPane().add(chat, BorderLayout.EAST);
		chat.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Chat", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		chat.add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] {222};
		gbl_panel_1.rowHeights = new int[]{352, 0};
		gbl_panel_1.columnWeights = new double[]{1.0};
		gbl_panel_1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel_1.add(scrollPane, gbc_scrollPane);
		
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		scrollPane.setViewportView(chatArea);
		
		JPanel chatMenu = new JPanel();
		chat.add(chatMenu, BorderLayout.SOUTH);
		GridBagLayout gbl_chatMenu = new GridBagLayout();
		gbl_chatMenu.columnWidths = new int[]{188, 87, 0};
		gbl_chatMenu.rowHeights = new int[]{57, 0};
		gbl_chatMenu.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_chatMenu.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		chatMenu.setLayout(gbl_chatMenu);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.insets = new Insets(0, 0, 0, 5);
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;
		chatMenu.add(panel_3, gbc_panel_3);
		
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{202, 0};
		gbl_panel_3.rowHeights = new int[]{57, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 0;
		gbc_scrollPane_2.gridy = 0;
		panel_3.add(scrollPane_2, gbc_scrollPane_2);
		
		speakArea = new JTextArea();
		scrollPane_2.setViewportView(speakArea);
		speakArea.setLineWrap(true);
		
		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String speak = speakArea.getText();
				speak = "c "+speak;
				speakArea.setText("");
				getUser().sendCommand(speak);
			}
		});
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.anchor = GridBagConstraints.EAST;
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 0;
		chatMenu.add(btnSend, gbc_btnSend);

		joinLabel = new JLabel("");
		GridBagConstraints gbc_joinLabel = new GridBagConstraints();
		gbc_joinLabel.insets = new Insets(0, 0, 5, 5);
		gbc_joinLabel.gridx = 4;
		gbc_joinLabel.gridy = 1;
		exit.add(joinLabel, gbc_joinLabel);

		turnLabel = new JLabel("");
		GridBagConstraints gbc_turnLabel = new GridBagConstraints();
		gbc_turnLabel.insets = new Insets(0, 0, 5, 5);
		gbc_turnLabel.gridx = 14;
		gbc_turnLabel.gridy = 1;
		exit.add(turnLabel, gbc_turnLabel);

		forfitButton = new JButton("Give Up");
		forfitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getUser().sendCommand("f");
			}
		});
		GridBagConstraints gbc_forfitButton = new GridBagConstraints();
		gbc_forfitButton.insets = new Insets(0, 0, 5, 5);
		gbc_forfitButton.gridx = 23;
		gbc_forfitButton.gridy = 1;
		exit.add(forfitButton, gbc_forfitButton);

		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_3 = new GridBagConstraints();
		gbc_horizontalStrut_3.gridwidth = 8;
		gbc_horizontalStrut_3.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_3.gridx = 6;
		gbc_horizontalStrut_3.gridy = 1;
		exit.add(horizontalStrut_3, gbc_horizontalStrut_3);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.gridwidth = 6;
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut.gridx = 17;
		gbc_horizontalStrut.gridy = 1;
		exit.add(horizontalStrut, gbc_horizontalStrut);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_1.gridx = 24;
		gbc_horizontalStrut_1.gridy = 1;
		exit.add(horizontalStrut_1, gbc_horizontalStrut_1);

		drawButton = new JButton("Offer Draw");
		drawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getUser().sendCommand("d");
			}
		});
		GridBagConstraints gbc_drawButton = new GridBagConstraints();
		gbc_drawButton.insets = new Insets(0, 0, 5, 5);
		gbc_drawButton.gridx = 25;
		gbc_drawButton.gridy = 1;
		exit.add(drawButton, gbc_drawButton);

		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_2 = new GridBagConstraints();
		gbc_horizontalStrut_2.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut_2.gridx = 26;
		gbc_horizontalStrut_2.gridy = 1;
		exit.add(horizontalStrut_2, gbc_horizontalStrut_2);

		Component verticalStrut_3 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_3 = new GridBagConstraints();
		gbc_verticalStrut_3.insets = new Insets(0, 0, 0, 5);
		gbc_verticalStrut_3.gridx = 1;
		gbc_verticalStrut_3.gridy = 2;
		exit.add(verticalStrut_3, gbc_verticalStrut_3);

		JPanel title = new JPanel();
		frmChess.getContentPane().add(title, BorderLayout.NORTH);
		
	   
		//panel_2.setBorder(new MatteBorder(2, 2, 2, 2, (Color) Color.BLACK));
		
		
		lbl_white = new JLabel();
		lbl_black = new JLabel();
		lbl_white.setOpaque(true);
		lbl_black.setOpaque(true);
		
		lbl_white.setBackground(Color.WHITE);
		lbl_white.setForeground(Color.BLACK);
		
		lbl_black.setBackground(Color.BLACK);
		lbl_black.setForeground(Color.WHITE);
		
		//g1.getPanel_2().setBorder(new MatteBorder(2, 2, 2, 2, (Color) Color.BLACK));
		
		title.add(lbl_white,BorderLayout.WEST);
		title.add(lbl_black,BorderLayout.EAST);
		
		lbl_white.setFont(new Font("Baskerville Old Face", Font.PLAIN, 32));
		lbl_black.setFont(new Font("Baskerville Old Face", Font.PLAIN, 32));

		
		JPanel game = new JPanel();
		getFrmChess().getContentPane().add(game, BorderLayout.CENTER);
		GridBagLayout gbl_game = new GridBagLayout();
		gbl_game.columnWidths = new int[]{530, 0};
		gbl_game.rowHeights = new int[]{530, 0};
		gbl_game.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_game.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		game.setLayout(gbl_game);
		
		JLayeredPane gameScreen = new JLayeredPane();
		gameScreen.setBounds(198, 71, 530, 530);
		GridBagConstraints gbc_gameScreen = new GridBagConstraints();
		gbc_gameScreen.fill = GridBagConstraints.BOTH;
		gbc_gameScreen.anchor = GridBagConstraints.NORTHWEST;
		gbc_gameScreen.gridx = 0;
		gbc_gameScreen.gridy = 0;
		
		board = new BoardView(gameScreen,this);
		board.create(frmChess);
		board.listener();
		board.initPieces();
		game.add(gameScreen, gbc_gameScreen);
		
		/*
		frmChess.getContentPane().setLayout(null);
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(0, 0, 1350, 800);
		lblNewLabel.setIcon(new ImageIcon(GameView.class.getResource("99.jpg")));
		frmChess.getContentPane().add(lblNewLabel);	
		*/
	}
	public void removeUserButtons() {
		btnSend.setEnabled(false);
	}
	public void removePlayerButtons() {
		drawButton.setEnabled(false);
		forfitButton.setEnabled(false);
	}
	public void enablePlayerButtons() {
		drawButton.setEnabled(true);
		forfitButton.setEnabled(true);
	}
	public BoardView getBoard() {
		return board;
	}
	public void setBoard(BoardView board) {
		this.board = board;
	}
	public void manageButtons() {
		if(getUser().getClass() == (new Spectator()).getClass())
			removePlayerButtons();
		else if (getUser().getClass() == (new Guest()).getClass()) {
			removePlayerButtons();
			removeUserButtons();
		}
	}
}