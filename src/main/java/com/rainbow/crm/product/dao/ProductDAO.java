package com.rainbow.crm.product.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.category.model.Category;
import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.product.model.Product;
import com.techtrade.rads.framework.utils.Utils;

public class ProductDAO extends HibernateDAO{

	@Override
	public Object getById(Object PK) {
		int product = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Product.class, product);
		closeSession(session,false);
		return obj;
	}
	
	public Product findByName(int company, String name) {
		Product product = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from Product where name = :name and company.id =:company  " ) ;
		query.setParameter("name", name);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst))
			product = (Product) lst.get(0) ;
		closeSession(session, false);
		return product;
	}
	
	@Override
	public void batchCreate(List<CRMModelObject> objects) throws DatabaseException{
		if(!Utils.isNullList(objects)) {
			int pk = getMaxPlusOneId("id", "Product");
			for (CRMModelObject object : objects ) {
				Product product = (Product) object;
				product.setId(pk ++);
			}
		super.batchCreate(objects);
		}
	}

}
