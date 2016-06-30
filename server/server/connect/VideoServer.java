package server.connect;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VideoServer extends Server {
	
	public VideoServer(int tcpPort) {
		super(tcpPort);
	}
	
	public void processMessage() throws IOException {
		//debug("Entered Loop...");
        selector.selectNow();  // non-blocking
        Set<SelectionKey> keys = selector.selectedKeys();

        for (Iterator<SelectionKey> i = keys.iterator(); i.hasNext();) {
		    SelectionKey key = (SelectionKey) i.next();   // Get a key from the set, and remove it from the set
		    i.remove();
		    Channel c = (Channel) key.channel();          // Get the channel associated with the key
		
		    if (key.isAcceptable() && c == tcpserver) {
		        debug("Connecting as TCP");
		        //TcpHandler tcpHandler = new TcpHandler(tcpserver, encoder);
		        (new Thread(new TcpHandler(tcpserver, encoder))).start();
		        //tcpHandler.echoClientResponse();
		        debug("Sent TCP");  
		    } else {
		    	debug("Not a TCP-connection!!!");
		    /*
		    else if (key.isReadable() && c == udpserver) {
		        debug("Connecting as UDP");
		        //UdpHandler udpHandler = new UdpHandler(udpserver, encoder, receiveBuffer);
		        (new Thread(new UdpHandler(udpserver, encoder, receiveBuffer))).start();
		        //udpHandler.echoClientResponse();
		        debug("Sent UDP");  
		        */
        	}
        }
	}
	
	public static void debug(String str) {
	 	System.out.println(str);
	}

}
