package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import application.Exceptions.NullEventEndPointException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CalendarViewController {
	
	private static Stage applicationStage; 
	private static User currentUser;
	private static FXMLLoader loader = new FXMLLoader();
	
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
	
	// Opens a new application window depending on the user that has logged-in from the loginView.
	// Needs to be static and take in a user as an argument
	public static void initializeCalendarView(ActionEvent loginEvent, User loginUser) {
		try {
			Stage loginStage = (Stage)((Node)loginEvent.getSource()).getScene().getWindow();
 			loginStage.close();
      
			currentUser = loginUser; 
 			Parent root = loader.load(new FileInputStream("src/application/FXML/CalendarView.fxml"));
 			applicationStage = new Stage(); 
 			Scene scene = new Scene(root);
 			applicationStage.setScene(scene);
 			applicationStage.setTitle(currentUser.getUsername() + "'s Calendar");
 			applicationStage.show();
			
			System.out.println("Welcome " + currentUser.getUsername() + "!");

			generateDayView();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void initializeLoginView() {
		try {
			FXMLLoader loader = new FXMLLoader();
 			Parent root = loader.load(new FileInputStream("src/application/FXML/LoginView.fxml"));
 			Scene scene = new Scene(root);
 			applicationStage.setScene(scene);
			applicationStage.setTitle("Login");
 			applicationStage.show();
						
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static void generateDayView() throws NullEventEndPointException {		
    	System.out.println("generateDayView: Attempting to generate day view...");
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
