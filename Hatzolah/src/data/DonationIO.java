package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

public class DonationIO {
	public static void addDonation(Connection dbConnection, int donorID, double amount, LocalDate dateGiven) throws SQLException {
		String sql = "insert into donations (donorId, amount, dateGiven) values (?,?,?)";
		dbConnection.setAutoCommit(false);
		try(PreparedStatement pStatement = dbConnection.prepareStatement(sql);){
			pStatement.setInt(1, donorID);
			pStatement.setDouble(2, amount);
			pStatement.setDate(3, Date.valueOf(dateGiven));
			pStatement.executeUpdate();
			dbConnection.commit();
		} catch (SQLException sqlE) {
			dbConnection.rollback();
			throw sqlE;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error");
		}
	}
}
