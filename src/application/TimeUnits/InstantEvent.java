package application.TimeUnits;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import application.Exceptions.NullEventEndPointException;
import javafx.scene.paint.Color;

/**
 * Representation of an event that happens at a single time, not over a period of time.
 * Has a name and colour associated to it.
 */
public class InstantEvent extends Event {
	private static final long serialVersionUID = 8018666614595537151L;
	
	public InstantEvent(ZonedDateTime instant, String aName, Color aColour) throws NullEventEndPointException {
		super(instant, null, aName, aColour);
	}
	
	public InstantEvent(LocalDateTime instant, String aName, Color aColour) throws NullEventEndPointException {
		super(instant, null, aName, aColour);
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s/%s/%s - %s", getName(), getStart().getYear(), getStart().getMonthValue(), getStart().getDayOfMonth(), getStart().toLocalTime().toString());
	}
}
