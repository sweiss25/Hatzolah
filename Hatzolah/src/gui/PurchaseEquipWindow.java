package gui;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import data.BranchIO;
import data.BusIO;
import data.EquipmentIO;
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

public class PurchaseEquipWindow extends Stage {
	private Connection dbConnection;
	private TextField equipId;
	private TextField unitPrice;
	private TextField branchName;

	public PurchaseEquipWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;
		BorderPane borderLayout = new BorderPane();
		GridPane grid = new GridPane();
		grid.add(new Label("Equipment ID: "), 0, 0);
		equipId = new TextField();
		grid.add(equipId, 1,0);
		grid.add(new Label("Unit Price: "), 0, 1);
		unitPrice = new TextField();
		grid.add(unitPrice, 1,1);
		grid.add(new Label("Branch Name: "), 0, 2);
		branchName = new TextField();
		grid.add(branchName, 1,2);
		borderLayout.setCenter(grid);
		Button okButton = new Button("Purchase");
		okButton.setOnAction(new PurchaseEquipController(dbConnection));
		HBox hbox = new HBox();
		hbox.getChildren().add(okButton);
		borderLayout.setBottom(hbox);
		Scene scene = new Scene(borderLayout);
		
		this.setScene(scene);
		this.show();
	}

	class PurchaseEquipController implements EventHandler<ActionEvent> {
		private Connection dbConnection;
		
		public PurchaseEquipController(Connection dbConnection) {
			this.dbConnection = dbConnection;
		}

		@Override
		public void handle(ActionEvent arg0) {
			if(equipId.getText().equals("") || unitPrice.getText().equals("") || branchName.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "All fields required");
				return;
			}
			try {
				if(!EquipmentIO.isEquip(dbConnection, Integer.parseInt(equipId.getText()))){
					JOptionPane.showMessageDialog(null, "invalid equipment id");
					return;
				}
				ArrayList<String> brs = BranchIO.viewBranches(dbConnection);
				if(brs.contains(branchName.getText())) {						
					EquipmentIO.purchaseEquip(dbConnection, Integer.parseInt(equipId.getText()), Double.valueOf(unitPrice.getText()), branchName.getText());
					JOptionPane.showMessageDialog(null, "Equipment Purchased");
				}else {
					JOptionPane.showMessageDialog(null, "invalid branch name");
					return;
				}
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "Equipment Not Purchased");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "error");
			}
		}
	}
}
