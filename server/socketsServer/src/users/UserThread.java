package users;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import api.SocketAPI;
import room.Window;

public class UserThread extends Thread{
	private User user;
	private Window window;
	private ObjectOutputStream out;
	public UserThread(Socket socket) {
		user = new User(socket);
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Window getRoom() {
		return window;
	}
	public void setRoom(Window room) {
		this.window = room;
	}
	@Override
	public void run() {
		String read ="";
		while(!read.equals("e")) {
			try {
				read = SocketAPI.readConnection(user.getSocket());
			} catch (IOException e) {
				System.out.println("Closing");
				SocketAPI.terminateConnection(user.getSocket());
				break;
			}
			System.out.println(read);
			window.processCommands(read,this);
		}
	}
	public ObjectOutputStream getOut() {
		return out;
	}
}