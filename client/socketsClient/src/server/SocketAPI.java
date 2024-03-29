package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketAPI {

	public static void writeToSocket(Socket destination,String output) {
		byte[] bc;
		try {
			OutputStream sc = destination.getOutputStream();
			bc = toByte(output);
			sc.write(bc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String readConnection(Socket socket) throws IOException {
		InputStream input;
		String readS = "Read failed";
		byte[] b = new byte[200];
		int size;
		input = socket.getInputStream();
		size = input.read(b);
		readS = byteToString(b,size);
		return readS;
	}
	public static void terminateConnection(Socket toClose) {
		writeToSocket(toClose,"exit");
		try {
			System.out.println("Connection to :"+toClose.getInetAddress()+" terminated");
			toClose.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static byte[] toByte(String s) {
		byte[] b = s.getBytes();
		return b;
	}
	private static String byteToString(byte[] b,int size) {
		byte[] buffer = new byte[size];
		int i = 0;
		for(i = 0; i < size; i++)
			buffer[i] = b[i];
		return new String(buffer);
	}
}