package com.rainbow.crm.filter.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMContext;
import com.techtrade.rads.framework.controller.abstracts.ViewController;
import com.techtrade.rads.framework.filter.Filter;
import com.techtrade.rads.framework.filter.FilterNode;
import com.techtrade.rads.framework.utils.Utils;

public class CRMFilter extends CRMModelObject{
	int id;
	Integer companyId;
	String user;
	String name;
	String page;
	boolean isPublic;
	Set<CRMFilterDetails> details;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	public Set<CRMFilterDetails> getDetails() {
		return details;
	}
	public void addDetail (CRMFilterDetails detail) {
		if (details  == null )
			details = new HashSet  <CRMFilterDetails>();
		details.add(detail);
		
	}
	public void setDetails(Set<CRMFilterDetails> details) {
		this.details = details;
	}
	
	
	
	public static CRMFilter parseRadsFilter(ViewController viewController, Filter filter) {
		CRMFilter newFilter = new  CRMFilter();
		int detailCount = 0; 
		CRMContext ctx= (CRMContext)viewController.getContext() ;
		newFilter.setUser(ctx.getUser());
		newFilter.setCompanyId(ctx.getLoggedinCompany());
		newFilter.setPage(viewController.getClass().getName());
		if (filter != null && !Utils.isNullList(filter.getNodeList())) {
			for (FilterNode node : filter.getNodeList()) {
				CRMFilterDetails detail = new CRMFilterDetails();
				//detail.setId(++detailCount);
				String field = node.getField();
				String value = String.valueOf(node.getValue()) ;
				detail.setField(field);
				detail.setValue(value);
				detail.setParent(newFilter);
				if (field.equals("FilterName")) {
					newFilter.setName(value);
				}
				newFilter.addDetail(detail);
			}
		}
		return newFilter;
	}
	

}
