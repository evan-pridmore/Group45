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
 * @author evan-pridmore
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

	@FXML
	private void addTimedEvent(ActionEvent event) throws NullEventEndPointException, EventOutsideTimeUnitException {
		boolean error = false;
		eventStartDate.setBorder(null);
		eventEndDate.setBorder(null);
		
		
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
			LocalDateTime start = eventStartDate.getValue().atStartOfDay().plusHours(eventStartHour.getValue()).plusMinutes(eventStartMinute.getValue());
			LocalDateTime end = eventEndDate.getValue().atStartOfDay().plusHours(eventEndHour.getValue()).plusMinutes(eventEndMinute.getValue());
			if (end.minusDays(1).equals(start))
				end = end.minusNanos(1000000000);
			TimedEvent newEvent = new TimedEvent(start, end, name, colour);
			getCurrentUser().addEvent(newEvent);
			
			getMakerStage().close();
			User.serializeUser(getCurrentUser());
		} else {
			eventErrorLabel.setVisible(true);
		}
	}
	
	@FXML
	private void addInstantEvent(ActionEvent event) throws NullEventEndPointException, EventOutsideTimeUnitException {
		boolean error = false;
		deadlineTimeDate.setBorder(null);

		if (deadlineTimeDate.getValue() == null) {
			deadlineTimeDate.setBorder(errorBorder);
			error = true;
		}
			

		String name = deadlineName.getText();
		
		Color colour = deadlineColour.getValue();
		
		if (!error) {
			LocalDateTime time = deadlineTimeDate.getValue().atStartOfDay().plusHours(deadlineTimeHour.getValue()).plusMinutes(deadlineTimeMinute.getValue());
			InstantEvent newEvent = new InstantEvent(time, name, colour);
			getCurrentUser().addEvent(newEvent);
			
			getMakerStage().close();
			User.serializeUser(getCurrentUser());
		} else {
			deadlineErrorLabel.setVisible(true);
		}
	}
	
	public void setRandomColour() {
		Color randomColour = new Color(Math.random(),Math.random(),Math.random(), Math.random());
		eventColour.setValue(randomColour);
		deadlineColour.setValue(randomColour);
	} 

}