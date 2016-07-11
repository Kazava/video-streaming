package server_v5.main;

import org.opencv.core.Core;
import javafx.application.Application;
import server_v5.gui.Gui;
import server_v5.main.Main;

public class Main {
	static Gui gui;

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Application.launch(Gui.class, args);
	}
}
