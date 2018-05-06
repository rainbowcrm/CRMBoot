package com.rainbow.crm.expensehead.service;

import java.util.List;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.expensehead.model.ExpenseHead;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.IBusinessService;
import com.techtrade.rads.framework.model.transaction.TransactionResult;

public interface IExpenseHeadService extends IBusinessService{
	
	public ExpenseHead getByName(int company, String name) ;
	
	public TransactionResult batchCreate(List<CRMModelObject> objects, CRMContext context) throws CRMDBException;

	public List<ExpenseHead>  getAllExpenseHeads(int company);
}
