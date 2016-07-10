package client_v5.network;

import java.io.File;
import java.io.IOException;
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
	UdpReceiver(Channel channel) {
		this.udpChannel = (DatagramChannel) channel;
		isAlive = true;
	}
	


	@Override
	public void run() {
		
		receiveVideo();
		
	}
	
	public void receiveVideo(){
		
		int width = 42, height = 32;
		
		int size = width*height;
		
		this.byteBuffer = ByteBuffer.allocate(size * 8);
		
		int[] pixels = new int[size];
		
		Video video = new Video(new File("."), width, height);
		System.out.println("-> POSER");
		
		while(isAlive){
			
	        try {
				Thread.sleep(32);
				this.byteBuffer.clear();
				this.udpChannel.receive(byteBuffer);	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   	


			for(int i = 0; i < size; i++){
				pixels[i] = byteBuffer.getInt();
			}
			
			Frame frame = new Frame(pixels);
//			video.addFrame(frame);
			
			Gui.writeImg(frame, width, height);
			
		}
		
		
		
		
	}
	
}
