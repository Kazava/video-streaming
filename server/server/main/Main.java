package server.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import client.compression.Compression;
import javafx.application.Application;
import server.connect.ServerInterface;
import server.connect.VideoServer;
import server.gui.Gui;
import server.main.Main;
import server.video.Video;

public class Main {
	
	static ServerInterface vs;
	static Gui gui;
	static Compression comppression;
	static Video video;
	
	
	public static void main(String[] args){
		
		//video = new Video("video/example.avi"); //TODO: class: AviVideo
		
		Application.launch(Gui.class, args);
		
		vs = new VideoServer(8001);
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
	}
	

	
	
	
	
	
	
	
	
}
