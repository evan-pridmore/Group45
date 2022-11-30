package application.TimeUnits;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import application.Exceptions.NullEventEndPointException;

/**
 * The basic class from which all other time units are created. Should never be directly constructed.
 * Stores a start and end time. End time may be null.
*/
public class TimeUnit implements Serializable {
	private static final long serialVersionUID = -2458286694103395827L;
	private ZonedDateTime start;
	private ZonedDateTime end = null;
	
	protected TimeUnit(LocalDateTime startDate, LocalDateTime endDate) throws NullEventEndPointException {
		if (startDate == null)
			throw new NullEventEndPointException("TimeUnit start cannot be null.");
		setStart(startDate);
		if (endDate != null)
			setEnd(endDate);
	}
	
	protected TimeUnit(ZonedDateTime startDate, ZonedDateTime endDate) throws NullEventEndPointException {
		if (startDate == null)
			throw new NullEventEndPointException("TimeUnit start cannot be null.");
		setStart(startDate.toLocalDateTime());
		if (endDate != null)
			setEnd(endDate.toLocalDateTime());
	}
	
	protected void setStart(LocalDateTime startDate) {
		//Store the DateTime in UTC so that event times are preserved when changing time zones.
		start = startDate.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
	}
	
	
	public ZonedDateTime getStart() {
		//Return the DateTime in the local time zone.
		return start.withZoneSameInstant(ZoneId.systemDefault());	
	}
	
	protected void setEnd(LocalDateTime endDate) {
		//Store the DateTime in UTC so that event times are preserved when changing time zones.
		end = endDate.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
	}
	
	public ZonedDateTime getEnd() {
		//Return the DateTime in the local time zone.
		if (end != null)
			return end.withZoneSameInstant(ZoneId.systemDefault());
		else
			return null;
	}
	
	/**
	 * Whether or not the {@link TimeUnit} this method is calls upon is full contained within the given {@link TimeUnit}.
	 * @param biggerTimeUnit The {@link TimeUnit} that the called on {@link TimeUnit} may be in. Should be larger.
	 * @return True if the called on {@link TimeUnit} is fully contained in biggerTimeUnit, else false.
	 */
	public boolean containedIn(TimeUnit biggerTimeUnit) {
		//Only check the start date if the end date is null. (InstantEvents)
		if (end == null) {
			System.out.println("A");
			if (biggerTimeUnit.getStart().minusNanos(1).isBefore(start) && biggerTimeUnit.getEnd().plusNanos(1).isAfter(start))
				return true;
		}
				
		else if (biggerTimeUnit.getStart().minusNanos(1).isBefore(start) && biggerTimeUnit.getEnd().plusNanos(1).isAfter(end))
			return true;
		return false;
	}
	
	/**
	 * A static version of {@link #containedIn()} that checks if one {@link TimeUnit} falls full within the other.
	 * @param smallerTimeUnit The smaller {@link TimeUnit} that may fit in the other.
	 * @param biggerTimeUnit The larger {@link TimeUnit} that may hold the other.
	 * @return True if the called on smallerTimeUnit is fully contained in biggerTimeUnit, else false.
	 */
	public static boolean containedIn(TimeUnit smallerTimeUnit, TimeUnit biggerTimeUnit) {
		return smallerTimeUnit.containedIn(biggerTimeUnit);
	}
	
	/**
	 * Whether or not the called {@link TimeUnit} start time falls between the given {@link TimeUnit}'s start and end time.
	 * @param otherTimeUnit The other {@link TimeUnit} that the called upon one may start in.
	 * @return True if the called on {@link TimeUnit} starts within otherTimeUnit, else false.
	 */
	public boolean startsIn(TimeUnit otherTimeUnit) {
		if (end != null && otherTimeUnit.getStart().minusNanos(1).isBefore(start) && !(otherTimeUnit.getEnd().plusNanos(1).isBefore(end))) 
			return true;
		return false;
	}
	
	/**
	 * A static version of {@link #startsIn} that checks if one {@link TimeUnit} starts in the other.
	 * @param smallerTimeUnit The {@link TimeUnit} to check the start of.
	 * @param biggerTimeUnit The {@link TimeUnit} to check if smallerTimeUnit starts in.
	 * @return True if smallerTimeUnit starts in biggerTimeUnit, else false.
	 */
	public static boolean startsIn(TimeUnit smallerTimeUnit, TimeUnit biggerTimeUnit) {
		return smallerTimeUnit.startsIn(biggerTimeUnit);
	}
	
	/**
	 * Whether or not the called {@link TimeUnit} end time falls between the given {@link TimeUnit}'s start and end time.
	 * @param otherTimeUnit The other {@link TimeUnit} that the called upon one may end in.
	 * @return True if the called on {@link TimeUnit} ends within otherTimeUnit, else false.
	 */
	public boolean endsIn(TimeUnit otherTimeUnit) {
		if (end != null && otherTimeUnit.getEnd().plusNanos(1).isAfter(end) && !(otherTimeUnit.getStart().plusNanos(1).isAfter(start))) 
			return true;
		return false;
	}
	
	/**
	 * A static version of {@link #startsIn} that checks if one {@link TimeUnit} starts in the other.
	 * @param smallerTimeUnit The {@link TimeUnit} to check the start of.
	 * @param biggerTimeUnit The {@link TimeUnit} to check if smallerTimeUnit starts in.
	 * @return True if smallerTimeUnit starts in biggerTimeUnit, else false.
	 */
	public static boolean endsIn(TimeUnit smallerTimeUnit, TimeUnit biggerTimeUnit) {
		return smallerTimeUnit.endsIn(biggerTimeUnit);
	}
	
	/**
	 * Whether or not any portion of given {@link TimeUnit} falls inside of the called {@link TimeUnit}.
	 * @param otherTimeUnit The {@link TimeUnit} to that may fall within the called time unit.
	 * @return True if any part of the given {@link TimeUnit} falls inside the called {@link TimeUnit}, else false.
	 */
	public boolean contains(TimeUnit otherTimeUnit) throws NullEventEndPointException {
		//TimeUnits that occur at a single instant cannot contain anything.
		if (end == null) 
			return false;
		//create a time unit with the same start and end as called instance for comparing.
		TimeUnit self = new TimeUnit(start, end);
		
		//check for full containment.
		if (otherTimeUnit.containedIn(self))
			return otherTimeUnit.containedIn(self);
		
		//Check for multiday events.
		else if (otherTimeUnit.getEnd() != null && self.containedIn(otherTimeUnit))
			return true;
		
		//Check for starting or ending in self.
		else if (otherTimeUnit.startsIn(self) || otherTimeUnit.endsIn(self))
			return true;
		return false;
	}
}
