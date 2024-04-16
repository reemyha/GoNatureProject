package client;

import java.io.IOException;
import java.time.LocalDate;

import controller.*;
import enums.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ocsf.client.AbstractClient;
import logic.BookingDetail;
import logic.CancellationData;
import logic.CancellationDetail;
import logic.ManagerRequestDetail;
import logic.Message;
import logic.ReportDetail;
import logic.WorkerDetail;

public class Client extends AbstractClient
{
	  //Instance variables **********************************************
	  
	  /**
	   * The interface type variable.  It allows the implementation of 
	   * the display method in the client.
	   */
	  ClientController clientUI; 

	  
	  //Constructors ****************************************************
	  
	  /**
	   * Constructs an instance of the chat client.
	   *
	   * @param host The server to connect to.
	   * @param port The port number to connect on.
	   * @param clientUI The interface type variable.
	   */
	  
	  static public WorkerController workerController;
	  static public MainScreenController mainScreenController;
	  static public BookingController bookingController;
	  static public ReportController reportController;
	  
	  public Client(String host, int port, ClientController clientUI) 
	    throws IOException 
	  {
	    super(host, port); //Call the superclass constructor
	    this.clientUI = clientUI;
	    System.out.println("Connecting...");
	    openConnection();
	    
	    //Initilazing The Contorllers
	    
	    bookingController = new BookingController();
	    mainScreenController = new MainScreenController();
	    workerController = new WorkerController();
	    reportController = new ReportController();
	    //to be continued if needed
	  }

	  
	  //Instance methods ************************************************
	    
	  /**
	   * This method handles all data that comes in from the server.
	   *
	   * @param msg The message from the server.
	   */
	  public void handleMessageFromServer(Object msg) 
	  {
		  //get the message a message object from the server (getcmd,getobj) while obj is the data from the server  
	      Message m = (Message)msg;

	      switch(m.getCmd()) {
	      
	      	case WorkerLoginResult:
	      		WorkerDetail workerLoginResult =  (WorkerDetail)m.getObj();
	      		mainScreenController.setWorkerLoginValid(workerLoginResult);
	      		break;
	      		
	      	case VisitorLoginResult:
	      		Boolean visitorLoginResult =  (Boolean)m.getObj();
	      		mainScreenController.setVisitorLoginValid(visitorLoginResult);
	      		break;
	      
	      	case VisitorMyBooking:
	      		ArrayList<BookingDetail> myBookingList = (ArrayList<BookingDetail>)m.getObj();
	      		bookingController.setMyBookingTableList(myBookingList);
	      		break;
	      		
	      	case CheckIfGroupGuide:
	      		Boolean groupguideLoginResult =  (Boolean)m.getObj();
	      		mainScreenController.setGroupGuideValid(groupguideLoginResult);
	      		break;
	      		
	      	case CancelBooking:
	      		Boolean cancelBookingResult =  (Boolean)m.getObj();
	      		bookingController.setCancelBooking(cancelBookingResult);
	      		break;

	      	case AverageParkStayTimeCheck:
	      		Boolean averageParkStayTimeResult =  (Boolean)m.getObj();
	      		workerController.setAverageParkStayTime(averageParkStayTimeResult);
	      		break;
	      		
	      	case OnlineBookingCapacityCheck:
	      		Boolean OnlineBookingCapacityResult =  (Boolean)m.getObj();
	      		workerController.setOnlineBookingCapacity(OnlineBookingCapacityResult);
	      		break;
	      		
	      	case ParkCapacityCheck:
	      		Boolean ParkCapacityResult =  (Boolean)m.getObj();
	      		workerController.setParkCapacity(ParkCapacityResult);
	      		break;
	      		
	      	case CheckParkCapacity:
	      		boolean bookingAvailable = (Boolean)m.getObj();
	      		bookingController.setCheckIfBookingAvailable(bookingAvailable);
	      		break;
	      	
	      	case checkIfExist:
	      		String checkexistig = (String)m.getObj();
	      		bookingController.setCheckIfExistBooking(checkexistig);
	      		break;
	      		
	      	case visitorReportData:
	      		Map<String, int[]> VisitorReportData = (Map<String, int[]>)m.getObj();
	      		reportController.setvisitorReportData(VisitorReportData);
	      		break;
	      		
	      		
	      		
	      	case visitorStatisticData:
	      		Map<LocalDate, int[]> visitorStatisticData = (Map<LocalDate, int[]>)m.getObj();
	      		reportController.setvisitorStatisticData(visitorStatisticData);
	      		break;
	      		
	      	case CancellationReportData:
	      		 CancellationData CancellationReportData = (CancellationData)m.getObj();
	      		 reportController.setCancellationReportData(CancellationReportData.getCancellations());
	      		 reportController.setDayCount(CancellationReportData.getDayCount());
	      		break;
	      		

	      	case CheckSixSlots:
	      		ArrayList<String> SixSlotsFromDB = (ArrayList<String>)m.getObj();
	      		bookingController.setSixSlots(SixSlotsFromDB);
	      		break;
	      		

	      	case CancelNonPayedBooking:
	      		bookingController.setCancelNonPayedBook();
	      		break;
	      		

	      	case vistorAmountData:
	      		int vistorAmountData = (int)m.getObj();
	      		workerController.setVistorAmountData(vistorAmountData);
	      		break;
	      	case RequestTableData:
	      		ArrayList<ManagerRequestDetail> requestList = (ArrayList<ManagerRequestDetail>)m.getObj();
	      		workerController.setRequestList(requestList);
	      		System.out.println(requestList.size() + "client");
	      		break;
	      	case RequestRemoved:
	      		workerController.setGotResponse(true);
	      		break;
	      	case CurrentOccupancy:
	      		String CurrOcc = (String)m.getObj();
	      		workerController.setCurrentOccupancy(CurrOcc);
	      		break;

	  		  
	  	  case MaxOccupancy:
	  		  	String MaxOcc = (String)m.getObj();
	  		  	workerController.setMaxOccupancy(MaxOcc);
	      		break;
	      		
	  	  case BookingDetails:
	  		  	BookingDetail bd = (BookingDetail)m.getObj();
	  		  	bookingController.setBookingDetails(bd);
	  		  	break;

	  		  	
	  	  case ChangePaymentStatusInDB:
	  		  bookingController.setPaymentStatus();
	  		  break;	      		

	  	  case AddReportCheck:
	  		  boolean reportCheck = (boolean)m.getObj();
	  		  reportController.setAddReportCheck(reportCheck);
	  		  break;
	  		  
	  	  case SetReportList:
	  		ArrayList<ReportDetail> ReportList = (ArrayList<ReportDetail>)m.getObj();
	  		reportController.setReportList(ReportList);
	  		  break;
	  		  
	  	  case CheckVisitorAlerts:
	  		  boolean AlertedVisitor = (boolean)m.getObj();
	  		  bookingController.setIsVisitorAlerted(AlertedVisitor);
	  		  break;
	  		  
	  	  case AlertBooks:
	  		ArrayList<BookingDetail> AlertedBookingList = (ArrayList<BookingDetail>)m.getObj();
      		bookingController.setMyBookingTableList(AlertedBookingList);
      		break;
      		
	  	  case AlertConfirmInDB:
	  		  boolean IsAlertConfirmed = (boolean)m.getObj();
	  		  bookingController.setAlertedConfirmStatus(IsAlertConfirmed);
	  		  break;
	  	  case AlertCancelInDB:
	  		bookingController.setAlertedCancelStatus();	  		  
	  		  break;

	  		  
	  	  case EnterPark:
	  		  workerController.setEnterPark();
	  		  break;
	      	
	  	  case ExitPark:
	  		workerController.setExitPark();
	  		  break;

	  	  case statReportData:
	  		HashMap<String,Integer> statReportData = (HashMap<String,Integer>)m.getObj();
	  		reportController.setVisitorStatData(statReportData);
	  		break;
	  	  case terminate:
	  	    // Exit the application
      		Message newMsg = new Message(null, Commands.ClientDisconnect);
      		try {
				sendToServer(newMsg);
				this.closeConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
	         System.exit(0);
	  		 break;

//	  	  case statReportData:
//	  		HashMap<String,Integer> statReportData = (HashMap<String,Integer>)m.getObj();
//	  		workerController.setstatReportData(statReportData);
//	  		break;

	  	  case ParkNames:
	  		mainScreenController.setParkNames((ArrayList<String>)m.getObj());
	  		break;
	  		  
	  	  case OccasionalBookingNumber:
	  		  String  st =(String)m.getObj();
	  		  bookingController.setOccasioanlBookingNumber(st);
;	  		  break;

	  	  case CheckBookInDB:
	  		  int result = (int)m.getObj();
	  		  workerController.setCheckBookInDB(result);
	  		  break;
	  		  
		default:
			break;


	      		
	      
	      }
		  
		  
			/*	
			 * 
			 * 
			 * 		 
			 * 
				//TODO: Add Switch case for all msgs from server.
			 */
	  }

	  
	  /**
	   * This method handles all data coming from the UI            
	   *
	   * @param message The message from the UI.    
	   */
	  public void handleMessageFromClientUI(String[] message)  
	  {

	    try
	    {
	    	System.out.println("sendtoserver");
	    	sendToServer(message);
	    	
	    }
	    catch(IOException e)
	    {
	      clientUI.display
	        ("Could not send message to server.  Terminating client.");
	      quit();
	    }
	  }

	  
	  /**
	   * This method terminates the client.
	   */
	  public void quit()
	  {
	    try
	    {
//	      Message msg = new Message(null,Commands.ClientConnect);
//	      ClientController.client.sendToServer(msg);

	      closeConnection();
	    }
	    catch(IOException e) {}
	    System.exit(0);
	  }

}
