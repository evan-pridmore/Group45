package application;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

/**A controller that manages {@link EventViewerView.fxml} and is associated GUI components (e.g., buttons, labels, textfields, etc.)
 * 
 * Extends {@link ApplicationController} which provides a range of static variables.
 * 
 * This should ONLY manage the associated GUI, and should NOT initialize any other stages, scenes, windows, views, etc.
 * (to switch windows or create new stages, add an initialize method in {@link ApplicationController}
 * 
 * @author evan-pridmore
 */
public class EventManagementController extends ApplicationController {
	
	@FXML
	private VBox allEventPanel;

	public void addEvent(ActionEvent addEvent) {
		initializeEventMakerView();
	}
}
