package user;

import java.util.LinkedList;
import java.util.StringTokenizer;

import client.Client;
import pieces.Piece;
import room.RoomState;
import server.SocketAPI;
import window.Window;
import windows.create.CreateAcc;
import windows.game.GameView;
import windows.joinroom.JoinRoom;
import windows.lobby.Lobby;
import windows.login.Login;

public class User {
	protected Client client;
	private String name;
	private String password;
	private Window window;
	private Window backWindow;
	public User(Client client) {
		setClient(client);
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	protected void printBoard(String command) {
		GameView room =(GameView) window;
		String rawBoardState = command.substring(2,command.length());
		int line;
		String letters = " ||a||b||c||d||e||f||g||h|\n";
		room.getGame().setText(letters);
		int nr = 1;
		for (line = 64*2; line >= 16; line-=16) {
			String row = rawBoardState.substring(line-16,line);
			int col = 0;
			room.getGame().append((nr++)+"|");
			for(col = 0; col < 8; col++) {
				String output = "|"+row.charAt(col*2) + "|";
				room.getGame().append(output);
			}
			room.getGame().append("\n");
		}	
	}
	protected String getCommandsFromServer() {
		try {
			return SocketAPI.readConnection(client.getSocket());
		} catch(Exception e) {
			System.out.println("Read failed");
			return " ";
		}
	}
	public boolean processCommands(String com) {
		char command = com.charAt(0);
		System.out.println("command: "+com);
		boolean valid = false;
		valid = threadSafe(com);
		switch(command) {
		case 'u':
			String[] rooms = getRooms(com.substring(2,com.length()));
			printRooms(rooms);
			valid = true;
			break;
		case 'b':
			System.out.println("Printing board");
			printBoard(com);
			valid = true;
			break;
		case 'l':
			if(ev(com.charAt(2))) {
				System.out.println("Login sucssessful");
				String[] info = getLogin(com.substring(4,com.length()));
				window.getFrmChess().setVisible(false);
				backWindow.getFrmChess().setVisible(false);
				setType(new Spectator(client),info[0],info[1],new Lobby());
				SocketAPI.writeToSocket(client.getSocket(), "u");
				valid = false;
			}
			else {
				Login log = (Login)window;
				log.getStatus().setText("Invalid login");
				System.out.println("Invalid login");
			}
			break;
		case 'r':
			if(ev(com.charAt(2))) {
				System.out.println("Resgiter sucssessful");
				String[] info = getLogin(com.substring(4,com.length()));
				window.getFrmChess().setVisible(false);
				backWindow.getFrmChess().setVisible(false);
				setType(new Spectator(client),info[0],info[1],new Lobby());
				SocketAPI.writeToSocket(client.getSocket(), "u");
				valid = true;
			}
			else {
				CreateAcc log = (CreateAcc)window;
				log.getStatus().setText("Invalid register");
				System.out.println("Invalid register");
			}
			break;
		case 'j':
			if(ev(com.charAt(2))) {
				User n = getType(com.charAt(4));
				System.out.println("Joined room");
				window.getFrmChess().setVisible(false);
				backWindow.getFrmChess().setVisible(false);
				GameView g = new GameView(n);
				setRoom(g);
				setType(n,getName(),getPassword(),g);
				valid = false;
			}
			else {
				JoinRoom j = (JoinRoom)getRoom();
				j.getStatus().setText("Error joining room");
				System.out.println("Error joining");
				valid = true;
			}
			break;
		case 'g':
			if(ev(com.charAt(2))) {
				System.out.println("Entered as guest");
				getBackWindow().getFrmChess().setVisible(false);
				Guest n = new Guest(client);
				setType(n,"","",new Lobby());
				Lobby land = (Lobby)n.getRoom();
				land.removeUserButtons();
				SocketAPI.writeToSocket(client.getSocket(), "u");
				valid = true;
			}
			break;
		case 's':
			System.out.println("Waiting room state");
			GameView g = (GameView)window;
			RoomState roomstate = waitRoom(client);
			loadRoomStateToGameView(roomstate,g);
			System.out.println("Room received");
			valid = true;
			break;
		default: System.out.println("Unknown user command");
		}
		return valid;
	}
	public void loadRoomStateToGameView(RoomState roomstate,GameView g) {
		Piece.color turn = roomstate.getTurn();
		String roomName = roomstate.getRoomName();
		boolean roomEmpty = roomstate.isRoomEmpty();
		LinkedList<String> history = roomstate.getHistory();
		g.getLblGameRoom().setText(roomName);
		for(String move : history) {
			g.getHistoryArea().append(move);
		}
	}
	public RoomState waitRoom(Client client) {
		return RoomState.waitroom(client.getUser(),client.getIn());
	}
	public String[] getRooms(String roomList) {
		StringTokenizer tok = new StringTokenizer(roomList," ");
		int number = tok.countTokens();
		String[] rooms = new String[number];
		int n = 0;
		while(tok.hasMoreTokens()) {
			String room = tok.nextToken();
			rooms[n++] = room;
			System.out.println(room);
		}
		return rooms;
	}
	public User getType(char t) {
		switch(t) {
		case 'p':
			return new Player(client);
		case 's':
			return new Spectator(client);
		default: return new Guest(client);
		}
	}
	public void setType(User n,String name,String pass,Window newWindow) {
		n.setName(name);
		n.setPassword(pass);
		n.backWindow = window;
		n.setRoom(newWindow);
		client.getWait().setCaller(n);
		client.setUser(n);
		newWindow.setUser(n);
	}
	public Window getRoom() {
		return window;
	}
	public void setRoom(Window room) {
		this.window = room;
	}
	public String[] getLogin(String input) {
		System.out.println(input);
		StringTokenizer tok = new StringTokenizer(input," ");
		String[] output = new String[2];
		output[0] = tok.nextToken();
		output[1] = tok.nextToken();
		return output;
	}
	public boolean ev(char r) {
		switch(r) {
			case 's': return true;
			case 'u': return false;
			default: return false;
		}
	}
	public boolean threadSafe(String com) {
		char command = com.charAt(0);
		switch(command) {
		case 'h':
			System.out.println("New history entry");
			addToHistory(com);
			break;
		case 'c':
			System.out.println("New chat entry");
			addToChat(com);
			break;
		default: return false;
		}
		return false;
	}
	public void sendCommand(String input) {
		SocketAPI.writeToSocket(client.getSocket(),input);
	}
	public void addToHistory(String move) {
		GameView room = (GameView) window;
		room.getHistoryArea().append(move.substring(2,move.length())+'\n');
	}
	public void printRooms(String[] rooms) {
		Lobby l = (Lobby) getRoom();
		l.getTextArea().setText("");
		for(String room : rooms)
			l.getTextArea().append(room+'\n');
	}
	public void addToChat(String speak) {
		GameView room = (GameView) window;
		room.getChatArea().append(speak.substring(2,speak.length())+'\n');
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Window getBackWindow() {
		return backWindow;
	}
	public void setBackWindow(Window backWindow) {
		this.backWindow = backWindow;
	}
	public void goodbye(){
		if(window != null)
			window.getFrmChess().setVisible(false);
		if(backWindow != null) 
			backWindow.getFrmChess().setVisible(false);
		System.exit(0);
	}
}