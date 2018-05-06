package com.rainbow.crm.category.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.category.model.Category;
import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class CategoryDAO extends HibernateDAO{

	@Override
	public Object getById(Object PK) {
		int category = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Category.class, category);
		closeSession(session,false);
		return obj;
	}
	
	public Category findByName(int company, String name) {
		Category category = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Category where name = :name and company.id =:company  " ) ;
		query.setParameter("name", name);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst))
			category = (Category) lst.get(0) ;
		closeSession(session, false);
		return category;
	}
	
	@Override
	public void batchCreate(List<CRMModelObject> objects) throws DatabaseException{
		if(!Utils.isNullList(objects)) {
			int pk = getMaxPlusOneId("id", "Category");
			for (CRMModelObject object : objects ) {
				Category category = (Category) object;
				category.setId(pk ++);
			}
		super.batchCreate(objects);
		}
	}
	
	public List<Category> getAllCategories(int company)  {
		Session session = openSession(false);
		Query query = session.createQuery(" from Category where  company.id =:company  " ) ;
		query.setParameter("company", company);
		List lst = query.list();
		closeSession(session, false);
		return lst;
	}

}
