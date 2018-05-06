package com.rainbow.crm.reports.service;

import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.ConnectionCreater;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.reports.model.SalesReport;
import com.techtrade.rads.framework.utils.Utils;

public class ReportService  implements IReportService{

	@Override
	public byte[] getSalesReport(SalesReport report, CRMContext context) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("fromDate", report.getFrom());
		parameters.put("toDate", report.getTo());
		parameters.put("companyId", context.getLoggedinCompany());
		parameters.put("companyName", context.getLoggedInUser().getCompany().getName());
		Connection connection  = ConnectionCreater.getConnection() ;
		URL resource = null;
		if(report.getDivision() == null || report.getDivision().getId() == -1 ) {
			if(Utils.isNull(report.getReportType()) || "DailySales".equalsIgnoreCase(report.getReportType()))
				resource = this.getClass().getResource("/jaspertemplates/Daily_Sales.jrxml");
			else if ("DailyItemSales".equalsIgnoreCase(report.getReportType()))  
				resource = this.getClass().getResource("/jaspertemplates/Daily_Item_Sale.jrxml");
			else if ("DailyProductSales".equalsIgnoreCase(report.getReportType()))  
				resource = this.getClass().getResource("/jaspertemplates/Daily_Product_Sale.jrxml");
			else if ("DailyCategorySales".equalsIgnoreCase(report.getReportType()))  
				resource = this.getClass().getResource("/jaspertemplates/Daily_Category_Sale.jrxml");
			else if ("DailyBrandSales".equalsIgnoreCase(report.getReportType()))  
				resource = this.getClass().getResource("/jaspertemplates/Daily_Brand_Sale.jrxml");
			else if ("DailyAssociateSales".equalsIgnoreCase(report.getReportType()))  
				resource = this.getClass().getResource("/jaspertemplates/Daily_Associate_Sale.jrxml");
		}else {
			IDivisionService divisionService = (IDivisionService)SpringObjectFactory.INSTANCE.getInstance("IDivisionService");
			Division div = (Division)divisionService.getById(report.getDivision().getId());
			report.setDivision(div);
			if(Utils.isNull(report.getReportType()) || "DailySales".equalsIgnoreCase(report.getReportType()))
				resource = this.getClass().getResource("/jaspertemplates/Daily_Division_Sales.jrxml");
			else if ("DailyItemSales".equalsIgnoreCase(report.getReportType()))  
				resource = this.getClass().getResource("/jaspertemplates/Daily_Division_Item_Sale.jrxml");
			else if ("DailyProductSales".equalsIgnoreCase(report.getReportType()))  
				resource = this.getClass().getResource("/jaspertemplates/Daily_Division_Product_Sale.jrxml");
			else if ("DailyCategorySales".equalsIgnoreCase(report.getReportType()))  
				resource = this.getClass().getResource("/jaspertemplates/Daily_Division_Category_Sale.jrxml");
			else if ("DailyBrandSales".equalsIgnoreCase(report.getReportType()))  
				resource = this.getClass().getResource("/jaspertemplates/Daily_Division_Brand_Sale.jrxml");
			else if ("DailyAssociateSales".equalsIgnoreCase(report.getReportType()))  
				resource = this.getClass().getResource("/jaspertemplates/Daily_Division_Associate_Sale.jrxml");
			parameters.put("divisionId", report.getDivision().getId());
			parameters.put("divisionName", report.getDivision().getName());
		}
		
		JasperDesign jasperDesign = JRXmlLoader.load(resource.getPath());
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign); 
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
        byte[] output = JasperExportManager.exportReportToPdf(jasperPrint); 
      // JasperViewer.viewReport(jasperPrint); 
        return output; 
	}
	
	

}
