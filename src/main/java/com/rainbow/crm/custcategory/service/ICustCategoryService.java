package com.rainbow.crm.custcategory.service;

import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.custcategory.model.CustCategory;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.framework.query.model.QueryReport;

public interface ICustCategoryService extends ITransactionService{
	
  public QueryReport checCustomers(CustCategory custCategory, CRMContext context);
  
  public boolean isCustomerInCategory (CustCategory custCategory, Customer customer);
	
}
