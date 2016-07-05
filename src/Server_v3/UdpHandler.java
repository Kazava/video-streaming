package Server_v3;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;


public class UdpHandler {
	SocketAddress clientAddress;
	String message;
	ByteBuffer message_bytes;
	DatagramChannel udpserver;
	CharBuffer charBuffer;
	
	UdpHandler(DatagramChannel udpserver, CharsetEncoder encoder, ByteBuffer receiveBuffer) throws IOException {
		acceptUdpConnection(udpserver, encoder, receiveBuffer);
	}
	
	public void acceptUdpConnection(DatagramChannel udpserver, CharsetEncoder encoder, ByteBuffer receiveBuffer) throws IOException {
		this.udpserver = udpserver;
		this.message_bytes = receiveBuffer;
	    this.clientAddress = this.udpserver.receive(this.message_bytes);
  	    if (clientAddress != null) {
  		    setMemberVariables(this.message_bytes, encoder);
  	    }
	}
	
	public void setMemberVariables(ByteBuffer receiveBuffer, CharsetEncoder encoder) 
								   throws UnsupportedEncodingException, CharacterCodingException {
		this.charBuffer = StandardCharsets.US_ASCII.decode(this.message_bytes);
		this.message = this.charBuffer.toString();
		System.out.println("---->" + this.message); 

        this.message_bytes = encoder.encode(CharBuffer.wrap("Server echoes: "+ this.message));
	}
	
	public void echoClientResponse() throws IOException {
        this.udpserver.send(this.message_bytes, this.clientAddress);
	}
}
