package com.rainbow.crm.division.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.utils.Utils;

public class DivisionDAO extends HibernateDAO {
	public static DivisionDAO  INSTANCE  = new DivisionDAO();

	@Override
	public Object getById(Object PK) {
		int divisionId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Division.class, divisionId);
		closeSession(session,false);
		return obj;
	}
	
	public Division getByCode(String code,int company) {
		Division division = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Division where code = :code  and company.id =:id and deleted=false  " ) ;
		query.setParameter("code", code);
		query.setParameter("id", company);
		List lst = query.list();
		if (!Utils.isNullList(lst))
			division = (Division)lst.get(0);
		closeSession(session, false);
		return division;
	}


	
	public Division getByName(String name,int company) {
		Division division = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Division where name = :name  and company.id =:id  and deleted=false  " ) ;
		query.setParameter("name", name);
		query.setParameter("id", company);
		List lst = query.list();
		if (!Utils.isNullList(lst))
			division = (Division)lst.get(0);
		closeSession(session, false);
		return division;
	}
	
	
	public Division getDefaultDivision(int company) {
		Division division = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Division where company.id =:id  and deleted=false  order by divisionType " ) ;
		query.setParameter("id", company);
		List lst = query.list();
		if (!Utils.isNullList(lst))
			division = (Division)lst.get(0);
		closeSession(session, false);
		return division;
	}
	
	public List<Division> getAllDivisions(int company) {
		Session session = openSession(false);
		Query query = session.createQuery("from Division where company.id=  :id and deleted=false  "  );
		query.setParameter("id", company);
		List<Division> ans = query.list();
		closeSession(session,false);
		return ans;
	}

}
