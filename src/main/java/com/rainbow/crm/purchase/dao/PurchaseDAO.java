package com.rainbow.crm.purchase.dao;

import org.hibernate.Session;

import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.purchase.model.Purchase;

public class PurchaseDAO  extends HibernateDAO{

	@Override
	public Object getById(Object PK) {
		int purchaseId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Purchase.class, purchaseId);
		closeSession(session, false);
		return obj;
	}

	/*@Override
	public void create(CRMModelObject object) {
		Purchase purchase = (Purchase) object ;
		boolean success = false;
		Session session = openSession(true);
		try {
			session.save(purchase);
			session.flush();
			success = true; 
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
			throw new DatabaseException(ex,DatabaseException.DIRTY_READ_ERROR);
		}finally {
			closeSession(session,success);
		}
	}*/
	
	

	
	
	
}
