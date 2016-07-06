package client.connect;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import javafx.scene.image.Image;

public class VideoClient extends Client {
	
	private UdpHandlerClient udpHandler;
	
	private boolean isAlive = true;
	
	Queue<Image> imageBuffer = new LinkedList<Image>();

	public VideoClient(int tcpPort, int udpPort) {
		super(tcpPort, udpPort);
		udpHandler =  new UdpHandlerClient(udpChannel, "Debug");
	}

	public void processMessage() throws IOException {
		
        (new UdpHandlerClient(udpChannel, "Debug")).run();
	}
	
	public void sendTcpCommand(CMD cmd) {
		// Simulates Button-press:
		System.out.println("Client sends new TCP command: " + cmd.toString());
		
		// Write TCP enum:
		(new Thread(new TcpHandlerClient(cmd, tcpChannel))).start();
	}
	
	public void receiveAsUdp() {
		UdpHandlerClient udp = new UdpHandlerClient(udpChannel, "Normal");
		
		while(isAlive){
			try {
				Thread.sleep(8); // 60fps  
				imageBuffer.add(udp.renderVideo());
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setIsAlive(boolean isAlive){
		this.isAlive = isAlive;
	}
	
	public Image getImage(){
		return imageBuffer.poll();
	}
	
	//TODO: Make server not listen to udp constantly, make method for opening and disallowing udp receive
	// so that GUI can call udpHandlerClient functions.

	
}
