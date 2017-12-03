package windows.connect;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import socketsClient.Client;
import user.User;
import user.WaitingInput;
import window.Window;
import windows.landing.LandingScreen;

public class Connect extends Window{
	private JTextField ipField;
	private JTextField portField;
	private Client client;
	/**
	 * Create the application.
	 */
	public Connect(Client client) {
		initialize();
		setClient(client);
		run();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChess.setResizable(false);
		frmChess.setBounds(100, 100, 450, 300);
		frmChess.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmChess.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 55, 79, 116, 0};
		gbl_panel.rowHeights = new int[]{64, 25, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblWelcomToPawnstars = new JLabel("Welcome to Pawnstars");
		lblWelcomToPawnstars.setFont(new Font("Baskerville Old Face", Font.PLAIN, 19));
		GridBagConstraints gbc_lblWelcomToPawnstars = new GridBagConstraints();
		gbc_lblWelcomToPawnstars.gridwidth = 2;
		gbc_lblWelcomToPawnstars.insets = new Insets(0, 0, 5, 5);
		gbc_lblWelcomToPawnstars.gridx = 1;
		gbc_lblWelcomToPawnstars.gridy = 0;
		panel.add(lblWelcomToPawnstars, gbc_lblWelcomToPawnstars);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut.gridx = 3;
		gbc_verticalStrut.gridy = 0;
		panel.add(verticalStrut, gbc_verticalStrut);
		
		JLabel lblInputIpAddress = new JLabel("Input IP address: ");
		lblInputIpAddress.setFont(new Font("Baskerville Old Face", Font.PLAIN, 13));
		GridBagConstraints gbc_lblInputIpAddress = new GridBagConstraints();
		gbc_lblInputIpAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblInputIpAddress.anchor = GridBagConstraints.EAST;
		gbc_lblInputIpAddress.gridx = 2;
		gbc_lblInputIpAddress.gridy = 1;
		panel.add(lblInputIpAddress, gbc_lblInputIpAddress);
		
		ipField = new JTextField();
		GridBagConstraints gbc_ipField = new GridBagConstraints();
		gbc_ipField.anchor = GridBagConstraints.WEST;
		gbc_ipField.insets = new Insets(0, 0, 5, 0);
		gbc_ipField.gridx = 3;
		gbc_ipField.gridy = 1;
		panel.add(ipField, gbc_ipField);
		ipField.setColumns(10);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut.gridx = 0;
		gbc_horizontalStrut.gridy = 2;
		panel.add(horizontalStrut, gbc_horizontalStrut);
		
		JLabel lblInputPort = new JLabel("Input port: ");
		lblInputPort.setFont(new Font("Baskerville Old Face", Font.PLAIN, 13));
		GridBagConstraints gbc_lblInputPort = new GridBagConstraints();
		gbc_lblInputPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblInputPort.anchor = GridBagConstraints.EAST;
		gbc_lblInputPort.gridx = 2;
		gbc_lblInputPort.gridy = 2;
		panel.add(lblInputPort, gbc_lblInputPort);
		
		portField = new JTextField();
		GridBagConstraints gbc_portField = new GridBagConstraints();
		gbc_portField.insets = new Insets(0, 0, 5, 0);
		gbc_portField.anchor = GridBagConstraints.WEST;
		gbc_portField.gridx = 3;
		gbc_portField.gridy = 2;
		panel.add(portField, gbc_portField);
		portField.setColumns(10);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String ip = ipField.getText();
				String port = portField.getText();
				int portn = Integer.parseInt(port);
				client.connect(ip, portn);
				User user = new User(client);
				WaitingInput wait = new WaitingInput(user);
				client.setUser(user);
				client.setWait(wait);
				user.setType(user, "", "", new LandingScreen());
				
			}
		});
		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnConnect.gridx = 3;
		gbc_btnConnect.gridy = 3;
		panel.add(btnConnect, gbc_btnConnect);
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}

}
