package com.rainbow.crm.corpsalesperiod.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.sales.service.ISalesService;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriod;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodAnalyzer;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodBrand;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodCategory;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodDivision;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodLine;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodProduct;
import com.rainbow.crm.corpsalesperiod.service.ICorpSalesPeriodService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.BarData;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class CorpSalesPeriodAnalyzerController  extends CRMGeneralController{

	@Override
	public PageResult submit(ModelObject object) {
		// TODO Auto-generated method stub
		return new PageResult();
	}


	
	@Override
	public PageResult read(ModelObject object) {
		CorpSalesPeriodAnalyzer analyzer = (CorpSalesPeriodAnalyzer) object;
		int period = analyzer.getSalePeriod();
		if (period > 0) {
			ICorpSalesPeriodService service = (ICorpSalesPeriodService) SpringObjectFactory.INSTANCE
					.getInstance("ICorpSalesPeriodService");
			ISalesService salesService = (ISalesService) SpringObjectFactory.INSTANCE
					.getInstance("ISalesService");
			CorpSalesPeriod corpSalesPeriod = (CorpSalesPeriod) service.getById(period);
			BarChartData barChartData = new BarChartData();
			barChartData.setTitle("Sales Period Analyzer");
			if ("It".equalsIgnoreCase(analyzer.getBasedOn())) {
				Set<CorpSalesPeriodLine> periodLines = corpSalesPeriod
						.getCorpSalesPeriodLines();
				int minY = 0, maxY = 0;
				barChartData.setSubTitle("Item Wise");
				for (CorpSalesPeriodLine periodLine : periodLines) {
					BarData barData = new BarData();
					barData.setText(periodLine.getItem().getName());
					if (periodLine.getQty() > maxY) {
						maxY = periodLine.getQty();
					}
					barData.setValue(periodLine.getQty());
					barData.setColor("green");
					barData.setTextColor("blue");
					barData.setLegend("Target");
					BarChartData.Division divis = barChartData.new Division();
					divis.addBarData(barData);
					divis.setDivisionTitle(periodLine.getItem().getName());

					BarData actualSales = new BarData();
					//actualSales.setText(periodLine.getItem().getName());
					int soldQty = salesService.getItemSaleQuantity(
							periodLine.getItem(), corpSalesPeriod.getFromDate(),
							corpSalesPeriod.getToDate(), null);
					actualSales.setValue(soldQty);
					if (soldQty > maxY) {
						maxY = soldQty;
					}
					actualSales.setColor("red");
					actualSales.setLegend("Actual");
					divis.addBarData(actualSales);

					barChartData.addDivision(divis);
				}
				BarChartData.Range range = barChartData.new Range();
				range.setyMax(maxY);
				range.setyMin(0);
				barChartData.setRange(range);
				analyzer.setSalesData(barChartData);
			}else  if ("BR".equalsIgnoreCase(analyzer.getBasedOn())) {
				Set<CorpSalesPeriodBrand> brands = corpSalesPeriod.getCorpSalesPeriodBrands();
				barChartData.setSubTitle("Brand Wise");
				int minY = 0, maxY = 0;
				for (CorpSalesPeriodBrand periodLine : brands) {
					BarData barData = new BarData();
					barData.setText(periodLine.getBrand().getName());
					if (periodLine.getLineTotal() > maxY) {
						maxY = (int)periodLine.getLineTotal();
					}
					barData.setValue(periodLine.getLineTotal());
					barData.setColor("green");
					barData.setTextColor("blue");
					barData.setLegend("Target");
					BarChartData.Division divis = barChartData.new Division();
					divis.addBarData(barData);
					divis.setDivisionTitle(periodLine.getBrand().getName());

					BarData actualSales = new BarData();
					//actualSales.setText(periodLine.getTerritory().getTerritory());
					int soldQty = salesService.getBrandSaleQuantity(
							periodLine.getBrand().getId(), corpSalesPeriod.getFromDate(),
							corpSalesPeriod.getToDate(), null);
					actualSales.setValue(soldQty);
					if (soldQty > maxY) {
						maxY = soldQty;
					}
					actualSales.setColor("red");
					actualSales.setLegend("Actual");
					divis.addBarData(actualSales);

					barChartData.addDivision(divis);
				}
				BarChartData.Range range = barChartData.new Range();
				range.setyMax(maxY);
				range.setyMin(0);
				barChartData.setRange(range);
				analyzer.setSalesData(barChartData);
			}else  if ("DIV".equalsIgnoreCase(analyzer.getBasedOn())) {
				Set<CorpSalesPeriodDivision> divisions = corpSalesPeriod.getCorpSalesPeriodDivisions();
				barChartData.setSubTitle("Division Wise");
				int minY = 0, maxY = 0;
				for (CorpSalesPeriodDivision periodLine : divisions) {
					BarData barData = new BarData();
					barData.setText(periodLine.getDivision().getName());
					if (periodLine.getLineTotal() > maxY) {
						maxY = (int)periodLine.getLineTotal();
					}
					barData.setValue(periodLine.getLineTotal());
					barData.setColor("green");
					barData.setTextColor("blue");
					barData.setLegend("Target");
					BarChartData.Division divis = barChartData.new Division();
					divis.addBarData(barData);
					divis.setDivisionTitle(periodLine.getDivision().getName());

					BarData actualSales = new BarData();
					//actualSales.setText(periodLine.getTerritory().getTerritory());
					int soldQty = salesService.getDivisionSaleQuantity(
							corpSalesPeriod.getFromDate(),
							corpSalesPeriod.getToDate(), periodLine.getDivision());
					actualSales.setValue(soldQty);
					if (soldQty > maxY) {
						maxY = soldQty;
					}
					actualSales.setColor("red");
					actualSales.setLegend("Actual");
					divis.addBarData(actualSales);

					barChartData.addDivision(divis);
				}
				BarChartData.Range range = barChartData.new Range();
				range.setyMax(maxY);
				range.setyMin(0);
				barChartData.setRange(range);
				analyzer.setSalesData(barChartData);
			}else  if ("CTG".equalsIgnoreCase(analyzer.getBasedOn())) {
				Set<CorpSalesPeriodCategory> categories = corpSalesPeriod.getCorpSalesPeriodCategories();
				barChartData.setSubTitle("Category Wise");
				int minY = 0, maxY = 0;
				for (CorpSalesPeriodCategory periodLine : categories) {
					BarData barData = new BarData();
					barData.setText(periodLine.getCategory().getName());
					if (periodLine.getLineTotal() > maxY) {
						maxY = (int)periodLine.getLineTotal();
					}
					barData.setValue(periodLine.getLineTotal());
					barData.setColor("green");
					barData.setTextColor("blue");
					barData.setLegend("Target");
					BarChartData.Division divis = barChartData.new Division();
					divis.addBarData(barData);
					divis.setDivisionTitle(periodLine.getCategory().getName());

					BarData actualSales = new BarData();
					//actualSales.setText(periodLine.getTerritory().getTerritory());
					int soldQty = salesService.getCategorySaleQuantity(
							periodLine.getCategory().getId(), corpSalesPeriod.getFromDate(),
							corpSalesPeriod.getToDate(), null);
					actualSales.setValue(soldQty);
					if (soldQty > maxY) {
						maxY = soldQty;
					}
					actualSales.setColor("red");
					actualSales.setLegend("Actual");
					divis.addBarData(actualSales);

					barChartData.addDivision(divis);
				}
				BarChartData.Range range = barChartData.new Range();
				range.setyMax(maxY);
				range.setyMin(0);
				barChartData.setRange(range);
				analyzer.setSalesData(barChartData);
			}else  if ("PRD".equalsIgnoreCase(analyzer.getBasedOn())) {
				Set<CorpSalesPeriodProduct> products = corpSalesPeriod.getCorpSalesPeriodProducts();
				barChartData.setSubTitle("Product Wise");
				int minY = 0, maxY = 0;
				for (CorpSalesPeriodProduct periodLine : products) {
					BarData barData = new BarData();
					barData.setText(periodLine.getProduct().getName());
					if (periodLine.getLineTotal() > maxY) {
						maxY = (int)periodLine.getLineTotal();
					}
					barData.setValue(periodLine.getLineTotal());
					barData.setColor("green");
					barData.setTextColor("blue");
					barData.setLegend("Target");
					BarChartData.Division divis = barChartData.new Division();
					divis.addBarData(barData);
					divis.setDivisionTitle(periodLine.getProduct().getName());

					BarData actualSales = new BarData();
					//actualSales.setText(periodLine.getTerritory().getTerritory());
					int soldQty = salesService.getProductSaleQuantity(
							periodLine.getProduct().getId(), corpSalesPeriod.getFromDate(),
							corpSalesPeriod.getToDate(), null);
					actualSales.setValue(soldQty);
					if (soldQty > maxY) {
						maxY = soldQty;
					}
					actualSales.setColor("red");
					actualSales.setLegend("Actual");
					divis.addBarData(actualSales);

					barChartData.addDivision(divis);
				}
				BarChartData.Range range = barChartData.new Range();
				range.setyMax(maxY);
				range.setyMin(0);
				barChartData.setRange(range);
				analyzer.setSalesData(barChartData);
			}
		}

		return new PageResult();
	}
	
	public Map <String, String > getItemClassTypes() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_ITEMCLASS_TYPE);
		return ans;
	}
	public Map<String, String >  getSalePeriods() {
		ICorpSalesPeriodService service =  (ICorpSalesPeriodService)SpringObjectFactory.INSTANCE.getInstance("ICorpSalesPeriodService") ;
		List<CorpSalesPeriod> periods = (List<CorpSalesPeriod>)service.listData(0, 1000, null, (CRMContext)getContext(),null);
		Map<String, String> ans = new HashMap<String, String>();
		if(!Utils.isNullList(periods)) {
			for (CorpSalesPeriod period : periods) {
				ans.put(String.valueOf(period.getId()),period.getPeriod());
			}
			return ans;
		}
		return null;
	}
	
	public String getCompanyName() {
		ICompanyService service = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company =(Company) service.getById(((CRMContext)getContext()).getLoggedinCompany());
		return company.getName();
	}

}
