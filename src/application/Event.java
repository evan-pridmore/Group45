package application;

import java.util.Date;
/**
 * Base class for all user created events. Should not be constructed manually.
 */
class Event extends TimeUnit {
	private String name;
	private int[] colour = new int[3];
	
	Event(Date start, Date end, String aName, int[] aColour) throws NullEventEndPointException, InvalidColourException {
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
	
	public void setColour(int[] newColour) throws InvalidColourException {
		if (newColour.length != 3)
			throw new InvalidColourException();
		colour = newColour;
	}
	
	public int[] getColour() {
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