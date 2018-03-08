/*
 * Copyright (C) 2018  Ahmad A. A. (https://github.com/bbpgrs/)
 */

package application; 
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
//import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * The Main class deals with initializing the application UI and loading the FXML and CSS files for layout and styling.
 * 
 * @version 0.6
 */
public class Main extends Application {
	
	/**
	 * Current version of the Application. The same as the current version of the MainController and Main classes.
	 */
	private static final String versionStr = "v0.6.0";
	
	@Override
	/**
	 * Initiate and start the application's UI. Also loads the FXML and CSS files for layout and styling.
	 */	
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("application.fxml")); //load FXML file and assign it as the root for the UI
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); //load the CSS file
			primaryStage.setTitle("ToolsFX "+versionStr); //set the title of the window
			primaryStage.setResizable(false); //the UI was designed to work best at 400x400 dimensions
			primaryStage.setScene(scene);
			primaryStage.show();								
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Returns a String of the current version of the Application
	 * 
	 * @return String representation of the current version of the Application formatted as such "v1.2.3"
	 */
	public static String getVer() {
		return versionStr;
	}
	
	/**
	 * main method used to launch the application from the command line or an executable JAR that was not packaged by JavaFX packaging tools.
	 * 
	 * @param args command-line arguments
	 */	
	public static void main(String[] args) {
		try {
			launch(args);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
