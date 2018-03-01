package NIOEchoServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
/**
 * @author sunboteng
 */
public class NioEchoServer {
	public void server(int port) throws IOException {
		System.out.println("Listening for connections on port " + port);
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		serverSocketChannel.configureBlocking(false);
		Selector selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {
			selector.select();
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = readyKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey key = iterator.next();
				iterator.remove();
				if (key.isAcceptable()) {
					ServerSocketChannel server = (ServerSocketChannel) key
							.channel();
					SocketChannel client = server.accept();
					System.out.println("Accepted connection from " + client);
					client.configureBlocking(false);
					client.register(selector, SelectionKey.OP_WRITE
							| SelectionKey.OP_READ, ByteBuffer.allocate(100));
				}
				if (key.isReadable()) {
					SocketChannel client = (SocketChannel) key.channel();
					ByteBuffer output = (ByteBuffer) key.attachment();
					client.read(output);
				}
				if (key.isWritable()) {
					SocketChannel client = (SocketChannel) key.channel();
					ByteBuffer output = (ByteBuffer) key.attachment();
					output.flip();
					client.write(output);
					output.compact();
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		new NioEchoServer().server(9999);
	}
}