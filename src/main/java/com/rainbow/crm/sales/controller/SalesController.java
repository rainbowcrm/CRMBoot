package com.rainbow.crm.sales.controller;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMTransactionController;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.distributionorder.service.IDistributionOrderService;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.promotion.engines.PromotionEngine;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.service.ISalesService;
import com.rainbow.crm.sales.validator.SalesErrorCodes;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.TransactionController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

public class SalesController extends CRMTransactionController{
	
	ServletContext ctx;
	HttpServletResponse resp;
	
	public boolean isAssociateHeaderLevel()
	{
		CRMContext ctx= (CRMContext)getContext() ;
		String slsAssociation = ConfigurationManager.getConfig(ConfigurationManager.SALESPERSONAL_ASSOCIATION, ctx);
		if(CRMConstants.SALESORDER_ASSOCIATION.ORDER_HEADER.equals(slsAssociation) || CRMConstants.SALESORDER_ASSOCIATION.ORDER_HEAD_LINE.equals(slsAssociation) )
			return true;
		else
			return false;
	}
	
	public boolean isAssociateLineLevel()
	{
		CRMContext ctx= (CRMContext)getContext() ;
		String slsAssociation = ConfigurationManager.getConfig(ConfigurationManager.SALESPERSONAL_ASSOCIATION, ctx);
		if(CRMConstants.SALESORDER_ASSOCIATION.ORDER_LINE.equals(slsAssociation) || CRMConstants.SALESORDER_ASSOCIATION.ORDER_HEAD_LINE.equals(slsAssociation) )
			return true;
		else
			return false;
	}
	
	@Override
	public PageResult submit(ModelObject object) {
		return super.submit(object);
	}

	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		if("createDO".equals(actionParam)) {
			IDistributionOrderService distributionservice = (IDistributionOrderService)SpringObjectFactory.INSTANCE.getInstance("IDistributionOrderService") ;
			Sales sales =(Sales)getService().getById(object.getPK());
			distributionservice.createDOfromSalesOrder(sales, (CRMContext)getContext()) ;
			return new PageResult();
		}else if ( "applyPromotion".equals(actionParam)  ) {
			Sales sales = (Sales) object ;
			super.adaptfromUI(sales);
			PageResult result = new PageResult();
			List<RadsError > errors = super.validateforCreate() ;
			if (Utils.isNullList(errors))
				PromotionEngine.applyPromotions(sales, (CRMContext) getContext());
			else
				result.setErrors(errors);
			result.setObject(sales);
			return result;
		}else if ( "emailInvoice".equals(actionParam)  ) { 
			PageResult result = new PageResult();
			ISalesService  salesService = (ISalesService) getService();
			TransactionResult transResult = salesService.emailInvoice((Sales) object, (CRMContext) getContext());
			result.setResult(transResult.getResult());
			result.setErrors(transResult.getErrors());
			result.setObject((Sales) object);
			return result;
		} else if ( "getLoayltyDiscount".equals(actionParam)  ) {
			 Sales sales = (Sales) object ;
			 if(sales.getLoyaltyRedeemed() != null && sales.getLoyaltyRedeemed().doubleValue() > 0 ) {
				 PageResult result =new PageResult();
				 double loyaltyRedeeming = sales.getLoyaltyRedeemed().doubleValue();
				 double avaibaleLoyalty = sales.getAvailableLoyalty();
				 if (loyaltyRedeeming > avaibaleLoyalty)  {
					 RadsError error  = CRMValidator.getErrorforCode(((CRMContext)getContext()).getLocale(),
							 SalesErrorCodes.REDEEM_LOYALTY_GREATER_THAN_AVLBLE);
					 result.addError(error);
					 return result;
				 }
				 String loyaltyDiscount = ConfigurationManager.getConfig(ConfigurationManager.REDEEM_LOYALTY,(CRMContext)getContext());
				 Double loyaltDiscAmount = Double.parseDouble(loyaltyDiscount) * loyaltyRedeeming ;
				 sales.setLoyaltyDiscount(loyaltDiscAmount);
				 result.setObject(sales);
				 return result;
			 }
			 return new PageResult();
		}else
			return super.submit(object, actionParam);
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request,HttpServletResponse response,UIPage page) {
		ctx =  request.getServletContext() ;
		resp = response ;
		return CommonUtil.generateContext(request,page);
	}
	
	public ISalesService getService() {
		ISalesService serv = (ISalesService) SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		return serv;
	}


	public Map <String, String > getDeliveryModes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_DELIVERY_MODE);
		return ans;
	}
	
	public Map <String, String > getOrderTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_ORDERTYPE);
		return ans;
	}
	
	public Map <String, String > getAssociates() {
		Map<String, String> ans = new HashMap<String,String>();
		List<User> userList  = null ;
		if ( getObject() !=null ) {
			Sales sales = (Sales) getObject();
			IUserService userService  = (IUserService) SpringObjectFactory.INSTANCE.getInstance("IUserService") ;
			if (sales.getDivision() != null && sales.getDivision().getId() > 0 ){
				userList = (List<User>)userService.findAll("User"," where division.id= "  + sales.getDivision().getId() ,"userId",(CRMContext)getContext());
			}else
				userList = (List<User>)userService.findAll("User",null,"userId",(CRMContext)getContext());
				
		}
		if(!Utils.isNullList(userList)) {
			userList.forEach( user ->  { 
				ans.put(user.getUserId(), user.getUserId());
			});
		}
		return ans;
	}
	
	@Override
	public PageResult print() {
		PageResult result  = new PageResult();
		try {
		ISalesService salesService = getService();
		
		byte[] byteArray = salesService.printInvoice((Sales) object,(CRMContext) getContext()) ;
		resp.setContentType("application/xls");
		resp.setHeader("Content-Disposition","attachment; filename=receipt.pdf" );
		OutputStream responseOutputStream = resp.getOutputStream();
		responseOutputStream.write(byteArray);
		responseOutputStream.close();
		result.setResponseAction(PageResult.ResponseAction.FILEDOWNLOAD);
		return result;
		
	/*	String htmlData = salesService.generateInvoice((Sales) object,(CRMContext)getContext());
        OutputStream responseOutputStream = resp.getOutputStream();
        
        resp.setContentType("application/html");
		resp.setHeader("Content-Disposition","attachment; filename=inv.html" );
        responseOutputStream.write(htmlData.getBytes());
        responseOutputStream.close();
        result.setResponseAction(PageResult.ResponseAction.FILEDOWNLOAD);*/
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		return result;
	}
	
	public Map <String, String > getAllDivisions() {
		return CommonUtil.getAllDivisions((CRMContext)getContext());
	}

}
