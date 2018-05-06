package com.rainbow.crm.product.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.product.model.ProductFAQ;
import com.rainbow.crm.product.model.ProductPriceRange;
import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class ProductPriceRangeDAO extends HibernateDAO{
	
	@Override
	public Object getById(Object PK) {
		int product = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(ProductPriceRange.class, product);
		closeSession(session,false);
		return obj;
	}

	
	public List<ProductPriceRange> getByProductId(int product) {
		Session session = openSession(false);
		Query query = session.createQuery(" from ProductPriceRange where product.id = :product and deleted= false " ) ;
		query.setParameter("product", product);
		List<ProductPriceRange> lst = query.list();
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
