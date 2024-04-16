package server;

import javafx.application.Application;
import javafx.stage.Stage;
import ocsf.server.ConnectionToClient;
import screenController.ServerScreenController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

import logic.Message;
import enums.Commands;
import server.EchoServer;


public class ServerUI extends Application {

	//private static Connection conn = null;
	/*
	private static final int DEFAULT_PORT = 5555;
    private static final String DB_URL = "jdbc:mysql://localhost/gonature?serverTimezone=IST";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Aa123456";
    */

    public static EchoServer sv=null ;

    public static boolean gotResponse = false;

	public static void main( String args[] ) throws Exception
	   {   
		 launch(args);
	  } // end main

	@Override
	public void start(Stage primaryStage) throws Exception {

		ServerScreenController aFrame = new ServerScreenController();
		aFrame.start(primaryStage);


	}


	public static void disconnect() {
        if (isServerRunning() == true)
        {
            for (ConnectionToClient client : EchoServer.ClientList) {
        		gotResponse = false;
            	try {
            		Message msg = new Message(null,Commands.terminate);
            		client.sendToClient(msg);
            		while (!gotResponse) {
            				Thread.sleep(100);
            		}
            		client.close();
                } catch (Exception e) {
                    e.printStackTrace(); // Handle the exception as needed
                }
            }
        	try {
        	   sv.stopListening();
        	   sv.close();
           } catch (IOException e) {
                e.printStackTrace();
            }
           EchoServer.ClientList.clear();
           System.out.println("Server Disconnected");
        }
	}



/*
	public static void runServer(String p)
	{
		boolean flag = false;
		 int port = 0; //Port to listen on
	        try
	        {
	        	port = Integer.parseInt(p); //Set port to 5555
	          
	        }
	        catch(Throwable t)
	        {
	        	System.out.println("ERROR - Could not connect!");
	        }
	    	//opening server
	        EchoServer sv = new EchoServer(port);
	        
	        try {
				flag = true;
				sv.listen(); // Start listening for connections	
				
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("ERROR - Could not listen for clients!");
				flag = false;
			}
	}*/

	public static boolean runServer(String p) {
	    boolean flag = false;
	    int port = 0; // Port to listen on

	    try {
	        port = Integer.parseInt(p); // Set port to the specified value

	        // Opening server
	         sv = new EchoServer(port);

	        try {
	            sv.listen(); // Start listening for connections
	            System.out.println("Server is running on port " + port);
	            flag = true;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            System.out.println("ERROR - Could not listen for clients!");
	        }
	    } catch (Throwable t) {
	        System.out.println("ERROR - Could not connect!");
	    }

	    return flag;
	}

	public static boolean isServerRunning() {
		return (sv != null && sv.isListening());
	}


}