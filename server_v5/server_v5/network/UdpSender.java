package server_v5.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import server_v5.compression.Compression;
import server_v5.video.Video;

public class UdpSender implements Runnable {

	private DatagramChannel udpChannel;
	private ByteBuffer buffer;
	private boolean isPaused;
	Video video;
	
	public UdpSender(Video video){
		this.video = video;
		this.isPaused = false;
	}

	@Override
	public void run() { sendUdp(); }
	
	public void sendUdp(){
        try {
            /**
             * bei 420 x 320 pixels wird ein image unkomprimiert
             * in 68 UDP packages verteilt.
             */

            int[] pixels;
            int packageSize = 2000;
            
            udpChannel = DatagramChannel.open();
            buffer = ByteBuffer.allocate(packageSize * 4);
            
            while(0 < video.getFrames(1) && !isPaused){
                
                pixels = Compression.encode(video.getFrames().poll().getPixels());
                
                int remaining = pixels.length;
                int numOfPixels = pixels.length;
                int headerSize = 4;
                int rounds = 0;
                
                while(remaining > 0){
                    buffer.clear();
                    
                    /** send header (4 bytes)**/
                    buffer.putInt(numOfPixels); // number of pixels in per frame
                    buffer.putInt(video.getWidth()); // number of pixels width per frame
                    buffer.putInt(video.getHeight()); // number of pixels height per frame
                    buffer.putInt(remaining); // number of pixels left including the the package data
                    
                    int fill = packageSize - headerSize;
                    for(int i = 0; i < fill && remaining > 0; i++){
                        remaining--;
                        buffer.putInt(pixels[remaining]);
                    }
                    buffer.flip();
                    udpChannel.send(buffer, new InetSocketAddress("localhost", 9999));
                    
                }
                
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
	
	public void pauseSender() {
		
	}
	
	// For Debugging ByteBuffer:
	private void printBytes(byte[] in){
		System.out.println(" *** Bytes from Server ***");
		for(int i = 0; i < in.length; i++){
			System.out.print(String.format("%02X ",  in[i]));
		}
		System.out.print("\n");
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public void pause() {
		isPaused = isPaused ? false : true;
		if(isPaused)
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else this.notify();
	}
	
	
}
