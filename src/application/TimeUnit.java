package application;

import java.util.Date;

class TimeUnit{
	private Date start;
	private Date end;
	
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
}
