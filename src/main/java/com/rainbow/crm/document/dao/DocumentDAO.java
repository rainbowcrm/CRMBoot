package com.rainbow.crm.document.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.document.model.Document;
import com.techtrade.rads.framework.utils.Utils;

public class DocumentDAO extends HibernateDAO {

	@Override
	public Object getById(Object PK) {
		int documentID = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Document.class, documentID);
		if (obj != null) {
			Document document =(Document) obj;
		}
		closeSession(session,false);
		return obj;
	}
	
	
	public List<Document> getDocumentsforSalesLead(int lead) {
		Session session = openSession(false);
		Query query = session.createQuery(" from Document where lead.id = :lead    and deleted = false  " ) ;
		query.setParameter("lead", lead);
		List<Document> lst = query.list();
		closeSession(session, false);
		return lst;
	}
	
	public List<Document> getDocumentsforItem(int item) {
		Session session = openSession(false);
		Query query = session.createQuery(" from Document where item.id = :item    and deleted = false  " ) ;
		query.setParameter("item", item);
		List<Document> lst = query.list();
		closeSession(session, false);
		return lst;
	}
	
	public List<Document> getDocumentsforCustomer(int customer) {
		Session session = openSession(false);
		Query query = session.createQuery(" from Document where customer.id = :customer    and deleted = false  " ) ;
		query.setParameter("customer", customer);
		List<Document> lst = query.list();
		closeSession(session, false);
		return lst;
	}

}
