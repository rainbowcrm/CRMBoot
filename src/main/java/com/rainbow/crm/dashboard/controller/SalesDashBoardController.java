package com.rainbow.crm.dashboard.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.dashboard.model.SalesDashBoard;
import com.rainbow.crm.dashboard.service.IDashBoardService;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.graphdata.BarChartData;
import com.techtrade.rads.framework.model.graphdata.LineChartData;
import com.techtrade.rads.framework.model.graphdata.PieChartData;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

public class SalesDashBoardController extends CRMGeneralController{
	
	String graphId;
	
	static final String TARGET_ANALYSIS = "targetanalysis" ;
	static final String PORTFOLIO_SPLITS = "portfoliosplits" ;
	static final String SALES_HISTORY = "saleshistory" ;
	static final String LEAD_SPLITS = "leadSplits" ;

	static final String DIV_TARGET_ANALYSIS = "divtargetanalysis" ;
	static final String ADMIN_TARGET_ANALYSIS = "admintargetanalysis" ;
	static final String DIV_ASSOC_SALES_SPLITS = "divassocsalessplits" ;
	static final String ADMIN_ASSOC_SALES_SPLITS = "adminassocsalessplits" ;
	static final String DIV_PROD_SALES_SPLITS = "divprodsalessplits" ;
	static final String DIV_MGR_SALELEADSPLITS="divmgrsalesleadsplits";
	static final String ADMIN_MGR_SALELEADSPLITS="adminmgrsalesleadsplits";
	static final String DIV_SALES_HISTORY="divsaleshistory";
	static final String ADMIN_SALES_HISTORY="adminsaleshistory";
	
	public Map<String,String> getAllSplitUpOptions()
	{
		SalesDashBoard dashBoard = (SalesDashBoard) object;
		Map <String, String> ans = new HashMap<String,String>();
		ans.put("ASSOCIATE", "Associate");
		ans.put("TERRITORY", "Territory");
		ans.put("PRODUCT", "Product");
		ans.put("ITEM", "Item");
		ans.put("CATEGORY", "Category");
		ans.put("BRAND", "Brand");
		CRMContext ctx= (CRMContext)getContext();
		if( ctx.getPageAccessCode() != null && ctx.getPageAccessCode().contains("ADMIN") )
			ans.put("DIVISION", "Division") ;
		
		return ans;
	/*	<option key ="ASSOCIATE"> Associate</option>
		  <option key ="TERRITORY"> Territory</option>
		  <option key ="PRODUCT"> Product</option>
		  <option key ="ITEM"> Item</option>
		  <option key ="CATEGORY"> Category</option>
		  <option key ="BRAND"> Brand</option>*/
	}
	
	@Override
	public PageResult submit(ModelObject object) {
		SalesDashBoard dashBoard = (SalesDashBoard) object;
		IDashBoardService service = (IDashBoardService) SpringObjectFactory.INSTANCE.getInstance("IDashBoardService");
		if(TARGET_ANALYSIS.equalsIgnoreCase(graphId))  {
		BarChartData barChartData = service.setSalesTargetData(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
		dashBoard.setSalesTargetData(barChartData);
		}
		
		if(DIV_TARGET_ANALYSIS.equalsIgnoreCase(graphId))  {
			String classification = dashBoard.getClassification() ;
			BarChartData barChartData = service.setDivisionSalesTargetData(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(),
					(CRMContext)getContext(),classification,false);
			dashBoard.setDivManagersalesTargetData(barChartData);
		}
		if(ADMIN_TARGET_ANALYSIS.equalsIgnoreCase(graphId))  {
			String classification = dashBoard.getClassification() ;
			BarChartData barChartData = service.setDivisionSalesTargetData(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(),
					(CRMContext)getContext(),classification,true);
			dashBoard.setDivManagersalesTargetData(barChartData);
		}
		
		if(PORTFOLIO_SPLITS.equalsIgnoreCase(graphId))  {
		PieChartData pieChartData  = service.getPortfolioSplits(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
		dashBoard.setPortfolioSplits(pieChartData);
		}
		if (LEAD_SPLITS.equalsIgnoreCase(graphId)) {
			PieChartData pieChartData  = service.getLeadSplits(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
			dashBoard.setLeadSplits(pieChartData);
		}
		
		if(DIV_MGR_SALELEADSPLITS.equalsIgnoreCase(graphId)) {
			PieChartData pieChartData  = service.getDivisionLeadSplits(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext(),false);
			dashBoard.setDivManagersalesleadSplits(pieChartData);
		}
		
		if(ADMIN_MGR_SALELEADSPLITS.equalsIgnoreCase(graphId)) {
			PieChartData pieChartData  = service.getDivisionLeadSplits(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext(),true);
			dashBoard.setDivManagersalesleadSplits(pieChartData);
		}
		
		if(DIV_ASSOC_SALES_SPLITS.equalsIgnoreCase(graphId))  {
			String type = dashBoard.getSalespiecriteria() ;
			if(Utils.isNullString(type)  || "ASSOCIATE".equals(type)) {
				PieChartData pieChartData  = service.getAssociateSplits(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext(),false);
				dashBoard.setDivManagerSalesAssociateSplits(pieChartData);
			} else if("TERRITORY".equals(type)) {
				PieChartData pieChartData  = service.getTerritorySplits(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext(),false);
				dashBoard.setDivManagerSalesAssociateSplits(pieChartData);
			}else if( "PRODUCT".equals(type)) {
				PieChartData pieChartData  = service.getProductwiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext(),false);
				dashBoard.setDivManagerSalesAssociateSplits(pieChartData);
			} else if("ITEM".equals(type)) {
				PieChartData pieChartData  = service.getItemwiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext(),false);
				dashBoard.setDivManagerSalesAssociateSplits(pieChartData);
			} else if("CATEGORY".equals(type)) {
				PieChartData pieChartData  = service.getCategorywiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext(),false);
				dashBoard.setDivManagerSalesAssociateSplits(pieChartData);
			}else if("BRAND".equals(type)) {
				PieChartData pieChartData  = service.getBrandwiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext(),false);
				dashBoard.setDivManagerSalesAssociateSplits(pieChartData);
			}
		}
		
		if(ADMIN_ASSOC_SALES_SPLITS.equalsIgnoreCase(graphId))  {
			String type = dashBoard.getSalespiecriteria() ;
			if(Utils.isNullString(type)  || "ASSOCIATE".equals(type)) {
				PieChartData pieChartData  = service.getAssociateSplits(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext(),true);
				dashBoard.setDivManagerSalesAssociateSplits(pieChartData);
			} else if("TERRITORY".equals(type)) {
				PieChartData pieChartData  = service.getTerritorySplits(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext(),true);
				dashBoard.setDivManagerSalesAssociateSplits(pieChartData);
			}else if( "PRODUCT".equals(type)) {
				PieChartData pieChartData  = service.getProductwiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext(),true);
				dashBoard.setDivManagerSalesAssociateSplits(pieChartData);
			} else if("ITEM".equals(type)) {
				PieChartData pieChartData  = service.getItemwiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext(),true);
				dashBoard.setDivManagerSalesAssociateSplits(pieChartData);
			} else if("CATEGORY".equals(type)) {
				PieChartData pieChartData  = service.getCategorywiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext(),true);
				dashBoard.setDivManagerSalesAssociateSplits(pieChartData);
			}else if("BRAND".equals(type)) {
				PieChartData pieChartData  = service.getBrandwiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext(),true);
				dashBoard.setDivManagerSalesAssociateSplits(pieChartData);
			}else if("DIVISION".equals(type)) {
				PieChartData pieChartData  = service.getDivisionwiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext(),true);
				dashBoard.setDivManagerSalesAssociateSplits(pieChartData);
			}
		}
		
		if(DIV_PROD_SALES_SPLITS.equalsIgnoreCase(graphId))  {
			String type = dashBoard.getSalespiecriteria() ; 
			if(Utils.isNullString(type)  || "PRODUCT".equals(type)) {
				PieChartData pieChartData  = service.getProductwiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext(),false);
				dashBoard.setDivManagerSaleProductsSplits(pieChartData);
			} else if("ITEM".equals(type)) {
				PieChartData pieChartData  = service.getItemwiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext(),false);
				dashBoard.setDivManagerSaleProductsSplits(pieChartData);
			} else if("CATEGORY".equals(type)) {
				PieChartData pieChartData  = service.getCategorywiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext(),false);
				dashBoard.setDivManagerSaleProductsSplits(pieChartData);
			}else if("BRAND".equals(type)) {
				PieChartData pieChartData  = service.getBrandwiseSales(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext(),false);
				dashBoard.setDivManagerSaleProductsSplits(pieChartData);
			}
		}
		
		if(SALES_HISTORY.equalsIgnoreCase(graphId))  {
		LineChartData lineChartData = service.getSalesHistory(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
		dashBoard.setSalesHistory(lineChartData);
		}
		
		if (DIV_SALES_HISTORY.equalsIgnoreCase(graphId))  {
		LineChartData lineChartData = service.getDivSalesHistory(((CRMContext)getContext()).getLoggedInUser(), new java.util.Date(), (CRMContext)getContext());
		dashBoard.setDivSalesHistory(lineChartData);
		}
		if (ADMIN_SALES_HISTORY.equalsIgnoreCase(graphId))  {
			LineChartData lineChartData = service.getCorpSalesHistory( new java.util.Date(), (CRMContext)getContext());
			dashBoard.setDivSalesHistory(lineChartData);
			}
		
		return new PageResult();
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request,
			HttpServletResponse response,UIPage page) { 
		graphId= request.getParameter("graphId");
		return super.generateContext(request, response,page);
	}

	
	
}


