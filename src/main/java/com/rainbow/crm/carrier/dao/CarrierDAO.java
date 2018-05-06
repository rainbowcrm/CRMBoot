package com.rainbow.crm.carrier.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.carrier.model.Carrier;
import com.rainbow.crm.division.model.Division;
import com.techtrade.rads.framework.utils.Utils;

public class CarrierDAO extends  HibernateDAO {

	@Override
	public Object getById(Object PK) {
		int carrierID = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Carrier.class, carrierID);
		if (obj != null) {
			Carrier carrier =(Carrier) obj;
		}
		closeSession(session,false);
		return obj;
	}
	
	public Carrier findByEmail(int company, String email) {
		Carrier carrier = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Carrier where email = :email and company.id =:company  " ) ;
		query.setParameter("email", email);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst)) {
			carrier = (Carrier) lst.get(0) ;
		}
		closeSession(session, false);
		return carrier;
	}
	
	public Carrier findByCode(int company, String code) {
		Carrier carrier = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Carrier where code = :code and company.id =:company  " ) ;
		query.setParameter("code", code);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst)) {
			carrier = (Carrier) lst.get(0) ;
		}
		closeSession(session, false);
		return carrier;
	}
	
	public Carrier findByPhone(int company, String phone) {
		Carrier carrier = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Carrier where phone = :phone and company.id =:company  " ) ;
		query.setParameter("phone", phone);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst)){
			carrier = (Carrier) lst.get(0) ;
		}
		closeSession(session, false);
		return carrier;
	}
	
	public List<Carrier> getAllCarriers(int company) {
		Session session = openSession(false);
		Query query = session.createQuery("from Carrier where company.id=  :id "  );
		query.setParameter("id", company);
		List<Carrier> ans = query.list();
		closeSession(session,false);
		return ans;
	}

}
