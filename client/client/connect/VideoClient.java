package client.connect;

import java.io.IOException;
import java.util.Scanner;

public class VideoClient extends Client {
	
	private UdpHandlerClient udpHandler;

	public VideoClient(int tcpPort, int udpPort) {
		super(tcpPort, udpPort);
		udpHandler =  new UdpHandlerClient(udpChannel);
	}

	public void processMessage() throws IOException {
		
        (new UdpHandlerClient(udpChannel)).run();
	}
	
	public void sendTcpCommand(CMD cmd) {
		// Simulates Button-press:
		System.out.println("Client sends new TCP command: " + cmd.toString());
		
		// Write TCP enum:
		(new Thread(new TcpHandlerClient(cmd, tcpChannel))).start();
	}

	
}
