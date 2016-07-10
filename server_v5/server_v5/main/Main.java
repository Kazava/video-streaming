package server_v5.main;

import org.opencv.core.Core;

import server_v5.network.Server;
import server_v5.network.VideoStreamServer;

public class Main {
	public static void main(String[] args) {
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Server server = new VideoStreamServer();
		new Thread((Runnable) server).start();
	}

}
