package logic;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents details about a date range and park name.
 */
public class DateDetail implements Serializable{
	
	private LocalDate start,end;
	private String parkName;


	public DateDetail(LocalDate start, LocalDate end) {
		this.setStart(start);
		this.setEnd(end);
	}

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public String getParkName() {
		return parkName;
	}
	
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	
	

}
