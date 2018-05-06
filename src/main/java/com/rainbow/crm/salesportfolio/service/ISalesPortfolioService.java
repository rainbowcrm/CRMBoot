package com.rainbow.crm.salesportfolio.service;

import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.salesportfolio.model.SalesPortfolio;

public interface ISalesPortfolioService extends ITransactionService{

	public List<SalesPortfolio> getPortfoliosforExpiry(Date date) ;
	
	public List<SalesPortfolio> getUsersforItem (Sku sku, int divisionId, Date date) ;


  
}
