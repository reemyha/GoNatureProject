package logic;

import java.io.Serializable;
/**
 * Represents details of a worker including worker ID, role, park name, and name.
 */
public class WorkerDetail implements Serializable{
	
	private int workerId;
	private String role;
	private String parkName;
	private String name;
	

	public WorkerDetail(int workerId,String role) {
		this.workerId = workerId;
		this.role = role;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public int getWorkerId() {
		return workerId;
	}
	public String getRole() {
		return role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "WorkerDetail [workerId=" + workerId + ", role=" + role + ", parkName=" + parkName + ", name=" + name
				+ "]";
	}
	
	

}
