package com.rainbow.crm.expensevoucher.validator;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.expensevoucher.model.ExpenseVoucher;
import com.rainbow.crm.expensevoucher.model.ExpenseVoucherLine;
import com.rainbow.crm.expensevoucher.service.IExpenseVoucherService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public class ExpenseVoucherValidator extends CRMValidator {

	ExpenseVoucher expenseVoucher ;
	 
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		IExpenseVoucherService service =(IExpenseVoucherService)SpringObjectFactory.INSTANCE.getInstance("IExpenseVoucherService");
		ExpenseVoucher exist = (ExpenseVoucher)service.getByBusinessKey(expenseVoucher, context);
		if(exist != null) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Doc_Number"))) ;
		}
		
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IExpenseVoucherService service =(IExpenseVoucherService)SpringObjectFactory.INSTANCE.getInstance("IExpenseVoucherService");
		ExpenseVoucher exist = (ExpenseVoucher)service.getByBusinessKey(expenseVoucher, context);
		if(exist != null && exist.getId() != expenseVoucher.getId()) {
				errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Doc_Number"))) ;
		}
		
	}
	protected void checkforErrors(ModelObject object) {
		expenseVoucher = (ExpenseVoucher) object ;
		if (expenseVoucher.getCompany() == null){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Company"))) ;
		}
		if(Utils.isNull(expenseVoucher.getExpenseDate()) ){
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Date"))) ;
		}
		if (Utils.isNullSet(expenseVoucher.getExpenseVoucherLines())) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Line_Items"))) ;
		}else {
			for (ExpenseVoucherLine line : expenseVoucher.getExpenseVoucherLines()) {
				if (line.getExpenseHead() == null ) {
					errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Expense_Head"))) ;
				}else if (line.getExpenseHead().isDeleted() ) {
					errors.add(getErrorforCode(CommonErrorCodes.OBJECT_DELETED,externalize.externalize(context, "Expense_Head") + line.getExpenseHead().getCode())) ;
				}else if (line.getRequestedAmount() <=0 ) {
					errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,externalize.externalize(context, "Amount") + line.getExpenseHead().getCode(),"0") ) ;
				}
			}
		}
		FiniteValue status = expenseVoucher.getStatus();
		if(status != null && CRMConstants.EXP_VOUCHER_STATUS.APPROVED.equals(status.getCode()) || CRMConstants.EXP_VOUCHER_STATUS.REJECTED.equals(status.getCode()) ||
				CRMConstants.EXP_VOUCHER_STATUS.CLOSED.equals(status.getCode()) ||  CRMConstants.EXP_VOUCHER_STATUS.REJECTEDCLOSED.equals(status.getCode())){
		
			errors.add(getErrorforCode(ExpenseVoucherErrorCodes.INVALID_STATUS,externalize.externalize(context, status.getDescription())));
		}
		
	}
	
	public ExpenseVoucherValidator(CRMContext context) {
		super(context);
	}
	public ExpenseVoucherValidator(){
		
	}

	
}
