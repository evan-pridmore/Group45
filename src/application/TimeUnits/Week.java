package application.TimeUnits;

import java.util.Date;
import java.util.Locale;

import application.Exceptions.EventOutsideTimeUnitException;
import application.Exceptions.NullEventEndPointException;

import java.time.ZonedDateTime;
import java.time.temporal.WeekFields;
import java.util.Calendar;


/**
 * A representation of one Calendar week, from Sunday to Saturday that holds an {@link Array} of 7 {@link Day}s.
 */
public class Week extends TimeUnit {
	private static final long serialVersionUID = 4639573022345939820L;
	private Day[] weekDays = new Day[7] ;
	private int weekNum;
	
	public Week(ZonedDateTime chosenDate) throws NullPointerException, NullEventEndPointException {
		super(chosenDate, null);
		
		//Get the first day of the week of the chosen day.
		Calendar chosenDateCalendar = Calendar.getInstance(Locale.CANADA);
		chosenDateCalendar.setTime(new Date(chosenDate.toEpochSecond() * 1000));
		chosenDate = chosenDate.minusDays(chosenDateCalendar.get(Calendar.DAY_OF_WEEK) - chosenDateCalendar.getFirstDayOfWeek()).toLocalDate().atStartOfDay(chosenDate.getZone());
		
		//Set the start of the week to the start of the first day.
		setStart(chosenDate.toLocalDateTime());
		//Set the end of the week to the end of the last day.
		setEnd(chosenDate.plusWeeks(1).minusNanos(1).toLocalDateTime());
		
		//Create the days in the week.
		generateDays();
		
		//Set weekNum to the number week of the year.
		weekNum = getStart().get(WeekFields.SUNDAY_START.weekOfWeekBasedYear());
	}

	private void generateDays() throws NullEventEndPointException {
		ZonedDateTime dayStart = getStart();
		for (int i = 0; i < weekDays.length; i++) {
				weekDays[i] = new Day(dayStart);
				dayStart = dayStart.plusDays(1);
		}
	}
	
	/**
	 * 
	 * @return The array of {@link Day}s contained in the week.
	 */
	public Day[] getDays() {
		return weekDays;
	}
	
	/**
	 * Gets a {@link Day} object held within the week.
	 * @param weekDay an int from 1-7 corresponding to the number of the day in the week.
	 * @return The day of the week tied to the weekDay provided.
	 * @throws IndexOutOfBoundsException if weekDay is <0 or >7.
	 */
	public Day getDay(int weekDay) throws IndexOutOfBoundsException {
		try {
			return weekDays[weekDay - 1];
		}
		catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException("Day of week must be 1-7.");
		}
	}
	
	/**
	 * Gets the number of the week in the year.
	 * @return Integer value of the week number in the year.
	 */
	public int getWeekNum() {
		return weekNum;
	}
	
	/**
	 * Adds an {@link Event} to the week by passing it into the {@link Day}(s) it belongs to and calling {@link Day#addEvent(Event)}.
	 * @param event The {@link Event} to be added to a {@link Day} in the {@link Week}.
	 * @throws EventOutsideTimeUnitException If the event does not belong to a {@link Day} stored in the array.
	 */
	public void addEvent(Event event) throws NullEventEndPointException, EventOutsideTimeUnitException {
		if (event.containedIn(this)) {
			for (Day day : weekDays) {
				if (event.containedIn(day)) {
					day.addEvent(event);
				}
				else if (event.startsIn(day)) {
					TimedEvent firstPart = new TimedEvent(event.getStart(), day.getEnd(), event.getName(), event.getColour());
					TimedEvent secondPart = new TimedEvent(day.getEnd().plusNanos(1000000000), event.getEnd(), event.getName(), event.getColour());
					addEvent(firstPart);
					addEvent(secondPart);
				}
			}
		}

		else 
			throw new EventOutsideTimeUnitException(String.format("Tried to add Event %1$s to week of %2$s, but it %1$s is not contained within it.", event.getName(), this.getStart().toString()));
	}
}