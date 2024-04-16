package workerScreenController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.io.OutputStream;
import java.io.FileOutputStream;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

import java.util.ResourceBundle;



import client.ClientController;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import enums.Commands;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import logic.ManagerRequestDetail;
import logic.Message;
import logic.ReportDetail;
import javafx.scene.control.cell.PropertyValueFactory;

public class DepartmentManagerDashboardController extends WorkerScreenController implements Initializable{

	
    @FXML
    private Button parkDashboard;

    @FXML
    private Button parksVisitsReport;

    @FXML
    private Button cancellationReport;
    @FXML
    private Text welcomebackt;
    @FXML
    private TableView<ReportDetail> reportTable;

    @FXML
    private TableColumn<ReportDetail, Integer> reportId;

    @FXML
    private TableColumn<ReportDetail, String> parkName;

    @FXML
    private TableColumn<ReportDetail, LocalDate> fromC;

    @FXML
    private TableColumn<ReportDetail, LocalDate> toC;

    @FXML
    private Button downloadReport;

    @FXML
    private ComboBox<String> reportComboBox;
    
    @FXML
    private Button aboutUs;

    @FXML
    private Button logout;
    
    @FXML
    private Text errortxt;

    @FXML
    private TableView<ManagerRequestDetail> requestTable;

    @FXML
    private TableColumn<ManagerRequestDetail, Integer> requestNumber;

    @FXML
    private TableColumn<ManagerRequestDetail, String> changes;

    private ManagerRequestDetail requestSelected;
    
    private ReportDetail reportSelected;

    @FXML
    private Button confirmChanges;

    @FXML
    private Button denyChanges;
	@FXML
	private TextField directoryTextField;

	private DirectoryChooser directoryChooser;

    private ArrayList<ManagerRequestDetail> requestList;

	private ArrayList<ReportDetail> reportList;
    

	
    @Override
    public void initialize(URL location, ResourceBundle resources) {
	    directoryChooser = new DirectoryChooser();
    }
	
    @FXML
    void denyChangesBtn(ActionEvent event) throws Exception {
    	removeRequest(requestSelected.getRequestNumber());
    }

    @FXML
    void confirmChangesBtn(ActionEvent event) throws Exception {
    	confirm();
    }
    @FXML
    void reportComboBoxChange(ActionEvent event) throws Exception {
    	reportRefresh();
    }
    
	@FXML
	private void handleChooseDirectory(ActionEvent event) {
	    // Show the directory chooser dialog
	    File selectedDirectory = directoryChooser.showDialog(null);

	    if (selectedDirectory != null) {
	        // Update the text field with the selected directory path
	        directoryTextField.setText(selectedDirectory.getAbsolutePath());
	        downloadReport.setDisable(false);
	    }
	}
	
	/**
	 * Confirms the requested change and sends the corresponding message to the server.
	 * @throws Exception Throws Exception if an error occurs during execution.
	 */
    private void confirm() throws Exception {

    	Message msg = null;
    	System.out.println("."+requestSelected.getChangeTo()+".");
    	switch (requestSelected.getChangeTo()) {
    	
    	case "Set Park Capacity of ":
    		msg = new Message(requestSelected,Commands.ChangeParkCapacity);
    		ClientController.client.sendToServer(msg);
    		break;
    	case "Set Online Booking Capacity of ":
    		msg = new Message(requestSelected,Commands.ChangeOnlineBookingCapacity);
    		ClientController.client.sendToServer(msg);
    		break;
    	case "Set Park Stay Time of ":
    		msg = new Message(requestSelected,Commands.ChangeAverageParkStayTime);
    		ClientController.client.sendToServer(msg);
    		break;
    	default:
	  		System.out.println("Shouldn't have gotten here?!?!?!");
	  		break;  
    			
    	}    	
    	
		boolean awaitResponse = false;
            // wait for response
		while (!awaitResponse) {
			try {
				Thread.sleep(100);
				awaitResponse = ClientController.client.workerController.isGotResponse();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    	//requestTable.getItems().remove(requestSelected);
		ClientController.client.workerController.setGotResponse(false);
		System.out.println("Set complete");
		removeRequest(requestSelected.getRequestNumber());
    }
    
    private void removeRequest(int requestNumber) throws Exception {
		Message requestIndexmsg = new Message(requestNumber,Commands.removeRequest);
		ClientController.client.sendToServer(requestIndexmsg);
		boolean awaitResponse = false;
        // wait for response
		while (!awaitResponse) {
			try {
				Thread.sleep(100);
				awaitResponse = ClientController.client.workerController.isGotResponse();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ClientController.client.workerController.setGotResponse(false);
		refresh();
    }
    

    @FXML
    void parksVisitsReportBtn(ActionEvent event) {

    }
    
    @FXML
    void refreshBtn(ActionEvent event) throws Exception {
    	System.out.println("refreshed");
    	requestTable.getItems().clear();
    	refresh();
    }
    
    @FXML
    void downloadReportBtn(ActionEvent event) throws Exception {
    	
    	
        // Get the directory path from the text field
        String directoryPath = directoryTextField.getText();
        String fileName = "/Report #"+  reportSelected.getReportId()+".pdf";
        OutputStream out = new FileOutputStream(directoryPath+fileName);
        out.write(reportSelected.getFile());
        out.close();
    	
    }
    
	private void setComboBox() 
	{
		ArrayList<String> parkNames = new ArrayList<String>();
		parkNames.add("Visitor Statistic Report");
		parkNames.add("Park Availability Report");
		ObservableList<String> list1 = FXCollections.observableArrayList(parkNames);
		reportComboBox.setItems(list1);
		reportComboBox.getSelectionModel().selectFirst();




	}
    
	/**
	 * Refreshes the request table by clearing it and fetching updated data from the server.
	 * @throws Exception Throws Exception if an error occurs during execution.
	 */
    private void refresh() throws Exception {
    	requestTable.getItems().clear();
    	requestList = null;
    	System.out.println("got refreshed");
		Message msg = new Message(null,Commands.getRequestTable);
		ClientController.client.sendToServer(msg);
		boolean awaitResponse = false;
        // wait for response
		while (!awaitResponse) {
			try {
				Thread.sleep(100);
				awaitResponse = ClientController.client.workerController.isGotResponse();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		ClientController.client.workerController.setGotResponse(false);
		requestList =  ClientController.client.workerController.getRequestList();

		System.out.println(requestList.size());
    	requestTable.getItems().clear();

        for (ManagerRequestDetail RequestData : requestList) {
        	requestTable.getItems().add(RequestData);}
    	requestTable.refresh();
    }
    
    /**
     * Initializes the request table by setting cell value factories and refreshing the table.
     * @throws Exception Throws Exception if an error occurs during execution.
     */
    private void startRequestTable() throws Exception 
    {
        requestNumber.setCellValueFactory(new PropertyValueFactory<>("requestNumber"));
        changes.setCellValueFactory(new PropertyValueFactory<>("changes"));
        changes.setCellFactory(TextFieldTableCell.forTableColumn());

        refresh();
    }
    
    /**
     * Initializes the report table by setting cell value factories and refreshing the table.
     * @throws Exception Throws Exception if an error occurs during execution.
     */
    private void startReportTable() throws Exception 
    {

    	reportId.setCellValueFactory(new PropertyValueFactory<>("reportId"));
    	parkName.setCellValueFactory(new PropertyValueFactory<>("parkName"));
    	fromC.setCellValueFactory(new PropertyValueFactory<>("dateFrom"));
    	toC.setCellValueFactory(new PropertyValueFactory<>("dateTo"));
    	reportRefresh();
    }
    
	public String getReportType() {
		return reportComboBox.getValue();
	}
	
	/**
	 * Refreshes the report table by clearing it and fetching updated data from the server.
	 * @throws Exception Throws Exception if an error occurs during execution.
	 */
    private void reportRefresh() throws Exception {
    	reportTable.getItems().clear();
    	reportList = null;
    	System.out.println("got refreshed");

		Message msg = new Message(getReportType(),Commands.getReportTable);
		ClientController.client.sendToServer(msg);
		boolean awaitResponse = true;
        // wait for response
		while (awaitResponse) {
			try {
				Thread.sleep(100);
				awaitResponse = ClientController.client.reportController.isGotResponse();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ClientController.client.reportController.setGotResponse(true);
		reportList =  ClientController.client.reportController.getReportList();
		if (reportList == null || reportList.isEmpty()) {
			System.out.println("there is no report yet");
		}
		else {
			for (ReportDetail reportData : reportList) {
	        	reportTable.getItems().add(reportData);}
	        reportTable.getSelectionModel().selectFirst();
	        reportSelected =  reportTable.getSelectionModel().getSelectedItem();
	        reportTable.refresh();
			
		}
		
    }
    
    /**
     * Starts the Department Manager screen.
     * @param primaryStage The primary stage to set the scene.
     * @throws Exception Throws Exception if an error occurs during execution.
     */
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/WorkerScreens/DepartmentManagerScreen.fxml"));
    	loader.setController(this); // Set the controller
    	Parent root = loader.load();
    	Scene scene = new Scene(root);
    	primaryStage.setTitle("GoNature Dashboard");
    	primaryStage.setScene(scene);
    	RemoveTopBar(primaryStage,root);
    	primaryStage.show();
    	setComboBox();
    	startRequestTable();
    	startReportTable();
    	welcomebackt.setText("Welcome Back " + ClientController.client.workerController.getWorkerDetail().getName());
    	
    	requestTable.setOnMouseClicked(event -> {
    		confirmChanges.setDisable(false);
    		requestSelected = requestTable.getSelectionModel().getSelectedItem();
    		});
    	//if pressed outside of the tableview, the selection is cleared and cancel button is disabled
    	scene.setOnMousePressed(event -> {
            if (!requestTable.getBoundsInParent().contains(event.getX(), event.getY())) {
            	requestTable.getSelectionModel().clearSelection();
            	confirmChanges.setDisable(true);
            }
        });
    	
    	reportTable.setOnMouseClicked(event -> {
    		confirmChanges.setDisable(false);
    		reportSelected = reportTable.getSelectionModel().getSelectedItem();
    		});


    	

	}
}
