package com.rainbow.crm.carrier.controller;



import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMCRUDController;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.carrier.service.ICarrierService;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class CarrierController extends CRMCRUDController{
	
	public IBusinessService getService() {
		ICarrierService serv = (ICarrierService) SpringObjectFactory.INSTANCE.getInstance("ICarrierService");
		return serv;
	}

	@Override
	public PageResult create() {
		try {
			 PageResult  result = new PageResult(getService().create((CRMModelObject)object, (CRMContext)getContext()));
			 result.setObject(object);
			 setReadMode();
			 return result;
		} catch(CRMDBException ex) {
			return new PageResult(Result.FAILURE,ex.getErrors(),object);
		}
	}

}
