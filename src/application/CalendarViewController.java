package application;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;

public class CalendarViewController extends ApplicationController  {
	
	@FXML
    private Menu EditMenu;

    @FXML
    private Menu UserMenu;
	
	@FXML
	private VBox upcomingEventsVBox;
	
	@FXML
	private VBox dayViewVBox;
	
	@FXML
	private Label dayLabel;
	
	@FXML
	private Label weekViewDateLabel;
	
    @FXML
    void switchUser(ActionEvent switchUserEvent) {
    	System.out.println("switchUser: Attempting to switch user...");
    	appController.initializeLoginView();
    }
    
    @FXML
    void logOut(ActionEvent logOutEvent) {
    	System.out.println("logOut: Attempting to log out...");
    	appController.initializeLoginView();
    }
    
    @FXML
    void addEventMenu(ActionEvent addEventEvent) throws FileNotFoundException, IOException {
    	System.out.println("addEventMenu: Attempting to initialize EventManagerView...");
    }
    
    @FXML
    void removeEventMenu(ActionEvent removeEventEvent) {
    	System.out.println("removeEventMenu: Attempting to remove event...");
    }
    
    @FXML
    void openAdminPreferences(ActionEvent openAdminPreferencesEvent) {
    	System.out.println("openAdminPreferences: Attempting to update labels...");
    }
    
    void updateDateLabels() {
    	System.out.println("updateSelectedDateLabels: Attempting to update labels...");
    	dayLabel.setText("Hello :)");
    }
}
