
package com.rainbow.crm.corpsalesperiod.service;

import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriod;

public interface ICorpSalesPeriodService extends ITransactionService{

  public List<CorpSalesPeriod> getStartingCorpSalesPeriodsforAlerts(Date startDt ) ;
  
  public List<CorpSalesPeriod> getEndCorpSalesPeriodsforAlerts(Date endDt ) ;

  
  public CorpSalesPeriod getActiveCorpSalesPeriod(Date date) ;
  
}
