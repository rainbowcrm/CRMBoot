package com.rainbow.crm.filter.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.filter.model.CRMFilter;
import com.rainbow.crm.filter.model.CRMFilterDetails;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.techtrade.rads.framework.utils.Utils;

public class CRMFilterDAO extends HibernateDAO {

	public static CRMFilterDAO INSTANCE = new CRMFilterDAO();
	
	@Override
	public Object getById(Object PK) {
		int filterId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(CRMFilter.class, filterId);
		closeSession(session, false);
		return obj;
	}
	
	public void create(CRMFilter filter) {
		int id =  getMaxPlusOneId("id", "CRMFilter") ;
		filter.setId(id);
		int detailId = getMaxPlusOneId("id", "CRMFilterDetails") ;
		for (CRMFilterDetails details : filter.getDetails()) {
			details.setId(detailId ++ );
		}
		Session session = openSession(true);
		session.save(filter);
		session.flush();
		closeSession(session, true);
		 
	}
	
	public void update(CRMFilter filter) {
		Session session = openSession(true);
		Query queryDel = session.createQuery(" delete from CRMFilterDetails where parent.id = :id  ");
		queryDel.setParameter("id", filter.getId());
		queryDel.executeUpdate() ;
		int detailId = getMaxPlusOneId("id", "CRMFilterDetails") ;
		for (CRMFilterDetails details : filter.getDetails()) {
			details.setId(detailId ++ );
			details.setParent(filter);
		}		
		session.update(filter);
		session.flush();
		closeSession(session, true);
	}
	
	public CRMFilter findById(int id) {
		CRMFilter filter = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from CRMFilter where id = :id  " ) ;
		query.setParameter("id", id);
		List lst = query.list();
		if (!Utils.isNullList(lst))
			filter = (CRMFilter) lst.get(0) ;
		closeSession(session, false);
		return filter;
	}
	
	public Map<String,String> findAllByPage(String user, String page ) {
		Map<String,String> ans = new HashMap<String,String>();
		Session session = openSession(false);
		Query query = session.createQuery(" from CRMFilter where user = :user    and page = :page " ) ;
		query.setParameter("user", user);
		query.setParameter("page", page);
		List<CRMFilter> lst = query.list();
		if(!Utils.isNullList(lst)) {
			for (CRMFilter filter :  lst) {
				ans.put(String.valueOf(filter.getId()),filter.getName());
			}
		}
		closeSession(session, false);
		return ans;
	}
	
	public CRMFilter findByKey(String user, String page,String filterName ) {
		Session session = openSession(false);
		CRMFilter filter =null;
		Query query = session.createQuery(" from CRMFilter where user = :user    and page = :page and name=:name " ) ;
		query.setParameter("user", user);
		query.setParameter("page", page);
		query.setParameter("name", filterName);
		List<CRMFilter> lst = query.list();
		if (!Utils.isNullList(lst))
			filter = (CRMFilter) lst.get(0) ;
		closeSession(session, false);
		return filter;
	}
	
	

}
