package application;

import application.TimeUnits.Event;
import javafx.scene.shape.Rectangle;

public class EventBlock {

	private static final long serialVersionUID = 3926476910803848511L;

	static Rectangle generateEventBlock(Event inputEvent) {
		Rectangle eventBlock = new Rectangle(200, 75, inputEvent.getColour());
		
		return eventBlock;
	}

}
