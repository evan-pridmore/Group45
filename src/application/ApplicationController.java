package application;

import java.io.FileInputStream;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationController {

	protected static ApplicationController appController;
	protected static LoginViewController loginController;
	protected static CalendarViewController calendarController;
	protected static EventManagementController eventManagementController;
	
	private static Stage appStage;	
	
	private static User currentUser; 
	
	// Define static controllers for each view to standardize across instances. 
	static void setLoginController(LoginViewController inputController) {
		loginController = inputController;
	}
	
	static void setCalendarController(CalendarViewController inputController) {
		calendarController = inputController;
	}
	
	static void setEventManagementController(EventManagementController inputController) {
		eventManagementController = inputController;
	}

	// Define components of GUI to standardize across instances.
	static void setAppStage(Stage inputStage) {
		appStage = inputStage;
	}
	
	// Define current logged in user across application.
	static void setCurrentUser(User inputUser) {
		currentUser = inputUser;
	}
	
	// Getter methods.
	static User getCurrentUser() {
		return currentUser;
	}
	
	static void initializeLoginView() {
    	System.out.println("initializeLoginView: Attempting to initialize LoginView...");
		try {
			FXMLLoader loginLoader = new FXMLLoader();
			Parent rootScene = loginLoader.load(new FileInputStream("src/application/FXML/LoginView.fxml"));
			
			if (loginController == null) {
				System.out.println("loginController null");
				loginController = loginLoader.getController();
			} else {
				System.out.println("loginController not null");
				loginLoader.setController(loginController);
			}
			
			appStage.setTitle("Login");
			appStage.setScene(new Scene(rootScene));
			appStage.show();
  				  							
		} catch (Exception e) { e.printStackTrace(); }
		
		System.out.println("initializeLoginView: LoginView successfully initialized.");
		}
	
	// Opens a new application window depending on the user that has logged-in from the loginView.
	// Needs to be static and take in a user as an argument
	static void initializeCalendarView() {
    	System.out.println("initializeCalendarView: Attempting to initialize CalendarView...");
		try {
			FXMLLoader calendarLoader = new FXMLLoader();
			Parent rootScene = calendarLoader.load(new FileInputStream("src/application/FXML/CalendarView.fxml"));
			
			if (calendarController == null) {
				System.out.println("calendarController null");
				calendarController = calendarLoader.getController();
			} else {
				System.out.println("calendarController not null");
				calendarLoader.setController(calendarController);
			}
			
			calendarController.updateGUI();
			
			appStage.setTitle(String.format("%s's Calendar", currentUser.getUsername()));
			appStage.setScene(new Scene(rootScene));
			appStage.show();
		} catch (Exception e) { e.printStackTrace(); }

	}
	
}
