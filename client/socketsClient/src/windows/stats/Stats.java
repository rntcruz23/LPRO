package windows.stats;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import window.Window;
import windows.lobby.Lobby;

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
		frmChess.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		frmChess.getContentPane().add(panel, BorderLayout.SOUTH);

		JButton btnReturnToLobby = new JButton("Return to lobby");
		btnReturnToLobby.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChess.setVisible(false);
				Lobby frmLobby = (Lobby)getUser().getBackWindow();
				getUser().setRoom(frmLobby);
			}
		});
		panel.add(btnReturnToLobby);
	}

}
