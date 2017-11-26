package room;

import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.concurrent.locks.Condition;

import api.SocketAPI;
import socketsServer.Server;
import users.UserThread;

public class Landing extends Window implements Runnable{
	private LinkedList<UserThread> users;
	private Thread t;
	private Server server;
	public Landing(Server server) {
		super();
		setServer(server);
		setUsers(new LinkedList<UserThread>());
		setT(new Thread(this));
	}
	@Override
	public void run() {
		while(true) Thread.yield();
	}
	@Override
	public void processCommands(String input, UserThread user) {
		char com = input.charAt(0);
		switch(com) {
			case 'l':
				String[] info = getLogin(input.substring(2, input.length()));
				if(server.getDb().userExists(info[0],info[1])) {
					changeToLobby(user,info);
					SocketAPI.writeToSocket(user.getUser().getSocket(), "l s "+info[0]+" "+info[1]);
					user.getUser().setType('s');
				}
				else SocketAPI.writeToSocket(user.getUser().getSocket(), "l u");
				break;
			case 'g':
				changeToLobby(user,new String[] {"",""});
				SocketAPI.writeToSocket(user.getUser().getSocket(), "g s");
				user.getUser().setType('g');
				break;
			case 'r':
				String[] newInfo = getLogin(input.substring(2, input.length()));
				if(server.getDb().insertNewUser(newInfo[0], newInfo[1])) {
					changeToLobby(user,newInfo);
					SocketAPI.writeToSocket(user.getUser().getSocket(), "r s "+newInfo[0]+" "+newInfo[1]);
				}
				else SocketAPI.writeToSocket(user.getUser().getSocket(), "r u");
				break;
			case 'e':
				users.remove(user);
				SocketAPI.terminateConnection(user.getUser().getSocket());
				break;
			default:System.out.println("Unknown commad");
		}
	}
	public void changeToLobby(UserThread user,String[] info) {
		user.getUser().setName(info[0]);
		user.getUser().setPassword(info[1]);
		server.getLob().getUsers().add(user);
		user.setRoom(server.getLob());
	}
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