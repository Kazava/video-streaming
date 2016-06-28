package server.connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharsetEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TcpHandler {
	InputStreamReader isr;
	BufferedReader br;
	String message;
	ByteBuffer message_bytes;
	SocketChannel client;
	
	TcpHandler(ServerSocketChannel tcpserver, CharsetEncoder encoder) throws IOException {
		acceptTcpConnection(tcpserver, encoder);
	}
	
	public void acceptTcpConnection(ServerSocketChannel tcpserver, CharsetEncoder encoder) throws IOException {
		this.client = tcpserver.accept();
        if (this.client != null) {
        	setMemberVariables(encoder);
        }
	}
	
	public void setMemberVariables(CharsetEncoder encoder) throws IOException {
		this.isr = new InputStreamReader(this.client.socket().getInputStream());
		this.br = new BufferedReader(isr);
		this.message = br.readLine().trim();
		this.message_bytes = encoder.encode(CharBuffer.wrap("Server echoes: "+ this.message));
	}
	
	public void echoClientResponse() throws IOException {
        this.client.write(this.message_bytes);
        this.client.close();
	}
}
