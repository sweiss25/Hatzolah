package gui;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import data.BranchIO;
import data.EquipmentIO;
import data.SuppliesIO;
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

public class PurchaseSuppliesWindow extends Stage {
	private Connection dbConnection;
	private TextField supplyId;
	private TextField unitPrice;
	private TextField qty;
	private TextField branchName;

	public PurchaseSuppliesWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;
		BorderPane borderLayout = new BorderPane();
		GridPane grid = new GridPane();
		grid.add(new Label("Supply ID: "), 0, 0);
		supplyId = new TextField();
		grid.add(supplyId, 1,0);
		grid.add(new Label("Unit Price: "), 0, 1);
		unitPrice = new TextField();
		grid.add(unitPrice, 1,1);
		grid.add(new Label("Branch Name: "), 0, 2);
		branchName = new TextField();
		grid.add(branchName, 1,2);
		grid.add(new Label("Quantity: "), 0, 3);
		qty = new TextField();
		grid.add(qty, 1,3);
		borderLayout.setCenter(grid);
		Button okButton = new Button("Purchase");
		okButton.setOnAction(new PurchaseSuppliesController(dbConnection));
		HBox hbox = new HBox();
		hbox.getChildren().add(okButton);
		borderLayout.setBottom(hbox);
		Scene scene = new Scene(borderLayout);
		
		this.setScene(scene);
		this.show();
	}

	class PurchaseSuppliesController implements EventHandler<ActionEvent> {
		private Connection dbConnection;
		
		public PurchaseSuppliesController(Connection dbConnection) {
			this.dbConnection = dbConnection;
		}

		@Override
		public void handle(ActionEvent arg0) {
			if(supplyId.getText().equals("") || unitPrice.getText().equals("") || qty.getText().equals("") || branchName.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "All fields required");
				return;
			}
			try {
				if(!SuppliesIO.isSupply(dbConnection, Integer.parseInt(supplyId.getText()))){
					JOptionPane.showMessageDialog(null, "invalid supply id");
					return;
				}
				ArrayList<String> brs = BranchIO.viewBranches(dbConnection);
				if(brs.contains(branchName.getText())) {		
					SuppliesIO.purchaseSupplies(dbConnection, Integer.parseInt(supplyId.getText()), Double.valueOf(unitPrice.getText()), Integer.parseInt(qty.getText()), branchName.getText());
					JOptionPane.showMessageDialog(null, "Supplies Purchased");
				}else {
					JOptionPane.showMessageDialog(null, "invalid branch name");
					return;
				}
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "Supplies Not Purchased");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "error");
			}
		}
	}
}
