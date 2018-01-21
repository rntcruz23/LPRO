package room;

import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.concurrent.locks.Condition;

import api.SocketAPI;
import server.Server;
import users.UserThread;

public class Landing extends Window implements Runnable{
	private LinkedList<UserThread> users;
	private Thread t;
	private Server server;
	
	/**
	 * Start new landing room
	 * @param server				server associated with this landing room
	 */
	public Landing(Server server) {
		super();
		setServer(server);
		setUsers(new LinkedList<UserThread>());
		setT(new Thread(this));
	}
	/**
	 * Main loop
	 * does nothing
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while(true) Thread.yield();
	}
	/** 
	 * Command list:
	 * l [username] [password]		- login with combination given username/password
	 * g							- enter as guest
	 * r [username] [password]		- register with combination given username/password
	 * e							- exit landing page. Terminates connection
	 * default						- prints "Unkown command" to terminal
	 * @see room.Window#processCommands(java.lang.String, users.UserThread)
	 */
	@Override
	public void processCommands(String input, UserThread user) {
		char com = input.charAt(0);
		switch(com) {
			case 'l':
				String[] info = getLogin(input.substring(2, input.length()));
				try {
					if(server.getDb().userExists(info[0],info[1])) {
						changeToLobby(user,info);
						SocketAPI.writeToSocket(user.getUser().getSocket(), "l s "+info[0]+" "+info[1]);
						user.getUser().setType('s');
					}
					else SocketAPI.writeToSocket(user.getUser().getSocket(), "l u");
				}catch(Exception e) {
					System.out.println("Problem sending status: "+e.getMessage());
				}
				break;
			case 'g':
				changeToLobby(user,new String[] {"",""});
				SocketAPI.writeToSocket(user.getUser().getSocket(), "g s");
				user.getUser().setType('g');
				break;
			case 'r':
				String[] newInfo = getLogin(input.substring(2, input.length()));
				try {
					if(server.getDb().insertNewUser(newInfo[0], newInfo[1])) {
						changeToLobby(user,newInfo);
						SocketAPI.writeToSocket(user.getUser().getSocket(), "r s "+newInfo[0]+" "+newInfo[1]);
					}
					else SocketAPI.writeToSocket(user.getUser().getSocket(), "r u");
				}catch(Exception e) {
					System.out.println("Problem sendind status: "+e.getMessage());
				}
				break;
			case 'e':
				users.remove(user);
				SocketAPI.terminateConnection(user.getUser().getSocket());
				SocketAPI.writeToSocket(user.getUser().getSocket(), "e");
				server.updateOccupation(-1);
				break;
			default:System.out.println("Unknown commad");
		}
	}
	/**
	 * Change user thread to lobby room
	 * @param user					user to send to lobby
	 * @param info					credentials to give to user
	 */
	public void changeToLobby(UserThread user,String[] info) {
		user.getUser().setName(info[0]);
		user.getUser().setPassword(info[1]);
		server.getLob().getUsers().add(user);
		user.setRoom(server.getLob());
		users.remove(user);
	}
	/**
	 * Get login information from client command: l [username] [password]
	 * @param input					input command: l [username] [password]
	 * @return						String array containing username and password
	 */
	public String[] getLogin(String input) {
		input += " ";
		System.out.println(input);
		StringTokenizer tok = new StringTokenizer(input," ");
		String[] output = new String[2];
		output[0] = tok.nextToken();
		output[1] = tok.nextToken();
		return output;
	}
	
	public Thread getT() {
		return t;
	}
	public void setT(Thread t) {
		this.t = t;
	}
	public LinkedList<UserThread> getUsers() {
		return users;
	}
	public void setUsers(LinkedList<UserThread> users) {
		this.users = users;
	}
	public Server getServer() {
		return server;
	}
	public void setServer(Server server) {
		this.server = server;
	}
	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}
}