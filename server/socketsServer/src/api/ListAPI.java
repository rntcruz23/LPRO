package api;

import java.util.LinkedList;

import users.UserThread;

public class ListAPI {
	public static void writeToList(LinkedList<UserThread> to,String output) {
		for(UserThread t:to)
			SocketAPI.writeToSocket(t.getUser().getSocket(), output);
	}
	public static void terminateList(LinkedList<UserThread>t) {
		writeToList(t,"exit");
		t.clear();
	}
}