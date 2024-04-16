package screenController;



import java.io.IOException;

import java.io.IOException;
import java.util.ArrayList;

import client.ClientController;
import enums.Commands;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.BookingDetail;
import logic.LoginDetail;
import logic.Message;

public class WaitinigorRescheduleController extends VisitorScreenController
{
    @FXML
    private Button enter;


		@FXML
		private TableView<String> dateTime;

		@FXML
		private TableColumn<String, String> pointsColumn_tableExam;
		
		@FXML
		private Button ok;
 

		private BookingDetail details;
		
		private String NewDateAndTime;

	    @FXML
	    
	    ArrayList<String> AvailableSlots;
	    /**
	     * Handles the action event when the Enter button is clicked to enter the waiting list for booking.
	     *
	     * @param event The action event triggered by clicking the Enter button.
	     * @throws Exception If an error occurs while setting up the new booking or sending it to the server.
	     */
	    public void enterBtn(ActionEvent event) throws Exception {
	        // Set the appropriate table for the waiting list
	        details.setTableName("waitinglist");

	        // Send the booking details with the new table information to the server
	        Message newBookingMessage = new Message(details, Commands.newBookingToDB);
	        ClientController.client.sendToServer(newBookingMessage);

	        // Wait for response from the server
	        boolean awaitResponse = false;
	        while (!awaitResponse) {
	            try {
	                Thread.sleep(100);
	                awaitResponse = ClientController.client.bookingController.isGotResponse();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }

	        // Hide the current window
	        ((Node) event.getSource()).getScene().getWindow().hide();

	        // Pop up the confirmation waiting list screen
	        PopUpWaitinglist newScreen = new PopUpWaitinglist();
	        newScreen.start(new Stage());
	    }

	    

	    @FXML
	    void backbtn(ActionEvent event) throws Exception {
	    	((Node)event.getSource()).getScene().getWindow().hide();
	    	NewBookingController newScreen = new NewBookingController();
			newScreen.start(new Stage());

	    }

	    /**
	     * Handles the action event when the OK button is clicked to confirm an alternative date for booking.
	     *
	     * @param event The action event triggered by clicking the OK button.
	     * @throws Exception If an error occurs while setting up the new booking or sending it to the server.
	     */
	    @FXML
	    public void okBtn(ActionEvent event) throws Exception {
	        // Set the new date and time for the booking
	        details.setDate(NewDateAndTime);
	        details.setTableName("booking");

	        // Set the new booking details in the booking controller
	        ClientController.client.bookingController.setNewBooking(details);

	        // Create a message to send to the server with the new booking details
	        Message newBookingMessage = new Message(details, Commands.newBookingToDB);

	        // Send the new booking message to the server
	        ClientController.client.sendToServer(newBookingMessage);

	        // Wait for response from the server
	        boolean awaitResponse = false;
	        while (!awaitResponse) {
	            try {
	                Thread.sleep(100);
	                awaitResponse = ClientController.client.bookingController.isGotResponse();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }

	        // Once response received, set flag to indicate response received
	        ClientController.client.bookingController.setGotResponse();

	        // Hide the current window
	        ((Node) event.getSource()).getScene().getWindow().hide();

	        // Open the payment controller in a new window
	        PaymentController newScreen = new PaymentController();
	        newScreen.start(new Stage());
	    }

	    	
	    
	    
	    
	   
	    
	    public void start(Stage primaryStage) throws Exception 
		{
	    	ArrayList<String> availableBooks[];
	    	
	    	this.details=ClientController.client.bookingController.getNewBooking();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Waitinglist_Reschedule.fxml"));
	    	loader.setController(this); 
	    	Parent root = loader.load();
	    	Scene scene = new Scene(root);
	    	primaryStage.setTitle("Waitinglist_Reschedule");
	    	primaryStage.setScene(scene);
	    	RemoveTopBar(primaryStage,root);
	    	primaryStage.show();
	    	CheckavailableSlotinDB();
	    	dateTime.setOnMouseClicked(event -> {
	    		if (AvailableSlots.size()>1) {
	    			ok.setDisable(false);
	    			NewDateAndTime = dateTime.getSelectionModel().getSelectedItem().toString();
	    	    } 
	    	});
	    	//if pressed outside of the tableview, the selection is cleared and cancel button is disabled
	    	scene.setOnMousePressed(event -> {
	            if (!dateTime.getBoundsInParent().contains(event.getX(), event.getY())) {
	            	dateTime.getSelectionModel().clearSelection();
	                ok.setDisable(true);
	            }
	        });
	    	
		}
	    
	    /**
	     * Checks the availability of slots in the database and updates the UI accordingly.
	     *
	     * @throws IOException If an I/O error occurs while sending or receiving data from the server.
	     */
	    public void CheckavailableSlotinDB() throws IOException {
	        // Set cell value factory for the table column
	        pointsColumn_tableExam.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

	        // Create a message to send to the server with details and command
	        Message CheckSlot = new Message(details, Commands.CheckSixSlots);

	        // Send the message to the server
	        ClientController.client.sendToServer(CheckSlot);

	        // Wait for response from the server
	        boolean awaitResponse = false;
	        while (!awaitResponse) {
	            try {
	                Thread.sleep(100);
	                awaitResponse = ClientController.client.bookingController.isGotResponse();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	        
	        // Once response received, set flag to indicate response received
	        ClientController.client.bookingController.setGotResponse();

	        // Retrieve available slots from the booking controller
	        AvailableSlots = ClientController.client.bookingController.getSixSlots();

	        // Check if available slots exist
	        if (AvailableSlots.size() < 1) {
	            // Display "Too many visitors" message in the TableView
	            dateTime.getItems().clear(); // Clear previous data
	            dateTime.setItems(FXCollections.observableArrayList("You try to book more visitors than park capacity")); // Display message
	            enter.setDisable(true); // Disable the 'enter' button
	        } else {
	            // Convert ArrayList to ObservableList and display in the TableView
	            ObservableList<String> observableSlots = FXCollections.observableArrayList(AvailableSlots);
	            dateTime.getItems().clear(); // Clear previous data
	            dateTime.setItems(observableSlots); // Set the items directly to the TableView
	        }
	    }


}
