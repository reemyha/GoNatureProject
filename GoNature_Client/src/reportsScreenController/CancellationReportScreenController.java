package reportsScreenController;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import client.ClientController;
import enums.Commands;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.CancellationData;
import logic.CancellationDetail;
import logic.Message;
import logic.WorkerDetail;
import workerScreenController.WorkerScreenController;

public class CancellationReportScreenController extends WorkerScreenController implements Initializable { 

    @FXML
    private Button parkDashboard;

    @FXML
    private Button cancellationReport;

    @FXML
    private Button BackBtn;

    @FXML
    private Button Show;

    @FXML
    private Label averageCanceltxt;

    @FXML
    private BarChart<String, Number> CancellationChart;

    @FXML
    private CategoryAxis xAxisR;

    @FXML
    private NumberAxis yAxisR;
    
    @FXML
    private Text errortxt;

    @FXML
    private ComboBox<String> parkNameCombo;

	@FXML
    private TableView<CancellationDetail> tableView;

    @FXML
    private TableColumn<CancellationDetail, Integer> orderNumberC;

    @FXML
    private TableColumn<CancellationDetail, String> visitorIDC;

    @FXML
    private TableColumn<CancellationDetail, LocalDate> dateTimeC;

    @FXML
    private TableColumn<CancellationDetail, Integer> numOfVisitorsC;

    private ObservableList<String> daysOfWeek = FXCollections.observableArrayList(
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    );
    
   

	public void setvisitorIDCombo(ComboBox<String> parkNameCombo) {
		this.parkNameCombo = parkNameCombo;
	}
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	setComboBox();
        // Set the gapStartAndEnd property to true
        xAxisR = new CategoryAxis();
		yAxisR = new NumberAxis(0, 20, 2);
		
		//visitorChart.setAnimated(false);
		CancellationChart.setBarGap(1.0d);
		CancellationChart.setCategoryGap(10.0);
		
        xAxisR.setCategories(daysOfWeek);
        
        
        averageCanceltxt.setText("Average cancellation :");
        
        
        // Set cell value factories for table columns
        orderNumberC.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
        visitorIDC.setCellValueFactory(new PropertyValueFactory<>("visitorID"));
        dateTimeC.setCellValueFactory(new PropertyValueFactory<>("visitTime"));
        numOfVisitorsC.setCellValueFactory(new PropertyValueFactory<>("numVisitors"));
        
    }

    

	private void setComboBox() {
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
		
//        ArrayList<String> parkNames = new ArrayList<String>();
//        parkNames.add("BlackForest");
//        parkNames.add("Hyde Park");
//        parkNames.add("YellowStone");
        parkNames.add("All Parks");
        parkNameCombo.setItems(FXCollections.observableArrayList(parkNames));
        
    }

    /**
     * Handles the action event when the show button is clicked to display cancellation statistics for the selected park.
     *
     * @param event The action event triggered by clicking the show button.
     * @throws IOException If an I/O error occurs.
     */
    public void showBtn(ActionEvent event) throws IOException {
    	
    	errortxt.setVisible(false);
    	String selectedParkName = parkNameCombo.getValue();
    	System.out.println(selectedParkName);
        if (selectedParkName != null) {
            // Call method to get cancellation data for the selected park and update the chart
            Message msg = new Message(selectedParkName, Commands.CancellationReportRequest);
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
            ArrayList<CancellationDetail> cancellationData = ClientController.client.reportController.getCancellationReportData();
            int[] cancelldayAVGCount = ClientController.client.reportController.getDayCount();
            //update the barchart with the stat data
            CreateCancellationStatisticsBarChar(cancelldayAVGCount);
            
            
            // Update the table view with cancellation details
            updateTableView(cancellationData);
        } else {
            // Handle case when no park is selected
        	errortxt.setVisible(true);
        	errortxt.setText("Please select Park.");
        	errortxt.setFill(Color.RED);
        }
     }
    
    

    /**
     * Creates a bar chart to display cancellation statistics based on the average cancellation count per day.
     *
     * @param cancelldayAVGCount An array containing the average cancellation count for each day of the week.
     */
    private void CreateCancellationStatisticsBarChar(int[] cancelldayAVGCount) {
    	
    	int totalCancellations = 0;
        for (int count : cancelldayAVGCount) {
            totalCancellations += count;
        }

        // Calculate the average cancellation count
        int daysOfWeekCount = daysOfWeek.size();
        double averageCancellation = (double) totalCancellations / daysOfWeekCount;

        // Set the text of averageCanceltxt label to display the average cancellation count
        averageCanceltxt.setText("Average cancellation: " + String.format("%.2f", averageCancellation));
        
        xAxisR = new CategoryAxis();
		yAxisR = new NumberAxis(0, 20, 2);
		//visitorChart.setAnimated(false);
		CancellationChart.setBarGap(1.0d);
		CancellationChart.setCategoryGap(10.0);
    	
        ObservableList<javafx.scene.chart.XYChart.Series<String, Number>> series = FXCollections.observableArrayList();
        for (int i = 0; i < cancelldayAVGCount.length; i++) {
            javafx.scene.chart.XYChart.Series<String, Number> seriesData = new javafx.scene.chart.XYChart.Series<>();
            seriesData.setName(daysOfWeek.get(i));
            seriesData.getData().add(new javafx.scene.chart.XYChart.Data<>(daysOfWeek.get(i), cancelldayAVGCount[i]));
            series.add(seriesData);
        }
        CancellationChart.getData().clear();
        CancellationChart.getData().addAll(series);
    }

    /**
     * Updates the TableView with the provided list of cancellation data.
     *
     * @param cancellationData The list of cancellation details to be displayed in the TableView.
     */
    private void updateTableView(ArrayList<CancellationDetail> cancellationData) {
        // Convert ArrayList to ObservableList
        ObservableList<CancellationDetail> data = FXCollections.observableArrayList(cancellationData);
        
        // Set the items of the TableView to the ObservableList
        tableView.setItems(data);
    }
		
    /**
     * Initializes and displays the Cancellation Report screen.
     * Loads the FXML file, sets the controller, and displays the scene on the stage.
     *
     * @param primaryStage The primary stage for the Cancellation Report screen.
     * @throws Exception If there is an error loading the FXML file or setting up the scene.
     */
	    public void start(Stage primaryStage) throws Exception {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/WorkerScreens/CancellationReportScreen.fxml"));
			loader.setController(this); // Set the controller
	    	Parent root = loader.load();
	    	Scene scene = new Scene(root);
	    	primaryStage.setTitle("cancelation report");
	    	primaryStage.setScene(scene);
	    	RemoveTopBar(primaryStage,root);
	    	primaryStage.show();
		}
	 

	    /**
	     * Handles the action event when the back button is clicked.
	     * Clears existing data from the cancellation chart and table view.
	     *
	     * @param event The ActionEvent triggered by clicking the back button.
	     */
	    public void backBtn(ActionEvent event) {
	        // Clear existing data from the chart
	    	CancellationChart.getData().clear();
	    	xAxisR.setCategories(daysOfWeek); // Reset categories for the x-axis
	        xAxisR = new CategoryAxis();
			yAxisR = new NumberAxis(0, 20, 2);
			//visitorChart.setAnimated(false);
			CancellationChart.setBarGap(1.0d);
			CancellationChart.setCategoryGap(10.0);
	        xAxisR.setCategories(daysOfWeek);
	    	tableView.getItems().clear();
	        
	    } 
	    
	    
		//checking if the inputs are invalid
//		if (visitorIDCombo.getValue() == null) {
//	        errorMessage.append("parkname is missing.\n");
		

}