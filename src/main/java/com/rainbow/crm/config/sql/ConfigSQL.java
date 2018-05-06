package com.rainbow.crm.config.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.config.model.ConfigLine;
import com.rainbow.crm.config.model.ConfigSet;
import com.rainbow.crm.database.ConnectionCreater;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.utils.Utils;

public class ConfigSQL {

	private static void deleteConfigSet(int company) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String sql = " DELETE FROM COMPANY_CONFIGURATION WHERE COMPANY_ID = ?  ";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, company);
			statement.executeUpdate();
		} catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
	}

	public static void saveConfigSet(ConfigSet configSet, int company) {
		deleteConfigSet(company);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String sql = " INSERT INTO COMPANY_CONFIGURATION(CONFIG_CODE,COMPANY_ID,VALUE,CREATED_DATE) VALUES (?,?,?,?) ";
			statement = connection.prepareStatement(sql);
			Iterator it = configSet.getConfigMap().keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				List<ConfigLine> lines = configSet.getConfigMap().get(key);
				for (ConfigLine configLine : lines) {
					statement.setString(1, configLine.getConfigCode());
					statement.setInt(2, company);
					statement.setString(3, configLine.getValue());
					statement.setDate(4,
							new java.sql.Date(new java.util.Date().getDate()));
					statement.addBatch();
				}
			}
			statement.executeBatch();
		} catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
	}

	public static ConfigSet getConfigSet(int company) {
		ConfigSet set = new ConfigSet();
		set.setCompany(company);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String sql = "Select * from CONFIG_GROUPS ";
			statement = connection.prepareStatement(sql);
			rs = statement.executeQuery();
			while (rs.next()) {
				String groupName = rs.getString(1);
				List<ConfigLine> configLines = getConfigLinesforGroup(company,
						groupName);
				set.getConfigMap().put(groupName, configLines);
			}
		} catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return set;
	}

	
	public static String getConfigforCode(int company , String configCode) {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = ConnectionCreater.getConnection();
			String sql = "Select BC.DEFAULT_VALUE,CC.VALUE "
					+ "  from BASE_CONFIGURATION BC LEFT JOIN  COMPANY_CONFIGURATION CC ON  ( BC.CODE= CC.CONFIG_CODE AND CC.COMPANY_ID =? ) WHERE BC.CODE= ? " ;
			statement = connection.prepareStatement(sql);
			statement.setInt(1, company);
			statement.setString(2, configCode);
			rs = statement.executeQuery();
			if (rs.next()) {
				String defaultValue = rs.getString(1);
				String value = rs.getString(2);
				if (value  == null)
					return defaultValue;
				else
					return value ;
			}
			
		}catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return null;
		
	}
		
	private static List<ConfigLine> getConfigLinesforGroup(int company,
			String groupName) {
		List<ConfigLine> list = new ArrayList<ConfigLine>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String sql = "Select BC.CODE,BC.CONFIG_DESCRIPTION,BC.CONFIG_GROUP,BC.VALUE_TYPE,BC.VALUE_GENERATOR,BC.DEFAULT_VALUE,CC.VALUE "
					+ "  from BASE_CONFIGURATION BC LEFT JOIN  COMPANY_CONFIGURATION CC ON  ( BC.CODE= CC.CONFIG_CODE AND CC.COMPANY_ID =? ) WHERE BC.CONFIG_GROUP= ? ";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, company);
			statement.setString(2, groupName);
			rs = statement.executeQuery();
			while (rs.next()) {
				ConfigLine configLine = new ConfigLine();
				configLine.setCompany(company);
				configLine.setConfigCode(rs.getString(1));
				configLine.setDescription(rs.getString(2));
				configLine.setGroup(rs.getString(3));
				configLine.setValueType(new FiniteValue(rs.getString(4)));
				configLine.setValueGenerator(rs.getString(5));
				String defaultValue = rs.getString(6);
				configLine.setDefaultValue(defaultValue);
				String value = rs.getString(7);
				if (value != null)
					configLine.setValue(value);
				else
					configLine.setValue(defaultValue);
				list.add(configLine);
			}

		} catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return list;
	}
}
