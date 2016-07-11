package client_tcp;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class clientTransferHandler {
	
	BufferedImage bi = null;
	
	public clientTransferHandler(){
		
	}
	
	public void receive(InputStream is){
		try {
			BufferedImage image = ImageIO.read(is);
		} catch (IOException e) {
			log("Input stream is not available yet!");
			e.printStackTrace();
		}
		
		File outputfile = new File("res/output.png");
		try {
			ImageIO.write(bi, "PNG", outputfile);
		} catch (IOException e) {
			log("Cant write image from buffered image!");
			e.printStackTrace();
		}
	}
	
	
    // Because "System.out.println" is too long to type...
    public static void log(String str){
    	System.out.println(str);
    }
	
}
