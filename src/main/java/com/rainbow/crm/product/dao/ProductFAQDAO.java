package com.rainbow.crm.product.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.model.ProductFAQ;
import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class ProductFAQDAO extends HibernateDAO{

	@Override
	public Object getById(Object PK) {
		int product = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(ProductFAQ.class, product);
		closeSession(session,false);
		return obj;
	}

	
	public List<ProductFAQ> getByProductId(int product) {
		Session session = openSession(false);
		Query query = session.createQuery(" from ProductFAQ where product.id = :product and deleted= false " ) ;
		query.setParameter("product", product);
		List<ProductFAQ> lst = query.list();
		closeSession(session, false);
		return lst;
	}
	
	public void batchUpdate(List<? extends CRMModelObject> objects)  throws DatabaseException{
		Session session = openSession(true);
		boolean success = false; 
		try {
			for (ModelObject object : objects) {
				session.saveOrUpdate(object);
			}
			session.flush();
			success = true;
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
			throw new DatabaseException(ex,DatabaseException.PERSISTENCE_ERROR);
		}finally {
			closeSession(session,success);
		}
	}

	
	
}
