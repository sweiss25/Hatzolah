package gui;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import data.DonorIO;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class DeleteDonorWindow extends Stage {
	private Connection dbConnection;
	private TextField donorID;

	public DeleteDonorWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;
		this.setTitle("Find Donor");
		BorderPane borderLayout = new BorderPane();
		HBox hBox= new HBox();
		Label instructionLabel = new Label ("Enter Donor ID to delete donor: ");
		donorID = new TextField();
		hBox.getChildren().addAll(instructionLabel, donorID);
		borderLayout.setTop(hBox);
		Button deleteButton = new Button ("Delete");
		deleteButton.setOnAction(new DeleteDonorController(dbConnection));
		HBox buttonBox = new HBox();
		buttonBox.getChildren().add(deleteButton);
		borderLayout.setBottom(buttonBox);
		Scene theScene = new Scene(borderLayout);
		this.setScene(theScene);
		this.show();
	}

	class DeleteDonorController implements EventHandler<ActionEvent> {
		private Connection dbConnection;
		
		public DeleteDonorController(Connection dbConnection) {
			this.dbConnection = dbConnection;
		}

		@Override
		public void handle(ActionEvent arg0) {
			if(donorID.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "All fields required");
				return;
			}
			try {
				if(!DonorIO.isDonor(dbConnection, Integer.parseInt(donorID.getText()))){
					JOptionPane.showMessageDialog(null, "invalid donor id");
					return;
				}
				DonorIO.deleteDonor(dbConnection, Integer.parseInt(donorID.getText()));
				JOptionPane.showMessageDialog(null, "donor deleted");
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "donor not deleted");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "error");
			}
		}
	}
}
