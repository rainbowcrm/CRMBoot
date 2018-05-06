package com.rainbow.crm.expensehead.validator;

import com.rainbow.crm.expensehead.model.ExpenseHead;
import com.rainbow.crm.expensehead.service.IExpenseHeadService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class ExpenseHeadValidator extends CRMValidator {

	ExpenseHead  expensehead ;
	
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		IExpenseHeadService service =(IExpenseHeadService)SpringObjectFactory.INSTANCE.getInstance("IExpenseHeadService");
		ExpenseHead exist = (ExpenseHead)service.getByName(expensehead.getCompany().getId(), expensehead.getName());
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "ExpenseHead_Name"))) ;
		}
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IExpenseHeadService service =(IExpenseHeadService)SpringObjectFactory.INSTANCE.getInstance("IExpenseHeadService");
		ExpenseHead exist = (ExpenseHead)service.getByName(expensehead.getCompany().getId(), expensehead.getName());
		if(exist != null && exist.getId() != expensehead.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "ExpenseHead_Name"))) ;
		}
	}
	
	protected void checkforErrors(ModelObject object) {
		expensehead =  (ExpenseHead) object;
		if (expensehead.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if (expensehead.getName() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Expensehead_Name"))) ;
		}
	}

	public ExpenseHeadValidator(CRMContext context) {
		super(context);
	}
	public ExpenseHeadValidator(){
		
	}
	
}
