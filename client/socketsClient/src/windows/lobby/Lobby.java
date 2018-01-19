package windows.lobby;

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
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import server.SocketAPI;
import window.Window;
import windows.joinroom.JoinRoom;
import windows.landing.LandingScreen;
import windows.newroom.CreateRoom;
import windows.stats.Stats;

public class Lobby extends Window{
	private JTextArea textArea;
	private JButton btnStats;
	private JButton btnNewRoom;
	private JButton btnLogout;
	private JTable table_1;
	
	
	public JTable table() {
		return table_1;
	}
	
	public JTextArea getTextArea() {
		return textArea;
	}
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
	/**
	 * Create the application.
	 */
	public Lobby() {
		super();
		initialize();
		run();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChess.setResizable(false);
		frmChess.setBounds(100, 100, 763, 658);
		frmChess.setIconImage(Toolkit.getDefaultToolkit().getImage(Lobby.class.getResource("FreeChessKing.png")));
		
		
		JPanel roomsPanel = new JPanel();
		roomsPanel.setBorder(new TitledBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, new Color(192, 192, 192), new Color(192, 192, 192)), new LineBorder(new Color(0, 0, 0))), "Room List", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frmChess.getContentPane().add(roomsPanel, BorderLayout.WEST);
		roomsPanel.setLayout(new BoxLayout(roomsPanel, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		roomsPanel.add(scrollPane);
		
		table_1 = new JTable();
		table_1.setEnabled(false);
		table_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		DefaultTableModel model=new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Name Room", "Player", "Spectator", "Guest"
				}
			);
		
		table_1.setModel(model);
		
		scrollPane.setViewportView(table_1);
		
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
		gbc_horizontalStrut.gridx = 1;
		gbc_horizontalStrut.gridy = 1;
		panel_1.add(horizontalStrut, gbc_horizontalStrut);
		
		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChess.setVisible(false);
				LandingScreen frmLanding = new LandingScreen();
				frmLanding.setUser(getUser());
				getUser().setRoom(frmLanding);
				frmLanding.getFrmChess().setVisible(true);
				SocketAPI.writeToSocket(getUser().getClient().getSocket(), "x");
			}
		});
		GridBagConstraints gbc_btnLogout = new GridBagConstraints();
		gbc_btnLogout.insets = new Insets(0, 0, 5, 0);
		gbc_btnLogout.anchor = GridBagConstraints.WEST;
		gbc_btnLogout.gridx = 1;
		gbc_btnLogout.gridy = 1;
		panel_1.add(btnLogout, gbc_btnLogout);
		
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.gridx = 1;
		gbc_verticalStrut.gridy = 0;
		panel_1.add(verticalStrut, gbc_verticalStrut);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(UIManager.getBorder("CheckBox.border"));
		frmChess.getContentPane().add(panel_2, BorderLayout.EAST);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{85, 75, 50, 0};
		gbl_panel_2.rowHeights = new int[]{177, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JTextField textField = new JTextField();
		textField.setEditable(false);
		textField.setFont(new Font("Tahoma", Font.BOLD, 14));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setForeground(Color.RED);
		textField.setBackground(SystemColor.menu);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 5;
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 0;
		panel_2.add(textField, gbc_textField);
		textField.setColumns(10);
	
		
		
		ImageIcon bStats=getScaledImage(createImageIcon("button_stats.png"),120,50);
		JLabel LabelStats = new JLabel();
		GridBagConstraints gbc_LabelStats = new GridBagConstraints();
		gbc_LabelStats.insets = new Insets(0, 0, 15, 15);
		gbc_LabelStats.gridx = 1;
		gbc_LabelStats.gridy = 1;
		panel_2.add(LabelStats, gbc_LabelStats,new Integer(1));
		LabelStats.setIcon(bStats);
		LabelStats.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Stats frmStats = new Stats();
				frmStats.setUser(getUser());
				getUser().setBackWindow(getUser().getRoom());
				getUser().setRoom(frmStats);
				frmStats.getFrmChess().setVisible(true);
				SocketAPI.writeToSocket(getUser().getClient().getSocket(),"y");
					
			}
		});
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
		gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_2.gridx = 1;
		gbc_verticalStrut_2.gridy = 2;
		panel_2.add(verticalStrut_2, gbc_verticalStrut_2);
		
		
		ImageIcon bJoin=getScaledImage(createImageIcon("button_join.png"),120,50);
		JLabel LabelJjoin = new JLabel();
		GridBagConstraints gbc_LabelJjoin = new GridBagConstraints();
		gbc_LabelJjoin.insets = new Insets(0, 0, 15, 15);
		gbc_LabelJjoin.gridx = 1;
		gbc_LabelJjoin.gridy = 3;
		panel_2.add(LabelJjoin, gbc_LabelJjoin,new Integer(1));
		LabelJjoin.setIcon(bJoin);
		LabelJjoin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int i = table_1.getSelectedRow();
				
				if(i>=0) {
				String room =(String) table_1.getValueAt(i, 0);
				String query = "j "+room;
				SocketAPI.writeToSocket(getUser().getClient().getSocket(), query);
				}
				else {
					textField.setText("Select a room!");	
				}
					
			}
		});

			
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_3 = new GridBagConstraints();
		gbc_verticalStrut_3.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_3.gridx = 1;
		gbc_verticalStrut_3.gridy = 4;
		panel_2.add(verticalStrut_3, gbc_verticalStrut_3);
		
		ImageIcon bnewGame=getScaledImage(createImageIcon("button_newgame.png"),120,50);
		JLabel LabelnewGame = new JLabel();
		GridBagConstraints gbc_LabelnewGame = new GridBagConstraints();
		gbc_LabelnewGame.insets = new Insets(0, 0, 15, 15);
		gbc_LabelnewGame.gridx = 1;
		gbc_LabelnewGame.gridy = 5;
		panel_2.add(LabelnewGame, gbc_LabelnewGame,new Integer(1));
		LabelnewGame.setIcon(bnewGame);
		LabelnewGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				CreateRoom frmNewRoom = new CreateRoom();
				frmNewRoom.setUser(getUser());
				getUser().setBackWindow(getUser().getRoom());
				getUser().setRoom(frmNewRoom);
				frmNewRoom.getFrmChess().setVisible(true);
					
			}
		});
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_4 = new GridBagConstraints();
		gbc_verticalStrut_4.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_4.gridx = 1;
		gbc_verticalStrut_4.gridy = 6;
		panel_2.add(verticalStrut_4, gbc_verticalStrut_4);
		
		ImageIcon brefresh=getScaledImage(createImageIcon("refresh.png"),60,50);
		JLabel Labelrefresh = new JLabel();
		GridBagConstraints gbc_Labelrefresh = new GridBagConstraints();
		gbc_Labelrefresh.insets = new Insets(0, 0, 0, 5);
		gbc_Labelrefresh.gridx = 1;
		gbc_Labelrefresh.gridy = 7;
		panel_2.add(Labelrefresh, gbc_Labelrefresh,new Integer(1));
		Labelrefresh.setIcon(brefresh);
		Labelrefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				SocketAPI.writeToSocket(getUser().getClient().getSocket(), "u");
			}
		});

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
	public void removeUserButtons() {
		btnStats.setEnabled(false);
		btnNewRoom.setEnabled(false);
		btnLogout.setText("Exit");
	}
}