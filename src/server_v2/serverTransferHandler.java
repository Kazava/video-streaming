package server_v2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class serverTransferHandler {
	
	String imgPath = "res/image.png";
	File image = null;
	BufferedImage bi = null;
	
	public serverTransferHandler(){
		
		image = new File(imgPath);
		
		try {
			bi = ImageIO.read(image);
		} catch (IOException e) {
			System.err.println("File not found!!!");
			e.printStackTrace();
		}
	}
	
	public void send(OutputStream os){
		
		try {
			ImageIO.write(bi, "PNG" , os);
		} catch (IOException e) {
			System.err.println("Buffered Image stream not found!!!");
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
}
