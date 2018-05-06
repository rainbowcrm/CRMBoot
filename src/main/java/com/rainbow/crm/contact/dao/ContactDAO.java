package com.rainbow.crm.contact.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.contact.model.Contact;
import com.techtrade.rads.framework.utils.Utils;

public class ContactDAO extends HibernateDAO {

	@Override
	public Object getById(Object PK) {
		int contactID = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Contact.class, contactID);
		if (obj != null) {
			Contact contact =(Contact) obj;
			contact.setFullName(contact.getFirstName() + " " + contact.getLastName());
		}
		closeSession(session,false);
		return obj;
	}
	
	public Contact findByEmail(int company, String email) {
		Contact contact = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Contact where email = :email and company.id =:company  and deleted = false" ) ;
		query.setParameter("email", email);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst)) { 
			contact = (Contact) lst.get(0) ;
			contact.setFullName(contact.getFirstName() + " " + contact.getLastName());
		}
		closeSession(session, false);
		return contact;
	}
	
	
	public Contact findByPhone(int company, String phone) {
		Contact contact = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Contact where phone = :phone and company.id =:company and deleted = false  " ) ;
		query.setParameter("phone", phone);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst)){
			contact = (Contact) lst.get(0) ;
			contact.setFullName(contact.getFirstName() + " " + contact.getLastName());
		}
		closeSession(session, false);
		return contact;
	}
	
	public Contact findByfullNameAndPhone(int company, String firstName , String lastName,  String phone) {
		Contact contact = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Contact where firstName =:firstName and lastName =:lastName and  phone = :phone and company.id =:company and deleted = false  " ) ;
		query.setParameter("firstName", firstName);
		query.setParameter("lastName", lastName);
		query.setParameter("phone", phone);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst)){
			contact = (Contact) lst.get(0) ;
			contact.setFullName(contact.getFirstName() + " " + contact.getLastName());
		}
		closeSession(session, false);
		return contact;
	}
	
	public Contact findByfullName(int company, String firstName , String lastName) {
		Contact contact = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Contact where firstName =:firstName and lastName =:lastName  and company.id =:company and deleted = false  " ) ;
		query.setParameter("firstName", firstName);
		query.setParameter("lastName", lastName);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst)){
			contact = (Contact) lst.get(0) ;
			contact.setFullName(contact.getFirstName() + " " + contact.getLastName());
		}
		closeSession(session, false);
		return contact;
	}

}

