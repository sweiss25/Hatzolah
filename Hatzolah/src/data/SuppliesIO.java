package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

public class SuppliesIO {
	
	public static boolean isSupply(Connection dbConnection, int supplyId) throws SQLException { //makes sure its not deleted
		String query = "select supplyId from supplies where isDeleted = 0"; //not doing autocommitt since were not inserting/updating the db
		try (PreparedStatement pStatement = dbConnection.prepareStatement(query);) {
			ResultSet rs = pStatement.executeQuery();
			while (rs.next()) {
				int sup = rs.getInt("supplyId");
				if(sup==supplyId) {
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
	
	public static void purchaseSupplies(Connection dbConnection, int supplyId, double unitPrice, int qty, String branchName) throws SQLException {
		int purchaseId;
		String insertPurchase = "insert into purchases (branch, datePurchased, totalSpent) values (?,?,?)";
		dbConnection.setAutoCommit(false);
		try {
			PreparedStatement pStatement = dbConnection.prepareStatement(insertPurchase);
			pStatement.setString(1, branchName);
			pStatement.setDate(2, Date.valueOf(LocalDate.now()));
			pStatement.setDouble(3, (unitPrice*qty));
		    pStatement.executeUpdate();
		    dbConnection.commit();
		    
		    String getPurchaseId = "select max(purchaseId) from purchases";
			pStatement = dbConnection.prepareStatement(getPurchaseId);
		    ResultSet rs = pStatement.executeQuery();
		    rs.next();
		    purchaseId = rs.getInt(1);
		
		    String insertPurchaseSupply = "insert into supplyPurchases (purchaseId, supplyId, unitPrice, qtyBought) values (?,?,?,?)";
			pStatement = dbConnection.prepareStatement(insertPurchaseSupply);
			pStatement.setInt(1, purchaseId);
			pStatement.setInt(2, supplyId);
			pStatement.setDouble(3, unitPrice);
			pStatement.setInt(4, qty);
		    pStatement.executeUpdate();
		    
		    String updateQty = "update supplies set qty_on_hand += ? where supplyId = ?";
			pStatement = dbConnection.prepareStatement(updateQty);
			pStatement.setInt(1, qty);
			pStatement.setInt(2, supplyId);
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
