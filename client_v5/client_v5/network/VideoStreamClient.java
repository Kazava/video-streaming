package client_v5.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.Channel;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;

/*
 * The GUI should be able to call each function
 */
public class VideoStreamClient implements Runnable, Client {

	private boolean isAlive;
	private Network network;

	public VideoStreamClient() {}
	
	public void startListening() {
		
		this.isAlive = true;
		System.out.println("Client starts receiving...");
		
		UdpReceiver udpReceiver = new UdpReceiver();
		new Thread(udpReceiver).start();
			
	//			while (isAlive) {
	//
		//			network.choosingConnection(channel); // first stop here!
		//			if (channel instanceof ServerSocketChannel) {
		//				// TODO: Read TCP received message, convert to enum and reply in another class.
		//			}
		//			else if (channel instanceof DatagramChannel) {
		//				System.out.println("UDP received!");
		//				// TODO: Read UDP received message
		//				UdpReceiver udpReceiver = new UdpReceiver(channel);
		//				new Thread(udpReceiver).start();
		//			} else {
		//				System.out.println("meh");
		//			}
	//			}

	}
	
	public void stopListening() {
		this.isAlive = false;
		System.out.println("...Server stopped.");
	}

	public void sendMessage(CMD cmd) {
		TcpSender tcpSender = new TcpSender(cmd);
		new Thread(tcpSender).start();
	}

	public void run() {
//		network = new Network(9001, 9002);
		System.out.println("Client running...");
		startListening();		
	}

}
