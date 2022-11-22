package application;

import java.util.ArrayList;
import java.util.Date;

class Day extends TimeUnit {
	private ArrayList<Event> Events = new ArrayList<Event>();

	Day(Date start) throws NullEventEndPointException {
		super(start, new Date(start.getTime()  + 86400000 - 1));
		System.out.println(getStart());
		System.out.println(getEnd());
	}
	
}
