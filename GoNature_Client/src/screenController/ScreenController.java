package screenController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ScreenController {
	
	private double xOffset = 0;
    private double yOffset = 0;
	
	@FXML
	public void xBtn(MouseEvent event) throws Exception {
		System.exit(0);
	}
	
	
	public void RemoveTopBar(Stage primaryStage,Parent root) {
		  // remove the top bar(minimize, resize, exit)
		  primaryStage.initStyle(StageStyle.UNDECORATED);
		  
	    	// Handle mouse pressed event to start window dragging
	        root.setOnMousePressed(event -> {
	            xOffset = event.getSceneX();
	            yOffset = event.getSceneY();
	        });

	        // Handle mouse dragged event to drag the window
	        root.setOnMouseDragged(event -> {
	            primaryStage.setX(event.getScreenX() - xOffset);
	            primaryStage.setY(event.getScreenY() - yOffset);
	        });
	}
}
