package com.rainbow.crm.followup.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.followup.model.Followup;
import com.techtrade.rads.framework.utils.Utils;

public class FollowupDAO extends HibernateDAO {

	@Override
	public Object getById(Object PK) {
		int followupID = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Followup.class, followupID);
		if (obj != null) {
			Followup followup =(Followup) obj;
		}
		closeSession(session,false);
		return obj;
	}
	
	
	public List<Followup> getFollowupsforDayforAlerts(Date startDt, long interval) {
		Session session = openSession(false);
		Query query = session.createQuery(" from Followup where nextFollwup < :nextFollwup    and alerted = false  " ) ;
		query.setParameter("nextFollwup", new Timestamp(startDt.getTime() + interval));
		List<Followup> lst = query.list();
		closeSession(session, false);
		return lst;
	}
	

	public List<Followup> getFollowupsforSalesLead(int lead) {
		Session session = openSession(false);
		Query query = session.createQuery(" from Followup where lead.id = :leadId    and deleted = false  order by followupDate " ) ;
		query.setParameter("leadId", lead);
		List<Followup> lst = query.list();
		closeSession(session, false);
		return lst;
	}
}
