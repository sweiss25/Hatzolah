package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class BusIO {
	public static void addBus(Connection dbConnection, String vin, String branchName, LocalDate datePurchased, LocalDate dateLastMaintained, double price) throws SQLException {
		String sql = "insert into buses (vin, branchName, datePur, dateLastMaintain, purchasePrice) values (?,?,?,?,?)";
		dbConnection.setAutoCommit(false);
		try (PreparedStatement pStatement = dbConnection.prepareStatement(sql);) {
			pStatement.setString(1, vin);
			pStatement.setString(2, branchName);
			pStatement.setDate(3, Date.valueOf(datePurchased));
			pStatement.setDate(4, Date.valueOf(dateLastMaintained));
			pStatement.setDouble(5, price);
		    pStatement.executeUpdate();
		    dbConnection.commit();
		} catch(SQLException sqlE) {
			dbConnection.rollback();
			throw sqlE;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error");
		}
	}
	
	public static boolean isBus(Connection dbConnection, int busId)throws SQLException {
		String query = "select busId from buses where isDeleted = 0"; 
		try (PreparedStatement pStatement = dbConnection.prepareStatement(query);) {
			ResultSet rs = pStatement.executeQuery();
			while (rs.next()) {
				int bus = rs.getInt("busId");
				if(bus==busId) {
					return true;
				}
			}
		}catch(SQLException sqlE) {
			throw sqlE;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error");
		}
		return false;
	}
	
	public static void deleteBus(Connection dbConnection, int busId) throws SQLException {
		String sql = "update buses set isDeleted = 1 where busId = ?";
		dbConnection.setAutoCommit(false);
		try (PreparedStatement pStatement = dbConnection.prepareStatement(sql);) {
			pStatement.setInt(1, busId);
		    pStatement.executeUpdate();
		    dbConnection.commit();
		} catch(SQLException sqlE) {
			dbConnection.rollback();
			throw sqlE;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error");
		}
	}
	
	public static void updateBus(Connection dbConnection, int id, LocalDate date) throws SQLException {
		String sql = "update buses set dateLastMaintain = ? where busId = ?";
		dbConnection.setAutoCommit(false);
		try (PreparedStatement pStatement = dbConnection.prepareStatement(sql);) {
			pStatement.setDate(1, Date.valueOf(date));
			pStatement.setInt(2, id);
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

