package com.rainbow.crm.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.LoginSQLs;
import com.rainbow.crm.filter.dao.CRMFilterDAO;
import com.rainbow.crm.filter.model.CRMFilter;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.DataSheetController;
import com.techtrade.rads.framework.filter.Filter;
import com.techtrade.rads.framework.filter.FilterNode;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public abstract class CRMDataSheetController extends DataSheetController{

	protected final int recordsPerPage  = 12;
	public abstract IBusinessService getService();
	public abstract Object getPrimaryKeyValue(ModelObject object) ;
	
	protected String getFilter(Filter filterData ) {
		StringBuffer whereCondition = new  StringBuffer();
		if (filterData!=null  && !Utils.isNullList(filterData.getNodeList()) ) {
			for (FilterNode node : filterData.getNodeList()) {
				if (!Utils.isNullString(String.valueOf(node.getValue())) && !"FilterName".equals(node.getField())) {
					if (whereCondition.length() < 1)
						whereCondition.append( " where  "  + Utils.initlower(node.getField())  + getOperator(node) +  "'" +  node.getValue() + "'");
					else
						whereCondition.append( " and  "  + Utils.initlower(node.getField())  +  getOperator(node) + "'" +  node.getValue() + "'");
				}
			}
		}
		return whereCondition.toString();
	}
	
	
	@Override
	public long getTotalNumberofRecords(Filter filter) {
		long totalRecords = getService().getTotalRecordCount((CRMContext) getContext(),getFilter(filter));
		return totalRecords;
	}
	
	private static String getOperator(FilterNode node) {
		if (node.getOperater() == null || node.getOperater() == FilterNode.Operator.EQUALS) {
			return "=";
		} else if (node.getOperater() == FilterNode.Operator.GREATER_THAN_EQUAL) {
			return ">=";
		}else if (node.getOperater() == FilterNode.Operator.GREATER_THAN) {
			return ">";
		}else if (node.getOperater() == FilterNode.Operator.LESS_THAN_EQUAL) {
			return "<=";
		}else if (node.getOperater() == FilterNode.Operator.LESS_THAN) {
			return "<";
		}
		return "=";
	}
	
	@Override
	public void saveFilter(Filter filter) {
          CRMFilter crmFilter= CRMFilter.parseRadsFilter(this, filter);
          CRMFilter existing = CRMFilterDAO.INSTANCE.findByKey(crmFilter.getUser(),crmFilter.getPage()  ,crmFilter.getName());
          if (existing == null)
        	  CRMFilterDAO.INSTANCE.create(crmFilter);
          else{
        	 // crmFilter.setId(existing.getId());
        	  existing.setDetails(crmFilter.getDetails());
        	  CRMFilterDAO.INSTANCE.update(existing);
          }
	}
	
	public Map <String, String > getSavedFiters() {
		Map<String, String> ans = new LinkedHashMap<String, String> ();
		ans.put("null", "---Select one---") ;
		ans.putAll(CRMFilterDAO.INSTANCE.findAllByPage(this.getContext().getUser(), this.getClass().getName()));
		
		return ans;
	}
	
	public int getTotalNumberofPages(Filter filter) {
		long totalRecords = getService().getTotalRecordCount((CRMContext)getContext(),getFilter(filter));
		int rem = (int)totalRecords % recordsPerPage ;
		if (rem > 0 ) 
			return (int)( totalRecords /recordsPerPage ) + 1;
		else 
			return(int)( totalRecords /recordsPerPage );
	}
	
	@Override
	public List<ModelObject> getData(int pageNumber, Filter filter,SortCriteria sortCriteria) {
		int from  = (pageNumber-1)*recordsPerPage ;
		IBusinessService serv = (IBusinessService)getService();
		return (List)serv.listData(from,  from + recordsPerPage, getFilter(filter),(CRMContext)getContext(),sortCriteria);
		
	}
	
	@Override
	public IRadsContext generateContext(HttpServletRequest request,HttpServletResponse response,UIPage page) {
		return CommonUtil.generateContext(request,page);
	}
	
	
	
	@Override
	public IRadsContext generateContext(String authToken,UIPage page) {
		return CommonUtil.generateContext(authToken,page);
	}
	
	@Override
	public PageResult delete(List<ModelObject> objects) {
		for (ModelObject object : objects) {
			((CRMModelObject)object).setDeleted(true);
		}
		IBusinessService serv = (IBusinessService)getService();
		try {
			return new PageResult( serv.batchUpdate((List)objects, (CRMContext)getContext()));
		}catch(CRMDBException ex) {
			Logwriter.INSTANCE.error(ex);
			return new PageResult( TransactionResult.Result.FAILURE, ex.getErrors(),null);
		}
		
	}
	@Override
	public List<ModelObject> populateFullObjectfromPK(List<ModelObject> objects) {
		IBusinessService serv = getService();
		List<ModelObject> fullObjects = new ArrayList<ModelObject>();
		if (!Utils.isNullList(objects)) {
			for (ModelObject obj : objects) {
				 ModelObject fullObj = (ModelObject)serv.getById(getPrimaryKeyValue(obj));
				 fullObjects.add(fullObj);
			}
		}
		return fullObjects;
	}
	
	@Override
	public PageResult update() {
		IBusinessService service = getService();
		try {
			return new PageResult( service.batchUpdate((List)objects, (CRMContext)getContext()));
		}catch(CRMDBException ex) {
			Logwriter.INSTANCE.error(ex);
			return new PageResult( TransactionResult.Result.FAILURE, ex.getErrors(),null);
		}
	}
	
	public String getCompanyName() {
		ICompanyService service = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company =(Company) service.getById(((CRMContext)getContext()).getLoggedinCompany());
		return company.getName();
	}
	@Override
	public PageResult print(List<ModelObject> objects) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
