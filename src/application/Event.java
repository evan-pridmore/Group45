package application;

import java.util.Date;

public class Event extends TimeUnit{
	private String name;
	private int[] colour = new int[3];
	
	Event(Date start, Date end, String aName, int[] aColour) throws InvalidColourException {
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
}
