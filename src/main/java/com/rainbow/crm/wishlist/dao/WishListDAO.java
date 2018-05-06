package com.rainbow.crm.wishlist.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.wishlist.model.WishList;
import com.rainbow.crm.wishlist.model.WishListLine;
import com.techtrade.rads.framework.utils.Utils;

public class WishListDAO  extends HibernateDAO{

	@Override
	public Object getById(Object PK) {
		int wishListId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(WishList.class, wishListId);
		closeSession(session, false);
		return obj;
	}
	
	public List<WishListLine>  getWishesPerSkuByPrice(Sku item, double price, String reason ) {
		Session session = openSession(false) ;
    	try  {
	    	String queryString = " from WishListLine  where item.id =  :item_id   " +   
    	    " and reasonCode= :reasonCode and desiredPrice <=:price and salesLeadGenerated  is false "  ;
	    	Query  query = session.createQuery(queryString);
	    	query.setParameter("item_id", item.getId()) ;
	    	query.setParameter("price", price) ;
	    	query.setParameter("reasonCode", reason);
	    	List<WishListLine> wishlistLines = query.list();
	    	return wishlistLines ;
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}finally{
    		session.close();
    	}
    	return null;
	}
	
	public List<WishListLine>  getWishesPerItemByPrice(Item item, double price, String reason ) {
		Session session = openSession(false) ;
    	try  {
	    	String queryString = " from WishListLine  where sku.item.id =  :item_id   " +   
    	    " and reasonCode= :reasonCode and desiredPrice <=:price and salesLeadGenerated  is false "  ;
	    	Query  query = session.createQuery(queryString);
	    	query.setInteger("item_id", item.getId()) ;
	    	query.setDouble("price", price) ;
	    	query.setString("reasonCode", reason);
	    	List<WishListLine> wishlistLines = query.list();
	    	return wishlistLines ;
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}finally{
    		session.close();
    	}
    	return null;
	}
	
	public List<WishListLine>  getWishesPerSkuByInventory(Sku item, Division division, double Qty , String reason ) {
		Session session = openSession(false) ;
    	try  {
	    	String queryString = " from WishListLine  where sku.id =  :item_id and qty <= :qty  and division.id= :division_id "  + 
    	    " and reasonCode= :reasonCode  and salesLeadGenerated  is false "  ;
	    	Query  query = session.createQuery(queryString);
	    	query.setInteger("item_id", item.getId()) ;
	    	query.setDouble("qty", Qty) ;
	    	query.setInteger("division_id", division.getId());
	    	query.setString("reasonCode", reason);
	    	List<WishListLine> wishlistLines = query.list();
	    	return wishlistLines ;
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}finally{
    		session.close();
    	}
    	return null;
	}
	
	public List<WishListLine>  getWishesPerSku(Sku item, Division division, double Qty , String reason ) {
		Session session = openSession(false) ;
    	try  {
	    	String queryString = " from WishListLine  where sku.id =  :item_id and qty <= :qty  and division.id= :division_id "  + 
    	    " and reasonCode= :reasonCode  and salesLeadGenerated  is false "  ;
	    	Query  query = session.createQuery(queryString);
	    	query.setInteger("item_id", item.getId()) ;
	    	query.setDouble("qty", Qty) ;
	    	query.setInteger("division_id", division.getId());
	    	query.setString("reasonCode", reason);
	    	List<WishListLine> wishlistLines = query.list();
	    	return wishlistLines ;
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}finally{
    		session.close();
    	}
    	return null;
	}
	
	public List<WishListLine>  getOpenWishesPerItem(Item item,Date fromDate, Date toDate ) {
		Session session = openSession(false) ;
    	try  {
	    	String queryString = " from WishListLine  where sku.item.id =  :item_id  and salesLeadGenerated  is false and voided=false and  "+ 
    	  " wishListDoc.voided=false and  wishListDoc.wishListDate >= :fromDate and  wishListDoc.wishListDate <= :toDate "  ;
	    	Query  query = session.createQuery(queryString);
	    	query.setInteger("item_id", item.getId()) ;
	    	query.setDate("fromDate", fromDate);
	    	query.setDate("toDate", toDate);
	    	List<WishListLine> wishlistLines = query.list();
	    	return wishlistLines ;
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}finally{
    		session.close();
    	}
    	return null;
	}
	
	public List<WishListLine>  getOpenWishesPerCustomer(Customer customer,Date fromDate, Date toDate ) {
		Session session = openSession(false) ;
    	try  {
	    	String queryString = " from WishListLine  where wishListDoc.customer.id =  :customer_id  and salesLeadGenerated  is false and voided=false and  "+ 
    	  " wishListDoc.voided=false and  wishListDoc.wishListDate >= :fromDate and  wishListDoc.wishListDate <= :toDate "  ;
	    	Query  query = session.createQuery(queryString);
	    	query.setInteger("customer_id", customer.getId()) ;
	    	query.setDate("fromDate", fromDate);
	    	query.setDate("toDate", toDate);
	    	List<WishListLine> wishlistLines = query.list();
	    	return wishlistLines ;
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}finally{
    		session.close();
    	}
    	return null;
	}


	/*public int getTotalQtySold (Item item , Date from , Date To) {
		Session session = openSession(false) ;
    	try  {
    	String queryString = " Select sum(qty) from WishListLineItem line ,WishList wishList where line= "  ;
    	Query  query = session.createQuery(queryString);
    	List lst = query.list();
    	if(!Utils.isNull(lst)) {
    	  Object obj = lst.get(0);
    	  if (obj!=null && obj instanceof Integer) {
    		  return(((Integer)obj).intValue() +1 );
    	  }
    	}
    		return 1;
    	}finally{
    		session.close();
    	}
	}*/
	
	/*@Override
	public void create(CRMModelObject object) {
		WishList wishList = (WishList) object ;
		boolean success = false;
		Session session = openSession(true);
		try {
			session.save(wishList);
			session.flush();
			success = true; 
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
			throw new DatabaseException(ex,DatabaseException.DIRTY_READ_ERROR);
		}finally {
			closeSession(session,success);
		}
	}*/
	
	

	
	
	
}
