package application;

import java.io.FileInputStream;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationController {

	private static ApplicationController appController;
	private static LoginViewController loginController;
	private static CalendarViewController calendarController;
	
	private Stage applicationStage;
	private Scene applicationScene;
	
	private User currentUser; 
	private static Date selectedDate = new Date();
	
	public Stage getApplicationStage() {
		return applicationStage;
	}
	
	public void setApplicationStage(Stage stageInput) {
		applicationStage = stageInput;
	}
	
	public Scene getApplicationScene() {
		return applicationScene;
	}
	
	public void setApplicationScene(Scene sceneInput) {
		applicationScene = sceneInput;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public Date getSelectedDate() {
		return selectedDate;
	}
	
	
	public void setApplicationController() {
		appController = this;
	}
	
	public static ApplicationController getApplicationController() {
		return appController;
	}
	
	
	public static void setLoginViewController(LoginViewController controllerInput) {
		loginController = controllerInput;
	}
	
	public static LoginViewController getLoginViewController() {
		return loginController;
	}
	
	
	
	public static void setCalendarViewController(CalendarViewController controllerInput) {
		calendarController = controllerInput;
	}
	
	public static CalendarViewController getCalendarViewController() {
		return calendarController;
	}
	
	// Opens a new application window depending on the user that has logged-in from the loginView.
		// Needs to be static and take in a user as an argument
		public void initializeCalendarView(ActionEvent loginEvent, User loginUser) {
	    	System.out.println("initializeCalendarView: Attempting to initialize CalendarView...");
			currentUser = loginUser; 

			try {
				FXMLLoader loader = new FXMLLoader();
	 			Parent root = loader.load(new FileInputStream("src/application/FXML/CalendarView.fxml"));
	 			
	 			calendarController = (CalendarViewController) loader.getController();
	 			
	 			// Login event is used to get a reference to the application Window, which is then casted to Stage.
	 			applicationStage = (Stage)((Node)loginEvent.getSource()).getScene().getWindow();
	 			applicationScene = new Scene(root);
	 			applicationStage.setScene(applicationScene);
	 			applicationStage.setTitle(currentUser.getUsername() + "'s Calendar");
	 			applicationStage.centerOnScreen();
	 			
	  			applicationStage.show();
	  						
			} catch (Exception e) { e.printStackTrace(); }
			
			System.out.println("Today's date is: " + selectedDate);
			System.out.println("initializeCalendarView: CalendarView successfully initialized.");
		}
		
		public void initializeLoginView() {
	    	System.out.println("initializeLoginView: Attempting to initialize LoginView...");

			try {
				FXMLLoader loader = new FXMLLoader();
	 			Parent root = loader.load(new FileInputStream("src/application/FXML/LoginView.fxml"));
	 			applicationScene = new Scene(root);
	 			applicationStage.setScene(applicationScene);
				applicationStage.setTitle("Login");
	 			applicationStage.centerOnScreen();
	 			applicationStage.show();
							
			} catch (Exception e) { e.printStackTrace(); }
			
			System.out.println("initializeLoginView: LoginView successfully initialized.");
		}

}
