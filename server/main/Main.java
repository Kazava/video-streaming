package main;

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
		
		video = new Video("video/example.avi"); //TODO: class: AviVideo
		
		vs = new VideoServer(8000, 8001);
		
		vs.run();
		
		gui = new GUI();
		
		gui.show();
		
		
		
		
		
		
		
	}
	

	
	
	
	
	
	
	
	
}
