package windows.drawprompt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import server.SocketAPI;
import user.User;
import window.Window;
import java.awt.Component;
import java.awt.Graphics2D;

import javax.swing.Box;
import javax.swing.ImageIcon;

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
		getFrmChess().getContentPane().add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{50, 50, 50, 50, 0};
		gbl_panel.rowHeights = new int[]{60, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		
		
		JPanel panel_2 = new JPanel();
		getFrmChess().getContentPane().add(panel_2, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		 gbl_panel_2.columnWidths = new int[]{50, 50, 50, 50, 0};
		 gbl_panel_2.rowHeights = new int[]{60, 0, 0, 0};
		 gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		 gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout( gbl_panel_2);


		JLabel drawOffer = new JLabel("Your opponent is offering a draw, do you accept?");
		GridBagConstraints gbc_drawOffer = new GridBagConstraints();
		gbc_drawOffer.gridwidth = 3;
		gbc_drawOffer.insets = new Insets(0, 0, 5, 0);
		gbc_drawOffer.gridx = 1;
		gbc_drawOffer.gridy = 0;
		panel.add(drawOffer, gbc_drawOffer);

		JButton btnNo = new JButton("No");
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmChess.setVisible(false);
				getUser().setRoom(getUser().getBackWindow());
			}
		});
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
		gbc_btnYes.gridy = 2;
		panel.add(btnYes, gbc_btnYes);
		
				
		GridBagConstraints gbc_btnNo = new GridBagConstraints();
		gbc_btnYes.insets = new Insets(0, 0, 0, 5);
		gbc_btnNo.gridx =3;
		gbc_btnNo.gridy = 1;
		panel.add(btnNo, gbc_btnNo);
		
		
		ImageIcon bnewGame=getScaledImage(createImageIcon("gestures.png"),150,150);
		JLabel LabelnewGame = new JLabel();
		GridBagConstraints gbc_LabelnewGame = new GridBagConstraints();
		gbc_LabelnewGame.insets = new Insets(0, 0, 15, 15);
		gbc_LabelnewGame.gridx = 3;
		gbc_LabelnewGame.gridy = 1;
		panel_2.add(LabelnewGame, gbc_LabelnewGame);
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