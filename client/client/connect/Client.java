package client.connect;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

abstract class Client implements ClientInterface {
	int tcpPort;
    int udpPort;
	CharsetEncoder encoder;
    SocketChannel tcpChannel;
    DatagramChannel udpChannel;
    Selector selector;
	
    /*
     * Constructor:
     * Initialize the client with tcp- and udp-ports of choice.
     * @params port		Port of choice
     */
    Client(int tcpPort, int udpPort) {
    	this.tcpPort = tcpPort;
    	this.udpPort = udpPort;
    }
	
	
	public void setupClient() {
		this.encoder = Charset.forName("US-ASCII").newEncoder();
    	try {
	    	configurePort();
	    	configureSelector();
    	} catch (Exception e) {
    		System.out.println("oops");
    	    System.err.println(e);
    	    System.exit(1);
    	}
    	System.out.println("(Client) set to TCP-Port: " + this.tcpPort);
    	System.out.println("(Client) set to UDP-Port: " + this.udpPort);
	}
	
	public void configurePort() throws IOException {
		this.udpChannel = DatagramChannel.open();
		this.udpChannel.socket().bind(new InetSocketAddress(udpPort));
        this.udpChannel.configureBlocking(false);
        System.out.println("Ports configured!");
	}

	public void configureSelector() throws IOException {
		this.selector = Selector.open();
        this.udpChannel.register(selector, SelectionKey.OP_READ);
        System.out.println("Selector set!");
	}
	
	public static void debug(String str) {
	 	System.out.println(str);
	}
	
	public void run() throws IOException {
		setupClient();
		for(;;){
			processMessage();
		}
	}
	
	abstract public void processMessage() throws IOException;
	abstract public void sendTcpCommand(CMD cmd);

	
}
