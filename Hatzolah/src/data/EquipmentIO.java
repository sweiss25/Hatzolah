package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

public class EquipmentIO {
	
	public static boolean isEquip(Connection dbConnection, int equipId) throws SQLException { //makes sure its not deleted
		String query = "select equipmentId from supplies where isDeleted = 0"; //not doing autocommitt since were not inserting/updating the db
		try (PreparedStatement pStatement = dbConnection.prepareStatement(query);) {
			ResultSet rs = pStatement.executeQuery();
			while (rs.next()) {
				int eq = rs.getInt("equipmentId");
				if(eq==equipId) {
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
	
	public static void purchaseEquip(Connection dbConnection, int equipId, double unitPrice, String branchName) throws SQLException {
		int purchaseId;
		String insertPurchase = "insert into purchases (branch, datePurchased, totalSpent) values (?,?,?)";
		dbConnection.setAutoCommit(false);
		try {
			PreparedStatement pStatement = dbConnection.prepareStatement(insertPurchase);
			pStatement.setString(1, branchName);
			pStatement.setDate(2, Date.valueOf(LocalDate.now()));
			pStatement.setDouble(3, unitPrice);
		    pStatement.executeUpdate();
		    
		    String getPurchaseId = "select max(purchaseId) from purchases";
		    pStatement = dbConnection.prepareStatement(getPurchaseId);
		    ResultSet rs = pStatement.executeQuery();
		    rs.next();
		    purchaseId = rs.getInt(1);
		    
		    String insertPurchaseEquip = "insert into equipPurchases (purchaseId, equipId, unitPrice) values (?,?,?)";
		    pStatement = dbConnection.prepareStatement(insertPurchaseEquip);
			pStatement.setInt(1, purchaseId);
			pStatement.setInt(2, equipId);
			pStatement.setDouble(3, unitPrice);
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
