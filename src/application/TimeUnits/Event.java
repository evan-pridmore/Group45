package application.TimeUnits;

import java.util.Date;

import application.Exceptions.NullEventEndPointException;
import javafx.scene.paint.Color;
/**
 * Base class for all user created events. Should not be constructed manually.
 */
public class Event extends TimeUnit {
	private String name;
	private Color colour;
	
	Event(Date start, Date end, String aName, Color aColour) throws NullEventEndPointException {
		super(start, end);
		setName(aName);
		setColour(aColour);
	}
		
	public void setName(String givenName) {
		name = givenName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setColour(Color newColour) {
		colour = newColour;
	}
	
	public Color getColour() {
		return colour;
	}
	
	public boolean equals(Event otherEvent) {
		if(otherEvent.getStart().equals(getStart()) && otherEvent.getEnd().equals(getEnd()) && otherEvent.getName().equals(getName()))
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return new String(getStart().toString() + "-->" + getEnd().toString());
	}
}