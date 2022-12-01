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
		User user = getCurrentUser();
		
			TreeItem<String> rootItem = new TreeItem<String>("All Events");
			if (!(eventTree.getRoot() == null)) {
				System.out.println("CCC");
				eventTree.getRoot().getChildren().clear();
			}
		
		ArrayList<Integer> yearList = new ArrayList<Integer>();
		ArrayList<ArrayList<String>> yearMonthList = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<ArrayList<Event>>> yearMonthEventList = new ArrayList<ArrayList<ArrayList<Event>>>();
		
		
		for (Week week : user.getEvents()) {
			for (Day day : week.getDays()) {
				if (day.getEvents().size() > 0) {	
					for (Event event : day.getEvents()) {
						int year = event.getStart().getYear();
						String month = event.getStart().getMonth().toString();
						
						if (yearList.size() == 0) {
							yearList.add(event.getStart().getYear());
							yearMonthList.add(new ArrayList<String>());
							yearMonthEventList.add(new ArrayList<ArrayList<Event>>());
							
							yearMonthList.get(0).add(event.getStart().getMonth().toString());
							yearMonthEventList.get(0).add(new ArrayList<Event>());
							
							yearMonthEventList.get(0).get(0).add(event);
						}
						else {
							if (!(yearList.contains(event.getStart().getYear()))) {
								yearList.add(event.getStart().getYear());
								yearMonthList.add(new ArrayList<String>());
								yearMonthEventList.add(new ArrayList<ArrayList<Event>>());
								
								yearMonthList.get(yearList.indexOf(year)).add(month);
								yearMonthEventList.get(yearList.indexOf(year)).add(new ArrayList<Event>());
								
								yearMonthEventList.get(yearList.indexOf(year)).get(yearMonthList.get(yearList.indexOf(year)).indexOf(month)).add(event);
							}
							else {
								if(!(yearMonthList.get(yearList.indexOf(year)).contains(month))) {
									yearMonthList.get(yearList.indexOf(year)).add(month);
									yearMonthEventList.get(yearList.indexOf(year)).add(new ArrayList<Event>());
					
									yearMonthEventList.get(yearList.indexOf(year)).get(yearMonthList.get(yearList.indexOf(year)).indexOf(month)).add(event);
								}
								else {
									yearMonthEventList.get(yearList.indexOf(year)).get(yearMonthList.get(yearList.indexOf(year)).indexOf(month)).add(event);
								}
							}
						}
					}
				}
			}	
		}
		if (yearList.size() > 0) {
			for (int i : yearList) {
				TreeItem<String> yearItem = new TreeItem<>(""+ i);
				for (String month : yearMonthList.get(yearList.indexOf(i))) {
					TreeItem<String> monthItem = new TreeItem<>(month);
					for (Event event : yearMonthEventList.get(yearList.indexOf(i)).get(yearMonthList.get(yearList.indexOf(i)).indexOf(month))) {
						TreeItem<String> nameItem = new TreeItem<>(event.toString());
						monthItem.getChildren().add(nameItem);
					}
					yearItem.getChildren().add(monthItem);
				}
			if (eventTree.getRoot() == null)
				rootItem.getChildren().add(yearItem);
			else 
				eventTree.getRoot().getChildren().add(yearItem);
			}
		}
		if (eventTree.getRoot() == null)
			eventTree.setRoot(rootItem);
 	}
	
	public void selectEvent() {

	}
	
	public void closeEventViewer() {
		allEventPanel.getChildren().clear();
	}
}
