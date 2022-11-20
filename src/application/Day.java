package application;

import java.util.ArrayList;
import java.util.Date;

class Day extends TimeUnit {
	private ArrayList<Event> Events = new ArrayList<Event>();

	Day(Date beginning) {
		super.setStart(beginning);
		System.out.println(getStart());
		super.setEnd(new Date(beginning.getTime() + 86400000 - 1));
		System.out.println(getEnd());
	}
	
}
