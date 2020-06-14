package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class BranchIO {
	public static void addBranch(Connection dbConnection, String name, int yearEst) throws SQLException {
		String sql = "insert into branches (name, yearEst) values (?,?)";
		dbConnection.setAutoCommit(false);
		try (PreparedStatement pStatement = dbConnection.prepareStatement(sql);) {
			pStatement.setString(1, name);
			pStatement.setInt(2, yearEst);
			pStatement.executeUpdate();
		    dbConnection.commit();
		} catch(SQLException sqlE) {
			dbConnection.rollback();
			throw sqlE;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error");
		}
	}
	
	public static void deleteBranch(Connection dbConnection, String name) throws SQLException {
		String sql = "update branches set isDeleted = 1 where name = ?";
		dbConnection.setAutoCommit(false);
		try (PreparedStatement pStatement = dbConnection.prepareStatement(sql);) {
			pStatement.setString(1, name);
			pStatement.executeUpdate();
		    dbConnection.commit();
		} catch(SQLException sqlE) {
			dbConnection.rollback();
			throw sqlE;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error");
		}
	}
	
	public static ArrayList<String> viewBranches(Connection dbConnection) throws SQLException {
		ArrayList<String> branchNames = new ArrayList<>();
		String query = "select name from branches where isDeleted = 0";
		dbConnection.setAutoCommit(false);
		try (PreparedStatement pStatement = dbConnection.prepareStatement(query);) {
			ResultSet rs = pStatement.executeQuery();
			while (rs.next()) {
				String branch = rs.getString("name");
				branchNames.add(branch);
			}
			dbConnection.commit();
			return branchNames;
		} catch(SQLException sqlE) {
			dbConnection.rollback();
			throw sqlE;
		}
	}
	
	public static int viewBranchMemberCount(Connection dbConnection, String branch) throws SQLException {
		int memberCount;
		String query = "select count (memberId) from members where branch = ?";
		PreparedStatement pStatement = dbConnection.prepareStatement(query);
		pStatement.setString(1, branch);
		ResultSet rs = pStatement.executeQuery();
		rs.next();
		memberCount = rs.getInt(1); 
		return memberCount;
	}
	
	public static int viewBranchAmbulanceCount(Connection dbConnection, String branch) throws SQLException {
		int ambulanceCount;
		String query = "select count (busId) from buses where branchName = ? and isDeleted = 0";
		PreparedStatement pStatement = dbConnection.prepareStatement(query);
		pStatement.setString(1, branch);
		ResultSet rs = pStatement.executeQuery();
		rs.next();
		ambulanceCount = rs.getInt(1);  
		return ambulanceCount;
	}
	
	public static int viewBranchAvgCallsYear(Connection dbConnection, String branch) throws SQLException {
		int avgCallsPerYear;
		String query = "select avg (numCalls) from (select year(timeReceived) as year, count(callId) AS numCalls FROM calls WHERE branch = ? GROUP BY YEAR(timeReceived))myTable";
		PreparedStatement pStatement = dbConnection.prepareStatement(query);
		pStatement.setString(1, branch);
		ResultSet rs = pStatement.executeQuery();
		rs.next();
		avgCallsPerYear = rs.getInt(1);  
		return avgCallsPerYear;
	}
	
	public static int viewBranchDonorCount(Connection dbConnection, String branch) throws SQLException {
		int donorCount;
		String query = "SELECT COUNT(donorId) FROM donors WHERE branch = ? and isDeleted = 0";
		PreparedStatement pStatement = dbConnection.prepareStatement(query);
		pStatement.setString(1, branch);
		ResultSet rs = pStatement.executeQuery();
		rs.next();
		donorCount = rs.getInt(1);  
		return donorCount;
	}
	
	public static double viewBranchTotalDonations(Connection dbConnection, String branch) throws SQLException {
		double totalDonations;
		String query = "SELECT SUM(amount) FROM donations INNER JOIN donors ON donations.donorId = donors.donorId WHERE branch = ? and isDeleted = 0";
		PreparedStatement pStatement = dbConnection.prepareStatement(query);
		pStatement.setString(1, branch);
		ResultSet rs = pStatement.executeQuery();
		rs.next();
		totalDonations = rs.getDouble(1);  
		return totalDonations;
	}
}
