package application;

import java.io.FileInputStream;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EventManagement {
	
	private static User currentUser;
		
	public static void initializeEventManagerView(User loginUser) {
		try {
			currentUser = loginUser; 
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Parent root = loader.load(new FileInputStream("src/application/EventManagerView.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle(currentUser.getUsername() + "'s Events");
			stage.show();
			
			System.out.println("Opened " + currentUser.getUsername() + "'s event manager!");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}	
	}
}
