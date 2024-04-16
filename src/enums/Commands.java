package enums;

//Server


/*
 * this class has various commands for communication between client and server.
 */


public enum Commands {

	
	ClientConnect,      //command to indicates client connection.
    CheckWorkerLogin,	// command to check the worker log in details
    CheckVisitorLogin ,  // command to check the visitor log in details
    WorkerLoginResult,   // command to communicate worker login result
    VisitorLoginResult,	 // command to communicate visitor login result
    VisitorLogOut,		// command to indicates visitor logout 
    WorkerLogOut,       // command to indicates worker logout 
	
    CheckIfGroupGuide,
    ChangeParkCapacity,ChangeOnlineBookingCapacity,ChangeAverageParkStayTime,
    
    AverageParkStayTimeCheck, OnlineBookingCapacityCheck, ParkCapacityCheck,


    VisitorStatisticRequest, 
    visitorStatisticData,

    


    VisitorMyBooking,
    CancelBooking, 



    newBookingToDB, checkIfExist, CheckParkCapacity,
    CancellationReportRequest,

    CancellationReportData, ClientDisconnect, 
    CheckSixSlots, CancelNonPayedBooking,

    getVisitorAmountInPark, vistorAmountData, AddManagerRequestDetail, getRequestTable, RequestTableData,
    removeRequest, RequestRemoved,

  
    CurrentOccupancy, MaxOccupancy, BookingDetails, 
    SendAproveMessage, 

    
    CheckVisitorAlerts, 
    AlertBooks, AlertConfirmInDB, AlertCancelInDB,


 

    AddReport, AddReportCheck, getReportTable, SetReportList,


     



     visitReportRequest, visitorReportData, GetVisitorStatReport, statReportData,
     
     terminate,

    ChangePaymentStatusInDB, EnterPark, ExitPark, OccasionalBookingNumber, CheckBookInDB, ParkNames
 


     

    ;
}