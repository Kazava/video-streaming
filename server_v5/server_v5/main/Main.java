package server_v5.main;

import server_v5.network.Server;
import server_v5.network.VideoStreamServer;

public class Main {
	public static void main(String[] args) {
		Server server = new VideoStreamServer();
		new Thread((Runnable) server).start();
	}

}
