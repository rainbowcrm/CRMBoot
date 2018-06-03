package com.rainbow.crm.saleslead.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.activation.FileDataSource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMTransactionController;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.enquiry.validator.EnquiryErrorCodes;
import com.rainbow.crm.followup.service.IFollowupService;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadExtended;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.saleslead.validator.SalesLeadErrorCodes;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;


public class SalesLeadExtendedController extends CRMTransactionController{

	ServletContext ctx;
	HttpServletResponse resp;
	
	@Override
	public PageResult submit(ModelObject object) {
		return null;
	}
	
	public ISalesLeadService getService() {
		ISalesLeadService serv = (ISalesLeadService) SpringObjectFactory.INSTANCE.getInstance("ISalesLeadService");
		return serv;
	}

	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		ISalesLeadService service= getService();
		SalesLeadExtended leadExtended = (SalesLeadExtended) object;
		SalesLead lead =  (SalesLead) service.getById(leadExtended.getId());
		PageResult result = new PageResult();
		CRMContext context =(CRMContext) getContext();
		if("printquote".equals(actionParam)) {
			try {
			byte[] byteArray = service.printQuotation((SalesLead) object) ;
			resp.setContentType("application/xls");
			resp.setHeader("Content-Disposition","attachment; filename=quote.pdf" );
			
			OutputStream responseOutputStream = resp.getOutputStream();
			responseOutputStream.write(byteArray);
			responseOutputStream.close();
			result.setResponseAction(PageResult.ResponseAction.FILEDOWNLOAD);
			return result;
			}catch(Exception ex)
			{
				Logwriter.INSTANCE.error(ex);
			}
		}else if ("schedulefollowup".equalsIgnoreCase(actionParam)) {
			IFollowupService followupService = (IFollowupService) SpringObjectFactory.INSTANCE.getInstance("IFollowupService");
			TransactionResult transactionResult =  followupService.createFollowup(leadExtended,lead,(CRMContext) getContext()) ;
			if (transactionResult.getResult().equals(Result.FAILURE))  {
				result.setResult(Result.FAILURE);
				result.setObject(leadExtended);
				result.setErrors(transactionResult.getErrors());
			}
			leadExtended= service.getSalesLeadWithExtension(lead.getId(),context);
			result.setResult(Result.SUCCESS);
			result.setObject(leadExtended);

			return result;
		}
		else if ("emailQuote".equalsIgnoreCase(actionParam)) {
			try { 
			byte[] byteArray = service.printQuotation((SalesLead) object) ;
			FileDataSource source = new FileDataSource(new File(lead.getDocNumber() +"+_quote.pdf"));
			OutputStream sourceOS = source.getOutputStream();
			sourceOS.write(byteArray);
			sourceOS.close();
			service.sendEmailWithQuote(lead, context, ".", source);
			}catch(Exception ex) {
				Logwriter.INSTANCE.error(ex);
			}
		}else if("gensales".equalsIgnoreCase(actionParam)) {
			if (lead.getSales() != null  ) {
				result.addError(CRMValidator.getErrorforCode(context.getLocale(), SalesLeadErrorCodes.SALES_ALREADY_GENERATED));
				result.setResult(Result.FAILURE);
				return result;
			}
			if (lead.getStatus() ==null  || !lead.getStatus().equals(CRMConstants.SALESCYCLE_STATUS.CLOSED)){
				result.addError(CRMValidator.getErrorforCode(context.getLocale(), SalesLeadErrorCodes.SALESLEAD_NOTCLOSED));
				result.setResult(Result.FAILURE);
				return result;
				
			}
			TransactionResult transResult= service.generateSalesOrder(lead,context );
			if(!transResult.hasErrors()) {
				lead.setSales((Sales)transResult.getObject());
				context.setReFetchAfterWrite(false);
				service.update(lead, context);
			}else {
				PageResult  pageResult = new PageResult(transResult) ;
				return pageResult;
			}
		}else if("closelead".equalsIgnoreCase(actionParam)) {
			if (lead.getStatus() !=null  &&
					(lead.getStatus().equals(CRMConstants.SALESCYCLE_STATUS.CLOSED)  || lead.getStatus().equals(CRMConstants.SALESCYCLE_STATUS.FAILED))  ) {
				result.addError(CRMValidator.getErrorforCode(context.getLocale(), SalesLeadErrorCodes.SALESLEAD_ALREADY_COMPLETED));
				result.setResult(Result.FAILURE);
				return result;
			}
			lead.setSalesWon(true);
			lead.setClosureDate(new java.util.Date());
			lead.setStatus(new FiniteValue (CRMConstants.SALESCYCLE_STATUS.CLOSED));
			service.update(lead, (CRMContext) getContext());
		}else if("renounce".equalsIgnoreCase(actionParam)) {
			if(lead.getStatus() !=null  &&
					(lead.getStatus().equals(CRMConstants.SALESCYCLE_STATUS.CLOSED)  || lead.getStatus().equals(CRMConstants.SALESCYCLE_STATUS.FAILED))  ) {
				result.addError(CRMValidator.getErrorforCode(context.getLocale(), SalesLeadErrorCodes.SALESLEAD_ALREADY_COMPLETED));
				result.setResult(Result.FAILURE);
				return result;
			}
			lead.setSalesWon(false);
			lead.setStatus(new FiniteValue (CRMConstants.SALESCYCLE_STATUS.FAILED));
			service.update(lead, (CRMContext) getContext());
		}
		lead =  (SalesLead) service.getById(leadExtended.getId());
		setObject(lead);
		
		result.setObject(lead);
		return result;
	}
	
	@Override
	public IRadsContext generateContext(HttpServletRequest request,HttpServletResponse response,UIPage page) {
		ctx =  request.getServletContext() ;
		resp = response ;
		return CommonUtil.generateContext(request,page);
	}
	
	
	
	

}
