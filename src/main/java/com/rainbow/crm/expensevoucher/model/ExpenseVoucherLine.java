package com.rainbow.crm.expensevoucher.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.expensehead.model.ExpenseHead;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.sales.model.Sales;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class ExpenseVoucherLine extends CRMBusinessModelObject{

	int lineNumber;
	ExpenseHead expenseHead;
	String billNumber;
	String expenseLocation;
	Double requestedAmount;
	Double correctedAmount;
	Double approvedAmount;
	String associateComments;
	String managerComments;
	String file1;
	String file2;
	String  filePath;
	ExpenseVoucher expenseVoucherDoc;
	
	byte[] file1Data ;
	byte[] file2Data ;
	
	String fileWithLink; 
	
	public ExpenseVoucherLine() {
	
	}
	@RadsPropertySet(isBK=true)
	public int getLineNumber() {
		return lineNumber;
	}
	@RadsPropertySet(isBK=true)
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public ExpenseVoucher getExpenseVoucherDoc() {
		return expenseVoucherDoc;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public void setExpenseVoucherDoc(ExpenseVoucher expenseVoucherDoc) {
		this.expenseVoucherDoc = expenseVoucherDoc;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public ExpenseHead getExpenseHead() {
		return expenseHead;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setExpenseHead(ExpenseHead expenseHead) {
		this.expenseHead = expenseHead;
	}
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public String getExpenseLocation() {
		return expenseLocation;
	}
	public void setExpenseLocation(String expenseLocation) {
		this.expenseLocation = expenseLocation;
	}
	public Double getRequestedAmount() {
		return requestedAmount;
	}
	public void setRequestedAmount(Double requestedAmount) {
		this.requestedAmount = requestedAmount;
	}
	public Double getCorrectedAmount() {
		return correctedAmount;
	}
	public void setCorrectedAmount(Double correctedAmount) {
		this.correctedAmount = correctedAmount;
	}
	public Double getApprovedAmount() {
		return approvedAmount;
	}
	public void setApprovedAmount(Double approvedAmount) {
		this.approvedAmount = approvedAmount;
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
	public String getFile1() {
		return file1;
	}
	public void setFile1(String file1) {
		this.file1 = file1;
	}
	public String getFile2() {
		return file2;
	}
	public void setFile2(String file2) {
		this.file2 = file2;
	}
	public byte[] getFile1Data() {
		return file1Data;
	}
	public void setFile1Data(byte[] file1Data) {
		this.file1Data = file1Data;
	}
	public byte[] getFile2Data() {
		return file2Data;
	}
	public void setFile2Data(byte[] file2Data) {
		this.file2Data = file2Data;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileWithLink() {
		return fileWithLink;
	}
	public void setFileWithLink(String fileWithLink) {
		this.fileWithLink = fileWithLink;
	}
	
	
	
	
}
