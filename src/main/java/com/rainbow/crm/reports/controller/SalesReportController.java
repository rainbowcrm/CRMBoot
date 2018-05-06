package com.rainbow.crm.reports.controller;

import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.reports.model.SalesReport;
import com.rainbow.crm.reports.service.IReportService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

public class SalesReportController  extends CRMGeneralController{

	HttpServletResponse resp;
	HttpServletRequest request;
	@Override
	public PageResult submit(ModelObject object) {
		String repType = request.getParameter("reportType") ;
		((SalesReport) object).setReportType(repType);
		return new PageResult();
	}
	@Override
	public IRadsContext generateContext(HttpServletRequest request,HttpServletResponse response,UIPage page) {
		resp = response ;
		this.request= request;
		return CommonUtil.generateContext(request,page);
	}
	
	
	

	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		if("viewReport".equalsIgnoreCase(actionParam)) 
		{
			try {
			IReportService service = (IReportService) SpringObjectFactory.INSTANCE.getInstance("IReportService");
			byte[] byteArray = service.getSalesReport((SalesReport) object, (CRMContext)getContext());
			resp.setContentType("application/xls");
			resp.setHeader("Content-Disposition","attachment; filename=dreport.pdf" );
			
			OutputStream responseOutputStream = resp.getOutputStream();
			responseOutputStream.write(byteArray);
			responseOutputStream.close();
			PageResult result = new PageResult();
			result.setResponseAction(PageResult.ResponseAction.FILEDOWNLOAD);
			return result;
			}catch(Exception ex) {
				Logwriter.INSTANCE.error(ex);
			}
		}
		return super.submit(object, actionParam);
	}
	
	
	public Map <String, String > getAllDivisions() {
		CRMContext ctx = ((CRMContext) getContext());
		boolean allowAll =CommonUtil.allowAllDivisionAccess(ctx);
		Map<String, String> ans = new LinkedHashMap<String, String>();
		IDivisionService service = (IDivisionService) SpringObjectFactory.INSTANCE
				.getInstance("IDivisionService");
		List<Division> divisions = service.getAllDivisions(ctx
				.getLoggedinCompany());
		if (allowAll)
			ans.put("-1", "All Divisions");
		if (!Utils.isNullList(divisions)) {
			for (Division division : divisions) {
				if (allowAll || division.getId() == ctx.getLoggedInUser().getDivision().getId())
					ans.put(String.valueOf(division.getId()), division.getName());
			}
		}
		return ans;
	}
	

}
