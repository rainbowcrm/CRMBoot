package com.rainbow.crm.common;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.territory.service.ITerritoryService;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.TransactionController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.utils.Utils;

public abstract class CRMTransactionController extends TransactionController {

	
	public abstract ITransactionService getService() ;

	@Override
	public List<RadsError> adaptfromUI(ModelObject modelObject) {
		return  getService().adaptfromUI((CRMContext)getContext(),modelObject);
	}

	@Override
	public List<RadsError> adapttoUI(ModelObject modelObject) {
		return  getService().adaptToUI((CRMContext)getContext(),modelObject);
	}

	public Map<String,String> getFiniteValues(String groupCode)
	{
		return GeneralSQLs.getFiniteValues(groupCode);

	}

	public Map<String,String> getFiniteValuesWithSelect(String groupCode)
	{
		return GeneralSQLs.getFiniteValuesWithSelect(groupCode);

	}

	@Override
	public List<RadsError> validateforCancel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void init(HttpServletRequest request) {
		Object obj  = request.getParameter("id") ;
		if (obj != null && Utils.isPositiveInt(String.valueOf(obj)))  {
			int id = Integer.parseInt(String.valueOf(obj));
			((CRMBusinessModelObject)object).setId(id);
		}
	}

	
	@Override
	public List<RadsError> validateforCreate() {
		return getService().validateforCreate((CRMModelObject)object, (CRMContext)getContext());
	}

	
	@Override
	public List<RadsError> validateforUpdate() {
		return getService().validateforUpdate((CRMModelObject)object, (CRMContext)getContext());
	}

	@Override
	public PageResult create() {
		PageResult  result = new PageResult(getService().create((CRMModelObject)object, (CRMContext)getContext()));
		result.setObject(object);
		return result ;
	}
	
	@Override
	public PageResult update() {
		return new PageResult(getService().update((CRMModelObject)object, (CRMContext)getContext()));
	}

	@Override
	public PageResult delete() {
		return null;
	}

	@Override
	public PageResult read() {
		PageResult result = new PageResult();
		ModelObject thisObject = getService().getByBusinessKey((CRMModelObject)object, (CRMContext)getContext());
		setObject(thisObject);
		result.setObject(thisObject);
		return result;
		
	}
	

	@Override
	public PageResult print() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelObject populateFullObjectfromPK(ModelObject objects) {
		object = (ModelObject) getService().getById(object.getPK());
		adapttoUI(object);
		return object;
	}

	@Override
	public IRadsContext generateContext(HttpServletRequest request,
			HttpServletResponse response, UIPage page) {
		
		return CommonUtil.generateContext(request,page);
	}
	
	@Override
	public IRadsContext generateContext(String authToken, UIPage page) {
		return CommonUtil.generateContext(authToken,page);
	}
	
	public String getCompanyName() {
		ICompanyService service = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company =(Company) service.getById(((CRMContext)getContext()).getLoggedinCompany());
		return company.getName();
	}
	
	public Map <String, String > getAllDivisions() {
		CRMContext ctx = ((CRMContext) getContext());
		boolean allowAll =CommonUtil.allowAllDivisionAccess(ctx);
		Map<String, String> ans = new LinkedHashMap<String, String>();
		IDivisionService service = (IDivisionService) SpringObjectFactory.INSTANCE
				.getInstance("IDivisionService");
		List<Division> divisions = service.getAllDivisions(ctx
				.getLoggedinCompany());
		if (!Utils.isNullList(divisions)) {
			for (Division division : divisions) {
				if (allowAll || division.getId() == ctx.getLoggedInUser().getDivision().getId())
					ans.put(String.valueOf(division.getId()), division.getName());
			}
		}
		return ans;
	}
	public Map <String, String > getAllDivisionsWithSelect() {
		CRMContext ctx = ((CRMContext) getContext());
		boolean allowAll =CommonUtil.allowAllDivisionAccess(ctx);
		Map<String, String> ans = new LinkedHashMap<String, String>();
		ans.put("-1", "--Select one--");
		IDivisionService service = (IDivisionService) SpringObjectFactory.INSTANCE
				.getInstance("IDivisionService");
		List<Division> divisions = service.getAllDivisions(ctx
				.getLoggedinCompany());
		if (!Utils.isNullList(divisions)) {
			for (Division division : divisions) {
				if (allowAll || division.getId() == ctx.getLoggedInUser().getDivision().getId())
					ans.put(String.valueOf(division.getId()), division.getName());
			}
		}
		return ans;
	}
	
	public Map <String, String > getAllTerritories() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		ITerritoryService service =(ITerritoryService) SpringObjectFactory.INSTANCE.getInstance("ITerritoryService");
		List<Territory> territorries = (List<Territory>)service.findAll("Territory", "", "territory", (CRMContext)getContext());
		ans.put("-1", "--Select one--");
		if (!Utils.isNullList(territorries)) {
			for (Territory territory : territorries) {
				ans.put(String.valueOf(territory.getId()), territory.getTerritory());
			}
		}
		return ans;
	}


}
