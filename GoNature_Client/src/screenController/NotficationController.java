package screenController;

import java.io.IOException;
import java.util.ArrayList;
import client.ClientController;
import enums.Commands;
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


public class NotficationController extends VisitorScreenController {
	private StringBuilder popupscreen = new StringBuilder();

	
	private String orderID;

    @FXML
    private Button confirm;

    @FXML
    private Button cancel;

    @FXML
    private TableView<BookingDetail> BookingTable;

    @FXML
    private TableColumn<String, BookingDetail> Bookingnumber;

    @FXML
    private TableColumn<String, BookingDetail> ParkName;

    @FXML
    private TableColumn<String, BookingDetail> DateAndTime;

    @FXML
    /**
     * Handles the action event when the Confirm button is clicked to confirm an alert.
     *
     * @param event The action event triggered by clicking the Confirm button.
     * @throws IOException If an error occurs while sending confirmation message to the server or displaying popup screens.
     */
    void ConfirmBtn(ActionEvent event) throws IOException {
        // Create a new BookingDetail object for the alert to confirm
        BookingDetail alertToConfirm = new BookingDetail();
        alertToConfirm.setOrderNumber(orderID);

        // Create a message to confirm the alert in the database
        Message alertToConfirmMsg = new Message(alertToConfirm, Commands.AlertConfirmInDB);

        // Send the confirmation message to the server
        ClientController.client.sendToServer(alertToConfirmMsg);

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

        // Check if the alert confirmation status is true
        if (ClientController.client.bookingController.getAlertedConfirmStatus()) {
            // Display confirmation popup screen
            popupscreen("Confirm");
        } else {
            // Display error popup screen
            popupscreen("Error");
        }

        // Close the current window
        ((Node) event.getSource()).getScene().getWindow().hide();
    }


    @FXML
    void cancelBtn(ActionEvent event) throws IOException {
    	BookingDetail alertToCancel = new BookingDetail();
    	alertToCancel.setOrderNumber(orderID);
    	Message alertToCancelMsg = new Message(alertToCancel, Commands.AlertCancelInDB);
    	ClientController.client.sendToServer(alertToCancelMsg);
    	((Node)event.getSource()).getScene().getWindow().hide();

    }
    
    /**
     * Displays a popup screen based on the specified FXML file.
     *
     * @param toshow The name of the FXML file (without the extension) to be displayed as a popup screen.
     */
    private void popupscreen(String toshow) {
        try {
            // Load the FXML file for the specified popup screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/2Hours" + toshow + ".fxml"));

            // Set the controller for the FXML file
            loader.setController(this);

            // Load the root element from the FXML file
            Parent root = loader.load();

            // Create a new scene with the loaded root element
            Scene scene = new Scene(root);

            // Create a new stage for the popup screen
            Stage primaryStage = new Stage();

            // Set the scene for the popup stage
            primaryStage.setScene(scene);

            // Remove the top bar from the popup stage
            RemoveTopBar(primaryStage, root);

            // Show the popup stage
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any IOException that might occur during loading the FXML file
        }
    }

    
    
	/**
	 * Sets up the TableView with booking details retrieved from the server for the current user.
	 */
	public void setTable() {
	    // Set cell value factories for columns
	    Bookingnumber.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
	    ParkName.setCellValueFactory(new PropertyValueFactory<>("parkName"));
	    DateAndTime.setCellValueFactory(new PropertyValueFactory<>("Date"));

	    // Create a LoginDetail object to retrieve bookings for the current user
	    LoginDetail myBookingList = new LoginDetail(ClientController.client.bookingController.getID());

	    // Create a message to request booking details for the current user
	    Message myBookingListMsg = new Message(myBookingList, Commands.AlertBooks);

	    try {
	        // Send the message to the server
	        ClientController.client.sendToServer(myBookingListMsg);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

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

	    // Retrieve booking details for the current user from the controller
	    ArrayList<BookingDetail> myList = ClientController.client.bookingController.getMyBookingList();

	    // Add booking details to the TableView
	    for (BookingDetail i : myList) {
	        BookingTable.getItems().add(i);
	    }
	}

    
    
    /**
     * Initializes and displays the BookingNearby GUI, allowing the user to view nearby bookings and take actions on them.
     *
     * @param primaryStage The primary stage for the BookingNearby GUI.
     * @throws Exception If an error occurs while loading the FXML file, setting up the GUI, or initializing the table.
     */
    public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/BookingNearby.fxml"));
    	loader.setController(this); // Set the controller
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("AlertToClient");
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
    	setTable();   
    	
    	// Enable cancel button and save orderID when a row in the table is clicked
    	BookingTable.setOnMouseClicked(event -> {
    		if (BookingTable.getSelectionModel().getSelectedItem() != null) {
    	        cancel.setDisable(false);
    	        confirm.setDisable(false);
    	        orderID = BookingTable.getSelectionModel().getSelectedItem().getOrderNumber();
    	    } else {
    	        // If no row is selected, disable the cancel button
    	        cancel.setDisable(true);
    	        }
    		});

    	// Clear selection and disable cancel button when clicked outside of the table
    	scene.setOnMousePressed(event -> {
            if (!BookingTable.getBoundsInParent().contains(event.getX(), event.getY())) {
            	BookingTable.getSelectionModel().clearSelection();
                cancel.setDisable(true);
                confirm.setDisable(true);
            }
        });
    	
	}

}



