package screenController;


import java.io.IOException;
import java.util.ArrayList;

import client.Client;
import client.ClientController;
import client.ClientUI;
import controller.BookingController;
import enums.Commands;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.BookingDetail;
import logic.LoginDetail;
import logic.Message;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.DateCell;
import javafx.util.Callback;
//import server.ServerUI;



public class NewBookingController extends VisitorScreenController {

	private StringBuilder errorMessage = new StringBuilder();
	@FXML
	private ComboBox<String> timeCombo;
	@FXML
	private ComboBox<String> parkNameCombo;
	@FXML
	private ComboBox<String> numOfVisitorsCombo;
	@FXML
	private ComboBox<String> numOfVisitorsGCombo;
	@FXML
	private TextField telephoneT;
	@FXML
	private TextField emailT;
	@FXML
	private TextArea errorT;
	@FXML
	private DatePicker dateCombo;
	@FXML
	private CheckBox guide;
	
	public void getTime() {
		timeCombo.getValue();
	}
	
	public void getParkName() {
		parkNameCombo.getValue();
	}
	
	public void getNumOfVisitors() {
		numOfVisitorsCombo.getValue();
	}
	
	public void getNumOfVisitorsG() {
		numOfVisitorsGCombo.getValue();
	}
	
	public void getTelephone() {
		telephoneT.getText();
	}
	
	public void getEmail() {
		emailT.getText();
	}
	
	private void errorscreen(String toshow) throws IOException
	{
		errorMessage.append(toshow+".\n");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/NotValidInputErrorScreen.fxml"));
		loader.setController(this); // Set the controller
		Parent root = loader.load();
    	Scene scene = new Scene(root);
    	Stage primaryStage = new Stage();
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
    	errorT.setText(errorMessage.toString());
	}
	
	private void configureDatePicker() {
	    // Set the date picker to show only dates that are at least 24 hours ahead
	    dateCombo.setDayCellFactory(new Callback<DatePicker, DateCell>() {
	        @Override
	        public DateCell call(DatePicker param) {
	            return new DateCell() {
	                @Override
	                public void updateItem(LocalDate item, boolean empty) {
	                    super.updateItem(item, empty);
	                    // Disable selection of dates less than 24 hours ahead
	                    LocalDateTime minimumDateTime = LocalDateTime.now().plusHours(24);
	                    LocalDate minimumDate = minimumDateTime.toLocalDate();
	                    setDisable(item.isBefore(minimumDate));
	                }
	            };
	        }
	    });
	}

	
	/**
	 * Handles the action event when the Next button is clicked to proceed with booking.
	 *
	 * @param event The action event triggered by clicking the Next button.
	 * @throws Exception If an error occurs while processing the booking, sending messages to the server, or setting up new screens.
	 */
	public void nextBtn(ActionEvent event) throws Exception {
	    String Ordernumberstr;
	    if (validateInputs()) {
	        BookingDetail details = new BookingDetail();
	        details.setTime(timeCombo.getValue());
	        details.setParkName(parkNameCombo.getValue());
	        details.setNumOfVisitors(numOfVisitorsCombo.getValue());
	        details.setEmail(emailT.getText());
	        details.setVisitorID(ClientController.client.bookingController.getID());

	        // Format the phone number into "xxx-xxx-xxxx" format
	        String phoneNumber = telephoneT.getText().replaceAll("[^\\d]", ""); // Remove non-numeric characters
	        String formattedPhoneNumber = String.format("%s-%s-%s", phoneNumber.substring(0, 3), phoneNumber.substring(3, 6), phoneNumber.substring(6));
	        details.setTelephone(formattedPhoneNumber);

	        if (guide.isSelected()) {
	            details.setVisitType("Guided");
	            details.setNumOfVisitorsG(numOfVisitorsGCombo.getValue());
	        } else {
	            if (Integer.valueOf(numOfVisitorsCombo.getValue()) > 1) {
	                details.setVisitType("Group");
	            } else {
	                details.setVisitType("Solo");
	            }
	        }

	        // Check if the current time is valid
	        if (checkCurrentTime()) {
	            // Combine date and time into LocalDateTime object
	            LocalDateTime dateTime = dateCombo.getValue().atTime(LocalTime.parse(details.getTime()));

	            // Format LocalDateTime into the desired format
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
	            String formattedDateTime = dateTime.format(formatter);
	            details.setDate(formattedDateTime);

	            // Create messages to send to the server
	            Message Checknewbooking = new Message(details, Commands.CheckParkCapacity);
	            Message newBookin = new Message(details, Commands.newBookingToDB);

	            // Send the messages to the server
	            ClientController.client.sendToServer(Checknewbooking);
	            boolean awaitResponse = false;
	            while (!awaitResponse) {
	                try {
	                    Thread.sleep(100);
	                    awaitResponse = ClientController.client.bookingController.isGotResponse();
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	            ClientController.client.bookingController.setGotResponse();

	            // Check if booking is available in the database
	            if (ClientController.client.bookingController.getCheckIfBookingAvailable()) {
	                // There is available space
	                details.setTableName("booking");
	                ((Node) event.getSource()).getScene().getWindow().hide();
	                PaymentController newScreen = new PaymentController();

	                // Set details for all screens to know details
	                ClientController.client.bookingController.setNewBooking(details);
	                newScreen.start(new Stage());
	                System.out.println("The booking is available in db.");

	                // Send the booking details to the server
	                ClientController.client.sendToServer(newBookin);
	                awaitResponse = false;
	                while (!awaitResponse) {
	                    try {
	                        Thread.sleep(100);
	                        awaitResponse = ClientController.client.bookingController.isGotResponse();
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();
	                    }
	                }
	                ClientController.client.bookingController.setGotResponse();

	                // Get the order number from the server
	                Ordernumberstr = ClientController.client.bookingController.getCheckIfExistBooking();
	                details.setOrderNumber(Ordernumberstr);
	                ClientController.client.bookingController.setNewBooking(details);
	            } else {
	                // No available space, redirect to waiting list or reschedule
	                ClientController.client.bookingController.setNewBooking(details);
	                ((Node) event.getSource()).getScene().getWindow().hide();
	                WaitinigorRescheduleController newS = new WaitinigorRescheduleController();
	                newS.start(new Stage());
	            }

	            System.out.println(details.toString());
	        }
	    } else {
	        // Display error screen for invalid inputs
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/NotValidInputErrorScreen.fxml"));
	        loader.setController(this);
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage primaryStage = new Stage();
	        primaryStage.setScene(scene);
	        RemoveTopBar(primaryStage, root);
	        primaryStage.show();
	        errorT.setText(errorMessage.toString());
	    }
	}

	
	/**
	 * Validates the input fields for booking details.
	 *
	 * @return True if all required fields are filled and inputs are valid, otherwise false.
	 */
	private boolean validateInputs() {
	    // Check if all required fields are filled
	    if (timeCombo.getValue() == null) {
	        errorMessage.append("Time is required.\n");
	    }
	    if (parkNameCombo.getValue() == null) {
	        errorMessage.append("Park name is required.\n");
	    }
	    if (numOfVisitorsCombo.getValue() == null && numOfVisitorsGCombo.getValue() == null) {
	        errorMessage.append("Number of visitors is required.\n");
	    }
	    if (telephoneT.getText().isEmpty()) {
	        errorMessage.append("Telephone number is required.\n");
	    } else if (telephoneT.getText().length() != 10) {
	        errorMessage.append("Phone number must be 10 digits long.\n");
	    }
	    if (emailT.getText().isEmpty()) {
	        errorMessage.append("Email is required.\n");
	    } else {
	        // Validate email format using regular expression
	        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
	        if (!emailT.getText().matches(emailPattern)) {
	            errorMessage.append("Email is not in a valid format.\n");
	        }
	    }
	    if (dateCombo.getValue() == null) {
	        errorMessage.append("Visit date is required.\n");
	    }
	    // Return true if there are no error messages, otherwise false
	    return errorMessage.length() == 0 ? true : false;
	}

	
	
	/**
	 * Checks if the selected booking time is at least 24 hours in advance.
	 *
	 * @return True if the selected time is at least 24 hours in advance, otherwise false.
	 */
	public boolean checkCurrentTime() {
	    LocalDate date = dateCombo.getValue();
	    String hour = timeCombo.getValue();

	    // Splitting the hour string to extract hours and minutes
	    String[] timeParts = hour.split(":");
	    int selectedHour = Integer.parseInt(timeParts[0]);
	    int selectedMinute = Integer.parseInt(timeParts[1]);

	    // Creating a LocalTime object for the selected time
	    LocalTime arrivalTime = LocalTime.of(selectedHour, selectedMinute);

	    // Getting the current date and time
	    LocalDate currentDate = LocalDate.now();
	    LocalTime currentTime = LocalTime.now();

	    // Combine date and time into LocalDateTime object
	    LocalDateTime bookingDateTime = date.atTime(arrivalTime);

	    // Calculate the minimum allowed booking time (24 hours from now)
	    LocalDateTime minimumBookingTime = LocalDateTime.now().plusHours(24);

	    // Comparing the selected date and time with the minimum allowed booking time
	    if (bookingDateTime.isBefore(minimumBookingTime)) {
	        errorMessage.append("You can only book 24 hours or more in advance. Please select a future time.\n");
	        return false;
	    }
	    return true;
	}



	
	/**
	 * Checks if the current user is a group guide.
	 *
	 * @return True if the current user is not a group guide, otherwise false.
	 */
	public boolean checkGuide() {
	    // Create a LoginDetail object for the current user
	    LoginDetail loginDetail = new LoginDetail(ClientController.client.bookingController.getID());
	    // Create a message to check if the current user is a group guide
	    Message loginDetailMsg = new Message(loginDetail, Commands.CheckIfGroupGuide);
	    
	    // Send the message to the server
	    boolean awaitResponse = true;
	    try {
	        ClientController.client.sendToServer(loginDetailMsg);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Wait for response from the server
	    while (awaitResponse) {
	        try {
	            Thread.sleep(100);
	            awaitResponse = ClientController.client.mainScreenController.isGotResponse();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

	    // Check if the current user is not a group guide
	    if (!ClientController.client.mainScreenController.isGroupGuideLoginValid()) {
	        // User is not a group guide
	        return true;
	    } else {
	        // User is a group guide
	        return false;
	    }
	}

	
	 
	
	
	/**
	 * Handles the action event when the "Guide" checkbox is clicked.
	 *
	 * @param event The action event triggered by clicking the "Guide" checkbox.
	 * @throws Exception If an error occurs while handling the action event.
	 */
	public void guideCB(ActionEvent event) throws Exception {
	    // Check if the "Guide" checkbox is selected
	    if (guide.isSelected()) {
	        // If selected, disable and hide the "Number of Visitors" combo box and enable and show the "Number of Visitors for Guide" combo box
	        numOfVisitorsCombo.setDisable(true);
	        numOfVisitorsCombo.setVisible(false);
	        numOfVisitorsGCombo.setDisable(false);
	        numOfVisitorsGCombo.setVisible(true);
	    } else {
	        // If not selected, enable and show the "Number of Visitors" combo box and disable and hide the "Number of Visitors for Guide" combo box
	        numOfVisitorsCombo.setDisable(false);
	        numOfVisitorsCombo.setVisible(true);
	        numOfVisitorsGCombo.setDisable(true);
	        numOfVisitorsGCombo.setVisible(false);
	    }
	}

	
	
	// clears error text and close the window
	public void okBtn(ActionEvent event) throws Exception {
		errorMessage.delete(0, errorMessage.length());
		((Node)event.getSource()).getScene().getWindow().hide();
	}
	
	/**
	 * Sets up the ComboBoxes with predefined options for park names, number of visitors, time, and number of visitors for guided tours.
	 */
	private void setComboBox() {
	    // Initialize ArrayLists to store predefined options for park names, number of visitors, time, and number of visitors for guided tours
	    
		ClientController.client.mainScreenController.getParkNames();
		boolean awaitResponse = true;
		while (awaitResponse) {
            try {
                Thread.sleep(100);
                awaitResponse = ClientController.client.mainScreenController.isGotResponse();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		ClientController.client.mainScreenController.setGotResponse(true);
		ArrayList<String> parkNames = ClientController.client.mainScreenController.allParkNames();
		
		//ArrayList<String> parkNames = new ArrayList<String>();
	    ArrayList<String> NumOfVisitors = new ArrayList<String>();
	    ArrayList<String> NumOfVisitorsG = new ArrayList<String>();
	    ArrayList<String> Time = new ArrayList<String>();
	    
//	    // Populate the parkNames list with predefined park names
//	    parkNames.add("BlackForest");
//	    parkNames.add("Hyde Park");
//	    parkNames.add("YellowStone");
	    
	    // Populate the NumOfVisitors list with options for the number of visitors (1 to 5)
	    for (int i = 1; i < 6; i++) {
	        NumOfVisitors.add(String.valueOf(i));
	    }
	    
	    // Populate the NumOfVisitorsG list with options for the number of visitors for guided tours (1 to 15)
	    for (int i = 1; i < 16; i++) {
	        NumOfVisitorsG.add(String.valueOf(i));
	    }
	    
	    // Populate the Time list with options for booking time slots (from 08:00 to 16:00)
	    for (int i = 8; i <= 16; i++) {
	        if (i < 10)
	            Time.add("0" + String.valueOf(i) + ":00");
	        else
	            Time.add(String.valueOf(i) + ":00");
	    }
	    
	    // Create ObservableList objects from the ArrayLists to populate ComboBoxes
	    ObservableList<String> list1 = FXCollections.observableArrayList(parkNames);
	    parkNameCombo.setItems(list1);
	    ObservableList<String> list2 = FXCollections.observableArrayList(NumOfVisitors);
	    numOfVisitorsCombo.setItems(list2);
	    ObservableList<String> list3 = FXCollections.observableArrayList(Time);
	    timeCombo.setItems(list3);
	    ObservableList<String> list4 = FXCollections.observableArrayList(NumOfVisitorsG);
	    numOfVisitorsGCombo.setItems(list4);
	    
	    // Select the default values for number of visitors ComboBoxes
	    numOfVisitorsCombo.getSelectionModel().select("1");
	    numOfVisitorsGCombo.getSelectionModel().select("1");
	}

	
	
	/**
	 * Checks if the current visitor has been alerted.
	 *
	 * @return True if the visitor has been alerted, otherwise false.
	 * @throws Exception If an error occurs while checking the visitor's alerts.
	 */
	public boolean CheckIfVisitorisalerted() throws Exception {
	    boolean awaitResponse;
	    
	    // Retrieve the visitor's ID from the client controller
	    String savedID = ClientController.client.bookingController.getID();
	    
	    // Create a BookingDetail object with the visitor's ID to check alerts
	    BookingDetail IdToCheckAlerts = new BookingDetail();
	    IdToCheckAlerts.setVisitorID(savedID);
	    
	    // Create a message to check the visitor's alerts
	    Message IdToCheckAlertsMsg = new Message(IdToCheckAlerts, Commands.CheckVisitorAlerts);
	    
	    // Send the message to the server
	    ClientController.client.sendToServer(IdToCheckAlertsMsg);
	    awaitResponse = false;
	    
	    // Wait for response from the server
	    while (!awaitResponse) {
	        try {
	            Thread.sleep(100);
	            awaitResponse = ClientController.client.bookingController.isGotResponse();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	    // Set the response flag to false after getting the response
	    ClientController.client.bookingController.setGotResponse();
	    
	    // Check if the visitor has been alerted
	    if (ClientController.client.bookingController.getIsVisitorAlerted()) {
	        // If alerted, display a notification and return true
	        NotficationController newS = new NotficationController();
	        newS.start(new Stage());
	        return true;
	    }
	    // If not alerted, return false
	    return false;
	}

	
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/NewBookingScreen.fxml"));
    	loader.setController(this); // Set the controller
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("NewBooking");
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
    	setComboBox();
    	configureDatePicker();
    	guide.setDisable(checkGuide());//Permission for group guide only
    	CheckIfVisitorisalerted();
    	//add a method to check if this client is in the 24 hour pending table (commingbooks)
    	
	}
}
