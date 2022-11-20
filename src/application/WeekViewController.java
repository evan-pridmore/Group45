package application;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;

public class WeekViewController {

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
    private void presentNewEventMenu(ActionEvent newEvent) {
    	System.out.println("New Event Menu");
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
