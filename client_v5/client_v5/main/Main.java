package client_v5.main;

import client_v5.network.CMD;
import client_v5.network.Client;
import client_v5.network.VideoStreamClient;

public class Main {
	public static void main(String[] args) {
		Client client = new VideoStreamClient();
		//new Thread((Runnable) client).start();
		
		// Simulate button to send 'PLAY' message:
		client.sendMessage(CMD.PLAY);
	}
}
