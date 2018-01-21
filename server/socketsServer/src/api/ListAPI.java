package api;

import java.util.LinkedList;

import users.UserThread;

public class ListAPI {
	/**
	 * Write output to a list
	 * @param to					list to write to
	 * @param output				String to write
	 */
	public static void writeToList(LinkedList<UserThread> to,String output) {
		for(UserThread t:to)
			SocketAPI.writeToSocket(t.getUser().getSocket(), output);
	}
	/**
	 * Closes connection to a list. Writes "exit" to list
	 * @param t						user list to terminate
	 */
	public static void terminateList(LinkedList<UserThread>t) {
		writeToList(t,"exit");
		t.clear();
	}
}