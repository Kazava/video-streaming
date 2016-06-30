package client.main;

import java.io.IOException;

import client.compression.Compression;
import client.connect.ClientInterface;
import client.connect.VideoClient;
import client.gui.Gui;

public class Main {
	static ClientInterface client;
	static Gui gui;
	static Compression compression;
	
	public static void main(String[] args) {
		client = new VideoClient(8001, 8002);
		try {
			client.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
