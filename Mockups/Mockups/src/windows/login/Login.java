package windows.login;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window.Type;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Login {

	private JFrame frmChess;
	private JPasswordField passField;
	private JTextField nameField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChess = new JFrame();
		frmChess.setTitle("Chess");
		frmChess.setType(Type.POPUP);
		frmChess.setResizable(false);
		frmChess.setAlwaysOnTop(true);
		frmChess.setBounds(100, 100, 461, 310);
		frmChess.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel login = new JPanel();
		frmChess.getContentPane().add(login, BorderLayout.CENTER);
		GridBagLayout gbl_login = new GridBagLayout();
		gbl_login.columnWidths = new int[]{99, 124, 0};
		gbl_login.rowHeights = new int[]{0, 38, 0, 46, 48, 0, 0};
		gbl_login.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_login.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		login.setLayout(gbl_login);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_1.gridx = 1;
		gbc_verticalStrut_1.gridy = 0;
		login.add(verticalStrut_1, gbc_verticalStrut_1);
		
		JLabel newUser = new JLabel("Username: ");
		newUser.setFont(new Font("Baskerville Old Face", Font.PLAIN, 15));
		GridBagConstraints gbc_newUser = new GridBagConstraints();
		gbc_newUser.anchor = GridBagConstraints.EAST;
		gbc_newUser.insets = new Insets(0, 0, 5, 5);
		gbc_newUser.gridx = 0;
		gbc_newUser.gridy = 1;
		login.add(newUser, gbc_newUser);
		
		nameField = new JTextField();
		GridBagConstraints gbc_nameField = new GridBagConstraints();
		gbc_nameField.anchor = GridBagConstraints.LINE_START;
		gbc_nameField.insets = new Insets(0, 0, 5, 0);
		gbc_nameField.gridx = 1;
		gbc_nameField.gridy = 1;
		login.add(nameField, gbc_nameField);
		nameField.setColumns(10);
		
		JLabel newPass = new JLabel("Password: ");
		newPass.setFont(new Font("Baskerville Old Face", Font.PLAIN, 15));
		GridBagConstraints gbc_newPass = new GridBagConstraints();
		gbc_newPass.insets = new Insets(0, 0, 5, 5);
		gbc_newPass.anchor = GridBagConstraints.EAST;
		gbc_newPass.gridx = 0;
		gbc_newPass.gridy = 2;
		login.add(newPass, gbc_newPass);
		
		passField = new JPasswordField();
		passField.setColumns(10);
		GridBagConstraints gbc_passField = new GridBagConstraints();
		gbc_passField.anchor = GridBagConstraints.WEST;
		gbc_passField.insets = new Insets(0, 0, 5, 0);
		gbc_passField.gridx = 1;
		gbc_passField.gridy = 2;
		login.add(passField, gbc_passField);
		
		JButton loginBtn = new JButton("Enter");
		loginBtn.setFont(new Font("Baskerville Old Face", Font.PLAIN, 21));
		GridBagConstraints gbc_loginBtn = new GridBagConstraints();
		gbc_loginBtn.insets = new Insets(0, 0, 5, 0);
		gbc_loginBtn.anchor = GridBagConstraints.WEST;
		gbc_loginBtn.gridx = 1;
		gbc_loginBtn.gridy = 4;
		login.add(loginBtn, gbc_loginBtn);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.gridx = 1;
		gbc_verticalStrut.gridy = 5;
		login.add(verticalStrut, gbc_verticalStrut);
		
		JPanel title = new JPanel();
		frmChess.getContentPane().add(title, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(UIManager.getBorder("DesktopIcon.border"));
		title.add(panel_1);
		
		JLabel loginLabel = new JLabel("Login");
		loginLabel.setFont(new Font("Baskerville Old Face", Font.PLAIN, 21));
		panel_1.add(loginLabel);
	}


}
