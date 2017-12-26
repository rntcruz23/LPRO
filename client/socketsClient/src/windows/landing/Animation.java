package windows.landing;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Animation extends JPanel implements Runnable {
	
	ImageIcon image =createImageIcon("crown.png"); 
	
	private int degrees;
	private int x=0;
	private int y=0;
	private Timer timer;
	private int DELAY=100;
	private boolean stop=false;
	private int i=0;
	
	public Animation() {
		
		init();
		
	}
	
	private void init() {
		
		setBounds(110, 100, 300, 300);
		//setOpaque(false);
		//setBackground(new Color(211, 211, 211));
		
		if(image==null) System.out.println("Erro ao carregar Imagem\n");
		
		
		timer	=	new	Timer(DELAY, new	ActionListener(){	
			public	void	actionPerformed(ActionEvent	e)	{					
				move();			
			}								
});
		timer.start(); 	
		
	}
	
	 @Override
	public void run() {	
		 if(stop) {
			 System.out.println("stop");
		 timer.stop();
		 }
				
	 }
		
	 
	 public void move() {
	
				x+=10;
				degrees+=5;
				repaint();
				
								
	 }				
	 
	 
	 public void stop(){
		 System.out.println("stop");
		  stop=true;
			 
	 }
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
	
		AffineTransform old =AffineTransform.getTranslateInstance(0,0);
        old.rotate(Math.toRadians(degrees),image.getIconWidth()/2,image.getIconHeight()/2);
        //draw shape/image (will be rotated)
        g2d.setTransform(old);
        g2d.drawImage(image.getImage(),old, this);  
		
	}

	private ImageIcon createImageIcon(String path) {
		java.net.URL imgUrl = getClass().getResource(path);
		if(imgUrl != null)
			return new ImageIcon(imgUrl);
		System.out.println("No file path " + path);
		return null;	
	}


	}