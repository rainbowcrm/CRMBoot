package com.rainbow.crm.expensevoucher.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.vendor.model.Vendor;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class ExpenseVoucher extends CRMBusinessModelObject{

	Division division;
	String docNumber;
	User salesAssoicate;
	User manager;
	Date expenseDate;
	String associateComments;
	String managerComments;
	Set<ExpenseVoucherLine> expenseVoucherLines;
	FiniteValue status;
	Double requestedTotal;
	Double correctedTotal;
	Double approvedTotal;
	
	
	
	
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Division getDivision() {
		return division;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setDivision(Division division) {
		this.division = division;
	}
	@RadsPropertySet(isBK =true)
	public String getDocNumber() {
		return docNumber;
	}
	@RadsPropertySet(isBK =true)
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public FiniteValue getStatus() {
		return status;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setStatus(FiniteValue status) {
		this.status = status;
	}
	
	
	public Set<ExpenseVoucherLine> getExpenseVoucherLines() {
		return expenseVoucherLines;
	}
	public void setExpenseVoucherLines(Set<ExpenseVoucherLine> expenseVoucherLines) {
		this.expenseVoucherLines = expenseVoucherLines;
	}
	
	public void addExpenseVoucherLine(ExpenseVoucherLine expenseVoucherLine) {
		if (expenseVoucherLines == null )
			expenseVoucherLines = new LinkedHashSet <ExpenseVoucherLine> ();
		this.expenseVoucherLines.add(expenseVoucherLine);
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public User getSalesAssoicate() {
		return salesAssoicate;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setSalesAssoicate(User salesAssoicate) {
		this.salesAssoicate = salesAssoicate;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public User getManager() {
		return manager;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setManager(User manager) {
		this.manager = manager;
	}
	public Date getExpenseDate() {
		return expenseDate;
	}
	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}
	public String getAssociateComments() {
		return associateComments;
	}
	public void setAssociateComments(String associateComments) {
		this.associateComments = associateComments;
	}
	public String getManagerComments() {
		return managerComments;
	}
	public void setManagerComments(String managerComments) {
		this.managerComments = managerComments;
	}
	public Double getRequestedTotal() {
		return requestedTotal;
	}
	public void setRequestedTotal(Double requestedTotal) {
		this.requestedTotal = requestedTotal;
	}
	public Double getCorrectedTotal() {
		return correctedTotal;
	}
	public void setCorrectedTotal(Double correctedTotal) {
		this.correctedTotal = correctedTotal;
	}
	public Double getApprovedTotal() {
		return approvedTotal;
	}
	public void setApprovedTotal(Double approvedTotal) {
		this.approvedTotal = approvedTotal;
	}
	
	public boolean isOpen()
	{
		if(status != null && CRMConstants.EXP_VOUCHER_STATUS.REQUESTED.equals(status.getCode()) || CRMConstants.EXP_VOUCHER_STATUS.COUNTERED.equals(status.getCode()) ||
				CRMConstants.EXP_VOUCHER_STATUS.REREQUESTED.equals(status.getCode()))
			return true ;
		else 
			return false;
	}
	
	
}
