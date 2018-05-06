package com.rainbow.crm.company.service;

import java.util.List;

import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.company.model.Company;
import com.techtrade.rads.framework.model.abstracts.ModelObject;

public interface ICompanyService  extends IBusinessService{
 
	 public Company findByCode(String code) ;
	 
	 public Company findByName(String name) ;
	 
	 public List<Company> findAllActiveCompanies() ;
	
	
}
