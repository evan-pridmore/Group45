package application;

import java.util.ArrayList;
import java.util.Collections;

import java.time.Month;

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
 */
public class EventViewerController extends ApplicationController {
	@FXML
	private TreeView<String> eventTree;
	private ArrayList<Integer> yearList;
	private ArrayList<ArrayList<Month>> yearMonthList;
	private ArrayList<ArrayList<ArrayList<Event>>> yearMonthEventList;
	private ArrayList<ArrayList<ArrayList<String>>> yearMonthEventNameList;
	
	/**
	 * Sets the {@link TreeView} in the event viewer to tree showing all of the {@link User}'s {@link Event}s sorted by year, month, and start time.
	 */
	public void makeTree() {
		User user = getCurrentUser();
		
		//Create a new root item to store everything else.
		TreeItem<String> rootItem = new TreeItem<String>("All Events");
		if (!(eventTree.getRoot() == null)) {
			eventTree.getRoot().getChildren().clear();
		}
		
		//Create ArrayLists for storing
		yearList = new ArrayList<Integer>();
		yearMonthList = new ArrayList<ArrayList<Month>>();
		yearMonthEventList = new ArrayList<ArrayList<ArrayList<Event>>>();
		yearMonthEventNameList = new ArrayList<ArrayList<ArrayList<String>>>();
		
		for (Week week : user.getEvents()) {
			for (Day day : week.getDays()) {
				if (day.getEvents().size() > 0) {
					for (Event event : day.getEvents()) {
						//Get year and month of every user event.
						int year = event.getStart().getYear();
						Month month = event.getStart().getMonth();
						
						if (yearList.size() == 0) {
							//Store the year in first index.
							yearList.add(event.getStart().getYear());
							yearMonthList.add(new ArrayList<Month>());
							yearMonthEventList.add(new ArrayList<ArrayList<Event>>());
							yearMonthEventNameList.add(new ArrayList<ArrayList<String>>());
							
							//Store the month in first index of year representation.
							yearMonthList.get(0).add(event.getStart().getMonth());
							yearMonthEventList.get(0).add(new ArrayList<Event>());
							yearMonthEventNameList.get(0).add(new ArrayList<String>());
							
							//Store the month in first index of month representation.
							yearMonthEventList.get(0).get(0).add(event);
							yearMonthEventNameList.get(0).get(0).add(event.toString());
						}
						else {
							//New year is found
							if (!(yearList.contains(event.getStart().getYear()))) {
								//Add the year to the year list and sort it.
								yearList.add(event.getStart().getYear());
								Collections.sort(yearList);
								yearMonthList.add(yearList.indexOf(year), new ArrayList<Month>());
								yearMonthEventList.add(yearList.indexOf(year), new ArrayList<ArrayList<Event>>());
								yearMonthEventNameList.add(yearList.indexOf(year), new ArrayList<ArrayList<String>>());
								
								//Add the month to the index of its year and sort the ArrayList at this index.
								yearMonthList.get(yearList.indexOf(year)).add(month);
								Collections.sort(yearMonthList.get(yearList.indexOf(year)));
								yearMonthEventList.get(yearList.indexOf(year)).add(yearMonthList.get(yearList.indexOf(year)).indexOf(month),new ArrayList<Event>());
								yearMonthEventNameList.get(yearList.indexOf(year)).add(yearMonthList.get(yearList.indexOf(year)).indexOf(month), new ArrayList<String>());
								
								//Add the event info to the index month at the index of its year.
								yearMonthEventList.get(yearList.indexOf(year)).get(yearMonthList.get(yearList.indexOf(year)).indexOf(month)).add(event);
								yearMonthEventNameList.get(yearList.indexOf(year)).get(yearMonthList.get(yearList.indexOf(year)).indexOf(month)).add(event.toString());
							}
							else {
								//If the year exists and does not contain the month:
								if(!(yearMonthList.get(yearList.indexOf(year)).contains(month))) {
									//Add the month to the index of its year and sort the ArrayList at this index.
									yearMonthList.get(yearList.indexOf(year)).add(month);
									Collections.sort(yearMonthList.get(yearList.indexOf(year)));
									yearMonthEventList.get(yearList.indexOf(year)).add(yearMonthList.get(yearList.indexOf(year)).indexOf(month), new ArrayList<Event>());
									yearMonthEventNameList.get(yearList.indexOf(year)).add(yearMonthList.get(yearList.indexOf(year)).indexOf(month), new ArrayList<String>());
									
									//Add the event info to the index month at the index of its year.
									yearMonthEventList.get(yearList.indexOf(year)).get(yearMonthList.get(yearList.indexOf(year)).indexOf(month)).add(event);
									yearMonthEventNameList.get(yearList.indexOf(year)).get(yearMonthList.get(yearList.indexOf(year)).indexOf(month)).add(event.toString());
								}
								//If both the year and month already exist:
								else {
									//Add the event info to the index month at the index of its year.
									yearMonthEventList.get(yearList.indexOf(year)).get(yearMonthList.get(yearList.indexOf(year)).indexOf(month)).add(event);
									yearMonthEventNameList.get(yearList.indexOf(year)).get(yearMonthList.get(yearList.indexOf(year)).indexOf(month)).add(event.toString());
								}
							}
						}
					}
				}
			}
		}
		//If the user has events, go through each year they belong and create a TreeItem that contains TreeItems of months in it (that have events in them)
		//That contain TreeItems of the Events in them.
		if (yearList.size() > 0) {
			for (int i : yearList) {
				TreeItem<String> yearItem = new TreeItem<>(""+ i);
				for (Month month : yearMonthList.get(yearList.indexOf(i))) {
					TreeItem<String> monthItem = new TreeItem<>(month.toString());
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
	
	/**
	 * Loads the {@link Event} selected by the {@link User} into the event manager when they click on it.
	 */
	@FXML
	private void selectEvent() {
		//Get the TreeItem the user has clicked on.
		TreeItem<String> selectedEventItem = eventTree.getSelectionModel().getSelectedItem();
		
		//Ensure that the selected item is valid and contains an event and is not a year or month.
		if (selectedEventItem != null && selectedEventItem.isLeaf()) {
			//Get the correct Event from the ArrayLists created in #makeTree.
			String eventName = selectedEventItem.getValue();
			String eventMonth = selectedEventItem.getParent().getValue();
			int eventYear = Integer.parseInt(selectedEventItem.getParent().getParent().getValue());
			
			//Open the manager view and clear the user's selection
			initializeEventManagerView(yearMonthEventList.get(yearList.indexOf(eventYear)).get(yearMonthList.get(yearList.indexOf(eventYear)).indexOf(Month.valueOf(eventMonth))).get(yearMonthEventNameList.get(yearList.indexOf(eventYear)).get(yearMonthList.get(yearList.indexOf(eventYear)).indexOf(Month.valueOf(eventMonth))).indexOf(eventName)), false);
			eventTree.getSelectionModel().clearSelection();
		}
	}
}
