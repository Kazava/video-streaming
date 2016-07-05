package client.main;

import java.io.IOException;

import client.compression.Compression;
import client.connect.ClientInterface;
import client.connect.VideoClient;
import client.gui.Gui;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main {
	static ClientInterface client;
	static Gui gui;
	static Compression compression;
	
	public static void main(String[] args) throws Exception {
		
		client = new VideoClient(8001, 8002);

		Thread backend = new Thread(new Runnable() {
		     public void run() {
		    	 try {
					client.run();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		     }
		});  
		backend.start();

		// start Gui
		Application.launch(Gui.class, args);
	}
	
	public static VideoClient getClient(){
		return (VideoClient) client;
	}
}
