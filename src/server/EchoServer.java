
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;
import java.io.*;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import JDBC.DbController;
import controller.WorkerController;
import enums.Commands;
import javafx.scene.control.Alert.AlertType;
import controller.BookingController;
import controller.LoginController;
import controller.PdfController;
import controller.ReminderController;
import controller.RequestsController;
import ocsf.server.*;
import screenController.ServerScreenController;
import logic.*;
/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */

public class EchoServer extends AbstractServer 
{
	//NotifyThread alertThread;	
	
	
	

  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   * 
   */
	static public ArrayList<ConnectionToClient> ClientList;

	
	
  public EchoServer(int port) 
  {
    super(port);
    System.out.println(port);
    ClientList = new ArrayList<ConnectionToClient>();

    //SalertThread = new NotifyThread();


  }
  
  static public DbController dbController;
  private ServerScreenController serverScreenController;
  
  //TO DO ; boolean flag for connection to database 

  //Instance methods ************************************************
  
  public void setDbController(DbController dbController) {
	this.dbController = dbController;
}


/**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   * @param 
   */
  
  
  //TODO :Create Switch case for msgs from user (SHOW,UPDATE,ect...)
  
  public void handleMessageFromClient (Object msg, ConnectionToClient client)
  {
	  System.out.println(msg);

	  Message m = (Message)msg ;
	  
	  switch(m.getCmd()) {
	  
	  case ClientConnect:
		  ClientDetail newClient = new ClientDetail(client.getInetAddress().getCanonicalHostName(),client.getInetAddress().getHostAddress(),true);
		  this.serverScreenController.loadTable(newClient);
		  ClientList.add(client);
		  break;
	  case ClientDisconnect:
		  ClientDetail removedClient = new ClientDetail(client.getInetAddress().getCanonicalHostName(),client.getInetAddress().getHostAddress(),true);
		  this.serverScreenController.updateTable(removedClient);
		  ServerUI.gotResponse = true;

		  break;
		  
	  case CheckVisitorLogin :
		  LoginDetail vObj =  (LoginDetail)m.getObj();
		  LoginController vLoginDetails = new LoginController(vObj,client);
		  vLoginDetails.CheckVisitorLogin();
		  break;
		  	  
	  case WorkerLogOut :
		  int logoutID_w = (int)m.getObj();
		  LoginController WlogoutID = new LoginController(client);
		  WlogoutID.WorkerLogout(logoutID_w);
		  break;
		  	
	  case CheckWorkerLogin :
		  LoginDetail wObj =  (LoginDetail)m.getObj();
		  LoginController wLoginDetails = new LoginController(wObj,client);
		  wLoginDetails.CheckWorkerLogin();
		  break;
		 
	  case VisitorLogOut :
		  LoginDetail logoutID = (LoginDetail)m.getObj();
		  LoginController vlogoutID = new LoginController(logoutID,client);
		  vlogoutID.VisitorLogout();
		  break;
		  
	  case CheckIfGroupGuide :
		  LoginDetail gObj =  (LoginDetail)m.getObj();
		  LoginController gLoginDetails = new LoginController(gObj,client);
		  gLoginDetails.CheckGroupGuide();
		  break;
		  
	  case ChangeParkCapacity:
		  ManagerRequestDetail ParkCapacityObj =  (ManagerRequestDetail)m.getObj();
		  
		  int ParkCapacityInt = Integer.parseInt(ParkCapacityObj.getAmountTo());
		  WorkerController PCworkerController = new WorkerController(ParkCapacityInt,ParkCapacityObj.getParkName(),client);
		  PCworkerController.ChangeParkCapacity();
		  break;
	  
	  case ChangeOnlineBookingCapacity:
		  ManagerRequestDetail onlineCapacityObj =  (ManagerRequestDetail)m.getObj();
		  int onlineCapacityInt = Integer.parseInt(onlineCapacityObj.getAmountTo());
		  WorkerController OBworkerController = new WorkerController(onlineCapacityInt,onlineCapacityObj.getParkName(),client);
		  OBworkerController.ChangeOnlineBookingCapacity();
		  break;
		  
	  case ChangeAverageParkStayTime:
		  ManagerRequestDetail ParkStayTimeObj =  (ManagerRequestDetail)m.getObj();
		  int ParkStayTimeInt = Integer.parseInt(ParkStayTimeObj.getAmountTo());
		  WorkerController PSworkerController = new WorkerController(ParkStayTimeInt,ParkStayTimeObj.getParkName(),client);
		  PSworkerController.ChangeAverageParkStayTime();
		  break;

	  case VisitorStatisticRequest:
		  DateDetail visitorStatisticDateDetail = (DateDetail)m.getObj();
		  WorkerController VSRWorkerController = new WorkerController(client);
		  VSRWorkerController.getVisitorStatistic(visitorStatisticDateDetail);
		  break;
		  
	  case CancellationReportRequest:
		  String CancellationReportDateDetail = (String)m.getObj();
		  WorkerController CRWorkerController = new WorkerController(client);
		  CRWorkerController.getCancellationReport(CancellationReportDateDetail);
		  break;
	  
	  case VisitorMyBooking:
		  LoginDetail myBookingListID = (LoginDetail)m.getObj();
		  BookingController myBookingList = new BookingController(client);
		  myBookingList.myBookingList(myBookingListID);
		  break;
		
	  case CancelBooking:
		  BookingDetail orderID = (BookingDetail)m.getObj();
		  BookingController cancelOrder = new BookingController(client);
		  cancelOrder.cancelOrder(orderID);
		  break;
		  
	  case CheckParkCapacity:
		  //save newbooking as a bookingdetail
		  BookingDetail BookingDetailToCheck = (BookingDetail)m.getObj();		  
		  //create new controller to send the data to db
		  BookingController BookingToCheck = new BookingController(client);		  
		  //call the execute method 
		  BookingToCheck.CheckBooking(BookingDetailToCheck);
		  break;
		  
	  
	  case newBookingToDB:
		  //save newbooking as a bookingdetail
		  BookingDetail newBookingDetail = (BookingDetail)m.getObj();		  
		  //create new controller to send the data to db
		  BookingController newBooking = new BookingController(client);		  
		  //call the execute method 
		  newBooking.AddNewBooking(newBookingDetail);
		  break;
		  

	  case CheckSixSlots:
		  //save newbooking as a bookingdetail
		  BookingDetail SlotsToCheck = (BookingDetail)m.getObj();
     	  //create new controller to send the data to db
		  BookingController Slots = new BookingController(client);
		  //call the execute method 
		  Slots.GetAvailableSlots(SlotsToCheck);
		  break;
		  
	  case CancelNonPayedBooking:
		  String NonPayedBookToCancel = (String)m.getObj();
		  BookingController NonPayed = new BookingController(client);
		  NonPayed.CancelNonPayedBook(NonPayedBookToCancel);
		  break;

	  case getVisitorAmountInPark:
		  String parkName = (String)m.getObj();
		  WorkerController workerController = new WorkerController(client);
		  workerController.getVisitorAmountInPark(parkName);
		  break;
		  
	  case AddManagerRequestDetail:
		  ManagerRequestDetail managerRequestDetail = (ManagerRequestDetail)m.getObj();
		  RequestsController AddrequestsController = new RequestsController();
		  AddrequestsController.addRequest(managerRequestDetail);
		  break;
	  case getRequestTable:
		  RequestsController GetrequestsController = new RequestsController();
		  GetrequestsController.sendList(client);

		  break;
	  case removeRequest:
		  int removeRequest = (int)m.getObj();
		  RequestsController RMrequestsController = new RequestsController();
		  RMrequestsController.removeRequest(removeRequest);
		  Message RequestRemoved = new Message(null,Commands.RequestRemoved);
		  try {
			client.sendToClient(RequestRemoved);
		} catch (IOException e) {e.printStackTrace();}
		  break;
		  
	  case visitReportRequest:
		  DateDetail visitorReportDateDetail = (DateDetail)m.getObj();
		  WorkerController VRWorkerController = new WorkerController(client);
		  VRWorkerController.getVisitorReport(visitorReportDateDetail);
		  break;
		  
	  case CurrentOccupancy:
		  String parkName1 = (String)m.getObj();
		  WorkerController co = new WorkerController(client);
		  co.getCurrOccupancy(parkName1);
		  break;
		  
	  case MaxOccupancy:
		  String parkName2 = (String)m.getObj();
		  WorkerController mo = new WorkerController(client);
		  mo.getMaxOccupancy(parkName2);
		  break;
		  
	  case BookingDetails:
		  BookingDetail bookingDet = (BookingDetail)m.getObj();
		  BookingController bc = new BookingController(client);
		  bc.bookingDetails(bookingDet);
		  break;

		  
	  case ChangePaymentStatusInDB:
		  BookingDetail PaymentStatusmsg = (BookingDetail)m.getObj();
		  BookingController PaymentStatus = new BookingController(client);
		  PaymentStatus.ChangePaymentStatus(PaymentStatusmsg);
		  break;

		 		  	
	  case AddReport:
		  System.out.println("maddison");
		  ReportDetail report = (ReportDetail)m.getObj();
		  PdfController pdfController = new PdfController(client);
		  pdfController.newReport(report);
		  break;
		  
	  case getReportTable:
		  String reportType = (String)m.getObj();
		  PdfController pdfReportController = new PdfController(client);
		  pdfReportController.getReportTable(reportType);
		  break;
		  

	  case CheckVisitorAlerts:
		  BookingDetail visitorIDAlert = (BookingDetail)m.getObj();
		  BookingController visitorIDToAlret = new BookingController(client);
		  visitorIDToAlret.CheckIfVisitorIsAlert(visitorIDAlert);
		  break;

	  case AlertBooks:
		  LoginDetail AlertedBookingListID = (LoginDetail)m.getObj();
		  BookingController AlertedBookingList = new BookingController(client);
		  AlertedBookingList.AlertedBookList(AlertedBookingListID);

		  break;

	  case AlertConfirmInDB:
		  BookingDetail AlertOrderIDToConfirm = (BookingDetail)m.getObj();
		  BookingController AlertOrderIDToConfirmDB = new BookingController(client);
		  AlertOrderIDToConfirmDB.AlertOrderConfirmation(AlertOrderIDToConfirm);
		  break;
	  case AlertCancelInDB:
		  BookingDetail AlertOrderIDToCancel = (BookingDetail)m.getObj();
		  BookingController AlertOrderIDToCancelDB = new BookingController(client);
		  AlertOrderIDToCancelDB.AlertOrderCancelation(AlertOrderIDToCancel);
		  break;
		  

		  
	  case OccasionalBookingNumber:
		  BookingController bookingNumber = new BookingController(client);
		  bookingNumber.occasionalBookingNumber();
		  break;
		  
	  case CheckBookInDB:
		  String st = (String)m.getObj();
		  WorkerController check = new WorkerController(client);
		  check.checkBookInDB(st);
		  break;
		  
	  case GetVisitorStatReport:
		  DateDetail VisitorStatDetail = (DateDetail)m.getObj();
		  WorkerController StatReportController = new WorkerController(client);
		  StatReportController.GetvisitorStatReport(VisitorStatDetail);
		  break;

		  
	  case EnterPark:
		  ParkEntryDetails enterPark = (ParkEntryDetails)m.getObj();
		  WorkerController enter = new WorkerController(client);
		  enter.enterPark(enterPark);
		  break;
		  
	  case ExitPark:
		  ParkEntryDetails exitPark = (ParkEntryDetails)m.getObj();
		  WorkerController exit = new WorkerController(client);
		  exit.exitPark(exitPark);
		  break;
	  case ParkNames:
		  LoginController ParkNames = new LoginController(client);
		  ParkNames.getParkNames();
		  break;

		  //DONT FORGET THE BREAK!!!
	  	default:
	  		System.out.println("Shouldn't have gotten here?!?!?!");
	  		break;  

	  		
	  	

	  }
  
	  
	  /*
	  Switch Case:::
	  For each action requested from the user
	  we will send the Object MSG to the appropriate controller.Function 
	  */
  }
  
  

  public DbController getDbController() {
	return dbController;
}


/**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
 * @return 
 * @return 
 * @return 
   */

  protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
		// Establish a connection to the database when the server starts
		//alertThread.run();

	}


public void setServerScreenController(ServerScreenController serverScreenController) {
	this.serverScreenController = serverScreenController;

}

  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */

//TODO: Fix ServerStopped()   
/*
    protected void serverStopped()  {
	  System.out.println("Server has stopped listening for connections.");
		try {
			conn.close();
		} // close connection to data base
		catch (SQLException e) {
			e.printStackTrace();
		}  
  }
 */


	  
   
}