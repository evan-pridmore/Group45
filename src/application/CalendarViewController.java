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
	
	public static Stage applicationStage; 
	public static Scene applicationScene;
	private static User currentUser;
	
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
	
    @FXML
    private void switchUser(ActionEvent switchUserEvent) {
    	System.out.println("switchUser: Attempting to switch user...");
    	ApplicationController.getApplicationController().initializeLoginView();
    }
    
    @FXML
    private void logOut(ActionEvent logOutEvent) {
    	System.out.println("logOut: Attempting to log out...");
    	ApplicationController.getApplicationController().initializeLoginView();
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
    
/*	FXMLLoader loader = new FXMLLoader();
		Parent eventMaker = loader.load(new FileInputStream("src/application/FXML/EventMakerView.fxml"));
		EventMakerController controller = (EventMakerController) loader.getController();
		controller.initalizeEventMakerController(currentUser, eventMaker);
 */
    
}
