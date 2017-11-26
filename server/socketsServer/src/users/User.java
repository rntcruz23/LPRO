package users;

import java.net.Socket;

import pieces.Piece;

public class User {
	private String name;
	private String password;
	private Socket socket;
	private char type;
	private Piece.color turn;
	public User(Socket socket) {
		setSocket(socket);
	}
	public User(String name,String pass) {
		setName(name);
		setPassword(pass);
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public Piece.color getTurn() {
		return turn;
	}
	public void setTurn(Piece.color turn) {
		this.turn = turn;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
}