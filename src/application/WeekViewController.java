package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WeekViewController {
	Stage applicationStage;
	FXMLLoader loader = new FXMLLoader();
	
    @FXML
    private Menu EditMenu;

    @FXML
    private Menu UserMenu;
    
    @FXML
    private VBox Sunday;
    
    @FXML
    private VBox Monday;

    @FXML
    private VBox Tuesday;

    @FXML
    private VBox Wednesday;

    @FXML
    private VBox Thursday;

    @FXML
    private VBox Friday;
    
    @FXML
    private VBox Saturday;
    
    @FXML
    private VBox UpcomingWindow;
    
    @FXML
    private void presentNewEventMenu(ActionEvent newEvent) throws FileNotFoundException, IOException  {
    	System.out.println("New Event Menu");
    	VBox menu = loader.load(new FileInputStream("src/application/EventManagerView.fxml"));
    	Scene scene = new Scene(menu);
    	applicationStage.setScene(scene);
    }
    
    @FXML
    private void presentRemoveEventMenu(ActionEvent removeEvent) {
    	System.out.println("Remove Event Menu");
    }
    
    @FXML
    private void presentSwitchUserMenu(ActionEvent switchUserEvent) {
    	System.out.println("Switch User Menu");
    }
    
    @FXML
    private void logOut(ActionEvent logOutEvent) {
    	System.out.println("Log out");
    }
}
