package windows.drawprompt;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import server.SocketAPI;
import user.User;
import window.Window;
import windows.landing.LandingScreen;

public class Draw extends Window {
	/**
	 * Create the application.
	 */
	public Draw(User u) {
		super();
		initialize();
		setUser(u);
		run();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		getFrmChess().setResizable(false);
		getFrmChess().setBounds(100, 100, 450, 300);
		frmChess.setIconImage(Toolkit.getDefaultToolkit().getImage(Draw.class.getResource("FreeChessKing.png")));
		
		JPanel panel = new JPanel();
		getFrmChess().getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 122, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel drawOffer = new JLabel("Your opponent is offering a draw, do you accept?");
		GridBagConstraints gbc_drawOffer = new GridBagConstraints();
		gbc_drawOffer.gridwidth = 2;
		gbc_drawOffer.insets = new Insets(0, 0, 5, 0);
		gbc_drawOffer.gridx = 2;
		gbc_drawOffer.gridy = 1;
		panel.add(drawOffer, gbc_drawOffer);
		
		JButton btnYes = new JButton("Yes");
		btnYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChess.setVisible(false);
				getUser().setRoom(getUser().getBackWindow());
				SocketAPI.writeToSocket(getUser().getClient().getSocket(), "a");
			}
		});
		GridBagConstraints gbc_btnYes = new GridBagConstraints();
		gbc_btnYes.insets = new Insets(0, 0, 0, 5);
		gbc_btnYes.gridx = 2;
		gbc_btnYes.gridy = 3;
		panel.add(btnYes, gbc_btnYes);
		
		JButton btnNo = new JButton("No");
		btnYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getUser().setRoom(getUser().getBackWindow());
				frmChess.setVisible(false);
				
			}
		});
		GridBagConstraints gbc_btnNo = new GridBagConstraints();
		gbc_btnNo.gridx = 3;
		gbc_btnNo.gridy = 3;
		panel.add(btnNo, gbc_btnNo);
	}
}