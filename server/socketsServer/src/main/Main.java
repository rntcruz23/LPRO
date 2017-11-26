package main;

import socketsServer.Server;

public class Main {
	public static void main(String[] args) {
		Server server = new Server(4412);
		server.waitUsers();
	}
}