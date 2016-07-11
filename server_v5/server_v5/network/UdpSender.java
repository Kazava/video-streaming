package server_v5.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

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
			
			while(0 < video.getFrames(1)){
				buffer.clear();
				pixels = video.getFrames().poll().getPixels();
				for(int i = 0; i < size; i++){
					buffer.putInt(pixels[i]);
				}
				buffer.flip();
				udpChannel.send(buffer, new InetSocketAddress("localhost", 9999));
//				printBytes(buffer.array()); // for debugging
				try {
					Thread.sleep(32);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			udpChannel.close();
			System.out.println("Server finished sending video.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// For Debugging ByteBuffer:
	private void printBytes(byte[] in){
		System.out.println(" *** Bytes from Server ***");
		for(int i = 0; i < in.length; i++){
			System.out.print(String.format("%02X ",  in[i]));
		}
		System.out.print("\n");
	}
	
	
}
