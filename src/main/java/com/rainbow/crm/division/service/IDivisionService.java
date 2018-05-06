package com.rainbow.crm.division.service;

import java.util.List;

import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.division.model.Division;

public interface IDivisionService extends IBusinessService{
	
	public Division getByCode(int company, String code) ;
	public Division getByName(int company, String name) ;
	
	public List<Division> getAllDivisions (int company) ;

	public Division getDefaultDivision(int company) ;
	
	
	
}
