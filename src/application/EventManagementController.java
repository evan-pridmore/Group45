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
	
	private User currentUser;
	
	@FXML
	private VBox allEventPanel;

	public void initializeEventManagerView(Stage manageStage, User loginUser) throws FileNotFoundException, IOException {
			currentUser = loginUser; 
			manageStage.show();
			
			System.out.println("Opened " + currentUser.getUsername() + "'s event manager!");
	}
	
	public void addEvent(ActionEvent addEvent) throws FileNotFoundException, IOException {
		FXMLLoader loader = new FXMLLoader();
		Parent eventMaker = loader.load(new FileInputStream("src/application/FXML/EventMakerView.fxml"));
		EventMakerController controller = (EventMakerController) loader.getController();
		controller.initalizeEventMakerController(currentUser, eventMaker);
	}
}
