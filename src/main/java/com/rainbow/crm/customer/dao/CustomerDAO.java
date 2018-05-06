package com.rainbow.crm.customer.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.customer.model.Customer;
import com.techtrade.rads.framework.utils.Utils;

public class CustomerDAO extends HibernateDAO {

	@Override
	public Object getById(Object PK) {
		int customerID = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Customer.class, customerID);
		if (obj != null) {
			Customer customer =(Customer) obj;
			customer.setFullName(customer.getFirstName() + " " + customer.getLastName());
		}
		closeSession(session,false);
		return obj;
	}
	
	public Customer findByEmail(int company, String email) {
		Customer customer = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Customer where email = :email and company.id =:company  " ) ;
		query.setParameter("email", email);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst)) {
			customer = (Customer) lst.get(0) ;
			customer.setFullName(customer.getFirstName() + " " + customer.getLastName());
		}
		closeSession(session, false);
		return customer;
	}
	
	
	public Customer findByPhone(int company, String phone) {
		Customer customer = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Customer where phone = :phone and company.id =:company  " ) ;
		query.setParameter("phone", phone);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst)){
			customer = (Customer) lst.get(0) ;
			customer.setFullName(customer.getFirstName() + " " + customer.getLastName());
		}
		closeSession(session, false);
		return customer;
	}

}
