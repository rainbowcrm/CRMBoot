package com.rainbow.crm.enquiry.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.enquiry.model.Enquiry;
import com.techtrade.rads.framework.utils.Utils;

public class EnquiryDAO extends HibernateDAO {

	@Override
	public Object getById(Object PK) {
		int enquiryID = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Enquiry.class, enquiryID);
		if (obj != null) {
			Enquiry enquiry =(Enquiry) obj;
		}
		closeSession(session,false);
		return obj;
	}
	
	
	public List<Enquiry> getEnquirysforDayforAlerts(Date startDt, long interval) {
		Session session = openSession(false);
		Query query = session.createQuery(" from Enquiry where nextFollwup < :nextFollwup    and alerted = false  " ) ;
		query.setParameter("nextFollwup", new Timestamp(startDt.getTime() + interval));
		List<Enquiry> lst = query.list();
		closeSession(session, false);
		return lst;
	}
	

}
