package com.rainbow.crm.saleslead.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.wishlist.model.WishListLine;
import com.techtrade.rads.framework.utils.Utils;

public class SalesLeadDAO  extends HibernateDAO{

	@Override
	public Object getById(Object PK) {
		int salesLeadId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(SalesLead.class, salesLeadId);
		closeSession(session, false);
		return obj;
	}

	public List<SalesLeadLine> getLeadsForCustomer(int customer,Date fromDate, Date toDate)
	{
		Session session = openSession(false) ;
    	try  {
	    	String queryString = " from SalesLeadLine  where salesLeadDoc.customer.id =  :customer_id  and  voided=false and  "+ 
    	  "  salesLeadDoc.voided=false and  salesLeadDoc.releasedDate >= :fromDate and  salesLeadDoc.releasedDate <= :toDate "  ;
	    	Query  query = session.createQuery(queryString);
	    	query.setInteger("customer_id", customer) ;
	    	query.setDate("fromDate", fromDate);
	    	query.setDate("toDate", toDate);
	    	List<SalesLeadLine> salesLeadLines = query.list();
	    	return salesLeadLines ;
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
    	String queryString = " Select sum(qty) from SalesLeadLineItem line ,SalesLead salesLead where line= "  ;
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
		SalesLead salesLead = (SalesLead) object ;
		boolean success = false;
		Session session = openSession(true);
		try {
			session.save(salesLead);
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
