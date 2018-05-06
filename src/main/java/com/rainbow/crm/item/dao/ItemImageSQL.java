package com.rainbow.crm.item.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.database.ConnectionCreater;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.model.ItemImage;
import com.rainbow.crm.logger.Logwriter;

public class ItemImageSQL {

	 public static void insertItemImage (ItemImage image,CRMContext context) {
	 	Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql = " INSERT INTO ITEM_IMAGES (ID,SKU_ID,SUFFIX,IMAGE_FILE,CREATED_DATE,CREATED_BY,VERSION) VALUES (?,?,?,?,?,?,?)" ;
			statement = connection.prepareStatement(sql) ;
			int id = getNextID();
			statement.setInt(1,id);
			statement.setInt(2,image.getSku().getId());
			statement.setString(3, String.valueOf(image.getSuffix()));
			statement.setString(4, image.getFileName());
			statement.setTimestamp(5,new Timestamp(new java.util.Date().getTime()));
			statement.setString(6, context.getUser());
			statement.setInt(7,1);
			statement.executeUpdate();
		}catch(SQLException ex) {;
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
	 }
	 
	 private static int getNextID() {
	    Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   "SELECT  MAX(ID) FROM ITEM_IMAGES" ;
			statement = connection.prepareStatement(sql);
			rs = statement.executeQuery() ;
			if (rs.next()) {
				Integer nxt = rs.getInt(1);
				if (nxt == null || nxt ==0 )
					nxt = 1;
				else
					nxt ++ ;
				return nxt;
			}
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return 1;
	 }
	 
	 public static void updateItemImage (ItemImage image,CRMContext context) {
	 	Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql = " UPDATE ITEM_IMAGES SET IMAGE_FILE = ? , LAST_UPDATED_DATE = ? , LAST_UPDATED_BY = ?, VERSION = VERSION + 1 WHERE ID = ?" ;
			statement = connection.prepareStatement(sql) ;
			statement.setString(1, image.getFileName());
			statement.setTimestamp(2,new Timestamp(new java.util.Date().getTime()));
			statement.setString(3, context.getUser());
			statement.setInt(4,image.getId());
			statement.executeUpdate();
		}catch(SQLException ex) {;
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
	 }
	 
	 public static void DeleteItemImage (ItemImage image) {
	 	Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql = " DELETE FROM ITEM_IMAGES WHERE ID = ?" ;
			statement = connection.prepareStatement(sql) ;
			statement.setInt(1, image.getId());
			statement.executeUpdate();
		}catch(SQLException ex) {;
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
	 }
	 
	 
	 public static void DeleteAllImagesforItem(Sku item) {
		 	Connection connection = null;
			PreparedStatement statement = null;
			ResultSet rs  = null ;
			try {
				connection  = ConnectionCreater.getConnection() ;
				String sql = " DELETE FROM ITEM_IMAGES WHERE SKU_ID = ?" ;
				statement = connection.prepareStatement(sql) ;
				statement.setInt(1, item.getId());
				statement.executeUpdate();
			}catch(SQLException ex) {;
				Logwriter.INSTANCE.error(ex);
			}finally {
				ConnectionCreater.close(connection, statement, rs);	
			}
		 }
	
	 public  static List<String> getAllItemImages(int skuid) {
		 Connection connection = null;
			PreparedStatement statement = null;
			ResultSet rs  = null ;
			List<String> images = new ArrayList<> ();
			try {
				connection  = ConnectionCreater.getConnection() ;
				String sql =   "SELECT IMG.IMAGE_FILE FROM ITEM_IMAGES IMG, SKUS IT where IMG.SKU_ID = ?   AND IMG.SKU_ID = IT.ID" ;
				statement = connection.prepareStatement(sql);
				statement.setInt(1, skuid);
				rs = statement.executeQuery() ;
				while (rs.next()) {
					String fileName = rs.getString(1);
					images.add(fileName);
				}
			}catch(SQLException ex) {
				Logwriter.INSTANCE.error(ex);
			}finally {
				ConnectionCreater.close(connection, statement, rs);	
			}
			return images;
	 }
	 
	 public static ItemImage getItemImage (int itemId, char suffix) {
	 	Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs  = null ;
		ItemImage itemImage  = null;
		try {
			connection  = ConnectionCreater.getConnection() ;
			String sql =   "SELECT IMG.ID, IT.SKU_NAME, IT.ID,IMG.IMAGE_FILE,IMG.SUFFIX FROM ITEM_IMAGES IMG, SKUS IT where IMG.SKU_ID = ?  AND IMG.SUFFIX = ?  AND IMG.SKU_ID = IT.ID" ;
			statement = connection.prepareStatement(sql);
			statement.setInt(1, itemId);
			statement.setString(2,String.valueOf(suffix));
			rs = statement.executeQuery() ;
			if (rs.next()) {
				itemImage = new ItemImage();
				itemImage.setId(rs.getInt(1));
				Sku item = new Sku();
				item.setId(rs.getInt(3));
				item.setName(rs.getString(2));
				itemImage.setSku(item);
				itemImage.setFilePath(CRMAppConfig.INSTANCE.getProperty("Image_Path"));
				itemImage.setFileName(rs.getString(4));
				itemImage.setSuffix(rs.getString(5).charAt(0));
			}
		}catch(IOException exIO) {
			Logwriter.INSTANCE.error(exIO);
		}catch(SQLException ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			ConnectionCreater.close(connection, statement, rs);	
		}
		return itemImage;
	 }
}
