package client_v5.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/*
 * Just sends TCP messages.
 */
public class TcpSender implements Runnable {
	private CMD cmd;
	private ByteBuffer messageBuffer;
	private SocketChannel client;
	
	public TcpSender(CMD cmd) {
		this.cmd = cmd;
	}
	
	public void writeCommandToServer() throws IOException {
    	this.client = SocketChannel.open(new InetSocketAddress("localhost", 8001));	// connect to server
		this.messageBuffer = ByteBuffer.allocate(1);
		this.messageBuffer.clear();
		this.messageBuffer.put(new byte[]{(byte)this.cmd.ordinal()});	
		this.messageBuffer.flip();
		while(this.messageBuffer.hasRemaining()) {
		    client.write(this.messageBuffer);
		}
		this.client.close();
		System.out.println("Command sent is: " + this.cmd.name());
	}
	
	public void run() {
		try {
			writeCommandToServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
