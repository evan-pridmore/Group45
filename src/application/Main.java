package application;
	
import java.io.FileInputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		System.out.println("Main: Starting application...");

		try {
			// Initializing ApplicationController
			System.out.print("Main: Initializing ApplicationController...");
			ApplicationController appController = new ApplicationController();
			appController.setApplicationController();
			System.out.println("     Done.");

			// Initializing LoginViewController
			System.out.print("Main: Initializing LoginViewController...");
			FXMLLoader loginLoader = new FXMLLoader();
			loginLoader.load(new FileInputStream("src/application/FXML/LoginView.fxml"));
			LoginViewController loginController = loginLoader.getController();
			appController.setLoginViewController(loginController);
			System.out.println("     Done.");
			
			// Initializing CalendarViewController
			System.out.print("Main: Initializing CalendarViewController...");
			FXMLLoader calendarLoader = new FXMLLoader();
			calendarLoader.load(new FileInputStream("src/application/FXML/CalendarView.fxml"));
			CalendarViewController calendarController = calendarLoader.getController();
			appController.setCalendarViewController(calendarController);
			System.out.println("     Done.");
			
			// Initializing EventManagementController
			System.out.print("Main: Initializing EventManagementController... ");
			FXMLLoader eventManagerLoader = new FXMLLoader();
			eventManagerLoader.load(new FileInputStream("src/application/FXML/EventsViewerView.fxml"));
			EventManagementController eventManagerController = eventManagerLoader.getController();
			appController.setEventManagementController(eventManagerController);
			System.out.println("     Done.");
			
			System.out.println("");
			
			appController.setApplicationStage(primaryStage);

			// Initializing application GUI...
			appController.initializeLoginView();
			
	    	System.out.println(String.format("Main: initialized controllers.%n--> appController = %s %n--> loginController = %s %n-->calendarController = %s %n--> currentUser = %s", appController, loginController, calendarController, eventManagerController));
	    	System.out.println("");
			
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
