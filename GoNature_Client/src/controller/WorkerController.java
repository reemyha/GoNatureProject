package controller;

import java.util.ArrayList;
import java.util.HashMap;

import logic.ManagerRequestDetail;
import logic.WorkerDetail;
/**
 * The WorkerController class manages worker-related data and responses.
 */
public class WorkerController {
	
	private boolean gotResponse = false;
	private int result;
	private WorkerDetail workerDetail;
	private int vistorAmount;
	private ArrayList<ManagerRequestDetail> requestList;
	private String CurrentOccupancy;
	private String MaxOccupancy;
	private boolean AverageParkStayTime,OnlineBookingCapacity,ParkCapacity;

	
	public boolean getAverageParkStayTime() {
		return AverageParkStayTime;
	}

	public void setAverageParkStayTime(Boolean averageParkStayTimeResult) {
		AverageParkStayTime = averageParkStayTimeResult;
		gotResponse = true;
	}

	public boolean getOnlineBookingCapacity() {
		return OnlineBookingCapacity;
	}

	public void setOnlineBookingCapacity(boolean onlineBookingCapacity) {
		OnlineBookingCapacity = onlineBookingCapacity;
		gotResponse = true;
	}

	public boolean getParkCapacity() {
		return ParkCapacity;
	}

	public void setParkCapacity(boolean parkCapacity) {
		ParkCapacity = parkCapacity;
		gotResponse = true;
	}

	public boolean isGotResponse() {
		return gotResponse;
	}
	
	public void setGotResponse(boolean gotResponse) {
		this.gotResponse = gotResponse;
	}

	public WorkerDetail getWorkerDetail() {
		return workerDetail;
	}

	public void setWorkerDetail(WorkerDetail workerDetail) {
		this.workerDetail = workerDetail;
	}

	public void setCurrentOccupancy(String currOcc) {
		CurrentOccupancy = currOcc;
		this.gotResponse = true;
	}


	public void setVistorAmountData(int vistorAmountData) {
		this.vistorAmount = vistorAmountData;
		this.gotResponse = true;
		
	}
	
	
	public int getVistorAmount() {
		return vistorAmount;
	}

	public ArrayList<ManagerRequestDetail> getRequestList() {
		return requestList;
	}
	

	public void setRequestList(ArrayList<ManagerRequestDetail> requestList) {

  		this.requestList = null;
		this.requestList = requestList;
		this.gotResponse = true;
	}

//	public WorkerController() {
//		
//	}

	public void setMaxOccupancy(String MaxOcc) {
		MaxOccupancy = MaxOcc;
		this.gotResponse = true;
	}

	
	public String getCurrentOccupancy() {
		return CurrentOccupancy;
	}

	public String getMaxOccupancy() {
		return MaxOccupancy;
	}



	public void setEnterPark() {
		this.gotResponse = true;
	}

	public void setExitPark() {
		this.gotResponse = true;
	}

	public void setCheckBookInDB(int result) {
		this.result =result;
		this.gotResponse = true;
	}
	
	public int getCheckBookInDB() {
		return result;
	}
}
