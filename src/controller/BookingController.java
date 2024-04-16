package controller;
//Server
import java.io.IOException;
import java.util.ArrayList;

import enums.Commands;
import logic.BookingDetail;
import logic.LoginDetail;
import logic.Message;
import ocsf.server.ConnectionToClient;
import server.ServerUI;
/**
 * The BookingController class manages booking-related operations and interactions with clients.
 */
public class BookingController {
	
	private static final Commands ChangePaymentStatusInDB = null;
	private ConnectionToClient client;
	
	 /**
     * Constructs a BookingController with the specified client connection.
     * @param client The client connection
     */
	public BookingController(ConnectionToClient client) {
		this.client = client;
	}
	
	 /**
     * Retrieves the list of bookings for a given user ID and sends it to the client.
     * @param ID The login details containing the user ID
     */
	public void myBookingList(LoginDetail ID) {
		ArrayList<BookingDetail> list = ServerUI.sv.dbController.bookingList(ID.getId());
		Message msg = new Message(list,Commands.VisitorMyBooking  );
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
     * Cancels a booking with the specified order ID and sends the cancellation status to the client.
     * @param orderID The booking details containing the order ID
     */
	public void cancelOrder(BookingDetail orderID) {
		boolean succeeded = ServerUI.sv.dbController.cancelOrderInDB(orderID.getOrderNumber());
		Message msg = new Message(succeeded,Commands.CancelBooking  );
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 /**
     * Adds a new booking with the specified details and checks for its availability.
     * Sends appropriate messages back to the client.
     * @param newBookingDetail The details of the new booking
     */
	public void AddNewBooking(BookingDetail newBookingDetail) {
		String checkExisting = ServerUI.sv.dbController.AddNewBookingInDB(newBookingDetail.getParkName(),newBookingDetail.getDate(),
				newBookingDetail.getNumOfVisitors(),newBookingDetail.getVisitType(),newBookingDetail.getEmail(),
				newBookingDetail.getTelephone(),newBookingDetail.getVisitorID(), newBookingDetail.getTableName());
		Message msg = new Message(checkExisting,Commands.checkIfExist );
		try {
			client.sendToClient(msg);
		} catch (IOException e) { 
			e.printStackTrace();
		}
		
	}
	
	/**
     * Checks the availability of a booking with the specified details and sends the result to the client.
     * @param newBookingDetail The details of the booking to check
     */
	public void CheckBooking(BookingDetail newBookingDetail) {
		boolean checkCapacity = ServerUI.sv.dbController.CheckParkAvailability(newBookingDetail.getParkName(),newBookingDetail.getDate(),
				newBookingDetail.getNumOfVisitors(),newBookingDetail.getVisitType(),newBookingDetail.getEmail(),
				newBookingDetail.getTelephone(),newBookingDetail.getVisitorID());
		Message msg = new Message(checkCapacity,Commands.CheckParkCapacity  );
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

    /**
     * Retrieves the nearest available slots for booking and sends them to the client.
     * @param slotsToCheck The details of the booking to check available slots for
     */
	public void GetAvailableSlots(BookingDetail slotsToCheck) {
		ArrayList<String> SlotsFromDB = ServerUI.sv.dbController.getNearestAvailableDates(slotsToCheck.getParkName(),slotsToCheck.getDate(),
				slotsToCheck.getNumOfVisitors(),slotsToCheck.getVisitType(),slotsToCheck.getEmail(),
				slotsToCheck.getTelephone(),slotsToCheck.getVisitorID());
		Message msg = new Message(SlotsFromDB,Commands.CheckSixSlots );	
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

    /**
     * Cancels non-paid bookings and sends confirmation to the client.
     * @param nonPayedBookToCancel The ID of the non-paid booking to cancel
     */
	public void CancelNonPayedBook(String nonPayedBookToCancel) {
		ServerUI.sv.dbController.CancelNonPayedBookingFromDB(nonPayedBookToCancel);
		Message msg = new Message(null,Commands.CancelNonPayedBooking );
		
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Retrieves the details of a specific booking and sends them to the client.
	 * @param bookingDet The details of the booking to retrieve
	 */
	public void bookingDetails(BookingDetail bookingDet) {
		BookingDetail bd = ServerUI.sv.dbController.getBookingDetails(bookingDet);
		Message msg = new Message(bd,Commands.BookingDetails );
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Changes the payment status of a booking and sends confirmation to the client.
	 * @param paymentStatusmsg The details of the booking including the payment status change
	 */
	public void ChangePaymentStatus(BookingDetail paymentStatusmsg) {
		ServerUI.sv.dbController.ChangePaymentStatusInDB(paymentStatusmsg.getOrderNumber(),paymentStatusmsg.getPaymentStatus());
		Message StatusChanged = new Message(null, Commands.ChangePaymentStatusInDB) ;
		try {
			client.sendToClient(StatusChanged);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	/**
	 * Checks if a visitor needs to be alerted and sends the result to the client.
	 * @param visitorIDAlert The details of the visitor to check for alerts
	 */
	public void CheckIfVisitorIsAlert(BookingDetail visitorIDAlert) {
		boolean NeedAlert = ServerUI.sv.dbController.CheckIfNeedToBeAlert(visitorIDAlert.getVisitorID());
		Message msg = new Message(NeedAlert,Commands.CheckVisitorAlerts  );
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Retrieves a list of alerted bookings for a specific user ID and sends them to the client.
	 * @param alertedBookingListID The login details containing the user ID
	 */
	public void AlertedBookList(LoginDetail alertedBookingListID) {
		ArrayList<BookingDetail> list = ServerUI.sv.dbController.AlrtedbookingList(alertedBookingListID.getId());
		Message msg = new Message(list,Commands.AlertBooks  );
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Confirms an alerted booking order and sends confirmation status to the client.
	 * @param alertOrderIDToConfirm The details of the booking to confirm
	 */
	public void AlertOrderConfirmation(BookingDetail alertOrderIDToConfirm) {
		boolean IsConfirmable = ServerUI.sv.dbController.AlertOrderConfirmationInDB(alertOrderIDToConfirm.getOrderNumber());
		Message msg = new Message(IsConfirmable, Commands.AlertConfirmInDB);
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Cancels an alerted booking order and sends confirmation to the client.
	 * @param alertOrderIDToCancel The details of the booking to cancel
	 */
	public void AlertOrderCancelation(BookingDetail alertOrderIDToCancel) {
		ServerUI.sv.dbController.cancelOrderFromDB(alertOrderIDToCancel.getOrderNumber());
		Message msg = new Message(null, Commands.AlertCancelInDB);
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * Retrieves the occasional booking number from the database and sends it to the client.
	 */
	public void occasionalBookingNumber() {
		String st = ServerUI.sv.dbController.occasionalBookingNumber();
		Message msg = new Message(st, Commands.OccasionalBookingNumber) ;
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	
	
//	public void createNewBooking() {
//		
//		//1:get data from user
//		//2:prase the data
//		//3:place it in the correct table using dbctrl
//		//4:return to the user the result
//	}
	
	
	
	
}
