package application;


import java.time.ZonedDateTime;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		System.out.println("Main: Starting application...");
		
		primaryStage.setResizable(false);
		
		ApplicationController.setAppStage(primaryStage);
		ApplicationController.setSelectedDate(ZonedDateTime.now());
		ApplicationController.initializeLoginView();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
