package application;

import java.io.FileInputStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class CalendarViewController {
	
	private static User currentUser;
	
	// If it is necessary, we can grab a reference to the stage of this window from initializeCalendarView.
	// private static Stage applicationStage; 

	@FXML
	private VBox upcomingEventsVBox;
	
	@FXML
	private static VBox dayViewVBox;
	
	// Opens a new application window depending on the user that has logged-in from the loginView.
	// Needs to be static and take in a user as an argument
	public static void initializeCalendarView(ActionEvent loginEvent, User loginUser) {
		try {
			currentUser = loginUser; 
			FXMLLoader loader = new FXMLLoader();
			Parent root = loader.load(new FileInputStream("src/application/WeekView.fxml"));
			// Login event is used to get a reference to the application Window, which is then casted to Stage.
			Stage stage = (Stage)((Node)loginEvent.getSource()).getScene().getWindow();
			// applicationStage = stage; If it is necessary, we can grab a reference to the stage of this window.
			
			WeekViewController controller = (WeekViewController) loader.getController();
			controller.applicationStage = stage;
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle(currentUser.getUsername() + "'s Calendar");
			stage.show();
			
			System.out.println("Welcome " + currentUser.getUsername() + "!");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		}
	}
}
