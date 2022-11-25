package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class CalendarViewController {
	
	static FXMLLoader loader = new FXMLLoader();
	private static Stage applicationStage; 
	private static User currentUser;

	@FXML
    private Menu EditMenu;

    @FXML
    private Menu UserMenu;
	
	@FXML
	private VBox upcomingEventsVBox;
	
	@FXML
	private static VBox dayViewVBox;
	
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
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
		
    @FXML
    private void SwitchUserMenu(ActionEvent switchUserEvent) {
    	System.out.println("Switch User Menu");
    }
    
    @FXML
    private void logOut(ActionEvent logOutEvent) {
    	System.out.println("logOut: Attempting to log out...");
    }
    
    @FXML
    private void addEventMenu(ActionEvent addEventEvent) throws FileNotFoundException, IOException {
    	System.out.println("addEventMenu: Attempting to initialize EventManagerView...");
    	EventManagementController.initializeEventManagerView(applicationStage, currentUser);
    }
    
    @FXML
    private void removeEventMenu(ActionEvent removeEventEvent) {
    	
    }
    
}
