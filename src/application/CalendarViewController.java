package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import application.Exceptions.InvalidColourException;
import application.Exceptions.NullEventEndPointException;
import application.TimeUnits.Event;
import application.TimeUnits.Week;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class CalendarViewController extends ApplicationController  {
	
	public SimpleDateFormat dateLabelFormat = new SimpleDateFormat("EEEE, MMMM dd");
	public Date currentDate = new Date();
	
	@FXML
    private Menu EditMenu;

    @FXML
    private Menu UserMenu;
	
	@FXML
	private VBox upcomingEventsVBox;
	
	@FXML
	private Label upcomingEventsLabel;
	
	@FXML
	private VBox dayViewVBox;
	
	@FXML
	private Label dayDateLabel;
	
	@FXML
	private Label weekDateLabel;
	
	@FXML
	private Label monthDateLabel;
	
	@FXML
	void initialize() {
		updateDateLabels(currentDate);
	}
	
    @FXML
    void switchUser(ActionEvent switchUserEvent) {
    	System.out.println("switchUser: Attempting to switch user...");
    	ApplicationController.initializeLoginView();
    }
    
    @FXML
    void logOut(ActionEvent logOutEvent) {
    	System.out.println("logOut: Attempting to log out...");
    	ApplicationController.initializeLoginView();
    }
    
    @FXML
    void addEventMenu(ActionEvent addEventEvent) throws FileNotFoundException, IOException {
    	System.out.println("addEventMenu: Attempting to initialize EventManagerView...");
    	EventManagementController.initializeEventManagerView();
    }
    
    @FXML
    void removeEventMenu(ActionEvent removeEventEvent) throws FileNotFoundException, IOException {
    	System.out.println("removeEventMenu: Attempting to remove event...");
    	EventManagementController.initializeEventManagerView();
    }
    
    @FXML
    void openAdminPreferences(ActionEvent openAdminPreferencesEvent) {
    	System.out.println("openAdminPreferences: ...");

    }
    
    void updateDateLabels(Date inputDate) {
    	String formattedDate = dateLabelFormat.format(inputDate);
    	System.out.println("updateSelectedDateLabels: Attempting to update labels...");
    	dayDateLabel.setText(formattedDate);
    	weekDateLabel.setText(formattedDate);
    	monthDateLabel.setText(formattedDate);
    	
    }
}
