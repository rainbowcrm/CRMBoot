package com.rainbow.crm.alert.service;

import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.alert.model.Alert;
import com.techtrade.rads.framework.model.abstracts.RadsError;

public interface IAlertService extends IBusinessService{

	public List<RadsError> acknowledgeAlert(Alert alert,CRMContext context);  

	public List<RadsError> pushAlertNotifictions(Alert alert,CRMContext context);
}
