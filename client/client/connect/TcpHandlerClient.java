package client.connect;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TcpHandlerClient implements Runnable {
	CMD cmd;
	ByteBuffer messageBytes;
	SocketChannel client;
	
	TcpHandlerClient(CMD cmd, SocketChannel tcp) {
		this.client = tcp;
		this.cmd = cmd;
	}
	
	public void writeCommandToClient() throws IOException {
    	this.client = SocketChannel.open(new InetSocketAddress("localhost", 8001));	// connect to server
		this.messageBytes = ByteBuffer.allocate(1);
		this.messageBytes.clear();
		this.messageBytes.put(new byte[]{(byte)this.cmd.ordinal()});	
		this.messageBytes.flip();
		while(this.messageBytes.hasRemaining()) {
		    client.write(this.messageBytes);
		}
		this.client.close();
		System.out.println("Command sent is: " + this.cmd.name());
	}
	
	public void run() {
		try {
			writeCommandToClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
