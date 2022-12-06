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

    @FXML
    public void deleteEvent(ActionEvent deleteEvent) {
    	eventStartDate.setBorder(null);
		eventStartHour.setBorder(null);
		eventStartMinute.setBorder(null);
		eventEndDate.setBorder(null);
		eventEndHour.setBorder(null);
		eventEndMinute.setBorder(null);
    	errorLabel.setVisible(false);
    	
    	getCurrentUser().removeEvent(viewedEvent);
    	if (!directClick)
    		initializeEventViewerView();
    	User.serializeUser(getCurrentUser());
    	getCalendarController().updateCalendarGUI();
    	getManagerStage().close();
		
    }

    @FXML
    public void saveEvent(ActionEvent replaceEvent) throws NullEventEndPointException, EventOutsideTimeUnitException {
    	eventStartDate.setBorder(null);
		eventStartHour.setBorder(null);
		eventStartMinute.setBorder(null);
		eventEndDate.setBorder(null);
		eventEndHour.setBorder(null);
		eventEndMinute.setBorder(null);
    	errorLabel.setVisible(false);
    	boolean error = false;
    	getCurrentUser().removeEvent(viewedEvent);
    	
    	if (viewedEvent instanceof InstantEvent) {
    		LocalDateTime time = eventStartDate.getValue().atStartOfDay().plusHours(eventStartHour.getValue()).plusMinutes(eventStartMinute.getValue());

    		String name = eventName.getText();
    		
    		Color colour = eventColour.getValue();

    		InstantEvent newEvent = new InstantEvent(time, name, colour);
    		getCurrentUser().addEvent(newEvent);
    		
    	} else if (viewedEvent instanceof TimedEvent) {
    		LocalDateTime start = eventStartDate.getValue().atStartOfDay().plusHours(eventStartHour.getValue()).plusMinutes(eventStartMinute.getValue());
    		LocalDateTime end = eventEndDate.getValue().atStartOfDay().plusHours(eventEndHour.getValue()).plusMinutes(eventEndMinute.getValue());
    		if (end.minusDays(1).equals(start))
    			end = end.minusNanos(1000000000);
    	
    		String name = eventName.getText();
    		
    		Color colour = eventColour.getValue();
    		  		
    		if(start.isAfter(end)) {
    			error = true;
				if(eventStartDate.getValue().isAfter(eventEndDate.getValue())) {
					eventStartDate.setBorder(errorBorder);
					eventEndDate.setBorder(errorBorder);
				 }else if (eventStartDate.getValue().equals(eventEndDate.getValue())) {
					 System.out.println(eventStartHour.getValue() + " " + eventEndHour.getValue());
					if(eventStartHour.getValue() > eventEndHour.getValue()) {
						eventStartHour.setBorder(errorBorder);
						eventEndHour.setBorder(errorBorder);
						eventStartMinute.setBorder(errorBorder);
						eventEndMinute.setBorder(errorBorder);
					} else if (eventStartMinute.getValue() > eventEndMinute.getValue()) {
						eventStartMinute.setBorder(errorBorder);
						eventEndMinute.setBorder(errorBorder);
					}
				 }
				errorLabel.setVisible(true);	
			}
    		if (!error) {
    			TimedEvent newEvent = new TimedEvent(start, end, name, colour);
    			getCurrentUser().addEvent(newEvent);
    		}
    	}
    	if (!error) {
	    	if (!directClick)
	    		initializeEventViewerView();
	    	User.serializeUser(getCurrentUser());
	    	getCalendarController().updateCalendarGUI();
	    	getManagerStage().close();
    	}
    }
    
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
    	
    	if (selectedEvent instanceof InstantEvent) {
    		if (rootVBox.getChildren().contains(endVBox))
    			rootVBox.getChildren().remove(endVBox);
    		startLabel.setText("Time");
    		
    	}
    	else if (selectedEvent instanceof TimedEvent) {
    		if (!rootVBox.getChildren().contains(endVBox))
    			rootVBox.getChildren().add(2, endVBox);
    		startLabel.setText("Start");
    		eventEndDate.setValue(selectedEvent.getEnd().toLocalDate());
        	eventEndHour.increment(selectedEvent.getEnd().getHour());
        	eventEndMinute.increment(selectedEvent.getEnd().getMinute());
    	}
    	eventStartDate.setValue(selectedEvent.getStart().toLocalDate());
    	eventStartHour.increment(selectedEvent.getStart().getHour());
    	eventStartMinute.increment(selectedEvent.getStart().getMinute());
    	
    	eventName.setText(selectedEvent.getName());
    	
    	eventColour.setValue(selectedEvent.getColour());

    }

}
