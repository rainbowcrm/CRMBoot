package com.rainbow.crm.user.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.utils.Utils;


public class UserDAO extends HibernateDAO {
	
	private void UserDAO() {
		
	}
	
	public static UserDAO  INSTANCE  = new UserDAO();

	@Override
	public Object getById(Object PK) {
		String userId = String.valueOf(PK);
		Session session = openSession(false);
		Object obj = session.get(User.class, userId);
		setPrefix((User)obj);
		closeSession(session, false);
		return obj;
	}
	
	public User getByEmail(String email) {
		User user = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from User where email = :email    " ) ;
		query.setParameter("email", email);
		List lst = query.list();
		if (!Utils.isNullList(lst)) {
			user = (User)lst.get(0);
			setPrefix(user);
		}
		closeSession(session, false);
		return user;
	}
	
	public User getByPhone(String phone) {
		User user = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from User where phone = :phone    " ) ;
		query.setParameter("phone", phone);
		List lst = query.list();
		if (!Utils.isNullList(lst)){
			user = (User)lst.get(0);
			setPrefix(user);
		}
		closeSession(session, false);
		return user;
	}
	
	public List<User> getAllUsersByDivision(int  division,boolean includeDeleted) {
		User user = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from User where division.id = :division and deleted in (false,:deleted)    " ) ;
		query.setParameter("division", division);
		query.setParameter("deleted", includeDeleted);
		List lst = query.list();
		closeSession(session, false);
		return lst;
	}
	
	@Override
	protected void supplement(CRMModelObject modelObj) {
		if (modelObj != null)
			setPrefix((User)modelObj);
	}
	private void setPrefix(User user) {
		if (user != null) {
			String userId = user.getUserId() ;
			String firstPart = userId.substring(0, userId.indexOf("@"));
			user.setPrefix(firstPart);
		}
	}

}
