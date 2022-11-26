package application.TimeUnits;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import application.Exceptions.EventOutsideTimeUnitException;
import application.Exceptions.InvalidColourException;
import application.Exceptions.NullEventEndPointException;


public class Month extends TimeUnit {
	private Week[] monthWeeks = new Week[4];
	private int length;
	public Month(Date chosenDate) throws NullPointerException, NullEventEndPointException {
		super(chosenDate, null, true);
		LocalDateTime dateAsLocalDateTime = chosenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		int dayofMonth = dateAsLocalDateTime.getDayOfMonth() - 1;
		dateAsLocalDateTime = dateAsLocalDateTime.toLocalDate().atStartOfDay().minusDays(dayofMonth);
		setStart(Date.from(dateAsLocalDateTime.atZone(ZoneId.systemDefault()).toInstant()));
		if (((dateAsLocalDateTime.getYear() % 4 == 0) && (dateAsLocalDateTime.getYear() % 100 != 0)) || (dateAsLocalDateTime.getYear() % 400 == 0)) {
			length = dateAsLocalDateTime.getMonth().length(true);
			dateAsLocalDateTime = dateAsLocalDateTime.plusDays((length)).minusNanos(1000000000);
		}
		else {
			length = dateAsLocalDateTime.getMonth().length(false);
			System.out.println(dateAsLocalDateTime);
			dateAsLocalDateTime = dateAsLocalDateTime.plusDays((length)).minusNanos(1000000000);
			System.out.println(dateAsLocalDateTime);
		}
		 setEnd(Date.from(dateAsLocalDateTime.atZone(ZoneId.systemDefault()).toInstant()));
		generateWeeks();
	}
	
	private Month(Date start, Date end, Week[] monthArray) throws NullEventEndPointException {
		super(start, end);
		monthWeeks = monthArray;
	}
	
	private void generateWeeks() throws NullPointerException, NullEventEndPointException {
		Date dayStart = getStart();
		for (int i = 0; i < monthWeeks.length; i++) {
			monthWeeks[i] = new Week(dayStart);
			dayStart = new Date(dayStart.getTime() + (86400000 * length));
		}
	}
	
	public Week getWeek(int monthWeek) {
		try {
			return monthWeeks[monthWeek - 1];
		}
		catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException("Week of month must be 1-4.");
		}
	}
	
	public void addEvent(Event event) throws NullEventEndPointException, EventOutsideTimeUnitException, InvalidColourException {
		Month self = new Month(getStart(), getEnd(), monthWeeks);
		if (self.contains(event)) {
			for (Week week : monthWeeks) {
				if (event instanceof InstantEvent) {
					week.addEvent(event);
					break;
				}
				else if (event instanceof TimedEvent) {
					if (event.containedIn(week)) {
						week.addEvent(event);
						break;
					}
					else if (event.startsIn(week)) {
						week.addEvent(new TimedEvent(event.getStart(), week.getEnd(), event.getName(), event.getColour()));
						continue;
					}
					else if (event.endsIn(week)) {
						week.addEvent(new TimedEvent(week.getStart(), event.getEnd(), null, event.getColour()));
						continue;
					}
				}
			}
		}
		else {
			throw new EventOutsideTimeUnitException(String.format("Tried to add Event %1$s to month of %2$s, but it %1$s does not fall within it.", event.getName(), self.getStart().toString()));
		}
	}
}
