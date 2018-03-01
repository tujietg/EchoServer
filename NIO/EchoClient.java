package NIOEchoServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author sunboteng
 */
public class EchoClient {
	public static void main(String[] args) throws Exception {

		Socket socket = new Socket("192.168.4.10", 9999);

		Scanner scanner = new Scanner(System.in);

		OutputStreamWriter writer = new OutputStreamWriter(
				socket.getOutputStream());

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));

		while (true) {
			System.out
					.println("Please enter the message you want to send to the server side:");

			String str = scanner.nextLine();
			writer.write(str + "\n");
			// writer.close();
			writer.flush();
			System.out.println(bufferedReader.readLine());
		}

	}
}
