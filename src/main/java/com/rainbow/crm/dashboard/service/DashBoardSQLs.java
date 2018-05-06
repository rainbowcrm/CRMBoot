package com.rainbow.crm.dashboard.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.database.ConnectionCreater;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.reasoncode.service.IReasonCodeService;
import com.techtrade.rads.framework.utils.Utils;

public class DashBoardSQLs {

	public static Double getDivisionTotalSale(int division,  Date startDate, Date endDate )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT SUM(SALES_LINES.LINE_TOTAL) FROM SALES , SALES_LINES WHERE SALES.ID= SALES_LINES.SALES_ID AND  " + 
			 " SALES.SALES_DATE > ? AND  SALES.SALES_DATE <= ?  AND " + 
			" SALES.DIVISION_ID= ? AND  SALES_LINES.IS_VOIDED = FALSE AND SALES.IS_VOIDED= FALSE  ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, division);
			rs = statement.executeQuery();
			while (rs.next()) {
				Double amount = rs.getDouble(1);
				return amount; 
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return 0d ;
	}
	
	public static Double getPeriodTotalSale(String associate,  Date startDate, Date endDate )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT SUM(SALES_LINES.LINE_TOTAL) FROM SALES , SALES_LINES WHERE SALES.ID= SALES_LINES.SALES_ID AND  " + 
			 " SALES.SALES_DATE > ? AND  SALES.SALES_DATE <= ?  AND " + 
			" SALES_LINES.USER_ID= ? AND  SALES_LINES.IS_VOIDED = FALSE AND SALES.IS_VOIDED= FALSE  ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setString(3, associate);
			rs = statement.executeQuery();
			while (rs.next()) {
				Double amount = rs.getDouble(1);
				return amount; 
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return 0d ;
	}
	
	private static String getLeadStatusQuery (String [] statuses, boolean isIncludeNull)
	{
		if (statuses == null || statuses.length == 0) return  " ";
		StringBuffer status = new StringBuffer(" AND (") ;
		if (isIncludeNull )
			 status.append(" SALES_LEADS.STATUS IS NULL OR  ");
		status.append(" SALES_LEADS.STATUS IN (  ") ;
		for (int i = 0 ; i < statuses.length ; i ++ ) {
			status.append("'" + statuses[i] + "'" );
			if(i < statuses.length-1 )
				status.append(",");
		}
		status.append( " ))");
		return status.toString() ;
	}
	
	
	public static Double getTotalforAllLeadsByStatus(int division,Date startDate, Date endDate , String [] statuses , boolean includeNullStatus, int company )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String divisionClause = (division>0)?" AND SALES_LEADS.DIVISION_ID = ? ":"" ; 
			String statusQuery = getLeadStatusQuery(statuses, includeNullStatus);
			String sql =  " SELECT SUM(SALESLEAD_LINES.PRICE) FROM SALES_LEADS , SALESLEAD_LINES WHERE SALES_LEADS.ID= SALESLEAD_LINES.SALESLEAD_ID AND  " + 
			 " SALES_LEADS.RELEASED_DATE > ? AND  SALES_LEADS.RELEASED_DATE <= ? AND SALES_LEADS.COMPANY_ID = ?    " +  divisionClause + 
			"  AND  SALESLEAD_LINES.IS_VOIDED = FALSE AND SALES_LEADS.IS_VOIDED= FALSE  " + statusQuery; 
			
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, company);
			if (division > 0)
				statement.setInt(4, division);
		
			rs = statement.executeQuery();
			while (rs.next()) {
				Double amount = rs.getDouble(1);
				return amount; 
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return 0d ;
	}
	
	public static Double getTotalforAllLeads(int division,Date startDate, Date endDate , int company )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String divisionClause = (division>0)?" AND SALES_LEADS.DIVISION_ID = ? ":"" ; 
			String sql =  " SELECT SUM(SALESLEAD_LINES.PRICE) FROM SALES_LEADS , SALESLEAD_LINES WHERE SALES_LEADS.ID= SALESLEAD_LINES.SALESLEAD_ID AND  " + 
			 " SALES_LEADS.RELEASED_DATE > ? AND  SALES_LEADS.RELEASED_DATE <= ?   AND SALES_LEADS.COMPANY_ID = ?  " +  divisionClause + 
			"  AND  SALESLEAD_LINES.IS_VOIDED = FALSE AND SALES_LEADS.IS_VOIDED= FALSE  ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, company);
			if (division > 0)
				statement.setInt(4, division);
		
			rs = statement.executeQuery();
			while (rs.next()) {
				Double amount = rs.getDouble(1);
				return amount; 
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return 0d ;
	}

	public static Double getSaleAllMade(int divison,  Date startDate, Date endDate )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT SUM(SALES_LINES.LINE_TOTAL) FROM SALES , SALES_LINES WHERE SALES.ID= SALES_LINES.SALES_ID AND  " + 
			 " SALES.SALES_DATE > ? AND  SALES.SALES_DATE <= ?  AND SALES.DIVISION_ID = ?  " + 
			"  AND  SALES_LINES.IS_VOIDED = FALSE AND SALES.IS_VOIDED= FALSE  ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, divison);
			rs = statement.executeQuery();
			while (rs.next()) {
				Double amount = rs.getDouble(1);
				return amount; 
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return 0d ;
	}
	
	public static Map<String, Double> getExpenseMadeByAssociate(int divison,  Date startDate, Date endDate )
	{
		Map<String, Double> ans = new HashMap<String, Double>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT SUM(REQ_TOTAL),USER_ID FROM EXPENSE_VOUCHERS WHERE   " + 
			 " EXPENSE_DATE > ? AND  EXPENSE_DATE <= ?  AND DIVISION_ID= ?  " + 
			"  AND IS_DELETED = FALSE   GROUP BY USER_ID";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3,divison);
			rs = statement.executeQuery();
			while (rs.next()) {
				Double amount = rs.getDouble(1);
				String user = rs.getString(2);
				ans.put(user, amount);
			}
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans;
	}
	
	
	public static Map<String, Double> getExpenseMadeByAssociateWithoutDivision(  Date startDate, Date endDate, int company )
	{
		Map<String, Double> ans = new HashMap<String, Double>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT SUM(REQ_TOTAL),USER_ID FROM EXPENSE_VOUCHERS WHERE   " + 
			 " EXPENSE_DATE > ? AND  EXPENSE_DATE <= ?  AND COMPANY_ID =?   " + 
			"  AND IS_DELETED = FALSE   GROUP BY USER_ID";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, company);
			rs = statement.executeQuery();
			while (rs.next()) {
				Double amount = rs.getDouble(1);
				String user = rs.getString(2);
				ans.put(user, amount);
			}
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans;
	}
	
	public static Map<String, Double> getSaleMadeByAssociate(int divison,  Date startDate, Date endDate, int company )
	{
		Map<String, Double> ans = new HashMap<String, Double>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String divisionSQL = (divison>0)?" AND SALES.DIVISION_ID = ? ": " ";
			String sql =  " SELECT SUM(SALES_LINES.LINE_TOTAL),USER_ID FROM SALES , SALES_LINES WHERE SALES.ID= SALES_LINES.SALES_ID AND  " + 
			 " SALES.SALES_DATE > ? AND  SALES.SALES_DATE <= ?   AND SALES.COMPANY_ID = ?  " +   divisionSQL  + 
			"  AND  SALES_LINES.IS_VOIDED = FALSE AND SALES.IS_VOIDED= FALSE  GROUP BY USER_ID";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, company);
			if(divison > 0 )
				statement.setInt(4, divison);
			rs = statement.executeQuery();
			while (rs.next()) {
				Double amount = rs.getDouble(1);
				String user = rs.getString(2);
				ans.put(user, amount);
			}
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans;
	}
	
	public static Map<String, Double> getSaleMadeByAssociateWithoutDivision(  Date startDate, Date endDate, int company )
	{
		Map<String, Double> ans = new HashMap<String, Double>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT SUM(SALES_LINES.LINE_TOTAL),USER_ID FROM SALES , SALES_LINES WHERE SALES.ID= SALES_LINES.SALES_ID AND  " + 
			 " SALES.SALES_DATE > ? AND  SALES.SALES_DATE <= ?  AND SALES.COMPANY_ID = ?  " + 
			"  AND  SALES_LINES.IS_VOIDED = FALSE AND SALES.IS_VOIDED= FALSE  GROUP BY USER_ID";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, company);
			rs = statement.executeQuery();
			while (rs.next()) {
				Double amount = rs.getDouble(1);
				String user = rs.getString(2);
				ans.put(user, amount);
			}
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans;
	}
	
	public static Map<String, Double> getSaleMadeByTerritory(int divison,  Date startDate, Date endDate , int company )
	{
		Map<String, Double> ans = new HashMap<String, Double>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String divisionSQL = (divison  >0) ? "  AND SALES.DIVISION_ID = ? ":" ";
			String sql =  " SELECT SUM(SALES.NET_AMOUNT),TERRITORIES.TERRITORY FROM SALES , TERRITORIES WHERE TERRITORIES.ID= SALES.TERRITORY_ID AND  " + 
			 " SALES.SALES_DATE > ? AND  SALES.SALES_DATE <= ?   AND SALES.COMPANY_ID = ?  " +   divisionSQL + 
			"  AND  SALES.IS_VOIDED= FALSE  GROUP BY TERRITORIES.TERRITORY";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, company);
			if(divison > 0 )
				statement.setInt(4, divison);
			rs = statement.executeQuery();
			while (rs.next()) {
				Double amount = rs.getDouble(1);
				String user = rs.getString(2);
				ans.put(user, amount);
			}
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans;
	}
	
	public static Map<String, Double> getReasonWiseSaleLeadsforDivision(int division,   Date startDate, Date endDate , int company)
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Double> ans = new HashMap<String, Double> ();
		try {
			connection = ConnectionCreater.getConnection();
			IReasonCodeService reasonCodeService = (IReasonCodeService) SpringObjectFactory.INSTANCE.getInstance("IReasonCodeService") ;
			String divisionPart = (division==-1)?"":" AND SALES_LEADS.DIVISION_ID = ? ";
			String sql =  " SELECT COUNT(SALES_LEADS.ID),SALES_LEADS.MGR_REASON_ID FROM SALES_LEADS  WHERE   " + 
			"  SALES_LEADS.RELEASED_DATE >= ? AND  SALES_LEADS.RELEASED_DATE <= ?  AND SALES_LEADS.COMPANY_ID = ? AND "   + 
			"   SALES_LEADS.IS_VOIDED = FALSE  " + divisionPart +   "    GROUP BY SALES_LEADS.MGR_REASON_ID ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, company);
			if(division != -1)
				statement.setInt(4, division);
			
			rs = statement.executeQuery();
			while (rs.next()) {
				Integer reasonId  = rs.getInt(2);
				ReasonCode code = null  ;
				if(reasonId> 0 ) {
					code = (ReasonCode) reasonCodeService.getById(reasonId) ;
				}
				Double count = rs.getDouble(1);
				if(code != null)
					ans.put(code.getReason(), count);
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans ;
	}
	
	public static Map<String, Double> getStatusWiseSaleLeadsforDivision(int division,   Date startDate, Date endDate , int company)
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Double> ans = new HashMap<String, Double> ();
		try {
			connection = ConnectionCreater.getConnection();
			String divisionPart = (division==-1)?"":" AND SALES_LEADS.DIVISION_ID = ? ";
			String sql =  " SELECT COUNT(SALES_LEADS.ID),SALES_LEADS.STATUS FROM SALES_LEADS  WHERE   " + 
			"  SALES_LEADS.RELEASED_DATE >= ? AND  SALES_LEADS.RELEASED_DATE <= ?  AND SALES_LEADS.COMPANY_ID = ? AND "   + 
			"   SALES_LEADS.IS_VOIDED = FALSE  " + divisionPart +   "    GROUP BY SALES_LEADS.STATUS ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, company);
			if(division != -1)
				statement.setInt(4, division);
			
			rs = statement.executeQuery();
			while (rs.next()) {
				String statusCode  = rs.getString(2);
				String status = "Unassigned" ;
				if(!Utils.isNullString(statusCode)) {
					FiniteValue fValue=	GeneralSQLs.getFiniteValue(statusCode);
					status =fValue.getDescription();		
				}
				Double count = rs.getDouble(1);
				ans.put(status, count);
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		
		return ans ;
	}
	
	public static Map<String, Double> getAssociateWiseSaleLeadsWithoutDivision( int company,  Date startDate, Date endDate )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Double> ans = new HashMap<String, Double> ();
		try {
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT COUNT(SALES_LEADS.ID),SALES_LEADS.SALES_ASSOCIATE FROM SALES_LEADS  WHERE   " + 
			"  SALES_LEADS.RELEASED_DATE >= ? AND  SALES_LEADS.RELEASED_DATE <= ? AND " + 
			" (SALES_LEADS.COMPANY_ID =  ?  ) AND  SALES_LEADS.IS_VOIDED = FALSE  GROUP BY SALES_LEADS.SALES_ASSOCIATE ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, company);
			rs = statement.executeQuery();
			while (rs.next()) {
				String user  = rs.getString(2);
				Double count = rs.getDouble(1);
				ans.put(user, count);
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		
		return ans ;
	}
	
	public static Map<String, Double> getAssociateWiseSaleLeads(int division,   Date startDate, Date endDate )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Double> ans = new HashMap<String, Double> ();
		try {
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT COUNT(SALES_LEADS.ID),SALES_LEADS.SALES_ASSOCIATE FROM SALES_LEADS  WHERE   " + 
			"  SALES_LEADS.RELEASED_DATE >= ? AND  SALES_LEADS.RELEASED_DATE <= ? AND SALES_LEADS.DIVISION_ID = ? AND " + 
			"   SALES_LEADS.IS_VOIDED = FALSE  GROUP BY SALES_LEADS.SALES_ASSOCIATE ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, division);
			rs = statement.executeQuery();
			while (rs.next()) {
				String user  = rs.getString(2);
				Double count = rs.getDouble(1);
				ans.put(user, count);
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		
		return ans ;
	}
	
	public static Map<String, Double> getStatusWiseSaleLeads(int division, String associate,  Date startDate, Date endDate )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Double> ans = new HashMap<String, Double> ();
		try {
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT COUNT(SALES_LEADS.ID),SALES_LEADS.STATUS FROM SALES_LEADS  WHERE   " + 
			"  SALES_LEADS.RELEASED_DATE >= ? AND  SALES_LEADS.RELEASED_DATE <= ? AND SALES_LEADS.DIVISION_ID = ? AND " + 
			" (SALES_LEADS.SALES_ASSOCIATE =  ?  ) AND  SALES_LEADS.IS_VOIDED = FALSE  GROUP BY SALES_LEADS.STATUS ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, division);
			statement.setString(4, associate);
			rs = statement.executeQuery();
			while (rs.next()) {
				String statusCode  = rs.getString(2);
				String status = "Open" ;
				if(!Utils.isNullString(statusCode)) {
					FiniteValue fValue=	GeneralSQLs.getFiniteValue(statusCode);
					status =fValue.getDescription();		
				}
				Double count = rs.getDouble(1);
				ans.put(status, count);
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		
		return ans ;
	}
	
	public static Map<String, Double> getItemWiseSale(int division, String associate,  Date startDate, Date endDate )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Double> ans = new HashMap<String, Double> ();
		try {
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT SUM(SALES_LINES.LINE_TOTAL),ITEMS.ITEM_NAME FROM SALES , SALES_LINES  ,  SKUS, ITEMS WHERE SALES.ID= SALES_LINES.SALES_ID AND  " + 
			 " SALES_LINES.SKU_ID = SKUS.ID AND SKUS.ITEM_ID = ITEMS.ID AND  SALES.SALES_DATE >= ? AND  SALES.SALES_DATE <= ? AND SALES.DIVISION_ID = ? AND " + 
			" SALES_LINES.USER_ID= ? AND  SALES_LINES.IS_VOIDED = FALSE AND SALES.IS_VOIDED= FALSE GROUP BY ITEMS.ITEM_NAME ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, division);
			statement.setString(4, associate);
			rs = statement.executeQuery();
			while (rs.next()) {
				String itemName = rs.getString(2);
				Double amount = rs.getDouble(1);
				ans.put(itemName, amount);
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans ;
		
	}
	
	
	public static Map<String, Double> getProductWiseSale(int division,  Date startDate, Date endDate, int companyId )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Double> ans = new HashMap<String, Double> ();
		try {
			connection = ConnectionCreater.getConnection();
			String divisionSQL = (division> 0) ? " AND SALES.DIVISION_ID = ? " : "";
			String sql =  " SELECT SUM(SALES_LINES.LINE_TOTAL),PRODUCTS.PRODUCT_NAME FROM SALES , SALES_LINES  ,  SKUS, ITEMS,PRODUCTS WHERE SALES.ID= SALES_LINES.SALES_ID AND  " + 
			 " SALES_LINES.SKU_ID = SKUS.ID AND SKUS.ITEM_ID = ITEMS.ID AND ITEMS.PRODUCT_ID=PRODUCTS.ID AND SALES.SALES_DATE >= ? AND  SALES.SALES_DATE <= ?  "  +
			 " AND SALES.COMPANY_ID = ?  " +   divisionSQL + "  AND " + 
			" SALES_LINES.IS_VOIDED = FALSE AND SALES.IS_VOIDED= FALSE GROUP BY PRODUCTS.PRODUCT_NAME ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, companyId);
			if (division  > 0)
				statement.setInt(4, division);
			rs = statement.executeQuery();
			while (rs.next()) {
				String productName = rs.getString(2);
				Double amount = rs.getDouble(1);
				ans.put(productName, amount);
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans ;
		
	}
	
	public static Map<String, Double> getCategoryWiseSale(int division,  Date startDate, Date endDate, int company )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Double> ans = new HashMap<String, Double> ();
		try {
			connection = ConnectionCreater.getConnection();
			String divisionSQL =  (division >0 )? " AND SALES.DIVISION_ID = ?  " : "" ;
			String sql =  " SELECT SUM(SALES_LINES.LINE_TOTAL),CATEGORIES.CATEGORY_NAME FROM SALES , SALES_LINES  ,  SKUS, ITEMS,PRODUCTS, CATEGORIES WHERE SALES.ID= SALES_LINES.SALES_ID AND  " + 
			 " SALES_LINES.SKU_ID = SKUS.ID AND SKUS.ITEM_ID = ITEMS.ID AND ITEMS.PRODUCT_ID=PRODUCTS.ID AND PRODUCTS.CATEGORY_ID = CATEGORIES.ID AND SALES.SALES_DATE >= ? AND  SALES.SALES_DATE <= ? " +  
			 " AND SALES.COMPANY_ID = ?  " +  divisionSQL +  " AND " + 
			" SALES_LINES.IS_VOIDED = FALSE AND SALES.IS_VOIDED= FALSE GROUP BY CATEGORIES.CATEGORY_NAME ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, company);
			if(division > 0)
				statement.setInt(4, division);
			rs = statement.executeQuery();
			while (rs.next()) {
				String productName = rs.getString(2);
				Double amount = rs.getDouble(1);
				ans.put(productName, amount);
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans ;
		
	}
	
	public static Map<String, Double> getBrandWiseSale(int division,  Date startDate, Date endDate, int company )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Double> ans = new HashMap<String, Double> ();
		try {
			connection = ConnectionCreater.getConnection();
			String divisionSQL = (division >0) ? " AND SALES.DIVISION_ID = ? " : "";
			String sql =  " SELECT SUM(SALES_LINES.LINE_TOTAL),BRANDS.BRAND_NAME FROM SALES , SALES_LINES  ,  SKUS, ITEMS,BRANDS WHERE SALES.ID= SALES_LINES.SALES_ID AND  " + 
			 " SALES_LINES.SKU_ID = SKUS.ID AND SKUS.ITEM_ID = ITEMS.ID AND ITEMS.BRAND_ID=BRANDS.ID AND SALES.SALES_DATE >= ? AND  SALES.SALES_DATE <= ? " +
			 " AND SALES.COMPANY_ID = ?  " + 	divisionSQL + "  AND " + 
			" SALES_LINES.IS_VOIDED = FALSE AND SALES.IS_VOIDED= FALSE GROUP BY BRANDS.BRAND_NAME ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, company);
			if(division > 0)
				statement.setInt(4, division);
			rs = statement.executeQuery();
			while (rs.next()) {
				String productName = rs.getString(2);
				Double amount = rs.getDouble(1);
				ans.put(productName, amount);
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans ;
		
	}
	
	public static Map<String, Double> getDivisionWiseSale(Date startDate, Date endDate, int company )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Double> ans = new HashMap<String, Double> ();
		try {
			connection = ConnectionCreater.getConnection();
			String sql =  " SELECT SUM(SALES_LINES.LINE_TOTAL),DIVISIONS.DIVISION_NAME FROM SALES , SALES_LINES  ,  SKUS, ITEMS,DIVISIONS WHERE SALES.ID= SALES_LINES.SALES_ID AND  " + 
			 " SALES_LINES.SKU_ID = SKUS.ID AND SKUS.ITEM_ID = ITEMS.ID AND SALES.DIVISION_ID = DIVISIONS.ID AND SALES.SALES_DATE >= ? AND  SALES.SALES_DATE <= ? " +
			 " AND SALES.COMPANY_ID = ?    AND " + 
			" SALES_LINES.IS_VOIDED = FALSE AND SALES.IS_VOIDED= FALSE GROUP BY DIVISIONS.DIVISION_NAME ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, company);
			rs = statement.executeQuery();
			while (rs.next()) {
				String productName = rs.getString(2);
				Double amount = rs.getDouble(1);
				ans.put(productName, amount);
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans ;
		
	}
	
	public static Map<String, Double> getItemWiseSale(int division,  Date startDate, Date endDate, int company )
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		Map<String, Double> ans = new HashMap<String, Double> ();
		try {
			connection = ConnectionCreater.getConnection();
			String divisionSQL = (division > 0)?" AND SALES.DIVISION_ID = ? ": " ";
			String sql =  " SELECT SUM(SALES_LINES.LINE_TOTAL),ITEMS.ITEM_NAME FROM SALES , SALES_LINES  ,  SKUS, ITEMS WHERE SALES.ID= SALES_LINES.SALES_ID AND  " + 
			 " SALES_LINES.SKU_ID = SKUS.ID AND SKUS.ITEM_ID = ITEMS.ID AND  SALES.SALES_DATE >= ? AND  SALES.SALES_DATE <= ? " + 
			 " AND SALES.COMPANY_ID = ?  "   +divisionSQL  + " AND " + 
			" SALES_LINES.IS_VOIDED = FALSE AND SALES.IS_VOIDED= FALSE GROUP BY ITEMS.ITEM_NAME ";
			statement = connection.prepareStatement(sql);
			statement.setDate(1, startDate);
			statement.setDate(2, endDate);
			statement.setInt(3, company);
			if(division > 0)
			statement.setInt(4, division);
			rs = statement.executeQuery();
			while (rs.next()) {
				String productName = rs.getString(2);
				Double amount = rs.getDouble(1);
				ans.put(productName, amount);
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return ans ;
		
	}

}
