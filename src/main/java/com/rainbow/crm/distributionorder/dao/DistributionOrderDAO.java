package com.rainbow.crm.distributionorder.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.hibernate.SpringHibernateDAO;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.distributionorder.model.DistributionOrder;
import com.rainbow.crm.distributionorder.model.DistributionOrderLine;
import com.techtrade.rads.framework.utils.Utils;

public class DistributionOrderDAO  extends HibernateDAO{

	@Override
	public Object getById(Object PK) {
		int distributionOrderId = Integer.parseInt(String.valueOf(PK));
		Session session = openSession(false);
		Object obj = session.get(DistributionOrder.class, distributionOrderId);
		closeSession(session, false);
		massageDOLines(((DistributionOrder)obj).getDistributionOrderLines());
		return obj;
	}
	
	private void  massageDOLines(Set<DistributionOrderLine> lines)  {
		lines.forEach(line -> { 
			if (line.getPickDate() != null)
				line.setPicked(true);
			
		});
		
	}


	
	

	
	
	
}
