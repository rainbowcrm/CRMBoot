package com.rainbow.crm.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.division.service.DivisionService;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.utils.Utils;

public class GeneralSQLs {
	
	public static boolean isUserValid(String user, String password){
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   "Select * from users where user_id = ? and password = ? " ;
			statement = connection.prepareStatement(sql);
			statement.setString(1, user);
			statement.setString(2, password);
			rs = statement.executeQuery() ;
			if (rs.next()) {
				return true; 
			}else
				return false; 
			
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return false;
	}
	
	public static Map<String,String> getFiniteValuesWithSelect(String typeCode) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		Map<String,String> finiteValues = new LinkedHashMap<String,String>();
		finiteValues.put("-1", "--Select One--");
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   "Select CODE,DESCRIPTION from FINITE_VALUES where TYPE_CODE = ? " ;
			statement = connection.prepareStatement(sql);
			statement.setString(1, typeCode);
			rs = statement.executeQuery() ;
			while (rs.next()) {
				String code = rs.getString(1);
				String desc = rs.getString(2);
				finiteValues.put(code,desc);
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return finiteValues;
	}
	
	
	public static Map<String,String> getFiniteValues(String typeCode) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		Map<String,String> finiteValues = new HashMap<String,String>();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   "Select CODE,DESCRIPTION from FINITE_VALUES where TYPE_CODE = ? " ;
			statement = connection.prepareStatement(sql);
			statement.setString(1, typeCode);
			rs = statement.executeQuery() ;
			while (rs.next()) {
				String code = rs.getString(1);
				String desc = rs.getString(2);
				finiteValues.put(code,desc);
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return finiteValues;
	}
	
	public static FiniteValue getFiniteValue(String code) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		FiniteValue finiteValue = new FiniteValue();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   "Select CODE,DESCRIPTION,IS_DEFAULT from FINITE_VALUES where CODE = ? " ;
			statement = connection.prepareStatement(sql);
			statement.setString(1, code);
			rs = statement.executeQuery() ;
			if (rs.next()) {
				finiteValue = new FiniteValue();
				finiteValue.setCode(code);
				finiteValue.setDefault(rs.getBoolean(3));
				finiteValue.setDescription(rs.getString(2));
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return finiteValue;
	}
	
	public static FiniteValue getFiniteValueFromDescription(String descrption) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		FiniteValue finiteValue = new FiniteValue();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   "Select CODE,DESCRIPTION,IS_DEFAULT from FINITE_VALUES where DESCRIPTION = ? " ;
			statement = connection.prepareStatement(sql);
			statement.setString(1, descrption);
			rs = statement.executeQuery() ;
			if (rs.next()) {
				finiteValue = new FiniteValue();
				finiteValue.setCode(rs.getString(1));
				finiteValue.setDefault(rs.getBoolean(3));
				finiteValue.setDescription(rs.getString(2));
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return finiteValue;
	}

	
	public static List<FiniteValue> getAllFiniteValues () {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		List<FiniteValue> finiteValues = new ArrayList<FiniteValue>();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   "Select CODE,TYPE_CODE,DESCRIPTION,IS_DEFAULT from FINITE_VALUES  " ;
			statement = connection.prepareStatement(sql);
			rs = statement.executeQuery() ;
			while (rs.next()) {
				String code = rs.getString(1);
				String typeCode = rs.getString(2);
				String desc = rs.getString(3);
				boolean def = rs.getBoolean(4);
				FiniteValue val = new FiniteValue(typeCode,code,desc,def);
				finiteValues.add(val);
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return finiteValues;
	}
	
	public static Map<String,String> getDefaultUOMs () {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		Map<String,String> ans = new HashMap<String,String>();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   "Select ID,UOM_CODE from UOMS where company_id = -1  " ;
			statement = connection.prepareStatement(sql);
			rs = statement.executeQuery() ;
			while (rs.next()) {
				Integer id = rs.getInt(1);
				String typeCode = rs.getString(2);
				ans.put(String.valueOf(id), typeCode);
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return ans;
	}
	
	
  private static void updatePKValue(String tableName) {
	  Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql = " UPDATE GENERATED_PKS set CURRENT_MAX= CURRENT_MAX + 1 where TBL_NAME  = ? " ;
			statement = connection.prepareStatement(sql) ;
			statement.setString(1, tableName);
			statement.executeUpdate() ;
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
  }
	
  private static void insertPKValue(String tableName) {
	  Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql = " INSERT INTO GENERATED_PKS(TBL_NAME,CURRENT_MAX) VALUES (?,1) " ;
			statement = connection.prepareStatement(sql) ;
			statement.setString(1, tableName);
			statement.executeUpdate();
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
  }
   public synchronized static int getNextPKValue(String tableName) {
	   Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		Map<String,String> ans = new HashMap<String,String>();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   "Select max(current_max) from GENERATED_PKS  where TBL_NAME = ? " ;
			statement = connection.prepareStatement(sql);
			statement.setString(1, tableName);
			rs = statement.executeQuery() ;
			if (rs.next()) {
				Object obj = rs.getObject(1);
				if (obj == null) {
					insertPKValue(tableName);
					return 1;
				} else {
					int val = Integer.parseInt(String.valueOf(obj)) ;
					updatePKValue(tableName);
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
	
   public static int getItemSoldQty(int itemId, Date fromDate , Date toDate , int divisionId) {
	   Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		Map<String,String> ans = new HashMap<String,String>();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String divisionSQL = (divisionId>-1)?"and sales.division_id = ?":"" ;
			String sql = "Select sum(sllines.qty) from SALES sales,SALES_LINES sllines,SKUS skus  where sales.id = sllines.sales_id   " +
			" and  sllines.sku_id = skus.id and sales.SALES_DATE >= ? and sales.SALES_DATE<= ?  and skus.item_id = ?  " + divisionSQL ;
			statement = connection.prepareStatement(sql);
			statement.setTimestamp(1, new java.sql.Timestamp(fromDate.getTime()));
			statement.setTimestamp(2, new java.sql.Timestamp(toDate.getTime()));
			statement.setInt(3, itemId);
			if( divisionId > -1)
				statement.setInt(4, divisionId);
			rs = statement.executeQuery() ;
			if (rs.next()) {
				return rs.getInt(1);
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return 0;
		
   }
   
   public static int getTotalSoldQty( Date fromDate , Date toDate , int divisionId) {
	   Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		Map<String,String> ans = new HashMap<String,String>();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String divisionSQL = (divisionId>-1)?"and sales.division_id = ?":"" ;
			String sql = "Select sum(sllines.LINE_TOTAL) from SALES sales,SALES_LINES sllines,SKUS skus  where sales.id = sllines.sales_id  " +
			" and  sllines.sku_id = skus.id and sales.SALES_DATE >= ? and sales.SALES_DATE<= ?  " + divisionSQL ;
			statement = connection.prepareStatement(sql);
			statement.setTimestamp(1, new java.sql.Timestamp(fromDate.getTime()));
			statement.setTimestamp(2, new java.sql.Timestamp(toDate.getTime()));
			if( divisionId > -1)
				statement.setInt(3, divisionId);
			rs = statement.executeQuery() ;
			if (rs.next()) {
				return rs.getInt(1);
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return 0;
		
   }

   
   public static int getSalesMenSoldQty(String userId, Date fromDate , Date toDate , int divisionId) {
	   Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		Map<String,String> ans = new HashMap<String,String>();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql = "Select sum(sllines.LINE_TOTAL) from SALES sales,SALES_LINES sllines,SKUS skus  where sales.id = sllines.sales_id and sales.division_id = ?  " +
			" and  sllines.sku_id = skus.id and sales.SALES_DATE >= ? and sales.SALES_DATE<= ?  and sllines.USER_ID = ?  " ;
			statement = connection.prepareStatement(sql);
			statement.setInt(1, divisionId);
			statement.setTimestamp(2, new java.sql.Timestamp(fromDate.getTime()));
			statement.setTimestamp(3, new java.sql.Timestamp(toDate.getTime()));
			statement.setString(4, userId);
			rs = statement.executeQuery() ;
			if (rs.next()) {
				return rs.getInt(1);
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return 0;
		
   }

   public static int getCategorySoldQty(int categoryId, Date fromDate , Date toDate , int divisionId) {
	   Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		Map<String,String> ans = new HashMap<String,String>();
		try {
			String divisionSQL = (divisionId>-1)?"and sales.division_id = ?":"" ;
			connection  = ConnectionCreater.getConnection() ;
			String sql = "Select sum(sllines.LINE_TOTAL) from SALES sales,SALES_LINES sllines,SKUS skus,items,products  where sales.id = sllines.sales_id   " +
			" and skus.item_id = items.id and items.product_id = products.id and sllines.sku_id = skus.id and sales.SALES_DATE >= ? and sales.SALES_DATE<= ?  and products.category_id = ?  " 
					 + divisionSQL;
			statement = connection.prepareStatement(sql);
			
			statement.setTimestamp(1, new java.sql.Timestamp(fromDate.getTime()));
			statement.setTimestamp(2, new java.sql.Timestamp(toDate.getTime()));
			statement.setInt(3, categoryId);
			if( divisionId > -1)
				statement.setInt(4, divisionId);
			rs = statement.executeQuery() ;
			if (rs.next()) {
				return rs.getInt(1);
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return 0;
		
   }
   
   public static int getDivisionSoldQty( Date fromDate , Date toDate , int divisionId) {
	   Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		Map<String,String> ans = new HashMap<String,String>();
		try {
			String divisionSQL =  " and sales.division_id = ?";
			connection  = ConnectionCreater.getConnection() ;
			String sql = "Select sum(sllines.LINE_TOTAL) from SALES sales,SALES_LINES sllines,SKUS skus,items,products  where sales.id = sllines.sales_id   " +
			" and skus.item_id = items.id and items.product_id = products.id and sllines.sku_id = skus.id and sales.SALES_DATE >= ? and sales.SALES_DATE<= ?    " 
					 + divisionSQL;
			statement = connection.prepareStatement(sql);
			
			statement.setTimestamp(1, new java.sql.Timestamp(fromDate.getTime()));
			statement.setTimestamp(2, new java.sql.Timestamp(toDate.getTime()));
			statement.setInt(3, divisionId);
			rs = statement.executeQuery() ;
			if (rs.next()) {
				return rs.getInt(1);
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return 0;
		
   }
   
   public static int getProductSoldQty(int productId, Date fromDate , Date toDate , int divisionId) {
	   Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		Map<String,String> ans = new HashMap<String,String>();
		try {
			String divisionSQL = (divisionId>-1)?"and sales.division_id = ?":"" ;
			connection  = ConnectionCreater.getConnection() ;
			String sql = "Select sum(sllines.LINE_TOTAL) from SALES sales,SALES_LINES sllines,SKUS skus,items  where sales.id = sllines.sales_id   " +
			" and skus.item_id = items.id and sllines.sku_id = skus.id and sales.SALES_DATE >= ? and sales.SALES_DATE<= ?  and items.product_id = ?  " + divisionSQL ;
			statement = connection.prepareStatement(sql);
			
			statement.setTimestamp(1, new java.sql.Timestamp(fromDate.getTime()));
			statement.setTimestamp(2, new java.sql.Timestamp(toDate.getTime()));
			statement.setInt(3, productId);
			if( divisionId > -1)
				statement.setInt(4, divisionId);
			rs = statement.executeQuery() ;
			if (rs.next()) {
				return rs.getInt(1);
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return 0;
		
   }
   
   public static int getBrandSoldQty(int brandId, Date fromDate , Date toDate , int divisionId) {
	   Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		Map<String,String> ans = new HashMap<String,String>();
		try {
			String divisionSQL = (divisionId>-1)?"and sales.division_id = ?":"" ;
			connection  = ConnectionCreater.getConnection() ;
			String sql = "Select sum(sllines.LINE_TOTAL) from SALES sales,SALES_LINES sllines,SKUS skus,items  where sales.id = sllines.sales_id   " +
			" and skus.item_id = items.id and sllines.sku_id = skus.id and sales.SALES_DATE >= ? and sales.SALES_DATE<= ?  and items.brand_id = ? "  + 
					divisionSQL ;
			statement = connection.prepareStatement(sql);
			
			statement.setTimestamp(1, new java.sql.Timestamp(fromDate.getTime()));
			statement.setTimestamp(2, new java.sql.Timestamp(toDate.getTime()));
			statement.setInt(3, brandId);
			if( divisionId > -1)
				statement.setInt(4, divisionId);
			rs = statement.executeQuery() ;
			if (rs.next()) {
				return rs.getInt(1);
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return 0;
		
   }   
   
   public static int getTerritorySoldQty(int territoryId, Date fromDate , Date toDate , int divisionId) {
	   Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		Map<String,String> ans = new HashMap<String,String>();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql = "Select sum(sllines.LINE_TOTAL) from SALES sales,SALES_LINES sllines,SKUS skus  where sales.id = sllines.sales_id and sales.division_id = ?  " +
			" and  sllines.sku_id = skus.id and sales.SALES_DATE >= ? and sales.SALES_DATE<= ?  and sales.territory_id = ?  " ;
			statement = connection.prepareStatement(sql);
			statement.setInt(1, divisionId);
			statement.setTimestamp(2, new java.sql.Timestamp(fromDate.getTime()));
			statement.setTimestamp(3, new java.sql.Timestamp(toDate.getTime()));
			statement.setInt(4, territoryId);
			rs = statement.executeQuery() ;
			if (rs.next()) {
				return rs.getInt(1);
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return 0;
		
   }
   
   public static Map getItemSoldQtyByProduct(int productId, Date fromDate , Date toDate , int divisionId, String itemClass) {
	   Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		Map<Integer,Double> ans = new HashMap<Integer,Double>();
		try {
			connection  = ConnectionCreater.getConnection() ;
			String divisionCond = (divisionId!=-1)?" and sales.division_id= " +  divisionId:" ";
			String itemClassCond =(!Utils.isNull(itemClass))?" and skus.item_class = '" + itemClass +"'":" ";
			String sql = "Select sum(sllines.qty),sllines.sku_id from SALES sales,SALES_LINES sllines,skus skus where sales.id = sllines.sales_id  " +
			" and  sllines.sku_id = skus.id and  skus.product_id= ? and sales.SALES_DATE >= ? and sales.SALES_DATE<= ? " + divisionCond  +  itemClassCond +
			" group by   sllines.sku_id" ;
			statement = connection.prepareStatement(sql);
			//statement.setInt(1, divisionId);
			statement.setInt(1, productId);
			statement.setTimestamp(2, new java.sql.Timestamp(fromDate.getTime()));
			statement.setTimestamp(3, new java.sql.Timestamp(toDate.getTime()));
			rs = statement.executeQuery() ;
			while (rs.next()) {
				Double qty = rs.getDouble(1);
				Integer itemId = rs.getInt(2);
				ans.put(itemId, qty);
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return ans;
		
   }
	
}
