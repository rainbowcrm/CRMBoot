package com.rainbow.framework.query.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMGeneralController;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.framework.query.model.Query;
import com.rainbow.framework.query.model.QueryReport;
import com.rainbow.framework.query.service.IQueryService;
import com.rainbow.framework.setup.dao.DataSetupSQL;
import com.rainbow.framework.setup.model.Metadata;
import com.rainbow.framework.setup.sql.MetadataSQL;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.utils.Utils;

public class QueryController extends CRMGeneralController {

	private boolean resultFetched;
	@Override
	public PageResult submit(ModelObject object) {
		return new PageResult();
	}
	
	private IQueryService getService()
	{
		return 	(IQueryService)SpringObjectFactory.INSTANCE.getInstance("IQueryService") ;
	}

	
	
	public boolean getResultFetched()
	{
		return resultFetched;
	}
	
	@Override
	public PageResult submit(ModelObject object, String actionParam) {
		Query query = (Query)object;
		Company company = CommonUtil.getCompany(((CRMContext  )getContext()).getLoggedinCompany());
		query.setCompany(company);
		query.setOwner(((CRMContext  )getContext()).getLoggedInUser());
		IQueryService service= getService();
		if ("clear".equals(actionParam)) {
			query = new Query();
			PageResult result =  new PageResult();
			result.setObject(query);
			return result;
		}else if ("entitychanged".equals(actionParam) ) {
			PageResult result =  new PageResult();
			return result;
		}else if ("fetchQuery".equalsIgnoreCase(actionParam)) {
			int queryId = query.getId();
			query = service.getQuery(queryId);
			setObject(query);
			PageResult result =  new PageResult();
			result.setObject(query);
			return result;
		}else if ("runQuery".equalsIgnoreCase(actionParam) || "saveQuery".equalsIgnoreCase(actionParam)){
			
			List<RadsError> errors = service.validate(query, (CRMContext  )getContext());
			
			if(Utils.isNullList(errors)) {
				if ("runQuery".equalsIgnoreCase(actionParam)){
					QueryReport report =service.getResult(query, (CRMContext  )getContext());
					resultFetched = true;
					String reportData= service.getVelocityConverted(report,  (CRMContext  )getContext());
					query.setReportData(reportData);
				} else {
					if(query.getDivision() != null && query.getDivision().getId() < 0 )
						  query.setDivision(null);
					service.saveQuery(query, (CRMContext  )getContext());
				}
			}
			else {
				PageResult result = new PageResult();
				result.setErrors(errors);
				return result;
			}
				
		}
		return new PageResult();
	}




	public Map <String, String > getEvaluationPeriods() {
		Map<String, String> ans = GeneralSQLs.getFiniteValues(CRMConstants.FV_EVALDATE);
		return ans;
	}
	
	public Map <String, String > getAllEntities() {
		List<Metadata> metaDatas = MetadataSQL.getTransactionEntities();
		Map<String, String> ans = new LinkedHashMap<String, String>();
		ans.put("null", "---Select one---") ;
		metaDatas.forEach( metaData ->  { 
			ans.put(metaData.getObjectName(),metaData.getObjectName());
			
		} );
		return ans;
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
	
	public Map <String, String > getAllSavedQueries() {
		IQueryService service= getService();
		List<Query> queries = service.getAllQueriesforOwner((CRMContext)getContext());
		Map<String, String> ans = new LinkedHashMap<String, String>();
		ans.put("null", "---Select one---") ;
		queries.forEach(query ->  {
			ans.put(String.valueOf(query.getId()), query.getName());
		});
		return ans;
	}
	
	public Map <String, String > getAllAggregationTypes() {
		Map<String, String> ans = new HashMap<String, String>();
		ans.put("sum", "sum");
		ans.put("avg", "avg");
		ans.put("count", "count");
		ans.put("max", "max");
		ans.put("min", "min");
		return ans;
	}

	public Map <String, String > getOperators() {
		Map<String, String> ans = new HashMap<String, String>();
		ans.put("=", "=");
		ans.put("<=", "<=");
		ans.put("<", "<");
		ans.put(">=", ">=");
		ans.put(">", ">");
		ans.put("!=", "!=");
		
		return ans;
	}

	
	public Map <String, String > 	getAllEntityFields() {
		Map<String, String> ans = new HashMap<String, String>();
		if (getObject() != null)  {
			Query  query =(Query) getObject();
			String entity = query.getEntity() ;
			return MetadataSQL.getAllEntityFields(entity);
		}
		return ans;
	}
	
	public Map <String, String > 	getNumericEntityFields() {
		Map<String, String> ans = new HashMap<String, String>();
		if (getObject() != null)  {
			Query  query =(Query) getObject();
			String entity = query.getEntity() ;
			return MetadataSQL.getNumericEntityFields(entity);
		}
		return ans;
	}
}
