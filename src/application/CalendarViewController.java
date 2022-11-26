package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CalendarViewController {
	
	private static Stage applicationStage; 
	private static User currentUser;
	private static Date selectedDate = new Date();
	
	@FXML
    private Menu EditMenu;

    @FXML
    private Menu UserMenu;
	
	@FXML
	private VBox upcomingEventsVBox;
	
	@FXML
	private static VBox dayViewVBox;
	
	@FXML
	private static Label dayViewDateLabel;
	
	@FXML
	private static Label weekViewDateLabel;
	
	// Opens a new application window depending on the user that has logged-in from the loginView.
	// Needs to be static and take in a user as an argument
	public static void initializeCalendarView(ActionEvent loginEvent, User loginUser) {
    	System.out.println("initializeCalendarView: Attempting to initialize CalendarView...");

		try {
			FXMLLoader loader = new FXMLLoader();
			currentUser = loginUser; 
 			Parent root = loader.load(new FileInputStream("src/application/FXML/CalendarView.fxml"));
 			// Login event is used to get a reference to the application Window, which is then casted to Stage.
 			applicationStage = (Stage)((Node)loginEvent.getSource()).getScene().getWindow();
 			Scene scene = new Scene(root);
 			applicationStage.setScene(scene);
 			applicationStage.setTitle(currentUser.getUsername() + "'s Calendar");
 			applicationStage.centerOnScreen();
 			
  			applicationStage.show();
  						
		} catch (Exception e) { e.printStackTrace(); }
		
		System.out.println("Today's date is: " + selectedDate);
		System.out.println("initializeCalendarView: CalendarView successfully initialized.");
	}
	
	public static void initializeLoginView() {
    	System.out.println("initializeLoginView: Attempting to initialize LoginView...");

		try {
			FXMLLoader loader = new FXMLLoader();
 			Parent root = loader.load(new FileInputStream("src/application/FXML/LoginView.fxml"));
 			Scene scene = new Scene(root);
 			applicationStage.setScene(scene);
			applicationStage.setTitle("Login");
 			applicationStage.centerOnScreen();
 			applicationStage.show();
						
		} catch (Exception e) { e.printStackTrace(); }
		
		System.out.println("initializeLoginView: LoginView successfully initialized.");
	}
	
    @FXML
    private void switchUser(ActionEvent switchUserEvent) {
    	System.out.println("switchUser: Attempting to switch user...");
    	initializeLoginView();
    }
    
    @FXML
    private void logOut(ActionEvent logOutEvent) {
    	System.out.println("logOut: Attempting to log out...");
    	initializeLoginView();
    }
    
    @FXML
    private void addEventMenu(ActionEvent addEventEvent) throws FileNotFoundException, IOException {
    	System.out.println("addEventMenu: Attempting to initialize EventManagerView...");
    	EventManagementController.initializeEventManagerView(applicationStage, currentUser);
    }
    
    @FXML
    private void removeEventMenu(ActionEvent removeEventEvent) {
    	System.out.println("removeEventMenu: Attempting to remove event...");
    	EventManagement.initializeEventManagerView(currentUser);
    }
}
