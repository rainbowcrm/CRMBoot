package com.rainbow.crm.alert.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.alert.model.Alert;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.techtrade.rads.framework.utils.Utils;

public class AlertDAO extends HibernateDAO {

	@Override
	public Object getById(Object PK) {
		int alertID = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Alert.class, alertID);
		if (obj != null) {
			Alert alert =(Alert) obj;
		}
		closeSession(session,false);
		return obj;
	}
	
	public Alert findByOwner(int company, String owner , FiniteValue status) {
		Alert alert = null;
		Session session = openSession(false);
		StringBuffer statusStr  ;
		if (status != null )
			statusStr =  new StringBuffer(  " and  status =:status");
		else
			statusStr =  new StringBuffer(  " ");
		Query query = session.createQuery(" from Alert where owner = :owner and company.id =:company  " + statusStr.toString() ) ;
		
		
		query.setParameter("owner", owner);
		query.setParameter("company", company);
		if (status != null )
			query.setParameter("status", status.getCode());
		List lst = query.list();
		if (!Utils.isNullList(lst)) {
			alert = (Alert) lst.get(0) ;
		}
		closeSession(session, false);
		return alert;
	}
	
	
	public Alert findByStatus(int company, String status) {
		Alert alert = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Alert where status = :status and company.id =:company  " ) ;
		query.setParameter("phone", status);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst)){
			alert = (Alert) lst.get(0) ;
		}
		closeSession(session, false);
		return alert;
	}

}
