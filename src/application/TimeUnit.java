package application;

import java.util.Date;

class TimeUnit{
	private Date start;
	private Date end;
	
	protected TimeUnit(Date startDate, Date endDate) {
		start = startDate;
		end = endDate;
	}
	
	protected void setStart(Date startDate) {
		start = startDate;
	}
	
	
	public Date getStart() {
		return start;	
	}
	
	protected void setEnd(Date endDate) {
		end = endDate;
	}
	
	public Date getEnd() {
		return end;
	}
	
	public boolean containedIn(TimeUnit biggerTimeUnit) {
		if (biggerTimeUnit.getStart().before(start) && biggerTimeUnit.getEnd().after(end)) 
			return true;
		return false;
	}
	
	public static boolean containedIn(TimeUnit smallerTimeUnit, TimeUnit biggerTimeUnit) {
		return smallerTimeUnit.containedIn(biggerTimeUnit);
	}
	
	public boolean startsIn(TimeUnit otherTimeUnit) {
		if (otherTimeUnit.getStart().before(start) && !(otherTimeUnit.getEnd().before(end))) 
			return true;
		return false;
	}
	
	public static boolean startsIn(TimeUnit smallerTimeUnit, TimeUnit biggerTimeUnit) {
		return smallerTimeUnit.startsIn(biggerTimeUnit);
	}
	
	public boolean endsIn(TimeUnit otherTimeUnit) {
		if (otherTimeUnit.getEnd().after(end) && !(otherTimeUnit.getStart().after(start))) 
			return true;
		return false;
	}
	
	public static boolean endsIn(TimeUnit smallerTimeUnit, TimeUnit biggerTimeUnit) {
		return smallerTimeUnit.endsIn(biggerTimeUnit);
	}
	
	public boolean contains(TimeUnit smallerTimeUnit) {
		TimeUnit self = new TimeUnit(start, end);
		if (smallerTimeUnit.startsIn(self) || smallerTimeUnit.endsIn(self))
			return true;
		return false;
	}
}
