package window;

import java.awt.EventQueue;
import java.awt.Font;

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
	public JFrame getFrmChess() {
		return frmChess;
	}
	public void setFrmChess(JFrame frmChess) {
		this.frmChess = frmChess;
	}
}