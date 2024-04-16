package reportsScreenController;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import javafx.stage.DirectoryChooser;
import javafx.scene.control.TextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


import javax.imageio.ImageIO;
import logic.ReportDetail;


import client.ClientController;
import client.ClientUI;
import enums.Commands;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.DateDetail;
import logic.Message;
import workerScreenController.WorkerScreenController;


import java.io.File; 
public class VisitorStatisticReportScreenController extends WorkerScreenController implements Initializable {
	
	@FXML
	private TextField directoryTextField;

	private DirectoryChooser directoryChooser;
	
    @FXML
    private Button parkDashboard;

    @FXML
    private Button visitorStatisticReport;

    @FXML
    private Button parkAvailabilityReport;

    @FXML
    private Button aboutUs;

    @FXML
    private Button logout;

    @FXML
    private BarChart<?, ?> visitorChart;

    @FXML
    private CategoryAxis xAxisR;

    @FXML
    private NumberAxis yAxisR;

    @FXML
    private DatePicker FromDate;

    @FXML
    private DatePicker ToDate;

    @FXML
    private Button Show;
    
    @FXML
    private Text errortxt;

    @FXML
    private Label Totalvisitortxt;

    @FXML
    private Button BackBtn;
    
    @FXML
    private Text msgToUser;
    @FXML
    private Button sendReportToSystem;
    
    @FXML
    private DateDetail dateDetail;
    
    
    @FXML
    /**
     * Handles the action event when the user clicks the "Send Report To System" button.
     * It takes a snapshot of the visitor chart, saves it as a PNG image, and sends the report details to the server.
     * Additionally, it updates the user interface to indicate the status of the report creation and submission.
     *
     * @param event The action event triggered by clicking the button.
     * @throws IOException If an I/O error occurs while saving the image or sending the report details.
     */
    public void sendReportToSystemBtn(ActionEvent event) throws IOException {
    	errortxt.setVisible(false);
        WritableImage image = visitorChart.snapshot(new SnapshotParameters(), null);

        // Get the directory path from the text field
        String directoryPath = directoryTextField.getText();

        // Create a File object with the directory path and image file name
        File outputFile = new File(directoryPath, "VisitorStatistic.png");

        // Save the WritableImage to the file
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outputFile);
        
        // Create the ReportDetail object with the image byte array
        ReportDetail report = new ReportDetail(dateDetail.getParkName(), dateDetail.getStart(), dateDetail.getEnd(), "Visitor Statistic Report", outputFile.getAbsolutePath(), directoryTextField.getText());
        System.out.println("gina");
        Message msg = new Message(report, Commands.AddReport);
        ClientController.client.sendToServer(msg);
        System.out.println("chloe");
        boolean awaitResponse = true;
        while (awaitResponse) {
            try {
                Thread.sleep(100);
                awaitResponse = ClientController.client.reportController.isGotResponse();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ClientController.client.reportController.setGotResponse(true);
        if (ClientController.client.reportController.isReportCheck()) {
        	msgToUser.setVisible(true);
            msgToUser.setText("Report Created And Saved To Server Successfully");}
        else {
        	msgToUser.setVisible(true);
            msgToUser.setText("Error Creating Report");
            errortxt.setFill(Color.RED);
        }
            
        sendReportToSystem.setDisable(true);
    
    }
    
    
    
    @Override
    /**
     * Initializes the controller after its root element has been completely processed.
     * It sets up the date picker cell factories, clears existing data from the visitor chart, and initializes default data for the visits report.
     * Additionally, it initializes the DirectoryChooser for selecting directories.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    public void initialize(URL location, ResourceBundle resources) {
    	setDatePickerCellFactory(FromDate);
        setDatePickerCellFactory(ToDate);
//        if (visitorChart == null) {
//            visitorChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
//        }
        visitorChart.getData().clear();
        // Initialize method
        setDateDefultForVisitsReport();
	    // Initialize the DirectoryChooser
	    directoryChooser = new DirectoryChooser();
	    
    }

    /**
     * Handles the event when the "Show" button is clicked to display visitor statistics based on selected dates.
     * It sends a request to the server to fetch visitor statistic data within the selected date range.
     * If both the "From" and "To" dates are selected, it retrieves and displays the visitor statistics in a bar chart.
     * If any date is not selected, it displays an error message.
     *
     * @param event The ActionEvent triggered by clicking the "Show" button.
     * @throws IOException If an I/O exception occurs while sending the request.
     */
    public void showBtn(ActionEvent event) throws IOException {
    	errortxt.setVisible(false);
        // HashMap to store visitor data
        Map<LocalDate, int[]> visitorData;
        // Variables to store the fromDate and toDate
        LocalDate fromDate = FromDate.getValue();
        LocalDate toDate = ToDate.getValue();
        if (fromDate != null && toDate != null) { // Check if both dates are selected
        dateDetail = new DateDetail(fromDate, toDate);
        dateDetail.setParkName(ClientController.client.workerController.getWorkerDetail().getParkName());

        System.out.println(dateDetail.getEnd());
        Message msg = new Message(dateDetail, Commands.VisitorStatisticRequest);
        ClientController.client.sendToServer(msg);
        boolean awaitResponse = true;

        while (awaitResponse) {
            try {
                Thread.sleep(100);
                awaitResponse = ClientController.client.reportController.isGotResponse();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ClientController.client.reportController.setGotResponse(true);
        visitorData =  ClientController.client.reportController.getVisitorStatisticData();
        sendReportToSystem.setDisable(false);
        CreateVisitorStatisticsBarChar(visitorData);
        }
        else {
        	errortxt.setVisible(true);
        	errortxt.setText("Please select both From and To dates.");
        	errortxt.setFill(Color.RED);
        }
    }

    
    /**
     * Creates and displays a visitor statistics bar chart based on the provided visitor data.
     *
     * @param visitorData The visitor data to be displayed on the chart.
     */
	private void CreateVisitorStatisticsBarChar(Map<LocalDate, int[]> visitorData) {
        //Clear existing data from the chart
        visitorChart.getData().clear();
        xAxisR = new CategoryAxis();
		yAxisR = new NumberAxis(0, 20, 2);
		visitorChart.setAnimated(true);
		visitorChart.setBarGap(1.0d);
		visitorChart.setCategoryGap(10.0);
        // new array to hold the dates
        ArrayList<LocalDate> dates = new ArrayList<>(visitorData.keySet());
        
        // Sort the dates in ascending order
        Collections.sort(dates);

        // Create categories array from the sorted dates (contains dates)
        String[] categories = new String[dates.size()];
        for (int i = 0; i < dates.size(); i++) {
            categories[i] = dates.get(i).toString();
        }
        
        // Set categories on the x-axis
        xAxisR.setCategories(FXCollections.observableArrayList(Arrays.asList(categories)));

        // Create new series for each type of visitor
        XYChart.Series<String, Number> Solo = new XYChart.Series<>();
        XYChart.Series<String, Number> Group = new XYChart.Series<>();
        XYChart.Series<String, Number> Guided_group = new XYChart.Series<>();

        // Setting names for the series
        Solo.setName("Solo");
        Group.setName("Group");
        Guided_group.setName("Guided group");
        int visitorcounter = 0;
        
        for (Map.Entry<LocalDate, int[]> entry : visitorData.entrySet()) {
            LocalDate date = entry.getKey();
            int[] visitorCounts = entry.getValue();
            System.out.println(date+"3");
            // Add data points to the series
            Solo.getData().add(new XYChart.Data<>(date.toString(), visitorCounts[0]));
            visitorcounter += visitorCounts[0] ;
            Group.getData().add(new XYChart.Data<>(date.toString(), visitorCounts[1]));
            visitorcounter += visitorCounts[1] ;
            Guided_group.getData().add(new XYChart.Data<>(date.toString(), visitorCounts[2]));
            visitorcounter += visitorCounts[2] ;
            
        }
        
        
        // Add series to the chart
        visitorChart.getData().addAll(new XYChart.Series[]{Solo, Group, Guided_group});
        
        
        Totalvisitortxt.setText("Total visitor number is: " + visitorcounter);
    }

    // Method to clear the data in the graph screen
   
	 public void backBtn(ActionEvent event) {
        // Clear existing data from the chart
        visitorChart.getData().clear();
        Totalvisitortxt.setText("Total visitor number is: ");
    }

	 /**
	  * Sets default date and initializes the visitor chart for the visits report.
	  */
    private void setDateDefultForVisitsReport() {
    	
    	xAxisR = new CategoryAxis();
		yAxisR = new NumberAxis(0, 20, 2);
		//visitorChart.setAnimated(false);
		visitorChart.setBarGap(1.0d);
		visitorChart.setCategoryGap(10.0);
		
		HashMap<LocalDate, int[]> defaultVisitorData = generateDefaultVisitorDataForLastWeek();
		
		CreateVisitorStatisticsBarChar(defaultVisitorData);
    	
    }
    
    /**
     * Generates default visitor data for the last week.
     *
     * @return A HashMap containing visitor data for each day of the last week.
     */
    private HashMap<LocalDate, int[]> generateDefaultVisitorDataForLastWeek() {
        // Create a HashMap to hold visitor data
        HashMap<LocalDate, int[]> defaultVisitorData = new HashMap<>();

        // Get today's date
        LocalDate currentDate = LocalDate.now();

        // Generate data for the last week
        for (int i = 0; i < 7; i++) {
            // Subtract i days from the current date to get the date of each day in the last week
            LocalDate date = currentDate.minusDays(i);
            
            // For demonstration purposes, let's assume some random visitor counts for each day
            int[] visitorCounts = new int[]{10, 15, 20}; // Example counts for solo, group, and guided group visitors
            
            // Add the date and visitor counts to the HashMap
            defaultVisitorData.put(date, visitorCounts);
        }

        return defaultVisitorData;
    }

    /**
     * Checks if the selected dates are before the current date.
     * 
     * @return True if both selected dates are before the current date, false otherwise.
     */
    public boolean checkCurrentTime() {
        LocalDate fromdate = FromDate.getValue();
        LocalDate todate = ToDate.getValue();

        // Check if both dates are before today
        if (fromdate != null && todate != null) {
            return fromdate.isBefore(LocalDate.now()) && todate.isBefore(LocalDate.now());
        }

        return false; // Return false if any of the dates is not selected
    }

    /**
     * Sets a custom day cell factory for the provided DatePicker to disable future dates and change their appearance.
     *
     * @param datePicker The DatePicker to which the custom day cell factory will be set.
     */
    private void setDatePickerCellFactory(DatePicker datePicker) {
        // Create a custom day cell factory to disable dates of today and future
        Callback<DatePicker, DateCell> dayCellFactory = new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isAfter(LocalDate.now())) {
                            // Disable dates of future
                            setDisable(true);
                            //change color for disabled cells
                            setStyle("-fx-background-color: #ffc0cb;"); 
                        }
                    }
                };
            }
        };

        // Set the custom day cell factory to the date picker
        datePicker.setDayCellFactory(dayCellFactory);
    }
    
    /**
     * Starts the Visitor Statistic Report screen by loading the FXML file and setting up the scene.
     *
     * @param primaryStage The primary stage for the Visitor Statistic Report screen.
     * @throws Exception If an error occurs during loading or setting up the scene.
     */
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/WorkerScreens/VisitorStatisticReportScreen.fxml"));
    	loader.setController(this); // Set the controller
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("LoginScreen");
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
	}

	@FXML
	/**
	 * Handles the action event when the "Choose Directory" button is clicked to select a directory.
	 *
	 * @param event The action event triggered by clicking the "Choose Directory" button.
	 */
	private void handleChooseDirectory(ActionEvent event) {
	    // Show the directory chooser dialog
	    File selectedDirectory = directoryChooser.showDialog(null);

	    if (selectedDirectory != null) {
	        // Update the text field with the selected directory path
	        directoryTextField.setText(selectedDirectory.getAbsolutePath());
	    }
	}
    
    
}
   
    
