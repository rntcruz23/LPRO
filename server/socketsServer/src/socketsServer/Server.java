package socketsServer;

import java.io.IOException;
import java.net.ServerSocket;

import room.Landing;
import room.Lobby;
import users.UserThread;

public class Server {
	private ServerSocket socket;
	private int port,started;
	private final String admin = "pawnstars";
	private final String adminp = "pawnstars";
	private DataBase db;
	private Admin adminW;
	private Landing land;
	private Lobby lob;
 	public boolean loginCheck(String user,String pass) {
		return (admin.equals(user) && adminp.equals(pass));
	}
	public Server(int port) {
		this.setPort(port);
		setStarted(0);
		land = new Landing(this);
		lob = new Lobby(this);
		land.getT().start();
		lob.getT().start();
		adminW = new Admin(this);
		while(started == 0) Thread.yield();
		setDb(new DataBase());
		try {
			setSocket(new ServerSocket(port));
		} catch (IOException e) {
			System.out.println("Error opening server");
		}
	}
	public UserThread waitNewConnection() {
		UserThread u = null;
		try {
			System.out.println("Waiting new connection on: "+socket);
			u = new UserThread(socket.accept());
		} catch (IOException e) {
			System.out.println("Error creating new connection");
		}
		return u;
	}
	public ServerSocket getSocket() {
		return socket;
	}
	public void setSocket(ServerSocket socket) {
		this.socket = socket;
	}
	public DataBase getDb() {
		return db;
	}
	public void setDb(DataBase db) {
		this.db = db;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getAdmin() {
		return admin;
	}
	public String getAdminp() {
		return adminp;
	}
	public Admin getAdminW() {
		return adminW;
	}
	public void setAdminW(Admin adminW) {
		this.adminW = adminW;
	}
	public int getStarted() {
		return started;
	}
	public void setStarted(int started) {
		this.started = started;
	}
	public void waitUsers() {
		while(true) {
			UserThread newUser = waitNewConnection();
			land.getUsers().add(newUser);
			newUser.setRoom(land);
			newUser.start();
		}
	}
	public Landing getLand() {
		return land;
	}
	public void setLand(Landing land) {
		this.land = land;
	}
	public Lobby getLob() {
		return lob;
	}
	public void setLob(Lobby lob) {
		this.lob = lob;
	}
}