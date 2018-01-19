package window;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import user.User;

public class Window {
	protected User u;
	protected JFrame frmChess;
	public Window() {
		setFrmChess(new JFrame());
		getFrmChess().setTitle("Chess");
		frmChess.setFont(new Font("Baskerville Old Face", Font.PLAIN, 12));
		frmChess.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frmChess.setResizable(false);
	}
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmChess.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void setUser(User user) {
		u = user;
	}
	public User getUser() {
		return u;
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
	public JFrame getFrmChess() {
		return frmChess;
	}
	public void setFrmChess(JFrame frmChess) {
		this.frmChess = frmChess;
	}
}