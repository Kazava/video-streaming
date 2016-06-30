package client.connect;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class VideoClient extends Client {

	public VideoClient(int tcpPort, int udpPort) {
		super(tcpPort, udpPort);
	}

	@Override
	public void processMessage() throws IOException {
		System.out.println("Write a command: ");
		String message = "";
		Scanner scan = new Scanner(System.in);
		message = scan.nextLine();
		
		//super.connectTcp();
		(new Thread(new TcpHandlerClient(message, tcpChannel))).start();
		
		
		
	}
	

	
}
