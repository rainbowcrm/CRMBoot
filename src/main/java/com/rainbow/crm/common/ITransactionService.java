package com.rainbow.crm.common;

import java.util.List;

import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;

public interface ITransactionService extends IBusinessService {
	
	public List<RadsError> adaptfromUI(CRMContext context,ModelObject object) ;
	
	public List<RadsError> adaptToUI(CRMContext context,ModelObject object) ;

}
