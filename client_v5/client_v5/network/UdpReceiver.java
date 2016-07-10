package client_v5.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.DatagramChannel;

public class UdpReceiver implements Runnable {
	private DatagramChannel udpChannel;
	private String messageString;
	private ByteBuffer byteBuffer;
	private byte[] byteArray;
	
	/*
	 * Just reads udp messages
	 */
	UdpReceiver(Channel channel) {
		this.udpChannel = (DatagramChannel) channel;
	}
	
	public void readUdpMessage() throws IOException, InterruptedException {
		byteBuffer = ByteBuffer.allocate(48); // probably needs more space		
		byteBuffer.clear();
		udpChannel.receive(byteBuffer);				
		byteArray = byteBuffer.array();
	}

	@Override
	public void run() {
		try {
			readUdpMessage();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
