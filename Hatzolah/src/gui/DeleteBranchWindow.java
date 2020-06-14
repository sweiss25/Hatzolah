package gui;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import data.BranchIO;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class DeleteBranchWindow extends Stage {
	private Connection dbConnection;
	private TextField branchName;

	public DeleteBranchWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;
		this.setTitle("Find Branch");
		BorderPane borderLayout = new BorderPane();
		HBox hBox= new HBox();
		Label instructionLabel = new Label ("Enter branch name to delete branch: ");
		branchName = new TextField();
		hBox.getChildren().addAll(instructionLabel, branchName);
		borderLayout.setTop(hBox);
		Button deleteButton = new Button ("Delete");
		deleteButton.setOnAction(new DeleteBranchController(dbConnection));
		HBox buttonBox = new HBox();
		buttonBox.getChildren().add(deleteButton);
		borderLayout.setBottom(buttonBox);
		Scene theScene = new Scene(borderLayout);
		this.setScene(theScene);
		this.show();
	}

	class DeleteBranchController implements EventHandler<ActionEvent> {
		private Connection dbConnection;
		
		public DeleteBranchController(Connection dbConnection) {
			this.dbConnection = dbConnection;
		}

		@Override
		public void handle(ActionEvent arg0) {
			if(branchName.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "All fields required");
				return;
			}
			try {
				ArrayList<String> brs = BranchIO.viewBranches(dbConnection);
				if(brs.contains(branchName.getText())) {
					BranchIO.deleteBranch(dbConnection, branchName.getText());
					JOptionPane.showMessageDialog(null, "branch deleted");
				}else {
					JOptionPane.showMessageDialog(null, "invalid branch name");
					return;
				}
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "branch not deleted");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "error");
			}
		}
	}
}
