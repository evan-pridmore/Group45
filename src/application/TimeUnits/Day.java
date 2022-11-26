package application.TimeUnits;

import java.util.ArrayList;
import java.util.Date;

import application.Exceptions.NullEventEndPointException;

public class Day extends TimeUnit {
	private ArrayList<Event> events = new ArrayList<Event>();

	Day(Date start) throws NullEventEndPointException {
		super(start, new Date(start.getTime()  + 86400000 - 1));
		System.out.println(getStart());
		System.out.println(getEnd());
	}
	
	public void addEvent(Event newEvent) {
		if (events.size() > 0) {
			int  index = 0;
			for (int i = 0; i < events.size(); i++) {
				if (events.get(i).getStart().before(newEvent.getStart())) {
					index = i + 1;
				}
			}
			events.add(index, newEvent);
		}
	}
}