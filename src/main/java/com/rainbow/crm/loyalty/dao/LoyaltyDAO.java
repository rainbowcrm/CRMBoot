package com.rainbow.crm.loyalty.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.loyalty.model.Loyalty;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.techtrade.rads.framework.utils.Utils;

public class LoyaltyDAO extends HibernateDAO {

	@Override
	public Object getById(Object PK) {
		int loyaltyID = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Loyalty.class, loyaltyID);
		if (obj != null) {
			Loyalty loyalty =(Loyalty) obj;
		}
		closeSession(session,false);
		return obj;
	}
	
	public Loyalty findBySalesBill(int company , String billNumber, boolean includeDeleted)
	{
		
		Loyalty loyalty = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Loyalty where sales.billNumber = :billNumber and company.id =:company and  deleted= :deleted "   ) ;
		query.setParameter("billNumber", billNumber);
		query.setParameter("company", company);
		query.setParameter("deleted", includeDeleted);
		List lst = query.list();
		if (!Utils.isNullList(lst)) {
			loyalty = (Loyalty) lst.get(0) ;
		}
		closeSession(session, false);
		return loyalty;
	}
	
	public Loyalty findByOwner(int company, String owner , FiniteValue status) {
		Loyalty loyalty = null;
		Session session = openSession(false);
		StringBuffer statusStr  ;
		if (status != null )
			statusStr =  new StringBuffer(  " and  status =:status");
		else
			statusStr =  new StringBuffer(  " ");
		Query query = session.createQuery(" from Loyalty where owner = :owner and company.id =:company  " + statusStr.toString() ) ;
		
		
		query.setParameter("owner", owner);
		query.setParameter("company", company);
		if (status != null )
			query.setParameter("status", status.getCode());
		List lst = query.list();
		if (!Utils.isNullList(lst)) {
			loyalty = (Loyalty) lst.get(0) ;
		}
		closeSession(session, false);
		return loyalty;
	}
	
	
	public Loyalty findByStatus(int company, String status) {
		Loyalty loyalty = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Loyalty where status = :status and company.id =:company  " ) ;
		query.setParameter("phone", status);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst)){
			loyalty = (Loyalty) lst.get(0) ;
		}
		closeSession(session, false);
		return loyalty;
	}

}
