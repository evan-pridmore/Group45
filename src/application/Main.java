package application;


import java.time.ZonedDateTime;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		System.out.println("Main: Starting application...");
		
		primaryStage.setResizable(false);
		
		// Assigns references/data to static variables in the ApplicationController class.
		ApplicationController.setAppStage(primaryStage);
		ApplicationController.setSelectedDate(ZonedDateTime.now());
		
		// Triggers the initialization of the Login View.
		ApplicationController.initializeLoginView();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
