package logic;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * Represents details of a manager request.
 */
public class ManagerRequestDetail implements Serializable{
	

	private String parkName;
	private String changeTo;
	private String amountTo;
    private int requestNumber;
    private String changes;



       
    
    
	public int getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(int requestNumber) {
		this.requestNumber = requestNumber;
	}
	public String getChanges() {
		return changes;
	}


	
	
	public ManagerRequestDetail(String parkName, String changeTo, String amountTo) {

		this.parkName = parkName;
		this.changeTo = changeTo;
		this.amountTo = amountTo;
		this.changes= createString();
	}

	
	public String createString() {
		return changeTo + parkName + " To "+ amountTo +"." ;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getChangeTo() {
		return changeTo;
	}
	public void setChangeTo(String changeTo) {
		this.changeTo = changeTo;
	}
	public String getAmountTo() {
		return amountTo;
	}
	public void setAmountTo(String amountTo) {
		this.amountTo = amountTo;
	}
	public void setChanges(String changes) {
		this.changes = changes;
	}

	
	
	
}
