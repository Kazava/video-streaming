package server_v5.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TcpReceiver implements Runnable {
	ServerSocketChannel tcpChannel;
	SocketChannel tcpServer;
	String messageString;
	int ordinal;
	CMD cmd;
	
	public TcpReceiver(Channel channel) {
		this.tcpChannel = (ServerSocketChannel) channel;
	}
	
	// Only reads TCP message and converts it to an enum:
	public void readTcp() {
		try {
			tcpServer = tcpChannel.accept();
	        if (tcpServer != null) {
				ByteBuffer messageBuffer = ByteBuffer.allocate(1);
				tcpServer.read(messageBuffer);
				tcpServer.close();
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
