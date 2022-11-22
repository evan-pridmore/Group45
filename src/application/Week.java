package application;

import java.util.Date;
import java.util.Calendar;

public class Week extends TimeUnit{
	private Day[] weekDays = new Day[7] ;
	Week(Date chosenDate) throws NullPointerException, NullEventEndPointException {
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
		
		System.out.println(getStart());
		long plusSixDays = calendarWeek.getTime().getTime() + 604800000 - 1;
		super.setEnd(new Date(plusSixDays));
		System.out.println(getEnd());
		generateDays();
	}
	
	private void generateDays() throws NullEventEndPointException {
		Date dayStart = getStart();
		for (int i = 0; i < weekDays.length; i++) {
				weekDays[i] = new Day(dayStart);
				dayStart = new Date(dayStart.getTime() + 86400000);
		}
	}
}
