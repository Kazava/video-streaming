package main;

import java.util.logging.Level;
import java.util.logging.Logger;

import compression.Compression;
import connect.ServerInterface;
import connect.VideoServer;
import gui.GUI;
import video.Video;

public class Main {
	
	static ServerInterface vs;
	static GUI gui;
	static Compression comp;
	static Video video;
	
	
	public static void main(String[] args){
		
		//video = new Video("video/example.avi"); //TODO: class: AviVideo
		
		vs = new VideoServer(8000, 8001);
			try {
		vs.run();	
		} catch (java.io.IOException e) {
	          Logger l = Logger.getLogger(Main.class.getName());
	          l.log(Level.WARNING, "IOException in Main", e);
	    } catch (Throwable t) {
	          Logger l = Logger.getLogger(Main.class.getName());
	          l.log(Level.SEVERE, "FATAL error in Main", t);
	          System.exit(1);
	    }
		
		//gui = new GUI();
		
		//gui.show();
	}
	

	
	
	
	
	
	
	
	
}
