package com.rainbow.crm.user.service;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.user.model.User;

public interface IUserService extends IBusinessService{

	 public User getByEmail(String email);
	 public User getByPhone(String phone);
	 
	 public List<User> getByDivision(Division division, CRMContext context);
}
