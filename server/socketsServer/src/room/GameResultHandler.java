package room;

import java.sql.SQLException;

import api.SocketAPI;
import server.Server;
import users.UserThread;

public class GameResultHandler {
	private GameResultHandler() {};
	/**
	 * Set user as winner
	 * @param room			room to define winner
	 * @param user			user to become winner
	 */
	public static void playerWin(Room room, UserThread user) {
		Server server = room.getServer();
		String username = user.getUser().getName();
		String password = user.getUser().getPassword();
		int[] currStats;
		try {
			currStats = server.getDb().getinfo(username,password);
			server.getDb().changeinfo(username, password, currStats[0]+1,currStats[1], currStats[2]);
			room.setGameRunning(false);
			room.setGameFinished(true);
			room.setTurnStatus(username+" won!");
		} catch (SQLException e) {
			System.out.println("Error updating stats");
		}
	}
	/**
	 * Draw game
	 * Count as draw for both players
	 * @param room
	 */
	public static void drawGame(Room room) {
		Server server = room.getServer();
		for(UserThread user: room.getPlayers()) {
			String username = user.getUser().getName();
			String password = user.getUser().getPassword();
			int[] currStats;
			try {
				currStats = server.getDb().getinfo(username,password);
				server.getDb().changeinfo(username, password, currStats[0],currStats[1], currStats[2]+1);
				room.setGameRunning(false);
				room.setGameFinished(true);
				room.setTurnStatus("Both players agreed to a draw");
			} catch (SQLException e) {
				System.out.println("Error updating stats");
			}
		}
	}
	/**
	 * Set defeat for user
	 * @param room			room game where user is playing
	 * @param user			defeated user
	 */
	public static void playerDefeated(Room room,UserThread user){
		Server server = room.getServer();
		String username = user.getUser().getName();
		String password = user.getUser().getPassword();
		int[] currStats;
		try {
			currStats = server.getDb().getinfo(username,password);
			server.getDb().changeinfo(username, password, currStats[0],currStats[1]+1, currStats[2]);
		} catch (SQLException e) {
			System.out.println("Error updating stats");
		}
	}
	/**
	 * Send a draw prompt to user
	 * @param user			user to ask for draw
	 */
	public static void promptDraw(UserThread user) {
		if(user != null)
			SocketAPI.writeToSocket(user.getUser().getSocket(),"d");
	}
}