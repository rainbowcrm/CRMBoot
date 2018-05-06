package com.rainbow.crm.sales.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.service.IProductService;
import com.rainbow.crm.sales.model.SalesTrend;
import com.rainbow.crm.sales.service.ISalesService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.graphdata.LineChartData;
import com.techtrade.rads.framework.model.graphdata.LineChartEntryData;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class SalesTrendController  extends CRMGeneralController{

	@Override
	public PageResult submit(ModelObject object) {
		return new PageResult();
	}

	
	@Override
	public PageResult read(ModelObject object) {
		String [] colors = { "Brown" , "Red","Green" , "Violet" , "Indigo" , "Majenta" ,"Yellow" , "Orange", 
				"Salmon","Gray","SandyBrown","Teal","CadetBlue","OrangeRed","SeaGreen","crimson"} ;
		LineChartData lineChartData = new LineChartData();
		SalesTrend trend = (SalesTrend) object;
		int period = trend.getNoItervals() ;
		Date fromDate = trend.getFromDate();
		Date toDate = trend.getToDate() ;
		long diff =  (toDate.getTime()  - fromDate.getTime())/period  ;
		Product selectedProduct =  trend.getProduct();
		IProductService iProdService = (IProductService)SpringObjectFactory.INSTANCE.getInstance("IProductService") ;
		selectedProduct = iProdService.getByName(((CRMContext)getContext()).getLoggedinCompany(), selectedProduct.getName());
		ISkuService itemService = (ISkuService)SpringObjectFactory.INSTANCE.getInstance("ISkuService");
		ISalesService salesService = (ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		List<Sku> items = itemService.getAllByProduct(((CRMContext)getContext()).getLoggedinCompany(), selectedProduct.getId());
		Date periodFrom  = new Date(fromDate.getTime()) ;
		Date periodTo = new Date(periodFrom.getTime() + diff) ;
		int maxValue = 0;
		int colorIndex = 0;
		lineChartData.setBorderColor(colors[colorIndex++]);
		lineChartData.setTitle("Sales Trend");
		lineChartData.setSubTitle(selectedProduct.getName());
		Map <Integer,LineChartEntryData> itemMap = new HashMap<Integer,LineChartEntryData>() ;
		try {
			//lineChartData.addInterval(Utils.dateToString(periodFrom, "dd-MM-yyyy"));
			lineChartData.setStartingPoint(Utils.dateToString(periodFrom, "dd-MM-yyyy"));
			for (int i = 0 ; i <  period  ; i ++) 
			{
				lineChartData.addInterval(Utils.dateToString(periodTo, "dd-MM-yyyy"));	
				Map mapSales = salesService.getItemSoldQtyByProduct(selectedProduct, periodFrom, periodTo, null, null);
				Iterator it = mapSales.keySet().iterator();
				while(it.hasNext()) {
					Integer itemId = (Integer)it.next() ;
					Double qty = (Double) mapSales.get(itemId);
					if (qty > maxValue)
						maxValue = new Double(qty).intValue(); 
					LineChartEntryData lineChartEntryData ;
					if (itemMap.get(itemId) == null) {
						lineChartEntryData = new LineChartEntryData();
						Sku item = (Sku)itemService.getById(itemId);
						lineChartEntryData.setText(item.getName());
						lineChartEntryData.setColor(colors[colorIndex ++] );
					}else {
						lineChartEntryData = (LineChartEntryData)itemMap.get(itemId) ;
					}
					lineChartEntryData.addToValueMap(Utils.dateToString(periodTo, "dd-MM-yyyy"), qty);
					itemMap.put(itemId, lineChartEntryData);
				}
				periodFrom.setTime(periodTo.getTime() );
				periodTo.setTime(periodFrom.getTime() + diff);
						
			}
		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		
		LineChartData.Range range = lineChartData.new Range();
		range.setyMax(maxValue);
		lineChartData.setRange(range);
		Iterator it = itemMap.keySet().iterator();
		while(it.hasNext()) {
			LineChartEntryData entry = itemMap.get(it.next());
			lineChartData.addEntry(entry);
			
		}
		trend.setChartData(lineChartData);
		return super.read(object);
	}

}
