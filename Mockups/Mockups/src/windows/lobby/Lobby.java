package windows.lobby;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import windows.game.GameView;
import windows.landing.LandingScreen;
import windows.newroom.CreateRoom;
import windows.stats.Stats;

public class Lobby {

	private JFrame frmChess;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Lobby window = new Lobby();
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
	public Lobby() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChess = new JFrame();
		frmChess.setTitle("Chess");
		frmChess.setResizable(false);
		frmChess.setBounds(100, 100, 963, 658);
		frmChess.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel roomsPanel = new JPanel();
		roomsPanel.setBorder(new TitledBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, new Color(192, 192, 192), new Color(192, 192, 192)), new LineBorder(new Color(0, 0, 0))), "Room List", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frmChess.getContentPane().add(roomsPanel, BorderLayout.WEST);
		roomsPanel.setLayout(new BoxLayout(roomsPanel, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		roomsPanel.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		textArea.setColumns(50);
		
		JPanel title = new JPanel();
		frmChess.getContentPane().add(title, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel.setBorder(UIManager.getBorder("CheckBox.border"));
		title.add(panel);
		
		JLabel lblLobby = new JLabel("Lobby");
		panel.add(lblLobby);
		lblLobby.setFont(new Font("Baskerville Old Face", Font.PLAIN, 21));
		
		JPanel panel_1 = new JPanel();
		frmChess.getContentPane().add(panel_1, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 71, 0};
		gbl_panel_1.rowHeights = new int[]{22, 25, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut.gridx = 0;
		gbc_horizontalStrut.gridy = 1;
		panel_1.add(horizontalStrut, gbc_horizontalStrut);
		
		JButton btnLogout = new JButton("Logout");
		GridBagConstraints gbc_btnLogout = new GridBagConstraints();
		gbc_btnLogout.insets = new Insets(0, 0, 5, 0);
		gbc_btnLogout.anchor = GridBagConstraints.WEST;
		gbc_btnLogout.gridx = 1;
		gbc_btnLogout.gridy = 1;
		panel_1.add(btnLogout, gbc_btnLogout);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.gridx = 1;
		gbc_verticalStrut.gridy = 2;
		panel_1.add(verticalStrut, gbc_verticalStrut);
		
		JPanel panel_2 = new JPanel();
		frmChess.getContentPane().add(panel_2, BorderLayout.EAST);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{120, 137, 99, 0};
		gbl_panel_2.rowHeights = new int[]{177, 0, 0, 0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_1.gridx = 1;
		gbc_verticalStrut_1.gridy = 0;
		panel_2.add(verticalStrut_1, gbc_verticalStrut_1);
		
		JButton btnStats = new JButton("Stats");
		btnStats.setFont(new Font("Baskerville Old Face", Font.PLAIN, 15));
		GridBagConstraints gbc_btnStats = new GridBagConstraints();
		gbc_btnStats.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnStats.insets = new Insets(0, 0, 5, 5);
		gbc_btnStats.gridx = 1;
		gbc_btnStats.gridy = 1;
		panel_2.add(btnStats, gbc_btnStats);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_2.gridx = 1;
		gbc_verticalStrut_2.gridy = 2;
		panel_2.add(verticalStrut_2, gbc_verticalStrut_2);
		
		JButton btnJoinGame = new JButton("Join Game");
		btnJoinGame.setFont(new Font("Baskerville Old Face", Font.PLAIN, 15));
		GridBagConstraints gbc_btnJoinGame = new GridBagConstraints();
		gbc_btnJoinGame.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnJoinGame.insets = new Insets(0, 0, 5, 5);
		gbc_btnJoinGame.gridx = 1;
		gbc_btnJoinGame.gridy = 3;
		panel_2.add(btnJoinGame, gbc_btnJoinGame);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_3 = new GridBagConstraints();
		gbc_verticalStrut_3.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_3.gridx = 1;
		gbc_verticalStrut_3.gridy = 4;
		panel_2.add(verticalStrut_3, gbc_verticalStrut_3);
		
		JButton btnNewRoom = new JButton("New Room");
		btnNewRoom.setFont(new Font("Baskerville Old Face", Font.PLAIN, 15));
		GridBagConstraints gbc_btnNewRoom = new GridBagConstraints();
		gbc_btnNewRoom.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewRoom.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewRoom.gridx = 1;
		gbc_btnNewRoom.gridy = 5;
		panel_2.add(btnNewRoom, gbc_btnNewRoom);
		
		btnJoinGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChess.setVisible(false);
				GameView frmGame = new GameView();
				frmGame.getFrame().setVisible(true);
			}
		});
		
		btnStats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChess.setVisible(false);
				Stats frmStats = new Stats();
				frmStats.getFrame().setVisible(true);
			}
		});
		
		btnNewRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateRoom frmNewRoom = new CreateRoom();
				frmNewRoom.getFrame().setVisible(true);
			}
		});
		
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChess.setVisible(false);
				LandingScreen frmLanding = new LandingScreen();
				frmLanding.getFrame().setVisible(true);
			}
		});
	}
	public JFrame getFrame() {
		return frmChess;
	}
}
