package gui;

import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import data.DonorIO;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.event.*;

public class UpdateDonorWindow extends Stage {
	private Connection dbConnection;
	private TextField donorID;
	private TextField branch;
	private TextField fName;
	private TextField lName;
	private TextField addr;

	public UpdateDonorWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;
		BorderPane borderLayout = new BorderPane();
		GridPane grid = new GridPane();
		
		grid.add(new Label("Donor ID: "), 0, 0);
		donorID = new TextField();
		grid.add(donorID, 1, 0);
		
		grid.add(new Label("Updated Address: "), 0, 1);
		addr = new TextField();
		grid.add(addr, 1,1);
		
	
		borderLayout.setCenter(grid);
		Button okButton = new Button("Update");
		okButton.setOnAction(new UpdateDonorController(dbConnection));
		HBox hbox = new HBox();
		hbox.getChildren().add(okButton);
		borderLayout.setBottom(hbox);
		Scene scene = new Scene(borderLayout);
		
		this.setScene(scene);
		this.show();
	}

	class UpdateDonorController implements EventHandler<ActionEvent> {
		private Connection dbConnection;
		
		public UpdateDonorController(Connection dbConnection) {
			this.dbConnection = dbConnection;
		}

		@Override
		public void handle(ActionEvent arg0) {
			if(donorID.getText().equals("") || addr.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "All fields required");
				return;
			}
			try {
				if(!DonorIO.isDonor(dbConnection, Integer.parseInt(donorID.getText()))){
					JOptionPane.showMessageDialog(null, "invalid donor id");
					return;
				}
				DonorIO.updateDonor(dbConnection, Integer.parseInt(donorID.getText()), addr.getText());
				JOptionPane.showMessageDialog(null, "Donor updated");
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "Donor not updated");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "error");
			}
		}
	}
}

