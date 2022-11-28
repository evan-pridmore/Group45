package application;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		System.out.println("Main: Starting application...");
			LoginViewController loginController = new LoginViewController();
			ApplicationController.setLoginController(loginController);
			
			CalendarViewController calendarController = new CalendarViewController();
			ApplicationController.setCalendarController(calendarController);
			
			EventManagementController eventManagementController = new EventManagementController();
			ApplicationController.setEventManagementController(eventManagementController);
			
			ApplicationController.setAppStage(primaryStage);
			
			ApplicationController.initializeLoginView();
			
		try {
			
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
