package gui;

import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JOptionPane;

import data.BranchIO;
import data.DonationIO;
import data.DonorIO;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.event.*;

public class AddDonationWindow extends Stage {
	private Connection dbConnection;
	private TextField donorID;
	private TextField amount;
	private TextField dateGiven;

	public AddDonationWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;
		BorderPane borderLayout = new BorderPane();
		GridPane grid = new GridPane();
		
		grid.add(new Label("Donor ID:"), 0, 0);
		donorID = new TextField();
		grid.add(donorID, 1, 0);
		
		grid.add(new Label("Amount Donated: "), 0, 1);
		amount = new TextField();
		grid.add(amount, 1,1);
		
		grid.add(new Label("Date Donated: "), 0, 2);
		dateGiven = new TextField();
		dateGiven.setFocusTraversable(false);
		dateGiven.setPromptText("yyyy-mm-dd");
		grid.add(dateGiven, 1,2);
		
		borderLayout.setCenter(grid);
		Button okButton = new Button("Add");
		okButton.setOnAction(new AddDonationController(dbConnection));
		HBox hbox = new HBox();
		hbox.getChildren().add(okButton);
		borderLayout.setBottom(hbox);
		Scene scene = new Scene(borderLayout);
		
		this.setScene(scene);
		this.show();
	}

	class AddDonationController implements EventHandler<ActionEvent> {
		private Connection dbConnection;
		
		public AddDonationController(Connection dbConnection) {
			this.dbConnection = dbConnection;
		}

		@Override
		public void handle(ActionEvent arg0) {
			if(donorID.getText().equals("") || amount.getText().equals("") || dateGiven.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "All fields required");
				return;
			}
			try {
				if(!DonorIO.isDonor(dbConnection, Integer.parseInt(donorID.getText()))){
					JOptionPane.showMessageDialog(null, "invalid donor id");
					return;
				}
				DonationIO.addDonation(dbConnection, Integer.parseInt(donorID.getText()), Double.parseDouble(amount.getText()), LocalDate.parse(dateGiven.getText()));
				JOptionPane.showMessageDialog(null, "Donation added");
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "Donation not added");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "error");
			}
		}
	}
}
