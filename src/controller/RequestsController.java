package controller;

import java.io.IOException;
import java.util.ArrayList;

import enums.Commands;
import logic.ManagerRequestDetail;
import logic.Message;
import ocsf.server.ConnectionToClient;
import server.ServerUI;
/**
 * Controller class responsible for managing requests made by managers.
 */
public class RequestsController {




    /**
     * Adds a new request to the database based on the provided details.
     * @param requestDetail The details of the request to be added
     */
	public void addRequest(ManagerRequestDetail requestDetail) {
		
		String parkName = requestDetail.getParkName();
		String changeTo = requestDetail.getChangeTo();
		String amountTo = requestDetail.getAmountTo();
		int requestNumber = requestDetail.getRequestNumber();
		String changes = requestDetail.getChanges();
		
		ServerUI.sv.dbController.addRequestToDb(parkName,changeTo,amountTo,requestNumber,changes);
		
		
	}
	
	/**
     * Removes a request from the database based on the specified index.
     * @param requestIndex The index of the request to be removed
     */
	public void removeRequest(int RequestIndex) {
		
		ServerUI.sv.dbController.removeRequestToDb(RequestIndex);
	}
    /**
     * Sends the list of manager requests to the specified client connection.
     * @param client The connection to the client
     */
	public void sendList(ConnectionToClient client) {
		
		ArrayList<ManagerRequestDetail> requestList = ServerUI.sv.dbController.getRequestList();
		
		  Message RequestTableData = new Message(requestList,Commands.RequestTableData);
		  try {
		        System.out.println("Contents of the ArrayList:");
		        for (ManagerRequestDetail element : requestList) {
		            System.out.println(element.getChanges());
		        }
			client.sendToClient(RequestTableData);
		} catch (IOException e) {e.printStackTrace();}

		
	}
}
