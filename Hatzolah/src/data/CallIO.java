package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;

import javax.swing.JOptionPane;

public class CallIO {
	
	public static void addCall(Connection dbConnection, String branch, Time timeReceived, String callerAddr, String callerName, int patientAge, int transport, int busId, int equipId) throws SQLException {
		int callId;
		String insertCall = "insert into calls (branch, timeReceived, callerAddr, callerName, patientAge, transport, busId) values (?,?,?,?,?,?,?)";
		dbConnection.setAutoCommit(false);
		try {
			PreparedStatement pStatement = dbConnection.prepareStatement(insertCall);
			pStatement.setString(1, branch);
			pStatement.setTime(2, timeReceived);
			pStatement.setString(3, callerAddr);
			pStatement.setString(4, callerName);
			pStatement.setInt(5, patientAge);
			pStatement.setInt(6, transport);
			pStatement.setInt(7, busId);
		    pStatement.executeUpdate();
		    
		    String getCallId = "select max(callId) from calls";
		    pStatement = dbConnection.prepareStatement(getCallId);
		    ResultSet rs = pStatement.executeQuery();
		    rs.next();
		    callId = rs.getInt(1);  
		    
		    String insertEquip = "insert into equipCalls (callId, equipId) values (?,?)";
			pStatement = dbConnection.prepareStatement(insertEquip);
			pStatement.setInt(1, callId);
			pStatement.setInt(2, equipId);
			pStatement.executeUpdate();
			
		    dbConnection.commit();
		    
		} catch(SQLException sqlE) {
			dbConnection.rollback();
			throw sqlE;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error");
		}
	}
	
	public static void addCallSymptoms(Connection dbConnection, int callId, int sympId) throws SQLException {
		String insertSymp = "insert into sympCalls (callId, sympId) values (?,?)";
		dbConnection.setAutoCommit(false);
		try (PreparedStatement pStatement = dbConnection.prepareStatement(insertSymp);) {
			pStatement.setInt(1, callId);
			pStatement.setInt(2, sympId);
			pStatement.executeUpdate();
			dbConnection.commit();
		} catch (SQLException sqlE) {
			dbConnection.rollback();
			throw sqlE;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "error");
		}
	}
	
	public static void addCallMembers(Connection dbConnection, int callId, String memId) throws SQLException {
		String insertMem = "insert into memCalls (callId, memId) values (?,?)";
		dbConnection.setAutoCommit(false);
		try (PreparedStatement pStatement = dbConnection.prepareStatement(insertMem);) {
			pStatement.setInt(1, callId);
			pStatement.setString(2, memId);
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
