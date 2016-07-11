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
	private ByteBuffer buffer;
	private byte[] byteArray;
	
	boolean isAlive;
	
	/*
	 * Just reads udp messages
	 */
	UdpReceiver() {
		isAlive = true;
	}
	


	@Override
	public void run() {
		
		receiveVideo();
		
	}
	
	public void receiveVideo(){
		
        try {
            
            int packageSize = 2000;
            
            buffer = ByteBuffer.allocate(packageSize * 4);
            udpChannel = DatagramChannel.open();
            udpChannel.socket().bind(new InetSocketAddress("localhost",9999));

            while(isAlive){
                
                int[]pixels = new int[720*480];
                int numOfPixels, width, height;
                int remaining = Integer.MAX_VALUE;
                
                int rounds = 0;
                
                do{
                    
                    buffer.clear();
                    udpChannel.receive(buffer);
                    buffer.flip();
                    int headerSize = 4;

                    /** read header (4 bytes)**/
                    numOfPixels = buffer.getInt(); // number of pixels in per frame
                    width = buffer.getInt(); // number of pixels width per frame
                    height = buffer.getInt(); // number of pixels height per frame
                    remaining = buffer.getInt(); // number of pixels left including the the package data
                    
                    int fill = packageSize - headerSize;
                    
                    int durations = 0;
                    while(durations < fill && remaining > 0){
                        int index = remaining -1;
                        pixels[index] = (0xff000000 | buffer.getInt());
                        remaining--;
                        durations++;
                    }
                    
                }while(remaining > 0);
                
                Frame frame = new Frame(pixels);
                Gui.writeImg(frame, width, height);
                
                Thread.sleep(16);
                
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
