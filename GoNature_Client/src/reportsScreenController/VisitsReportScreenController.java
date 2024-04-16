package reportsScreenController;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import client.ClientController;
import enums.Commands;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.CancellationData;
import logic.CancellationDetail;
import logic.DateDetail;
import logic.Message;
import logic.WorkerDetail;
import workerScreenController.WorkerScreenController;
import javafx.scene.control.ScrollPane;


public class VisitsReportScreenController extends WorkerScreenController implements Initializable {

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
    private Button BackBtn;

    @FXML
    private Button Show;

    @FXML
    private VBox pieChartContainer;
    

    @FXML
    private ComboBox<String> parkNameCombo;

    @FXML
    private DatePicker FromDate;

    @FXML
    private DatePicker ToDate;

    @FXML
    private ScrollPane ScrollPane;
    
    @FXML
    private PieChart PieChartSolo;

    @FXML
    private PieChart PieChartGroup;

    @FXML
    private PieChart PieChartGuided;


    @FXML
    void backBtn(ActionEvent event) {
    	// Clear the pie chart container VBox
        pieChartContainer.getChildren().clear();


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
        setComboBox();
        setDatePickerCellFactory(FromDate);
        setDatePickerCellFactory(ToDate);
    }

    @FXML
    /**
     * Handles the action event when the "Show" button is clicked to display visit reports.
     *
     * @param event The action event triggered by clicking the "Show" button.
     * @throws IOException If an I/O exception occurs.
     */
    void showBtn(ActionEvent event) throws IOException {
        String parkName = parkNameCombo.getValue();
        LocalDate fromDate = FromDate.getValue();
        LocalDate toDate = ToDate.getValue();

        if (parkName == null || fromDate == null || toDate == null) {
            System.out.println("You need to choose a park name and dates");
            return;
        }

        DateDetail dateDetail = new DateDetail(fromDate, toDate);
        dateDetail.setParkName(parkName);
        
        Message msg = new Message(dateDetail, Commands.visitReportRequest);
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
        Map<String, int[]> visitorData = ClientController.client.reportController.getVisitorReportData();
        createPieCharts(visitorData);
    }

    /**
     * Creates PieCharts for visitor data and displays them in the pieChartContainer.
     *
     * @param visitorData A map containing visitor types as keys and corresponding data arrays as values.
     */
    private void createPieCharts(Map<String, int[]> visitorData) {
        pieChartContainer.getChildren().clear(); // Clear previous pie charts
        if (visitorData == null ) {
        	System.out.println("error creating piechart");
        	return ;
        }
        for (Entry<String, int[]> entry : visitorData.entrySet()) {
            String visitorType = entry.getKey();
            int[] data = entry.getValue();

            if (data == null || data.length == 0) {
                System.out.println("Error: Data for " + visitorType + " is null or empty.");
                continue;
            }

            int[] startTimeData = Arrays.copyOfRange(data, 0, 4);
            int[] durationData = Arrays.copyOfRange(data, 4, 8);

            int totalStartTimeCount = getTotalCount(startTimeData);
            int totalDurationCount = getTotalCount(durationData);

            PieChart startTimePieChart = createPieChartForStartTime(startTimeData, totalStartTimeCount, visitorType);
            PieChart durationPieChart = createPieChartForDuration(durationData, totalDurationCount, visitorType);

            VBox chartVBox = new VBox(startTimePieChart, durationPieChart);
            chartVBox.setSpacing(10);

            Label visitorTypeLabel = new Label(visitorType);
            visitorTypeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

            VBox rowVBox = new VBox(visitorTypeLabel, chartVBox);
            rowVBox.setSpacing(10);
            
            pieChartContainer.getChildren().add(rowVBox);
        }
        if (ScrollPane != null) {
            ScrollPane.setContent(pieChartContainer);
        } else {
            System.out.println("ScrollPane instance is null");
        }
  
    }

    /**
     * Creates a PieChart based on the provided counts, total start time count, and visitor type.
     *
     * @param counts             An array of counts representing the number of visits starting at each time category.
     * @param totalStartTimeCount The total count of all visits starting at different times.
     * @param visitorType        The type of visitor for which the PieChart is being created.
     * @return A PieChart representing the distribution of visit start times for the specified visitor type.
     */
    private PieChart createPieChartForStartTime(int[] counts, int totalStartTimeCount, String visitorType) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        String[] startTimeLabels = {"8:00-10:00", "10:00-12:00", "12:00-14:00", "14:00+"};

        for (int i = 0; i < counts.length; i++) {
            double percentage = totalStartTimeCount != 0 ? (double) counts[i] / totalStartTimeCount * 100 : 0.0;
            String label = startTimeLabels[i] + " (" + String.format("%.2f%%", percentage) + ")";
            pieChartData.add(new PieChart.Data(label, counts[i]));
        }

        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle(visitorType + " Start Time");
        pieChart.setLabelsVisible(true);
        pieChart.setLegendVisible(true);
        pieChart.setLegendSide(Side.RIGHT);
        pieChart.setPrefSize(400, 400);

        return pieChart;
    }

    /**
     * Creates a PieChart based on the provided counts, total duration count, and visitor type.
     *
     * @param counts             An array of counts representing the number of visits in each duration category.
     * @param totalDurationCount The total count of all visit durations.
     * @param visitorType        The type of visitor for which the PieChart is being created.
     * @return A PieChart representing the distribution of visit durations for the specified visitor type.
     */
    private PieChart createPieChartForDuration(int[] counts, int totalDurationCount, String visitorType) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        String[] durationLabels = {"1", "1-2", "2-3", "4+"};

        for (int i = 0; i < counts.length; i++) {
            double percentage = totalDurationCount != 0 ? (double) counts[i] / totalDurationCount * 100 : 0.0;
            String label = durationLabels[i] + " (" + String.format("%.2f%%", percentage) + ")";
            pieChartData.add(new PieChart.Data(label, counts[i]));
        }

        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle(visitorType + " Duration");
        pieChart.setLabelsVisible(true);
        pieChart.setLegendVisible(true);
        pieChart.setLegendSide(Side.RIGHT);
        pieChart.setPrefSize(400, 400);

        return pieChart;
    }

    /**
     * Calculates the total count from an array of integers.
     * @param data The array of integers to calculate the total count from.
     * @return The total count.
     */
    private int getTotalCount(int[] data) {
        int totalCount = 0;
        for (int count : data) {
            totalCount += count;
        }
        return totalCount;
    }
    
//    private PieChart createPieChart(int[] counts, String[] labels, String title) {
//        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
//
//        for (int i = 0; i < counts.length; i++) {
//            pieChartData.add(new PieChart.Data(labels[i], counts[i]));
//        }
//
//        PieChart pieChart = new PieChart(pieChartData);
//        pieChart.setTitle(title);
//
//        return pieChart;
//    }
    
    /**
     * Sets a custom day cell factory for the provided DatePicker component.
     * Disables dates that are after today and changes their background color.
     * @param datePicker The DatePicker component to set the custom day cell factory for.
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
    	
//        ArrayList<String> parkNames = new ArrayList<>();
//        parkNames.add("BlackForest");
//        parkNames.add("Hyde Park");
//        parkNames.add("YellowStone Park");
        parkNameCombo.setItems(FXCollections.observableArrayList(parkNames));
    }

    /**
     * Initializes and displays the ParkVisitReportScreen.
     * @param primaryStage The primary stage for the application.
     * @throws Exception Throws Exception if an error occurs during initialization.
     */
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/WorkerScreens/ParkVisitReportScreen.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("LoginScreen");
        primaryStage.setScene(scene);
        RemoveTopBar(primaryStage, root);
        primaryStage.show();
    }
}