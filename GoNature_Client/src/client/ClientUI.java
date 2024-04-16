package client;

import javafx.application.Application;
import javafx.stage.Stage;
import screenController.ConnectionScreenController;

public class ClientUI extends Application { 
	


	public static void main( String args[] ) throws Exception
	   { 
		    launch(args);  //launch application.
	   } // end main
	 
	@Override
	public void start(Stage primaryStage) throws Exception {

		ConnectionScreenController mainScreen = new ConnectionScreenController(); // create MainScreen and start it.		 
		mainScreen.start(primaryStage); 
						 
		 
	}
	
	
	
	
		
}
