package com.rainbow.crm.custcategory.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.rainbow.crm.custcategory.model.CustCategoryComponent;
import com.rainbow.crm.database.ConnectionCreater;
import com.rainbow.crm.logger.Logwriter;

public class CustCategorySQLs {
	
	public static CustCategoryComponent getComponentByKey(String entity, String key) 
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		CustCategoryComponent ans = null;
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   " SELECT * FROM CUST_CATEGORY_COMPONENTS WHERE ENTITY = ? AND HQL_KEY_FIELD = ? ORDER BY JOIN_HQL_CLAUSE  " ;
			statement = connection.prepareStatement(sql);
			statement.setString(1, entity);
			statement.setString(2, key);
			rs = statement.executeQuery() ;
			while (rs.next()) {
				String displayField = rs.getString("KEY_FIELD");
				String hqlField = rs.getString("HQL_KEY_FIELD");
				String dataType = rs.getString("DATA_TYPE");
				String joinHQLClause = rs.getString("JOIN_HQL_CLAUSE");
				Boolean isAggregated = rs.getBoolean("IS_AGGREGATED_FIELD");
				String preCondition = rs.getString("PRECONDITION_FIELD");
				ans =new CustCategoryComponent(entity, key, hqlField, dataType, joinHQLClause, isAggregated, preCondition);
				
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return ans;
		
	}
	
	public static Map<String,String> getAllCriteriaFields (String entity) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		Map<String,String> ans = new LinkedHashMap<String, String>();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   " SELECT * FROM CUST_CATEGORY_COMPONENTS WHERE ENTITY = ? ORDER BY JOIN_HQL_CLAUSE  " ;
			statement = connection.prepareStatement(sql);
			statement.setString(1, entity);
			rs = statement.executeQuery() ;
			while (rs.next()) {
				String displayField = rs.getString("KEY_FIELD");
				String hqlField = rs.getString("HQL_KEY_FIELD");
				ans.put(hqlField, displayField);
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return ans;
	}
	
	public static String getDataType( String entity, String hqlKeyField)
	{
		Connection connection = null ;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		Map<String,String> ans = new LinkedHashMap<String, String>();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   " SELECT * FROM CUST_CATEGORY_COMPONENTS WHERE ENTITY = ? AND  HQL_KEY_FIELD = ?  " ;
			statement = connection.prepareStatement(sql);
			statement.setString(1, entity);
			statement.setString(2, hqlKeyField);
			rs = statement.executeQuery() ;
			if (rs.next()) {
				String dataType = rs.getString("DATA_TYPE");
				return dataType;
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return "STR";
	}

}
