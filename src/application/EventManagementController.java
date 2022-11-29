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

public class EventManagementController extends ApplicationController {
	
	@FXML
	private VBox allEventPanel;

	public static void initializeEventManagerView() throws FileNotFoundException, IOException {
			Stage eventsStage = new Stage();
			eventsStage.initModality(Modality.APPLICATION_MODAL);
			eventsStage.setTitle(ApplicationController.getCurrentUser().getUsername() + "'s Events");
			
			Parent root = new FXMLLoader().load(new FileInputStream("src/application/FXML/EventsViewerView.fxml"));
			Scene scene = new Scene(root);
			
			eventsStage.setScene(scene);
			eventsStage.show();
      
/* TIme_Unit_Rewrite BEGIN
	public void initializeEventManagerView(Stage manageStage, User loginUser) throws FileNotFoundException, IOException {
			currentUser = loginUser; 
			manageStage.show();
*/ TIme_Unit_Rewrite END
			
			System.out.println("Opened " + ApplicationController.getCurrentUser().getUsername() + "'s event manager!");
	}
	
	public void addEvent(ActionEvent addEvent) throws FileNotFoundException, IOException {
		FXMLLoader loader = new FXMLLoader();
		Parent eventMaker = loader.load(new FileInputStream("src/application/FXML/EventMakerView.fxml"));
		EventMakerController controller = (EventMakerController) loader.getController();
		controller.initalizeEventMakerController(currentUser, eventMaker);
	}
}
