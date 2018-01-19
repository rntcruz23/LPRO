package windows.stats;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import window.Window;
import windows.landing.LandingScreen;
import windows.lobby.Lobby;
import windows.newroom.CreateRoom;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.RenderingHints;
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
		frmChess.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
		gbc_winsLabel.gridx = 3;
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
		gbc_lostLabel.gridx = 3;
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
		gbc_drawLabel.gridx = 3;
		gbc_drawLabel.gridy = 3;
		panel_1.add(drawLabel, gbc_drawLabel);
		
		drawsValue = new JLabel("");
		GridBagConstraints gbc_drawsValue = new GridBagConstraints();
		gbc_drawsValue.gridx = 3;
		gbc_drawsValue.gridy = 3;
		panel_1.add(drawsValue, gbc_drawsValue);
		frmChess.setBounds(100, 100, 602, 388);

		JPanel panel = new JPanel();
		frmChess.getContentPane().add(panel, BorderLayout.SOUTH);

		JButton btnReturnToLobby = new JButton("Return to lobby");
		btnReturnToLobby.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChess.setVisible(false);
				Lobby frmLobby = (Lobby)getUser().getBackWindow();
				getUser().setRoom(frmLobby);
				getUser().getRoom().getFrmChess().setEnabled(true);
			}
		});
		panel.add(btnReturnToLobby);
		
		
		
		ImageIcon bnewGame=getScaledImage(createImageIcon("leadership.png"),150,150);
		JLabel LabelnewGame = new JLabel();
		GridBagConstraints gbc_LabelnewGame = new GridBagConstraints();
		gbc_LabelnewGame.insets = new Insets(0, 0, 15, 15);
		gbc_LabelnewGame.gridx = 3;
		gbc_LabelnewGame.gridy = 4;
		panel_1.add(LabelnewGame, gbc_LabelnewGame,new Integer(1));
		LabelnewGame.setIcon(bnewGame);
		
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


}
