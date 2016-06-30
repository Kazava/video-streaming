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

/*
 * TCP connection with client. Reads Clients commands and instantiates UdpHandler when
 * the right command was given. 
 * 
 *  Must be able to take in multiple TCP-commands while UDP-send is active.
 */
public class TcpHandler implements Runnable {
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
        System.out.println("Server accepted!");
		this.client.configureBlocking(false);
        if (this.client != null) {
        	setMemberVariables(encoder);	//TODO: read
        }
	}
	
	public void setMemberVariables(CharsetEncoder encoder) throws IOException {
		this.message_bytes = ByteBuffer.allocate(256);
		this.client.read(this.message_bytes);
		this.message = new String(this.message_bytes.array()).trim();
		System.out.println("Client wrote: " + this.message);
        //System.out.println("Server Members set!");

		/*
		this.isr = new InputStreamReader(this.client.socket().getInputStream());
		this.br = new BufferedReader(isr);
		this.message = br.readLine().trim();
		this.message_bytes = encoder.encode(CharBuffer.wrap("Server echoes: "+ this.message));
		*/

	}
	
	public void echoClientResponse() throws IOException, InterruptedException {
        this.client.write(this.message_bytes);
        System.out.println("TCP sleeping 3s...");
        Thread.sleep(3000);
        this.client.close();
        System.out.println("Server response sent!");
	}

	public void run() {
		try {
			echoClientResponse();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
