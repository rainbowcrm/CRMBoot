package com.rainbow.crm.followup.service;

import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.followup.model.Followup;
import com.rainbow.crm.saleslead.model.SalesLead;

public interface IFollowupService extends IBusinessService{
	

	 public List<Followup> getFollowupsforDayforAlerts(Date startDt ) ;
	 
	 public List<Followup> findBySalesLead(SalesLead lead) ;
}
