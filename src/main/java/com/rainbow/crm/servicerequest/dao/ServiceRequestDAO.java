package com.rainbow.crm.servicerequest.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.servicerequest.model.ServiceRequest;
import com.rainbow.crm.servicerequest.model.ServiceRequestLine;
import com.techtrade.rads.framework.utils.Utils;

public class ServiceRequestDAO  extends HibernateDAO{

	@Override
	public Object getById(Object PK) {
		int serviceRequestId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(ServiceRequest.class, serviceRequestId);
		closeSession(session, false);
		return obj;
	}


	
	public ServiceRequest getBySalesBill( String billNumber , int company )
	{
		Session session = openSession(false);
		Query query = session.createQuery(" from ServiceRequest where company.id = :company    and sales.billNumber = :billNumber and deleted= false " ) ;
		query.setParameter("company", company);
		query.setParameter("billNumber", billNumber);
		List<ServiceRequest> lst = query.list();
		closeSession(session, false);
		if(!Utils.isNullList(lst))
			return lst.stream().findFirst().get();
		else
			return null;
		
	}


	public List<ServiceRequestLine> getByItem( int item , int company , Date fromDate, Date toDate)
	{
		Session session = openSession(false);
		/*Query query = session.createQuery("  Select ServiceRequestLine.rating, ServiceRequestLine.comments,ServiceRequest.customer.firstName,ServiceRequest.customer.firstName,  " +
		"ServiceRequest.customer.phone,ServiceRequest.serviceRequestDate, ServiceRequest.division.name,ServiceRequestLine.sku.name  from ServiceRequestLine ServiceRequestLine  left outer join ServiceRequestLine.serviceRequestDoc ServiceRequest " +
		"  left outer join ServiceRequest.customer  Customer   where   ServiceRequestLine.serviceRequestObjectType.code ='FDBKSLSLN'  and   ServiceRequestLine.sku.item.id = :item   " +
		"  and   ServiceRequest.serviceRequestDate >= :fromDate and  ServiceRequest.serviceRequestDate <=  :toDate  and ServiceRequestLine.deleted= false and ServiceRequestLine.deleted = false " ) ;*/
		
		/*Query query = session.createQuery("  Select ServiceRequestLine.rating, ServiceRequestLine.comments,ServiceRequestLine.serviceRequestDoc.customer.firstName,ServiceRequestLine.serviceRequestDoc.customer.lastName,  " +
				"ServiceRequestLine.serviceRequestDoc.customer.phone,ServiceRequestLine.serviceRequestDoc.serviceRequestDate, ServiceRequestLine.serviceRequestDoc.division.name,ServiceRequestLine.sku.name  from ServiceRequestLine ServiceRequestLine where ServiceRequestLine.sku.item.id = :item   " +
				"  and    ServiceRequestLine.serviceRequestObjectType.code ='FDBKSLSLN' and ServiceRequestLine.serviceRequestDoc.serviceRequestDate >= :fromDate and  ServiceRequestLine.serviceRequestDoc.serviceRequestDate <=  :toDate  and ServiceRequestLine.deleted= false and ServiceRequestLine.serviceRequestDoc.deleted = false " ) ;
				*/
		
		Query query = session.createQuery("  from ServiceRequestLine ServiceRequestLine where ServiceRequestLine.sku.item.id = :item   " +
				"  and     ServiceRequestLine.serviceRequestDoc.serviceRequestDate >= :fromDate and  ServiceRequestLine.serviceRequestDoc.serviceRequestDate <=  :toDate  and ServiceRequestLine.deleted= false and ServiceRequestLine.serviceRequestDoc.deleted = false " ) ;
			
		
		query.setParameter("item", item);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		
		List<ServiceRequestLine> lst = query.list();
		closeSession(session, false);
		return lst;
		
	}
	

	public List<ServiceRequestLine> getByCustomer( int customer , int company , Date fromDate, Date toDate)
	{
		Session session = openSession(false);
		Query query = session.createQuery("  from ServiceRequestLine ServiceRequestLine where ServiceRequestLine.serviceRequestDoc.customer.id = :customer   " +
				"  and     ServiceRequestLine.serviceRequestDoc.serviceRequestDate >= :fromDate and  ServiceRequestLine.serviceRequestDoc.serviceRequestDate <=  :toDate  and ServiceRequestLine.deleted= false and ServiceRequestLine.serviceRequestDoc.deleted = false " ) ;
		query.setParameter("customer", customer);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		
		List<ServiceRequestLine> lst = query.list();
		closeSession(session, false);
		return lst;
		
	}
	
	
}
