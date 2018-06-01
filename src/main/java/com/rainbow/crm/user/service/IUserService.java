package com.rainbow.crm.user.service;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;

public interface IUserService extends IBusinessService{

	 public User getByEmail(String email);
	 public User getByPhone(String phone);
	 
	 public List<User> getByDivision(Division division, CRMContext context);

	public List<RadsError> adaptToUI(ModelObject model, CRMContext context);
}
