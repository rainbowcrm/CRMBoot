package com.rainbow.crm.enquiry.service;

import java.util.Date;
import java.util.List;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.common.ITransactionService;
import com.rainbow.crm.enquiry.model.Enquiry;
import com.techtrade.rads.framework.model.transaction.TransactionResult;

public interface IEnquiryService extends ITransactionService{

	public TransactionResult generateLead (Enquiry enquiry ,CRMContext context);
}
