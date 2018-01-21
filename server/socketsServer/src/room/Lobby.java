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
	
	/**
	 * Start new lobby
	 * @param server			server where lobby will run
	 */
	public Lobby(Server server) {
		super();
		users = new LinkedList<UserThread> ();
		setT(new Thread(this));
		rooms = new LinkedList<Room>();
		this.setServer(server);
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
	 * j [room name]				- join room
	 * y							- request statistics
	 * n [room name]				- create new room
	 * u							- request room list
	 * x							- return to landing room 
	 * @see room.Window#processCommands(java.lang.String, users.UserThread)
	 */
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
	/**
	 * Send room list to user
	 * @param user					user requesting rooms
	 */
	public void refresh(UserThread user) {
		String room = "u "+listToString(rooms);
		SocketAPI.writeToSocket(user.getUser().getSocket(),room);
	}
	/**
	 * Converts list of rooms to string
	 * @param room					list of rooms to convert
	 * @return						[room name]:[number of players]:[number of spectators]:[number of guest]
	 */
	public String listToString(LinkedList<Room> room) {
		String output = "";
		for(Room r: room)
			output += r.getRoomName()+":"+ r.getPlayers().size() +":"+r.getSpectators().size()+":"+r.getGuests().size()+" ";
		return output;
	}
	/**
	 * Get room with given name
	 * @param name					name to search
	 * @return						room with given name
	 */
	public Room getRoom(String name) {
		for(Room r : rooms)
			if(r.getRoomName().equals(name))
				return r;
		return null;
	}
	/**
	 * Check if room with given name exists
	 * @param name					name to search
	 * @return						<code>true</code> if room with given name exists
	 */
	public boolean checkRoom(String name) {
		if (name.contains(" ")) return true;
		for(Room r : rooms)
			if(r.getRoomName().equals(name))
				return true;
		return false;
	}
	
	@Override
	public Condition newCondition() {

		return null;
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