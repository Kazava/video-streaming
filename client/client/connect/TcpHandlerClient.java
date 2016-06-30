package client.connect;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TcpHandlerClient implements Runnable {
	String message;
	ByteBuffer buf;
	SocketChannel client;
	
	TcpHandlerClient(String message, SocketChannel tcp) {
		this.client = tcp;
		this.message = message;
	}
	
	public void writeCommand() throws IOException {
    	this.client = SocketChannel.open(new InetSocketAddress("localhost", 8001));	// connect to server
		this.buf = ByteBuffer.allocate(48);
		this.buf.clear();
		this.buf.put(message.getBytes());
		
		this.buf.flip();

		while(this.buf.hasRemaining()) {
		    client.write(this.buf);
		}
		this.client.close();
		System.out.println("Command sent!");
	}
	
	public void run() {
		try {
			writeCommand();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
