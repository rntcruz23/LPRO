package windows.stats;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import window.Window;
import windows.landing.LandingScreen;
import windows.lobby.Lobby;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;

public class Stats extends Window{
	private JLabel drawsValue;
	private JLabel lostValue;
	private JLabel winsValue;
	public JLabel getDrawsValue() {
		return drawsValue;
	}
	public void setDrawsValue(JLabel drawsValue) {
		this.drawsValue = drawsValue;
	}
	public JLabel getLostValue() {
		return lostValue;
	}
	public void setLostValue(JLabel lostValue) {
		this.lostValue = lostValue;
	}
	public JLabel getWinsValue() {
		return winsValue;
	}
	public void setWinsValue(JLabel winsValue) {
		this.winsValue = winsValue;
	}
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
		
		JPanel panel_1 = new JPanel();
		frmChess.setIconImage(Toolkit.getDefaultToolkit().getImage(Stats.class.getResource("FreeChessKing.png")));
		frmChess.getContentPane().add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{143, 41, 34, 45, 0};
		gbl_panel_1.rowHeights = new int[]{16, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel winsLabel = new JLabel("Wins: ");
		winsLabel.setFont(new Font("Baskerville Old Face", Font.PLAIN, 17));
		GridBagConstraints gbc_winsLabel = new GridBagConstraints();
		gbc_winsLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_winsLabel.insets = new Insets(0, 0, 5, 5);
		gbc_winsLabel.gridx = 2;
		gbc_winsLabel.gridy = 1;
		panel_1.add(winsLabel, gbc_winsLabel);
		
		winsValue = new JLabel("");
		GridBagConstraints gbc_winsValue = new GridBagConstraints();
		gbc_winsValue.insets = new Insets(0, 0, 5, 0);
		gbc_winsValue.gridx = 3;
		gbc_winsValue.gridy = 1;
		panel_1.add(winsValue, gbc_winsValue);
		
		JLabel lostLabel = new JLabel("Lost: ");
		lostLabel.setFont(new Font("Baskerville Old Face", Font.PLAIN, 17));
		GridBagConstraints gbc_lostLabel = new GridBagConstraints();
		gbc_lostLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_lostLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lostLabel.gridx = 2;
		gbc_lostLabel.gridy = 2;
		panel_1.add(lostLabel, gbc_lostLabel);
		
		lostValue = new JLabel("");
		GridBagConstraints gbc_lostValue = new GridBagConstraints();
		gbc_lostValue.insets = new Insets(0, 0, 5, 0);
		gbc_lostValue.gridx = 3;
		gbc_lostValue.gridy = 2;
		panel_1.add(lostValue, gbc_lostValue);
		
		JLabel drawLabel = new JLabel("Draws: ");
		drawLabel.setFont(new Font("Baskerville Old Face", Font.PLAIN, 17));
		GridBagConstraints gbc_drawLabel = new GridBagConstraints();
		gbc_drawLabel.insets = new Insets(0, 0, 0, 5);
		gbc_drawLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_drawLabel.gridx = 2;
		gbc_drawLabel.gridy = 3;
		panel_1.add(drawLabel, gbc_drawLabel);
		
		drawsValue = new JLabel("");
		GridBagConstraints gbc_drawsValue = new GridBagConstraints();
		gbc_drawsValue.gridx = 3;
		gbc_drawsValue.gridy = 3;
		panel_1.add(drawsValue, gbc_drawsValue);
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
