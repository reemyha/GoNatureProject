package controller;

import java.io.IOException;
import java.sql.SQLException;

import enums.Commands;
import ocsf.server.ConnectionToClient;
import server.ServerUI;
import logic.BookingDetail;
import logic.CancellationData;
import logic.CancellationDetail;
import logic.DateDetail;
import logic.Message;
import logic.ParkEntryDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * Controller class responsible for managing worker-related functionalities.
 */
public class WorkerController {

	private int ValueToChange;
	private DateDetail dates;
	private ConnectionToClient client;
	private String parkName;
	
	
	public WorkerController(int ValueToChange, String parkName, ConnectionToClient client) {
		this.ValueToChange = ValueToChange;
		this.parkName = parkName;
		this.client = client;
	}


	public WorkerController(ConnectionToClient client) {
		this.client = client;	
	}

	 /**
     * Changes the capacity of the park.
     */
	public void ChangeParkCapacity() 
	{
		System.out.println("WWW" + parkName);
		boolean bool = ServerUI.sv.dbController.ChangeParkCapacityInDb(ValueToChange,parkName);
		Message msg  = new Message(bool,Commands.ParkCapacityCheck);
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
     * Changes the online booking capacity of the park.
     */
	public void ChangeOnlineBookingCapacity() 
	{
		boolean bool = ServerUI.sv.dbController.ChangeOnlineBookingCapacityInDb(ValueToChange,parkName);
		Message msg  = new Message(bool,Commands.OnlineBookingCapacityCheck);
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    /**
     * Changes the average stay time of visitors in the park.
     */
	public void ChangeAverageParkStayTime() 
	{
		boolean bool = ServerUI.sv.dbController.ChangeAverageParkStayTimeInDb(ValueToChange,parkName);
		System.out.println("B" + bool);
		Message msg  = new Message(bool,Commands.AverageParkStayTimeCheck);
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    /**
     * Retrieves visitor statistics for the specified date range and park.
     */
	public void getVisitorStatistic(DateDetail visitorStatisticDateDetail) {
		System.out.println("A");
		LocalDate dateFrom = visitorStatisticDateDetail.getStart();
		LocalDate dateTo = visitorStatisticDateDetail.getEnd();
		String parkname = visitorStatisticDateDetail.getParkName();

		Map<LocalDate, int[]> visitorStatisticData = ServerUI.sv.getDbController().getVisitorStatisticDataInDb(dateFrom ,dateTo ,parkname);
		System.out.println("B");
		System.out.println(visitorStatisticData);
		Message msg  = new Message(visitorStatisticData,Commands.visitorStatisticData);
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
     * Retrieves the current occupancy of the specified park.
     */
	public void getCurrOccupancy(String parkName) {
		String co = ServerUI.sv.dbController.getCurrOccupancy(parkName);
		Message msg  = new Message(co,Commands.CurrentOccupancy);
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	/**
     * Retrieves the maximum occupancy of the specified park.
     */
	public void getMaxOccupancy(String parkName) {
		String mo = ServerUI.sv.dbController.getMaxOccupancy(parkName);
		Message msg  = new Message(mo,Commands.MaxOccupancy);
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * Retrieves cancellation report data for the specified park.
     */
	public void getCancellationReport(String parkName) {
        // Retrieve cancellation report data from the database
		int[] averageCancellationReport = ServerUI.sv.dbController.getAverageCancellationReportInDb(parkName);
        ArrayList<CancellationDetail> cancellationDetails = ServerUI.sv.dbController.getDetails(parkName);

        // Create a CancellationData object to hold both the list and the ArrayList
        CancellationData cancellationData = new CancellationData(cancellationDetails, averageCancellationReport);

        // Create a message containing the cancellation report data
        Message msg = new Message(cancellationData, Commands.CancellationReportData);

        try {
            // Send the message to the client
            client.sendToClient(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        
    
	
	/**
     * Retrieves visitor report data for the specified date range and park.
     */
	public void getVisitorReport(DateDetail visitorReportDateDetail){
		LocalDate dateFrom = visitorReportDateDetail.getStart();
		LocalDate dateTo = visitorReportDateDetail.getEnd();
		String parkname = visitorReportDateDetail.getParkName();
		
		
		try {
			Map<String, int[]> visitorReportData = ServerUI.sv.getDbController().getVisitorDataInDb(dateFrom ,dateTo ,parkname);
			
			Message msg  = new Message(visitorReportData,Commands.visitorReportData);
			client.sendToClient(msg);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	
	
	
	
	public void getCancellationAvrgByDay(String ParkName)
	{
		

	}


	/**
     * Retrieves the visitor amount in the specified park.
     */
	public void getVisitorAmountInPark(String parkName) {

		int vistorAmount = ServerUI.sv.dbController.getVisitorAmountInDb(parkName);
		Message msg  = new Message(vistorAmount,Commands.vistorAmountData);
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

    /**
     * Retrieves visitor statistics report data for the specified date range and park.
     */
	public void GetvisitorStatReport(DateDetail dateDetail) {
		
		HashMap<String, Integer> statReportData = ServerUI.sv.dbController.GetVisitorStatReportInDb(dateDetail.getStart(),dateDetail.getEnd(),dateDetail.getParkName());
		Message msg  = new Message(statReportData,Commands.statReportData);
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	

	
	
    /**
     * Registers a visitor's entry into the park.
     */
	public void enterPark(ParkEntryDetails enterPark) {
		ServerUI.sv.dbController.enterPark(enterPark);
		Message msg = new Message(null, Commands.EnterPark);
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * Registers a visitor's exit from the park.
     */
	public void exitPark(ParkEntryDetails exitPark) {
		ServerUI.sv.dbController.exitPark(exitPark);
		Message msg = new Message(null, Commands.ExitPark);
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


    /**
     * Checks if a booking exists in the database.
     */
	public void checkBookInDB(String BookNum) {
		int flag = ServerUI.sv.dbController.checkBookInDB(BookNum);
		Message msg = new Message(flag, Commands.CheckBookInDB);
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
