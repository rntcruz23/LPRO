package user;

import java.util.StringTokenizer;

import socketsClient.Client;
import socketsServer.SocketAPI;
import windows.game.GameView;

public class User {
	protected Client client;
	private String name;
	private String password;
	public GameView room;
	protected WaitingInput wait;
	public User(Client client) {
		setClient(client);
	}
	public User(int i) {
		
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	protected void printBoard(String command) {
		String rawBoardState = command.substring(2,command.length());
		int line;
		String letters = " ||1||2||3||4||5||6||7||8|\n";
		room.getGame().setText(letters);
		for (line = 0; line < 64; line+=8) {
			String row = rawBoardState.substring(line,line+8);
			int col = 0;
			room.getGame().append((line/8+1)+"|");
			for(col = 0; col < 8; col++) {
				String output = "|"+row.charAt(col) + "|";
				room.getGame().append(output);
			}
			room.getGame().append("\n");
		}
			
	}
	protected String getCommandsFromServer() {
		return SocketAPI.readConnection(client.getSocket());
	}
	public boolean processCommands(String com) {
		char command = com.charAt(0);
		System.out.println(com);
		boolean valid;
		valid = threadSafe(com);
		switch(command) {
		case 'b':
			System.out.println("Printing board");
			printBoard(com);
			break;
		case 'l':
			if(ev(com.charAt(2))) {
				System.out.println("Login sucssessful");
				String[] info = getLogin(com.substring(4,com.length()));
				setName(info[0]);
				setPassword(info[1]);
			}
			else System.out.println("Invalid Login");
			break;
		case 'r':
			if(ev(com.charAt(2))) {
				System.out.println("Resgiter sucssessful");
				String[] info = getLogin(com.substring(4,com.length()));
				setName(info[0]);
				setPassword(info[1]);
			}
			else System.out.println("Invalid Register");
			break;
		case 'j':
			if(ev(com.charAt(2))) {
				System.out.println("Joined room");
				Player n = new Player(client);
				n.setName(getName());
				n.setPassword(getPassword());
				client.getWait().setCaller(n);
				client.setUser(n);
			}
			else System.out.println("Error joining");
			break;
		case 'n':
			if(ev(com.charAt(2))) {
				System.out.println("Created room");
				Player n = new Player(client);
				String roomname = com.substring(4,com.length());
				n.getRoom().getLblGameRoom().setText(roomname);
				n.setName(getName());
				n.setPassword(getPassword());
				client.getWait().setCaller(n);
				client.setUser(n);
			}
			else System.out.println("Error creating");
			break;
		default: return valid;
		}
		return valid;
	}
	public GameView getRoom() {
		return room;
	}
	public void setRoom(GameView room) {
		this.room = room;
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
		room.getHistoryArea().append(move.substring(2,move.length())+'\n');
	}
	public void addToChat(String speak) {
		room.getChatArea().append(speak.substring(2,speak.length())+'\n');
	}
	public WaitingInput getWait() {
		return wait;
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
}