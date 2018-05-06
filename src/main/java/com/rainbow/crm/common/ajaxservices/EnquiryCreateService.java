package com.rainbow.crm.common.ajaxservices;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.enquiry.model.Enquiry;
import com.rainbow.crm.enquiry.service.IEnquiryService;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxUpdateService;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class EnquiryCreateService implements  IAjaxUpdateService {

	@Override
	public String update(String jsonString, IRadsContext ctx) {
		try {
			JSONObject json = new JSONObject(jsonString);
			String user = json.getString("user") ;
			String companyCode = user.substring(user.indexOf("@")+1,user.length());
			//String companyCode = user.substring(beginIndex)
			ICompanyService companyService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
			Company company = (Company)companyService.findByCode(companyCode);
			JSONObject enquiryJSON = json.getJSONObject("Enquiry");
			Enquiry enquiry = (Enquiry) Enquiry.instantiateObjectfromJSON(enquiryJSON.toString(), Enquiry.class.getName()	,ctx);
			enquiry.setCompany(company);
			((CRMContext)ctx).setLoggedinCompany(company.getId());
			IEnquiryService enquiryService = (IEnquiryService) SpringObjectFactory.INSTANCE.getInstance("IEnquiryService");
			TransactionResult result  = (TransactionResult)enquiryService.createFromScratch(enquiry, ((CRMContext)ctx));
			enquiry = (Enquiry)result.getObject() ;
		return enquiry.toJSON() ;
		
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		return "";
	
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request) {
		CRMContext context= (CRMContext) CommonUtil.generateContext(request,null);
		context.setAuthenticated(true);
		context.setAuthorized(true);
		return context;
	}
	
	
	

}
