package windows.create;

import java.awt.BorderLayout;
import java.awt.Component;
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
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import window.Window;

public class CreateAcc extends Window{
	private JPasswordField passField;
	private JTextField nameField;
	private JPasswordField pwdConfirm;

	/**
	 * Create the application.
	 */
	public CreateAcc() {
		super();
		initialize();
		run();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChess.setType(Type.POPUP);
		frmChess.setResizable(false);
		frmChess.setAlwaysOnTop(true);
		frmChess.setBounds(100, 100, 461, 310);
		frmChess.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel signin = new JPanel();
		frmChess.getContentPane().add(signin, BorderLayout.CENTER);
		GridBagLayout gbl_signin = new GridBagLayout();
		gbl_signin.columnWidths = new int[]{99, 124, 0};
		gbl_signin.rowHeights = new int[]{0, 38, 0, 46, 48, 0, 0};
		gbl_signin.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_signin.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		signin.setLayout(gbl_signin);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut_1.gridx = 1;
		gbc_verticalStrut_1.gridy = 0;
		signin.add(verticalStrut_1, gbc_verticalStrut_1);
		
		JLabel newUser = new JLabel("Username: ");
		newUser.setFont(new Font("Baskerville Old Face", Font.PLAIN, 15));
		GridBagConstraints gbc_newUser = new GridBagConstraints();
		gbc_newUser.anchor = GridBagConstraints.EAST;
		gbc_newUser.insets = new Insets(0, 0, 5, 5);
		gbc_newUser.gridx = 0;
		gbc_newUser.gridy = 1;
		signin.add(newUser, gbc_newUser);
		
		nameField = new JTextField();
		GridBagConstraints gbc_nameField = new GridBagConstraints();
		gbc_nameField.anchor = GridBagConstraints.LINE_START;
		gbc_nameField.insets = new Insets(0, 0, 5, 0);
		gbc_nameField.gridx = 1;
		gbc_nameField.gridy = 1;
		signin.add(nameField, gbc_nameField);
		nameField.setColumns(10);
		
		JLabel newPass = new JLabel("Password: ");
		newPass.setFont(new Font("Baskerville Old Face", Font.PLAIN, 15));
		GridBagConstraints gbc_newPass = new GridBagConstraints();
		gbc_newPass.insets = new Insets(0, 0, 5, 5);
		gbc_newPass.anchor = GridBagConstraints.EAST;
		gbc_newPass.gridx = 0;
		gbc_newPass.gridy = 2;
		signin.add(newPass, gbc_newPass);
		
		passField = new JPasswordField();
		passField.setColumns(10);
		GridBagConstraints gbc_passField = new GridBagConstraints();
		gbc_passField.anchor = GridBagConstraints.WEST;
		gbc_passField.insets = new Insets(0, 0, 5, 0);
		gbc_passField.gridx = 1;
		gbc_passField.gridy = 2;
		signin.add(passField, gbc_passField);
		
		JLabel lblConfirmPass = new JLabel("Confirm Password: ");
		lblConfirmPass.setFont(new Font("Baskerville Old Face", Font.PLAIN, 15));
		lblConfirmPass.setVerticalAlignment(SwingConstants.BOTTOM);
		lblConfirmPass.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblConfirmPass = new GridBagConstraints();
		gbc_lblConfirmPass.anchor = GridBagConstraints.EAST;
		gbc_lblConfirmPass.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmPass.gridx = 0;
		gbc_lblConfirmPass.gridy = 3;
		signin.add(lblConfirmPass, gbc_lblConfirmPass);
		
		pwdConfirm = new JPasswordField();
		pwdConfirm.setColumns(10);
		GridBagConstraints gbc_pwdConfirm = new GridBagConstraints();
		gbc_pwdConfirm.anchor = GridBagConstraints.LINE_START;
		gbc_pwdConfirm.insets = new Insets(0, 0, 5, 0);
		gbc_pwdConfirm.gridx = 1;
		gbc_pwdConfirm.gridy = 3;
		signin.add(pwdConfirm, gbc_pwdConfirm);
		
		JButton btnSignin = new JButton("Create Account");
		btnSignin.setFont(new Font("Baskerville Old Face", Font.PLAIN, 21));
		GridBagConstraints gbc_btnSignin = new GridBagConstraints();
		gbc_btnSignin.insets = new Insets(0, 0, 5, 0);
		gbc_btnSignin.anchor = GridBagConstraints.WEST;
		gbc_btnSignin.gridx = 1;
		gbc_btnSignin.gridy = 4;
		signin.add(btnSignin, gbc_btnSignin);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.gridx = 1;
		gbc_verticalStrut.gridy = 5;
		signin.add(verticalStrut, gbc_verticalStrut);
		
		JPanel title = new JPanel();
		frmChess.getContentPane().add(title, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(UIManager.getBorder("DesktopIcon.border"));
		title.add(panel_1);
		
		JLabel label = new JLabel("New user registrarion");
		label.setFont(new Font("Baskerville Old Face", Font.PLAIN, 21));
		panel_1.add(label);
	}


}
