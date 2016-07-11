package server.connect;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class UdpHandler implements Runnable{
	String messageString;
	String cmd;
	String mode;

	ByteBuffer message_bytes;
	DatagramChannel channel;
	private boolean stop;
	private String videoName;
	private VideoCapture cap;
	private Mat frame;
	
	UdpHandler(String videoName, String cmd, String mode)  {
		this.videoName = videoName;
		this.messageString = cmd;
		this.mode = mode;
		this.stop = false;
	}
	
	UdpHandler(String message) {
		this.messageString = message;
	}
	
	// For echoing client commands:
	public void writeUdpMessage() throws IOException, InterruptedException {
		if (messageString != "") { 
			DatagramChannel channel = DatagramChannel.open();
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
	
	// Sends video stream:
	public void prepareVideo() throws UnknownHostException, IOException {
		DatagramChannel channel = DatagramChannel.open();
		cap = new VideoCapture(videoName); 		//"./res/do_it.mp4"
		if(cap.isOpened()){
	        System.out.println("Opened Media File.");
	    }
	    else{
	        System.out.println("Media File is not opened.");
	    }
		
		while (!this.stop) {
			try {
				Thread.sleep(8);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			frame = new Mat();
			cap.read(frame);
			
			Size sz = new Size(60,40);
		    
		    Imgproc.resize( frame, frame, sz);
		    Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
		    
		    int bufferSize = frame.channels()*frame.cols()*frame.rows();
		    byte [] b = new byte[bufferSize];
		    frame.get(0,0,b); // get all the pixels	
		    	    
			ByteBuffer buf = ByteBuffer.allocate(b.length);
			//System.out.println(b.length);
			buf.put(b);
			buf.flip();
			int bytesSent = channel.send(buf, new InetSocketAddress(InetAddress.getLocalHost(), 8002));
		}
	}
	
	public void handleMessage() throws IOException, InterruptedException {

		if (this.mode == "Normal") {
			prepareVideo();
		}
		else if (this.mode == "Debug") {
			writeUdpMessage();
		}	
	}
	
	public void pause()  {
		//wait();
	}
	
	public void stop() {
		this.stop = true;
	}

	public void run() {
		try {
			handleMessage();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
