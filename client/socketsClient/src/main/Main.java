package main;

import client.Client;
import windows.connect.Connect;

public class Main {
	public static void main(String[] args) {
		Client client = new Client();
		Connect connectWindow = new Connect(client);
		connectWindow.run();
		while(client.isConnected()) {
			Thread.yield();
		}
	}
}