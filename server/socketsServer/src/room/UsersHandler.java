package room;

import java.util.LinkedList;

import api.ColorsAPI;
import api.SocketAPI;
import pieces.Piece;
import server.Server;
import users.UserThread;

public class UsersHandler {
	private UsersHandler() {}
	public static void remUser(Room room,UserThread user) {
		LinkedList<UserThread> players = room.getPlayers();
		LinkedList<UserThread> spectators = room.getSpectators();
		LinkedList<UserThread> guests = room.getGuests();
		LinkedList<UserThread> viewers = room.getViewers();
		Server server = room.getServer();
		if(players.remove(user)) {
			if(!spectators.isEmpty()) {
				UserThread nP = spectators.removeFirst();
				viewers.remove(nP);
				players.add(nP);
				Piece.color color = user.getUser().getTurn();
				char t = ColorsAPI.colorToString(color);
				String c = t+"";
				nP.getUser().setTurn(color);
				SocketAPI.writeToSocket(nP.getUser().getSocket(), "z "+c.toLowerCase());
			}
			if(room.isGameRunning()) {
				GameResultHandler.playerDefeated(room,user);
			}
		}else {
			spectators.remove(user);
			guests.remove(user);
			viewers.remove(user);
		}
		server.getLob().getUsers().add(user);
		user.setRoom(server.getLob());
		room.setGameRunning(players.size() == 2);
		room.setJoinStatus(user.getUser().getName()+" left the room");
	}
	public static void addUser(Room room, UserThread user) {
		LinkedList<UserThread> players = room.getPlayers();
		LinkedList<UserThread> spectators = room.getSpectators();
		LinkedList<UserThread> guests = room.getGuests();
		LinkedList<UserThread> viewers = room.getViewers();
		String newUser = "guest";
		if(user.getUser().getType() == 'g') {
			guests.add(user);
			viewers.add(user);
			SocketAPI.writeToSocket(user.getUser().getSocket(),"j s g");
		}
		else{
			if(players.size() < 2) newPlayer(players, user);
			else newSpec(spectators, viewers, user);
			newUser = user.getUser().getName();
		}
		room.setJoinStatus(newUser+" joined");
		UsersHandler.broadcastState(room,players,viewers);
		room.setJoinStatus("");
		room.sendBoard();
		room.setGameRunning(players.size() == 2);
	}
	public static void newSpec(LinkedList<UserThread> spectators,LinkedList<UserThread> viewers, UserThread user) {
		SocketAPI.writeToSocket(user.getUser().getSocket(),"j s s");
		spectators.add(user);
		viewers.add(user);
	}
	public static void newPlayer(LinkedList<UserThread> players, UserThread user) {
		SocketAPI.writeToSocket(user.getUser().getSocket(),"j s p");
		players.add(user);
		char t;
		if(players.size() == 1) {
			user.getUser().setTurn(Piece.color.white);
			t = 'w';
		}
		else {
			user.getUser().setTurn(players.get(0).getUser().getTurn() == Piece.color.white?Piece.color.black:Piece.color.white);
			t = 'b';
		}
		SocketAPI.writeToSocket(user.getUser().getSocket(),"t "+t);
	}
	@SafeVarargs
	public static void broadcastState(Room room,LinkedList<UserThread> ...args) {
		for(LinkedList<UserThread> list:args) {
			RoomState.sendRoom(list,room);
		}
	}
	public static UserThread getColorPlayer(Room room, Piece.color color) {
		UserThread u = null;
		u = room.getPlayers().get(0).getUser().getTurn()==color?room.getPlayers().get(0):new UserThread("");
		if (room.getPlayers().size() > 1)
			u = room.getPlayers().get(1).getUser().getTurn()==color?room.getPlayers().get(1):u;
		return u;
	}
}