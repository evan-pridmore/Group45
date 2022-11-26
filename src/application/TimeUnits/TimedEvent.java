package application.TimeUnits;

import java.util.Date;

import application.Exceptions.NullEventEndPointException;
import javafx.scene.paint.Color;

public class TimedEvent extends Event {

	private static final long serialVersionUID = -2575660811348119749L;

	public TimedEvent(Date start, Date end, String aName, Color aColour) throws NullEventEndPointException {
		super(start, end, aName, aColour);
		if (getEnd() == null)
			throw new NullEventEndPointException("Timed Event end date cannot be null.");
	}
}
