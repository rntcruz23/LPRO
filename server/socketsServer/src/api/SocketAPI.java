package api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketAPI {

	/**
	 * Write string to socket
	 * @param destination				output socket
	 * @param output					output string
	 */
	public static void writeToSocket(Socket destination,String output) {
		byte[] bc;
		try {
			OutputStream sc = destination.getOutputStream();
			bc = toByte(output);
			Thread.sleep(100);
			sc.write(bc);
		} catch (IOException | InterruptedException e) {
			System.out.println("Error writing to socket: "+e.getMessage());
		}
	}
	/**
	 * Read from socket
	 * @param socket					socket to read from
	 * @return							string read
	 * @throws IOException
	 */
	public static String readConnection(Socket socket) throws IOException{
		InputStream input;
		String readS = "Read failed";
		byte[] b = new byte[100];
		int size;
		input = socket.getInputStream();
		size = input.read(b);
		readS = byteToString(b,size);
		return readS;
	}
	/**
	 * Closes connection. Writes exit to socket
	 * @param toClose					socket to close
	 */
	public static void terminateConnection(Socket toClose) {
		writeToSocket(toClose,"exit");
		try {
			System.out.println("Connection to :"+toClose.getInetAddress()+" terminated");
			toClose.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Convert String to byte array to write to socket
	 * @param s							string to write
	 * @return							byte array to send
	 */
	private static byte[] toByte(String s) {
		byte[] b = s.getBytes();
		return b;
	}
	/**
	 * Convert byte array to string.
	 * @param b							byte array received from socket
	 * @param size						size of array
	 * @return							byte array as String
	 */
	private static String byteToString(byte[] b,int size) {
		byte[] buffer = new byte[size];
		int i = 0;
		for(i = 0; i < size; i++)
			buffer[i] = b[i];
		return new String(buffer);
	}
}