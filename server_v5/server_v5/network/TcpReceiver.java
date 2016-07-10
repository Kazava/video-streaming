package server_v5.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TcpReceiver implements Runnable {
	ServerSocketChannel tcpChannel;
	SocketChannel tcpClient;
	String messageString;
	int ordinal;
	CMD cmd;
	
	public TcpReceiver(Channel channel) {
		this.tcpChannel = (ServerSocketChannel) channel;
	}
	
	// Only reads TCP message and converts it to an enum:
	public void readTcp() {
		try {
			tcpClient = tcpChannel.accept();
	        if (tcpClient != null) {
				ByteBuffer messageBuffer = ByteBuffer.allocate(1);
				tcpClient.read(messageBuffer);
				tcpClient.close();
				messageBuffer.flip();
				ordinal = messageBuffer.get(0);
				cmd = CMD.values()[ordinal];
				messageString = cmd.name();
				System.out.println("Client wrote: " + messageString);
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		readTcp();
	}
	
}
