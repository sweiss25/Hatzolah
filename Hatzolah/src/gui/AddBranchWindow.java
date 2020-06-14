package gui;

import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import data.BranchIO;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.event.*;

public class AddBranchWindow extends Stage {
	private Connection dbConnection;
	private TextField branchName;
	private TextField branchYearEst;

	public AddBranchWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;
		BorderPane borderLayout = new BorderPane();
		GridPane grid = new GridPane();
		grid.add(new Label("Branch Name:"), 0, 0);
		branchName = new TextField();
		grid.add(branchName, 1, 0);
		grid.add(new Label("Year Established: "), 0, 1);
		branchYearEst = new TextField();
		grid.add(branchYearEst, 1,1);
		borderLayout.setCenter(grid);
		Button okButton = new Button("OK");
		okButton.setOnAction(new AddBranchController(dbConnection));
		HBox hbox = new HBox();
		hbox.getChildren().add(okButton);
		borderLayout.setBottom(hbox);
		Scene scene = new Scene(borderLayout);
		
		this.setScene(scene);
		this.show();
	}

	class AddBranchController implements EventHandler<ActionEvent> {
		private Connection dbConnection;
		
		public AddBranchController(Connection dbConnection) {
			this.dbConnection = dbConnection;
		}

		@Override
		public void handle(ActionEvent arg0) {
			if(branchName.getText().equals("") || branchYearEst.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "All fields required");
				return;
			}
			try {
				BranchIO.addBranch(dbConnection, branchName.getText(), Integer.parseInt(branchYearEst.getText()));
				JOptionPane.showMessageDialog(null, "branch added");
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "branch not added");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "error");
			}
		}
	}
}
