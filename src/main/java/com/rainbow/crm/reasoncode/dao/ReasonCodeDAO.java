package com.rainbow.crm.reasoncode.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class ReasonCodeDAO extends HibernateDAO{

	@Override
	public Object getById(Object PK) {
		int reasonCode = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(ReasonCode.class, reasonCode);
		closeSession(session,false);
		return obj;
	}
	
	public ReasonCode findByReason(int company, String reason) {
		ReasonCode reasonCode = null;
		Session session = openSession(false);
		Query query = session.createQuery(" from ReasonCode where reason = :reason and company.id =:company  and deleted = false " ) ;
		query.setParameter("reason", reason);
		query.setParameter("company", company);
		List lst = query.list();
		if (!Utils.isNullList(lst))
			reasonCode = (ReasonCode) lst.get(0) ;
		closeSession(session, false);
		return reasonCode;
	}
	
	@Override
	public void batchCreate(List<CRMModelObject> objects) throws DatabaseException{
		if(!Utils.isNullList(objects)) {
			int pk = getMaxPlusOneId("id", "ReasonCode");
			for (CRMModelObject object : objects ) {
				ReasonCode reasonCode = (ReasonCode) object;
				reasonCode.setId(pk ++);
			}
		super.batchCreate(objects);
		}
	}
	
	public List<ReasonCode> getAllReasons(int company)  {
		Session session = openSession(false);
		Query query = session.createQuery(" from ReasonCode where  company.id =:company and deleted=false " ) ;
		query.setParameter("company", company);
		List lst = query.list();
		closeSession(session, false);
		return lst;
	}
	
	public List<ReasonCode> getAllReasonsByType(int company, String type )  {
		Session session = openSession(false);
		Query query = session.createQuery(" from ReasonCode where  company.id =:company  and  deleted= false and reasonType.code =:type" ) ;
		query.setParameter("company", company);
		query.setParameter("type", type);
		List lst = query.list();
		closeSession(session, false);
		return lst;
	}
	
	public List<ReasonCode> getAllReasonsByTypeAndOrientation(int company, String type,String orientation )  {
		Session session = openSession(false);
		Query query = session.createQuery(" from ReasonCode where  company.id =:company  and  deleted= false and  " +
		"reasonType.code =:type and orientation.code = :orientation" ) ;
		query.setParameter("company", company);
		query.setParameter("type", type);
		query.setParameter("orientation", orientation);
		List lst = query.list();
		closeSession(session, false);
		return lst;
	}

}
