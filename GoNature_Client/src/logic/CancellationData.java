package logic;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Represents cancellation data including a list of cancellation details and an array of day counts.
 */
public class CancellationData implements Serializable{
	private ArrayList<CancellationDetail> Cancellations;
    private int[] dayCount;
    
	public CancellationData(ArrayList<CancellationDetail> cancellations, int[] dayCount) {
		super();
		Cancellations = cancellations;
		this.dayCount = dayCount;
	}
	public ArrayList<CancellationDetail> getCancellations() {
		return Cancellations;
	}
	public int[] getDayCount() {
		return dayCount;
	}
	
}
