package client.connect;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TcpHandlerClient implements Runnable {
	String message;
	ByteBuffer messageBytes;
	SocketChannel client;
	
	TcpHandlerClient(String message, SocketChannel tcp) {
		this.client = tcp;
		this.message = message;
	}
	
	public void writeCommand() throws IOException {
    	this.client = SocketChannel.open(new InetSocketAddress("localhost", 8001));	// connect to server
		this.messageBytes = ByteBuffer.allocate(48);
		this.messageBytes.clear();
		this.messageBytes.put(this.message.getBytes());
		
		this.messageBytes.flip();

		while(this.messageBytes.hasRemaining()) {
		    client.write(this.messageBytes);
		}
		
		this.client.close();
		System.out.println("Command sent is: " + this.message);
	}
	
	public void run() {
		try {
			writeCommand();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
