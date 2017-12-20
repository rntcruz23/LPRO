package windows.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import server.SocketAPI;
import user.User;
import window.Window;

public class GameView extends Window{
	
	private boardView board;
	private JTextArea gameScreen;
	private JTextArea chatArea;
	private JTextArea historyArea;
	private JLabel lblGameRoom;
	private JButton btnSend;
	private JLabel joinLabel;
	private JLabel turnLabel;
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

		getFrmChess().setResizable(false);
		getFrmChess().setBounds(100, 100, 1024, 680);
		getFrmChess().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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

		frmChess.setVisible(true);
		frmChess.setResizable(false);
		frmChess.setIconImage(Toolkit.getDefaultToolkit().getImage(GameView.class.getResource("FreeChessKing.png")));
		frmChess.setTitle("Chess Game");
		frmChess.setBounds(170, 50, 1024, 680);
		frmChess.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnReturnToLobby = new JButton("Return to lobby");
		btnReturnToLobby.setBounds(20, 600, 150, 40);
		btnReturnToLobby.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChess.setVisible(false);
				//Lobby lob =  new Lobby();
				//lob.setUser(getUser());
				//getUser().setRoom(lob);
				//SocketAPI.writeToSocket(getUser().getClient().getSocket(), "x");
			}
		});
			frmChess.getContentPane().setLayout(null);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(738, 71, 270, 448);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			frmChess.getContentPane().add(scrollPane);
			
			JTextArea viewchat = new JTextArea();
			scrollPane.setViewportView(viewchat);
			viewchat.setWrapStyleWord(true);
			viewchat.setLineWrap(true);
			viewchat.setBackground(new Color(192, 192, 192));
			viewchat.setBorder(new TitledBorder(new EtchedBorder(), "Chat"));
			
			JFormattedTextField chatArea = new JFormattedTextField();
			chatArea.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {			
					String speak = chatArea.getText();		
					String output = "c "+speak;
					viewchat.setText(speak);
					chatArea.setText("");
				     getUser().sendCommand(output);

				}
			});
			chatArea.setBackground(Color.WHITE);
			chatArea.setBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(153, 153, 153)));
			chatArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
			chatArea.setBounds(738, 517, 270, 59);
			chatArea.setBorder(new TitledBorder(new EtchedBorder(), "Send Message"));
			frmChess.getContentPane().add(chatArea);
			frmChess.getContentPane().add(btnReturnToLobby);			
			
		
		JTextField txtGame = new JTextField();
		txtGame.setBounds(340, 14, 253, 46);
		txtGame.setEditable(false);
		txtGame.setFont(new Font("Wide Latin", Font.PLAIN, 37));
		txtGame.setHorizontalAlignment(SwingConstants.CENTER);
		txtGame.setBackground(SystemColor.text);
		txtGame.setText("CHESS");
		frmChess.getContentPane().add(txtGame);
		txtGame.setColumns(10);
		

	
		JLayeredPane gameScreen = new JLayeredPane();
		gameScreen.setBounds(198, 71, 530, 530);
		frmChess.getContentPane().add(gameScreen);
		gameScreen.setLayout(null);
		
		
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 75, 188, 514);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frmChess.getContentPane().add(scrollPane_1);
		
		JTextArea historyArea = new JTextArea();
		scrollPane_1.setViewportView(historyArea);
		historyArea.setWrapStyleWord(true);
		historyArea.setLineWrap(true);
		historyArea.setBackground(new Color(192, 192, 192));
		historyArea.setBorder(new TitledBorder(new EtchedBorder(), "History"));
		
			
		JLabel lblNewLabel = new JLabel("CHESS");
		lblNewLabel.setBounds(0, 0, 1008, 641);
		lblNewLabel.setIcon(new ImageIcon(GameView.class.getResource("99.jpg")));
		frmChess.getContentPane().add(lblNewLabel);
		
		boardView board=new boardView(gameScreen,historyArea);
 	   
		frmChess.setVisible(true);
		
		board.create(frmChess);
		board.Listener();
		board.InitPieces();
		
		chatArea.setEditable(false);
		scrollPane.setViewportView(chatArea);
		
		JPanel chatMenu = new JPanel();
		chatArea.add(chatMenu, BorderLayout.SOUTH);
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
		
		JTextArea speakArea = new JTextArea();
		scrollPane_2.setViewportView(speakArea);
		speakArea.setLineWrap(true);
		
		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String speak = speakArea.getText();
				String output = "c "+speak;
				speakArea.setText("");
				
				getUser().sendCommand(output);
			}
		});
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.anchor = GridBagConstraints.EAST;
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 0;
		chatMenu.add(btnSend, gbc_btnSend);
		
		JPanel game = new JPanel();
		getFrmChess().getContentPane().add(game, BorderLayout.CENTER);
		/*
		JLabel lblNewLabel = new JLabel();
		board = getScaledImage(board,500, 500);
		*/
		GridBagLayout gbl_game = new GridBagLayout();
		gbl_game.columnWidths = new int[]{572, 0};
		gbl_game.rowHeights = new int[]{508, 0};
		gbl_game.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_game.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		game.setLayout(gbl_game);
		
		GridBagConstraints gbc_gameScreen = new GridBagConstraints();
		gbc_gameScreen.fill = GridBagConstraints.BOTH;
		gbc_gameScreen.anchor = GridBagConstraints.NORTHWEST;
		gbc_gameScreen.gridx = 0;
		gbc_gameScreen.gridy = 0;
		game.add(gameScreen, gbc_gameScreen);

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
		
		JButton forfitButton = new JButton("Give Up");
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
		
		JButton drawButton = new JButton("Offer Draw");
		drawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SocketAPI.writeToSocket(getUser().getClient().getSocket(), "d");
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
		getFrmChess().getContentPane().add(title, BorderLayout.NORTH);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(UIManager.getBorder("CheckBox.border"));
		title.add(panel_2);
		
		lblGameRoom = new JLabel("Game Room");
		panel_2.add(lblGameRoom);
		lblGameRoom.setFont(new Font("Baskerville Old Face", Font.PLAIN, 21));

	}
	public void removeUserButtons() {
		btnSend.setEnabled(false);
	}
	public JTextArea getGame() {
		return gameScreen;
	}
	public boardView getBoard() {
		return board;
	}
	public void setBoard(boardView board) {
		this.board = board;
	}
}