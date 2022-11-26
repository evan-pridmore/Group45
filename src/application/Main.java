package application;
	
import java.io.FileInputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			BorderPane root = loader.load(new FileInputStream("src/application/FXML/LoginView.fxml"));
			Scene scene = new Scene(root);
			
			ApplicationController appController = (ApplicationController) loader.getController();
			appController.setApplicationController();
			appController.setApplicationStage(primaryStage);
			appController.setApplicationScene(scene);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Login");
 			primaryStage.centerOnScreen();
			primaryStage.show();
			
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
