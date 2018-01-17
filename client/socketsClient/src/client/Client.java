package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import user.User;
import user.WaitingInput;


public class Client {
	
	private Socket socket;
	private User user;
	private WaitingInput wait;
	private ObjectInputStream in;
	private boolean connected;
	public Client() {
		System.out.println("New client");
		setConnected(false);
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
	public void connect(String ip,int port) throws IOException, UnknownHostException{
		InetAddress host = InetAddress.getByName(ip);
		socket = new Socket(host,port);
		System.out.println("Connection successful");
		setConnected(true);
		setIn(new ObjectInputStream(socket.getInputStream()));
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
	public ObjectInputStream getIn() {
		return in;
	}
	public void setIn(ObjectInputStream in) {
		this.in = in;
	}
	public boolean isConnected() {
		return connected;
	}
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
}