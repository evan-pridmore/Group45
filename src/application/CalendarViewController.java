package application;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

import application.TimeUnits.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;


/**A controller that manages {@link CalendarView.fxml} and is associated GUI components (e.g., buttons, labels, textfields, etc.)
 * 
 * Extends the abstract class {@link ApplicationController} which provides a range of static variables and non-abstract methods..
 * 
 * This should ONLY manage the associated GUI, and should NOT initialize any other stages, scenes, windows, views, etc.
 * (to switch windows or create new stages, add an initialize method in {@link ApplicationController}
 * 
 * @author evan-pridmore
 */
public class CalendarViewController extends ApplicationController  {
	
// Date Formatters:
	private DateTimeFormatter dateLabelFormat = DateTimeFormatter.ofPattern("EEEE, MMMM d, YYYY");
	private DateTimeFormatter simpleDateLabelFormat = DateTimeFormatter.ofPattern("d");
	private DateTimeFormatter eventDetailsFormat = DateTimeFormatter.ofPattern("h:mm a MMM d, YYYY");

// Upcoming Events Nodes:
	@FXML
	private VBox upcomingEventsVBox;
	
	@FXML
	private Label upcomingEventsLabel;
	
// Day View Nodes:
	@FXML
	private Label dayDateLabel;

	@FXML
	private DatePicker dayViewDatePicker;
	
	@FXML
	private AnchorPane dayViewAnchorPane;
	
	@FXML
	private VBox dayViewEventDetails;
	
// Week View Nodes:
	@FXML
	private Label weekDateLabel;
	
	@FXML
	private DatePicker weekViewDatePicker;
	
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
	private VBox weekViewEventDetails;
	
// Month View Nodes:
	@FXML
	private Label monthDateLabel;
	
	@FXML
	private DatePicker monthViewDatePicker;
	
	@FXML
	private VBox monthViewVBox;
	
	@FXML
	private VBox monthViewEventDetails;
	
// Menu Bar Methods:
    @FXML
    private void switchUser(ActionEvent switchUserEvent) {
    	System.out.println(String.format("switchUser: Attempting to switch user..."));
    	initializeLoginView();
    }
    
    @FXML
    private void logOut(ActionEvent logOutEvent) {
    	System.out.println(String.format("%nlogOut: Attempting to log out..."));
    	getAppStage().close();
    }
    
    @FXML
    private void addEventMenu(ActionEvent addEventEvent) {
    	System.out.println(String.format("%naddEventMenu: Attempting to initialize EventManagerView..."));
    	initializeEventMakerView();
    }
    
    @FXML
    private void manageEventsMenu(ActionEvent viewEventsEvent) {
    	System.out.println(String.format("%nremoveEventMenu: Attempting to remove event..."));
    	initializeEventViewerView();
    }
    
// Date Selector Methods:
    @FXML
    private void dayBackDate(ActionEvent changeDateEvent) {
    	setSelectedDate(getSelectedDate().minusDays(1));
    	updateDateLabels(getSelectedDate());
    	updateCalendarGUI();
    }
    
    @FXML
    private void dayForwardDate(ActionEvent changeDateEvent) {
    	setSelectedDate(getSelectedDate().plusDays(1));
    	updateDateLabels(getSelectedDate());
    	updateCalendarGUI();
    }
    
    @FXML
    private void weekBackDate(ActionEvent changeDateEvent) {
    	setSelectedDate(getSelectedDate().minusWeeks(1));
    	updateDateLabels(getSelectedDate());
    	updateCalendarGUI();
    }
    
    @FXML
    private void weekForwardDate(ActionEvent changeDateEvent) {
    	setSelectedDate(getSelectedDate().plusWeeks(1));
    	updateDateLabels(getSelectedDate());
    	updateCalendarGUI();
    }
    
    @FXML
    private void monthBackDate(ActionEvent changeDateEvent) {
    	setSelectedDate(getSelectedDate().minusMonths(1));
    	updateDateLabels(getSelectedDate());
    	updateCalendarGUI();
    }
    
    @FXML
    private void monthForwardDate(ActionEvent changeDateEvent) {
    	setSelectedDate(getSelectedDate().plusMonths(1));
    	updateDateLabels(getSelectedDate());
    	updateCalendarGUI();
    }
    
    @FXML
    private void viewDatePicked(ActionEvent dayViewDatePickedEvent) throws Exception {
    	System.out.println("dayViewDatePicked: Updating selected date..." + dayViewDatePicker.getValue());
    	
    	ZonedDateTime datePickerDate = null;
    	if (dayViewDatePicker.getValue() != null) {
            setSelectedDate(dayViewDatePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()));
    	} else if (weekViewDatePicker.getValue() != null) {
            setSelectedDate(weekViewDatePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()));
    	} else if (monthViewDatePicker.getValue() != null) {
            setSelectedDate(monthViewDatePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()));
    	} else {
    		throw new Exception ("Error updating selected date based on date picker.");
    	}
    	
        System.out.println("--> Selected updated to '"+ datePickerDate + "'.");  
    	updateCalendarGUI();
    }
    
// Value Conversion Methods:
    /**A method used to convert between the {@link ChronoField} concept of weekdays (Monday = 1, Sunday = 7) and the {@link TimeUnit} concept of weekdays
    * (Sunday = 1, Saturday = 7).
    * 
    * @param {@link ZonedDateTime} Date
    * @return Numeric day of week (Sunday = 1, Saturday = 7).
    */
    public int getConvertedDayOfWeek(ZonedDateTime inputDay) {
    	// Gets the specified weekOfYear and dayOfWeek (Requires translating between ChronoField definition of dayOfWeek and TimeUnit definition of dayOfWeek)...
    		// (ChronoField: Monday = 1, Sunday = 7 --> TimeUnit: Sunday = 1, Saturday = 7) 
     	int dayOfWeek = inputDay.get(ChronoField.DAY_OF_WEEK);	
    	if (dayOfWeek == 7) {
    		dayOfWeek = 1;
    	} else {
    		dayOfWeek = dayOfWeek + 1;
    	}
    	return dayOfWeek;
    }
       
    // GUI Update Methods:
    /**A method that updates the GUI specifically in {@link CalendarView.fxml} through the {@link CalendarViewController} class. <p>
     * 
     * Runs at during initialization (i.e., when {@link initializeCalendarView} is called) to update GUI components with 
     * updated information. This may include: <br>
     * --> updating the date displayed in the labels of the CalendarView GUI.
     * --> checking for and updating the displayed events on the present view.
     */
    public void updateCalendarGUI() {
    	System.out.println(String.format("%nupdateCalendarGUI: Updating GUI..."));
    	System.out.println(String.format("--> Selected date is '%s'", getSelectedDate().format(dateLabelFormat)));
    	
    	// Clears each event detail pane in order to prevent the addition of duplicate nodes.
    	dayViewEventDetails.getChildren().clear();
    	weekViewEventDetails.getChildren().clear();
    	monthViewEventDetails.getChildren().clear();
    	
    	// Updates the each component of the calendar view GUI.
		updateDateLabels(getSelectedDate());
		updateUpcomingEvents();
		updateDayView();
		updateWeekView();
		updateMonthView();
	}
     
    
    /**A method used to trigger an update of the all labels controlled by {@link CalendarViewController} reflecting the selected date. 
     * 
     * @param inputDate
     */
    private void updateDateLabels(ZonedDateTime inputDate) {
    	System.out.println(String.format("%nupdateDateLabels: Updating date labels..."));

    	String formattedDate = dateLabelFormat.format(inputDate);
    	dayDateLabel.setText(formattedDate);
    	weekDateLabel.setText(formattedDate);
    	monthDateLabel.setText(formattedDate);
    	
    }
    
    /**A method used to trigger an update of the contents of the day view.
     * 
     */
    
    private void updateDayView() {
     	System.out.println(String.format("%nupdateDayView: Updating day view..."));
     	updateDayPane(getSelectedDate(), dayViewAnchorPane, 240);
     }
    
    /**A method used to trigger an update of the contents of the week view.
     * 
     */
    
    private void updateWeekView() {
     	System.out.println(String.format("%nupdateWeekView: Updating week view..."));
     	
     	System.out.println(String.format("Selected Date: '%s'", getSelectedDate().format(dateLabelFormat)));
     	
     	// input date for week view must correspond to the appropriate week.
     	// Get the beginning date of the desired week.
     	int dayOfWeek = getConvertedDayOfWeek(getSelectedDate());
     
     	// Calculating the beginning date of a week...
     	ZonedDateTime weekStart = getSelectedDate().minusDays(dayOfWeek - 1);
     	
     	updateDayPane(weekStart, weekViewAnchorPane1, 110);
     	updateDayPane(weekStart.plusDays(1), weekViewAnchorPane2, 110);
     	updateDayPane(weekStart.plusDays(2), weekViewAnchorPane3, 110);
     	updateDayPane(weekStart.plusDays(3), weekViewAnchorPane4, 110);
     	updateDayPane(weekStart.plusDays(4), weekViewAnchorPane5, 110);
     	updateDayPane(weekStart.plusDays(5), weekViewAnchorPane6, 110);
     	updateDayPane(weekStart.plusDays(6), weekViewAnchorPane7, 110);

    }
    
    /**A method used to trigger an update of the contents of the month view.
     * 
     * <p>This finds the weeks that are included in a specified month INCLUSIVELY (i.e., weeks are included in their entirety, rather than exclusively containing
     * days within a month.) This avoids the issue of having to worry about beginning a month mid-week, and allows us to generate weeks from Sunday through to 
     * Saturday.
     * 
     * <p>Associated events in the days of the weeks in the specified month are passed onto {@link addSimpleEventBlock} to add event blocks.
     */
      
    private void updateMonthView() {
     	System.out.println(String.format("%nupdateMonthView: Updating month view..."));
      
     	// Clearing VBox to prevent duplicate event blocks from being added...     	
     	monthViewVBox.getChildren().clear();
     	
    	// Sequentially increase days of the month to keep track of calendar nuances (i.e., different end of the month dates, 30 vs. 31, etc.).
     	// Gets the date of the first day of the first week of a month.
     	ZonedDateTime monthStart = getSelectedDate().minusDays(getSelectedDate().getDayOfMonth() - 1);
     	ZonedDateTime firstWeekStart = monthStart.minusDays(getConvertedDayOfWeek(monthStart) - 1);
     	
     	// Generate dates in the specified month.
 		HBox weekHBox = null;
     	for (ZonedDateTime dayOfMonth = firstWeekStart; dayOfMonth.isBefore(monthStart.plusMonths(1)); dayOfMonth = dayOfMonth.plusDays(1)) {
         	System.out.println(String.format("--> Generating date '%s'", dayOfMonth.format(dateLabelFormat)));
         	
     		// On every Sunday, a new HBox is created to contain each day. Style and formatting of HBox is set.
     		if (getConvertedDayOfWeek(dayOfMonth) == 1) {
     			System.out.println("	--> Created a new week HBox.");
     			weekHBox = new HBox();
    			weekHBox.setMinSize(850, 200);
    			weekHBox.setMaxSize(850, 200);    
    			monthViewVBox.getChildren().add(weekHBox);
			}
     		
     		// On every day, a new VBox is created to contain events. Style and formatting of VBox is set.
 			System.out.println("	--> Created a new day VBox.");
     		VBox dayVBox = new VBox();
			dayVBox.setMinSize(121.1, 200);
			dayVBox.setStyle("-fx-border-color:#B8B8B8; -fx-border-width: 0.25; -fx-border-style: solid;");
			dayVBox.setId("monthViewVBox" + dayOfMonth.format(simpleDateLabelFormat));
	 		dayVBox.setSpacing(2);
			
			// On every day, a label is added as the first node in each day VBox to indicate the numeric day of the month.
			Label dayOfMonthLabel = new Label(dayOfMonth.format(simpleDateLabelFormat));
			dayOfMonthLabel.setOpacity(0.6);
			VBox.setMargin(dayOfMonthLabel, new Insets(0,4,0,0));
			
			dayVBox.setAlignment(Pos.TOP_RIGHT);
			dayVBox.getChildren().add(dayOfMonthLabel);
			
			// Gets events for every day in the specified month. eventCount is used to limit the number of events added to 8 in order to to prevent overlap.
			int eventCount = 0;
			int weekOfYear = dayOfMonth.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
	     	int dayOfWeek = getConvertedDayOfWeek(dayOfMonth);	
	     	
	     	// Getting the specified span of weeks in the specified month.
           	for (Week w: getCurrentUser().getEvents()) {
         		if (w.getWeekNum() == weekOfYear) {
         			
         			// Getting the specified day in the specified week.
         			Day dayTemp = w.getDay(dayOfWeek);
         			if (dayTemp.getStart().getYear() == dayOfMonth.getYear()) {
         				
         				// Getting every event in the specified day.
	         	    	for (Event e : dayTemp.getEvents()) {
	         	    		
	         	    			// Aforementioned event block count limiter.
	         	    			if (eventCount <= 7) {
	         	    				
	         	    				// Discovered events are passed into 'addSimpleEventBlock' alongside the appropriate VBox to create event blocks and and add them
	         	    				// to the VBox.
	                 				addSimpleEventBlock(e, dayVBox);
	                 				eventCount ++;
	         	    			} else {
	         	    				// In the case that there are MORE than 8 events in a single day VBox, a final '...' event block is created and added to the
	         	    				// VBox to indicate the presence of additional events in a single date.
	         	    				StackPane overflowPane = new StackPane();
	         	    				
	         	    				Rectangle overflowRectangle = new Rectangle(110, 14, Color.LIGHTGRAY);
	         	    	    		overflowRectangle.setArcHeight(10);
	         	    	    		overflowRectangle.setArcWidth(10);
	         	    	    		
	         	    				Label overflowLabel = new Label("...");
	         	    				overflowLabel.setAlignment(Pos.CENTER);
	         	    				overflowLabel.setMinWidth(100);
	         	    				
	         	    				overflowPane.getChildren().addAll(overflowRectangle, overflowLabel);
	         	    				
	         	    				dayVBox.getChildren().add(overflowPane);
	         	    				break;
         	    			}
         				}
         			}
     	    	}
     		}  
           	// After a day VBox is created with the appropriate events, it is added to its week HBox.
			weekHBox.getChildren().add(dayVBox);
     	}
    }
    
    /**A method used to trigger an update of the contents of the upcoming events view.
     * 
     * <p>The 10 closest events are found and added to the upcoming events VBox, limited to the next four weeks.
     */
           
    private void updateUpcomingEvents() {
    	// This should NOT be dependent on 'selectedDate' because it is relative to the current date not the selected date.
    	// Get the 10 CLOSEST events from the CURRENT DATE.
    	System.out.println(String.format("%nupdateUpcomingEvents: Updating upcomingEventsVbox..."));
    	
     	// Clears VBox to prevent the addition of duplicate event blocks.   	
     	upcomingEventsVBox.getChildren().clear();
    	
    	ZonedDateTime currentDate = ZonedDateTime.now();
    	int weekOfYear = currentDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
     	int dayOfWeek = getConvertedDayOfWeek(currentDate);	
     	
    	int eventCount = 0;
    	int addWeek = 0;
    	
    	// Rather than searching through the entirety of saved weeks, this limits the number of events to the next four weeks.
     	while (addWeek < 4) {
         	System.out.println("--> Attempting to get week '" + (weekOfYear + addWeek) + "' of the year...");
           
         	// Gets the specified week in the weeks stored in the user data file.
         	for (Week w: getCurrentUser().getEvents()) {
         		if (w.getWeekNum() == weekOfYear + addWeek) {
         			
         			// Gets the specified day in the specified week.
         			for (int dayCount = 1; dayCount <= 7; dayCount ++) {
             			Day dayTemp = w.getDay(dayCount); 
	             		if (dayTemp.getStart().getYear() == currentDate.getYear()) {
             			System.out.println("	--> Attempting to get '" + dayTemp.getEvents().size() + "' events from day '" + dayOfWeek + "' of the '" + weekOfYear + "' week of the year...");
	             	    
             			// Gets the events in the specified day.
             			for (Event e : dayTemp.getEvents()) {
             				
             					// Aforementioned event limiter.
	                 			if (eventCount < 10) {
	                 				addSimpleEventBlock(e, upcomingEventsVBox);
	                 				eventCount ++;
	                 			}
                 			}
             	    	}
         			}
         		}
           	}
           	addWeek ++;
     	}
     	
     	// In the case that there are no upcoming events in the next four weeks, a label is added to the VBox indicate this.
     	if (eventCount == 0) {
     		System.out.println("There are NO upcoming events in the next four weeks.");
     		Label noUpcomingEventsLabel = new Label("No upcoming events.");
	 		upcomingEventsVBox.setAlignment(Pos.TOP_CENTER);	 		
     		upcomingEventsVBox.getChildren().add(noUpcomingEventsLabel);
     	}
    }
    
    /**A method used to update BOTH the AnchorPanes in the day view and in the week view.
     * 
     * <p> Given that both day view and week view both have scheduled events, a single function can be adapted for both.
     * 
     * @param inputDate The specified date to update an AnchorPane to.
     * @param inputPane The AnchorPane to update.
     * @param inputWidth The width of the event block to add.
     */
    
    private void updateDayPane(ZonedDateTime inputDate, AnchorPane inputPane, int inputWidth) {
    	int weekOfYear = inputDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
     	int dayOfWeek = getConvertedDayOfWeek(inputDate);	

    	// Clears VBox to prevent duplicate event blocks from being added.   	
     	inputPane.getChildren().clear();

     	// Gets the specified week of the specified date.
       	for (Week w: getCurrentUser().getEvents()) {
       		
 			// Getting the specified day in the specified week.
     		if (w.getWeekNum() == weekOfYear) {
     			Day dayTemp = w.getDay(dayOfWeek);
     			if (dayTemp.getStart().getYear() == inputDate.getYear()) {
     				
     				// Gets the events in the specified day.
     				for (Event e : dayTemp.getEvents()) {
     					addScheduledEventBlock(e, inputPane);
     				}
     	    	}
     		}
       	}
    }   
    
// Add event block methods.
    /**A method used to create and add event blocks with a <u>scheduled</u> to a specified AnchorPane.
     * 
     * <p>Considering the generic storage of {@link Event} in the user data file, and the different natures of {@link TimedEvent} 
     * and {@link InstantEvent}, different generation processes are specified for each type.
     * 
     * @param inputEvent The instance of Event that is to be added to the specified pane.
     * @param inputPane The target pane.
     */
	private static void addScheduledEventBlock(Event inputEvent, AnchorPane inputPane) {
		System.out.println(String.format("		--> addDayEventBlock: Creating event block for event '%s'...", inputEvent.getName()));
		
    	Insets eventBlockInsets = new Insets(0, 5, 0, 5);
    	
		// Each hour in every pane is 60px. Thus, each minute is 1px.			
		if (inputEvent instanceof TimedEvent) {
			
			// Start POSITION should be equal to the number of minutes from the beginning of the day.		
			double startPos = inputEvent.getStart().get(ChronoField.MINUTE_OF_DAY) + 7;
			double endPos = inputEvent.getEnd().get(ChronoField.MINUTE_OF_DAY) + 7;
			
			// Height should be equal to the difference between startPos and endPos
			double height = endPos - startPos;
						
			// Creating event block rectangles with specific dimensions based on the origin on the pane (i.e., day view vs. week view). 
			Rectangle eventBlock;
			if (inputPane.getId().contains("day")) {
		 		eventBlock = new Rectangle(240, height, inputEvent.getColour().deriveColor(1.0, 1.0, 1.0, 0.5));
			} else {
		 		eventBlock = new Rectangle(113, height, inputEvent.getColour().deriveColor(1.0, 1.0, 1.0, 0.5));
			}
			
			// Setting event block rectangle properties.
			eventBlock.setArcHeight(10);
			eventBlock.setArcWidth(10);
	 		eventBlock.setStrokeType(StrokeType.INSIDE);
	  		eventBlock.setStrokeWidth(2.0);
	  		eventBlock.setStroke(inputEvent.getColour().deriveColor(1.0, 1.0, 0.25, 0.5));
			eventBlock.setBlendMode(BlendMode.COLOR_BURN);
			
			// Creating event block label, and setting properties.
	 		Label eventBlockLabel = new Label(inputEvent.getName());
	 		eventBlockLabel.setWrapText(true);
	 		eventBlockLabel.setMaxWidth(eventBlock.getWidth() - 5);
	 		eventBlockLabel.setTextOverrun(OverrunStyle.ELLIPSIS);
	 		eventBlockLabel.setAlignment(Pos.TOP_CENTER);
	 		eventBlockLabel.setPadding(eventBlockInsets);
	 		
	 		// Setting event block label with either white or black text, dependent on the colour of the event block rectangle.
	 		if (inputEvent.getColour().getBrightness() > 0.6) {
		 		eventBlockLabel.setTextFill(Color.BLACK);
	 		} else {
		 		eventBlockLabel.setTextFill(Color.WHITE);
	 		}
	 		
	 		// Creating StackPane to contain the event rectangle and label
	 		StackPane eventBlockPane = new StackPane(eventBlock, eventBlockLabel);
	 		StackPane.setAlignment(eventBlockLabel, Pos.TOP_CENTER);
	 		
	 		// Adding mouse click functionality to the event block stack pane to initiate display of event details.
	 		eventBlockPane.setOnMouseClicked(mouseEvent -> {
	 	    	getCalendarController().showEventDetails(inputEvent, inputPane);
	 	    	});
	 		
	 		// Adding StackPane to AnchorPane
	 		AnchorPane.setTopAnchor(eventBlockPane, startPos);
	 		inputPane.getChildren().add(eventBlockPane);
	 		
		} else if (inputEvent instanceof InstantEvent) {
			// An InstantEvent occurs at a SINGLE point of time.		
			// Start POSITION should be equal to the number of minutes from the beginning of the day.
			double startPos = ((InstantEvent) inputEvent).getStart().get(ChronoField.MINUTE_OF_DAY) + 1;
						 		
			// Creating event block rectangles with specific dimensions based on the origin on the pane (i.e., day view vs. week view). 
			Rectangle eventBlock;
			if (inputPane.getId().contains("day")) {
		 		eventBlock = new Rectangle(240, 14, inputEvent.getColour().deriveColor(1.0, 1.0, 1.0, 0.5));
			} else {
		 		eventBlock = new Rectangle(113, 14, inputEvent.getColour().deriveColor(1.0, 1.0, 1.0, 0.5));
			}
			
			// Setting event block rectangle properties.
			eventBlock.setArcHeight(10);
			eventBlock.setArcWidth(10);
	 		eventBlock.setStrokeType(StrokeType.INSIDE);
	  		eventBlock.setStrokeWidth(2.0);
	  		eventBlock.setStroke(inputEvent.getColour().deriveColor(1.0, 1.0, 0.25, 0.5));
	 		eventBlock.setBlendMode(BlendMode.COLOR_BURN);
	 		
			// Creating event block label, and setting properties.
			Label eventBlockLabel = new Label(inputEvent.getName());	 		
	 		eventBlockLabel.setMaxWidth(eventBlock.getWidth() - 5);
	 		eventBlockLabel.setTextOverrun(OverrunStyle.ELLIPSIS);
	 		eventBlockLabel.setAlignment(Pos.TOP_CENTER);
	 		eventBlockLabel.setPadding(eventBlockInsets);
	 		
	 		// Setting event block label with either white or black text, dependent on the colour of the event block rectangle.
	 		if (inputEvent.getColour().getBrightness() > 0.6) {
		 		eventBlockLabel.setTextFill(Color.BLACK);
	 		} else {
		 		eventBlockLabel.setTextFill(Color.WHITE);
	 		}
	 		
	 		// Creating StackPane to contain the event rectangle and label
	 		StackPane eventBlockPane = new StackPane(eventBlock, eventBlockLabel);
	 		StackPane.setAlignment(eventBlockLabel, Pos.TOP_CENTER);	 		 
	 		
	 		// Adding mouse click functionality to the event block stack pane to initiate display of event details.
	 		eventBlockPane.setOnMouseClicked(mouseEvent -> {
	 	    	getCalendarController().showEventDetails(inputEvent, inputPane);
	 	    	});
	 		
	 		// Adding StackPane to AnchorPane
	 		AnchorPane.setTopAnchor(eventBlockPane, startPos);
	 		inputPane.getChildren().add(eventBlockPane);
		}
 	}
	
    /**A method used to create and add event blocks with a <u>unscheduled</u> to a specified VBox.
     * 
     *<p>Ignores whether an event is of type {@link TimedEvent} or {@link InstantEvent}, an eventBlock is added to the 
     *VBox provided as an argument. Simplified event blocks are the same dimensions.
     * 
     * @param inputEvent The instance of Event that is to be added to the specified pane.
     * @param inputPane The target pane.
     */
	private static void addSimpleEventBlock(Event inputEvent, VBox inputPane) {    	
    	// Month view: VBox (120, 200), EventBlock (110, 14)
    	// Upcoming View: VBox (200, 690), EventBlock (190, 64)
    	Insets eventBlockLabelInsets = new Insets(0, 5, 0, 5);
    	 		
    	// Creates the label of the event block, and sets its properties.
 		Label eventBlockLabel = new Label(inputEvent.getName());
 		eventBlockLabel.setTextOverrun(OverrunStyle.ELLIPSIS);
 		eventBlockLabel.setAlignment(Pos.TOP_CENTER);
 		eventBlockLabel.setPadding(eventBlockLabelInsets);
 		
 		// Sets event block label with either white or black text, dependent on the colour of the event block rectangle.
 		if (inputEvent.getColour().getBrightness() > 0.6) {
	 		eventBlockLabel.setTextFill(Color.BLACK);
 		} else {
	 		eventBlockLabel.setTextFill(Color.WHITE);
 		}
 		
		// Creating event block rectangles with specific dimensions and properties based on the origin on the pane 
 		// (i.e., month view vs. upcoming events view). 
    	Rectangle eventBlock;
    	if (inputPane.getId().contains("upcoming")) {
    		eventBlock = new Rectangle(190, 60, inputEvent.getColour().deriveColor(1.0, 1.0, 1.0, 0.5));
    		eventBlock.setArcHeight(7.5);
    		eventBlock.setArcWidth(7.5);
    		eventBlockLabel.setMaxHeight(eventBlock.getHeight() - 10);
    		eventBlockLabel.setWrapText(true);
    	} else {
        	eventBlock = new Rectangle(110, 14, inputEvent.getColour().deriveColor(1.0, 1.0, 1.0, 0.5));
    		eventBlock.setArcHeight(10);
    		eventBlock.setArcWidth(10);
    	}
    	
    	// Specifies properties of the event block rectangle and label which are common between both views.
 		eventBlockLabel.setMaxWidth(eventBlock.getWidth() - 10);
 		
 		eventBlock.setStrokeType(StrokeType.INSIDE);
  		eventBlock.setStrokeWidth(2.0);
  		eventBlock.setStroke(inputEvent.getColour().deriveColor(1.0, 1.0, 0.25, 0.5));
 		eventBlock.setBlendMode(BlendMode.COLOR_BURN);

 		// Creating StackPane to contain the event rectangle and label
 		StackPane eventBlockPane = new StackPane(eventBlock, eventBlockLabel);
 		StackPane.setAlignment(eventBlockLabel, Pos.TOP_CENTER);
 		
 		// Adding mouse click functionality to the event block stack pane to initiate display of event details.
 		eventBlockPane.setOnMouseClicked(mouseEvent -> {
 	    	getCalendarController().showEventDetails(inputEvent, inputPane);
 	    	});
 		
 		// Adding StackPane to VBox
 		inputPane.getChildren().add(eventBlockPane);
	}
	
// Event detail pane methods.
	/**A method initiated by the click function of every event block that has been created. This updates EVERY details panel across all views. 
	 * 
	 * @param inputEvent The event provided to access details from.
	 * @param inputPane The pane the event has been added to.
	 */
	private void showEventDetails(Event inputEvent, Pane inputPane) {
		System.out.println(String.format("showEventDetails: Attempting to show event details for event '%s'...", inputEvent.getName()));

		// To simplify the process of iteratively adding nodes to each event detail panel, references for each panel are added to an array.
		VBox[] VBoxArray = {dayViewEventDetails, weekViewEventDetails, monthViewEventDetails};
		for (VBox detailsVBox : VBoxArray) {
			
			//To prevent the addition of duplicate nodes, the panel is initially cleared.
			detailsVBox.getChildren().clear();
			detailsVBox.setAlignment(Pos.CENTER);
			detailsVBox.setSpacing(10);
			
			// Creates a rectangle to hold the details of each event, corresponding to the event block's colour and properties.
			Rectangle eventDetailsBlock = new Rectangle(detailsVBox.getPrefWidth() - 15, 200, inputEvent.getColour().deriveColor(1.0, 1.0, 1.0, 0.5));
	 		eventDetailsBlock.setArcWidth(15);
	 		eventDetailsBlock.setArcHeight(15); 		
	 		eventDetailsBlock.setStrokeType(StrokeType.INSIDE);
	 		eventDetailsBlock.setStrokeWidth(2.0);
	  		eventDetailsBlock.setStroke(inputEvent.getColour().deriveColor(1.0, 1.0, 0.25, 0.5));
	 		eventDetailsBlock.setBlendMode(BlendMode.COLOR_BURN);

	 		// Creates a label to display the details of each event, which differs depending on the subclass of event. 
	 		// (i.e, Instant event does NOT have an end date).
	 		Label eventDetailsLabel = null;
	 		if (inputEvent instanceof TimedEvent) {
	 			eventDetailsLabel = new Label(String.format("Name: %s%nEvent Type: Deadline%nStart Date: %s%nEnd Date: %s%n", inputEvent.getName(), inputEvent.getStart().format(eventDetailsFormat), inputEvent.getEnd().format(eventDetailsFormat)));
	 		} else if (inputEvent instanceof InstantEvent){
	 			eventDetailsLabel = new Label(String.format("Name: %s%nEvent Type: Timed Event%nTime: %s%n", inputEvent.getName(), inputEvent.getStart().format(eventDetailsFormat)));
	 		}

	 		eventDetailsLabel.setMaxSize(eventDetailsBlock.getWidth() - 15, eventDetailsBlock.getHeight() - 15);
	 		eventDetailsLabel.setTextOverrun(OverrunStyle.ELLIPSIS);
	 		eventDetailsLabel.setWrapText(true);
	 		
	 		// Creates StackPane to contain the event block rectangle and label.
			StackPane eventDetailsPane = new StackPane(eventDetailsBlock, eventDetailsLabel);
			
			// Creates a button with a mouse event as a shortcut to edit this specific event.
			Button editButton = new Button("Edit");
	 		editButton.setOnMouseClicked(mouseEvent -> {
	 			super.initializeEventManagerView(inputEvent, true);
	 	    	});
			
	 		// Adds StackPane to event detail pane.
			detailsVBox.getChildren().addAll(eventDetailsPane, editButton);
		}
	}
	

}