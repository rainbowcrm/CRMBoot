package com.rainbow.crm.dashboard.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriod;
import com.rainbow.crm.corpsalesperiod.service.ICorpSalesPeriodService;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.sales.service.ISalesService;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.salesperiod.model.SalesPeriodAssociate;
import com.rainbow.crm.salesperiod.service.ISalesPeriodService;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.BarData;
import com.techtrade.rads.framework.model.graphdata.LineChartData;
import com.techtrade.rads.framework.model.graphdata.LineChartEntryData;
import com.techtrade.rads.framework.model.graphdata.PieChartData;
import com.techtrade.rads.framework.model.graphdata.BarChartData.Division;
import com.techtrade.rads.framework.model.graphdata.LineChartData.Range;
import com.techtrade.rads.framework.model.graphdata.PieSliceData;
import com.techtrade.rads.framework.utils.Utils;

public class DashBoardService  implements IDashBoardService{

	private SalesPeriod getSalesPeriodforUser(User associate, Date date,CRMContext context)
	{
		ISalesPeriodService salesPeriodService = (ISalesPeriodService)SpringObjectFactory.INSTANCE.getInstance("ISalesPeriodService");
		return salesPeriodService.getSalesPeriodforAssociate(associate.getUserId(), date);

	}

	private SalesPeriod getActiveSalesPeriodforManager(User associate, Date date,CRMContext context)
	{
		ISalesPeriodService salesPeriodService = (ISalesPeriodService)SpringObjectFactory.INSTANCE.getInstance("ISalesPeriodService");
		if(associate.getDivision() != null)
			return salesPeriodService.getActiveSalesPeriodforDivision(associate.getDivision().getId(), date);
		else
			return salesPeriodService.getActiveSalesPeriodforDivision(CommonUtil.getDefaultDivision(context).getId(), date);
	}
	
	private CorpSalesPeriod getActiveCorpSalesPeriodforManager(User associate, Date date,CRMContext context)
	{
		ICorpSalesPeriodService salesPeriodService = (ICorpSalesPeriodService)SpringObjectFactory.INSTANCE.getInstance("ICorpSalesPeriodService");
		return salesPeriodService.getActiveCorpSalesPeriod(date);
	}
	
	
	private SalesPeriodAssociate getAssociatePeriod(SalesPeriod currentPeriod, User associate)
	{
		for (SalesPeriodAssociate selectedAssociate  : currentPeriod.getSalesPeriodAssociates()) {
			if (selectedAssociate.getUser().getUserId().equals(associate.getUserId())) {
				return selectedAssociate;
			}
		}
		
		return null;
		
	}
	
	
	
	
	@Override
	public PieChartData getSaleLeadSplitsByReason(
			com.rainbow.crm.division.model.Division division, Date fromDate,
			Date toDate, CRMContext context, FiniteValue orientation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BarChartData getSalesLeadPotentials(
			com.rainbow.crm.division.model.Division division, Date fromDate,
			Date toDate, CRMContext context) {
		int divisionId = (division==null)?-1:division.getId();
		BarChartData barChartData = new BarChartData();
		barChartData.setTitle("Total vs Won vs Lost  vs Open");
		barChartData.setSubTitle(" ");

		Double totalPotential  = DashBoardSQLs.getTotalforAllLeads(division.getId(),new java.sql.Date(fromDate.getTime()),new java.sql.Date(toDate.getTime()) , context.getLoggedinCompany());
		BarData tagetBarData = new BarData();
		BarChartData.Division targetDivis = barChartData.new Division();
		tagetBarData.setText("Total");
		tagetBarData.setLegend("Total");
		tagetBarData.setValue(totalPotential);
		tagetBarData.setColor(CommonUtil.getGraphColors()[0]);
		targetDivis.addBarData(tagetBarData);
		barChartData.addDivision(targetDivis);
		
		BarChartData.Division winDivision = barChartData.new Division();
		String [] winStatuses = { "CLSD"};
		Double soldAmount  = DashBoardSQLs.getTotalforAllLeadsByStatus(division.getId(),new java.sql.Date(fromDate.getTime()),new java.sql.Date(toDate.getTime()) ,
				winStatuses, false, context.getLoggedinCompany());
		BarData actualBarData = new BarData();
		actualBarData.setText("Won");
		actualBarData.setLegend("Won");
		actualBarData.setValue(soldAmount);
		actualBarData.setColor(CommonUtil.getGraphColors()[1]);
		winDivision.addBarData(actualBarData);
		barChartData.addDivision(winDivision);

		BarChartData.Division failedDivision = barChartData.new Division();
		String [] failStatuses = { "FLD"};
		Double failedAmountt  = DashBoardSQLs.getTotalforAllLeadsByStatus(division.getId(),new java.sql.Date(fromDate.getTime()),new java.sql.Date(toDate.getTime()) ,
				failStatuses, false, context.getLoggedinCompany());
		BarData failBarData = new BarData();
		failBarData.setText("Failed");
		failBarData.setLegend("Failed");
		failBarData.setValue(failedAmountt);
		failBarData.setColor(CommonUtil.getGraphColors()[2]);
		failedDivision.addBarData(failBarData);
		barChartData.addDivision(failedDivision);
		
		BarChartData.Division openedDivision = barChartData.new Division();
		String [] openStatuses = { "INIT","ASSGND","INPRG","NGTD"};
		Double openAmt  = DashBoardSQLs.getTotalforAllLeadsByStatus(division.getId(),new java.sql.Date(fromDate.getTime()),new java.sql.Date(toDate.getTime()) ,
				openStatuses, true, context.getLoggedinCompany());
		BarData openBarData = new BarData();
		openBarData.setText("Open");
		openBarData.setLegend("Open");
		openBarData.setValue(openAmt);
		openBarData.setColor(CommonUtil.getGraphColors()[1]);
		openedDivision.addBarData(openBarData);
		barChartData.addDivision(openedDivision);
				
		BarChartData.Range range =  barChartData.new  Range();
		range.setyMax(totalPotential.intValue());
		range.setyMin(0);
		range.setxMin(0);
		range.setxMax(100);
		barChartData.setRange(range);
		return barChartData;
		
		
	}

	@Override
	public BarChartData setSalesTargetData(User associate, Date date,
			CRMContext context) {
		
		BarChartData barChartData = new BarChartData();
		barChartData.setTitle("Target vs Actual");
		barChartData.setSubTitle(" ");
		SalesPeriod currentPeriod = getSalesPeriodforUser(associate, date, context);
		if(currentPeriod == null)
			return null;
		SalesPeriodAssociate salesPerAssociate = getAssociatePeriod(currentPeriod,associate);
		Double target  = salesPerAssociate.getLineTotal() ;
		ISalesService salesService =  (ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		int soldQty = salesService.getSalesManSaleQuantity(
				associate, currentPeriod.getFromDate(),
				currentPeriod.getToDate(), currentPeriod.getDivision());
		BarData tagetBarData = new BarData();
		
		BarChartData.Division targetDivis = barChartData.new Division();
		tagetBarData.setText("Target");
		tagetBarData.setLegend("Target");
		tagetBarData.setValue(target);
		tagetBarData.setColor(CommonUtil.getGraphColors()[0]);
		targetDivis.addBarData(tagetBarData);
		
		BarData actualBarData = new BarData();
		actualBarData.setText("Actual");
		actualBarData.setLegend("Actual");
		actualBarData.setValue(soldQty);
		actualBarData.setColor(CommonUtil.getGraphColors()[1]);
		targetDivis.addBarData(actualBarData);
		
		targetDivis.setDivisionTitle("My Sales Figures");
		barChartData.addDivision(targetDivis);

		int totalSoldQty = salesService.getTotalSaleQuantity(currentPeriod.getFromDate(),
				currentPeriod.getToDate(), currentPeriod.getDivision());
		
		int noAssociates  =getNoAssociates(associate, date, context) ;
		
		BarChartData.Division avgDataDivis = barChartData.new Division();
		BarData tagetAvgBarData = new BarData();
		tagetAvgBarData.setText("Target");
		tagetAvgBarData.setLegend("Target");
		tagetAvgBarData.setValue(currentPeriod.getTotalTarget()/noAssociates);
		tagetAvgBarData.setColor(CommonUtil.getGraphColors()[0]);
		avgDataDivis.addBarData(tagetAvgBarData);
		
		BarData actualavgBarData = new BarData();
		actualavgBarData.setText("Actual");
		actualavgBarData.setLegend("Actual");
		actualavgBarData.setValue(totalSoldQty/noAssociates);
		actualavgBarData.setColor(CommonUtil.getGraphColors()[1]);
		avgDataDivis.addBarData(actualavgBarData);
		
		avgDataDivis.setDivisionTitle("Avg Sales Figures");
		barChartData.addDivision(avgDataDivis);
		
		BarChartData.Range range =  barChartData.new  Range();
		range.setyMax( (int)((target>soldQty)?target:soldQty));
		range.setyMin(0);
		range.setxMin(0);
		range.setxMax(100);
		barChartData.setRange(range);
		return barChartData;
	}

	
	
	
	
	
	@Override
	public LineChartData getCorpSalesHistory(
			 Date date,
			CRMContext context) {
		LineChartData lineChartData = new LineChartData();
		long points = 3;
		int maxValue = 0;
	try { 
		
		lineChartData.setBorderColor(CommonUtil.getGraphColors()[1] );
		lineChartData.setTitle("Sales Progression");
		lineChartData.setSubTitle("Sales History");
		int ct = 1;
		IDivisionService divisionService =  (IDivisionService)SpringObjectFactory.INSTANCE.getInstance("IDivisionService");
		List<com.rainbow.crm.division.model.Division> allDivisions = divisionService.getAllDivisions(context.getLoggedinCompany());
		for (com.rainbow.crm.division.model.Division division : allDivisions) {
			LineChartEntryData lineChartEntryData  = new LineChartEntryData();
			lineChartEntryData.setColor(CommonUtil.getGraphColors()[ct ++] );
			lineChartEntryData.setText(division.getName());
			for (long i = points;  i >0 ; i--) {
				Date startDate = new Date(date.getTime() -  (7 * i * 24l * 3600l * 1000l  ));
				Date endDate = new Date(date.getTime() -  (7 * (i-1) * 24l * 3600l * 1000l  ));
				Double saleQty = DashBoardSQLs.getDivisionTotalSale(division.getId(), new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
				if (i == points){
					lineChartData.setStartingPoint(Utils.dateToString(startDate, "dd-MM-yyyy"));
				}
				if(ct ==2 )
					lineChartData.addInterval(Utils.dateToString(endDate, "dd-MM-yyyy"));
				lineChartEntryData.addToValueMap(Utils.dateToString(endDate, "dd-MM-yyyy"), saleQty);
				if (saleQty > maxValue )
					maxValue = new Double(saleQty).intValue();
						
			}
			lineChartData.addEntry(lineChartEntryData);
		}
		LineChartData.Range range = lineChartData.new Range();
		range.setyMax(maxValue);
		lineChartData.setRange(range);
	}catch(Exception ex) {
		Logwriter.INSTANCE.error(ex);
	}

	return lineChartData;

	}

	@Override
	public LineChartData getDivSalesHistory(User manager, Date date,
			CRMContext context) {
		LineChartData lineChartData = new LineChartData();
		long points = 3;
		int maxValue = 0;
	try { 
		
		lineChartData.setBorderColor(CommonUtil.getGraphColors()[1] );
		lineChartData.setTitle("Sales Progression");
		lineChartData.setSubTitle("Sales History");
		int ct = 1;
		IUserService userService =  (IUserService)SpringObjectFactory.INSTANCE.getInstance("IUserService");
		List<User> allAssociates = userService.getByDivision(manager.getDivision(), context);
		for (User associate : allAssociates) {
			LineChartEntryData lineChartEntryData  = new LineChartEntryData();
			lineChartEntryData.setColor(CommonUtil.getGraphColors()[ct ++] );
			lineChartEntryData.setText(associate.getUserId());
			for (long i = points;  i >0 ; i--) {
				Date startDate = new Date(date.getTime() -  (7 * i * 24l * 3600l * 1000l  ));
				Date endDate = new Date(date.getTime() -  (7 * (i-1) * 24l * 3600l * 1000l  ));
				Double saleQty = DashBoardSQLs.getPeriodTotalSale(associate.getUserId(), new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
				if (i == points){
					lineChartData.setStartingPoint(Utils.dateToString(startDate, "dd-MM-yyyy"));
				}
				if(ct ==2 )
					lineChartData.addInterval(Utils.dateToString(endDate, "dd-MM-yyyy"));
				lineChartEntryData.addToValueMap(Utils.dateToString(endDate, "dd-MM-yyyy"), saleQty);
				if (saleQty > maxValue )
					maxValue = new Double(saleQty).intValue();
						
			}
			lineChartData.addEntry(lineChartEntryData);
		}
		LineChartData.Range range = lineChartData.new Range();
		range.setyMax(maxValue);
		lineChartData.setRange(range);
	}catch(Exception ex) {
		Logwriter.INSTANCE.error(ex);
	}

	return lineChartData;
 }

	@Override
	public LineChartData getSalesHistory(User associate, Date date,
			CRMContext context) {
		
		LineChartData lineChartData = new LineChartData();
		long points = 3;
		int maxValue = 0;
	try { 
		LineChartEntryData lineChartEntryData  = new LineChartEntryData();
		lineChartData.setBorderColor(CommonUtil.getGraphColors()[1] );
		lineChartData.setTitle("Sales Progression");
		lineChartData.setSubTitle("Sales History");
		lineChartEntryData.setColor(CommonUtil.getGraphColors()[1] );
		lineChartEntryData.setText("My Sale");
		for (long i = points;  i >0 ; i--) {
			Date startDate = new Date(date.getTime() -  (7 * i * 24l * 3600l * 1000l  ));
			Date endDate = new Date(date.getTime() -  (7 * (i-1) * 24l * 3600l * 1000l  ));
			Double saleQty = DashBoardSQLs.getPeriodTotalSale(associate.getUserId(), new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
			if (i == points){
				lineChartData.setStartingPoint(Utils.dateToString(startDate, "dd-MM-yyyy"));
			}
			lineChartData.addInterval(Utils.dateToString(endDate, "dd-MM-yyyy"));
			lineChartEntryData.addToValueMap(Utils.dateToString(endDate, "dd-MM-yyyy"), saleQty);
			if (saleQty > maxValue )
				maxValue = new Double(saleQty).intValue();
					
		}
		lineChartData.addEntry(lineChartEntryData);
		
		LineChartEntryData lineChartavgEntry  = new LineChartEntryData();
		lineChartavgEntry.setColor(CommonUtil.getGraphColors()[4] );
		lineChartavgEntry.setText("Avg Division Sale");
		for (long i = points;  i >0 ; i--) {
			Date startDate = new Date(date.getTime() -  (7 * i * 24l * 3600l * 1000l  ));
			Date endDate = new Date(date.getTime() -  (7 * (i-1) * 24l * 3600l * 1000l  ));
			SalesPeriod currentPeriod = getSalesPeriodforUser(associate, endDate, context);
			if (currentPeriod == null)
				return null;
			Double saleQty = DashBoardSQLs.getSaleAllMade(currentPeriod.getDivision().getId(), new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
			int noAssociates  =getNoAssociates(associate, endDate, context) ;
			lineChartavgEntry.addToValueMap(Utils.dateToString(endDate, "dd-MM-yyyy"), saleQty/noAssociates);
			if (saleQty > maxValue )
				maxValue = new Double(saleQty).intValue();
					
		}
		lineChartData.addEntry(lineChartavgEntry);
		
		LineChartData.Range range = lineChartData.new Range();
		range.setyMax(maxValue);
		lineChartData.setRange(range);
	}catch(Exception ex) {
		Logwriter.INSTANCE.error(ex);
	}
		
		return lineChartData;
	}
	
	
	
	private int getNoAssociates (User associate, Date endDate, CRMContext context) 
	{
		SalesPeriod currentPeriod = getSalesPeriodforUser(associate, endDate, context);
		ISalesPeriodService service = (ISalesPeriodService)SpringObjectFactory.INSTANCE.getInstance("ISalesPeriodService");
		currentPeriod =(SalesPeriod) service.getById(currentPeriod.getPK());
		int noAssociates = 1;
		if (!Utils.isNullSet(currentPeriod.getSalesPeriodAssociates()))
			noAssociates = currentPeriod.getSalesPeriodAssociates().size();
		return noAssociates; 
	}
	
	

	

	@Override
	public PieChartData getProductwiseSales(User manager, Date date,
			CRMContext context, boolean corproateAdmin) {
		PieChartData pieChartData  = new PieChartData();
		int divisionId=  -1 ;
		java.sql.Date fromDate = new java.sql.Date(new java.util.Date().getTime()) ;
		java.sql.Date toDate = new java.sql.Date(new java.util.Date().getTime());
		if (! corproateAdmin ) {
			SalesPeriod currentPeriod = getActiveSalesPeriodforManager(manager,date,context);
			if (currentPeriod == null )
				return null;
			
			divisionId = currentPeriod.getDivision().getId();
			fromDate = new java.sql.Date(currentPeriod.getFromDate().getTime());
			toDate  = new java.sql.Date(currentPeriod.getToDate().getTime());
		}else {
			CorpSalesPeriod currentPeriod = getActiveCorpSalesPeriodforManager(manager, toDate, context);
			if (currentPeriod == null )
				return null;
			
			fromDate = new java.sql.Date(currentPeriod.getFromDate().getTime());
			toDate = new java.sql.Date(currentPeriod.getToDate().getTime());
		}
		Map <String , Double > results = DashBoardSQLs.getProductWiseSale(divisionId, fromDate,
				toDate,context.getLoggedinCompany()) ;
		AtomicInteger index = new AtomicInteger(0);
		results.forEach(  (item, qty) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(qty);
			pieSliceData.setText(item);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Sale by Product");
		pieChartData.setTitle("Sale by Product");
		return pieChartData;

	}
	
		

	@Override
	public PieChartData getItemwiseSales(User manager, Date date,
			CRMContext context,boolean corporateAdmin) {
		 PieChartData pieChartData  = new PieChartData();
		 int divisionId=  -1 ;
			java.sql.Date fromDate = new java.sql.Date(new java.util.Date().getTime()) ;
			java.sql.Date toDate = new java.sql.Date(new java.util.Date().getTime());
			if (! corporateAdmin ) {
				SalesPeriod currentPeriod = getActiveSalesPeriodforManager(manager,date,context);
				if (currentPeriod == null )
					return null;
				
				divisionId = currentPeriod.getDivision().getId();
				fromDate = new java.sql.Date(currentPeriod.getFromDate().getTime());
				toDate  = new java.sql.Date(currentPeriod.getToDate().getTime());
			}else {
				CorpSalesPeriod currentPeriod = getActiveCorpSalesPeriodforManager(manager, toDate, context);
				if (currentPeriod == null )
					return null;
				
				fromDate = new java.sql.Date(currentPeriod.getFromDate().getTime());
				toDate = new java.sql.Date(currentPeriod.getToDate().getTime());
			}
		Map <String , Double > results = DashBoardSQLs.getItemWiseSale(divisionId, fromDate,
				toDate,context.getLoggedinCompany()) ;
		AtomicInteger index = new AtomicInteger(0);
		results.forEach(  (item, qty) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(qty);
			pieSliceData.setText(item);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Sale by Item");
		pieChartData.setTitle("Sale by Item");
		return pieChartData;
	}

	
	
	
	
	
	
	@Override
	public PieChartData getDivisionwiseSales(User manager, Date date,
			CRMContext context, boolean corporateAdmin) {
		PieChartData pieChartData  = new PieChartData();
		java.sql.Date fromDate = new java.sql.Date(new java.util.Date().getTime()) ;
		java.sql.Date toDate = new java.sql.Date(new java.util.Date().getTime());
		CorpSalesPeriod currentPeriod = getActiveCorpSalesPeriodforManager(manager, toDate, context);
		if (currentPeriod == null )
			return null;
		
		fromDate = new java.sql.Date(currentPeriod.getFromDate().getTime());
		toDate = new java.sql.Date(currentPeriod.getToDate().getTime());
		
		Map <String , Double > results = DashBoardSQLs.getDivisionWiseSale(  fromDate,
				toDate,context.getLoggedinCompany());
				
		AtomicInteger index = new AtomicInteger(0);
		results.forEach(  (item, qty) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(qty);
			pieSliceData.setText(item);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Sale by Brand");
		pieChartData.setTitle("Sale by Brand");
		return pieChartData;
	}

	@Override
	public PieChartData getBrandwiseSales(User manager, Date date,
			CRMContext context ,boolean corporateAdmin ) {
		PieChartData pieChartData  = new PieChartData();
		int divisionId=  -1 ;
		java.sql.Date fromDate = new java.sql.Date(new java.util.Date().getTime()) ;
		java.sql.Date toDate = new java.sql.Date(new java.util.Date().getTime());
		if (! corporateAdmin ) {
			SalesPeriod currentPeriod = getActiveSalesPeriodforManager(manager,date,context);
			if (currentPeriod == null )
				return null;
			
			divisionId = currentPeriod.getDivision().getId();
			fromDate = new java.sql.Date(currentPeriod.getFromDate().getTime());
			toDate  = new java.sql.Date(currentPeriod.getToDate().getTime());
		}else {
			CorpSalesPeriod currentPeriod = getActiveCorpSalesPeriodforManager(manager, toDate, context);
			if (currentPeriod == null )
				return null;
			
			fromDate = new java.sql.Date(currentPeriod.getFromDate().getTime());
			toDate = new java.sql.Date(currentPeriod.getToDate().getTime());
		}
		Map <String , Double > results = DashBoardSQLs.getBrandWiseSale(divisionId,  fromDate,
				toDate,context.getLoggedinCompany());
				
		AtomicInteger index = new AtomicInteger(0);
		results.forEach(  (item, qty) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(qty);
			pieSliceData.setText(item);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Sale by Brand");
		pieChartData.setTitle("Sale by Brand");
		return pieChartData;
	}
	
	@Override
	public PieChartData getCategorywiseSales(User manager, Date date,
			CRMContext context,boolean corporateAdmin) {
		PieChartData pieChartData  = new PieChartData();
		int divisionId=  -1 ;
		java.sql.Date fromDate = new java.sql.Date(new java.util.Date().getTime()) ;
		java.sql.Date toDate = new java.sql.Date(new java.util.Date().getTime());
		if (! corporateAdmin ) {
			SalesPeriod currentPeriod = getActiveSalesPeriodforManager(manager,date,context);
			if (currentPeriod == null )
				return null;
			
			divisionId = currentPeriod.getDivision().getId();
			fromDate = new java.sql.Date(currentPeriod.getFromDate().getTime());
			toDate  = new java.sql.Date(currentPeriod.getToDate().getTime());
		}else {
			CorpSalesPeriod currentPeriod = getActiveCorpSalesPeriodforManager(manager, toDate, context);
			if (currentPeriod == null )
				return null;
			
			fromDate = new java.sql.Date(currentPeriod.getFromDate().getTime());
			toDate = new java.sql.Date(currentPeriod.getToDate().getTime());
		}
		Map <String , Double > results = DashBoardSQLs.getCategoryWiseSale(divisionId,  fromDate,
				toDate,context.getLoggedinCompany()) ;
		AtomicInteger index = new AtomicInteger(0);
		results.forEach(  (item, qty) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(qty);
			pieSliceData.setText(item);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Sale by Category");
		pieChartData.setTitle("Sale by Category");
		return pieChartData;
	}

	
	@Override
	public PieChartData getLeadSplitsByStatus(
			com.rainbow.crm.division.model.Division division, Date fromDate,
			Date toDate, CRMContext context) {
		PieChartData pieChartData  = new PieChartData();
		Map <String , Double > results = DashBoardSQLs.getStatusWiseSaleLeadsforDivision(division.getId(),  new java.sql.Date( fromDate.getTime()),
				new java.sql.Date( toDate.getTime()), context.getLoggedinCompany()) ;
		AtomicInteger index = new AtomicInteger(0);
		results.forEach(  (status, count) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(count);
			pieSliceData.setText(status);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Sales Lead by Status");
		pieChartData.setTitle("Sales Lead by Status");
		return pieChartData;
	}
	
	
	
	
	@Override
	public PieChartData getLeadSplitsByReason(
			com.rainbow.crm.division.model.Division division, Date fromDate,
			Date toDate, CRMContext context) {
		PieChartData pieChartData  = new PieChartData();
		Map <String , Double > results = DashBoardSQLs.getReasonWiseSaleLeadsforDivision(division.getId(),  new java.sql.Date( fromDate.getTime()),
				new java.sql.Date( toDate.getTime()), context.getLoggedinCompany()) ;
		AtomicInteger index = new AtomicInteger(0);
		results.forEach(  (status, count) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(count);
			pieSliceData.setText(status);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Sales Lead by Reason");
		pieChartData.setTitle("Sales Lead by Reason");
		return pieChartData;
	}

	@Override
	public PieChartData getDivisionLeadSplits(User manager, Date date,
			CRMContext context, boolean corporateAdmin) {
		PieChartData pieChartData  = new PieChartData();
		int divisionId=  -1 ;
		java.sql.Date fromDate = new java.sql.Date(new java.util.Date().getTime()) ;
		java.sql.Date toDate = new java.sql.Date(new java.util.Date().getTime());
		if (! corporateAdmin ) {
			SalesPeriod currentPeriod = getActiveSalesPeriodforManager(manager,date,context);
			if (currentPeriod == null )
				return null;
			
			divisionId = currentPeriod.getDivision().getId();
			fromDate = new java.sql.Date(currentPeriod.getFromDate().getTime());
			toDate  = new java.sql.Date(currentPeriod.getToDate().getTime());
		}else {
			CorpSalesPeriod currentPeriod = getActiveCorpSalesPeriodforManager(manager, toDate, context);
			if (currentPeriod == null )
				return null;
			
			fromDate = new java.sql.Date(currentPeriod.getFromDate().getTime());
			toDate = new java.sql.Date(currentPeriod.getToDate().getTime());
		}
		Map <String , Double > results = DashBoardSQLs.getStatusWiseSaleLeadsforDivision(divisionId, fromDate,
				toDate, context.getLoggedinCompany()) ;
		AtomicInteger index = new AtomicInteger(0);
		results.forEach(  (status, count) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(count);
			pieSliceData.setText(status);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Sales Lead by Status");
		pieChartData.setTitle("Sales Lead by Status");
		return pieChartData;
	}

	
	
	

	@Override
	public PieChartData getLeadSplits(User associate, Date date,
			CRMContext context) {
		PieChartData pieChartData  = new PieChartData();
		SalesPeriod currentPeriod = getSalesPeriodforUser(associate, date, context);
		if(currentPeriod == null) return null;
		Map <String , Double > results = DashBoardSQLs.getStatusWiseSaleLeads(currentPeriod.getDivision().getId(), associate.getUserId(), new java.sql.Date( currentPeriod.getFromDate().getTime()),
				new java.sql.Date( currentPeriod.getToDate().getTime())) ;
		AtomicInteger index = new AtomicInteger(0);
		results.forEach(  (status, count) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(count);
			pieSliceData.setText(status);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Sales Lead by Status");
		pieChartData.setTitle("Sales Lead by Status");
		return pieChartData;
	}

	@Override
	public PieChartData getPortfolioSplits(User associate, Date date,
			CRMContext context) {
		PieChartData pieChartData  = new PieChartData();
		SalesPeriod currentPeriod = getSalesPeriodforUser(associate, date, context);
		if(currentPeriod == null) return null;
		Map <String , Double > results = DashBoardSQLs.getItemWiseSale(currentPeriod.getDivision().getId(), associate.getUserId(), new java.sql.Date( currentPeriod.getFromDate().getTime()),
				new java.sql.Date( currentPeriod.getToDate().getTime())) ;
		AtomicInteger index = new AtomicInteger(0);
		results.forEach(  (item, qty) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(qty);
			pieSliceData.setText(item);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Sale by Item");
		pieChartData.setTitle("Sale by Item");
		return pieChartData;
				
	}

	
	
	@Override
	public PieChartData getTerritorySplits(User manager, Date date,
			CRMContext context, boolean corproateAdmin) {
		int divisionId=  -1 ;
		java.sql.Date fromDate = new java.sql.Date(new java.util.Date().getTime()) ;
		java.sql.Date toDate = new java.sql.Date(new java.util.Date().getTime());
		if (! corproateAdmin ) {
			SalesPeriod currentPeriod = getActiveSalesPeriodforManager(manager,date,context);
			if (currentPeriod == null )
				return null;
			
			divisionId = currentPeriod.getDivision().getId();
			fromDate = new java.sql.Date(currentPeriod.getFromDate().getTime());
			toDate  = new java.sql.Date(currentPeriod.getToDate().getTime());
		}else {
			CorpSalesPeriod currentPeriod = getActiveCorpSalesPeriodforManager(manager, toDate, context);
			if (currentPeriod == null )
				return null;
			
			fromDate = new java.sql.Date(currentPeriod.getFromDate().getTime());
			toDate = new java.sql.Date(currentPeriod.getToDate().getTime());
		}
		
		Map<String , Double> splits = DashBoardSQLs.getSaleMadeByTerritory(divisionId, 
				fromDate, toDate,context.getLoggedinCompany());
		PieChartData pieChartData = new PieChartData();
		AtomicInteger index = new AtomicInteger(0);
		splits.forEach(  (item, qty) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(qty);
			pieSliceData.setText(item);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Sale by Territory");
		pieChartData.setTitle("Sale by Territory");
		return pieChartData;

	}

	
	
	
	@Override
	public PieChartData getAssociateSalesLeadSplits(
			com.rainbow.crm.division.model.Division division, Date fromDate,
			Date toDate, CRMContext context) {
		Map<String , Double> splits;
		if(division != null && division.getId() > -1)
		splits = DashBoardSQLs.getAssociateWiseSaleLeads(division.getId(), 
				new java.sql.Date(fromDate.getTime()), new java.sql.Date(toDate.getTime()));
		else
			splits = DashBoardSQLs.getAssociateWiseSaleLeadsWithoutDivision( context.getLoggedinCompany(),
					new java.sql.Date(fromDate.getTime()), new java.sql.Date(toDate.getTime()));
		PieChartData pieChartData = new PieChartData();
		AtomicInteger index = new AtomicInteger(0);
		splits.forEach(  (item, qty) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(qty);
			pieSliceData.setText(item);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Leads by Associate");
		pieChartData.setTitle("Leads by Associate");
		return pieChartData;


	}

	@Override
	public PieChartData getAssociateExpenseSplits(
			com.rainbow.crm.division.model.Division division, Date fromDate,
			Date toDate, CRMContext context) {
		Map<String , Double> splits;
		if(division != null && division.getId() > -1)
		splits = DashBoardSQLs.getExpenseMadeByAssociate(division.getId(), 
				new java.sql.Date(fromDate.getTime()), new java.sql.Date(toDate.getTime()));
		else
			splits = DashBoardSQLs.getExpenseMadeByAssociateWithoutDivision( 
					new java.sql.Date(fromDate.getTime()), new java.sql.Date(toDate.getTime()),context.getLoggedinCompany());
		PieChartData pieChartData = new PieChartData();
		AtomicInteger index = new AtomicInteger(0);
		splits.forEach(  (item, qty) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(qty);
			pieSliceData.setText(item);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Expense by Associate");
		pieChartData.setTitle("Expense by Associate");
		return pieChartData;

	}

	@Override
	public PieChartData getAssociateSaleSplits(
			com.rainbow.crm.division.model.Division division, Date fromDate,
			Date toDate, CRMContext context) {
		Map<String , Double> splits;
		if(division != null && division.getId() > -1)
		splits = DashBoardSQLs.getSaleMadeByAssociate(division.getId(), 
				new java.sql.Date(fromDate.getTime()), new java.sql.Date(toDate.getTime()),context.getLoggedinCompany());
		else
			splits = DashBoardSQLs.getSaleMadeByAssociateWithoutDivision( 
					new java.sql.Date(fromDate.getTime()), new java.sql.Date(toDate.getTime()),context.getLoggedinCompany());
		PieChartData pieChartData = new PieChartData();
		AtomicInteger index = new AtomicInteger(0);
		splits.forEach(  (item, qty) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(qty);
			pieSliceData.setText(item);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Sale by Associate");
		pieChartData.setTitle("Sale by Associate");
		return pieChartData;

	}

	@Override
	public PieChartData getAssociateSplits(User manager, Date date,
			CRMContext context,boolean corporateAdmin) {
		int divisionId=  -1 ;
		java.sql.Date fromDate = new java.sql.Date(new java.util.Date().getTime()) ;
		java.sql.Date toDate = new java.sql.Date(new java.util.Date().getTime());
		if (! corporateAdmin ) {
			SalesPeriod currentPeriod = getActiveSalesPeriodforManager(manager,date,context);
			if (currentPeriod == null )
				return null;
			
			divisionId = currentPeriod.getDivision().getId();
			fromDate = new java.sql.Date(currentPeriod.getFromDate().getTime());
			toDate  = new java.sql.Date(currentPeriod.getToDate().getTime());
		}else {
			CorpSalesPeriod currentPeriod = getActiveCorpSalesPeriodforManager(manager, toDate, context);
			if (currentPeriod == null )
				return null;
			
			fromDate = new java.sql.Date(currentPeriod.getFromDate().getTime());
			toDate  = new java.sql.Date(currentPeriod.getToDate().getTime());
		}
			
		Map<String , Double> splits = DashBoardSQLs.getSaleMadeByAssociate(divisionId, 
				fromDate ,toDate,context.getLoggedinCompany() );
		PieChartData pieChartData = new PieChartData();
		AtomicInteger index = new AtomicInteger(0);
		splits.forEach(  (item, qty) -> {  
			PieSliceData pieSliceData  = new PieSliceData();
			pieSliceData.setVolume(qty);
			pieSliceData.setText(item);
			pieSliceData.setColor(CommonUtil.getGraphColors()[index.getAndIncrement()]);
			pieChartData.addPieSlice(pieSliceData);
		} );
		pieChartData.setFooterNote("Sale by Associate");
		pieChartData.setTitle("Sale by Associate");
		return pieChartData;
	}

	private BarChartData makeBarChartData (SalesPeriod currentPeriod,int totalSoldQty,String divisionTitle, String title )
	{
		BarChartData barChartData = new BarChartData();
		BarChartData.Division avgDataDivis = barChartData.new Division();
		BarData tagetAvgBarData = new BarData();
		tagetAvgBarData.setText("Target");
		tagetAvgBarData.setLegend("Target");
		tagetAvgBarData.setValue(currentPeriod.getTotalTarget());
		tagetAvgBarData.setColor(CommonUtil.getGraphColors()[0]);
		avgDataDivis.addBarData(tagetAvgBarData);
		
		BarData actualavgBarData = new BarData();
		actualavgBarData.setText("Actual");
		actualavgBarData.setLegend("Actual");
		actualavgBarData.setValue(totalSoldQty);
		actualavgBarData.setColor(CommonUtil.getGraphColors()[1]);
		avgDataDivis.addBarData(actualavgBarData);
		
		avgDataDivis.setDivisionTitle(divisionTitle);
		barChartData.addDivision(avgDataDivis);
		barChartData.setTitle(title);
		barChartData.setSubTitle(currentPeriod.getPeriod());
		BarChartData.Range range =  barChartData.new  Range();
		range.setyMax( (int)((currentPeriod.getTotalTarget()>totalSoldQty)?currentPeriod.getTotalTarget():totalSoldQty));
		range.setyMin(0);
		range.setxMin(0);
		range.setxMax(100);
		barChartData.setRange(range);
		return barChartData;
		
	}
	
	private BarChartData makeBarChartData (CorpSalesPeriod currentPeriod,int totalSoldQty,String divisionTitle, String title )
	{
		BarChartData barChartData = new BarChartData();
		BarChartData.Division avgDataDivis = barChartData.new Division();
		BarData tagetAvgBarData = new BarData();
		tagetAvgBarData.setText("Target");
		tagetAvgBarData.setLegend("Target");
		tagetAvgBarData.setValue(currentPeriod.getTotalTarget());
		tagetAvgBarData.setColor(CommonUtil.getGraphColors()[0]);
		avgDataDivis.addBarData(tagetAvgBarData);
		
		BarData actualavgBarData = new BarData();
		actualavgBarData.setText("Actual");
		actualavgBarData.setLegend("Actual");
		actualavgBarData.setValue(totalSoldQty);
		actualavgBarData.setColor(CommonUtil.getGraphColors()[1]);
		avgDataDivis.addBarData(actualavgBarData);
		
		avgDataDivis.setDivisionTitle(divisionTitle);
		barChartData.addDivision(avgDataDivis);
		barChartData.setTitle(title);
		barChartData.setSubTitle(currentPeriod.getPeriod());
		BarChartData.Range range =  barChartData.new  Range();
		range.setyMax( (int)((currentPeriod.getTotalTarget()>totalSoldQty)?currentPeriod.getTotalTarget():totalSoldQty));
		range.setyMin(0);
		range.setxMin(0);
		range.setxMax(100);
		barChartData.setRange(range);
		return barChartData;
		
	}
	
	
	
	@Override
	public BarChartData setDivisionSalesTargetData(User manager, Date date,
			CRMContext context, String classification, boolean  corporateAdmin) {
		SalesPeriod currentPeriod = null;
		CorpSalesPeriod currPeriod = null;
		int divisionId=  -1 ;
		java.sql.Date fromDate = new java.sql.Date(new java.util.Date().getTime()) ;
		java.sql.Date toDate = new java.sql.Date(new java.util.Date().getTime());
		if (! corporateAdmin ) {
			currentPeriod = getActiveSalesPeriodforManager(manager,date,context);
			if (currentPeriod == null )
				return null;
			
			divisionId = currentPeriod.getDivision().getId();
			fromDate = new java.sql.Date(currentPeriod.getFromDate().getTime());
			toDate  = new java.sql.Date(currentPeriod.getToDate().getTime());
		}else {
			currPeriod = getActiveCorpSalesPeriodforManager(manager, toDate, context);
			if (currPeriod == null )
				return null;
			
			fromDate = new java.sql.Date(currPeriod.getFromDate().getTime());
			toDate = new java.sql.Date(currPeriod.getToDate().getTime());
		}
		ISalesService salesService =  (ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		if (Utils.isNullString(classification) || "TOTAL".equalsIgnoreCase(classification) ) {
			com.rainbow.crm.division.model.Division periodDivision = new com.rainbow.crm.division.model.Division();
			periodDivision.setId(divisionId);
			
		int totalSoldQty = salesService.getTotalSaleQuantity(fromDate,
				toDate, periodDivision);
		if (! corporateAdmin )
			return makeBarChartData(currentPeriod, totalSoldQty, "Total Sales Figures" ,"Sales Target state" );
		else
			return makeBarChartData(currPeriod, totalSoldQty, "Total Sales Figures" ,"Sales Target state" );
		//int noAssociates  =getNoAssociates(associate, date, context) ;
		
		}else {
			if ("".equalsIgnoreCase(classification)) {
				
				//salesService.getCategorySaleQuantity(categoryId,currentPeriod.getFromDate(),currentPeriod.getToDate(), currentPeriod.getDivision());
				
			}
			return setDivisionSalesTargetData(manager, date, context, null,corporateAdmin);
		}
			
		
	}

	
	
	
}
