package com.rainbow.crm.expensevoucher.service;

import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.expensevoucher.model.ExpenseVoucher;
import com.techtrade.rads.framework.model.abstracts.RadsError;

public interface IExpenseVoucherService extends ITransactionService{

	public List<RadsError> approve(CRMContext context ,ExpenseVoucher voucher);
	
	public List<RadsError> counter(CRMContext context ,ExpenseVoucher voucher);
	
	public List<RadsError> reject(CRMContext context ,ExpenseVoucher voucher);
	
	public List<RadsError> hold(CRMContext context ,ExpenseVoucher voucher);
	
		

}
