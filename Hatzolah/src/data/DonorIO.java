package data;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

public class DonorIO {
	public static void addDonor(Connection dbConnection, String branch, String firstName, String lastName, String addr) throws SQLException {
		String insertDonor = "insert into donors (branch, firstName, lastName, addr) values (?,?,?,?)";
		dbConnection.setAutoCommit(false);
		try (PreparedStatement pStatement = dbConnection.prepareStatement(insertDonor);) {
			pStatement.setString(1, branch);
			pStatement.setString(2, firstName);
			pStatement.setString(3, lastName);
			pStatement.setString(4, addr);
			pStatement.executeUpdate();
		    dbConnection.commit();
		} catch(SQLException sqlE) {
			dbConnection.rollback();
			throw sqlE;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error");
		}
	}
	
	public static boolean isDonor(Connection dbConnection, int donorID) throws SQLException {
		String query = "select donorId from donors where isDeleted = 0"; //not doing autocommitt since were not inserting/updating the db
		try (PreparedStatement pStatement = dbConnection.prepareStatement(query);) {
			ResultSet rs = pStatement.executeQuery();
			while (rs.next()) {
				int don = rs.getInt("donorId");
				if(don==donorID) {
					return true;
				}
			}
		} catch(SQLException sqlE) {
			throw sqlE;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error");
		}
		return false;
	}
	
	public static void deleteDonor(Connection dbConnection, int donorID) throws SQLException {
		String deleteDonor = "update donors set isDeleted = 1 where donorId = ?";
		dbConnection.setAutoCommit(false);
		try {
			PreparedStatement pStatement = dbConnection.prepareStatement(deleteDonor);
			pStatement.setInt(1, donorID);
			pStatement.executeUpdate();
		    dbConnection.commit();
		} catch(SQLException sqlE) {
			dbConnection.rollback();
			throw sqlE;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error");
		}
	}
	
	public static void updateDonor(Connection dbConnection, int donorID, String addr) throws SQLException {
		String updateInfo = "update donors set addr  = ? where donorID = ?";
		dbConnection.setAutoCommit(false);
		try (PreparedStatement pStatement = dbConnection.prepareStatement(updateInfo);) {
			pStatement.setString(1, addr);
			pStatement.setInt(2, donorID);
			pStatement.executeUpdate();
		    dbConnection.commit();
		} catch(SQLException sqlE) {
			dbConnection.rollback();
			throw sqlE;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error");
		}
	}
	
}