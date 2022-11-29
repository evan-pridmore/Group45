package application;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date

import application.TimeUnits.Week;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		System.out.println("Main: Starting application...");
		
		primaryStage.setResizable(false);
		
		ApplicationController.setAppStage(primaryStage);
		ApplicationController.initializeLoginView();
			
		try {
			Week test = new Week(ZonedDateTime.now());
			
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
