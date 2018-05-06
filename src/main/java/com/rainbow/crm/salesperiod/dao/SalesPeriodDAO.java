package com.rainbow.crm.salesperiod.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.filter.model.CRMFilter;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.salesperiod.model.SalesPeriodAssociate;
import com.techtrade.rads.framework.utils.Utils;

public class SalesPeriodDAO  extends HibernateDAO{

	@Override
	public Object getById(Object PK) {
		int salesPeriodId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(SalesPeriod.class, salesPeriodId);
		closeSession(session, false);
		return obj;
	}

	public List<SalesPeriod> getStartingSalesPeriodsforAlerts(Date startDt )
	{
		Session session = openSession(false);
		Query query = session.createQuery(" from SalesPeriod where fromDate = :fromDate    and startAlerted = false and voided= false " ) ;
		query.setParameter("fromDate", startDt);
		List<SalesPeriod> lst = query.list();
		closeSession(session, false);
		return lst;
	}
	
	public List<SalesPeriod> getEndingSalesPeriodsforAlerts(Date toDate )
	{
		Session session = openSession(false);
		Query query = session.createQuery(" from SalesPeriod where toDate = :toDate    and endAlerted = false and voided= false " ) ;
		query.setParameter("toDate", toDate);
		List<SalesPeriod> lst = query.list();
		closeSession(session, false);
		return lst;
	}
	
	
	public SalesPeriod getActiveSalesPeriodforDivision(int divisionId,  Date toDate )
	{
		Session session = openSession(false);
		try 
		{
			Query query = session.createQuery("from SalesPeriod  SalesPeriod  "
			+ "   where SalesPeriod.voided=false and SalesPeriod.fromDate <= :fromDate and SalesPeriod.toDate >= :toDate and   SalesPeriod.division.id = :divisionId" ) ;
			query.setDate("toDate", toDate);
			query.setDate("fromDate", toDate);
			query.setParameter("divisionId", divisionId);
			List	lst = query.list();
			if (!Utils.isNullList(lst)) {
				SalesPeriod period = (SalesPeriod)lst.stream().findFirst().get();
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
	
	public SalesPeriod getActiveSalesPeriodforAssociate(String userId,  Date toDate )
	{
		Session session = openSession(false);
		try 
		{
			Query query = session.createQuery("from SalesPeriod  SalesPeriod left outer join  SalesPeriod.salesPeriodAssociates salesPeriodAssociate "
			+ "   where SalesPeriod.voided=false and SalesPeriod.fromDate <= :fromDate and SalesPeriod.toDate >= :toDate and   salesPeriodAssociate.user.userId = :userId" ) ;
			query.setDate("toDate", toDate);
			query.setDate("fromDate", toDate);
			query.setParameter("userId", userId);
			List	lst = query.list();
			if (!Utils.isNullList(lst)) {
				Object[] objects = (Object[])lst.stream().findFirst().get();
				SalesPeriod period = (SalesPeriod) objects[0]  ;
				SalesPeriodAssociate associate = (SalesPeriodAssociate)objects[1] ;
				period.addSalesPeriodAssociate(associate);
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
		SalesPeriod salesPeriod = (SalesPeriod) object ;
		boolean success = false;
		Session session = openSession(true);
		try {
			session.save(salesPeriod);
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
