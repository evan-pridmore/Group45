package application;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import application.Exceptions.EventOutsideTimeUnitException;
import application.Exceptions.NullEventEndPointException;
import application.TimeUnits.Day;
import application.TimeUnits.TimedEvent;
import application.TimeUnits.Week;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws NullPointerException, NullEventEndPointException, EventOutsideTimeUnitException {
		System.out.println("Main: Starting application...");
		
		primaryStage.setResizable(false);
		
		ApplicationController.setAppStage(primaryStage);
		ApplicationController.setSelectedDate(ZonedDateTime.now());
		ApplicationController.initializeLoginView();
		
		
		Week testWeek = new Week(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()));
		ZonedDateTime testDate = ZonedDateTime.now().minusDays(1);
		TimedEvent testEvent = new TimedEvent(testDate, testDate.plusHours(1).plusDays(1), "TestEvent", Color.RED);
		testWeek.addEvent(testEvent);
		for (Day day : testWeek.getDays()) {
			System.out.println(day.getEvents());
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
