package logic;

import java.io.Serializable;
/**
 * Represents the details of a cancellation.
 */
public class CancellationDetail implements Serializable {
    
    private String orderNumber;
    private String visitTime;
    private int numVisitors;
    private String visitorID;
       
    public CancellationDetail(String orderNumber, String visitTime, int numVisitors, String visitorID) {
        this.orderNumber = orderNumber;
        this.visitTime = visitTime;
        this.numVisitors = numVisitors;
        this.visitorID = visitorID;
    }
       
    public String getOrderNumber() {
        return orderNumber;
    }
    
    public String getVisitTime() {
        return visitTime;
    }
    
    public int getNumVisitors() {
        return numVisitors;
    }
    
    public String getVisitorID() {
        return visitorID;
    }
}