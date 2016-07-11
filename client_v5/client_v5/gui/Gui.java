package client_v5.gui;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;

import client_v5.network.CMD;
import client_v5.network.Client;
import client_v5.network.VideoStreamClient;
import client_v5.video.*;

public class Gui extends Application{
	
	Stage stage;
	Scene scene;
	BorderPane layout;
	
	private static ImageView imgView;
	private static WritableImage img;
	private static PixelWriter pw;
	
	Button play;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.stage = primaryStage;
		
		initVideo(600, 400);
		
		layout = new BorderPane();
		
		play = new Button("Play");

		
		 play.setOnAction(new EventHandler<ActionEvent>() {
			 
			@Override
			public void handle(ActionEvent event) {
				
				Client client = new VideoStreamClient();
				client.sendMessage(CMD.PLAY);
				new Thread((Runnable) client).start();
				

			}
			
		});
		
		HBox toolbar = new HBox();
		toolbar.getChildren().add(play);
		toolbar.setAlignment(Pos.CENTER);
		toolbar.setMinHeight(50);
		
		StackPane stack = new StackPane();
		
		stack.getChildren().add(imgView);
		
		layout.setTop(toolbar);
		layout.setCenter(stack);
		
		scene = new Scene(layout);
		
		stage.setTitle("Video Streaming - Client");
		stage.centerOnScreen();
		stage.setScene(scene);
		stage.setMinWidth(700);
		stage.setMinHeight(500);
		
		stage.show();
		
		stage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
		});
		
	}
	
	public static void writeImg(Frame frame, int width, int height){
		int[] pixels = frame.getPixels();
		pw.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), pixels, 0, width);
	}
	
	public static void initVideo(int width, int height){
		img = new WritableImage(width, height);
		imgView = new ImageView(img);
		pw = img.getPixelWriter();
	}
	
	
}
