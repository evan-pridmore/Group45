package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class EventManagementController {
	
	private static Stage applicationStage; 
	private static User currentUser;
	
	@FXML
	private VBox allEventPanel;

	public static void initializeEventManagerView(Stage calendarViewStage, User loginUser) throws FileNotFoundException, IOException {
			currentUser = loginUser; 
			applicationStage = calendarViewStage;
			Stage eventsStage = new Stage();
			eventsStage.initModality(Modality.APPLICATION_MODAL);
			eventsStage.setTitle(currentUser.getUsername() + "'s Events");
			
			Parent root = new FXMLLoader().load(new FileInputStream("src/application/FXML/EventsViewerView.fxml"));
			Scene scene = new Scene(root);
			
			eventsStage.setScene(scene);
			eventsStage.show();
			
			System.out.println("Opened " + currentUser.getUsername() + "'s event manager!");
	}
	
	public void addEvent(ActionEvent addEvent) throws FileNotFoundException, IOException {
		Stage manageStage = new Stage();
		manageStage.initModality(Modality.APPLICATION_MODAL);
		manageStage.setTitle("New Event");
		
		Parent eventTypeSelector = new FXMLLoader().load(new FileInputStream("src/application/FXML/EventMakerView.fxml"));
		Scene test = new Scene(eventTypeSelector);
		manageStage.setScene(test);
		manageStage.show();
	}
}