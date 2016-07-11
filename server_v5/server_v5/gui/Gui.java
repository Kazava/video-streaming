package server_v5.gui;

import javafx.application.Platform;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import server_v5.network.Server;
import server_v5.network.VideoStreamServer;


public class Gui extends Application {
	private static Stage stage;
	private static Scene scene;
	private static Button[] buttons;
	private static String[] buttonNames;
	private boolean isRunning;
	private String btn_restart = "Restart Listening";
	private String btn_stop = "Stop Listening";

	private Server server;

	@Override
	public void start(Stage primaryStage) {
		server = new VideoStreamServer();
		new Thread((Runnable) server).start();
		this.isRunning = true;

		Gui.stage = primaryStage;
		
		stage.setTitle("Video Streaming Server");
		stage.centerOnScreen();
		
		/**
		 * A <code> GridPane</code> represents the toolbar
		 * which will be at the top of the gui window
		 */
		GridPane toolbar = new GridPane();
		toolbar.setPadding(new Insets(10, 10, 10, 10));
		toolbar.setVgap(10);
		toolbar.setHgap(70);
		toolbar.setAlignment(Pos.CENTER);
		toolbar.setMinHeight(50);
		toolbar.getStyleClass().add("toolbar");
		
		buttonNames = new String[]{btn_restart, btn_stop};
		buttons = new Button[buttonNames.length];
		
		for(int i = 0; i < buttonNames.length; i++){
			buttons[i] = new Button(buttonNames[i]);
			
			GridPane.setConstraints(buttons[i], i, 0);
	        toolbar.getChildren().add(buttons[i]);		
	        
	        setButtonActions(buttons[i]);
	        if (buttons[i].getText().equals(btn_restart)) {
	        	buttons[i].setDisable(true);
	        }
		}
		
		/**
		 * The <code>BorderPane</code> is responsible for the main
		 * structure of the window. The border pain seperates the stage
		 * into 5 sections (top, bottom, left, right and center);
		 */
		BorderPane layout = new BorderPane();
		layout.getStyleClass().add("layout");
		layout.setTop(toolbar);
		
		Gui.scene = new Scene(layout);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Gui.class.getResource("style.css").toExternalForm());
     
        stage.setOnCloseRequest(e -> {
        	Platform.exit();
        	System.out.println("...Server has shutdown.");
        	System.exit(0);
        });
		
		stage.setScene(scene);
		stage.show();
	}
	
	public void setButtonActions(Button btn) {
		 btn.setOnAction(new EventHandler<ActionEvent>() {
 			public void handle(ActionEvent event) {
 				// Restart listening:
 				if (btn.getText().equals(btn_restart) && isRunning == false) {
 	 				isRunning = true;
 	 				server.restartListening();
	 				buttons[0].setDisable(true);
	 				buttons[1].setDisable(false);
 				}
 				// Stop listening:
 				if (btn.getText().equals(btn_stop) && isRunning == true) {
 	 				isRunning = false;
 	 				server.stopListening();
	 				buttons[0].setDisable(false);
	 				buttons[1].setDisable(true); 			
	 			}
 			}
	    });
	}
}
