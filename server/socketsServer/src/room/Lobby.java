package room;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;

import api.SocketAPI;
import server.Server;
import users.UserThread;

public class Lobby extends Window implements Runnable{
	private LinkedList<UserThread> users;
	private LinkedList<Room> rooms;
	private Server server;
	private Thread t;
	public Lobby(Server server) {
		super();
		users = new LinkedList<UserThread> ();
		setT(new Thread(this));
		rooms = new LinkedList<Room>();
		this.setServer(server);
	}
	@Override
	public Condition newCondition() {

		return null;
	}
	@Override
	public void run() {
		while(true) Thread.yield();
	}
	@Override
	public void processCommands(String input, UserThread user) {
		char com = input.charAt(0);
		switch(com) {
			case 'j':
				String name = input.substring(2, input.length());
				if(checkRoom(name)) {
					Room r = getRoom(name);
					UsersHandler.addUser(r,user);
					user.setRoom(r);
				}
				else SocketAPI.writeToSocket(user.getUser().getSocket(),"j u "+name);
				break;
			case 'y':
				try {
					int[] stats = server.getDb().getinfo(user.getUser().getName(), user.getUser().getPassword());	
					String output = String.format("y %d %d %d", stats[0],stats[1],stats[2]);
					System.out.println(output);
					SocketAPI.writeToSocket(user.getUser().getSocket(), output);
				}catch(Exception e) {
					System.out.println("Problem sending status: "+e.getMessage());
				}
				break;
			case 'n':
				String newname = input.substring(2, input.length());
				if(!checkRoom(newname)) {
					Room newr = new Room(newname,user,server);
					rooms.add(newr);
					newr.getT().start();
					user.setRoom(newr);
				}
				else SocketAPI.writeToSocket(user.getUser().getSocket(),"n u");
				break;
			case 'u':
				refresh(user);
				break;
			case 'x':
				users.remove(user);
				server.getLand().getUsers().add(user);
				user.setRoom(server.getLand());
				break;
			default:System.out.println("Unknown commad");
		}
	}
	public void refresh(UserThread user) {
		String room = "u "+listToString(rooms);
		SocketAPI.writeToSocket(user.getUser().getSocket(),room);
	}
	public String listToString(LinkedList<Room> room) {
		String output = "";
		for(Room r: room) {
			int oc = r.getPlayers().size()+r.getViewers().size();
			output += r.getRoomName()+":"+ oc +" ";
		}
		
		return output;
		
	}
	public Room getRoom(String name) {
		for(Room r : rooms)
			if(r.getRoomName().equals(name))
				return r;
		return null;
	}
	public boolean checkRoom(String name) {
		for(Room r : rooms)
			if(r.getRoomName().equals(name))
				return true;
		return false;
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
	public Thread getT() {
		return t;
	}
	public void setT(Thread t) {
		this.t = t;
	}
	public LinkedList<Room> getRooms() {
		return rooms;
	}
	public void setRooms(LinkedList<Room> rooms) {
		this.rooms = rooms;
	}
}