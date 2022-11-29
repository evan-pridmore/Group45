package application.TimeUnits;

import java.time.ZonedDateTime;
import java.time.LocalDateTime;

import application.Exceptions.NullEventEndPointException;
import javafx.scene.paint.Color;
/**
 * Base class for all user created events. Should not be constructed manually.
 */
public class Event extends TimeUnit {
	private static final long serialVersionUID = 1632201054631730351L;
	private String name;
	private Color colour;
	
	Event(ZonedDateTime start, ZonedDateTime end, String aName, Color aColour) throws NullEventEndPointException {
		super(start, end);
		setName(aName);
		setColour(aColour);
	}
		
	Event(LocalDateTime start, LocalDateTime end, String aName, Color aColour) throws NullEventEndPointException {
		super(start, end);
		setName(aName);
		setColour(aColour);
	}

	/**
	 * Sets the {@link Event} name to a copy of the given name {@link String}.
	 * @param givenName {@link String} name to give event.
	 */
	public void setName(String givenName) {
		name = new String(givenName);
	}
	
	/**
	 * @return A copy of the {@link Event} name.
	 */
	public String getName() {
		return new String(name);
	}
	
	/**
	 * Sets the {@link Event}'s colour to the provided {@link Color}.
	 * @param newColour {@link Color} to set to.
	 */
	public void setColour(Color newColour) {
		colour = newColour;
	}
	
	/**
	 * @return The {@link Color} of the {@link Event}.
	 */
	public Color getColour() {
		return colour;
	}
	
	/**
	 * Whether or not the called {@link Event} is equal to the provided {@link Event}.
	 * @param otherEvent
	 * @return True if the two {@link Event}'s are the same, else false.
	 */
	public boolean equals(Event otherEvent) {
		if(otherEvent.getStart().equals(getStart()) && otherEvent.getEnd().equals(getEnd()) && otherEvent.getName().equals(getName()))
			return true;
		return false;
	}
}