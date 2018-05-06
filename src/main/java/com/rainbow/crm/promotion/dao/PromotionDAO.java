package com.rainbow.crm.promotion.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.salesportfolio.model.SalesPortfolio;
import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.promotion.model.Promotion;
import com.rainbow.crm.customer.model.Customer;
import com.techtrade.rads.framework.utils.Utils;

public class PromotionDAO extends HibernateDAO {

	@Override
	public Object getById(Object PK) {
		int promotionID = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(Promotion.class, promotionID);
		
		closeSession(session,false);
		return obj;
	}
	
	
	public List<Promotion> getPromotionsforType(int company , Date date, String code)
	{
		Session session = openSession(false);
		Query query = session.createQuery(" from Promotion where startDt <= :salesDate  and endDt >= :salesDate and company.id =:company  " +
		"    and deleted = false  and promoType.code = :code" ) ;
		query.setParameter("salesDate", new Timestamp(date.getTime() ));
		query.setParameter("company", company);
		query.setParameter("code", code);
		List<Promotion> lst = query.list();
		closeSession(session, false);
		return lst;
	}

}
