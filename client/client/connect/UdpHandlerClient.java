package client.connect;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UdpHandlerClient implements Runnable {
	private DatagramChannel udpChannel;
	private String message;
	private ByteBuffer messageBytes;
	
	UdpHandlerClient(DatagramChannel udpChannel) {
		this.udpChannel = udpChannel;
	}
	
	public void readUdpMessage() throws IOException, InterruptedException {
		System.out.println("sleeping 3s before reading...");
        Thread.sleep(3000);
		this.messageBytes = ByteBuffer.allocate(48);		// probably needs more space
		this.messageBytes.clear();
		this.udpChannel.receive(this.messageBytes);
		this.message = new String(this.messageBytes.array()).trim();
		if (this.message.length() > 1) {
			System.out.println("Server wrote as UDP: " + this.message);
		}
	}
		
	public void run() {
		try {
			readUdpMessage();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
