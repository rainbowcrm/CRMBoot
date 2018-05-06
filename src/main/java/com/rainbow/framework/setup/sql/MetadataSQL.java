package com.rainbow.framework.setup.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.config.model.ConfigLine;
import com.rainbow.crm.database.ConnectionCreater;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.framework.setup.model.EntityField;
import com.rainbow.framework.setup.model.Metadata;

public class MetadataSQL {

	public static List<Metadata> getMetadata() {
		List<Metadata> metaDataList = new ArrayList<Metadata>(); 
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String sql = "Select * from METADATA ";
			statement = connection.prepareStatement(sql);
			rs = statement.executeQuery();
			while (rs.next()) {
				String  entity = rs.getString("ENTITY");
				String className =  rs.getString("CLASS_NAME");
				FiniteValue metaDataType =new FiniteValue (rs.getString("METADATA_TYPE"));
				boolean isDivSpecific = rs.getBoolean("IS_DIVISION_SPEC");
				Metadata metaData  = new Metadata(entity,className,metaDataType,isDivSpecific);
				metaDataList.add(metaData);
			}
		} catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return metaDataList;
	}
	
	
	public static List<Metadata> getTransactionEntities(){
	 	Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		List<Metadata> ans = new ArrayList<Metadata>();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   " SELECT * FROM METADATA WHERE METADATA_TYPE = 'TRANS' " ;
			statement = connection.prepareStatement(sql);
			rs = statement.executeQuery() ;
			while (rs.next()) {
				String  entity = rs.getString("ENTITY");
				String className =  rs.getString("CLASS_NAME");
				FiniteValue metaDataType =new FiniteValue (rs.getString("METADATA_TYPE"));
				boolean isDivSpecific = rs.getBoolean("IS_DIVISION_SPEC");
				String hqlClass = rs.getString("HQL_CLASS");
				String desc = rs.getString("DESCRIPTION");
				String dateField = rs.getString("DATE_FIELD");
				Metadata metaData  = new Metadata(entity,className,metaDataType,isDivSpecific,hqlClass,desc,dateField);
				ans.add(metaData);
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return ans;
	}
	
	public static Metadata getMetaDataforEntity(String entity){
	 	Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   " SELECT * FROM METADATA WHERE ENTITY = ? " ;
			statement = connection.prepareStatement(sql);
			statement.setString(1, entity);
			rs = statement.executeQuery() ;
			while (rs.next()) {
				String className =  rs.getString("CLASS_NAME");
				FiniteValue metaDataType =new FiniteValue (rs.getString("METADATA_TYPE"));
				boolean isDivSpecific = rs.getBoolean("IS_DIVISION_SPEC");
				String hql = rs.getString("HQL_CLASS");
				String desc = rs.getString("DESCRIPTION") ;
				String dateField = rs.getString("DATE_FIELD");
				Metadata metaData  = new Metadata(entity,className,metaDataType,isDivSpecific,hql,desc,dateField);
				return  metaData;
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return null;
	}
	
	public static Map<String,String> getAllEntityFields (String entity) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		Map<String,String> ans = new LinkedHashMap<String, String>();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   " SELECT * FROM ENTITY_FIELDS WHERE ENTITY = ? ORDER BY JOIN_HQL_CLAUSE  " ;
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
	
	public static Map<String,String> getNumericEntityFields (String entity) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		Map<String,String> ans = new HashMap<String, String>();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   " SELECT * FROM ENTITY_FIELDS WHERE ENTITY = ? and DATA_TYPE = 'NUMER'" ;
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
	
	
	public static List<EntityField> getEntityFields (String entity) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		List<EntityField> fields =new ArrayList<EntityField> ();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   " SELECT * FROM ENTITY_FIELDS WHERE ENTITY = ? " ;
			statement = connection.prepareStatement(sql);
			statement.setString(1, entity);
			rs = statement.executeQuery() ;
			while (rs.next()) {
				String displayField = rs.getString("KEY_FIELD");
				String hqlField = rs.getString("HQL_KEY_FIELD");
				String desc = rs.getString("DESCRIPTION");
				String dType = rs.getString("DATA_TYPE");
				FiniteValue dataType = new FiniteValue(dType);
				String fvType = rs.getString("FV_TYPE");
				String joinClause = rs.getString("JOIN_HQL_CLAUSE");
				String joinCondition = rs.getString("JOIN_HQL_CONDITION");
				EntityField efield = new EntityField(entity,displayField,hqlField,desc,dataType,fvType,joinClause,joinCondition);
				fields.add(efield);
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return fields;
	}
	
	public static EntityField getEntityField (String entity, String keyField) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   " SELECT * FROM ENTITY_FIELDS WHERE ENTITY = ? AND HQL_KEY_FIELD = ? " ;
			statement = connection.prepareStatement(sql);
			statement.setString(1, entity);
			statement.setString(2, keyField);
			rs = statement.executeQuery() ;
			
			while (rs.next()) {
				String displayField = rs.getString("KEY_FIELD");
				String hqlField = rs.getString("HQL_KEY_FIELD");
				String desc = rs.getString("DESCRIPTION");
				String dType = rs.getString("DATA_TYPE");
				FiniteValue dataType = new FiniteValue(dType);
				String fvType = rs.getString("FV_TYPE");
				String joinClause = rs.getString("JOIN_HQL_CLAUSE");
				String joinCondition = rs.getString("JOIN_HQL_CONDITION");
				EntityField efield = new EntityField(entity,displayField,hqlField,desc,dataType,fvType,joinClause,joinCondition);
				return efield;
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return null;
	}
	
}
