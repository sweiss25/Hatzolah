package gui;

import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import data.BranchIO;
import data.BusIO;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.event.*;

public class AddBusWindow extends Stage {
	private Connection dbConnection;
	private TextField vin;
	private TextField branchName;
	private TextField datePurchased;
	private TextField dateLastMaintained;
	private TextField price;

	public AddBusWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;
		BorderPane borderLayout = new BorderPane();
		GridPane grid = new GridPane();
		grid.add(new Label("VIN: "), 0, 0);
		vin = new TextField();
		grid.add(vin, 1,0);
		grid.add(new Label("Branch Name: "), 0, 1);
		branchName = new TextField();
		grid.add(branchName, 1,1);
		grid.add(new Label("Date Purchased: "), 0, 2);
		datePurchased = new TextField();
		datePurchased.setFocusTraversable(false);
		datePurchased.setPromptText("yyyy-mm-dd");
		grid.add(datePurchased, 1,2);
		grid.add(new Label("Date Last Maintained: "), 0, 3);
		dateLastMaintained = new TextField();
		dateLastMaintained.setFocusTraversable(false);
		dateLastMaintained.setPromptText("yyyy-mm-dd");
		grid.add(dateLastMaintained, 1,3);
		grid.add(new Label("Purchase Price: "), 0, 4);
		price = new TextField();
		grid.add(price, 1,4);
		borderLayout.setCenter(grid);
		Button okButton = new Button("OK");
		okButton.setOnAction(new AddBusController(dbConnection));
		HBox hbox = new HBox();
		hbox.getChildren().add(okButton);
		borderLayout.setBottom(hbox);
		Scene scene = new Scene(borderLayout);
		
		this.setScene(scene);
		this.show();
	}

	class AddBusController implements EventHandler<ActionEvent> {
		private Connection dbConnection;
		
		public AddBusController(Connection dbConnection) {
			this.dbConnection = dbConnection;
		}

		@Override
		public void handle(ActionEvent arg0) {
			if(vin.getText().equals("") || branchName.getText().equals("") || datePurchased.getText().equals("") || dateLastMaintained.getText().equals("") || price.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "All fields required");
				return;
			}
			try {
				ArrayList<String> brs = BranchIO.viewBranches(dbConnection);
				if(brs.contains(branchName.getText())) {				
					BusIO.addBus(dbConnection, vin.getText(), branchName.getText(), LocalDate.parse(datePurchased.getText()), LocalDate.parse(dateLastMaintained.getText()), Double.valueOf(price.getText()));
					JOptionPane.showMessageDialog(null, "bus added");
				}else {
					JOptionPane.showMessageDialog(null, "invalid branch name");
					return;
				}
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "bus not added");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "error");
			}
		}
	}
}