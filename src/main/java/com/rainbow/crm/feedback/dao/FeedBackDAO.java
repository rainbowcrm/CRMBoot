package com.rainbow.crm.feedback.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.feedback.model.FeedBack;
import com.rainbow.crm.feedback.model.FeedBackLine;
import com.techtrade.rads.framework.utils.Utils;

public class FeedBackDAO  extends HibernateDAO{

	@Override
	public Object getById(Object PK) {
		int feedBackId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(FeedBack.class, feedBackId);
		closeSession(session, false);
		return obj;
	}


	
	public FeedBack getBySalesBill( String billNumber , int company )
	{
		Session session = openSession(false);
		Query query = session.createQuery(" from FeedBack where company.id = :company    and sales.billNumber = :billNumber and deleted= false " ) ;
		query.setParameter("company", company);
		query.setParameter("billNumber", billNumber);
		List<FeedBack> lst = query.list();
		closeSession(session, false);
		if(!Utils.isNullList(lst))
			return lst.stream().findFirst().get();
		else
			return null;
		
	}


	public List<FeedBackLine> getByItem( int item , int company , Date fromDate, Date toDate)
	{
		Session session = openSession(false);
		/*Query query = session.createQuery("  Select FeedBackLine.rating, FeedBackLine.comments,FeedBack.customer.firstName,FeedBack.customer.firstName,  " +
		"FeedBack.customer.phone,FeedBack.feedBackDate, FeedBack.division.name,FeedBackLine.sku.name  from FeedBackLine FeedBackLine  left outer join FeedBackLine.feedBackDoc FeedBack " +
		"  left outer join FeedBack.customer  Customer   where   FeedBackLine.feedBackObjectType.code ='FDBKSLSLN'  and   FeedBackLine.sku.item.id = :item   " +
		"  and   FeedBack.feedBackDate >= :fromDate and  FeedBack.feedBackDate <=  :toDate  and FeedBackLine.deleted= false and FeedBackLine.deleted = false " ) ;*/
		
		/*Query query = session.createQuery("  Select FeedBackLine.rating, FeedBackLine.comments,FeedBackLine.feedBackDoc.customer.firstName,FeedBackLine.feedBackDoc.customer.lastName,  " +
				"FeedBackLine.feedBackDoc.customer.phone,FeedBackLine.feedBackDoc.feedBackDate, FeedBackLine.feedBackDoc.division.name,FeedBackLine.sku.name  from FeedBackLine FeedBackLine where FeedBackLine.sku.item.id = :item   " +
				"  and    FeedBackLine.feedBackObjectType.code ='FDBKSLSLN' and FeedBackLine.feedBackDoc.feedBackDate >= :fromDate and  FeedBackLine.feedBackDoc.feedBackDate <=  :toDate  and FeedBackLine.deleted= false and FeedBackLine.feedBackDoc.deleted = false " ) ;
				*/
		
		Query query = session.createQuery("  from FeedBackLine FeedBackLine where FeedBackLine.sku.item.id = :item   " +
				"  and    FeedBackLine.feedBackObjectType.code ='FDBKSLSLN' and FeedBackLine.feedBackDoc.feedBackDate >= :fromDate and  FeedBackLine.feedBackDoc.feedBackDate <=  :toDate  and FeedBackLine.deleted= false and FeedBackLine.feedBackDoc.deleted = false " ) ;
			
		
		query.setParameter("item", item);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		
		List<FeedBackLine> lst = query.list();
		closeSession(session, false);
		return lst;
		
	}
	

	public List<FeedBackLine> getByCustomer( int customer , int company , Date fromDate, Date toDate)
	{
		Session session = openSession(false);
		Query query = session.createQuery("  from FeedBackLine FeedBackLine where FeedBackLine.feedBackDoc.customer.id = :customer   " +
				"  and     FeedBackLine.feedBackDoc.feedBackDate >= :fromDate and  FeedBackLine.feedBackDoc.feedBackDate <=  :toDate  and FeedBackLine.deleted= false and FeedBackLine.feedBackDoc.deleted = false " ) ;
		query.setParameter("customer", customer);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		
		List<FeedBackLine> lst = query.list();
		closeSession(session, false);
		return lst;
		
	}
	
	
}
