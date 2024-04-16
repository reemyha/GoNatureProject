package screenController;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.util.Arrays;
import java.util.List;


public class PricesScreenController extends VisitorScreenController {

    @FXML
    private TableView<List<String>> tableView;
    @FXML
    private TableColumn<List<String>, String> typeC;
    @FXML
    private TableColumn<List<String>, String> paymentC;
    @FXML
    private TableColumn<List<String>, String> valueC;
    

    public void setTable() {
        // Create sample data for each row
        ObservableList<List<String>> data = FXCollections.observableArrayList(
            Arrays.asList("Personal/family visit - \npre order.", "By the number\nof visitors.", "A discount of 15% off the\n price."),
            Arrays.asList("Personal/family visit - \noccasional visit.", "By the number\nof visitors.", "Full price."),
            Arrays.asList("Guided group - \npre order.", "By the number\nof visitors.", "25% off the full price.\nAdditional 12% Discount\r"
            		+ "on prepayment.\nThe guide does not pay."),
            Arrays.asList("Occasional group visit.", "By the number\nof visitors.", "10% off the full price.\r\n"
            		+ "The guide pays.")
        );

        // Bind the data to the table columns
        typeC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        paymentC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        valueC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
             
        // Set the data to the table view
        tableView.setItems(data);
                      
        tableView.setFixedCellSize(75); // Assuming each row height is 25px
        tableView.setMaxHeight(4 * tableView.getFixedCellSize() + 50); // Adding extra height for the header
  
        // Lock column resizing
        tableView.getColumns().forEach(column -> {
            column.setResizable(false);
        });
        
        // Remove row selection
        tableView.setSelectionModel(null);
    }

    /**
     * Initializes and displays the Prices GUI.
     *
     * @param primaryStage The primary stage for the Prices GUI.
     * @throws Exception If an error occurs while loading the FXML file, setting up the GUI, or initializing the table.
     */
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Prices.fxml"));
        loader.setController(this); // Set the controller
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("OurPrices");
        primaryStage.setScene(scene);
        RemoveTopBar(primaryStage, root);
        primaryStage.show();
        setTable();
    }
}
