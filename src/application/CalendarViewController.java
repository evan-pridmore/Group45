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
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
	
	void updateGUI() {
		updateDateLabels(new Date());
		updateUpcomingEvents();
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
    	dayDateLabel.setText("Date Label Changed");
    	Rectangle testEvent = new Rectangle(200, 75, Color.LIGHTBLUE);
    	Rectangle testEvent2 = new Rectangle(200, 75, Color.LIGHTYELLOW);
    	Rectangle testEvent3 = new Rectangle(200, 75, Color.PINK);

    	upcomingEventsVBox.getChildren().addAll(testEvent, testEvent2, testEvent3);
    	
    	upcomingEventsVBox.setSpacing(5);
    	Insets upcomingEventsInsets = new Insets(0, 5, 0, 5);
    	VBox.setMargin(testEvent, upcomingEventsInsets);
    	VBox.setMargin(testEvent2, upcomingEventsInsets);
    	VBox.setMargin(testEvent3, upcomingEventsInsets);
    }
    
    void updateUpcomingEvents() {
    	System.out.println("updateUpcomingEvents: Updating upcomingEventsVbox...");

    }
    
    void updateDateLabels(Date inputDate) {
    	System.out.println("updateDateLabels: Updating date labels...");

    	String formattedDate = dateLabelFormat.format(inputDate);
    	System.out.println("updateSelectedDateLabels: Attempting to update labels...");
    	dayDateLabel.setText(formattedDate);
    	weekDateLabel.setText(formattedDate);
    	monthDateLabel.setText(formattedDate);
    	
/* TIme_Unit_Rewrite BEGIN
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
*/ TIme_Unit_Rewrite EN
    }
}
