package application;

import java.util.Date;

public class TimedEvent extends Event {

	public TimedEvent(Date start, Date end, String aName, int[] aColour) throws NullEventEndPointException, InvalidColourException {
		super(start, end, aName, aColour);
		if (getEnd() == null)
			throw new NullEventEndPointException("Timed Event end date cannot be null.");
	}

}
