package com.rainbow.framework.setup.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rainbow.crm.database.ConnectionCreater;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.framework.setup.model.Metadata;

public class DataSetupSQL {

	public static List<Metadata> getMetadata(){
	 	Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		List<Metadata> ans = new ArrayList<Metadata>();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   " SELECT * FROM METADATA " ;
			statement = connection.prepareStatement(sql);
			rs = statement.executeQuery() ;
			while (rs.next()) {
				Metadata metadata = new Metadata();
				metadata.setObjectName(rs.getString(1));
				metadata.setClassName(rs.getString(2));
				ans.add(metadata);
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return ans;
	}
	
	
	
	public static Metadata getMetadataforClass(String className){
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		Metadata ans = new Metadata();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   " SELECT * FROM METADATA WHERE CLASS_NAME=?" ;
			statement = connection.prepareStatement(sql);
			statement.setString(1, className);
			rs = statement.executeQuery() ;
			
			while (rs.next()) {
				ans.setObjectName(rs.getString(1));
				ans.setClassName(rs.getString(2));
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return ans;
	}
	
	
}
