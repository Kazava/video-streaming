package server.connect;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import client.connect.CMD;

/*
 * TCP connection with client. Reads Clients commands and instantiates UdpHandler when
 * the right command was given. 
 * 
 *  Must be able to take in multiple TCP-commands while UDP-send is active.
 */
public class TcpHandler implements Runnable {
	CMD cmd;
	int ordinal;
	String messageString;
	ByteBuffer messageBytes;
	SocketChannel client;
	private boolean stop;
	private String videoName;
	private VideoCapture cap;
	private Mat frame;
	

	TcpHandler(ServerSocketChannel tcpserver) throws IOException {
		acceptTcpConnection(tcpserver);
	}
	
	TcpHandler(String udpMessage) {
		this.messageString = udpMessage;
		System.out.println("Preparing to shutdown");
	}
	
	public void acceptTcpConnection(ServerSocketChannel tcpserver) throws IOException {
		this.client = tcpserver.accept();
        System.out.println("Server accepted!");
		this.client.configureBlocking(false);
        if (this.client != null) {
        	readTcpMessage();
        }
	}
	
	public void readTcpMessage() throws IOException {
		this.messageBytes = ByteBuffer.allocate(1);
		this.client.read(this.messageBytes);
		this.messageBytes.flip();
		this.ordinal = messageBytes.get(0);
		this.cmd = CMD.values()[this.ordinal];
		this.messageString = this.cmd.name();
		System.out.println("Client wrote: " + this.messageString);
	}
	
	public CMD getCommand() {
		return this.cmd;
	}
	
	
	/*
	 * Everything after this part is not needed anymore!!!!
	 */
	
	
	
	public void writeUdpMessage() throws IOException, InterruptedException {
		if (messageString != "") { 
			DatagramChannel channel = DatagramChannel.open();
			//channel.socket().bind(new InetSocketAddress(9999)); // Random address, not sure if needed
			ByteBuffer buf = ByteBuffer.allocate(48);
			buf.clear();
			buf.put(messageString.getBytes());
			buf.flip();
			channel.send(buf, new InetSocketAddress("localhost", 8002));
			channel.close();
			String newMessage = new String(buf.array()).trim();
	        System.out.println("Server UDP response sent! Sent: " + newMessage);
		}
	}
	
	public void handleTcpMessage() throws IOException {
		DatagramChannel channel = DatagramChannel.open();
		this.videoName = "./res/do_it.mp4"; 				// TODO: Make server choose with GUI

		if (this.cmd.name() == "PLAY") {
			prepareVideo(channel, this.videoName);
		}
		
		ByteBuffer buf = ByteBuffer.allocate(48);

		
	}
	
	public void prepareVideo(DatagramChannel channel, String videoName) throws UnknownHostException, IOException {
		cap = new VideoCapture(videoName); 		//"./res/do_it.mp4"
		if(cap.isOpened()){
	        System.out.println("Opened Media File.");
	    }
	    else{
	        System.out.println("Media File is not opened.");
	    }
		frame = new Mat();
		cap.read(frame);
		
		Size sz = new Size(60,40);
	    
	    Imgproc.resize( frame, frame, sz );
	    Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
	    
	    int bufferSize = frame.channels()*frame.cols()*frame.rows();
	    byte [] b = new byte[bufferSize];
	    frame.get(0,0,b); // get all the pixels	
	    	    
		ByteBuffer buf = ByteBuffer.allocate(b.length);
		//System.out.println(b.length);
		buf.put(b);
		buf.flip();
		int bytesSent = channel.send(buf, new InetSocketAddress(InetAddress.getLocalHost(), 10000));
	}
	


	public void run() {
		try {
			writeUdpMessage();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
