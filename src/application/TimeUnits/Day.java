package application.TimeUnits;

import java.time.ZonedDateTime;
import java.util.ArrayList;

import application.Exceptions.NullEventEndPointException;

/**
 * A representation of a single day from 00:00:00 local time to 11:59:59 local time.
 * Has an {@link ArrayList} to hold {@link Event}s that fall within it, sorted in chronological order of start time.
 * Should not be manually constructed, only during {@link Week} construction.
 */
public class Day extends TimeUnit {
	private static final long serialVersionUID = -2390691817137550430L;
	private ArrayList<Event> events = new ArrayList<Event>();

	Day(ZonedDateTime start) throws NullEventEndPointException {
		super(start, start.plusDays(1).minusNanos(1000000000));
	}
	/**
	 * Adds the given {@link Event} to the {@link Day}'s {@link Event} {@link ArrayList} ordered by start time.
	 * @param newEvent {@link Event} to add to the {@link Day}.
	 */
	protected void addEvent(Event newEvent) {
		int  index = 0;
		if (events.size() > 0) {
			for (int i = 0; i < events.size(); i++) {
				if (events.get(i).getStart().isBefore(newEvent.getStart())) {
					index = i + 1;
				}
			}
		}
		events.add(index, newEvent);
	}
	
	/**
	 * @return The {@link ArrayList} containing the {@link Event}s store in the called {@link Day}.
	 */
	public ArrayList<Event> getEvents() {
		return events;
	}
}