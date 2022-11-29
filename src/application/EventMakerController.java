package application;

import java.time.LocalDateTime;

import application.Exceptions.EventOutsideTimeUnitException;
import application.Exceptions.NullEventEndPointException;
import application.TimeUnits.InstantEvent;
import application.TimeUnits.TimedEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**A controller that manages {@link EventMakerView.fxml} and is associated GUI components (e.g., buttons, labels, textfields, etc.)
 * 
 * Extends {@link ApplicationController} which provides a range of static variables.
 * 
 * This should ONLY manage the associated GUI, and should NOT initialize any other stages, scenes, windows, views, etc.
 * (to switch windows or create new stages, add an initialize method in {@link ApplicationController}
 * 
 * @author evan-pridmore
 */
public class EventMakerController extends ApplicationController {	
	@FXML
	private DatePicker eventStartDate;
	@FXML
	private ChoiceBox<Integer> eventStartHour;
	@FXML
	private ChoiceBox<Integer> eventStartMinute;
	@FXML
	private DatePicker eventEndDate;
	@FXML
	private ChoiceBox<Integer> eventEndHour;
	@FXML
	private ChoiceBox<Integer> eventEndMinute;
	@FXML
	private DatePicker deadlineTimeDate;
	@FXML
	private ChoiceBox<Integer> deadlineTimeHour;
	@FXML
	private ChoiceBox<Integer> deadlineTimeMinute;
	@FXML
	private TextField eventName;
	@FXML
	private TextField deadlineName;
	@FXML
	private ColorPicker eventColour;
	@FXML
	private ColorPicker deadlineColour;

	@FXML
	private void addTimedEvent(ActionEvent event) throws NullEventEndPointException, EventOutsideTimeUnitException {
		LocalDateTime start = eventStartDate.getValue().atStartOfDay().plusHours(eventStartHour.getValue()).plusMinutes(eventStartMinute.getValue());
		
		LocalDateTime end = eventEndDate.getValue().atStartOfDay().plusHours(eventEndHour.getValue()).plusMinutes(eventEndMinute.getValue());
	
		String name = eventName.getText();
		
		Color colour = eventColour.getValue();
		
		TimedEvent newEvent = new TimedEvent(start, end, name, colour);
		getCurrentUser().addEvent(newEvent);
		
		getEventsStage().close();
		User.serializeUser(getCurrentUser());
		
	}
	
	@FXML
	private void addInstantEvent(ActionEvent event) throws NullEventEndPointException, EventOutsideTimeUnitException {
		LocalDateTime time = deadlineTimeDate.getValue().atStartOfDay().plusHours(deadlineTimeHour.getValue()).plusMinutes(deadlineTimeMinute.getValue());

		String name = deadlineName.getText();
		
		Color colour = deadlineColour.getValue();
		
		InstantEvent newEvent = new InstantEvent(time, name, colour);
		getCurrentUser().addEvent(newEvent);
		
		System.out.println(getCurrentUser().getEvents().size());
		getEventsStage().close();
		User.serializeUser(getCurrentUser());
	}
	
	void closeEventMaker() {
		System.out.println("Test");
	}
	
}
