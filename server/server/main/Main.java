package server.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.opencv.core.Core;

import client.compression.Compression;
import javafx.application.Application;
import server.connect.ServerInterface;
import server.connect.VideoServer;
import server.gui.Gui;
import server.main.Main;
import server.video.Video;

public class Main {
	
	static Gui gui;
	static Compression comppression;
	static Video video;
	
	
	public static void main(String[] args){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		//video = new Video("video/example.avi"); //TODO: class: AviVideo
		
		Application.launch(Gui.class, args);
	}
}
