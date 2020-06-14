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

public class AddCallSympsWindow extends Stage {
	private Connection dbConnection;
	private TextField callId;
	private TextField sympId;
	private Button okButton;

	public AddCallSympsWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;
		BorderPane borderLayout = new BorderPane();
		GridPane grid = new GridPane();
		grid.add(new Label("Call ID: "), 0, 0);
		callId = new TextField();
		grid.add(callId, 1, 0);
		grid.add(new Label("Symptom ID: "), 0, 1);
		sympId = new TextField();
		grid.add(sympId, 1, 1);
		borderLayout.setCenter(grid);
		okButton = new Button("Add");
		okButton.setOnAction(new AddCallSympController(dbConnection));
		HBox hbox = new HBox();
		hbox.getChildren().add(okButton);
		borderLayout.setBottom(hbox);
		Scene scene = new Scene(borderLayout);
		
		this.setScene(scene);
		this.show();
	}

	class AddCallSympController implements EventHandler<ActionEvent> {
		private Connection dbConnection;
		
		public AddCallSympController(Connection dbConnection) {
			this.dbConnection = dbConnection;
		}

		@Override
		public void handle(ActionEvent arg0) {
			if(callId.getText().equals("") || sympId.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "All fields required");
				return;
			}
			try {
				CallIO.addCallSymptoms(dbConnection, Integer.parseInt(callId.getText()), Integer.parseInt(sympId.getText()));
				JOptionPane.showMessageDialog(null, "Call symptoms added");
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "Call symptoms not added");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "error");
			}
			callId.setText("");
			sympId.setText("");
			okButton.setText("Add Another");
		}
	}
}


