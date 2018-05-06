package com.rainbow.crm.expensehead.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.expensehead.model.ExpenseHead;
import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class ExpenseHeadDAO extends HibernateDAO{

	@Override
	public Object getById(Object PK) {
		int expensehead = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(ExpenseHead.class, expensehead);
		closeSession(session,false);
		return obj;
	}
	
	public ExpenseHead findByName(int company, String name) {
		ExpenseHead expensehead = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from ExpenseHead where name = :name and company.id =:company  and deleted=false " ) ;
		query.setParameter("name", name);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst))
			expensehead = (ExpenseHead) lst.get(0) ;
		closeSession(session, false);
		return expensehead;
	}
	
	@Override
	public void batchCreate(List<CRMModelObject> objects) throws DatabaseException{
		if(!Utils.isNullList(objects)) {
			int pk = getMaxPlusOneId("id", "ExpenseHead");
			for (CRMModelObject object : objects ) {
				ExpenseHead expensehead = (ExpenseHead) object;
				expensehead.setId(pk ++);
			}
		super.batchCreate(objects);
		}
	}
	
	public List<ExpenseHead> getAllExpenseHeads(int company)  {
		Session session = openSession(false);
		Query query = session.createQuery(" from ExpenseHead where  company.id =:company  and deleted=false " ) ;
		query.setParameter("company", company);
		List lst = query.list();
		closeSession(session, false);
		return lst;
	}

}
