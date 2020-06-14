package gui;

import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JOptionPane;

import data.MemberIO;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.event.*;

public class AddMemCredsWindow extends Stage {
	private Connection dbConnection;
	private TextField memberId;
	private TextField credId;
	private TextField dateReceived;
	private Button okButton;

	public AddMemCredsWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;
		BorderPane borderLayout = new BorderPane();
		GridPane grid = new GridPane();
		grid.add(new Label("Member ID: "), 0, 0);
		memberId = new TextField();
		grid.add(memberId, 1, 0);
		grid.add(new Label("Credential ID: "), 0, 1);
		credId = new TextField();
		grid.add(credId, 1, 1);
		grid.add(new Label("Date Received: "), 0, 2);
		dateReceived = new TextField();
		grid.add(dateReceived, 1, 2);
		borderLayout.setCenter(grid);
		okButton = new Button("Add");
		okButton.setOnAction(new AddMemCredsController(dbConnection));
		HBox hbox = new HBox();
		hbox.getChildren().add(okButton);
		borderLayout.setBottom(hbox);
		Scene scene = new Scene(borderLayout);
		
		this.setScene(scene);
		this.show();
	}

	class AddMemCredsController implements EventHandler<ActionEvent> {
		private Connection dbConnection;
		
		public AddMemCredsController(Connection dbConnection) {
			this.dbConnection = dbConnection;
		}

		@Override
		public void handle(ActionEvent arg0) {
			if(memberId.getText().equals("") || credId.getText().equals("") || dateReceived.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "All fields required");
				return;
			}
			try {
				MemberIO.addMemberCreds(dbConnection, memberId.getText(), Integer.parseInt(credId.getText()), LocalDate.parse(dateReceived.getText()));
				JOptionPane.showMessageDialog(null, "Member credentials added");
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "Member credentials not added");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "error");
			}
			memberId.setText("");
			credId.setText("");
			dateReceived.setText("");
			okButton.setText("Add Another");
		}
	}
}

