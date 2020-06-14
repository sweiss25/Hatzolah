package gui;

import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import data.BranchIO;
import gui.AddCallWindow.AddCallController;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.event.*;

public class ViewBranchesWindow extends Stage {
	private Connection dbConnection;

	public ViewBranchesWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;
		BorderPane borderLayout = new BorderPane();
		Button okButton = new Button("view branches");
		okButton.setOnAction(new ViewBranchesController(dbConnection));
		
		HBox hbox = new HBox();
		hbox.getChildren().add(okButton);
		borderLayout.setBottom(hbox);
		Scene scene = new Scene(borderLayout);
			
		this.setScene(scene);
		this.show();
	}

	class ViewBranchesController extends Stage implements EventHandler<ActionEvent> {
		private Connection dbConnection;
		
		public ViewBranchesController(Connection dbConnection) {
			this.dbConnection = dbConnection;
		}

		@Override
		public void handle(ActionEvent arg0) {
			try {
				ArrayList<String> branchNames = BranchIO.viewBranches(dbConnection);
				ListView<String> listView = new ListView<>();
				for(String branch : branchNames) {
					listView.getItems().add(branch);
				}
				HBox hbox = new HBox(listView);
				Scene scene = new Scene(hbox, 300, 120);
			    this.setScene(scene);
			    this.show();
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "branches are unable to be viewed");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "error");
			}
		}
	}
}


