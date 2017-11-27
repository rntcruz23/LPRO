package socketsClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import user.User;
import user.WaitingInput;


public class Client {
	
	private Socket socket;
	private User user;
	private WaitingInput wait;
	public Client() {
		System.out.println("New client");
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	/**
	 * @param ip
	 * @param port
	 */
	public void connect(String ip,int port) {
		try {
			InetAddress host = InetAddress.getByName(ip);
			try {
				socket = new Socket(host,port);
				System.out.println("Connection successful");
			} catch (IOException e) {
				System.out.println("Connection failed: "+e.getMessage());
				System.exit(0);
			}
		} catch (UnknownHostException e) {
			System.out.println("Unknown host: "+e.getMessage());
		}
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public WaitingInput getWait() {
		return wait;
	}
	public void setWait(WaitingInput wait) {
		wait.start();
		this.wait = wait;
	}
}