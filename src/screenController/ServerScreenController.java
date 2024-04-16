

package screenController;

import JDBC.DbController;
import JDBC.SqlConnection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.ClientDetail;
import server.EchoServer;
import server.ServerUI;
import server.NotifyThread;
public class ServerScreenController {
	
	NotifyThread alertThread;

	@FXML
	private TextField ipAddressT;
	@FXML
	private TextField portT;
	@FXML
	private TextField dbNameT;
	@FXML
	private TextField dbUsernameT;
	@FXML
	private TextField dbPasswordT;
	@FXML
	private Button startServer = null;
	@FXML
	private Button stopServer = null;
	@FXML
	private Button imprt = null;


	@FXML
	private TableView<ClientDetail> tableView;

	@FXML
	private TableColumn ipT;
	@FXML
	private TableColumn hostT;
	@FXML
	private TableColumn statusT;

	// TODO: Initilize Client Table.
	// TODO: Make The X button Functional.
	// TODO: Make Stop Server Button.

	private String getIpAddress() {
		return ipAddressT.getText();
	}

	private String getPort() {
		return portT.getText();
	}

	private String getDbName() {
		return dbNameT.getText();
	}

	private String getDbUsername() {
		return dbUsernameT.getText();
	}

	private String getDbPassword() {
		return dbPasswordT.getText();
	}

	public void loadTable(ClientDetail clientDetail) {// Client : {String IP, String Host, String Status}
		
		tableView.getItems().add(clientDetail);
	
	}
	public void updateTable(ClientDetail clientDetail) {

		boolean returnVal = tableView.getItems().remove(clientDetail);
		

	}

	public void stopServerBtn(ActionEvent event) {
		//Stop the NotifyThread gracefully
	    alertThread.stopThread(); // Assuming alertThread is accessible here

		ServerUI.disconnect();
		startServer.setDisable(false);
		stopServer.setDisable(true);
		disableDataInput(false);
		imprt.setDisable(true);

	}

	/*
	 * void connectServer(ActionEvent event) { EchoServer.main(null);
	 * EchoServer.startServer(dbNameField.getText(), usernameField.getText(),
	 * passwordField.getText()); }
	 *
	 * DB_URL = "jdbc:mysql://localhost/gonature?serverTimezone=IST"; DB_USER =
	 * "root"; DB_PASSWORD = "Aa123456";
	 *
	 *
	 */

	public void startServerBtn(ActionEvent event) {
		
		if (!ServerUI.isServerRunning()) {
			// The server is not running, so start it
			if (ServerUI.runServer(this.getPort())) {
				SqlConnection sqlconn = new SqlConnection(getDbName(), getDbUsername(), getDbPassword());
				DbController dbconn = new DbController(sqlconn.connectToDB());
				ServerUI.sv.setDbController(dbconn);
				ServerUI.sv.setServerScreenController(this);
				disableDataInput(true);
				startServer.setDisable(true);
				imprt.setDisable(false);
				stopServer.setDisable(false);
				
				
				//setup new thread
				alertThread = new NotifyThread();
			    Thread thread = new Thread(alertThread); // Create a new thread with NotifyThread as its target
			    thread.start(); // Start the new thread
			    


				
				//try { Thread.sleep(10000); } catch (InterruptedException e) { e.printStackTrace(); }
				//loadTable();
				
			
			} else {
				// Handle the case where the server failed to start
				System.out.println("Failed to start the server.");
			}
		} else {
			// The server is already running, handle accordingly
			System.out.println("The server is already running.");
		}
	}
	
	//import data from external data
	public void importBtn(ActionEvent event) {
		ServerUI.sv.dbController.importExternalData();
	}

	// Removes the abilty to enter new data while the server has started
	void disableDataInput(boolean Condition) {
		ipAddressT.setDisable(Condition);
		portT.setDisable(Condition);
		dbNameT.setDisable(Condition);
		dbUsernameT.setDisable(Condition);
		dbPasswordT.setDisable(Condition);
	}
   
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/serverScreen.fxml"));
		System.out.println("starting server gui");
		loader.setController(this); // Set the controller
		Parent root = loader.load();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
		primaryStage.show();
		ipT.setCellValueFactory(new PropertyValueFactory<>("ip"));
		hostT.setCellValueFactory(new PropertyValueFactory<>("hostName"));
		statusT.setCellValueFactory(new PropertyValueFactory<>("status"));
		stopServer.setDisable(true);

		
	}

}

