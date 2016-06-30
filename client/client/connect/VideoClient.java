package client.connect;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import server.connect.TcpHandler;

public class VideoClient extends Client {

	public VideoClient(int tcpPort, int udpPort) {
		super(tcpPort, udpPort);
	}

	public void processMessage() throws IOException {
 
        // Send TCP Commands as enums:
        sendTcpCommand(CMD.PLAY);
        
        // Read UDP Messages:
        (new Thread(new UdpHandlerClient(udpChannel))).start();
	}
	
	// TODO: send only enums instead of Strings!
	public void sendTcpCommand(CMD cmd) {
		
		System.out.println("(New Thread) Write anything to proceed: ");
		String message = "";
		Scanner scan = new Scanner(System.in);
		message = scan.nextLine();
		
		String myMessage;
		switch (cmd){
		case PLAY:
			myMessage =  "Play something now!";
			break;
		case PAUSE:
			myMessage =  "Pause it now!";
			break;
		case STOP:
			myMessage =  "STOP! Hammer-time!";
			break;
		case MEDIA_REQUEST:
			myMessage = "I want it ALL!";
			break;
		default:
			myMessage =  "Wupps!";
			break;
		}
		(new Thread(new TcpHandlerClient(myMessage, tcpChannel))).start();
	}

	
}
