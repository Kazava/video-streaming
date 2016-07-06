package server_v5.network;

import java.nio.channels.Channel;
import java.nio.channels.ServerSocketChannel;

/*
 * The GUI should call each function
 */
public class VideoStreamServer implements Runnable {
	private boolean isAlive;

	public VideoStreamServer() {}
	
	public void startServer() {
		this.isAlive = true;
		while (isAlive) {
			Network network = new Network(8001, 8002);
			Channel channel = network.choosingConnection();
			if (channel instanceof ServerSocketChannel) {
				System.out.println("TCP received!");
				//TODO: Read TCP received message and convert to enum in another class.
			}
			else
				System.out.println("meh");
		}
	}
	
	public void stopServer() {
		this.isAlive = false;
	}

	@Override
	public void run() {
		startServer();
	}
}
