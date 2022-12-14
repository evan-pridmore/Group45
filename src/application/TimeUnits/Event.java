package application.TimeUnits;

import java.time.ZonedDateTime;
import java.time.LocalDateTime;

import application.Exceptions.NullEventEndPointException;
import javafx.scene.paint.Color;
/**
 * Base class for all user created events. Should not be constructed manually.
 */
public abstract class Event extends TimeUnit {
	private static final long serialVersionUID = 1632201054631730351L;
	private String name;
	private double[] colour = new double[4];
	
	protected Event(ZonedDateTime start, ZonedDateTime end, String aName, Color aColour) throws NullEventEndPointException {
		super(start, end);
		if (end != null && end.minusDays(1).equals(start)) {
			setEnd(end.minusNanos(1000000000).toLocalDateTime());
		}
		setName(aName);
		setColour(aColour);
	}
		
	protected Event(LocalDateTime start, LocalDateTime end, String aName, Color aColour) throws NullEventEndPointException {
		super(start, end);
		if (end != null && end.minusDays(1).equals(start))
			setEnd(end.minusNanos(1000000000));
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
		colour[0] = newColour.getRed();
		colour[1] = newColour.getGreen();
		colour[2] = newColour.getBlue();
		colour[3] = newColour.getOpacity();
	}
	
	/**
	 * @return The {@link Color} of the {@link Event}.
	 */
	public Color getColour() {
		return new Color(colour[0], colour[1], colour[2], colour[3]);
	}
	
	/**
	 * Whether or not the called {@link Event} is equal to the provided {@link Event}.
	 * @param otherEvent
	 * @return True if the two {@link Event}'s are the same, else false.
	 */
	public boolean equals(Event otherEvent) {
		if (this.getClass() != otherEvent.getClass())
			return false;
		else if (this instanceof InstantEvent && getStart().equals(otherEvent.getStart()) && getName().equals(otherEvent.getName()) && getColour().equals(otherEvent.getColour()))
			return true;
		else if (this instanceof TimedEvent && getStart().equals(otherEvent.getStart()) && getEnd().equals(otherEvent.getEnd()) && getName().equals(otherEvent.getName()) && getColour().equals(otherEvent.getColour()))
			return true;
		else
			return false;
	}
}