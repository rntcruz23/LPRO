package socketsClient;

import java.util.Scanner;

import socketsServer.SocketAPI;
import user.User;
import user.WaitingInput;
import windows.landing.LandingScreen;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		scan.useDelimiter(System.getProperty("line.separator"));
		Client client = new Client();
		client.connect("127.0.0.1",4412);	
		User user = new User(client);
		WaitingInput wait = new WaitingInput(user);
		client.setUser(user);
		client.setWait(wait);
		user.setType(user, "", "", new LandingScreen());
		while(true) {
			String input = scan.next();
			SocketAPI.writeToSocket(client.getSocket(),input);
		}
	}
}