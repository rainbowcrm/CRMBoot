package com.rainbow.crm.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;




public class ConnectionCreater {

	static DataSource dataSource  = null ;
	private static void initialize() {
		try { 
			Context initContext  = new InitialContext();
			dataSource  = (DataSource)initContext.lookup("java:comp/env/jdbc/mysqldb");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException{
		if (dataSource == null)
			  initialize();
	//	Log4jWebLifeCycle webLifeCycle = WebLoggerContextUtils.getWebLifeCycle(null);
             // webLifeCycle.setLoggerContext();
		 
		return dataSource.getConnection() ; 
	}
	
	public static void close(Connection conn, PreparedStatement statement, ResultSet resultSet )  {
		try { 
			if(conn != null) conn.close(); 
			if (statement != null) statement.close(); 
			if (resultSet != null) resultSet.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
