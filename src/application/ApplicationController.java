package application;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationController {

	protected static ApplicationController appController;
	private static LoginViewController loginController;
	private static CalendarViewController calendarController;
	private static EventManagementController eventManagementController;
	
	private static Stage appStage;
	private static Scene appScene;
	
	
	private static User currentUser; 

	public Stage getAppStage() {
		return appStage;
	}
	
	public void setAppStage(Stage stageInput) {
		appStage = stageInput;
	}
	
	public static Scene getAppScene() {
		return appScene;
	}

	public static void setAppScene(Scene appScene) {
		ApplicationController.appScene = appScene;
	}

	public User getCurrentUser() {
		return currentUser;
	}
	
	public void setCurrentUser(User userInput) {
		currentUser = userInput;
	}
	
	public ApplicationController getApplicationController() {
		return appController;
	}
	
	public void setLoginViewController(LoginViewController controllerInput) {
		loginController = controllerInput;
	}
	
	public void setCalendarViewController(CalendarViewController controllerInput) {
		calendarController = controllerInput;
	}

		// Opens a new application window depending on the user that has logged-in from the loginView.
		// Needs to be static and take in a user as an argument
		public void initializeCalendarView() {
	    	System.out.println("initializeCalendarView: Attempting to initialize CalendarView...");

			try {
	  				  						
			} catch (Exception e) { e.printStackTrace(); }

		}
		
		public void initializeLoginView() {
	    	System.out.println("initializeLoginView: Attempting to initialize LoginView...");

			try {
							
			} catch (Exception e) { e.printStackTrace(); }
			
			System.out.println("initializeLoginView: LoginView successfully initialized.");
		}
}
