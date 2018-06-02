package com.rainbow.crm.followup.service;

import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.followup.model.Followup;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadExtended;
import com.techtrade.rads.framework.model.transaction.TransactionResult;

public interface IFollowupService extends IBusinessService{
	

	 public List<Followup> getFollowupsforDayforAlerts(Date startDt ) ;
	 
	 public List<Followup> findBySalesLead(SalesLead lead) ;

	 public TransactionResult createFollowup(SalesLeadExtended salesLeadExtended ,SalesLead lead, CRMContext context) ;
}
