package server_v5.network;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import server_v5.video.Frame;
import server_v5.video.Video;

public class UdpSender implements Runnable {

	private DatagramChannel udpChannel;
	private ByteBuffer buffer;
	Video video;
	
	public UdpSender(Video video){
		this.video = video;
	}

	@Override
	public void run() { sendUdp(); }
	
	public void sendUdp(){
		try {
			udpChannel = DatagramChannel.open();
			buffer = ByteBuffer.allocate(video.getByteSizeOfFrame()); // The new buffer's capacity, in bytes
			
			int size = video.getFramesSize();
			int[] pixels;
			
			/** set header **/
//			buffer.putInt(115);
//			buffer.putInt(70);
			
			while(0 < video.getFrames(1)){
				buffer.clear();
				
				pixels = video.getFrames().poll().getPixels();
				
				for(int i = 0; i < size; i++){
					buffer.putInt(pixels[i]);
				}
				
				udpChannel.send(buffer, new InetSocketAddress("localhost", 9002));
				
				try {
					Thread.sleep(32);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			udpChannel.close();
			System.out.println("Server finished sending video.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
