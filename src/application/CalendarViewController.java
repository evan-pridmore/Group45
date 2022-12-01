package application;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;

import application.TimeUnits.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**A controller that manages {@link CalendarView.fxml} and is associated GUI components (e.g., buttons, labels, textfields, etc.)
 * 
 * Extends {@link ApplicationController} which provides a range of static variables.
 * 
 * This should ONLY manage the associated GUI, and should NOT initialize any other stages, scenes, windows, views, etc.
 * (to switch windows or create new stages, add an initialize method in {@link ApplicationController}
 * 
 * @author evan-pridmore
 */
public class CalendarViewController extends ApplicationController  {
	
	public DateTimeFormatter dateLabelFormat = DateTimeFormatter.ofPattern("EEEE, MMMM dd");
	public DateTimeFormatter simpleTimeFormat = DateTimeFormatter.ofPattern("(dd) HH:mm");

	@FXML
	private Menu EditMenu;

	@FXML
  	private Menu UserMenu;
	
	@FXML
	private VBox upcomingEventsVBox;
	
	@FXML
	private Label upcomingEventsLabel;
	
	@FXML
	private AnchorPane dayViewAnchorPane;
	
	@FXML
	private Label dayDateLabel;
	
	@FXML
	private Label weekDateLabel;
	
	@FXML
	private Label monthDateLabel;
	
    @FXML
    void switchUser(ActionEvent switchUserEvent) {
    	System.out.println(String.format("switchUser: Attempting to switch user..."));
    	initializeLoginView();
    }
    
    @FXML
    void logOut(ActionEvent logOutEvent) {
    	System.out.println(String.format("%nlogOut: Attempting to log out..."));
    	initializeLoginView();
    }
    
    @FXML
    void addEventMenu(ActionEvent addEventEvent) {
    	System.out.println(String.format("%naddEventMenu: Attempting to initialize EventManagerView..."));
    	initializeEventMakerView();
    }
    
    @FXML
    void manageEventsMenu(ActionEvent removeEventEvent) {
    	System.out.println(String.format("%nremoveEventMenu: Attempting to remove event..."));
    	initializeEventViewerView();
    }
    
    @FXML
    void openAdminPreferences(ActionEvent openAdminPreferencesEvent) {
    	System.out.println("openAdminPreferences: Initializing tests...");
    	Rectangle testEvent = new Rectangle(200, 75, Color.LIGHTBLUE);
    	Rectangle testEvent2 = new Rectangle(200, 75, Color.LIGHTYELLOW);
    	Rectangle testEvent3 = new Rectangle(200, 75, Color.PINK);

    	upcomingEventsVBox.getChildren().addAll(testEvent, testEvent2, testEvent3);
    	
    	upcomingEventsVBox.setSpacing(5);
    	Insets upcomingEventsInsets = new Insets(0, 5, 0, 5);
    	VBox.setMargin(testEvent, upcomingEventsInsets);
    	VBox.setMargin(testEvent2, upcomingEventsInsets);
    	VBox.setMargin(testEvent3, upcomingEventsInsets);
    	
     	getCurrentUser().dumpEvents();
    	
		updateDayView();
    }
    
    /**A method that updates the GUI specifically in {@link CalendarView.fxml} through the {@link CalendarViewController} class. <p>
     * 
     * Runs at during initialization (i.e., when {@link initializeCalendarView} is called) to update GUI components with 
     * updated information. This may include: <br>
     * --> updating the date displayed in the labels of the CalendarView GUI.
     * --> checking for and updating the displayed events on the present view.
     */
    public void updateGUI() {
    	System.out.println(String.format("%nupdateGUI: Updating GUI..."));
    	System.out.println(String.format("--> Selected date is '%s'. (Week '%s', Day '%s')", getSelectedDate().format(dateLabelFormat), getSelectedDate().get(ChronoField.ALIGNED_WEEK_OF_YEAR), getSelectedDate().get(ChronoField.DAY_OF_WEEK)));
    	
		updateDateLabels(getSelectedDate());
		updateUpcomingEvents();
		updateDayView();
	}
    
    void updateDayView() {
     	// Get events from currentUser that exist within the specified date     	
     	System.out.println(String.format("%nupdateDayView: Updating day view..."));
     	updateEventPane(getSelectedDate(), dayViewAnchorPane);
     	System.out.println("updateDayView: Done.");
     }
    
    void updateUpcomingEvents() {
    	// This should NOT be dependent on 'selectedDate' because it is relative to the current date not the selected date.
    	System.out.println(String.format("%nupdateUpcomingEvents: Updating upcomingEventsVbox..."));

    }
    
    void updateDateLabels(ZonedDateTime inputDate) {
    	System.out.println(String.format("%nupdateDateLabels: Updating date labels..."));

    	String formattedDate = dateLabelFormat.format(inputDate);
    	dayDateLabel.setText(formattedDate);
    	weekDateLabel.setText(formattedDate);
    	monthDateLabel.setText(formattedDate);
    	
    }
    
    void updateEventPane(ZonedDateTime inputDay, AnchorPane inputPane) {
    	// Gets the events corresponding to a particular day, and adds it to the appropriate VBox.
    	System.out.println(String.format("updateVBoxEvents: Updating VBox '%s' with events corresponding to day '%s'...", inputPane, inputDay));
    	
    	// Clearing VBox to prevent duplicate event blocks from being added...     	
     	System.out.print(String.format("--> Clearing '%s' ...", inputPane));
     	inputPane.getChildren().clear();
     	System.out.println(String.format("	Cleared.%n"));

     	// Specifying the specific date to find events in...
     	System.out.println(String.format("--> User has '" + getCurrentUser().getEvents().size() + "' weeks stored in user data.%n"));
    	int weekOfYear = getSelectedDate().get(ChronoField.ALIGNED_WEEK_OF_YEAR);
     	int dayOfWeek = getSelectedDate().get(ChronoField.DAY_OF_WEEK);
     	System.out.println("--> Attempting to get day '" + dayOfWeek + "' of the '" + weekOfYear + "' week of the year...");

     	// Getting the specified week of the specified date...
     	System.out.print("	--> Attempting to get week '" + weekOfYear + "' of the year...");
     	boolean weekExists = false;
       	for (Week w: getCurrentUser().getEvents()) {
     		if (w.getWeekNum() == weekOfYear) {
     			// Week 'weekOfYear' exists in userEvents.
     			System.out.println("	Success!");
     			
     			// Getting the specified day in the specified week.
     			Day dayTemp = w.getDay(dayOfWeek); 
     			System.out.println("		--> Attempting to get '" + dayTemp.getEvents().size() + "' events from day '" + dayOfWeek + "' of the '" + weekOfYear + "' week of the year...");
     	    	for (Event e : dayTemp.getEvents()) {
     	        	System.out.println("			--> Event '" + e.getName() + "' found.");
     	        	addEventBlock(e, inputPane);
     	        	System.out.println(String.format("				--> Created event block for event '" + e.getName() + "'."));
     	    	}

     		}
     	if (!weekExists)
     		System.out.println("--> Week does NOT exist in user events.");
       	}
    }
    
	static Rectangle addEventBlock(Event inputEvent, AnchorPane inputPane) {
		// Each hour in every pane is 60px. Thus, each minute is 1px.
		
		// Start POSITION should be equal to the number of minutes from the beginning of the day.		
		double startPos = inputEvent.getStart().get(ChronoField.MINUTE_OF_DAY) + 7;
		double endPos = inputEvent.getEnd().get(ChronoField.MINUTE_OF_DAY) + 7;
		
		// Height should be equal to the difference between startPos and endPos
		double height = endPos - startPos;
		
		System.out.println(String.format("Event '%s' has a start time of '%s' ('%s' px) and and end time of '%s' ('%s' px)", inputEvent.getName(), inputEvent.getStart().toString().substring(11, 16), startPos, inputEvent.getEnd().toString().substring(11, 16), endPos));
		
 		Rectangle eventBlock = new Rectangle(240, height, inputEvent.getColour());
 		
 		inputPane.setTopAnchor(eventBlock, startPos);
 		
 		inputPane.getChildren().add(eventBlock);

 		return eventBlock;
 	}

}
