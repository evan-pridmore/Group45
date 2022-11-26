package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import application.Exceptions.EventOutsideTimeUnitException;
import application.Exceptions.NullEventEndPointException;
import application.TimeUnits.InstantEvent;
import application.TimeUnits.TimedEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EventMakerController {
	private User currentUser;
	
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
	
	public void initalizeEventMakerController(User loginUser, Parent sceneView) throws FileNotFoundException, IOException {
		currentUser = loginUser;
		Stage manageStage = new Stage();
		manageStage.initModality(Modality.APPLICATION_MODAL);
		manageStage.setTitle("New Event");
		
		Scene scene = new Scene(sceneView);
		manageStage.setScene(scene);
		manageStage.show();
	}

	@FXML
	private void addTimedEvent(ActionEvent event) throws NullEventEndPointException, EventOutsideTimeUnitException {
		LocalDateTime startAsLocalDateTime = eventStartDate.getValue().atStartOfDay();
		startAsLocalDateTime = startAsLocalDateTime.plusHours(eventStartHour.getValue());
		startAsLocalDateTime = startAsLocalDateTime.plusMinutes(eventStartMinute.getValue());
		Date start = Date.from(startAsLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
		
		LocalDateTime endAsLocalDateTime = eventEndDate.getValue().atStartOfDay();
		endAsLocalDateTime = endAsLocalDateTime.plusHours(eventEndHour.getValue());
		Date end = Date.from(endAsLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
		
		String name = eventName.getText();
		
		Color colour = eventColour.getValue();
		
		TimedEvent newEvent = new TimedEvent(start, end, name, colour);
		currentUser.addEvent(newEvent);
	}
	
	@FXML
	private void addInstantEvent(ActionEvent event) throws NullEventEndPointException, EventOutsideTimeUnitException {
		LocalDateTime timeAsLocalDateTime = deadlineTimeDate.getValue().atStartOfDay();
		timeAsLocalDateTime = timeAsLocalDateTime.plusHours(deadlineTimeHour.getValue());
		timeAsLocalDateTime = timeAsLocalDateTime.plusMinutes(deadlineTimeMinute.getValue());
		Date time = Date.from(timeAsLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
		
		String name = deadlineName.getText();
		
		Color colour = deadlineColour.getValue();
		
		InstantEvent newEvent = new InstantEvent(time, name, colour);
		currentUser.addEvent(newEvent);
	}
	
}
