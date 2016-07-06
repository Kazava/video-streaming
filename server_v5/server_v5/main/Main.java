package server_v5.main;

import server_v5.network.VideoStreamServer;

public class Main {
	public static void main(String[] args) {
		VideoStreamServer vss = new VideoStreamServer();
		new Thread(vss).start();
	}

}
