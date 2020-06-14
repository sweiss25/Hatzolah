package gui;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import data.BusIO;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class DeleteBusWindow extends Stage {
	private Connection dbConnection;
	private TextField busId;

	public DeleteBusWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;
		this.setTitle("Find Bus");
		BorderPane borderLayout = new BorderPane();
		HBox hBox= new HBox();
		Label instructionLabel = new Label ("Enter bus ID to delete bus: ");
		busId = new TextField();
		hBox.getChildren().addAll(instructionLabel, busId);
		borderLayout.setTop(hBox);
		Button deleteButton = new Button ("Delete");
		deleteButton.setOnAction(new DeleteBusController(dbConnection));
		HBox buttonBox = new HBox();
		buttonBox.getChildren().add(deleteButton);
		borderLayout.setBottom(buttonBox);
		Scene theScene = new Scene(borderLayout);
		this.setScene(theScene);
		this.show();
	}

	class DeleteBusController implements EventHandler<ActionEvent> {
		private Connection dbConnection;
		
		public DeleteBusController(Connection dbConnection) {
			this.dbConnection = dbConnection;
		}

		@Override
		public void handle(ActionEvent arg0) {
			if(busId.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "All fields required");
				return;
			}
			try {
				if(!BusIO.isBus(dbConnection, Integer.parseInt(busId.getText()))){
					JOptionPane.showMessageDialog(null, "invalid bus id");
					return;
				}
				BusIO.deleteBus(dbConnection, Integer.parseInt(busId.getText()));
				JOptionPane.showMessageDialog(null, "bus deleted");
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "bus not deleted");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "error");
			}
		}
	}
}

