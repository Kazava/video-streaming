package server.connect;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channel;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Server implements ServerInterface {
    int port;
    SocketAddress localPort;
    ServerSocketChannel tcpserver;
    Selector selector;
    
    /*
     * Constructor:
     * Initialize the server with tcp port of choice.
     * @params port		Port of choice
     */
    Server(int port) {
    	this.port = port;
    }
    
    /*
     * Set up the Server so that it can take TCP and UDP messages 
     * for the chosen port.
     */
    public void setupServer() {
    	try {
	    	configurePort();
	    	configureSelector();
    	} catch (Exception e) {
    	      System.err.println(e);
    	      System.exit(1);
    	}
    	debug("(Server) set to TCP-Port: " + this.port);
    }
    
    /*
     * Set up non-blocking TCP channel.
     */
    public void configurePort() throws IOException {
    	this.localPort = new InetSocketAddress(this.port);
    	this.tcpserver = ServerSocketChannel.open();
        this.tcpserver.socket().bind(this.localPort);      
        this.tcpserver.configureBlocking(false);
    }
    
    /*
     * Set up the selector so that it can dynamically choose
     * between TCP and UDP channels on one port.
     */
    public void configureSelector() throws IOException {
    	this.selector = Selector.open();
    	this.tcpserver.register(selector, SelectionKey.OP_ACCEPT);
    }
    
	public static void debug(String str) {
	 	System.out.println(str);
	}
    
    public void run() throws IOException {
    	setupServer();
    	for(;;) { // TODO: Exit for method, Threads
    		processMessage();
    	}
    }
    
    abstract public void processMessage() throws IOException;
}
