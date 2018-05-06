package com.rainbow.crm.brand.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.brand.model.Brand;
import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class BrandDAO extends HibernateDAO{

	@Override
	public Object getById(Object PK) {
		int brand = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Brand.class, brand);
		closeSession(session,false);
		return obj;
	}
	
	public Brand findByName(int company, String name) {
		Brand brand = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Brand where name = :name and company.id =:company  " ) ;
		query.setParameter("name", name);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst))
			brand = (Brand) lst.get(0) ;
		closeSession(session, false);
		return brand;
	}
	
	@Override
	public void batchCreate(List<CRMModelObject> objects) throws DatabaseException{
		if(!Utils.isNullList(objects)) {
			int pk = getMaxPlusOneId("id", "Brand");
			for (CRMModelObject object : objects ) {
				Brand brand = (Brand) object;
				brand.setId(pk ++);
			}
		super.batchCreate(objects);
		}
	}
	
	public List<Brand> getAllBrands(int company)  {
		Session session = openSession(false);
		Query query = session.createQuery(" from Brand where  company.id =:company  " ) ;
		query.setParameter("company", company);
		List lst = query.list();
		closeSession(session, false);
		return lst;
	}

}
