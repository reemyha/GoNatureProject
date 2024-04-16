package controller;
import java.io.IOException;
import java.util.ArrayList;

import client.ClientController;
import enums.Commands;
import logic.Message;
import logic.WorkerDetail;
/**
 * The MainScreenController class manages login validations and responses for different types of users.
 */
public class MainScreenController {
	

	private WorkerDetail workerLoginValid;
	private boolean visitorLoginValid;
	private boolean GroupGuideLoginValid;
	private boolean gotResponse = true;
	private ArrayList<String> parkNames;
	
	public void setGotResponse(boolean gotResponse) {
		this.gotResponse = gotResponse;
	}
	
	public WorkerDetail getWorkerLoginValid() {
		return workerLoginValid;
	}

	public boolean isGotResponse() {
		return gotResponse;
	}

	public void setWorkerLoginValid(WorkerDetail workerLoginValid) {
		this.workerLoginValid = workerLoginValid;
		this.gotResponse  = false;
	}
	
	
	public boolean isVisitorLoginValid() {
		return visitorLoginValid;
	}
	
	public void setVisitorLoginValid(boolean visitorLoginValid) {
		this.visitorLoginValid = visitorLoginValid;
		this.gotResponse  = false;
	}
	
	
	
	public boolean isGroupGuideLoginValid() {
		return GroupGuideLoginValid;
	}
	
	public void setGroupGuideValid(boolean GroupGuideLoginValide) {
		this.GroupGuideLoginValid = GroupGuideLoginValide;
		this.gotResponse  = false;
	}
	
	public void getParkNames()
	{
		Message msg = new Message(null,Commands.ParkNames);
		try {
			ClientController.client.sendToServer(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setParkNames(ArrayList<String> parkNames)
	{
		this.parkNames = parkNames;
		this.gotResponse  = false;

	}
	
	public ArrayList<String> allParkNames() {
		return parkNames;
	}
	
	
	
	
	

}
