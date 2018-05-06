package com.rainbow.framework.query.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.framework.query.model.QueryReport;
import com.techtrade.rads.framework.utils.Utils;

public class QueryDAO  extends HibernateDAO{

	@Override
	public Object getById(Object PK) {
		int queryId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Query.class, queryId);
		closeSession(session, false);
		return obj;
	}
	
	
	public  com.rainbow.framework.query.model.Query getQuery(int queryId)  {
		Session session = openSession(false);
		try {
		String queryString  = " from Query where id =:id and  deleted = false" ;
		Query query = session.createQuery( queryString  ) ;
		query.setParameter("id", queryId);
		List<com.rainbow.framework.query.model.Query> lst = query.list();
		return lst.get(0);
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			closeSession(session,false);
		}
		return null;
	}
	public List<com.rainbow.framework.query.model.Query> getAllQueriesforOwner(int company,String owner)
	{
		Session session = openSession(false);
		try {
		String queryString  = " from Query where company.id = :company and owner.userId = :owner and deleted = false" ;
		Query query = session.createQuery( queryString  ) ;
		query.setParameter("company", company);
		query.setParameter("owner", owner);
		List lst = query.list();
		return lst;
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			closeSession(session,false);
		}
		return null;
	
	}
	public List getQueryRecord(String queryString, int company, Date fromDate, Date toDate)
	{
		Session session = openSession(false);
		try {
		
		Query query = session.createQuery( queryString  ) ;
		query.setParameter("company", company);
		if(fromDate != null)
			query.setParameter("fromDate", fromDate);
		if(toDate != null)
			query.setParameter("toDate", toDate);
		
		query.setFirstResult(0);
		query.setMaxResults(10);
		List lst = query.list();
		return lst;
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}finally {
			closeSession(session,false);
		}
			
		return  null;
	}
	
	
	public void deleteOrphanedRecords(com.rainbow.framework.query.model.Query query )
	{
		Session session = openSession(true);
		boolean success = false; 
		try { 
			query.getConditions().forEach(  condition ->  {    
				if (condition.isDeleted()) {
					session.delete(condition );
					query.getConditions().remove(condition);
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
