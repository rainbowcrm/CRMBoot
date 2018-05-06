package com.rainbow.crm.custcategory.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.custcategory.model.CustCategory;
import com.rainbow.crm.customer.model.Customer;
import com.techtrade.rads.framework.utils.Utils;

public class CustCategoryDAO extends HibernateDAO {

	@Override
	public Object getById(Object PK) {
		int custCategoryID = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(CustCategory.class, custCategoryID);
		
		closeSession(session,false);
		return obj;
	}
	
	public List<Customer> getCustomersforRule(String queryString,Date fromDate, Date toDate )
	{
		Session session = openSession(false);
		try {
		Query query = session.createQuery(queryString);
		if(fromDate != null)
			query.setParameter("fromDate", fromDate);
		if(toDate != null)
			query.setParameter("toDate", toDate);
		List<Customer> lst = query.list();
		return lst;
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			closeSession(session,false);
		}
		return null;
		
	}
	

	public void deleteOrphanedRecords(CustCategory custCategory )
	{
		Session session = openSession(true);
		boolean success = false; 
		try { 
			custCategory.getConditions().forEach(  condition ->  {    
				if (condition.isDeleted()) {
					session.delete(condition );
					custCategory.getConditions().remove(condition);
				}
				
			} );
			session.flush();
			success = true; 
			
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
			throw new DatabaseException(ex,DatabaseException.DIRTY_READ_ERROR);
		}finally {
			closeSession(session,success);
		}
	}
	

}
