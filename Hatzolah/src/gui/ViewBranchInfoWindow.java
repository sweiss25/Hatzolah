package gui;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import data.BranchIO;
import data.CallIO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ViewBranchInfoWindow extends Stage {
	private Connection dbConnection;
	private TextField branch;
	
	public ViewBranchInfoWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;
		BorderPane borderLayout = new BorderPane();
		GridPane grid = new GridPane();
		grid.add(new Label("Branch: "), 0, 0);
		branch = new TextField();
		grid.add(branch, 1,0);
		
		borderLayout.setCenter(grid);
		Button okButton = new Button("Get Info");
		okButton.setOnAction(new ViewBranchInfoController(dbConnection));
		HBox hbox = new HBox();
		hbox.getChildren().add(okButton);
		borderLayout.setBottom(hbox);
		Scene scene = new Scene(borderLayout);
		
		this.setScene(scene);
		this.show();
	}

	class ViewBranchInfoController implements EventHandler<ActionEvent> {
		private Connection dbConnection;
		
		public ViewBranchInfoController(Connection dbConnection) {
			this.dbConnection = dbConnection;
		}

		@Override
		public void handle(ActionEvent arg0) {
			if(branch.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "All fields required");
				return;
			}
			try {
				String memberCount = "Number of Members: \t\t" + BranchIO.viewBranchMemberCount(dbConnection, branch.getText());
				String ambulanceCount = "Number of Ambulances: \t" + BranchIO.viewBranchAmbulanceCount(dbConnection, branch.getText());
				String avgCallsYear = "Average Calls Per Year: \t\t" + BranchIO.viewBranchAvgCallsYear(dbConnection, branch.getText());
				String numDonors = "Number of Donors: \t\t" + BranchIO.viewBranchDonorCount(dbConnection, branch.getText());
				String totalDonations = "Total Donation Amount: \t\t$" + BranchIO.viewBranchTotalDonations(dbConnection, branch.getText());
				
				ListView<String> listView = new ListView<>();
				listView.getItems().add(memberCount);
				listView.getItems().add(ambulanceCount);
				listView.getItems().add(avgCallsYear);
				listView.getItems().add(numDonors);
				listView.getItems().add(totalDonations);
				
				HBox hbox = new HBox(listView);
				Scene scene = new Scene(hbox, 300, 120);
			    setScene(scene);
				show();				
				
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "Unable to Retrieve Branch Information");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "error");
			}
		}
	}
}
