package application;

import java.io.FileInputStream;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EventManagerController {
	
	private static FXMLLoader loader = new FXMLLoader();
	private static Stage applicationStage; 
	private static User currentUser;
		
	public static void initializeEventManagerView(Stage calendarViewStage, User loginUser) {
		try {
			currentUser = loginUser; 
			applicationStage = calendarViewStage;
			Parent root = loader.load(new FileInputStream("src/application/FXML/EventManagerView.fxml"));
			Scene scene = new Scene(root);
			
			Stage ManagerStage = new Stage();
			ManagerStage.initModality(Modality.APPLICATION_MODAL);
			
			ManagerStage.setScene(scene);
			ManagerStage.setTitle(currentUser.getUsername() + "'s Events");
			ManagerStage.show();
			
			System.out.println("Opened " + currentUser.getUsername() + "'s event manager!");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
	
		}	
	}
	
}
