package application;

import java.util.ArrayList;

import application.TimeUnits.Day;
import application.TimeUnits.Event;
import application.TimeUnits.Week;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**A controller that manages {@link EventViewerView.fxml} and is associated GUI components (e.g., buttons, labels, textfields, etc.)
 * 
 * Extends {@link ApplicationController} which provides a range of static variables.
 * 
 * This should ONLY manage the associated GUI, and should NOT initialize any other stages, scenes, windows, views, etc.
 * (to switch windows or create new stages, add an initialize method in {@link ApplicationController}
 * 
 * @author evan-pridmore
 */
public class EventViewerController extends ApplicationController {
	@FXML
	private TreeView<String> eventTree;
	private ArrayList<Integer> yearList;
	private ArrayList<ArrayList<String>> yearMonthList;
	private ArrayList<ArrayList<ArrayList<Event>>> yearMonthEventList = new ArrayList<ArrayList<ArrayList<Event>>>();
	private ArrayList<ArrayList<ArrayList<String>>> yearMonthEventNameList = new ArrayList<ArrayList<ArrayList<String>>>();
	
		
	public void makeTree() {
		User user = getCurrentUser();
		
			TreeItem<String> rootItem = new TreeItem<String>("All Events");
			if (!(eventTree.getRoot() == null)) {
				eventTree.getRoot().getChildren().clear();
			}
		
		yearList = new ArrayList<Integer>();
		yearMonthList = new ArrayList<ArrayList<String>>();
		yearMonthEventList = new ArrayList<ArrayList<ArrayList<Event>>>();
		
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
							yearMonthEventNameList.add(new ArrayList<ArrayList<String>>());
							
							yearMonthList.get(0).add(event.getStart().getMonth().toString());
							yearMonthEventList.get(0).add(new ArrayList<Event>());
							yearMonthEventNameList.get(0).add(new ArrayList<String>());
							
							yearMonthEventList.get(0).get(0).add(event);
							yearMonthEventNameList.get(0).get(0).add(event.toString());
						}
						else {
							if (!(yearList.contains(event.getStart().getYear()))) {
								yearList.add(event.getStart().getYear());
								yearMonthList.add(new ArrayList<String>());
								yearMonthEventList.add(new ArrayList<ArrayList<Event>>());
								yearMonthEventNameList.add(new ArrayList<ArrayList<String>>());
								
								yearMonthList.get(yearList.indexOf(year)).add(month);
								yearMonthEventList.get(yearList.indexOf(year)).add(new ArrayList<Event>());
								yearMonthEventNameList.get(yearList.indexOf(year)).add(new ArrayList<String>());
								
								yearMonthEventList.get(yearList.indexOf(year)).get(yearMonthList.get(yearList.indexOf(year)).indexOf(month)).add(event);
								yearMonthEventNameList.get(yearList.indexOf(year)).get(yearMonthList.get(yearList.indexOf(year)).indexOf(month)).add(event.toString());
							}
							else {
								if(!(yearMonthList.get(yearList.indexOf(year)).contains(month))) {
									yearMonthList.get(yearList.indexOf(year)).add(month);
									yearMonthEventList.get(yearList.indexOf(year)).add(new ArrayList<Event>());
									yearMonthEventNameList.get(yearList.indexOf(year)).add(new ArrayList<String>());
					
									yearMonthEventList.get(yearList.indexOf(year)).get(yearMonthList.get(yearList.indexOf(year)).indexOf(month)).add(event);
									yearMonthEventNameList.get(yearList.indexOf(year)).get(yearMonthList.get(yearList.indexOf(year)).indexOf(month)).add(event.toString());
								}
								else {
									yearMonthEventList.get(yearList.indexOf(year)).get(yearMonthList.get(yearList.indexOf(year)).indexOf(month)).add(event);
									yearMonthEventNameList.get(yearList.indexOf(year)).get(yearMonthList.get(yearList.indexOf(year)).indexOf(month)).add(event.toString());
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
						
						Rectangle colourRectangle = new Rectangle(16, 16, event.getColour());
						colourRectangle.setStroke(Color.BLACK);
						colourRectangle.setStrokeType(StrokeType.INSIDE);
						
						nameItem.setGraphic(colourRectangle);
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
		TreeItem<String> selectedEventItem = eventTree.getSelectionModel().getSelectedItem();
		if (selectedEventItem != null && selectedEventItem.isLeaf()) {
			String eventName = selectedEventItem.getValue();
			String eventMonth = selectedEventItem.getParent().getValue();
			int eventYear = Integer.parseInt(selectedEventItem.getParent().getParent().getValue());
			initializeEventManagerView(yearMonthEventList.get(yearList.indexOf(eventYear)).get(yearMonthList.get(yearList.indexOf(eventYear)).indexOf(eventMonth)).get(yearMonthEventNameList.get(yearList.indexOf(eventYear)).get(yearMonthList.get(yearList.indexOf(eventYear)).indexOf(eventMonth)).indexOf(eventName)));

		}
	}
}
