package client.connect;

import java.io.IOException;
import java.util.Scanner;

public class VideoClient extends Client {

	public VideoClient(int tcpPort, int udpPort) {
		super(tcpPort, udpPort);
	}

	public void processMessage() throws IOException {
 
        // Send TCP Commands as enums:
//        sendTcpCommand(CMD.PLAY);
        
        // Read UDP Messages as String:
//        (new Thread(new UdpHandlerClient(udpChannel))).start();
	}
	
	public void sendTcpCommand(CMD cmd) {
		// Simulates Button-press:
		System.out.println("Client sends new TCP command: " + cmd.toString());
		
		// Write TCP enum:
		(new Thread(new TcpHandlerClient(cmd, tcpChannel))).start();
	}

	
}
