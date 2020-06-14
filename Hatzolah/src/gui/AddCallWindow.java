package gui;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import data.BranchIO;
import data.CallIO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AddCallWindow extends Stage {
	private Connection dbConnection;
	private TextField branch;
	private TextField timeReceived;
	private TextField callerAddr;
	private TextField callerName;
	private TextField patientAge;
	private TextField transport;
	private TextField busId;
	private TextField equipId;

	public AddCallWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;
		BorderPane borderLayout = new BorderPane();
		GridPane grid = new GridPane();
		grid.add(new Label("Branch: "), 0, 0);
		branch = new TextField();
		grid.add(branch, 1,0);
		grid.add(new Label("Time Received (hh:mm:ss): "), 0, 1);
		timeReceived = new TextField();
		grid.add(timeReceived, 1,1);
		grid.add(new Label("Caller Address : "), 0, 2);
		callerAddr = new TextField();
		grid.add(callerAddr, 1,2);
		grid.add(new Label("Caller Name: "), 0, 3);
		callerName = new TextField();
		grid.add(callerName, 1,3);
		grid.add(new Label("Patient age: "), 0, 4);
		patientAge = new TextField();
		grid.add(patientAge, 1,4);
		grid.add(new Label("Transport (0=no, 1=yes): "), 0, 5);
		transport = new TextField();
		grid.add(transport, 1,5);
		grid.add(new Label("Bus ID: "), 0, 6);
		busId = new TextField();
		grid.add(busId, 1,6);
		grid.add(new Label("Equipment ID: "), 0, 7);
		equipId = new TextField();
		grid.add(equipId, 1,7);
		
		borderLayout.setCenter(grid);
		Button okButton = new Button("Add Call");
		okButton.setOnAction(new AddCallController(dbConnection));
		HBox hbox = new HBox();
		hbox.getChildren().add(okButton);
		borderLayout.setBottom(hbox);
		Scene scene = new Scene(borderLayout);
		
		this.setScene(scene);
		this.show();
	}

	class AddCallController implements EventHandler<ActionEvent> {
		private Connection dbConnection;
		
		public AddCallController(Connection dbConnection) {
			this.dbConnection = dbConnection;
		}

		@Override
		public void handle(ActionEvent arg0) {
			try {
				if(branch.getText().equals("") || timeReceived.getText().equals("") || callerAddr.getText().equals("") || callerName.getText().equals("") || patientAge.getText().equals("") || transport.getText().equals("") || busId.getText().equals("") || equipId.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "All fields required");
					return;
				}
				ArrayList<String> brs = BranchIO.viewBranches(dbConnection);
				if(brs.contains(branch.getText())) {
					CallIO.addCall(dbConnection, branch.getText(), Time.valueOf(timeReceived.getText()), callerAddr.getText(), callerName.getText(), Integer.parseInt(patientAge.getText()), Integer.parseInt(transport.getText()), Integer.parseInt(busId.getText()), Integer.parseInt(equipId.getText()));
					JOptionPane.showMessageDialog(null, "Call Recorded");
				} else {
					JOptionPane.showMessageDialog(null, "invalid branch name");
					return;
				}
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "Call Not Recorded");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "error");
			}
		}
	}
}
