package gui;

import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import data.BranchIO;
import data.MemberIO;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.event.*;

public class UpdateMemberWindow extends Stage {
	private Connection dbConnection;
	private TextField memberId;
	private TextField phone;
	private TextField branch;
	private TextField maritalStat;
	private TextField address;
	private TextField jobId;

	public UpdateMemberWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;
		BorderPane borderLayout = new BorderPane();
		GridPane grid = new GridPane();
		grid.add(new Label("Member ID: "), 0, 0);
		memberId = new TextField();
		grid.add(memberId, 1, 0);
		grid.add(new Label("Phone Number (111-111-1111): "), 0, 1);
		phone = new TextField();
		grid.add(phone, 1,1);
		grid.add(new Label("Branch Name: "), 0, 2);
		branch = new TextField();
		grid.add(branch, 1,2);
		grid.add(new Label("Marital Status (single, married, divorced, widowed): "), 0, 3);
		maritalStat = new TextField();
		grid.add(maritalStat, 1,3);
		grid.add(new Label("Street Address: "), 0, 4);
		address = new TextField();
		grid.add(address, 1,4);
		grid.add(new Label("Job ID: "), 0, 5);
		jobId = new TextField();
		grid.add(jobId, 1,5);
		borderLayout.setCenter(grid);
		Button okButton = new Button("Update");
		okButton.setOnAction(new UpdateMemberController(dbConnection));
		HBox hbox = new HBox();
		hbox.getChildren().add(okButton);
		borderLayout.setBottom(hbox);
		Scene scene = new Scene(borderLayout);
		
		this.setScene(scene);
		this.show();
	}

	class UpdateMemberController implements EventHandler<ActionEvent> {
		private Connection dbConnection;
		
		public UpdateMemberController(Connection dbConnection) {
			this.dbConnection = dbConnection;
		}

		@Override
		public void handle(ActionEvent arg0) {
			if(memberId.getText().equals("") || phone.getText().equals("") || branch.getText().equals("") || maritalStat.getText().equals("") || address.getText().equals("") || jobId.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "All fields required");
				return;
			}
			try {
				ArrayList<String> brs = BranchIO.viewBranches(dbConnection);
				if(brs.contains(branch.getText())) {	
					MemberIO.updateMember(dbConnection, memberId.getText(), phone.getText(), branch.getText(), maritalStat.getText(), address.getText(), Integer.parseInt(jobId.getText()));
					JOptionPane.showMessageDialog(null, "Member updated");
				}else {
					JOptionPane.showMessageDialog(null, "invalid branch name");
					return;
				}
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "Member not updated");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "error");
			}
		}
	}
}

