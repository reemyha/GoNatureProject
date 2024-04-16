package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import com.mysql.cj.xdevapi.Statement;

import enums.Commands;
import logic.LoginDetail;
import logic.Message;
import logic.WorkerDetail;
import ocsf.server.ConnectionToClient;
import server.EchoServer;
import server.ServerUI;
/**
 * Controller class responsible for handling login-related operations.
 */
public class LoginController {
	
	
	private LoginDetail loginDetails;
	private ConnectionToClient client;
	
	//private String id , username , password ;
    /**
     * Constructs a new LoginController with the given login details and client connection.
     * @param loginDetails The login details to process
     * @param client The connection to the client
     */
	public LoginController(LoginDetail loginDetails, ConnectionToClient client) {
		
		this.loginDetails = loginDetails;
		this.client = client;
	}
	
    /**
     * Constructs a new LoginController with the given client connection.
     * @param client The connection to the client
     */
	public LoginController(ConnectionToClient client) {
		this.client = client;	
		}

	/**
     * Checks the login credentials of a worker and sends the result to the client.
     */
	public void CheckWorkerLogin() {
		
		String username = loginDetails.getUsername();
		String password = loginDetails.getPassword();
		WorkerDetail workerDetail = ServerUI.sv.dbController.checkWorkerInDB(password, username);
		System.out.println(workerDetail.getRole());
		Message msg = new Message(workerDetail,Commands.WorkerLoginResult);
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 /**
     * Checks if a visitor login is valid and sends the result to the client.
     */
	public void CheckVisitorLogin() {
		 String ID = loginDetails.getId();
		 if(!ServerUI.sv.dbController.checkVisitorInDB(ID))
		 {
			 Message msg = new Message(false,Commands.VisitorLoginResult  );
			 try {
					client.sendToClient(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
		 }
		 else {
				Message msg = new Message(true,Commands.VisitorLoginResult  );
				try {
					client.sendToClient(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}
	
	/**
     * Checks if a user is a group guide and sends the result to the client.
     */
	public void CheckGroupGuide() 
	{
		 String ID = loginDetails.getId();
		 if(!ServerUI.sv.dbController.checkGroupGuideInDB(ID))
		 {
			 Message msg = new Message(false,Commands.CheckIfGroupGuide  );
			 try {
					client.sendToClient(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
		 }
		 else {
				Message msg = new Message(true,Commands.CheckIfGroupGuide  );
				try {
					client.sendToClient(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}
	
	public void getParkNames()
	{
		try {

		ArrayList<String> queryRes = ServerUI.sv.dbController.getParkNames();
		Message msg = new Message(queryRes,Commands.ParkNames);
		client.sendToClient(msg);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	 /**
     * Logs out a visitor.
     */
	public void VisitorLogout() {
		String ID = loginDetails.getId();
		ServerUI.sv.dbController.visitorLogout(ID);
	}
	
	 /**
     * Logs out a worker.
     * @param logoutID_w The ID of the worker to log out
     */
	public void WorkerLogout(int logoutID_w) {
		ServerUI.sv.dbController.workerLogout(logoutID_w);
	}

}
