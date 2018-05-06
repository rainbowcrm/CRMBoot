package com.rainbow.crm.salespitch.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.salespitch.model.Salespitch;
import com.techtrade.rads.framework.utils.Utils;

public class SalespitchDAO extends HibernateDAO {

	@Override
	public Object getById(Object PK) {
		int salespitchID = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Salespitch.class, salespitchID);
		if (obj != null) {
			Salespitch salespitch =(Salespitch) obj;
		}
		closeSession(session,false);
		return obj;
	}
	
	
	public List<Salespitch> getSalespitchsforDayforAlerts(Date startDt, long interval) {
		Session session = openSession(false);
		Query query = session.createQuery(" from Salespitch where nextFollwup < :nextFollwup    and alerted = false  " ) ;
		query.setParameter("nextFollwup", new Timestamp(startDt.getTime() + interval));
		List<Salespitch> lst = query.list();
		closeSession(session, false);
		return lst;
	}
	

}
