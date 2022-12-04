package application;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import application.TimeUnits.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
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
	public DateTimeFormatter simpleDateLabelFormat = DateTimeFormatter.ofPattern("dd");
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
	private Button dayBackDateButton;
	
	@FXML
	private Button dayForwardDateButton;
	
	@FXML
	private DatePicker dayViewDatePicker;
	
	@FXML
	private AnchorPane dayViewAnchorPane;
	
	@FXML
	private Label weekDateLabel;
	
	@FXML
	private Button weekBackDateButton;
	
	@FXML
	private Button weekForwardDateButton;
	
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
	private Label monthDateLabel;
	
	@FXML
	private Button monthBackDateButton;
	
	@FXML
	private Button monthForwardDateButton;
	
	@FXML
	private DatePicker monthViewDatePicker;
	
	@FXML
	private VBox monthViewVBox;
	
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
    void dayBackDate(ActionEvent backDateEvent) {
    	ApplicationController.setSelectedDate(getSelectedDate().minusDays(1));
    	updateDateLabels(getSelectedDate());
    	updateCalendarViews();
    }
    
    @FXML
    void dayForwardDate(ActionEvent backDateEvent) {
    	ApplicationController.setSelectedDate(getSelectedDate().plusDays(1));
    	updateDateLabels(getSelectedDate());
    	updateCalendarViews();
    }
    
    @FXML
    void weekBackDate(ActionEvent backDateEvent) {
    	ApplicationController.setSelectedDate(getSelectedDate().minusWeeks(1));
    	updateDateLabels(getSelectedDate());
    	updateCalendarViews();
    }
    
    @FXML
    void weekForwardDate(ActionEvent backDateEvent) {
    	ApplicationController.setSelectedDate(getSelectedDate().plusWeeks(1));
    	updateDateLabels(getSelectedDate());
    	updateCalendarViews();
    }
    
    @FXML
    void monthBackDate(ActionEvent backDateEvent) {
    	ApplicationController.setSelectedDate(getSelectedDate().minusMonths(1));
    	updateDateLabels(getSelectedDate());
    	updateCalendarViews();
    }
    
    @FXML
    void monthForwardDate(ActionEvent backDateEvent) {
    	ApplicationController.setSelectedDate(getSelectedDate().plusMonths(1));
    	updateDateLabels(getSelectedDate());
    	updateCalendarViews();
    }
    
    @FXML
    void viewDatePicked(ActionEvent dayViewDatePickedEvent) {
    	System.out.println("dayViewDatePicked: Updating selected date..." + dayViewDatePicker.getValue());
    	
        ZonedDateTime datePickerDate = dayViewDatePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault());
        System.out.println("--> Selected updated to '"+ datePickerDate + "'.");  
        setSelectedDate(datePickerDate);
        
    	updateCalendarViews();
    }
    
    @FXML
    void openAdminPreferences(ActionEvent openAdminPreferencesEvent) {
    	System.out.println("openAdminPreferences: Initializing tests...");
    	
     	getCurrentUser().dumpEvents();
		updateMonthView();
		updateCalendarGUI();
    }
    
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
    	    	
		updateDateLabels(getSelectedDate());
		updateUpcomingEvents();
		updateDayView();
		updateWeekView();
		updateMonthView();
	}
    
    public void updateCalendarViews() {
    	System.out.println(String.format("%nupdateCalendarViews: Updating calendarViews..."));
    	System.out.println(String.format("--> Selected date is '%s'", getSelectedDate().format(dateLabelFormat)));

    	updateDateLabels(getSelectedDate());
    	updateDayView();
    	updateWeekView();
		updateMonthView();
    }
    
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
    
    void updateDayView() {
     	System.out.println(String.format("%nupdateDayView: Updating day view..."));
     	updateDayPane(getSelectedDate(), dayViewAnchorPane, 220);
     }
    
    void updateWeekView() {
     	System.out.println(String.format("%nupdateWeekView: Updating week view..."));
     	
     	//System.out.println(String.format(""));
     	System.out.println(String.format("Selected Date: '%s'", getSelectedDate().format(dateLabelFormat)));
     	
     	// input date for week view must correspond to the appropriate week.
     	// Get the beginning date of the desired week.
     	int dayOfWeek = getConvertedDayOfWeek(getSelectedDate());
     
     	// Calculating the beginning date of a week...
     	ZonedDateTime weekStart = getSelectedDate().minusDays(dayOfWeek - 1);
     	
     	updateDayPane(weekStart, weekViewAnchorPane1, 100);
     	updateDayPane(weekStart.plusDays(1), weekViewAnchorPane2, 100);
     	updateDayPane(weekStart.plusDays(2), weekViewAnchorPane3, 100);
     	updateDayPane(weekStart.plusDays(3), weekViewAnchorPane4, 100);
     	updateDayPane(weekStart.plusDays(4), weekViewAnchorPane5, 100);
     	updateDayPane(weekStart.plusDays(5), weekViewAnchorPane6, 100);
     	updateDayPane(weekStart.plusDays(6), weekViewAnchorPane7, 100);

    }
    
    void updateMonthView() {
     	System.out.println(String.format("%nnupdateMonthView: Updating month view..."));
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
     		// On every Sunday, a new HBox is created to contain each day.
     		if (getConvertedDayOfWeek(dayOfMonth) == 1) {
     			System.out.println("	--> Created a new week HBox.");
     			weekHBox = new HBox();
    			weekHBox.setMinSize(805, 200);
    			weekHBox.setMaxSize(805, 200);    
    			monthViewVBox.getChildren().add(weekHBox);
			}
     		
     		// On every day, a new VBox is created to contain events.
 			System.out.println("	--> Created a new day VBox.");
     		VBox dayVBox = new VBox();
			dayVBox.setMinSize(115, 200);
			
			// On every day, a date label is added to each day of month.
			Label dayOfMonthLabel = new Label(dayOfMonth.format(simpleDateLabelFormat));
			dayOfMonthLabel.setOpacity(0.6);
			dayVBox.setAlignment(Pos.TOP_RIGHT);
			dayVBox.getChildren().add(dayOfMonthLabel);
			
			// Gets events for each particular day. eventCount is used to limit the number of events added to prevent overlap.
			int eventCount = 0;
			int weekOfYear = dayOfMonth.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
	     	int dayOfWeek = getConvertedDayOfWeek(dayOfMonth);	
           	for (Week w: getCurrentUser().getEvents()) {
         		if (w.getWeekNum() == weekOfYear) {
         			// Getting the specified day in the specified week.
         			Day dayTemp = w.getDay(dayOfWeek); 
         	    	for (Event e : dayTemp.getEvents()) {
         	    			if (eventCount <= 7) {
                 				addMonthEventBlock(e, dayVBox);
                 				eventCount ++;
         	    			} else {
         	    				StackPane overflowPane = new StackPane();
         	    				Rectangle overflowRectangle = new Rectangle(115, 14, Color.LIGHTGRAY);
         	    				Label overflowLabel = new Label("...");
         	    				dayVBox.setAlignment(Pos.CENTER);
         	    				overflowPane.getChildren().addAll(overflowRectangle, overflowLabel);
         	    				
         	    				dayVBox.getChildren().add(overflowPane);
         	    				break;
         	    			}
         			}
     	    	}
     		}  
			weekHBox.getChildren().add(dayVBox);
     	}
    }
        
    void updateUpcomingEvents() {
    	// This should NOT be dependent on 'selectedDate' because it is relative to the current date not the selected date.
    	System.out.println(String.format("%nupdateUpcomingEvents: Updating upcomingEventsVbox..."));
    	
     	// Clearing VBox to prevent duplicate event blocks from being added...     	
     	upcomingEventsVBox.getChildren().clear();
    	
    	// Get the 10 CLOSEST events from the CURRENT DATE.
    	int eventCount = 0;
    	int addWeek = 0;
    	ZonedDateTime currentDate = ZonedDateTime.now();
    	
    	int weekOfYear = currentDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
     	int dayOfWeek = getConvertedDayOfWeek(currentDate);	
    	
     	while (addWeek < 4) {
         	System.out.println("--> Attempting to get week '" + (weekOfYear + addWeek) + "' of the year...");
           	for (Week w: getCurrentUser().getEvents()) {
         		if (w.getWeekNum() == weekOfYear + addWeek) {
         			// Getting the specified day in the specified week.
         			Day dayTemp = w.getDay(dayOfWeek); 
         			System.out.println("	--> Attempting to get '" + dayTemp.getEvents().size() + "' events from day '" + dayOfWeek + "' of the '" + weekOfYear + "' week of the year...");
         	    	for (Event e : dayTemp.getEvents()) {
             			if (eventCount < 10) {
             				addMonthEventBlock(e, upcomingEventsVBox);
             				eventCount ++;
             			}
         	    	}
         		}

           	}
           	addWeek ++;
     	}
     	if (eventCount == 0) {
     		System.out.println("There are NO upcoming events in the next four weeks.");
     		Label noUpcomingEventsLabel = new Label("No upcoming events.");
	 		upcomingEventsVBox.setAlignment(Pos.TOP_CENTER);	 		
     		upcomingEventsVBox.getChildren().add(noUpcomingEventsLabel);
     	}
    }
    
    void updateDateLabels(ZonedDateTime inputDate) {
    	System.out.println(String.format("%nupdateDateLabels: Updating date labels..."));

    	String formattedDate = dateLabelFormat.format(inputDate);
    	dayDateLabel.setText(formattedDate);
    	weekDateLabel.setText(formattedDate);
    	monthDateLabel.setText(formattedDate);
    	
    }
    
    void updateDayPane(ZonedDateTime inputDay, AnchorPane inputPane, int inputWidth) {
    	// Gets the specified weekOfYear and dayOfWeek (Requires translating between ChronoField definition of dayOfWeek and TimeUnit definition of dayOfWeek)...
    	// (ChronoField: Monday = 1, Sunday = 7 --> TimeUnit: Sunday = 1, Saturday = 7) 
    	int weekOfYear = inputDay.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
     	int dayOfWeek = getConvertedDayOfWeek(inputDay);	

    	// Clearing VBox to prevent duplicate event blocks from being added.   	
     	inputPane.getChildren().clear();

     	// Getting the specified week of the specified date.
       	for (Week w: getCurrentUser().getEvents()) {
     		if (w.getWeekNum() == weekOfYear) {
     			// Getting the specified day in the specified week.
     			Day dayTemp = w.getDay(dayOfWeek); 
     	    	for (Event e : dayTemp.getEvents()) {
     	        	addDayEventBlock(e, inputPane, inputWidth);
     	    	}
     		}
       	}
    }
    
	static void addDayEventBlock(Event inputEvent, AnchorPane inputPane, int inputWidth) {
		System.out.println(String.format("		--> addDayEventBlock: Creating event block for event '%s'...", inputEvent.getName()));
    	Insets eventBlockInsets = new Insets(0, 5, 0, 5);
		// Each hour in every pane is 60px. Thus, each minute is 1px.			
		if (inputEvent instanceof TimedEvent) {
			// Start POSITION should be equal to the number of minutes from the beginning of the day.		
			double startPos = inputEvent.getStart().get(ChronoField.MINUTE_OF_DAY) + 7;
			double endPos = inputEvent.getEnd().get(ChronoField.MINUTE_OF_DAY) + 7;
			
			// Height should be equal to the difference between startPos and endPos
			double height = endPos - startPos;
						
			// Creating rectangle and label components of the event block...
	 		Rectangle eventBlock = new Rectangle(inputWidth, height, inputEvent.getColour().deriveColor(1.0, 1.0, 1.0, 0.5));
	 		Label eventBlockLabel = new Label(inputEvent.getName());
	 		
	 		eventBlock.setBlendMode(BlendMode.COLOR_BURN);
	 		
	 		// Setting label font and colour...
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

	static void addUpcomingEventBlock(Event inputEvent, VBox inputPane) {
		System.out.println(String.format("		--> addUpcomingEventBlock: Creating event block for event '%s'...", inputEvent.getName()));
		
    	Insets eventBlockLabelInsets = new Insets(0, 5, 0, 5);
    	Insets eventBlockInsets = new Insets(2, 5, 2, 5);
    	
    	Rectangle eventBlock = new Rectangle(190, 60, inputEvent.getColour().deriveColor(1.0, 1.0, 1.0, 0.5));
 		Label eventBlockLabel = new Label(inputEvent.getName());
 		
 		eventBlock.setBlendMode(BlendMode.COLOR_BURN);
 		
 		eventBlockLabel.setStyle("-fx-font-weight: Bold");
 		eventBlockLabel.setMaxWidth(190);
 		eventBlockLabel.setWrapText(true);
 		eventBlockLabel.setAlignment(Pos.TOP_CENTER);
 		eventBlockLabel.setPadding(eventBlockLabelInsets);
 		
 		if (inputEvent.getColour().getBrightness() > 0.6) {
	 		eventBlockLabel.setTextFill(Color.BLACK);
 		} else {
	 		eventBlockLabel.setTextFill(Color.WHITE);
 		}
 		
 		StackPane eventBlockPane = new StackPane(eventBlock, eventBlockLabel);
 		StackPane.setAlignment(eventBlockLabel, Pos.TOP_CENTER);
 		
 		inputPane.setMargin(eventBlockPane, eventBlockInsets);
 		inputPane.getChildren().add(eventBlockPane);
	}

	static void addMonthEventBlock(Event inputEvent, VBox inputPane) {
		System.out.println(String.format("		--> addMonthEventBlock: Creating event block for event '%s'...", inputEvent.getName()));
		
    	Insets eventBlockLabelInsets = new Insets(0, 2, 0, 2);
    	Insets eventBlockInsets = new Insets(1, 2, 1, 2);
    	
    	Rectangle eventBlock = new Rectangle(115, 14, inputEvent.getColour().deriveColor(1.0, 1.0, 1.0, 0.5));
 		Label eventBlockLabel = new Label(inputEvent.getName());
 		
 		eventBlock.setBlendMode(BlendMode.COLOR_BURN);
 		
 		eventBlockLabel.setMaxWidth(115);
 		eventBlockLabel.setWrapText(true);
 		eventBlockLabel.setAlignment(Pos.TOP_CENTER);
 		eventBlockLabel.setPadding(eventBlockLabelInsets);
 		
 		if (inputEvent.getColour().getBrightness() > 0.6) {
	 		eventBlockLabel.setTextFill(Color.BLACK);
 		} else {
	 		eventBlockLabel.setTextFill(Color.WHITE);
 		}
 		
 		StackPane eventBlockPane = new StackPane(eventBlock, eventBlockLabel);
 		StackPane.setAlignment(eventBlockLabel, Pos.TOP_CENTER);
 		
 		inputPane.setMargin(eventBlockPane, eventBlockInsets);
 		inputPane.getChildren().add(eventBlockPane);
	}
}