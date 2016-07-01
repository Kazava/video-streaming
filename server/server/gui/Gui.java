package server.gui;

import server.gui.Gui;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Gui extends Application{
	
	private static Stage stage;
	private static Scene scene;
	
	private static Button[] buttons;
	private static String[] buttonNames;


	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.stage = primaryStage;
		
		stage.setTitle("Video Server by Carlos & Thilo");
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
		
		buttonNames = new String[]{"Start server", "Shut down server"};
		buttons = new Button[buttonNames.length];
		
		for(int i = 0; i < buttonNames.length; i++){
			buttons[i] = new Button(buttonNames[i]);
			
			GridPane.setConstraints(buttons[i], i, 0);
	        toolbar.getChildren().add(buttons[i]);
		}
		
		/**
		 * The <code>BorderPane</code> is responsible for the main
		 * structure of the window. The border pain seperates the stage
		 * into 5 sections (top, bottom, left, right and center);
		 */
		BorderPane layout = new BorderPane();
		layout.getStyleClass().add("layout");
		layout.setTop(toolbar);
		
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

}
