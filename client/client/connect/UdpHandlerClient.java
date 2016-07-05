package client.connect;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UdpHandlerClient implements Runnable {
	private DatagramChannel udpChannel;
	private String message;
	private ByteBuffer byteBuffer;
	private byte[] byteArray;
	
	UdpHandlerClient(DatagramChannel udpChannel) {
		this.udpChannel = udpChannel;
	}
	
	public void readUdpMessage() throws IOException, InterruptedException {
//		System.out.println("sleeping 3s before reading...");
        Thread.sleep(16); // 60fps
        
		this.byteBuffer = ByteBuffer.allocate(48); // probably needs more space
		
		this.byteBuffer.clear();
		this.udpChannel.receive(byteBuffer);
		
		
//		byteArray = new byte[byteBuffer.remaining()];
		
//		System.out.println(byteBuffer.remaining());
//		byteBuffer.get(byteArray);
		
		
		byteArray = byteBuffer.array();
		
		if(byteArray[0] != 0x00)
			printBytes(byteArray);
		
	}
		
	public void run() {
		try {
			readUdpMessage();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void printBytes(byte[] in){
		System.out.println(" *** Bytes from Server ***");
		for(int i = 0; i < in.length; i++){
			System.out.print(String.format("%02X ",  in[i]));
		}
		System.out.print("\n");
	}
	
}
