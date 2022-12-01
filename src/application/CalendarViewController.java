package application;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;

import application.Exceptions.EventOutsideTimeUnitException;
import application.Exceptions.NullEventEndPointException;
import application.TimeUnits.Day;
import application.TimeUnits.Event;
import application.TimeUnits.TimedEvent;
import application.TimeUnits.Week;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
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
    void switchUser(ActionEvent switchUserEvent) {
    	System.out.println("switchUser: Attempting to switch user...");
    	initializeLoginView();
    }
    
    @FXML
    void logOut(ActionEvent logOutEvent) {
    	System.out.println("logOut: Attempting to log out...");
    	initializeLoginView();
    }
    
    @FXML
    void addEventMenu(ActionEvent addEventEvent) {
    	System.out.println("addEventMenu: Attempting to initialize EventManagerView...");
    	initializeEventMakerView();
    }
    
    @FXML 
    void removeEventMenu(ActionEvent removeEventEvent) {
    	System.out.println("removeEventMenu: Attempting to remove event...");
    	initializeEventViewerView();
    }
    
    @FXML
    void openAdminPreferences(ActionEvent openAdminPreferencesEvent) throws NullEventEndPointException, EventOutsideTimeUnitException {
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
    	
    	/*
    	getCurrentUser().dumpEvents();
    	
    	TimedEvent AdminTestEvent = new TimedEvent(ZonedDateTime.now(), ZonedDateTime.now().plusHours(1), "AdminTest", Color.RED);
		getCurrentUser().addEvent(AdminTestEvent);
		User.serializeUser(getCurrentUser());
		System.out.println("AdminTest: Event created with name '" + AdminTestEvent.getName() + "'.");
    	*/
    	
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
    void updateGUI() {
    	System.out.println("updateGUI: Updating GUI...");
		updateDateLabels(getSelectedDate());
		updateUpcomingEvents();
		updateDayView();
	}
    
    void updateDayView() {
    	// Get events from currentUser that exist within the specified date     	
    	System.out.println(String.format("%nupdateDayView: Attempting to update day view..."));
    	
    	System.out.println("updateDayView: User has '" + getCurrentUser().getEvents().size() + "' weeks stored in user data.");
    	
    	ArrayList<Week> userEventsTemp = getCurrentUser().getEvents();
    	
    	int weekOfYear = getSelectedDate().get(ChronoField.ALIGNED_WEEK_OF_YEAR);
    	int dayOfWeek = getSelectedDate().get(ChronoField.DAY_OF_WEEK);
    	System.out.println("updateDayView: Attempting to get day '" + dayOfWeek + "' of the '" + weekOfYear + "' week of the year...");

    	System.out.println("--> Attempting to get week '" + weekOfYear + "' of the year...");
    	
    	boolean weekExists = false;
    	for (Week w: userEventsTemp) {
    		if (w.getWeekNum() == weekOfYear) {
    			// Week 'weekOfYear' exists in userEvents.
    			System.out.println("1	--> Week '" + weekOfYear + "' exists in userEvents.");
    			weekExists = true;
    			
    	    	System.out.println("2	--> Attempting to get day '" + dayOfWeek + "' of the '" + weekOfYear + "' week of the year...");
    	    	Day dayTemp = w.getDay(dayOfWeek + 1);    
    	    	
    	    	ArrayList<Event> dayEventsTemp = dayTemp.getEvents();
    	    	
    	    	System.out.println("3	--> Attempting to get '" + dayEventsTemp.size() + "' events from day '" + dayOfWeek + "' of the '" + weekOfYear + "' week of the year...");
    	    	for (Event e : dayEventsTemp) {
    	        	System.out.println("4	--> Event '" + e.getName() + "' found.");
    	    		dayViewVBox.getChildren().add(generateEventBlock(e));
    	        	System.out.println("5	--> Created event block for event '" + e.getName() + "' found.");
    	    	}
    		}
    	}
    	if (!weekExists)
    		System.out.println("--> Week does NOT exist in user events.");
    	
    	System.out.println("updateDayView: Done.");
    }
    
    void updateUpcomingEvents() {
    	System.out.println("updateUpcomingEvents: Updating upcomingEventsVbox...");

    }
    
    void updateDateLabels(ZonedDateTime inputDate) {
    	System.out.println("updateDateLabels: Updating date labels...");

    	String formattedDate = dateLabelFormat.format(inputDate);
    	System.out.println("updateSelectedDateLabels: Attempting to update labels...");
    	dayDateLabel.setText(formattedDate);
    	weekDateLabel.setText(formattedDate);
    	monthDateLabel.setText(formattedDate);	
    }
    
	static Rectangle generateEventBlock(Event inputEvent) {
		Rectangle eventBlock = new Rectangle(200, 75, inputEvent.getColour());
		
		return eventBlock;
	}

}
