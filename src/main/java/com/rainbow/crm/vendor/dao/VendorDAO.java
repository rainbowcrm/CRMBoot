package com.rainbow.crm.vendor.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.vendor.model.Vendor;
import com.techtrade.rads.framework.utils.Utils;

public class VendorDAO extends HibernateDAO {

	@Override
	public Object getById(Object PK) {
		int vendorID = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Vendor.class, vendorID);
		closeSession(session,false);
		return obj;
	}
	
	public Vendor findByCode(int company, String code) {
		Vendor vendor = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Vendor where code = :code and company.id =:company  " ) ;
		query.setParameter("code", code);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst))
			vendor = (Vendor) lst.get(0) ;
		closeSession(session, false);
		return vendor;
	}
	
	
	public Vendor findByName(int company, String name) {
		Vendor vendor = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Vendor where name = :name and company.id =:company  " ) ;
		query.setParameter("name", name);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst))
			vendor = (Vendor) lst.get(0) ;
		closeSession(session, false);
		return vendor;
	}

}
