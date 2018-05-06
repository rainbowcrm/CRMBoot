package com.rainbow.crm.corpsalesperiod.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.filter.model.CRMFilter;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriod;
import com.techtrade.rads.framework.utils.Utils;

public class CorpSalesPeriodDAO  extends HibernateDAO{

	@Override
	public Object getById(Object PK) {
		int corpSalesPeriodId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(CorpSalesPeriod.class, corpSalesPeriodId);
		closeSession(session, false);
		return obj;
	}

	public List<CorpSalesPeriod> getStartingCorpSalesPeriodsforAlerts(Date startDt )
	{
		Session session = openSession(false);
		Query query = session.createQuery(" from CorpSalesPeriod where fromDate = :fromDate    and startAlerted = false and voided= false " ) ;
		query.setParameter("fromDate", startDt);
		List<CorpSalesPeriod> lst = query.list();
		closeSession(session, false);
		return lst;
	}
	
	public List<CorpSalesPeriod> getEndingCorpSalesPeriodsforAlerts(Date toDate )
	{
		Session session = openSession(false);
		Query query = session.createQuery(" from CorpSalesPeriod where toDate = :toDate    and endAlerted = false and voided= false " ) ;
		query.setParameter("toDate", toDate);
		List<CorpSalesPeriod> lst = query.list();
		closeSession(session, false);
		return lst;
	}
	
	
	public CorpSalesPeriod getActiveCorpSalesPeriodforDivision( Date toDate )
	{
		Session session = openSession(false);
		try 
		{
			Query query = session.createQuery("from CorpSalesPeriod  CorpSalesPeriod  "
			+ "   where CorpSalesPeriod.voided=false and CorpSalesPeriod.fromDate <= :fromDate and CorpSalesPeriod.toDate >= :toDate " ) ;
			query.setDate("toDate", toDate);
			query.setDate("fromDate", toDate);
			List	lst = query.list();
			if (!Utils.isNullList(lst)) {
				CorpSalesPeriod period = (CorpSalesPeriod)lst.stream().findFirst().get();
				return period;
			}
		} catch( Exception ex )
		{
			Logwriter.INSTANCE.error(ex);
			
		} finally {
			closeSession(session, false);
		}
		return null;
		
	}
	
		
	
	
	/*@Override
	public void create(CRMModelObject object) {
		CorpSalesPeriod corpSalesPeriod = (CorpSalesPeriod) object ;
		boolean success = false;
		Session session = openSession(true);
		try {
			session.save(corpSalesPeriod);
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
