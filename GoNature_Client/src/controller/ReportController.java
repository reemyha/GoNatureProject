package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import logic.CancellationData;
import logic.CancellationDetail;
import logic.ReportDetail;
/**
 * The ReportController class manages report data and responses.
 */
public class ReportController {
	
	private boolean gotResponse = true;
	private Map<LocalDate, int[]> visitorStatisticData;
	private ArrayList<CancellationDetail> cancellationReportData;
	private int[] dayCount;
	private Map<String, int[]> visitorReportData;
	private Map<String, Integer> visitorAvilaData; //reportdata



	public Map<String, Integer> getVisitorStatData() {
		return visitorAvilaData;
	}



	public void setVisitorStatData(Map<String, Integer> visitorStatData) {
		this.visitorAvilaData = visitorStatData;
		this.gotResponse = false;
	}



	public int[] getDayCount() {
		return dayCount;
		
	}



	public void setDayCount(int[] dayCount) {
		this.dayCount = dayCount;
		this.gotResponse = false;
	}



	public void setVisitorStatisticData(Map<LocalDate, int[]> visitorStatisticData) {
		this.visitorStatisticData = visitorStatisticData;
	}




	private boolean reportCheck;
	private ArrayList<ReportDetail> reportList;
	
	

	public boolean isGotResponse() {
		return gotResponse;
	}
	
	
	
	public void setGotResponse(boolean gotResponse) {
		this.gotResponse = gotResponse;
	}



	public Map<LocalDate, int[]> getVisitorStatisticData() {
		return visitorStatisticData;
	}
	

	public void setvisitorStatisticData(Map<LocalDate, int[]> visitorStatisticData) {
		this.visitorStatisticData = visitorStatisticData;
		this.gotResponse = false;
	}



	public void setCancellationReportData(ArrayList<CancellationDetail> cancellationReportData) {
		this.cancellationReportData = cancellationReportData;
		this.gotResponse = false;
		
	}

	
	public ArrayList<CancellationDetail> getCancellationReportData() {
		return cancellationReportData;
	}
	
	public void setvisitorReportData(Map<String, int[]> visitorReportData) {
		System.out.println("we set the report ");
		this.visitorReportData = visitorReportData;
		this.gotResponse = false;
		
	}
	public Map<String, int[]> getVisitorReportData() {
		return visitorReportData;
	}


	

	public void setAddReportCheck(boolean reportCheck) {
		this.reportCheck = reportCheck;
		this.gotResponse = false;
	}

	public boolean isReportCheck() {
		return reportCheck;
	}



	public void setReportList(ArrayList<ReportDetail> reportList) {
		this.reportList = reportList;
		this.gotResponse = false;
	}



	public ArrayList<ReportDetail> getReportList() {
		return reportList;
	}


}
