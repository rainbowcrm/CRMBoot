package com.rainbow.framework.nextup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.rainbow.crm.database.ConnectionCreater;
import com.rainbow.crm.logger.Logwriter;

public class NextUPSQL {
	
	
	private static void updateSEQValue(String program, int division, int company) {
		 Connection connection = null;
			PreparedStatement statement = null;
			ResultSet rs  = null ;
			try {
				connection  = ConnectionCreater.getConnection() ;
				String sql = " UPDATE NEXTUP_VALUES SET SEQ_VALUE  =  SEQ_VALUE + 1 WHERE COMPANY_ID = ? AND DIVISION_ID = ? AND PROGRAM = ? " ;
				statement = connection.prepareStatement(sql) ;
				statement.setInt(1, company);
				statement.setInt(2, division);
				statement.setString(3, program);
				statement.executeUpdate();
			}catch(SQLException ex) {
				Logwriter.INSTANCE.error(ex);
			}finally {
				ConnectionCreater.close(connection, statement, rs);	
			}
	  }
		
	  private static void insertSEQValue(String program, int division, int company) {
		  Connection connection = null;
			PreparedStatement statement = null;
			ResultSet rs  = null ;
			try {
				connection  = ConnectionCreater.getConnection() ;
				String sql = " INSERT INTO NEXTUP_VALUES(COMPANY_ID,DIVISION_ID,PROGRAM,SEQ_VALUE) VALUES (?,?,?,1) " ;
				statement = connection.prepareStatement(sql) ;
				statement.setInt(1, company);
				statement.setInt(2, division);
				statement.setString(3, program);
				statement.executeUpdate();
			}catch(SQLException ex) {
				Logwriter.INSTANCE.error(ex);
			}finally {
				ConnectionCreater.close(connection, statement, rs);	
			}
	  }

	
	  public synchronized static int getNextPKValue(String program, int division, int company) {
		   Connection connection = null;
			PreparedStatement statement = null;
			ResultSet rs  = null ;
			Map<String,String> ans = new HashMap<String,String>();
			try {
				connection  = ConnectionCreater.getConnection() ;
				String sql =   "Select max(SEQ_VALUE) from NEXTUP_VALUES  where COMPANY_ID =? and DIVISION_ID = ? and  PROGRAM = ? " ;
				statement = connection.prepareStatement(sql);
				statement.setInt(1, company);
				statement.setInt(2, division);
				statement.setString(3, program);
				rs = statement.executeQuery() ;
				if (rs.next()) {
					Object obj = rs.getObject(1);
					if (obj == null) {
						insertSEQValue(program,division,company);
						return 1;
					} else {
						int val = Integer.parseInt(String.valueOf(obj)) ;
						updateSEQValue(program,division,company);
						return  ++val  ;
					}
				}
			}catch(SQLException ex) {
				Logwriter.INSTANCE.error(ex);
			}finally {
				ConnectionCreater.close(connection, statement, rs);	
			}
			return 1;
	   }
	  
	  
	  private static  NextUpConfig.NextUpComponent getComponent(NextUpConfig config,ResultSet rs , int seq ) throws SQLException {
		  NextUpConfig.NextUpComponent comp =  config.new NextUpComponent();
		  String type = rs.getString("FIELD"+ seq + "_TYPE");
		  int width = rs.getInt("FIELD"+ seq + "_WIDTH");
		  String value = rs.getString("FIELD"+ seq + "_VAULE");
		  comp.setFieldType(type);
		  comp.setFieldValue(value);
		  comp.setFieldWidth(width);
		  return comp ;
	  }
	  
	  public static NextUpConfig getNextUpConfig(int company,String program) {
	  	NextUpConfig nextUpConfig = new NextUpConfig();
	  	Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		Map<String,String> ans = new HashMap<String,String>();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   "Select *  from NEXTUP_CONFIG  where COMPANY_ID in (-1,?) and  PROGRAM = ? ORDER BY COMPANY_ID DESC " ;
			statement = connection.prepareStatement(sql);
			statement.setInt(1, company);
			statement.setString(2, program);
			rs = statement.executeQuery() ;
			if (rs.next()) {
				nextUpConfig.setCompany(rs.getInt("COMPANY_ID"));
				nextUpConfig.setProgram(rs.getString("PROGRAM"));
				NextUpConfig.NextUpComponent comp1= getComponent(nextUpConfig , rs , 1);
				NextUpConfig.NextUpComponent comp2= getComponent(nextUpConfig , rs , 2);
				NextUpConfig.NextUpComponent comp3= getComponent(nextUpConfig , rs , 3);
				NextUpConfig.NextUpComponent comp4= getComponent(nextUpConfig , rs , 4);
				nextUpConfig.setComponent1(comp1);
				nextUpConfig.setComponent2(comp2);
				nextUpConfig.setComponent3(comp3);
				nextUpConfig.setComponent4(comp4);
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return nextUpConfig;
	  }
}
