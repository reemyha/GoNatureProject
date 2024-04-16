 package logic;

import java.io.Serializable;
import java.time.LocalDate;
/**
 * Represents the details of a booking.
 */
public class BookingDetail implements Serializable {

	private String orderNumber;
    private String visitType;
    private String time;
    private String parkName;
    private String numOfVisitors;
    private String telephone;
    private String email;
    private String Date;
    private String visitorID;
    private String TableName;
    private int VisitDuration;
    private String PaymentStatus;
    private String Status;
    private String visitorType;

    
    public String getVisitorType() {
		return visitorType;
	}

	public void setVisitorType(String visitorType) {
		this.visitorType = visitorType;
	}

    
    public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		this.Status = status;
	}

	public String getPaymentStatus() {
		return PaymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.PaymentStatus = paymentStatus;
	}

	public int getVisitDuration() {
		return VisitDuration;
	}

	public void setVisitDuration(int visitDuration) {
		this.VisitDuration = visitDuration;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getNumOfVisitors() {
        return numOfVisitors;
    }

    public void setNumOfVisitors(String numOfVisitors) {
        this.numOfVisitors = numOfVisitors;
    }
    
    public String getNumOfVisitorsG() {
        return numOfVisitors;
    }

    public void setNumOfVisitorsG(String numOfVisitors) {
        this.numOfVisitors = numOfVisitors;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }
    public void setVisitorID(String visitorID)
    {
    	this.visitorID = visitorID;
    }
    
    public String getVisitorID()
    {
    	return visitorID;
    }
    
    public void setTableName(String TableName)
    {
    	this.TableName = TableName;
    }
    
    public String getTableName()
    {
    	return TableName;
    }
    

    @Override
    public String toString() {
        return "BookingDetails [TableName="+ TableName +" visitType=" + visitType + ", visitorID=" + visitorID + ", parkName=" + parkName
                + ", numOfVisitors=" + numOfVisitors + ", telephone=" + telephone + ", email=" + email
                + ", visitDate=" + Date + "]";
    }

	
}
