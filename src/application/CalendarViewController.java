package application;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;

import application.TimeUnits.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
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
	private Label dayDateLabel;
	
	@FXML
	private AnchorPane dayViewAnchorPane;
	
	@FXML
	private Label weekDateLabel;
	
	@FXML
	private AnchorPane weekViewAnchorPane1;
	
	@FXML
	private AnchorPane weekViewAnchorPane2;

	@FXML
	private AnchorPane weekViewAnchorPane3;

	@FXML
	private AnchorPane weekViewAnchorPane4;

	@FXML
	private AnchorPane weekViewAnchorPane5;

	@FXML
	private AnchorPane weekViewAnchorPane6;

	@FXML
	private AnchorPane weekViewAnchorPane7;

	
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
    public void updateCalendarGUI() {
    	System.out.println(String.format("%nupdateGUI: Updating GUI..."));
    	System.out.println(String.format("--> Selected date is '%s'. (Week '%s', Day '%s')", getSelectedDate().format(dateLabelFormat), getSelectedDate().get(ChronoField.ALIGNED_WEEK_OF_YEAR), getSelectedDate().get(ChronoField.DAY_OF_WEEK) + 1));
    	
		updateDateLabels(getSelectedDate());
		updateUpcomingEvents();
		updateDayView();
		updateWeekView();
	}
    
    public int getConvertedDayOfWeek(ZonedDateTime inputDay) {
    	// Gets the specified weekOfYear and dayOfWeek (Requires translating between ChronoField definition of dayOfWeek and TimeUnit definition of dayOfWeek)...
    	// (ChronoField: Monday = 1, Sunday = 7 --> TimeUnit: Sunday = 1, Saturday = 7) 
     	int dayOfWeek = inputDay.get(ChronoField.DAY_OF_WEEK);	
    	if (dayOfWeek == 7) {
    		System.out.print("dayOfWeek is Sunday. (ChronoField = 7, TimeUnit = 1). Converting to TimeUnit..."); 
    		dayOfWeek = 1;
    		System.out.println(" dayOfWeek is now '" + dayOfWeek + "'."); 
    	} else {
    		System.out.print(String.format("dayOfWeek is '%s'. (ChronoField = '%s', TimeUnit = '%s'. Converting to TimeUnit...", dayOfWeek, dayOfWeek, dayOfWeek + 1)); 
    		dayOfWeek = dayOfWeek + 1;
    		System.out.println(" dayOfWeek is now '" + dayOfWeek + "'."); 
    	}
    	return dayOfWeek;
    }
    
    void updateDayView() {
     	System.out.println(String.format("%nupdateDayView: Updating day view..."));
     	updateEventPane(getSelectedDate(), dayViewAnchorPane, 220);
     	System.out.println("updateDayView: Done.");
     }
    
    void updateWeekView() {
     	System.out.println(String.format("%nupdateWeekView: Updating week view..."));
     	
     	//System.out.println(String.format(""));
     	System.out.println(String.format("Selected Date: '%s'", getSelectedDate().format(dateLabelFormat)));
     	
     	// input date for week view must correspond to the appropriate day of the week.
     	// Get the beginning date of the desired week.
     	int dayOfWeek = getConvertedDayOfWeek(getSelectedDate());
     
     	// Calculating the beginning date of a week...
     	ZonedDateTime weekStart = getSelectedDate().minusDays(dayOfWeek - 1);
     	System.out.println(String.format("Beginning of week: '%s'", weekStart.format(dateLabelFormat)));

     	
     	updateEventPane(weekStart, weekViewAnchorPane1, 100);
     	updateEventPane(weekStart.plusDays(1), weekViewAnchorPane2, 100);
     	updateEventPane(weekStart.plusDays(2), weekViewAnchorPane3, 100);
     	updateEventPane(weekStart.plusDays(3), weekViewAnchorPane4, 100);
     	updateEventPane(weekStart.plusDays(4), weekViewAnchorPane5, 100);
     	updateEventPane(weekStart.plusDays(5), weekViewAnchorPane6, 100);
     	updateEventPane(weekStart.plusDays(6), weekViewAnchorPane7, 100);

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
    
    void updateEventPane(ZonedDateTime inputDay, AnchorPane inputPane, int inputWidth) {
    	// Gets the specified weekOfYear and dayOfWeek (Requires translating between ChronoField definition of dayOfWeek and TimeUnit definition of dayOfWeek)...
    	// (ChronoField: Monday = 1, Sunday = 7 --> TimeUnit: Sunday = 1, Saturday = 7) 
    	int weekOfYear = inputDay.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
     	int dayOfWeek = getConvertedDayOfWeek(inputDay);	

    	// Clearing VBox to prevent duplicate event blocks from being added...     	
     	System.out.print(String.format("Clearing pane..."));
     	inputPane.getChildren().clear();
     	System.out.println(String.format("	Cleared."));

     	// Specifying the specific date to find events in...
     	System.out.println(String.format("User has '" + getCurrentUser().getEvents().size() + "' weeks stored in user data."));
     	System.out.println("Attempting to get day '" + dayOfWeek + "' of the '" + weekOfYear + "' week of the year...");

     	// Getting the specified week of the specified date...
     	System.out.print("--> Attempting to get week '" + weekOfYear + "' of the year...");
     	boolean weekExists = false;
       	for (Week w: getCurrentUser().getEvents()) {
     		if (w.getWeekNum() == weekOfYear) {
     			// Week 'weekOfYear' exists in userEvents.
     			System.out.println("	Success!");
     			
     			// Getting the specified day in the specified week.
     			Day dayTemp = w.getDay(dayOfWeek); 
     			System.out.println("	--> Attempting to get '" + dayTemp.getEvents().size() + "' events from day '" + dayOfWeek + "' of the '" + weekOfYear + "' week of the year...");
     	    	for (Event e : dayTemp.getEvents()) {
     	        	System.out.println("		--> Event '" + e.getName() + "' found.");
     	        	addEventBlock(e, inputPane, inputWidth);
     	        	System.out.println(String.format("			--> Created event block for event '" + e.getName() + "'."));
     	    	}
     		}
     	if (!weekExists)
     		System.out.println("--> Week does NOT exist in user events.");
       	}
    }
    
	static void addEventBlock(Event inputEvent, AnchorPane inputPane, int inputWidth) {
    	Insets eventBlockInsets = new Insets(0, 5, 0, 5);
		// Each hour in every pane is 60px. Thus, each minute is 1px.			
		if (inputEvent instanceof TimedEvent) {
			// Start POSITION should be equal to the number of minutes from the beginning of the day.		
			double startPos = inputEvent.getStart().get(ChronoField.MINUTE_OF_DAY) + 7;
			double endPos = inputEvent.getEnd().get(ChronoField.MINUTE_OF_DAY) + 7;
			
			// Height should be equal to the difference between startPos and endPos
			double height = endPos - startPos;
			
			System.out.println(String.format("		--> Timed event '%s' has a start time of '%s' ('%s' px) and and end time of '%s' ('%s' px)", inputEvent.getName(), inputEvent.getStart().toString().substring(0, 16), startPos, inputEvent.getEnd().toString().substring(11, 16), endPos));
			
			// Creating rectangle and label components of the event block...
	 		Rectangle eventBlock = new Rectangle(inputWidth, height, inputEvent.getColour().deriveColor(1.0, 1.0, 1.0, 0.5));
	 		Label eventBlockLabel = new Label(inputEvent.getName());
	 		
	 		eventBlock.setBlendMode(BlendMode.COLOR_BURN);
	 		
	 		// Setting label font and colour...
	 		eventBlockLabel.setStyle("-fx-font-weight: Bold");
	 		eventBlockLabel.setWrapText(true);
	 		eventBlockLabel.setMaxWidth(inputWidth);
	 		eventBlockLabel.setAlignment(Pos.TOP_CENTER);
	 		eventBlockLabel.setPadding(eventBlockInsets);
	 		
	 		if (inputEvent.getColour().getBrightness() > 0.6) {
		 		eventBlockLabel.setTextFill(Color.BLACK);
	 		} else {
		 		eventBlockLabel.setTextFill(Color.WHITE);
	 		}
	 		
	 		// Creating StackPane to contain the event rectangle and label...
	 		StackPane eventBlockPane = new StackPane(eventBlock, eventBlockLabel);
	 		StackPane.setAlignment(eventBlockLabel, Pos.TOP_CENTER);	 		
	 		
	 		// Adding StackPane to AnchorPane...
	 		inputPane.setTopAnchor(eventBlockPane, startPos);
	 		inputPane.getChildren().add(eventBlockPane);
	 		
		} else if (inputEvent instanceof InstantEvent) {
			// An InstantEvent occurs at a single point of time.		
			// Start POSITION should be equal to the number of minutes from the beginning of the day.
			double startPos = ((InstantEvent) inputEvent).getEventTime().get(ChronoField.MINUTE_OF_DAY) + 1;
			
			System.out.println(String.format("		--> Instant event '%s' has a time of '%s' ('%s' px).", inputEvent.getName(), inputEvent.getStart().toString().substring(0, 16), startPos));
			 		
			// Creating rectangle and label components of the event block...
	 		Rectangle eventBlock = new Rectangle(inputWidth, 15, inputEvent.getColour().deriveColor(1.0, 1.0, 1.0, 0.5));
	 		Label eventBlockLabel = new Label(inputEvent.getName());

	 		eventBlock.setBlendMode(BlendMode.COLOR_BURN);
	 		
	 		// Setting label font and colour...
	 		eventBlockLabel.setStyle("-fx-font-weight: Bold");
	 		eventBlockLabel.setMaxWidth(inputWidth);
	 		eventBlockLabel.setWrapText(true);
	 		eventBlockLabel.setAlignment(Pos.TOP_CENTER);
	 		eventBlockLabel.setPadding(eventBlockInsets);
	 		
	 		if (inputEvent.getColour().getBrightness() > 0.6) {
		 		eventBlockLabel.setTextFill(Color.BLACK);
	 		} else {
		 		eventBlockLabel.setTextFill(Color.WHITE);
	 		}
	 		
	 		// Creating StackPane to contain the event rectangle and label...
	 		StackPane eventBlockPane = new StackPane(eventBlock, eventBlockLabel);
	 		StackPane.setAlignment(eventBlockLabel, Pos.TOP_CENTER);	 		
	 		
	 		// Adding StackPane to AnchorPane...
	 		inputPane.setTopAnchor(eventBlockPane, startPos);
	 		inputPane.getChildren().add(eventBlockPane);
	 		
		} else {
			System.out.println("Event block creation error.");
		}
 	}
}
