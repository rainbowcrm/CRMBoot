package com.rainbow.crm.reasoncode.service;

import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.techtrade.rads.framework.model.transaction.TransactionResult;

public interface IReasonCodeService extends IBusinessService{
	
	public ReasonCode getByName(int company, String name) ;
	
	
	public TransactionResult batchCreate(List<CRMModelObject> objects, CRMContext context) throws CRMDBException;

	public List<ReasonCode>  getAllReasonsforType(FiniteValue value, CRMContext context);
	
	public List<ReasonCode>  getAllReasonsforTypeAndOrientation(FiniteValue value, CRMContext context, FiniteValue orientation);
}
