package application.TimeUnits;

import java.util.Date;

import application.Exceptions.NullEventEndPointException;
import javafx.scene.paint.Color;

public class InstantEvent extends Event {
	private static final long serialVersionUID = 8018666614595537151L;
	//Variable that will point at base class' start instance variable for ease of writing as InstantEvents have no start and end, only a time at which they occur.
	private Date time;
	
	public InstantEvent(Date instant, String aName, Color aColour) throws NullEventEndPointException {
		super(instant, null, aName, aColour);
		time = getStart();
	}
}
