package com.rainbow.crm.common.scheduler;

import java.util.Date;
import java.util.List;






import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.alert.model.Alert;
import com.rainbow.crm.alert.service.IAlertService;
import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.followup.model.Followup;
import com.rainbow.crm.followup.service.IFollowupService;
import com.rainbow.crm.hibernate.HibernateDAO;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.service.ISalesService;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.salesperiod.service.ISalesPeriodService;
import com.rainbow.crm.salesportfolio.model.SalesPortfolio;
import com.rainbow.crm.salesportfolio.service.ISalesPortfolioService;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.utils.Utils;

public class SchedulerThread extends Thread{

	int interval ;
	
	
	private CRMContext makeContext(CRMBusinessModelObject model) {
		CRMContext context = new CRMContext();
		context.setLoggedinCompany(model.getCompany().getId());
		context.setAuthenticated(true);
		context.setUser(model.getLastUpdateUser());
		return context ;
	}
	
	private void createAlertforFollowup(Followup followup,CRMContext context) {
		IAlertService service = (IAlertService)SpringObjectFactory.INSTANCE.getInstance("IAlertService");
		Alert alert = new Alert();
		  alert.setCompany(followup.getCompany());
		  alert.setType (new FiniteValue(CRMConstants.ALERT_TYPE.FOLLOWUP));
		  alert.setActionDate(new java.util.Date());
		  alert.setDivision(followup.getLead().getDivision());
		  alert.setRaisedDate(new java.util.Date());
		  alert.setData("Followup due  for-" +  followup.getLead().getDocNumber());
		  alert.setUrl("./rdscontroller?page=newfollowup&id="+followup.getId() +"&hdnFixedAction=FixedAction.ACTION_GOEDITMODE");
		  alert.setStatus(new FiniteValue(CRMConstants.ALERT_STATUS.OPEN));
		  service.create(alert, context);
	}
	
	private void createAlertforSalesPeriod(SalesPeriod period,CRMContext context, FiniteValue alertType) {
		IAlertService service = (IAlertService)SpringObjectFactory.INSTANCE.getInstance("IAlertService");
		Alert alert = new Alert();
		  alert.setCompany(period.getCompany());
		  alert.setType (alertType);
		  alert.setActionDate(new java.util.Date());
		  alert.setDivision(period.getDivision());
		  alert.setRaisedDate(new java.util.Date());
		  alert.setData("Sales Targetting Start-" +  period.getPeriod());
		  alert.setUrl("./rdscontroller?page=newsalesperiod&id="+period.getId() +"&hdnFixedAction=FixedAction.ACTION_GOEDITMODE");
		  alert.setStatus(new FiniteValue(CRMConstants.ALERT_STATUS.OPEN));
		  service.create(alert, context);
	}
	
	private void createAlertforSales(Sales sales,CRMContext context, FiniteValue alertType,User owner) {
		IAlertService service = (IAlertService)SpringObjectFactory.INSTANCE.getInstance("IAlertService");
		Alert alert = new Alert();
		  alert.setCompany(sales.getCompany());
		  alert.setType (alertType);
		  alert.setActionDate(new java.util.Date());
		  alert.setDivision(sales.getDivision());
		  alert.setRaisedDate(new java.util.Date());
		  alert.setOwner(owner);
		  alert.setData("Capture Feedback for Sales -" + sales.getBillNumber());
		  alert.setUrl("./rdscontroller?page=newsales&id="+sales.getId() +"&hdnFixedAction=FixedAction.ACTION_GOEDITMODE");
		  alert.setStatus(new FiniteValue(CRMConstants.ALERT_STATUS.OPEN));
		  service.create(alert, context);
	}
	
	@Override
	public void run() 
	{
		try {
		for (; ; ) {
				if (HibernateDAO.getHibernateSessionFactory() != null) {
					raiseSalesPeriodAlerts();
					raiseFollowupAlerts();
					expireSalesPortFolios();
					raiseSalesFeedBackAlerts();
				}
		Thread.sleep(interval);
		}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}

	private void expireSalesPortFolios() {
		ISalesPortfolioService service = (ISalesPortfolioService) SpringObjectFactory.INSTANCE.getInstance("ISalesPortfolioService") ;
		List<SalesPortfolio> portfolios =service.getPortfoliosforExpiry(new java.util.Date());
		if(! Utils.isNullList(portfolios)) {
			for (SalesPortfolio portfolio : portfolios)  {
				CRMContext context =makeContext(portfolio);
				portfolio.setExpired(true);
				service.update(portfolio, context);
			}
		}
	}
	
	private void raiseFollowupAlerts () {
		IFollowupService service  = (IFollowupService) SpringObjectFactory.INSTANCE.getInstance("IFollowupService") ;
		List<Followup> followups = service.getFollowupsforDayforAlerts(new java.util.Date());
		if(!Utils.isNullList(followups)) {
			for (Followup followup : followups) {
				CRMContext context =makeContext(followup);
				createAlertforFollowup(followup,context);
				followup.setAlerted(true);
				service.update(followup, context);
			}
		}
	}
	
	private void raiseSalesFeedBackAlerts() 
	{
		ICompanyService companyService = (ICompanyService) SpringObjectFactory.INSTANCE.getInstance("ICompanyService") ;
		ISalesService service = (ISalesService) SpringObjectFactory.INSTANCE.getInstance("ISalesService") ;
		List<Company> activeCompanies = companyService.findAllActiveCompanies();
		activeCompanies.forEach( company-> { 
			String intervalValue =  ConfigurationManager.getConfig(ConfigurationManager.FEEDBACK_INTERVAL, company.getId());
			int interval =  Integer.parseInt(intervalValue);
			List<Sales> salesToBeAlerted = service.getNonAlertedSalesFeedBack(company.getId(), interval);
			salesToBeAlerted.forEach( sales ->  { 
				User owner = sales.getSalesMan() ;
				CRMContext context =makeContext(sales);
				if (owner == null ) {
					owner = CommonUtil.getDivisionManager(sales.getDivision(), context);
					
				}
				sales.setFeedBackAlerted(true);
				service.update(sales, context);
				createAlertforSales(sales, context, new FiniteValue( CRMConstants.ALERT_TYPE.FEEDBACK), owner);
			});
		});
		
		
	
	
	}
	
	private void raiseSalesPeriodAlerts () {
		
		ISalesPeriodService service = (ISalesPeriodService) SpringObjectFactory.INSTANCE.getInstance("ISalesPeriodService") ;
		List<SalesPeriod> startingPeriods = service.getStartingSalesPeriodsforAlerts(new java.util.Date());
		if(!Utils.isNullList(startingPeriods)) {
			for (SalesPeriod salesPeriod :  startingPeriods) {
				CRMContext context =makeContext(salesPeriod);
				createAlertforSalesPeriod(salesPeriod,context,new FiniteValue(CRMConstants.ALERT_TYPE.SLSPERSTART));
				salesPeriod.setStartAlerted(true);
				service.update(salesPeriod, context);
			}
		}
		List<SalesPeriod> endingPeriods = service.getEndSalesPeriodsforAlerts(new java.util.Date());
		if(!Utils.isNullList(endingPeriods)) {
			for (SalesPeriod salesPeriod :  endingPeriods) {
				CRMContext context =makeContext(salesPeriod);
				createAlertforSalesPeriod(salesPeriod,context,new FiniteValue(CRMConstants.ALERT_TYPE.SLSPEREND));
				salesPeriod.setEndAlerted(true);
				service.update(salesPeriod, context);
			}
		}
	}
	
	public SchedulerThread() {
		try {
		String inte = CRMAppConfig.INSTANCE.getProperty("Thread_Interval");
		interval = Integer.parseInt(inte);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	

}
