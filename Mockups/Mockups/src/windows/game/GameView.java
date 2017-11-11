package windows.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class GameView {

	private JFrame frmChess;
	private ImageIcon board;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameView window = new GameView();
					window.frmChess.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameView() {
		board = createImageIcon("/images/chessboard.jpg");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChess = new JFrame();
		frmChess.setTitle("Chess");
		frmChess.setBounds(100, 100, 1024, 680);
		frmChess.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel exit = new JPanel();
		frmChess.getContentPane().add(exit, BorderLayout.SOUTH);
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
		btnReturnToLobby.setFont(new Font("Baskerville Old Face", Font.PLAIN, 15));
		GridBagConstraints gbc_btnReturnToLobby = new GridBagConstraints();
		gbc_btnReturnToLobby.insets = new Insets(0, 0, 5, 5);
		gbc_btnReturnToLobby.anchor = GridBagConstraints.WEST;
		gbc_btnReturnToLobby.gridx = 1;
		gbc_btnReturnToLobby.gridy = 1;
		exit.add(btnReturnToLobby, gbc_btnReturnToLobby);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 0, 5);
		gbc_verticalStrut_1.gridx = 1;
		gbc_verticalStrut_1.gridy = 2;
		exit.add(verticalStrut_1, gbc_verticalStrut_1);
		
		JPanel history = new JPanel();
		frmChess.getContentPane().add(history, BorderLayout.WEST);
		history.setLayout(new BoxLayout(history, BoxLayout.X_AXIS));
		
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
		
		JTextArea historyArea = new JTextArea();
		scrollPane_1.setViewportView(historyArea);
		historyArea.setEditable(false);
		historyArea.setColumns(15);
		
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
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
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
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane_2.setViewportView(textArea_1);
		textArea_1.setLineWrap(true);
		
		JButton btnSend = new JButton("Send");
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.anchor = GridBagConstraints.EAST;
		gbc_btnSend.gridx = 1;
		gbc_btnSend.gridy = 0;
		chatMenu.add(btnSend, gbc_btnSend);
		
		JPanel game = new JPanel();
		frmChess.getContentPane().add(game, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel();
		board = getScaledImage(board,500, 500);
		lblNewLabel.setIcon(board);
		game.add(lblNewLabel);
		
		JPanel title = new JPanel();
		frmChess.getContentPane().add(title, BorderLayout.NORTH);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(UIManager.getBorder("CheckBox.border"));
		title.add(panel_2);
		
		JLabel lblGameRoom = new JLabel("Game Room");
		panel_2.add(lblGameRoom);
		lblGameRoom.setFont(new Font("Baskerville Old Face", Font.PLAIN, 21));
	}
	private ImageIcon createImageIcon(String path) {
		java.net.URL imgUrl = getClass().getResource(path);
		if(imgUrl != null)
			return new ImageIcon(imgUrl);
		System.out.println("No file path " + path);
		return null;	
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
}