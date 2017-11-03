package main;

import javax.swing.JFrame;

import dkeep.logic.Map;
import swing.inte.MainFrame;

public class WindowThread implements Runnable{
	MainFrame mainFrame;
	Map map;
	public WindowThread(Map game) {
		map = game;
		mainFrame = new MainFrame("Dragons",map);
	}
	@Override
	public void run() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(850,850);
		mainFrame.setVisible(true);
		mainFrame.screen.updateScreen(map);
	}
}