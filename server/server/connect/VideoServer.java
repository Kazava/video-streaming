package server.connect;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.util.Iterator;
import java.util.Set;

public class VideoServer extends Server {
	
	public VideoServer(int tcpPort) {
		super(tcpPort);
	}
	
	public void processMessage() throws IOException {
		//debug("Entered Loop...");
        selector.selectNow();  // non-blocking
        Set<SelectionKey> keys = selector.selectedKeys();

        for (Iterator<SelectionKey> i = keys.iterator(); i.hasNext();) {
		    SelectionKey key = (SelectionKey) i.next();
		    i.remove();
		    Channel c = (Channel) key.channel();        
		
		    if (key.isAcceptable() && c == tcpserver) {
		        debug("Connecting as TCP");
		        (new Thread(new TcpHandler(tcpserver))).start(); // TCP-Read enum, UDP-Write String
		    } else {
		    	debug("Not a TCP-connection!!!");
        	}
        }
	}
	
	public void shutdown() {
		this.isAlive = false;
		TcpHandler tcp = new TcpHandler("--> Server has disconnected.");
		try {
			tcp.writeUdpMessage();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
