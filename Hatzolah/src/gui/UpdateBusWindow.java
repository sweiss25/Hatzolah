package gui;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JOptionPane;

import data.BusIO;
import data.DonorIO;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class UpdateBusWindow extends Stage {
	private Connection dbConnection;
	private TextField busId;
	private TextField dateLastMaintained;

	public UpdateBusWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;
		this.setTitle("Find Bus");
		BorderPane borderLayout = new BorderPane();
		GridPane grid = new GridPane();
		grid.add(new Label("Enter bus ID to update its last maintenance date: "), 0, 0);
		busId = new TextField();
		grid.add(busId, 1, 0);
		grid.add(new Label("Enter date of last maintenance: "), 0, 1);
		dateLastMaintained = new TextField();
		dateLastMaintained.setFocusTraversable(false);
		dateLastMaintained.setPromptText("yyyy-mm-dd");
		grid.add(dateLastMaintained, 1, 1);
		borderLayout.setCenter(grid);
		Button updateButton = new Button ("Update");
		updateButton.setOnAction(new UpdateBusController(dbConnection));
		HBox buttonBox = new HBox();
		buttonBox.getChildren().add(updateButton);
		borderLayout.setBottom(buttonBox);
		Scene theScene = new Scene(borderLayout);
		this.setScene(theScene);
		this.show();
	}

	class UpdateBusController implements EventHandler<ActionEvent> {
		private Connection dbConnection;
		
		public UpdateBusController(Connection dbConnection) {
			this.dbConnection = dbConnection;
		}

		@Override
		public void handle(ActionEvent arg0) {
			if(busId.getText().equals("") || dateLastMaintained.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "All fields required");
				return;
			}
			try {
				if(!BusIO.isBus(dbConnection, Integer.parseInt(busId.getText()))){
					JOptionPane.showMessageDialog(null, "invalid bus id");
					return;
				}
				BusIO.updateBus(dbConnection, Integer.parseInt(busId.getText()), LocalDate.parse(dateLastMaintained.getText()));
				JOptionPane.showMessageDialog(null, "bus maintenance date update");
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "bus maintenance date not updated");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "error");
			}
		}
	}
}

