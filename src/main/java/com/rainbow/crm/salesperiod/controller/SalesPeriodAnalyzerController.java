package com.rainbow.crm.salesperiod.controller;

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
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.salesperiod.model.SalesPeriodAnalyzer;
import com.rainbow.crm.salesperiod.model.SalesPeriodAssociate;
import com.rainbow.crm.salesperiod.model.SalesPeriodBrand;
import com.rainbow.crm.salesperiod.model.SalesPeriodCategory;
import com.rainbow.crm.salesperiod.model.SalesPeriodLine;
import com.rainbow.crm.salesperiod.model.SalesPeriodProduct;
import com.rainbow.crm.salesperiod.model.SalesPeriodTerritory;
import com.rainbow.crm.salesperiod.service.ISalesPeriodService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.BarData;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class SalesPeriodAnalyzerController  extends CRMGeneralController{

	@Override
	public PageResult submit(ModelObject object) {
		// TODO Auto-generated method stub
		return new PageResult();
	}


	
	@Override
	public PageResult read(ModelObject object) {
		SalesPeriodAnalyzer analyzer = (SalesPeriodAnalyzer) object;
		int period = analyzer.getSalePeriod();
		if (period > 0) {
			ISalesPeriodService service = (ISalesPeriodService) SpringObjectFactory.INSTANCE
					.getInstance("ISalesPeriodService");
			ISalesService salesService = (ISalesService) SpringObjectFactory.INSTANCE
					.getInstance("ISalesService");
			SalesPeriod salesPeriod = (SalesPeriod) service.getById(period);
			BarChartData barChartData = new BarChartData();
			barChartData.setTitle("Sales Period Analyzer");
			if ("It".equalsIgnoreCase(analyzer.getBasedOn())) {
				Set<SalesPeriodLine> periodLines = salesPeriod
						.getSalesPeriodLines();
				int minY = 0, maxY = 0;
				barChartData.setSubTitle("Item Wise");
				for (SalesPeriodLine periodLine : periodLines) {
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
							periodLine.getItem(), salesPeriod.getFromDate(),
							salesPeriod.getToDate(), salesPeriod.getDivision());
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
			} else  if ("US".equalsIgnoreCase(analyzer.getBasedOn())) {
				Set<SalesPeriodAssociate> associates = salesPeriod.getSalesPeriodAssociates();
				barChartData.setSubTitle("Associate Wise");
				int minY = 0, maxY = 0;
				for (SalesPeriodAssociate periodLine : associates) {
					BarData barData = new BarData();
					barData.setText(periodLine.getUser().getFirstName() + " "+ periodLine.getUser().getLastName());
					if (periodLine.getLineTotal() > maxY) {
						maxY = (int)periodLine.getLineTotal();
					}
					barData.setValue(periodLine.getLineTotal());
					barData.setColor("green");
					barData.setTextColor("blue");
					barData.setLegend("Target");
					BarChartData.Division divis = barChartData.new Division();
					divis.addBarData(barData);
					divis.setDivisionTitle(periodLine.getUser().getFirstName() + " "+ periodLine.getUser().getLastName());

					BarData actualSales = new BarData();
					//actualSales.setText(periodLine.getUser().getFirstName() + " "+ periodLine.getUser().getLastName());
					int soldQty = salesService.getSalesManSaleQuantity(
							periodLine.getUser(), salesPeriod.getFromDate(),
							salesPeriod.getToDate(), salesPeriod.getDivision());
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
			}else  if ("TR".equalsIgnoreCase(analyzer.getBasedOn())) {
				Set<SalesPeriodTerritory> territories = salesPeriod.getSalesPeriodTerritories();
				barChartData.setSubTitle("Territory Wise");
				int minY = 0, maxY = 0;
				for (SalesPeriodTerritory periodLine : territories) {
					BarData barData = new BarData();
					barData.setText(periodLine.getTerritory().getTerritory());
					if (periodLine.getLineTotal() > maxY) {
						maxY = (int)periodLine.getLineTotal();
					}
					barData.setValue(periodLine.getLineTotal());
					barData.setColor("green");
					barData.setTextColor("blue");
					barData.setLegend("Target");
					BarChartData.Division divis = barChartData.new Division();
					divis.addBarData(barData);
					divis.setDivisionTitle(periodLine.getTerritory().getTerritory());

					BarData actualSales = new BarData();
					//actualSales.setText(periodLine.getTerritory().getTerritory());
					int soldQty = salesService.getTerritorySaleQuantity(
							periodLine.getTerritory().getId(), salesPeriod.getFromDate(),
							salesPeriod.getToDate(), salesPeriod.getDivision());
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
				Set<SalesPeriodBrand> brands = salesPeriod.getSalesPeriodBrands();
				barChartData.setSubTitle("Brand Wise");
				int minY = 0, maxY = 0;
				for (SalesPeriodBrand periodLine : brands) {
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
							periodLine.getBrand().getId(), salesPeriod.getFromDate(),
							salesPeriod.getToDate(), salesPeriod.getDivision());
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
				Set<SalesPeriodCategory> categories = salesPeriod.getSalesPeriodCategories();
				barChartData.setSubTitle("Category Wise");
				int minY = 0, maxY = 0;
				for (SalesPeriodCategory periodLine : categories) {
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
							periodLine.getCategory().getId(), salesPeriod.getFromDate(),
							salesPeriod.getToDate(), salesPeriod.getDivision());
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
				Set<SalesPeriodProduct> products = salesPeriod.getSalesPeriodProducts();
				barChartData.setSubTitle("Product Wise");
				int minY = 0, maxY = 0;
				for (SalesPeriodProduct periodLine : products) {
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
							periodLine.getProduct().getId(), salesPeriod.getFromDate(),
							salesPeriod.getToDate(), salesPeriod.getDivision());
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
		ISalesPeriodService service =  (ISalesPeriodService)SpringObjectFactory.INSTANCE.getInstance("ISalesPeriodService") ;
		List<SalesPeriod> periods = (List<SalesPeriod>)service.listData(0, 1000, null, (CRMContext)getContext(),null);
		Map<String, String> ans = new HashMap<String, String>();
		if(!Utils.isNullList(periods)) {
			for (SalesPeriod period : periods) {
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
