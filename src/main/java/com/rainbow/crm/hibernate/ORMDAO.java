package com.rainbow.crm.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public abstract class ORMDAO {
	

	
	protected abstract Session openSession(boolean newTransaction) ;
	
	
	protected  abstract void closeSession(Session session,boolean commitTransaction) ;
	 
	protected int getMaxPlusOneId(String idField, String tableName) {
    	Session session = openSession(false) ;
    	try  {
    	String queryString = " Select max(" + idField + ") from " + tableName ;
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
    }
    
	public long getTotalRecordCount( String tableName, CRMContext context, String whereCondition ) {
		Session session = openSession(false) ;
    	try  {
    	String queryString = " Select count(*) from " + tableName  + " " +  whereCondition; 
    	Query  query = session.createQuery(queryString);
    	List lst = query.list();
    	if(!Utils.isNull(lst)) {
    	  Object obj = lst.get(0);
    	  if (obj!=null && obj instanceof Long) {
    		  return(((Long)obj).longValue());
    	  } if (obj!=null && obj instanceof Integer) {
    		  return(((Integer)obj).intValue());
    	  }
    	}
    		return 1;
    	}finally{
    		session.close();
    	}
	}
    
	public abstract Object getById(Object PK);
	
	protected void supplement(List<CRMModelObject> objects) {
		if (!Utils.isNullList(objects)) {
			for (CRMModelObject modelObj : objects) {
				supplement(modelObj);
			}
		}
	}

	protected void supplement(CRMModelObject modelObj) {
		
	}
	
	public List<CRMModelObject> listData(String table, int from , int to , String whereCondition ) {
		Session session = openSession(false);
		Query query = session.createQuery("from " + table   +  ((Utils.isNull(whereCondition))?"":whereCondition) );
		query.setFirstResult(from);  
		query.setMaxResults(to-from);
		List<CRMModelObject> ans = query.list();
		supplement(ans);
		closeSession(session,false);
		return ans;
	}
	
	public List<CRMModelObject> listData(String table, int from , int to , String whereCondition, String orderby ) {
		Session session = openSession(false);
		Query query = session.createQuery("from " + table   +  ((Utils.isNull(whereCondition))?"":whereCondition) +
				 " " + ((Utils.isNull(orderby))?"": (" order by " + orderby) ) );
		query.setFirstResult(from);  
		query.setMaxResults(to-from);
		List<CRMModelObject> ans = query.list();
		supplement(ans);
		closeSession(session,false);
		return ans;
	}
	
	public List<CRMModelObject> findAll(String table,  String whereCondition, String orderby ) {
		Session session = openSession(false);
		Query query = session.createQuery("from " + table   +  ((Utils.isNull(whereCondition))?"":whereCondition) +
				 " " + ((Utils.isNull(orderby))?"": (" order by " + orderby) ) );
		List<CRMModelObject> ans = query.list();
		supplement(ans);
		closeSession(session,false);
		return ans;
	}
	
	@Transactional
	public void batchCreate(List<CRMModelObject> objects) throws DatabaseException{
		Session session = openSession(true);
		boolean success = false; 
		try {
			for (ModelObject object : objects) {
				session.save(object);
			}
			session.flush();
			success = true;
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
			throw new DatabaseException(ex,DatabaseException.PERSISTENCE_ERROR);
		}finally {
			closeSession(session,success);
		}
	}
	
	@Transactional
	public void create(CRMModelObject object) {
		Session session = openSession(true);
		boolean success = false; 
		try { 
			session.save(object);
			session.flush();
			success = true; 
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
			throw new DatabaseException(ex,DatabaseException.DIRTY_READ_ERROR);
		}finally {
			closeSession(session,success);
		}
	}
	
	@Transactional
	public void update(CRMModelObject object)   {
		Session session = openSession(true);
		boolean success = false; 
		try { 
			session.update(object);
			session.flush();
			success = true; 
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
			throw new DatabaseException(ex,DatabaseException.DIRTY_READ_ERROR);
		}finally {
			closeSession(session,success);
		}
		 
	}
	
	public void batchUpdate(List<? extends CRMModelObject> objects)  throws DatabaseException{
		Session session = openSession(true);
		boolean success = false; 
		try {
			for (ModelObject object : objects) {
				session.update(object);
			}
			session.flush();
			success = true;
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
			throw new DatabaseException(ex,DatabaseException.PERSISTENCE_ERROR);
		}finally {
			closeSession(session,success);
		}
	}


}
