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
    private DatePicker eventEndDate;
    @FXML
    private Spinner<Integer> eventEndHour;
    @FXML
    private Spinner<Integer> eventEndMinute;
    @FXML
    private TextField eventName;
    @FXML
    private DatePicker eventStartDate;
    @FXML
    private Spinner<Integer> eventStartHour;
    @FXML
    private Spinner<Integer> eventStartMinute;
    @FXML
    private Label startLabel;
    
    private Event viewedEvent;
    
    @FXML
    public void deleteEvent(ActionEvent deleteEvent) {
    	getCurrentUser().removeEvent(viewedEvent);
    	initializeEventViewerView();
    	User.serializeUser(getCurrentUser());
    	getManagerStage().close();
		
    }

    @FXML
    public void saveEvent(ActionEvent replaceEvent) throws NullEventEndPointException, EventOutsideTimeUnitException {
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
    	
    		String name = eventName.getText();
    		
    		Color colour = eventColour.getValue();
    		
    		TimedEvent newEvent = new TimedEvent(start, end, name, colour);
    		getCurrentUser().addEvent(newEvent);

    	}
    	initializeEventViewerView();
    	User.serializeUser(getCurrentUser());
    	getManagerStage().close();
    }
    
    public void setEvent(Event selectedEvent) {
    	viewedEvent = selectedEvent;
    	
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
