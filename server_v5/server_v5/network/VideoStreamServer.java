package server_v5.network;

import java.nio.channels.Channel;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;

/*
 * The GUI should be able to call each function
 */
public class VideoStreamServer implements Runnable, Server {
	private boolean isAlive;
	private Network network;

	public VideoStreamServer() {
		this.isAlive = true;
	}
	
	public void startListening() {
		while (isAlive) {
			Channel channel;
			channel = network.choosingConnection(); // first stop here!
			if (channel instanceof ServerSocketChannel) {
				//System.out.println("TCP received!");
				// TODO: Read TCP received message, convert to enum and reply in another class.
				
				TcpReceiver tcpReceiver = new TcpReceiver(channel);
				new Thread(tcpReceiver).start();
			}
			else if (channel instanceof DatagramChannel) {
				System.out.println("UDP received!");
				// TODO: Read UDP received message, convert to enum and reply in another class.
			} else {
				System.out.println("meh");
			}
		}
	}
	
	public void stopListening() {
		this.isAlive = false;
		System.out.println("--> Server stopped listening");
	}
	
	public void restartListening() {
		this.isAlive = true;
		System.out.println("--> Server is listening again!");
	}

	@Override
	public void run() {
		network = new Network(8001, 8002);
		System.out.println("Server running...");
		startListening();
	}

}
