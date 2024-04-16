package screenController;



import java.util.ArrayList;

import client.Client;
import client.ClientController;
import enums.Commands;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.BookingDetail;
import logic.LoginDetail;
import logic.Message;

public class PaymentController extends VisitorScreenController 
{

	private StringBuilder errorMessage = new StringBuilder();
	@FXML
	private Text totalPrice;
	@FXML
	private Text priceBeforeDiscount;
	@FXML
	private Text errorT;
	@FXML
	private ComboBox<String> paymentCombo;
	@FXML
	private Button cancel;


	private BookingDetail details;
	
	public int fullpricetoshow,discountPrice;
	
	private void setComboBox() 
	{
		ArrayList<String> payment = new ArrayList<String>();
		payment.add("Pay at visit");
		payment.add("Pay now");
		ObservableList<String> list1 = FXCollections.observableArrayList(payment);
		paymentCombo.setItems(list1);

	}
	
	/**
	 * Calculates and displays the price for the booking based on the number of visitors, visit type, and payment option selected.
	 * Updates the total price and price before discount labels accordingly.
	 */
	public void price() {
	    // Calculate the full price and initialize discount price
	    fullpricetoshow = 100 * Integer.parseInt(details.getNumOfVisitors());

	    // Check visit type
	    if ("Guided".equals(details.getVisitType())) {
	        // Apply discount for guided visit
	        fullpricetoshow -= 100; // Subtract 100₪ for guided visit
	        discountPrice = (int) (fullpricetoshow * 0.75); // Apply 25% discount

	        // Apply additional discount if payment is "Pay now"
	        if ("Pay now".equals(paymentCombo.getValue())) {
	            discountPrice = (int) (discountPrice * 0.88); // Apply 12% additional discount
	        }
	    } else {
	        // Apply discount for non-guided visit
	        discountPrice = (int) (0.85 * fullpricetoshow); // Apply 15% discount
	    }

	    // Display the total price after discounts
	    totalPrice.setText(String.valueOf(discountPrice) + "₪");

	    // Display the full price before discounts
	    priceBeforeDiscount.setText(String.valueOf(fullpricetoshow) + "₪");
	}

	
	 @FXML
	    void aprvConfirmBtn(ActionEvent event) throws Exception {
		 ((Node)event.getSource()).getScene().getWindow().hide();
	        ReceiptScreenController newScreen = new ReceiptScreenController();
	        newScreen.start(new Stage(), details, discountPrice);

	    }

	    @FXML
	    void aprvcancelBtn(ActionEvent event) {

	    }

	    /**
	     * Handles the action event when the Finish button is clicked to finalize the payment process.
	     *
	     * @param event The action event triggered by clicking the Finish button.
	     * @throws Exception If an error occurs while processing the payment, sending payment status to the server, or setting up confirmation dialogs.
	     */
	    public void finishBtn(ActionEvent event) throws Exception {
	        boolean awaitResponse = false;
	        // Check if payment option is selected
	        if (paymentCombo.getValue() == null) {
	            // Display error message if payment option is not selected
	            errorT.setVisible(true);
	        } else if (paymentCombo.getValue().equals("Pay at visit")) {
	            // Payment at visit
	            details.setPaymentStatus("NO");
	            // Send payment status to the server
	            Message PaymentStatusToDB = new Message(details, Commands.ChangePaymentStatusInDB);
	            ClientController.client.sendToServer(PaymentStatusToDB);
	            
	            // Wait for response from the server
	            while (!awaitResponse) {
	                try {
	                    Thread.sleep(100);
	                    awaitResponse = ClientController.client.bookingController.isGotResponse();
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	            
	            // Load payment confirmation dialog
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/payatparkconfirm.fxml"));
	            loader.setController(this);
	            Parent root = loader.load();
	            Scene scene = new Scene(root);
	            Stage primaryStage = new Stage();
	            primaryStage.setTitle("Confirmation Message");
	            primaryStage.setScene(scene);
	            primaryStage.initModality(Modality.APPLICATION_MODAL); // blocks all actions until user presses yes/no
	            primaryStage.initStyle(StageStyle.UNDECORATED); // removes top bar
	            primaryStage.show();
	            
	            // Close the current window
	            ((Node) event.getSource()).getScene().getWindow().hide();
	        } else {
	            // Pay now
	            details.setPaymentStatus("YES");
	            // Send payment status to the server
	            Message PaymentStatusToDB = new Message(details, Commands.ChangePaymentStatusInDB);
	            ClientController.client.sendToServer(PaymentStatusToDB);
	            
	            // Wait for response from the server
	            while (!awaitResponse) {
	                try {
	                    Thread.sleep(100);
	                    awaitResponse = ClientController.client.bookingController.isGotResponse();
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	            
	            // Load payment approval dialog
	            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/gui/PaymentApprove.fxml"));
	            loader1.setController(this);
	            Parent root1 = loader1.load();
	            Scene scene1 = new Scene(root1);
	            Stage primaryStage = new Stage();
	            primaryStage.setTitle("Confirmation Message");
	            primaryStage.setScene(scene1);
	            primaryStage.initModality(Modality.APPLICATION_MODAL); // blocks all actions until user presses yes/no
	            primaryStage.initStyle(StageStyle.UNDECORATED); // removes top bar
	            primaryStage.show();
	            
	            // Close the current window
	            ((Node) event.getSource()).getScene().getWindow().hide();
	            
	            // TODO: Add the booking to SQL.
	        }
	    }

	
	
	public void okBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		MyBookingController newScreen = new MyBookingController();
		newScreen.start(new Stage());
	}
	
	
	/**
	 * Handles the action event when the Cancel button is clicked to cancel a non-paid booking.
	 *
	 * @param event The action event triggered by clicking the Cancel button.
	 * @throws Exception If an error occurs while sending cancellation message to the server or setting up the NewBooking screen.
	 */
	public void cancelBtn(ActionEvent event) throws Exception {
	    // Create a message to cancel the non-paid booking with the order number
	    Message CancelNonPayed = new Message(details.getOrderNumber(), Commands.CancelNonPayedBooking);

	    // Send the cancellation message to the server
	    ClientController.client.sendToServer(CancelNonPayed);

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

	    // Open the NewBooking screen in a new window
	    NewBookingController newScreen = new NewBookingController();
	    newScreen.start(new Stage());
	}

	
	/**
	 * Initializes and displays the Payment GUI.
	 *
	 * @param primaryStage The primary stage for the Payment GUI.
	 * @throws Exception If an error occurs while loading the FXML file, setting up the GUI, or retrieving new booking details.
	 */
	public void start(Stage primaryStage) throws Exception 
	{

		this.details=ClientController.client.bookingController.getNewBooking();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Payment.fxml"));
    	loader.setController(this); 
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("Payment");
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
    	setComboBox();
    	errorT.setVisible(false);
    	
    	 // Define action event for ComboBox selection
    	 paymentCombo.setOnAction(event -> {
    	        String selectedPayment = paymentCombo.getSelectionModel().getSelectedItem();
    	        if (selectedPayment != null) {
    	        	errorT.setVisible(false);
    	            if (selectedPayment.equals("Pay at visit")) {
    	               price();
    	            } else if (selectedPayment.equals("Pay now")) {
    	               price();
    	            }
    	        }
    	    });
    	
    	
    } 

}