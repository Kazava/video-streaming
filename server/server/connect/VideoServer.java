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
		    SelectionKey key = (SelectionKey) i.next();
		    i.remove();
		    Channel c = (Channel) key.channel();        
		
		    if (key.isAcceptable() && c == tcpserver) {
		        debug("Connecting as TCP");
		        (new Thread(new TcpHandler(tcpserver))).start();
		    } else {
		    	debug("Not a TCP-connection!!!");
        	}
        }
	}
	


}