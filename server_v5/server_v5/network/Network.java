package server_v5.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;


/*
 * Just listens to both TCP and UDP connections.
 */
public class Network implements NetworkInterface {
	private int tcpPort;
	private int udpPort;
	private SocketAddress localTcpPort;
	private SocketAddress localUdpPort;
	private ServerSocketChannel tcpChannel;
	private DatagramChannel udpChannel;
	private Selector selector;
	
	public Network(int tcpPort, int udpPort) {
		this.tcpPort = tcpPort;
		this.udpPort = udpPort;
		try {
			setupNetwork();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setupNetwork() throws IOException {
	    localTcpPort = new InetSocketAddress(tcpPort);
	    localUdpPort = new InetSocketAddress(udpPort);
	    
    	tcpChannel = ServerSocketChannel.open();
	    tcpChannel.socket().bind(localTcpPort);
	    tcpChannel.configureBlocking(false);
	    
	    udpChannel = DatagramChannel.open();
	    udpChannel.socket().bind(localUdpPort);
	    udpChannel.configureBlocking(false);
	    
	    selector = Selector.open();
	    tcpChannel.register(selector, SelectionKey.OP_ACCEPT);
	    udpChannel.register(selector, SelectionKey.OP_READ);	    
	}

	/*
	 * Waits for either TCP or UDP connection and returns the 
	 * connection type.
	 */
	@Override
	public Channel choosingConnection() {
		try {
			selector.select(); // stop here!
			//System.out.println("Selector chose something!");
			Set<SelectionKey> keys = selector.selectedKeys();
			for (Iterator<SelectionKey> i = keys.iterator(); i.hasNext();) {
	            SelectionKey key = (SelectionKey) i.next();
	            i.remove();
	            Channel c = (Channel) key.channel();
	            if (key.isAcceptable() && c == tcpChannel)
	            	return tcpChannel;
	            else if (key.isReadable() && c == udpChannel) 
	            	return udpChannel;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
