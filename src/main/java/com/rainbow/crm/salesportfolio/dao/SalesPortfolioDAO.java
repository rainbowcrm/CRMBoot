package com.rainbow.crm.salesportfolio.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.filter.model.CRMFilter;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.salesportfolio.model.SalesPortfolio;

public class SalesPortfolioDAO  extends HibernateDAO{

	@Override
	public Object getById(Object PK) {
		int salesPortfolioId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(SalesPortfolio.class, salesPortfolioId);
		closeSession(session, false);
		return obj;
	}

	
	
	public List<SalesPortfolio> getPortfoliosforExpiry(Date date) {
		Session session = openSession(false);
		Query query = session.createQuery(" from SalesPortfolio where endDate < :endDate    and expired = false  " ) ;
		query.setParameter("endDate", new Timestamp(date.getTime() ));
		List<SalesPortfolio> lst = query.list();
		closeSession(session, false);
		return lst;
	}

	public List<Object> getPortfoliosforsku(int divisionId, int itemId, int productId, int brandId, int categoryId) {
		Session session = openSession(false);
		Query query = session.createQuery(" from SalesPortfolio parent  left outer join   parent.salesPortfolioLines  line  " +
		    "  where parent.division.id = :divisionId and  parent.voided = false and parent.expired= false  and(  " +
		     " ( line.portfolioType.code = 'SPFITEM' and line.portfolioKey = :itemId ) or " +
			"  ( line.portfolioType.code = 'SPFPROD' and line.portfolioKey = :productId ) or " +
			" ( line.portfolioType.code = 'SPFBRAND' and line.portfolioKey = :brandId ) or " +
			"  ( line.portfolioType.code = 'SPFCATG' and line.portfolioKey = :categoryId )  " +
				" )" );

		/*Query query = session.createQuery(" from SalesPortfolio parent  left outer join   parent.salesPortfolioLines  line  " +
				"  where parent.division.id = :divisionId and  parent.voided = false and parent.expired= false " );*/
		try {
		query.setCacheable(true);
		query.setParameter("divisionId", divisionId);
		query.setParameter("itemId", String.valueOf(itemId));
		query.setParameter("productId", String.valueOf(productId));
		query.setParameter("brandId", String.valueOf(brandId));
		query.setParameter("categoryId", String.valueOf(categoryId));
		List<Object> lst = query.list();
		System.out.println(lst);
		return lst;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally {
		closeSession(session, false);
		}
		return null;
	}
	
	/*@Override
	public void create(CRMModelObject object) {
		SalesPortfolio salesPortfolio = (SalesPortfolio) object ;
		boolean success = false;
		Session session = openSession(true);
		try {
			session.save(salesPortfolio);
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
