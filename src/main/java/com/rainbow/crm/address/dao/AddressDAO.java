package com.rainbow.crm.address.dao;


import org.hibernate.Session;

import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.address.model.Address;

public class AddressDAO extends HibernateDAO {

	@Override
	public Object getById(Object PK) {
		int addressID = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Address.class, addressID);
		closeSession(session,false);
		return obj;
	}

	
	


}
