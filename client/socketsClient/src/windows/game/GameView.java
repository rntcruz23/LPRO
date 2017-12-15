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
import javax.swing.BoxLayout;
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
import windows.lobby.Lobby;

public class GameView extends Window{
	
	private boardView board;
	private JTextArea gameScreen;
	private JTextArea chatArea;
	private JTextArea historyArea;
	private JLabel lblGameRoom;
	private JButton btnSend;
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
				     //getUser().sendCommand(output);
						
					
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