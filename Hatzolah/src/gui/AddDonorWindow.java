package gui;

import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import data.BranchIO;
import data.DonorIO;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.event.*;

public class AddDonorWindow extends Stage {
	private Connection dbConnection;
	private TextField donorID;
	private TextField branch;
	private TextField fName;
	private TextField lName;
	private TextField addr;
	
	public AddDonorWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;
		BorderPane borderLayout = new BorderPane();
		GridPane grid = new GridPane();
				
		grid.add(new Label("Branch Name: "), 0, 0);
		branch = new TextField();
		grid.add(branch, 1, 0);
		
		grid.add(new Label("First Name: "), 0, 1);
		fName = new TextField();
		grid.add(fName, 1, 1);
		
		grid.add(new Label("Last Name: "), 0, 2);
		lName = new TextField();
		grid.add(lName, 1, 2);
		
		grid.add(new Label("Address: "), 0, 3);
		addr = new TextField();
		grid.add(addr, 1, 3);
		
		borderLayout.setCenter(grid);
		Button okButton = new Button("Add");
		okButton.setOnAction(new AddDonorController(dbConnection));
		HBox hbox = new HBox();
		hbox.getChildren().add(okButton);
		borderLayout.setBottom(hbox);
		Scene scene = new Scene(borderLayout);
		
		this.setScene(scene);
		this.show();
	}
	
	class AddDonorController implements EventHandler<ActionEvent> {
		private Connection dbConnection;
		
		public AddDonorController(Connection dbConnection) {
			this.dbConnection = dbConnection;
		}
		@Override
		public void handle(ActionEvent arg0) {
			if(branch.getText().equals("") || fName.getText().equals("") || lName.getText().equals("") || addr.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "All fields required");
				return;
			}
			try {
				ArrayList<String> brs = BranchIO.viewBranches(dbConnection);
				if(brs.contains(branch.getText())) {
					DonorIO.addDonor(dbConnection, branch.getText(), fName.getText(), lName.getText(), addr.getText());
					JOptionPane.showMessageDialog(null, "Donor added");
				}else {
					JOptionPane.showMessageDialog(null, "invalid branch name");
					return;
				}
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "Donor not added");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "error");
			}
			
		}
		
	}

}
