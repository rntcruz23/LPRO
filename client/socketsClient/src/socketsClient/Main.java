package socketsClient;

import windows.connect.Connect;

public class Main {
	public static void main(String[] args) {
		Client client = new Client();
		Connect connectWindow = new Connect(client);
		connectWindow.run();
		while(true) {
			Thread.yield();
		}
	}
}