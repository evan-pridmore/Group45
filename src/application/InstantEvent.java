package application;

import java.util.Date;

public class InstantEvent extends Event {
	//Variable that will point at base class' start instance variable for ease of writing as InstantEvents have no start and end, only a time at which they occur.
	private Date time;
	
	public InstantEvent(Date instant, String aName, int[] aColour) throws NullEventEndPointException, InvalidColourException {
		super(instant, null, aName, aColour);
		time = getStart();
		
	}

}
