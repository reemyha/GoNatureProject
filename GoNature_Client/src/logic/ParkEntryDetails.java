package logic;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
/**
 * Represents details of park entry including order number, visit type, park name, 
 * start and end time, number of visitors, park capacity, and visit date.
 */
public class ParkEntryDetails implements Serializable {
	private String OrderNumber;
	private String VisitType;
	private String ParkName;
	private Time StartTime;
	private Time EndTime;
	private String NumOfVisitors;
	private String ParkCapacityAtm;
	private Date VisitDate;
	
	public ParkEntryDetails(String orderNumber, String visitType, String parkName, Time localTime, String numOfVisitors,
			String parkCapacityAtm, Date localDate) {
		super();
		OrderNumber = orderNumber;
		VisitType = visitType;
		ParkName = parkName;
		StartTime = localTime;
		NumOfVisitors = numOfVisitors;
		ParkCapacityAtm = parkCapacityAtm;
		VisitDate = localDate;
	}


	public ParkEntryDetails(String orderNumber, Time endTime, String parkName, String visitors) {
		super();
		OrderNumber = orderNumber;
		EndTime = endTime;
		ParkName = parkName;
		NumOfVisitors = visitors;
	}


	public String getOrderNumber() {
		return OrderNumber;
	}

	public String getVisitType() {
		return VisitType;
	}

	public String getParkName() {
		return ParkName;
	}

	public Time getStartTime() {
		return StartTime;
	}

	public Time getEndTime() {
		return EndTime;
	}

	public String getNumOfVisitors() {
		return NumOfVisitors;
	}

	public String getParkCapacityAtm() {
		return ParkCapacityAtm;
	}

	public Date getVisitDate() {
		return VisitDate;
	}

	public void setEndTime(Time endTime) {
		EndTime = endTime;
	}
	
}
