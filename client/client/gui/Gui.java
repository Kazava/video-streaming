package client.gui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.EventListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import client.connect.CMD;
import client.main.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Gui extends Application{
	
	private static Stage stage;
	private static Scene scene;
	
	private static Button[] buttons;
	private static String[] buttonNames;
	
	private ImageView imageView;
	private Image image;
	
	private boolean isAlive = true;


	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.stage = primaryStage;
		
		stage.setTitle("Video Player by Carlos & Thilo");
		stage.centerOnScreen();
		
		/**
		 * A <code>GridPane</code> represents the toolbar
		 * which will be at the top of the gui window
		 */
		GridPane toolbar = new GridPane();
		toolbar.setPadding(new Insets(10, 10, 10, 10));
		toolbar.setVgap(10);
		toolbar.setHgap(70);
		toolbar.setAlignment(Pos.CENTER);
		toolbar.setMinHeight(50);
		toolbar.getStyleClass().add("toolbar");
		
		buttonNames = new String[]{"Play", "Pause", "Stop", "Next"};
		buttons = new Button[buttonNames.length];
		
		for(int i = 0; i < buttonNames.length; i++){
			buttons[i] = new Button(buttonNames[i]);
			
			GridPane.setConstraints(buttons[i], i, 0);
	        toolbar.getChildren().add(buttons[i]);
	        
	        setButtonActions(buttons[i]);
		}
		
		StackPane videoStack = new StackPane();
		videoStack.setMinSize(400, 400);
		
		// The Video player
	    image = new Image("client/gui/image.png");
	    
	    imageView = new ImageView(image);
		
		videoStack.getChildren().add(imageView);
		
		/**
		 * The <code>BorderPane</code> is responsible for the main
		 * structure of the window. The border pain seperates the stage
		 * into 5 sections (top, bottom, left, right and center);
		 */
		BorderPane layout = new BorderPane();
		layout.getStyleClass().add("layout");
		layout.setTop(toolbar);
		layout.setCenter(videoStack);
		
		this.scene = new Scene(layout);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Gui.class.getResource("style.css").toExternalForm());
     
        stage.setOnCloseRequest(e -> {
        	Platform.exit();
        	System.exit(0);
        });
		
		stage.setScene(scene);
		stage.show();
		
		
	}
	
	/*
	 * set message which will be printed on the 
	 * graphical user interface
	 */
	public void setMessage(String text){
		
	}
	
	/**
	 * set actions to buttons
	 * @param btn
	 */
	private void setButtonActions(Button btn) {
		 btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(btn.getText() == "Play"){
					sendTcpCommand(CMD.PLAY);
					receiveUdp();
					repaintImage();
				}else if(btn.getText() == "Pause"){
					sendTcpCommand(CMD.PAUSE);
				}else if(btn.getText() == "Stop"){
					sendTcpCommand(CMD.STOP);
				}else if(btn.getText() == "Next"){
					sendTcpCommand(CMD.NEXT);
				}
			}
	    });
	}
	
	private void sendTcpCommand(CMD cmd){
		Thread sent = new Thread(new Runnable() {
		     public void run() {
		    	 Main.getClient().sendTcpCommand(cmd);
		     }
		});  
		sent.start();
	}
	
	private void receiveUdp() {
		Thread sent = new Thread(new Runnable() {
		     public void run() {
		    	 Main.getClient().receiveAsUdp();
		     }
		});  
		sent.start();
	}
	
	public void repaintImage(){
		while(isAlive){
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			image = Main.getClient().getImage();
			imageView = new ImageView(image);
		}
	}

}
