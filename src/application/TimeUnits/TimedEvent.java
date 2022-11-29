package application.TimeUnits;

import java.time.ZonedDateTime;
import java.time.LocalDateTime;

import application.Exceptions.NullEventEndPointException;
import javafx.scene.paint.Color;

/**
 * A representation of an event with both a start and end time that occurs between the two.
 * Additionally carry a name and colour associated to them.
 */
public class TimedEvent extends Event {
	private static final long serialVersionUID = -2575660811348119749L;

	public TimedEvent(ZonedDateTime start, ZonedDateTime end, String aName, Color aColour) throws NullEventEndPointException {
		super(start, end, aName, aColour);
		if (getEnd() == null)
			throw new NullEventEndPointException("Timed Event end date cannot be null.");
	}
		
	public TimedEvent(LocalDateTime start, LocalDateTime end, String aName, Color aColour) throws NullEventEndPointException {
		super(start, end, aName, aColour);
		if (getEnd() == null)
			throw new NullEventEndPointException("Timed Event end date cannot be null.");
	}
	
	@Override
	public String toString() {
		return new String(getName() + ": " + getStart().toString() + "-->" + getEnd().toString());
	}
}
