package windows.newroom;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import server.SocketAPI;
import window.Window;
import windows.landing.LandingScreen;
import windows.lobby.Lobby;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateRoom extends Window{
	private JTextField textField;
	private JLabel status;
	public JLabel getStatus() {
		return status;
	}
	public void setStatus(JLabel status) {
		this.status = status;
	}
	/**
	 * Create the application.
	 */
	public CreateRoom() {
		super();
		initialize();
		run();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChess.setResizable(false);
		frmChess.setBounds(100, 100, 450, 178);
		frmChess.setIconImage(Toolkit.getDefaultToolkit().getImage(CreateRoom.class.getResource("FreeChessKing.png")));
		JPanel panel = new JPanel();
		frmChess.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 104, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 2;
		gbc_verticalStrut.gridy = 0;
		panel.add(verticalStrut, gbc_verticalStrut);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_1.gridx = 0;
		gbc_horizontalStrut_1.gridy = 1;
		panel.add(horizontalStrut_1, gbc_horizontalStrut_1);
		
		JLabel lblRoomName = new JLabel("New room name: ");
		GridBagConstraints gbc_lblRoomName = new GridBagConstraints();
		gbc_lblRoomName.insets = new Insets(0, 0, 5, 5);
		gbc_lblRoomName.anchor = GridBagConstraints.EAST;
		gbc_lblRoomName.gridx = 1;
		gbc_lblRoomName.gridy = 1;
		panel.add(lblRoomName, gbc_lblRoomName);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrut.gridx = 4;
		gbc_horizontalStrut.gridy = 1;
		panel.add(horizontalStrut, gbc_horizontalStrut);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_1.gridx = 2;
		gbc_verticalStrut_1.gridy = 2;
		panel.add(verticalStrut_1, gbc_verticalStrut_1);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String room = textField.getText();
				String query = "n "+room;
				SocketAPI.writeToSocket(getUser().getClient().getSocket(), query);
			}
		});
		
		status = new JLabel("");
		GridBagConstraints gbc_status = new GridBagConstraints();
		gbc_status.insets = new Insets(0, 0, 5, 5);
		gbc_status.gridx = 3;
		gbc_status.gridy = 2;
		panel.add(status, gbc_status);
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.anchor = GridBagConstraints.WEST;
		gbc_btnCreate.insets = new Insets(0, 0, 0, 5);
		gbc_btnCreate.gridx = 2;
		gbc_btnCreate.gridy = 3;
		panel.add(btnCreate, gbc_btnCreate);
		
		JButton btnExit = new JButton("Return to Lobby");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChess.setVisible(false);
				Lobby frmLobby = (Lobby)getUser().getBackWindow();
				getUser().setRoom(frmLobby);
			}
		});
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.insets = new Insets(0, 0, 0, 5);
		gbc_btnExit.gridx = 3;
		gbc_btnExit.gridy = 3;
		panel.add(btnExit, gbc_btnExit);
	}

}
