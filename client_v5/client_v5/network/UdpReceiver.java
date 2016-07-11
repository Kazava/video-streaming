package client_v5.network;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.DatagramChannel;

import client_v5.gui.Gui;
import client_v5.video.Frame;
import client_v5.video.Video;

public class UdpReceiver implements Runnable {
	private DatagramChannel udpChannel;
	private String messageString;
	private ByteBuffer byteBuffer;
	private byte[] byteArray;
	
	boolean isAlive;
	
	/*
	 * Just reads udp messages
	 */
	UdpReceiver(DatagramChannel channel) {
		this.udpChannel = channel;
		isAlive = true;
	}
	


	@Override
	public void run() {
		
		receiveVideo();
		
	}
	
	public void receiveVideo(){
		
		int width = 42, height = 32;
		
		int size = width*height;
		
		byteBuffer = ByteBuffer.allocate(size * 8);
		
		int[] pixels = new int[size];
		
		System.out.println("waits for packages ...");
		
		try {
			DatagramChannel c = DatagramChannel.open();
			c.socket().bind(new InetSocketAddress("localhost",9999));

			while(isAlive){
				
				Thread.sleep(16);
				
				byteBuffer.clear();

				c.receive(byteBuffer);
				
				byteBuffer.flip();
				
				int temp = 0;
				
				for(int i = 0; i < size; i++){
					temp = byteBuffer.getInt();
					pixels[i] = (0xff000000 | temp);
				}
				
				if((temp & 0xff) != 0x00)System.out.println("Client just received some bytes!");
				
				Frame frame = new Frame(pixels);
				Gui.writeImg(frame, width, height);
				
			}
		} catch (IOException | InterruptedException e1) {
			e1.printStackTrace();
		}
		
	}
	
	private void printBytes(byte[] in){
		System.out.println(" *** Bytes from Server ***");
		for(int i = 0; i < in.length; i++){
			System.out.print(String.format("%02X ",  in[i]));
		}
		System.out.print("\n");
	}
	
}
