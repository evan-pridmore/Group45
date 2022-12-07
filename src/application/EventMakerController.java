package application;

import java.time.LocalDateTime;

import application.Exceptions.EventOutsideTimeUnitException;
import application.Exceptions.NullEventEndPointException;
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
import javafx.scene.paint.Color;

/**A controller that manages {@link EventMakerView.fxml} and is associated GUI components (e.g., buttons, labels, textfields, etc.)
 * 
 * Extends {@link ApplicationController} which provides a range of static variables.
 * 
 * This should ONLY manage the associated GUI, and should NOT initialize any other stages, scenes, windows, views, etc.
 * (to switch windows or create new stages, add an initialize method in {@link ApplicationController}
 * 
 */
public class EventMakerController extends ApplicationController {	
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
	private DatePicker deadlineTimeDate;
	@FXML
	private Spinner<Integer> deadlineTimeHour;
	@FXML
	private Spinner<Integer> deadlineTimeMinute;
	@FXML
	private TextField eventName;
	@FXML
	private TextField deadlineName;
	@FXML
	private ColorPicker eventColour;
	@FXML
	private ColorPicker deadlineColour;
	@FXML
	private Label eventErrorLabel;
	@FXML
	private Label deadlineErrorLabel;
	
	private Border errorBorder = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID ,new CornerRadii(3), BorderWidths.DEFAULT),new BorderStroke(Color.RED, BorderStrokeStyle.SOLID ,new CornerRadii(3), BorderWidths.DEFAULT),new BorderStroke(Color.RED, BorderStrokeStyle.SOLID ,new CornerRadii(3), BorderWidths.DEFAULT),new BorderStroke(Color.RED, BorderStrokeStyle.SOLID ,new CornerRadii(3), BorderWidths.DEFAULT));
	
	
	/**
	 * Method to creates and adds a {@link TimedEvent} to the current {@link User} from the information given by the user in the {@link EventMakerView.fxml}.
	 * @param event The {@link ActionEvent} of the user pressing the create event {@link Button} in the event maker.
	 * @throws NullEventEndPointException if the start or end of {@link TimedEvent} is given as null, should be unable to happen in practice.
	 * @throws EventOutsideTimeUnitException if the created {@link TimedEvent} is added into a week it does not belong in. Should be unable to occur in practice.
	 */
	@FXML
	private void addTimedEvent(ActionEvent event) throws NullEventEndPointException, EventOutsideTimeUnitException {
		//Reset.
		boolean error = false;
		eventStartDate.setBorder(null);
		eventStartHour.setBorder(null);
		eventStartMinute.setBorder(null);
		eventEndDate.setBorder(null);
		eventEndHour.setBorder(null);
		eventEndMinute.setBorder(null);
		eventErrorLabel.setText("Highlighted date(s) are not of valid format.");
		eventErrorLabel.setVisible(false);
		
		//Prevent the NullEventEndPointException from ever being triggered.
		if (eventStartDate.getValue() == null) {
			eventStartDate.setBorder(errorBorder);
			error = true;
		} if (eventEndDate.getValue() == null) {
			eventEndDate.setBorder(errorBorder);
			error = true;
		}
		
		String name = eventName.getText();

		Color colour = eventColour.getValue();

		
		if (!error) {
			//If neither endpoint date is null and fields into start and end LocalDateTimes.
			LocalDateTime start = eventStartDate.getValue().atStartOfDay().plusHours(eventStartHour.getValue()).plusMinutes(eventStartMinute.getValue());
			LocalDateTime end = eventEndDate.getValue().atStartOfDay().plusHours(eventEndHour.getValue()).plusMinutes(eventEndMinute.getValue());
			
			//Check if the start is before the end and inform the user of the fields needing adjustment.
			if(start.isAfter(end)) {
				//If the start date is before the end date, highlight date pickers.
				if(eventStartDate.getValue().isAfter(eventEndDate.getValue())) {
					eventStartDate.setBorder(errorBorder);
					eventEndDate.setBorder(errorBorder);
				 }else if (eventStartDate.getValue().equals(eventEndDate.getValue())) {
					 //If the date is the same, highlight the hour and minute inputs.
					if(eventStartHour.getValue() > eventEndHour.getValue()) {
						eventStartHour.setBorder(errorBorder);
						eventEndHour.setBorder(errorBorder);
						eventStartMinute.setBorder(errorBorder);
						eventEndMinute.setBorder(errorBorder);
					  //If the date, and hours are the same, highlight only the minute fields.
					} else if (eventStartMinute.getValue() > eventEndMinute.getValue()) {
						eventStartMinute.setBorder(errorBorder);
						eventEndMinute.setBorder(errorBorder);
					}
				 }
				//Inform the user of the issue.
				eventErrorLabel.setText("Event start cannot be before event end.");
				eventErrorLabel.setVisible(true);
			  
				//If the start and end are the same, highlight all fields.
			} else if(start.equals(end)) {
				eventStartDate.setBorder(errorBorder);
				eventEndDate.setBorder(errorBorder);
				eventStartHour.setBorder(errorBorder);
				eventEndHour.setBorder(errorBorder);
				eventStartMinute.setBorder(errorBorder);
				eventEndMinute.setBorder(errorBorder);
				
				//Suggest the user create a deadline.
				eventErrorLabel.setText("Start and end at the same time, consider a deadline?");
				eventErrorLabel.setVisible(true);
				
			} else {
				//If nothing is wrong create the new event and add it to the user, save the user data, and update the GUI.
				TimedEvent newEvent = new TimedEvent(start, end, name, colour);
				getCurrentUser().addEvent(newEvent);
				
				System.out.println("addTimedEvent: Event created (" + newEvent.toString() + ")");
				getMakerStage().close();
				User.serializeUser(getCurrentUser());
		    	getCalendarController().updateCalendarGUI();
			}
		} else {
			eventErrorLabel.setVisible(true);
		}
	}
	
	/**
	 * Method to creates and adds a {@link InstantEvent} to the current {@link User} from the information given by the user in the {@link EventMakerView.fxml}.
	 * @param event The {@link ActionEvent} of the user pressing the create deadline {@link Button} in the event maker.
	 * @throws NullEventEndPointException if the start of {@link InstantEvent} is given as null, should be unable to happen in practice.
	 * @throws EventOutsideTimeUnitException if the created {@link InstantEvent} is added into a week it does not belong in. Should be unable to occur in practice.
	 */
	@FXML
	private void addInstantEvent(ActionEvent event) throws NullEventEndPointException, EventOutsideTimeUnitException {
		//Reset
		boolean error = false;
		deadlineTimeDate.setBorder(null);
		
		//Prevent the NullEventEndPointException from being thrown.
		if (deadlineTimeDate.getValue() == null) {
			deadlineTimeDate.setBorder(errorBorder);
			error = true;
		}
			

		String name = deadlineName.getText();
		
		Color colour = deadlineColour.getValue();
	
		
		
		if (!error) {
			//If nothing is wrong create the new event and add it to the user, save the user data, and update the GUI.
			LocalDateTime time = deadlineTimeDate.getValue().atStartOfDay().plusHours(deadlineTimeHour.getValue()).plusMinutes(deadlineTimeMinute.getValue());
			InstantEvent newEvent = new InstantEvent(time, name, colour);
			getCurrentUser().addEvent(newEvent);
			
			System.out.println("addInstantEvent: Event created (" + newEvent.toString() + ")");
			getMakerStage().close();
			User.serializeUser(getCurrentUser());
	    	getCalendarController().updateCalendarGUI();
		} else {
			deadlineErrorLabel.setVisible(true);
		}
	}
	
	/**
	 * Sets the {@link ColourPicker} of the event maker to a random colour.
	 */
	public void setRandomColour() {
		Color randomColour = new Color(Math.random(),Math.random(),Math.random(), Math.random());
		eventColour.setValue(randomColour);
		deadlineColour.setValue(randomColour);
	} 

}