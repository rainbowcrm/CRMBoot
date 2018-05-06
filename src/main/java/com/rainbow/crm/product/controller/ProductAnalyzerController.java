package com.rainbow.crm.product.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.model.ProductAnalyzer;
import com.rainbow.crm.product.service.IProductService;
import com.rainbow.crm.sales.service.ISalesService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.graphdata.PieChartData;
import com.techtrade.rads.framework.model.graphdata.PieSliceData;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class ProductAnalyzerController  extends CRMGeneralController{

	@Override
	public PageResult submit(ModelObject object) {
		return new PageResult();
	}

	
	
	public String getCompanyName() {
		ICompanyService service = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company =(Company) service.getById(((CRMContext)getContext()).getLoggedinCompany());
		return company.getName();
	}

	@Override
	public PageResult read(ModelObject object) {
		String [] colors = { "Red","Blue" ,"Green" , "Violet" , "Indigo" , "Majenta" ,"Brown" ,"Yellow" , "Orange", 
				"Salmon","Gray","SandyBrown","Ivory","CadetBlue","OrangeRed","SeaGreen"} ;
		ProductAnalyzer analyzer = (ProductAnalyzer) object;
		PieChartData  pieTopChartData = readByItemCass(analyzer,CRMConstants.ITEM_CLASS.TOP_END,colors);
		pieTopChartData.setTitle("Items");
		pieTopChartData.setTitle("Top End");
		analyzer.setTopSalesData(pieTopChartData);
		
		PieChartData  pieupMedChartData = readByItemCass(analyzer,CRMConstants.ITEM_CLASS.UPPER_MEDIUM,colors);
		pieupMedChartData.setTitle("Items");
		pieupMedChartData.setTitle("Upper Medium");
		analyzer.setUpMedSalesData(pieupMedChartData);
		
		PieChartData  pielowMedChartData = readByItemCass(analyzer,CRMConstants.ITEM_CLASS.LOWER_MEDIUM,colors);
		pielowMedChartData.setTitle("Items");
		pielowMedChartData.setTitle("Lower Medium");
		analyzer.setLowMedSalesData(pielowMedChartData);
		
		PieChartData  pieeconChartData = readByItemCass(analyzer,CRMConstants.ITEM_CLASS.ECONOMIC,colors);
		pieeconChartData.setTitle("Items");
		pieeconChartData.setTitle("Economic");
		analyzer.setEconSalesData(pieeconChartData);
		
		return new PageResult();
	}
	
	private PieChartData readByItemCass  (ProductAnalyzer analyzer , String itemClass, String []colors) {
		int index = 0;
		PieChartData pieChartData = new PieChartData();
		Date fromDate = analyzer.getFromDate() ;
		Date toDate = analyzer.getToDate() ;
		Product product = analyzer.getProduct();
		IProductService service = (IProductService)SpringObjectFactory.INSTANCE.getInstance("IProductService");
		product =(Product) service.getByBusinessKey(product,(CRMContext) getContext());
		ISalesService salesService = (ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		ISkuService itemService = (ISkuService)SpringObjectFactory.INSTANCE.getInstance("ISkuService");
		Map map = salesService.getItemSoldQtyByProduct(product, fromDate, toDate,null,itemClass) ;
		Set keys = map.keySet();
		Iterator it = keys.iterator();
		while (it.hasNext()) {
			PieSliceData pieSliceData = new PieSliceData();
			Integer itemId = (Integer) it.next() ;
			Sku item = (Sku)itemService.getById(itemId);
			Double qty = (Double) map.get(itemId);
			pieSliceData.setVolume(qty);
			pieSliceData.setText(item.getName() + "-" +  qty);
			pieSliceData.setColor(colors[index ++ ]);
			pieChartData.addPieSlice(pieSliceData);
		}
		FiniteValue desc = GeneralSQLs.getFiniteValue(itemClass);
		pieChartData.setFooterNote(product.getName() + "-" + desc.getDescription());
		return pieChartData;
	}
	
	
	

}
