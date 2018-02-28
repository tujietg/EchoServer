package EchoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author sunboteng
 */
public class EchoServer {
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(9999);
			while (true) {
				Socket client = server.accept();
				System.out.println("Connection success......");
				ServerThread serverthread = new ServerThread(client);
				serverthread.start();
			}
		} catch (IOException e) {
			System.err.println("Exception caught:" + e);
		}
	}
}

class ServerThread extends Thread {
	Socket client;

	ServerThread(Socket client) {
		this.client = client;
	}

	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
			writer.println("[type 'bye' to disconnect]");
			while (true) {
				String line = reader.readLine();
				if (line.trim().equals("bye")) {
					writer.println("[bye!]");
					break;
				}
				writer.println("[Echo]" + line);
			}
		} catch (IOException e) {
			System.err.println("Exception caught:" + e);
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
