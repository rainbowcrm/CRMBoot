
package com.rainbow.crm.salesperiod.service;

import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.salesperiod.model.SalesPeriod;

public interface ISalesPeriodService extends ITransactionService{

  public List<SalesPeriod> getStartingSalesPeriodsforAlerts(Date startDt ) ;
  
  public List<SalesPeriod> getEndSalesPeriodsforAlerts(Date endDt ) ;

  public SalesPeriod getSalesPeriodforAssociate(String userId,  Date date) ;
  
  public SalesPeriod getActiveSalesPeriodforDivision(int divisionId,  Date date) ;
  
}
