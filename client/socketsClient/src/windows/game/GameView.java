package windows.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.ImageIcon;
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
import javax.swing.text.DefaultCaret;

import pieces.Piece;
import user.Guest;
import user.Spectator;
import user.User;
import window.Window;
import windows.lobby.Lobby;

public class GameView extends Window{

	private BoardView board;
	private JTextArea chatArea;
	private JTextArea historyArea;
	private JLabel lblGameRoom;
	private JButton drawButton;
	private JButton forfitButton;
	private JLabel joinLabel;
	private JLabel turnLabel;
	private JTextArea speakArea;
	private JLabel lblWhite;
	private JLabel lblBlack;
	private JLabel lblNextPlayer;
	private JLabel LabelcheckMate; 
	private JLabel Labelcheck; 
	/**
	 * Create the application.
	 */
	public JLabel getLabelcheckMate() {
		return LabelcheckMate;
	}
	
	public JLabel getLabelcheck() {
		return Labelcheck;
	}
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
				getUser().sendCommand("x");
				Lobby lob =  new Lobby();
				lob.setUser(getUser());
				getUser().setRoom(lob);
				if (getUser().getClass() == (new Guest()).getClass())
					lob.removeUserButtons();
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
		historyArea.setEditable(false);

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
		DefaultCaret caret = (DefaultCaret) chatArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
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
		speakArea.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				if (getUser().getClass() == (new Guest().getClass())) return;
				if (key == KeyEvent.VK_ENTER) { 
					String speak = speakArea.getText();
					speak = "c "+speak;
					speakArea.setText("");
					getUser().sendCommand(speak);
				}		
			}});

		ImageIcon checkMate=getScaledImage(createImageIcon("checkmate.png"),60,50);
		LabelcheckMate = new JLabel();
		GridBagConstraints gbc_LabelcheckMate = new GridBagConstraints();
		gbc_LabelcheckMate.insets = new Insets(0, 0, 0, 0);
		gbc_LabelcheckMate.gridx = 3;
		gbc_LabelcheckMate.gridy = 1;
		exit.add(LabelcheckMate, gbc_LabelcheckMate,new Integer(1));
		LabelcheckMate.setIcon(checkMate);
		
		ImageIcon check=getScaledImage(createImageIcon("check.png"),60,50);
		 Labelcheck = new JLabel();
		GridBagConstraints gbc_Labelcheck = new GridBagConstraints();
		 gbc_Labelcheck.insets = new Insets(0, 0, 0, 200);
		 gbc_Labelcheck.gridx = 3;
		 gbc_Labelcheck.gridy = 1;
		exit.add(Labelcheck, gbc_Labelcheck,new Integer(1));
		Labelcheck.setIcon(check);
	
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

		lblWhite = new JLabel();
		lblBlack = new JLabel();
		lblNextPlayer = new JLabel();
		lblWhite.setOpaque(true);
		lblBlack.setOpaque(true);
		lblNextPlayer.setOpaque(true);


		lblWhite.setBackground(Color.WHITE);
		lblWhite.setForeground(Color.BLACK);
		lblNextPlayer.setForeground(Color.BLACK);

		lblBlack.setBackground(Color.BLACK);
		lblWhite.setBorder(new MatteBorder(2, 2, 2, 2, (Color) Color.WHITE));
		lblBlack.setForeground(Color.WHITE);
		lblBlack.setBorder(new MatteBorder(2, 2, 2, 2, (Color) Color.BLACK));

		lblNextPlayer.setBackground(Color.gray);
		lblNextPlayer.setBorder(new MatteBorder(2, 2, 2, 2, (Color) Color.gray));

		title.add(lblWhite,BorderLayout.WEST);
		title.add(lblBlack,BorderLayout.EAST);
		title.add(lblNextPlayer,BorderLayout.PAGE_START);

		lblWhite.setFont(new Font("Baskerville Old Face", Font.PLAIN, 32));
		lblBlack.setFont(new Font("Baskerville Old Face", Font.PLAIN, 32));
		lblNextPlayer.setFont(new Font("Baskerville Old Face", Font.PLAIN, 32));

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
		}
	}
	public void managePlayerLabels(Piece.color turn,String whitePlayer,String blackPlayer,String nextPlayer) {
		getLblWhite().setText(whitePlayer);
		getLblBlack().setText(blackPlayer);
		getLblNextPlayer().setText(nextPlayer);
		if (turn == Piece.color.white) {
			getLblWhite().setBorder(new MatteBorder(2, 2, 2, 2, (Color) Color.RED));
			getLblBlack().setBorder(null);
		}
		else {
			getLblWhite().setBorder(null);
			getLblBlack().setBorder(new MatteBorder(2, 2, 2, 2, (Color) Color.RED));
		}

	}

	public JLabel getLblNextPlayer() {
		return lblNextPlayer;
	}
	public JLabel getLblWhite() {
		return lblWhite;
	}
	public JLabel getLblBlack() {
		return lblBlack;
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
	private ImageIcon getScaledImage(ImageIcon srcImgIcon, int w, int h){
		Image srcImg = srcImgIcon.getImage();
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();
	    
	    return new ImageIcon(resizedImg);
	}	
	private ImageIcon createImageIcon(String path) {
		java.net.URL imgUrl = getClass().getResource(path);
		if(imgUrl != null)
			return new ImageIcon(imgUrl);
		System.out.println("No file path " + path);
		return null;	
	}
}