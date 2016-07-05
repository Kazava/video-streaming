package server.connect;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.util.Iterator;
import java.util.Set;

public class VideoServer extends Server {
	String mode;
	UdpHandler udp;
	
	public VideoServer(int tcpPort, String mode) {
		super(tcpPort);
		this.mode = mode;
	}
	
	public void processMessage() throws IOException {
		//debug("Entered Loop...");
        selector.select();  // non-blocking
        Set<SelectionKey> keys = selector.selectedKeys();

        for (Iterator<SelectionKey> i = keys.iterator(); i.hasNext();) {
		    SelectionKey key = (SelectionKey) i.next();
		    i.remove();
		    Channel c = (Channel) key.channel();        
		
		    if (key.isAcceptable() && c == tcpserver) {
		        debug("Connecting as TCP");
		        // TODO: Read TCP
		        TcpHandler tcp = new TcpHandler(tcpserver);
		        String cmd = tcp.getCommand().name();
		        if (cmd == "PLAY") {
		        	udp = new UdpHandler("./res/do_it.mp4", cmd, this.mode);
		        	new Thread(udp).start();
		        }
		        if (cmd == "PAUSE") {
		        	udp.pause();	// Todo: if suspended then resume on play button
		        }
		        if (cmd == "STOP") {
		        	udp.stop();
		        }
		        
		    } else {
		    	debug("Not a TCP-connection!!!");
        	}
        }
	}
	
	public void shutdown() {
		this.isAlive = false;
		UdpHandler udp = new UdpHandler("--> Server has disconnected.");
		try {
			udp.writeUdpMessage();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
