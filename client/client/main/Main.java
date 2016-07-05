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
		
		// start Gui
		//Application.launch(Gui.class, args);
		
		client = new VideoClient(8001, 8002);
		try {
			client.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
