package client_v5.main;

import java.io.File;

import org.opencv.core.Core;

import client_v5.gui.Gui;
import client_v5.network.CMD;
import client_v5.network.Client;
import client_v5.network.VideoStreamClient;
import client_v5.video.Video;
import javafx.application.Application;

public class Main {
	public static void main(String[] args) {
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Application.launch(Gui.class, args);
		
	}
}
