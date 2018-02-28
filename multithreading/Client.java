package EchoServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
/**
 * @author sunboteng
 */
public class Client {
	public static void main(String[] args) throws Exception {

		Socket client = new Socket("127.0.0.1", 9999);

		Scanner scanner = new Scanner(System.in);

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				client.getInputStream()));

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				client.getOutputStream()));

		while (true) {
			System.out.println(reader.readLine());
			System.out
					.println("Please enter the message you want to send to the server side:");
			String line = scanner.nextLine();
			writer.write(line + "\n");
			writer.flush();
		}
	}
}
