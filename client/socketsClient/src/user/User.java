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
import windows.lobby.Lobby;
import windows.login.Login;

public class User {
	protected Client client;
	private String name;
	private String password;
	private Window window;
	private Window backWindow;
	
	/**
	 * Start new empty user, used only for class comparison
	 */
	public User() {}
	/**
	 * Start new user
	 * @param client					client responsible
	 */
	public User(Client client) {
		setClient(client);
	}

	/**
	 * Print pieces into board GUI
	 * @param command								b [string representing board]
	 */
	protected void printBoard(String command) {
		GameView room =(GameView) window;
		room.getBoard().putPieces(command.substring(2,130));
	}
	/**
	 * Reads connection to server
	 * @return										command form server
	 */
	protected String getCommandsFromServer() {
		try {
			return SocketAPI.readConnection(client.getSocket());
		} catch(Exception e) {
			System.out.println("Read failed");
			return " ";
		}
	}
	/**
	 * Command list:
	 * u [room list]								- update room list
	 * b [board string]								- received board state form server
	 * l [validation] [user name] [password]		- login validation
	 * r [validation] [user name] [password]		- register validation
	 * j [validation] [type]						- join validation. Informs what type o user: [g]uest|[p]layer|[s]pectator
	 * g											- logged as guest
	 * s											- inform user that room state will be sent
	 * @param com									command from server
	 * @return										<code>true</code> if valid user command found
	 */
	public boolean processCommands(String com) {
		char command = com.charAt(0);
		System.out.println("command: "+com);
		boolean valid = false;
		System.out.println("command_teste: "+command);
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
				g.manageButtons();
			}
			else valid = true;
			break;
		case 'g':
			if(ev(com.charAt(2))) {
				System.out.println("Entered as guest");
				getBackWindow().getFrmChess().setVisible(false);
				Guest n = new Guest(client);
				setType(n,"","",new Lobby());
				Lobby land = (Lobby)n.getRoom();
				land.removeUserButtons();
				sendCommand("u");
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
		case 'e':
			goodbye();
			break;
		case 'h':
			System.out.println("New history entry");
			addToHistory(com);
			break;
		case 'c':
			System.out.println("New chat entry");
			addToChat(com);
			break;
		default: System.out.println("Unknown user command");
		}
		return valid;
	}
	/**
	 * Load room state from object received from server to game view window
	 * @param roomstate								room state object from server
	 * @param g										game view window
	 */
	public void loadRoomStateToGameView(RoomState roomstate,GameView g) {
		Piece.color turn = roomstate.getTurn();
		String roomName = roomstate.getRoomName();
		String joinStatus = roomstate.getJoinStatus();
		String turnStatus = roomstate.getTurnStatus();
		String whitePlayer = roomstate.getWhitePlayer();
		String blackPlayer = roomstate.getBlackPlayer();
		String nextPlayer = roomstate.getNextPlayer();
		boolean roomEmpty = roomstate.isRoomEmpty();
		boolean check = roomstate.isCheck();
		boolean checkmate = roomstate.isCheckmate();
		LinkedList<String> history = roomstate.getHistory();
		
		if(roomEmpty)
			joinStatus = "Room is empty";
		if(!joinStatus.equals(""))
			addToChat("*********-"+joinStatus+"-*********");
		
		for(String move : history) {
			g.getHistoryArea().append(move);
		}
		g.getTurnLabel().setText(turnStatus);
		g.getFrmChess().setTitle("Chess Game - "+ roomName);
		g.managePlayerLabels(turn, whitePlayer, blackPlayer, nextPlayer);
		g.getLabelcheck().setEnabled(check);	
		g.getLabelcheckMate().setEnabled(checkmate);
	}
	/**
	 * After receiving s from server, wait for room state object
	 * @param client								client
	 * @return										room state object
	 */
	public RoomState waitRoom(Client client) {
		return RoomState.waitroom(client.getUser(),client.getIn());
	}
	/**
	 * Transform room list string into room list matrix
	 * @param roomList								room list [room1:players:spectators:guests room2:...]
	 * @return										array containing rooms
	 * 												[room1:players:spectators:guests]
	 * 												[room2:...						]
	 * 												[....							]
	 */
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
	/**
	 * Get new object from char
	 * @param t										char representing type [p]layer|[s]pectator
	 * @return										new object corresponding to type
	 */
	public User getType(char t) {
		switch(t) {
		case 'p':
			return new Player(client);
		case 's':
			return new Spectator(client);
		default: return new Guest(client);
		}
	}
	/**
	 * Change user to new window
	 * @param n										new user to add
	 * @param name									user name
	 * @param pass									password
	 * @param newWindow								window to add user to
	 */
	public void setType(User n,String name,String pass,Window newWindow) {
		n.setName(name);
		n.setPassword(pass);
		n.backWindow = window;
		n.setRoom(newWindow);
		client.getWait().setCaller(n);
		client.setUser(n);
		newWindow.setUser(n);
	}
	/**
	 * Get login information
	 * @param input									[user name] [password]
	 * @return										[user name]
	 * 												[password]
	 */
	public String[] getLogin(String input) {
		System.out.println(input);
		StringTokenizer tok = new StringTokenizer(input," ");
		String[] output = new String[2];
		output[0] = tok.nextToken();
		output[1] = tok.nextToken();
		return output;
	}
	/**
	 * Get validation from commands from server
	 * @param r										[command] [validation]
	 * @return										<code>true</true> if command valid
	 * 												validation: [s]uccessfull|[u]nccessfull
	 */
	public boolean ev(char r) {
		switch(r) {
		case 's': return true;
		case 'u': return false;
		default: return false;
		}
	}
	/**
	 * Send command to server
	 * @param input									check command list
	 */
	public void sendCommand(String input) {
		SocketAPI.writeToSocket(client.getSocket(),input);
	}
	/**
	 * Insert new history entry to GUI
	 * @param move									h [move]
	 */
	public void addToHistory(String move) {
		GameView room = (GameView) window;
		room.getHistoryArea().append(move.substring(2,move.length())+'\n');
	}
	/**
	 * Print room matrix to GUI
	 * @param rooms									room list matrix
	 */
	public void printRooms(String[] rooms) {
		Lobby l = (Lobby) getRoom();
		l.clearTable();
		for(String room : rooms) {			
			StringTokenizer tok = new StringTokenizer(room,":");
			int n = 0;
			Object[] newRow = new Object[4];
			while(tok.hasMoreTokens()){  
				newRow[n++] = tok.nextToken(); 	  
			}   
			l.addRow(newRow);
		}
	}
	/**
	 * Insert new chat entry to GUI
	 * @param speak									c [text]
	 */
	public void addToChat(String speak) {
		GameView room = (GameView) window;
		String username = "";
		String text = "";
		try {
			StringTokenizer tok = new StringTokenizer(speak.substring(2,speak.length()),":");
			username= tok.nextToken();
			text = tok.nextToken();
			room.getChatArea().append(username + ":");
		}catch(Exception e) {
			text = speak + "\n";
			return ;
		}finally{
			room.getChatArea().append(text);
		}
	}
	/**
	 * Close application
	 * 
	 */
	public void goodbye(){
		if(window != null)
			window.getFrmChess().setVisible(false);
		if(backWindow != null) 
			backWindow.getFrmChess().setVisible(false);
		client.setConnected(false);
	}
	/**
	 * Get current window
	 * @return						current window
	 */
	public Window getRoom() {
		return window;
	}
	/**
	 * Set current window
	 * @param room					current window
	 */
	public void setRoom(Window room) {
		this.window = room;
	}
	/**
	 * Get last window
	 * @return						last window
	 */
	public Window getBackWindow() {
		return backWindow;
	}
	/**
	 * Set last window
	 * @param backWindow			set last window
	 */
	public void setBackWindow(Window backWindow) {
		this.backWindow = backWindow;
	}
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
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