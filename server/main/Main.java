package main;

public class Main {
	
	Server server;
	GUI gui;
	Compression comp;
	Video video;
	
	
	public static void main(String[] args){
		
		video = new Video(video/example.avi);
		
		server = new Server(8000, 8001);
		
		server.init();
		
		gui = new GUI();
		
		gui.show();
		
		
		
		
		
		
		
	}
	

	
	
	
	
	
	
	
	
}
