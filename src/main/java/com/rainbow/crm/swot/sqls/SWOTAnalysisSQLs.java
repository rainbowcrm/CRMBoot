package com.rainbow.crm.swot.sqls;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.ConnectionCreater;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.reasoncode.service.IReasonCodeService;

public class SWOTAnalysisSQLs {

	public static Map<String,Integer> getSalesLeadReasonSplitups (int division, int company , Date startDate, Date endDate ,
			String factoryType, String orientation)
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Integer> ans = new HashMap<String, Integer> ();
		try {
			connection = ConnectionCreater.getConnection();
			String divisionPart = (division==-1)?"":" AND SALES_LEADS.DIVISION_ID = ? ";
			String sql =  " SELECT COUNT(SALES_LEADS.ID),REASON_CODES.REASON FROM SALES_LEADS,REASON_CODES  WHERE   " +
			" SALES_LEADS.MGR_REASON_ID = REASON_CODES.ID AND " +
			"  SALES_LEADS.RELEASED_DATE >= ? AND  SALES_LEADS.RELEASED_DATE <= ?  AND SALES_LEADS.COMPANY_ID = ? AND "   + 
			" REASON_CODES.ORIENTATION = ? AND REASON_CODES.FACTOR_TYPE = ? AND " +  
			"   SALES_LEADS.IS_VOIDED = FALSE  " + divisionPart +   "    GROUP BY REASON_CODES.REASON ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, company);
			statement.setString(4, orientation);
			statement.setString(5, factoryType);
			if(division != -1)
				statement.setInt(6, division);
			
			rs = statement.executeQuery();
			while (rs.next()) {
				Integer count  = rs.getInt(1);
				String reason = rs.getString(2)  ;
				ans.put(reason, count);
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans ;
	}
	
	public static Map<String,Integer> getFeedBackReasonSplitups (int division, int company , Date startDate, Date endDate ,
			String factoryType, String orientation)
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Integer> ans = new HashMap<String, Integer> ();
		try {
			connection = ConnectionCreater.getConnection();
			String divisionPart = (division==-1)?"":" AND FEEDBACKS.DIVISION_ID = ? ";
			String sql =  " SELECT COUNT(FEEDBACK_LINES.ID),REASON_CODES.REASON FROM FEEDBACKS,FEEDBACK_LINES,REASON_CODES WHERE   " +
					" FEEDBACKS.ID= FEEDBACK_LINES.FEEDBACK_ID AND FEEDBACK_LINES.REASON_CODE_ID = REASON_CODES.ID AND   " +
					 " FEEDBACKS.FDBACK_DATE >= ? AND  FEEDBACKS.FDBACK_DATE <= ?  AND FEEDBACKS.COMPANY_ID =?  AND " +
					 " REASON_CODES.ORIENTATION = ? AND REASON_CODES.FACTOR_TYPE = ?   "  +
					 divisionPart + 
					"  AND FEEDBACKS.IS_DELETED = FALSE AND FEEDBACK_LINES.IS_DELETED=FALSE  GROUP BY REASON_CODES.REASON ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, company);
			statement.setString(4, orientation);
			statement.setString(5, factoryType);
			if(division != -1)
				statement.setInt(6, division);
			
			rs = statement.executeQuery();
			while (rs.next()) {
				Integer count  = rs.getInt(1);
				String reason = rs.getString(2)  ;
				ans.put(reason, count);
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans ;
	}
	
	
	public static Map<String,Integer> getEnquiryReasonSplitups (int division, int company , Date startDate, Date endDate ,
			String factoryType, String orientation)
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Integer> ans = new HashMap<String, Integer> ();
		try {
			connection = ConnectionCreater.getConnection();
			String divisionPart = (division==-1)?"":" AND ENQUIRIES.DIVISION_ID = ? ";
			String sql =  " SELECT COUNT(ENQUIRIES.ID),REASON_CODES.REASON FROM ENQUIRIES,REASON_CODES WHERE   " +
					" ENQUIRIES.REASON_ID = REASON_CODES.ID AND   " +
					 " ENQUIRIES.ENQ_DATE >= ? AND  ENQUIRIES.ENQ_DATE <= ?  AND ENQUIRIES.COMPANY_ID =?  AND " +
					 " REASON_CODES.ORIENTATION = ? AND REASON_CODES.FACTOR_TYPE = ?   "  +
					 divisionPart + 
					"  AND ENQUIRIES.IS_DELETED = FALSE   GROUP BY REASON_CODES.REASON ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, company);
			statement.setString(4, orientation);
			statement.setString(5, factoryType);
			if(division != -1)
				statement.setInt(6, division);
			
			rs = statement.executeQuery();
			while (rs.next()) {
				Integer count  = rs.getInt(1);
				String reason = rs.getString(2)  ;
				ans.put(reason, count);
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans ;
	}
}
