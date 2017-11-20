package windows.stats;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import window.Window;

public class Stats extends Window{
	/**
	 * Create the application.
	 */
	public Stats() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChess = new JFrame();
		frmChess.setBounds(100, 100, 602, 388);
		frmChess.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frmChess.getContentPane().add(panel, BorderLayout.SOUTH);

		JButton btnReturnToLobby = new JButton("Return to lobby");
		panel.add(btnReturnToLobby);
	}

}
