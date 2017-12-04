package server;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Admin {

	private JFrame frame;
	private JTextField login;
	private JPasswordField pass;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private Component verticalStrut;
	private Component horizontalStrut;
	private JLabel lblAdminLogin;
	private JButton btnStartServer;
	private Server server;
	private JLabel status;
	private JLabel occupationLbl;
	private JLabel ipLbl;

	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Admin(Server server) {
		setServer(server);
		initialize();
		run();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 650, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{138, 83, 214, 214, 0};
		gbl_panel.rowHeights = new int[]{107, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		lblAdminLogin = new JLabel("Admin Login");
		lblAdminLogin.setFont(new Font("Baskerville Old Face", Font.PLAIN, 20));
		GridBagConstraints gbc_lblAdminLogin = new GridBagConstraints();
		gbc_lblAdminLogin.gridwidth = 2;
		gbc_lblAdminLogin.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdminLogin.gridx = 0;
		gbc_lblAdminLogin.gridy = 0;
		panel.add(lblAdminLogin, gbc_lblAdminLogin);

		verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 2;
		gbc_verticalStrut.gridy = 0;
		panel.add(verticalStrut, gbc_verticalStrut);

		lblUsername = new JLabel("Username: ");
		lblUsername.setFont(new Font("Baskerville Old Face", Font.PLAIN, 13));
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 1;
		panel.add(lblUsername, gbc_lblUsername);

		login = new JTextField();
		GridBagConstraints gbc_login = new GridBagConstraints();
		gbc_login.fill = GridBagConstraints.BOTH;
		gbc_login.insets = new Insets(0, 0, 5, 5);
		gbc_login.gridx = 2;
		gbc_login.gridy = 1;
		panel.add(login, gbc_login);
		login.setColumns(10);

		horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut.gridx = 0;
		gbc_horizontalStrut.gridy = 2;
		panel.add(horizontalStrut, gbc_horizontalStrut);

		lblPassword = new JLabel("Password: ");
		lblPassword.setFont(new Font("Baskerville Old Face", Font.PLAIN, 13));
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 2;
		panel.add(lblPassword, gbc_lblPassword);

		pass = new JPasswordField();
		GridBagConstraints gbc_pass = new GridBagConstraints();
		gbc_pass.fill = GridBagConstraints.BOTH;
		gbc_pass.insets = new Insets(0, 0, 5, 5);
		gbc_pass.gridx = 2;
		gbc_pass.gridy = 2;
		panel.add(pass, gbc_pass);

		JButton enter = new JButton("Login");
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String passe = new String(pass.getPassword());
				String user = login.getText();
				if(server.loginCheck(user, passe))
					btnStartServer.setEnabled(true);
				else status.setText("Invalid admin");
			}
		});
		enter.setFont(new Font("Baskerville Old Face", Font.PLAIN, 13));
		GridBagConstraints gbc_enter = new GridBagConstraints();
		gbc_enter.insets = new Insets(0, 0, 5, 5);
		gbc_enter.fill = GridBagConstraints.BOTH;
		gbc_enter.gridx = 2;
		gbc_enter.gridy = 3;
		panel.add(enter, gbc_enter);

		btnStartServer = new JButton("Start server");
		btnStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.setStarted(1);
			}
		});
		btnStartServer.setEnabled(false);
		btnStartServer.setFont(new Font("Baskerville Old Face", Font.PLAIN, 13));
		GridBagConstraints gbc_btnStartServer = new GridBagConstraints();
		gbc_btnStartServer.fill = GridBagConstraints.BOTH;
		gbc_btnStartServer.insets = new Insets(0, 0, 5, 5);
		gbc_btnStartServer.gridx = 2;
		gbc_btnStartServer.gridy = 4;
		panel.add(btnStartServer, gbc_btnStartServer);

		status = new JLabel("");
		GridBagConstraints gbc_status = new GridBagConstraints();
		gbc_status.insets = new Insets(0, 0, 5, 5);
		gbc_status.gridx = 2;
		gbc_status.gridy = 5;
		panel.add(status, gbc_status);

		ipLbl = new JLabel("");
		GridBagConstraints gbc_ipLbl = new GridBagConstraints();
		gbc_ipLbl.insets = new Insets(0, 0, 5, 5);
		gbc_ipLbl.gridx = 2;
		gbc_ipLbl.gridy = 6;
		panel.add(ipLbl, gbc_ipLbl);

		occupationLbl = new JLabel("");
		GridBagConstraints gbc_occupationLbl = new GridBagConstraints();
		gbc_occupationLbl.insets = new Insets(0, 0, 0, 5);
		gbc_occupationLbl.gridx = 2;
		gbc_occupationLbl.gridy = 7;
		panel.add(occupationLbl, gbc_occupationLbl);
	}
	public JLabel getStatus() {
		return status;
	}
	public void setStatus(JLabel status) {
		this.status = status;
	}
	public JLabel getOccupationLbl() {
		return occupationLbl;
	}
	public void setOccupationLbl(JLabel occupationLbl) {
		this.occupationLbl = occupationLbl;
	}
	public JLabel getIpLbl() {
		return ipLbl;
	}
	public void setIpLbl(JLabel ipLbl) {
		this.ipLbl = ipLbl;
	}
	public Server getServer() {
		return server;
	}
	public void setServer(Server server) {
		this.server = server;
	}
}