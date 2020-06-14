package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

public class MemberIO {
	public static void addMember(Connection dbConnection, LocalDate dateJoined, String phone, String branch, LocalDate dob, String maritalStat, String address, int jobId) throws SQLException {
		String memberId = branch.substring(0, 2) + (int)(Math.random()*(9999 - 1000) + 1000);
		String insertMember = "insert into members (memberId, dateJoined, phoneNum, branch, dob, maritalStat, homeAddr, jobId) values (?,?,?,?,?,?,?,?)";
		dbConnection.setAutoCommit(false);
		try (PreparedStatement pStatement = dbConnection.prepareStatement(insertMember);) {
			pStatement.setString(1, memberId);
			pStatement.setDate(2, Date.valueOf(dateJoined));
			pStatement.setString(3, phone);
			pStatement.setString(4, branch);
			pStatement.setDate(5, Date.valueOf(dob));
			pStatement.setString(6, maritalStat);
			pStatement.setString(7, address);
			pStatement.setInt(8, jobId);
			pStatement.executeUpdate();
		    dbConnection.commit();
		} catch(SQLException sqlE) {
			dbConnection.rollback();
			throw sqlE;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error");
		}
	}
	
	public static void deleteMember(Connection dbConnection, String memberId) throws SQLException {
		String deleteCreds = "delete from memberCreds where memberId = ?";
		dbConnection.setAutoCommit(false);
		try {
			PreparedStatement pStatement = dbConnection.prepareStatement(deleteCreds);
			pStatement.setString(1, memberId);
			pStatement.executeUpdate();
			
			String deleteCalls = "delete from memCalls where memberId = ?";
			pStatement = dbConnection.prepareStatement(deleteCalls);
			pStatement.setString(1, memberId);
			pStatement.executeUpdate();
		
			String deleteMember = "delete from members where memberId = ?";
			pStatement = dbConnection.prepareStatement(deleteMember);
			pStatement.setString(1, memberId);
			pStatement.executeUpdate();
		    dbConnection.commit();
		} catch(SQLException sqlE) {
			dbConnection.rollback();
			throw sqlE;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error");
		}
	}
	
	public static void updateMember(Connection dbConnection, String memberId, String phone, String branch, String maritalStat, String address, int jobId) throws SQLException {
		String updateInfo = "update members set phoneNum = ?, branch = ?, maritalStat = ?, homeAddr = ?, jobId  = ? where memberId = ?";
		dbConnection.setAutoCommit(false);
		try (PreparedStatement pStatement = dbConnection.prepareStatement(updateInfo);) {
			pStatement.setString(1, phone);
			pStatement.setString(2, branch);
			pStatement.setString(3, maritalStat);
			pStatement.setString(4, address);
			pStatement.setInt(5, jobId);
			pStatement.setString(6, memberId);
		    pStatement.executeUpdate();
		    dbConnection.commit();
		} catch(SQLException sqlE) {
			dbConnection.rollback();
			throw sqlE;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error");
		}
	}
	
	public static void addMemberCreds(Connection dbConnection, String memberId, int credId, LocalDate dateReceived) throws SQLException {
		String insertCreds = "insert into memberCreds (memberId, credId, dateEarned) values (?,?,?)";
		dbConnection.setAutoCommit(false);
		try (PreparedStatement pStatement = dbConnection.prepareStatement(insertCreds);) {
			pStatement.setString(1, memberId);
			pStatement.setInt(2, credId);
			pStatement.setDate(3, Date.valueOf(dateReceived));
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
