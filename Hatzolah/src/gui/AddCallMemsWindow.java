package gui;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

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

public class AddCallMemsWindow extends Stage {
	private Connection dbConnection;
	private TextField callId;
	private TextField memberId;
	private Button okButton;

	public AddCallMemsWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;
		BorderPane borderLayout = new BorderPane();
		GridPane grid = new GridPane();
		grid.add(new Label("Call ID: "), 0, 0);
		callId = new TextField();
		grid.add(callId, 1, 0);
		grid.add(new Label("Member ID: "), 0, 1);
		memberId = new TextField();
		grid.add(memberId, 1, 1);
		borderLayout.setCenter(grid);
		okButton = new Button("Add");
		okButton.setOnAction(new AddCallMemController(dbConnection));
		HBox hbox = new HBox();
		hbox.getChildren().add(okButton);
		borderLayout.setBottom(hbox);
		Scene scene = new Scene(borderLayout);
		
		this.setScene(scene);
		this.show();
	}

	class AddCallMemController implements EventHandler<ActionEvent> {
		private Connection dbConnection;
		
		public AddCallMemController(Connection dbConnection) {
			this.dbConnection = dbConnection;
		}

		@Override
		public void handle(ActionEvent arg0) {
			if(callId.getText().equals("") || memberId.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "All fields required");
				return;
			}
			try {
				CallIO.addCallMembers(dbConnection, Integer.parseInt(callId.getText()), memberId.getText());
				JOptionPane.showMessageDialog(null, "Call members added");
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "Call members not added");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "error");
			}
			callId.setText("");
			memberId.setText("");
			okButton.setText("Add Another");
		}
	}
}



