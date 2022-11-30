package application;

import java.util.ArrayList;

import application.TimeUnits.Day;
import application.TimeUnits.Event;
import application.TimeUnits.Week;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

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
	@FXML
	private TreeView<String> eventTree;

	
	public void makeTree() {
		eventTree.rootProperty().unbind();
		User user = getCurrentUser();
		
		for (Week week: user.getEvents()) {
			System.out.println("--------------");
			for (Day day : week.getDays()) {
				System.out.println(day.getEvents());
			}
		}
		
		ArrayList<Integer> yearList = new ArrayList<Integer>();
		ArrayList<String> monthList = new ArrayList<String>();
		ArrayList<ArrayList<String>> yearMonthList = new ArrayList<ArrayList<String>>();
		
		
		for (Week week : user.getEvents()) {
			for (Day day : week.getDays()) {
				if (day.getEvents().size() > 0) {	
					for (Event event : day.getEvents()) {
						if (yearList.size() == 0) {
							yearList.add(event.getStart().getYear());
							yearMonthList.add(new ArrayList<String>());
						}
						else {
							if (!(yearList.contains(event.getStart().getYear()))) {
								yearList.add(event.getStart().getYear());
								yearMonthList.add(new ArrayList<String>());
							}
						}
					}
				}
			}	
		}
		
		TreeItem<String> rootItem = new TreeItem<>("All Events");
		eventTree.setRoot(rootItem);
		
		System.out.println(yearList);
		if (yearList.size() > 0) {
			for (int i : yearList) {
			TreeItem<String> yearItem = new TreeItem<>(""+ i);
			rootItem.getChildren().add(yearItem);
			}
		}
 	}
	
	public void selectEvent() {

	}
}
