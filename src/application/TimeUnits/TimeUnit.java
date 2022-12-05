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
abstract class TimeUnit implements Serializable {
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
		if (this.startsIn(biggerTimeUnit) && this.endsIn(biggerTimeUnit))
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
		if (otherTimeUnit.getEnd() != null && otherTimeUnit.getStart().minusNanos(1).isBefore(getStart()) && otherTimeUnit.getEnd().plusNanos(1).isAfter(getStart()))
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
		if (otherTimeUnit.getEnd() != null) {
			if (getEnd() == null)
				return this.startsIn(otherTimeUnit);
			else if (otherTimeUnit.getStart().minusNanos(1).isBefore(getEnd()) && otherTimeUnit.getEnd().plusNanos(1).isAfter(getEnd()))
				return true;
		}
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
	public boolean contains(TimeUnit otherTimeUnit) {
		if (getEnd() != null)
			if (otherTimeUnit.startsIn(this) || otherTimeUnit.endsIn(this))
				return true;
		return false;
	}
}
