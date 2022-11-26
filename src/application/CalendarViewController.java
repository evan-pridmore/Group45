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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CalendarViewController {
	
	private User currentUser;
	private Stage applicationStage;
	
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
	public void initializeCalendarView(Stage newStage, ActionEvent loginEvent, User loginUser) throws NullEventEndPointException {
			currentUser = loginUser; 
			applicationStage = newStage;
			System.out.println("Welcome " + currentUser.getUsername() + "!");
			System.out.println("You have " + currentUser.getEvents().size() + " events.");
			
			applicationStage.show();
	}
	
	public void initializeLoginView() throws FileNotFoundException, IOException {
			FXMLLoader loader = new FXMLLoader();
 			Parent root = loader.load(new FileInputStream("src/application/FXML/LoginView.fxml"));
 			Scene scene = new Scene(root);
 			applicationStage.setScene(scene);
			applicationStage.setTitle("Login");
 			applicationStage.show();
	}
	

    @FXML
    private void switchUser(ActionEvent switchUserEvent) throws FileNotFoundException, IOException {
    	System.out.println("switchUser: Attempting to switch user...");
    	initializeLoginView();
    }
    
    @FXML
    private void logOut(ActionEvent logOutEvent) throws FileNotFoundException, IOException {
    	System.out.println("logOut: Attempting to log out...");
    	initializeLoginView();
    }
    
    @FXML
    private void addEventMenu(ActionEvent addEventEvent) throws FileNotFoundException, IOException {
  	System.out.println("addEventMenu: Attempting to initialize EventManagerView...");
    	
    	Stage eventsStage = new Stage();
		eventsStage.initModality(Modality.APPLICATION_MODAL);
		eventsStage.setTitle(currentUser.getUsername() + "'s Events");
		
    	FXMLLoader loader = new FXMLLoader();
    	Parent root = loader.load(new FileInputStream("src/application/FXML/EventsViewerView.fxml"));
		Scene scene = new Scene(root);
		eventsStage.setScene(scene);
		
		EventManagementController controller = loader.getController();		
    	controller.initializeEventManagerView(eventsStage, currentUser);
    }
    
    @FXML
    private void removeEventMenu(ActionEvent removeEventEvent) throws FileNotFoundException, IOException {
    	System.out.println("removeEventMenu: Attempting to remove event...");
     	
    	addEventMenu(removeEventEvent);
    }
}
