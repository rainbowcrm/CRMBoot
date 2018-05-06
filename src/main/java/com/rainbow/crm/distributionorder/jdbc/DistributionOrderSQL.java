package com.rainbow.crm.distributionorder.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.rainbow.crm.config.model.ConfigLine;
import com.rainbow.crm.database.ConnectionCreater;
import com.rainbow.crm.logger.Logwriter;

public class DistributionOrderSQL {
	
	public static int getDivisionWithInventory(int salesOrderID, int company, int count){
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			connection = ConnectionCreater.getConnection();
			String sql = "Select  COUNT(ORD.SKU_ID) AS CT,INV.DIVISION_ID from SALES_LINES ORD ,INVENTORY INV WHERE ORD.SKU_ID = INV.SKU_ID " + 
			"AND INV.CURRENT_QTY >= ORD.QTY AND ORD.SALES_ID = ?  GROUP BY INV.DIVISION_ID   HAVING  CT =  ? ORDER BY  (INV.CURRENT_QTY - ORD.QTY ) ";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, salesOrderID);
			statement.setInt(2, count);
			rs = statement.executeQuery();
			while (rs.next()) {
				return rs.getInt(2);
			}
		} catch (SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		} finally {
			ConnectionCreater.close(connection, statement, rs);
		}
		return -1 ;

	}

}
