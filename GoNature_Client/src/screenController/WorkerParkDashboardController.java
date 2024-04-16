package screenController;

import java.io.IOException;

import client.ClientController;
import enums.Commands;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.BookingDetail;
import logic.LoginDetail;
import logic.Message;
import logic.WorkerDetail;
import workerScreenController.WorkerScreenController;

public class WorkerParkDashboardController extends WorkerScreenController {
	
    @FXML
    private Text workerNameT;
    @FXML
    private Text visitorsInParkT;
    @FXML
    private Text orderNumberT;
    @FXML
    private Text parkNameT;
    @FXML
    private Text dateT;
    @FXML
    private Text visitTypeT;
    @FXML
    private Text numOfVisitorsT;
    @FXML
    private Text visitorsT;
    @FXML
    private Text errorT;
    @FXML
    private TextField checkOrderT;
    @FXML
    private Button payment;
    @FXML
    private Button approve;    
    @FXML
    private Button checkOrder;
    @FXML
    private Button occasionalVisitor;
    
    
    
    public void approveBtn(ActionEvent event) throws IOException  {
    	
    }
   
    //checks if order exist in DB. if yes, order information is displayed
    public void checkOrderBtn(ActionEvent event) throws IOException {
    	BookingDetail orderDet = new BookingDetail();
    	orderDet.setOrderNumber(checkOrderT.getText());
    	orderDet.setParkName(ClientController.client.workerController.getWorkerDetail().getParkName());
    	Message checkOrder = new Message(orderDet,Commands.BookingDetails);
    	try {
			ClientController.client.sendToServer(checkOrder);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	boolean awaitResponse = false;
		while (!awaitResponse) 
		{
			try {
				Thread.sleep(100);
				awaitResponse = ClientController.client.bookingController.isGotResponse();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ClientController.client.bookingController.setGotResponse();
		BookingDetail order = ClientController.client.bookingController.getNewBooking();
		if(order != null) {
			errorT.setVisible(false);
			orderNumberT.setText(order.getOrderNumber());
			parkNameT.setText(order.getParkName());
			dateT.setText(order.getDate());
			visitTypeT.setText(order.getVisitType());
			numOfVisitorsT.setText(order.getNumOfVisitors());
		}
		else
		{
			errorT.setVisible(true);
			orderNumberT.setText("");
			parkNameT.setText("");
			dateT.setText("");
			visitTypeT.setText("");
		}
    }
  
    public void occasionalVisitorBtn(ActionEvent event) throws IOException {
    		
    }

    public void paymentBtn(ActionEvent event) throws IOException {

    }
    
    public void setScreen() {
    	workerNameT.setText("Welcome back, " + ClientController.client.workerController.getWorkerDetail().getName());   	
    	visitorsInParkT.setText("Visitors in " + ClientController.client.workerController.getWorkerDetail().getParkName() + ":");   	
    	visitorsT.setText(getCurrentOccupancy()+ "/" + getMaxOccupancy());
    }
    
 // returns currOccupancy of the park
    public String getCurrentOccupancy() {
    	String parkName = ClientController.client.workerController.getWorkerDetail().getParkName();
    	Message currOccupancy = new Message(parkName,Commands.CurrentOccupancy);
    	try {
			ClientController.client.sendToServer(currOccupancy);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	boolean awaitResponse = false;
		while (!awaitResponse) 
		{
			try {
				Thread.sleep(100);
				awaitResponse = ClientController.client.workerController.isGotResponse();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ClientController.client.workerController.setGotResponse(false);
    	return ClientController.client.workerController.getCurrentOccupancy();
    }
    
    // returns maxOccupancy of the park
    public String getMaxOccupancy() {
    	String parkName = ClientController.client.workerController.getWorkerDetail().getParkName();
    	Message maxOccupancy = new Message(parkName,Commands.MaxOccupancy);
    	try {
			ClientController.client.sendToServer(maxOccupancy);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	boolean awaitResponse = false;
		while (!awaitResponse) 
		{
			try {
				Thread.sleep(100);
				awaitResponse = ClientController.client.workerController.isGotResponse();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ClientController.client.workerController.setGotResponse(false);
    	return ClientController.client.workerController.getMaxOccupancy();
    }
    
    public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/WorkerScreens/WorkerParkDashboardScreen.fxml"));
    	loader.setController(this); // Set the controller
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("WorkerScreen");
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
    	setScreen();
    	//disables 'CheckOrder' button whenever the textfield is empty
    	checkOrderT.textProperty().addListener((observable, oldValue, newValue) -> {
            // Check if TextField is empty or not
            if (newValue.trim().isEmpty()) {
                // If TextField is empty, disable the button
            	checkOrder.setDisable(true);
            } else {
                // If TextField has text, enable the button
            	checkOrder.setDisable(false);
            }
        });
	}

}
