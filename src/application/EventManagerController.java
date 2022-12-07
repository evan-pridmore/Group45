package application;

import java.time.LocalDateTime;

import application.Exceptions.EventOutsideTimeUnitException;
import application.Exceptions.NullEventEndPointException;
import application.TimeUnits.Event;
import application.TimeUnits.InstantEvent;
import application.TimeUnits.TimedEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


/**A controller that manages {@link EventManagerView.fxml} and is associated GUI components (e.g., buttons, labels, textfields, etc.)
 * 
 * Extends {@link ApplicationController} which provides a range of static variables.
 * 
 * This should ONLY manage the associated GUI, and should NOT initialize any other stages, scenes, windows, views, etc.
 * (to switch windows or create new stages, add an initialize method in {@link ApplicationController}
 * 
 */
public class EventManagerController extends ApplicationController {
	
	@FXML
	private VBox rootVBox;
    @FXML
    private VBox endVBox;
    @FXML
    private ColorPicker eventColour;
    @FXML
    private DatePicker eventStartDate;
    @FXML
    private Spinner<Integer> eventStartHour;
    @FXML
    private Spinner<Integer> eventStartMinute;
    @FXML
    private DatePicker eventEndDate;
    @FXML
    private Spinner<Integer> eventEndHour;
    @FXML
    private Spinner<Integer> eventEndMinute;
    @FXML
    private TextField eventName;
    @FXML
    private Label startLabel;
    @FXML
    private Label errorLabel;
    
    private Event viewedEvent;
    
    private boolean directClick;
    
	private Border errorBorder = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID ,new CornerRadii(3), BorderWidths.DEFAULT),new BorderStroke(Color.RED, BorderStrokeStyle.SOLID ,new CornerRadii(3), BorderWidths.DEFAULT),new BorderStroke(Color.RED, BorderStrokeStyle.SOLID ,new CornerRadii(3), BorderWidths.DEFAULT),new BorderStroke(Color.RED, BorderStrokeStyle.SOLID ,new CornerRadii(3), BorderWidths.DEFAULT));
	
	/**
	 * Deletes the provided {@link Event} from the currently logged in {@link User} when they press the DELETE {@link Button}.
	 * @param deleteEvent the {@link ActionEvent} of the {@link User} pressing the delete {@link Button}.
	 */
    @FXML
    private void deleteEvent(ActionEvent deleteEvent) {
    	//Reset scene if needed.
    	eventStartDate.setBorder(null);
		eventStartHour.setBorder(null);
		eventStartMinute.setBorder(null);
		eventEndDate.setBorder(null);
		eventEndHour.setBorder(null);
		eventEndMinute.setBorder(null);
    	errorLabel.setVisible(false);
    	
    	getCurrentUser().removeEvent(viewedEvent);
    	
    	//If the event was accessed through the event viewer, update it to reflect the change.
    	if (!directClick)
    		initializeEventViewerView();
    	
    	//Save the user info and update the GUI.
    	User.serializeUser(getCurrentUser());
    	getCalendarController().updateCalendarGUI();
    	getManagerStage().close();
		
    }
    /**
     * Replaces the old {@link Event} from the {@link User} with a new one with the information provided.
     * @param replaceEvent
     * @throws NullEventEndPointException
     * @throws EventOutsideTimeUnitException
     */
    @FXML
    private void saveEvent(ActionEvent replaceEvent) throws NullEventEndPointException, EventOutsideTimeUnitException {
    	//Reset the scene if needed.
    	eventStartDate.setBorder(null);
		eventStartHour.setBorder(null);
		eventStartMinute.setBorder(null);
		eventEndDate.setBorder(null);
		eventEndHour.setBorder(null);
		eventEndMinute.setBorder(null);
    	errorLabel.setVisible(false);
    	boolean error = false;
    	
    	getCurrentUser().removeEvent(viewedEvent);
    	
    	//Create an new event of the same type as the replaced one.
    	//DatePicker cannot have value set back to null, so any improper format will set to the old value, so less checking required.
    	if (viewedEvent instanceof InstantEvent) {
    		LocalDateTime time = eventStartDate.getValue().atStartOfDay().plusHours(eventStartHour.getValue()).plusMinutes(eventStartMinute.getValue());

    		String name = eventName.getText();
    		
    		Color colour = eventColour.getValue();

    		InstantEvent newEvent = new InstantEvent(time, name, colour);
    		getCurrentUser().addEvent(newEvent);
    		
    	} else if (viewedEvent instanceof TimedEvent) {
    		LocalDateTime start = eventStartDate.getValue().atStartOfDay().plusHours(eventStartHour.getValue()).plusMinutes(eventStartMinute.getValue());
    		LocalDateTime end = eventEndDate.getValue().atStartOfDay().plusHours(eventEndHour.getValue()).plusMinutes(eventEndMinute.getValue());
    	
    		String name = eventName.getText();
    		
    		Color colour = eventColour.getValue();
    		
    		//Check if the start time and end times are otherwise invalid.
    		if(start.isAfter(end) || start.equals(end)) {
    			error = true;
    			//If the start date is before the end date, highlight date pickers.
				if(eventStartDate.getValue().isAfter(eventEndDate.getValue())) {
					eventStartDate.setBorder(errorBorder);
					eventEndDate.setBorder(errorBorder);
				 }else if (eventStartDate.getValue().equals(eventEndDate.getValue())) {
					 //If the date is the same, highlight the hour and minute inputs.
					 System.out.println(eventStartHour.getValue() + " " + eventEndHour.getValue());
					if(eventStartHour.getValue() > eventEndHour.getValue()) {
						eventStartHour.setBorder(errorBorder);
						eventEndHour.setBorder(errorBorder);
						eventStartMinute.setBorder(errorBorder);
						eventEndMinute.setBorder(errorBorder);
					  //If the date, and hours are the same, highlight only the minute fields.
					} else if (eventStartMinute.getValue() > eventEndMinute.getValue()) {
						eventStartMinute.setBorder(errorBorder);
						eventEndMinute.setBorder(errorBorder);
					//If the start and end are the same, highlight all fields.
					} else { 
						eventStartDate.setBorder(errorBorder);
						eventEndDate.setBorder(errorBorder);
						eventStartHour.setBorder(errorBorder);
						eventEndHour.setBorder(errorBorder);
						eventStartMinute.setBorder(errorBorder);
						eventEndMinute.setBorder(errorBorder);
					}
				 }
				errorLabel.setVisible(true);	
			}
    		//If there are no issues make new event.
    		if (!error) {
    			TimedEvent newEvent = new TimedEvent(start, end, name, colour);
    			getCurrentUser().addEvent(newEvent);
    		}
    	}
    	//If there are no issues add the event to the user, the their data and update the GUI.
    	if (!error) {
	    	if (!directClick)
	    		initializeEventViewerView();
	    	User.serializeUser(getCurrentUser());
	    	getCalendarController().updateCalendarGUI();
	    	getManagerStage().close();
    	}
    }
    
    /**
     * Sets the {@link Event} that is being viewed the the manager.
     * @param selectedEvent The {@link Event} being viewed.
     * @param direct True if the user has clicked from the event details menu, false if they used the event viewer.
     */
    public void setEvent(Event selectedEvent, boolean direct) {
    	//Set the Spinners back to zero.
    	eventStartHour.decrement(24);
    	eventStartMinute.decrement(60);
    	eventEndHour.decrement(24);
    	eventEndMinute.decrement(60);
    	errorLabel.setVisible(false);
    	
    	directClick = direct;
    	
    	viewedEvent = selectedEvent;
    	System.out.println(selectedEvent.getStart());
    	
    	//If viewing an InstantEvent, remove the end time options from view.
    	if (selectedEvent instanceof InstantEvent) {
    		if (rootVBox.getChildren().contains(endVBox))
    			rootVBox.getChildren().remove(endVBox);
    		startLabel.setText("Time");
    		
    	}
    	//If viewing an TimedEvent, ensure that the end time options are visible..
    	else if (selectedEvent instanceof TimedEvent) {
    		if (!rootVBox.getChildren().contains(endVBox))
    			rootVBox.getChildren().add(2, endVBox);
    		startLabel.setText("Start");
    		//Load end data from event.
    		eventEndDate.setValue(selectedEvent.getEnd().toLocalDate());
        	eventEndHour.increment(selectedEvent.getEnd().getHour());
        	eventEndMinute.increment(selectedEvent.getEnd().getMinute());
    	}
    	
    	//Load data from event.
    	eventStartDate.setValue(selectedEvent.getStart().toLocalDate());
    	eventStartHour.increment(selectedEvent.getStart().getHour());
    	eventStartMinute.increment(selectedEvent.getStart().getMinute());
    	
    	eventName.setText(selectedEvent.getName());
    	
    	eventColour.setValue(selectedEvent.getColour());

    }

}
