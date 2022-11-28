package application;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationController {

	protected static ApplicationController appController;
	private static LoginViewController loginController;
	private static CalendarViewController calendarController;
	private static EventManagementController eventManagementController;
	
	private static Stage applicationStage;
	
	public SimpleDateFormat DateFormatPhrase = new SimpleDateFormat("EEEE, MMMM dd");
	
	private static User currentUser; 
	private Date selectedDate = new Date();
	
	public Stage getApplicationStage() {
		return applicationStage;
	}
	
	public void setApplicationStage(Stage stageInput) {
		applicationStage = stageInput;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public void setCurrentUser(User userInput) {
		currentUser = userInput;
	}
	
	public String getSelectedDate() {
		DateFormatPhrase.format(selectedDate);
		return DateFormatPhrase.format(selectedDate);
	}
	
	public void setSelectedDate(Date dateInput) {
		selectedDate = dateInput;
	}
	
	public ApplicationController getApplicationController() {
		return appController;
	}
	
	public void setApplicationController() {
		appController = this;
	}
	
	public void setLoginViewController(LoginViewController controllerInput) {
		loginController = controllerInput;
	}
	
	public void setCalendarViewController(CalendarViewController controllerInput) {
		calendarController = controllerInput;
	}
	
	public EventManagementController getEventManagementController() {
		return eventManagementController;
	}

	public void setEventManagementController(EventManagementController controllerInput) {
		eventManagementController = controllerInput;
	}

		// Opens a new application window depending on the user that has logged-in from the loginView.
		// Needs to be static and take in a user as an argument
		public void initializeCalendarView() {
	    	System.out.println("initializeCalendarView: Attempting to initialize CalendarView...");

			try {
				FXMLLoader loader = new FXMLLoader();
	 			Parent root = loader.load(new FileInputStream("src/application/FXML/CalendarView.fxml"));
				loader.setController(calendarController);
				
				System.out.println("initializeCalendarView: Contoller set to '" + loader.getController() + "'.");
	 			
	 			Scene scene = new Scene(root);
	 			applicationStage.setScene(scene);
	 			applicationStage.setTitle(currentUser.getUsername() + "'s Calendar");
	 			applicationStage.centerOnScreen();
	 			
	 			System.out.println(calendarController);
	 			calendarController.updateDateLabels();
	 			
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
				loader.setController(loginController);

				System.out.println("initializeLoginView: Contoller set to '" + loader.getController() + "'.");
				
	 			Scene scene = new Scene(root);
	 			applicationStage.setScene(scene);
	 			applicationStage.setTitle("Login");
	 			applicationStage.centerOnScreen();
	 			applicationStage.show();
							
			} catch (Exception e) { e.printStackTrace(); }
			
			System.out.println("initializeLoginView: LoginView successfully initialized.");
		}
}
