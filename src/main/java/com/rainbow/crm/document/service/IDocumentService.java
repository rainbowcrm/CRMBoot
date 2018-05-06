package com.rainbow.crm.document.service;

import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.document.model.Document;
import com.rainbow.crm.followup.model.Followup;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.saleslead.model.SalesLead;

public interface IDocumentService extends IBusinessService{

	public List<Document> findAllBySalesLead(SalesLead lead) ;
	
	public List<Document> findAllByItem(Item item) ;
	
	public List<Document> findAllByCustomer(Customer customer) ;
}
