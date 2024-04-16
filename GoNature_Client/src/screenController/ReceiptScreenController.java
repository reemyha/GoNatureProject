package screenController;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.BookingDetail;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;





public class ReceiptScreenController extends VisitorScreenController {
	
	    @FXML
	    private Label id;

	    @FXML
	    private Label email;

	    @FXML
	    private Label phone;

	    @FXML
	    private Label park;

	    @FXML
	    private Label date;

	    @FXML
	    private Label time;

	    @FXML
	    private Label type;

	    @FXML
	    private Label visitors;

	    @FXML
	    private Label totalPrice;
	    
	    private BookingDetail details;
	
	/**
	 * Handles the action event when the OK button is clicked to close the current window and open the MyBooking screen.
	 *
	 * @param event The action event triggered by clicking the OK button.
	 * @throws Exception If an error occurs while setting up the MyBooking screen.
	 */
	public void okBtn(ActionEvent event) throws Exception{
		((Node)event.getSource()).getScene().getWindow().hide();
		MyBookingController newScreen = new MyBookingController();
		newScreen.start(new Stage());
	}
	
	/**
	 * Sets the details on the receipt GUI with the provided price.
	 *
	 * @param price The price to be displayed on the receipt.
	 */
	public void setDetails(int price){
		id.setText(details.getVisitorID().toString());
		email.setText(details.getEmail().toString());
	    phone.setText(details.getTelephone().toString());
	    park.setText(details.getParkName().toString());
	    date.setText(details.getDate().toString());
	    time.setText(details.getTime().toString());
	    type.setText(details.getVisitType().toString());
	    visitors.setText(details.getNumOfVisitors().toString());
	    totalPrice.setText(String.valueOf(price)+"₪");
	    
	}
	
	
	/**
	 * Initializes and displays the Receipt GUI.
	 *
	 * @param primaryStage The primary stage for the Receipt GUI.
	 * @param details      The booking details to be displayed on the receipt.
	 * @param disprice     The discounted price to be displayed on the receipt.
	 * @throws Exception If an error occurs while loading the FXML file or setting up the GUI.
	 */
	public void start(Stage primaryStage,BookingDetail details,int disprice) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Receipt.fxml"));
    	loader.setController(this); // Set the controller
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("Receipt");
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
    	this.details=details;
    	setDetails(disprice);
    	}
}


















