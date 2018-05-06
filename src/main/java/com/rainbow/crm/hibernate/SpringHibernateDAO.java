package com.rainbow.crm.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public abstract class SpringHibernateDAO  extends ORMDAO{
	
	private  SessionFactory sessionFactory = null;

    public  SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public  void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	

	/**
	 * No need to set any value for  openTransaction , Spring manages it.. 
	 * @param openTransaction
	 * @return
	 */
	protected Session openSession(boolean openTransaction) {  // Don't use this parameter : let spring manage Transactions..
    	return sessionFactory.openSession() ;
    }

    protected Session getCurrentSession() {
    	return sessionFactory.getCurrentSession();
    }
    
    /**
     * No need to set commitTrans ;; it is present just to keep consistence with HibernateDAO
     * @param session
     * @param commitTrans
     */
    protected void closeSession(Session session,boolean commitTrans) {
    	//sessionFactory.getCurrentSession().close();
    	session.close();
    }
	
}
