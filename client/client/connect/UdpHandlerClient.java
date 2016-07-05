package client.connect;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class UdpHandlerClient implements Runnable {
	private DatagramChannel udpChannel;
	private String mode;
	private String message;
	private ByteBuffer byteBuffer;
	private byte[] byteArray;
	
	UdpHandlerClient(DatagramChannel udpChannel, String mode) {
		this.udpChannel = udpChannel;
		this.mode = mode;
	}
	
	public void readUdpMessage() throws IOException, InterruptedException {
//		System.out.println("sleeping 3s before reading...");
        Thread.sleep(16); // 60fps      
		this.byteBuffer = ByteBuffer.allocate(48); // probably needs more space		
		this.byteBuffer.clear();
		this.udpChannel.receive(byteBuffer);		
//		byteArray = new byte[byteBuffer.remaining()];	
//		System.out.println(byteBuffer.remaining());
//		byteBuffer.get(byteArray);		
		byteArray = byteBuffer.array();
		if(byteArray[0] != 0x00)
			printBytes(byteArray);
	}
	
	public void renderVideo() throws InterruptedException, IOException {
		
		Thread.sleep(16); // 60fps      
		this.byteBuffer = ByteBuffer.allocate(48); // probably needs more space		
		this.byteBuffer.clear();
		this.udpChannel.receive(byteBuffer);	
		byteArray = byteBuffer.array();
		
		
	}
	
	// http://blog.professional-webworkx.de/javafx-convert-a-bytes-array-to-a-javafx-image/
	public Image convertToJavaFXImage(byte[] raw, final int width, final int height) {
		WritableImage image = new WritableImage(width, height);
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(raw);
			BufferedImage read = ImageIO.read(bis);
			image = SwingFXUtils.toFXImage(read, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public void handleMessage() throws IOException, InterruptedException {
		if (this.mode == "Normal") {
			renderVideo();
		}
		else if (this.mode == "Debug") {
			readUdpMessage();
		}	
	}
		
	public void run() {
		try {
			handleMessage();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
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
