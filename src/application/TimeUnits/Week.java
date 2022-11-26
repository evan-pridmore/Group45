package application.TimeUnits;

import java.util.Date;

import application.Exceptions.EventOutsideTimeUnitException;
import application.Exceptions.NullEventEndPointException;

import java.util.Calendar;

public class Week extends TimeUnit {
	private static final long serialVersionUID = 4639573022345939820L;
	private Day[] weekDays = new Day[7] ;
	
	public Week(Date chosenDate) throws NullPointerException, NullEventEndPointException {
		super(chosenDate, null, true);
		Calendar calendarWeek = Calendar.getInstance();
		calendarWeek.setTime(chosenDate);
		
		int dayOffset = -(calendarWeek.get(Calendar.DAY_OF_WEEK) - calendarWeek.getFirstDayOfWeek());
		calendarWeek.add(Calendar.DAY_OF_MONTH, dayOffset);
		calendarWeek.set(Calendar.HOUR_OF_DAY, 0);
		calendarWeek.set(Calendar.MINUTE, 0);
		calendarWeek.set(Calendar.SECOND, 0);
		calendarWeek.set(Calendar.MILLISECOND, 0);
		super.setStart(calendarWeek.getTime());
		long plusSixDays = calendarWeek.getTime().getTime() + 604800000 - 1;
		super.setEnd(new Date(plusSixDays));
		generateDays();
	}
	
	private Week(Date start, Date end, Day[] weekArray) throws NullEventEndPointException {
		super(start, end);
		weekDays = weekArray;
	}
	
	
	private void generateDays() throws NullEventEndPointException {
		Date dayStart = getStart();
		for (int i = 0; i < weekDays.length; i++) {
				weekDays[i] = new Day(dayStart);
				dayStart = new Date(dayStart.getTime() + 86400000);
		}
	}
	
	public Day getDay(int weekDay) throws IndexOutOfBoundsException {
		try {
			return weekDays[weekDay - 1];
		}
		catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException("Day of week must be 1-7.");
		}
	}
	
	public void addEvent(Event event) throws NullEventEndPointException, EventOutsideTimeUnitException {
		Week self = new Week(getStart(), getEnd(), weekDays);
		if (self.contains(event)) {
			for (Day day : weekDays) {
				if (day.contains(event)) {
					if (event instanceof InstantEvent) {
						day.addEvent(event);
						break;
					} 
					else if (event instanceof TimedEvent) {
						if (event.containedIn(day)) {
							day.addEvent(event);
							break;
						}
						else if (event.startsIn(day)) {
							day.addEvent(new TimedEvent(event.getStart(), day.getEnd(), event.getName(), event.getColour()));
						}
						else if (event.endsIn(day)) {
							day.addEvent(new TimedEvent (new Date(day.getStart().getTime() + 1), event.getEnd(), null, event.getColour()));
						}
					}
				}
			}
		}
		else {
			throw new EventOutsideTimeUnitException(String.format("Tried to add Event %1$s to week of %2$s, but it %1$s does not fall within it.", event.getName(), self.getStart().toString()));
		}
	}
}
