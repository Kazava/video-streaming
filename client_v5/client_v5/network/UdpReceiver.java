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
		
		this.byteBuffer = ByteBuffer.allocate(size * 8);
		
		int[] pixels = new int[size];
		
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
			
			if(pixels[0] != 0x00){
				System.out.println("pixels " + pixels[0]);
			}
			
			Gui.writeImg(frame, width, height);
			
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
