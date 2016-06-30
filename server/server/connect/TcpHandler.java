package server.connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharsetEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * TCP connection with client. Reads Clients commands and instantiates UdpHandler when
 * the right command was given. 
 * 
 *  Must be able to take in multiple TCP-commands while UDP-send is active.
 */
public class TcpHandler implements Runnable {
	String message;
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
		this.messageBytes = ByteBuffer.allocate(256);
		this.client.read(this.messageBytes);
		this.message = new String(this.messageBytes.array()).trim();
		System.out.println("Client wrote: " + this.message);
	}
	
	// For testing in run();
	public void echoClientResponse() throws IOException, InterruptedException {
        this.client.write(this.messageBytes);
        System.out.println("TCP sleeping 3s...");
        Thread.sleep(3000);
        this.client.close();
        System.out.println("Server response sent!");
	}
	
	public void writeUdpMessage() throws IOException, InterruptedException {
		//Echo as UDP:
		if (message != "") { 
			DatagramChannel channel = DatagramChannel.open();
			channel.socket().bind(new InetSocketAddress(9999)); // Random address
			ByteBuffer buf = ByteBuffer.allocate(48);
			buf.clear();
			buf.put(message.getBytes());
			buf.flip();
	        //System.out.println("sleeping 3s...");
	        //Thread.sleep(3000);
			channel.send(buf, new InetSocketAddress("localhost", 8002));
			channel.close();
			String newMessage = new String(buf.array()).trim();

	        System.out.println("Server UDP response sent! Sent: " + newMessage);
		}
	}

	public void run() {
		try {
			//echoClientResponse();
			writeUdpMessage();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
