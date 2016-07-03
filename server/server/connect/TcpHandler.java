package server.connect;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;

/*
 * TCP connection with client. Reads Clients commands and instantiates UdpHandler when
 * the right command was given. 
 * 
 *  Must be able to take in multiple TCP-commands while UDP-send is active.
 */
public class TcpHandler implements Runnable {
	CMD cmd;
	int ordinal;
	String messageString;
	ByteBuffer messageBytes;
	SocketChannel client;
	
	TcpHandler(ServerSocketChannel tcpserver) throws IOException {
		acceptTcpConnection(tcpserver);
	}
	
	public void acceptTcpConnection(ServerSocketChannel tcpserver) throws IOException {
		this.client = tcpserver.accept();
        System.out.println("Server accepted!");
		this.client.configureBlocking(false);
        if (this.client != null) {
        	readTcpMessage();
        }
	}
	
	public void readTcpMessage() throws IOException {
		this.messageBytes = ByteBuffer.allocate(1);
		this.client.read(this.messageBytes);
		this.messageBytes.flip();
		this.ordinal = messageBytes.get(0);
		this.cmd = CMD.values()[this.ordinal];
		this.messageString = this.cmd.name();
		System.out.println("Client wrote: " + this.messageString);
	}
	
	public void writeUdpMessage() throws IOException, InterruptedException {
		if (messageString != "") { 
			DatagramChannel channel = DatagramChannel.open();
			channel.socket().bind(new InetSocketAddress(9999)); // Random address, not sure if needed
			ByteBuffer buf = ByteBuffer.allocate(48);
			buf.clear();
			buf.put(messageString.getBytes());
			buf.flip();
			channel.send(buf, new InetSocketAddress("localhost", 8002));
			channel.close();
			String newMessage = new String(buf.array()).trim();
	        System.out.println("Server UDP response sent! Sent: " + newMessage);
		}
	}
	
	public String writeMessage() {
		
		return "lol";
	}

	public void run() {
		try {
			writeUdpMessage();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
