package com.rainbow.crm.common;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.utils.Utils;

@Transactional(rollbackFor = Exception.class)
public abstract class AbstractionTransactionService extends AbstractService  implements ITransactionService{

	@Override
	public List<RadsError> adaptfromUI(CRMContext context, ModelObject object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> adaptToUI(CRMContext context, ModelObject object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionResult createFromScratch(CRMModelObject object,
			CRMContext context) {
		TransactionResult result = new TransactionResult();
		List<RadsError> errors =  adaptfromUI(context, object);
		if (Utils.isNullList(errors)) {
			errors = validateforCreate(object, context);
			if (Utils.isNullList(errors)) {
				TransactionResult  res = create(object, context);
				return res; 
			}else {
				result.setResult(TransactionResult.Result.FAILURE);
			}
		}else
		{
			result.setResult(TransactionResult.Result.FAILURE);
		}
		result.setErrors(errors);
		return result;
	}
}
