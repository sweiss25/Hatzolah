package gui;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import data.MemberIO;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class DeleteMemberWindow extends Stage {
	private Connection dbConnection;
	private TextField memberId;

	public DeleteMemberWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;
		this.setTitle("Find Member");
		BorderPane borderLayout = new BorderPane();
		HBox hBox= new HBox();
		Label instructionLabel = new Label ("Enter member ID to delete member: ");
		memberId = new TextField();
		hBox.getChildren().addAll(instructionLabel, memberId);
		borderLayout.setTop(hBox);
		Button deleteButton = new Button ("Delete");
		deleteButton.setOnAction(new DeleteMemberController(dbConnection));
		HBox buttonBox = new HBox();
		buttonBox.getChildren().add(deleteButton);
		borderLayout.setBottom(buttonBox);
		Scene theScene = new Scene(borderLayout);
		this.setScene(theScene);
		this.show();
	}

	class DeleteMemberController implements EventHandler<ActionEvent> {
		private Connection dbConnection;
		
		public DeleteMemberController(Connection dbConnection) {
			this.dbConnection = dbConnection;
		}

		@Override
		public void handle(ActionEvent arg0) {
			if(memberId.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "All fields required");
				return;
			}
			try {
				MemberIO.deleteMember(dbConnection, memberId.getText());
				JOptionPane.showMessageDialog(null, "member deleted");
			} catch (SQLException sqle) {
				JOptionPane.showMessageDialog(null, "member not deleted");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "error");
			}
		}
	}
}
