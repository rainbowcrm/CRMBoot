package com.rainbow.crm.sales.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.address.model.Address;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.vendor.model.Vendor;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class Sales extends CRMBusinessModelObject{

	Division division;
	String billNumber;
	Customer customer;
	Territory territory;
	Address deliveryAddress;
	FiniteValue orderType;
	Date salesDate ;
	String salesRef;
	User salesMan;
	double discAmount;
	double discPercent;
	double totalDisc;
	double taxPerc;
	double taxAmount;
	double netAmount;
	double grossAmount;
	boolean creditSales ;
	boolean settled =true;
	boolean voided;
	boolean returned;
	String commments;
	
	Double loyaltyRedeemed;
	Double loyaltyDiscount;
	Double availableLoyalty; 
	
	Set<SalesLine> salesLines;
	
	boolean isFutureDelivery;
	FiniteValue deliveryMode;
	String deliveryAgent;
	Date deliveryDate ;
	Date  paymentDueDate ;

	boolean isReturn;
	String originalBillNo;
	Integer originalSalesId;
	String originalDate;
	
	boolean askForFeedBack;
	boolean feedBackAlerted;
	boolean feedBackCaptured;
	
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Division getDivision() {
		return division;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setDivision(Division division) {
		this.division = division;
	}
	
	@RadsPropertySet(isBK =true)
	public String getBillNumber() {
		return billNumber;
	}
	@RadsPropertySet(isBK =true)
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	
	
	
	public Date getSalesDate() {
		return salesDate;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Customer getCustomer() {
		return customer;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}
	public double getDiscAmount() {
		return discAmount;
	}
	public void setDiscAmount(double discAmount) {
		this.discAmount = discAmount;
	}
	public double getDiscPercent() {
		return discPercent;
	}
	public void setDiscPercent(double discPercent) {
		this.discPercent = discPercent;
	}
	public double getTotalDisc() {
		return totalDisc;
	}
	public void setTotalDisc(double totalDisc) {
		this.totalDisc = totalDisc;
	}
	public double getTaxPerc() {
		return taxPerc;
	}
	public void setTaxPerc(double taxPerc) {
		this.taxPerc = taxPerc;
	}
	public double getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}
	public double getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}
	@RadsPropertySet(excludeFromJSON = true, excludeFromMap = true, excludeFromXML = true)
	public double getAbsNetAmount() {
		return Math.abs(netAmount);
	}
	
	@RadsPropertySet(excludeFromJSON = true, excludeFromMap = true, excludeFromXML = true)
	public void setAbsNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}
	
	
	public boolean isCreditSales() {
		return creditSales;
	}
	public void setCreditSales(boolean creditSales) {
		this.creditSales = creditSales;
	}
	public boolean isSettled() {
		return settled;
	}
	public void setSettled(boolean settled) {
		this.settled = settled;
	}

	public boolean isVoided() {
		return voided;
	}
	public void setVoided(boolean voided) {
		this.voided = voided;
	}
	public boolean isReturned() {
		return returned;
	}
	public void setReturned(boolean returned) {
		this.returned = returned;
	}
	public String getCommments() {
		return commments;
	}
	public void setCommments(String commments) {
		this.commments = commments;
	}
	public Set<SalesLine> getSalesLines() {
		return salesLines;
	}
	public void setSalesLines(Set<SalesLine> salesLines) {
		this.salesLines = salesLines;
	}
	public void addSalesLine(SalesLine salesLine) {
		if (salesLines == null )
			salesLines = new LinkedHashSet <SalesLine> ();
		this.salesLines.add(salesLine);
	}
	public String getSalesRef() {
		return salesRef;
	}
	public void setSalesRef(String salesRef) {
		this.salesRef = salesRef;
	}
	public User getSalesMan() {
		return salesMan;
	}
	public void setSalesMan(User salesMan) {
		this.salesMan = salesMan;
	}
	public boolean isFutureDelivery() {
		return isFutureDelivery;
	}
	public void setFutureDelivery(boolean isFutureDelivery) {
		this.isFutureDelivery = isFutureDelivery;
	}
	public FiniteValue getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(FiniteValue deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public String getDeliveryAgent() {
		return deliveryAgent;
	}
	public void setDeliveryAgent(String deliveryAgent) {
		this.deliveryAgent = deliveryAgent;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Date getPaymentDueDate() {
		return paymentDueDate;
	}
	public void setPaymentDueDate(Date paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}
	public FiniteValue getOrderType() {
		return orderType;
	}
	public void setOrderType(FiniteValue orderType) {
		this.orderType = orderType;
	}
	public Address getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public Territory getTerritory() {
		return territory;
	}
	public void setTerritory(Territory territory) {
		this.territory = territory;
	}
	public boolean isReturn() {
		return isReturn;
	}
	public void setReturn(boolean isReturn) {
		this.isReturn = isReturn;
	}
	public String getOriginalBillNo() {
		return originalBillNo;
	}
	public void setOriginalBillNo(String originalBillNo) {
		this.originalBillNo = originalBillNo;
	}
	public Integer getOriginalSalesId() {
		return originalSalesId;
	}
	public void setOriginalSalesId(Integer originalSalesId) {
		this.originalSalesId = originalSalesId;
	}
	public String getOriginalDate() {
		return originalDate;
	}
	public void setOriginalDate(String originalDate) {
		this.originalDate = originalDate;
	}
	public Double getLoyaltyRedeemed() {
		return loyaltyRedeemed;
	}
	public void setLoyaltyRedeemed(Double loyaltyRedeemed) {
		this.loyaltyRedeemed = loyaltyRedeemed;
	}
	public Double getLoyaltyDiscount() {
		return loyaltyDiscount;
	}
	public void setLoyaltyDiscount(Double loyaltyDiscount) {
		this.loyaltyDiscount = loyaltyDiscount;
	}
	public Double getAvailableLoyalty() {
		return availableLoyalty;
	}
	public void setAvailableLoyalty(Double availableLoyalty) {
		this.availableLoyalty = availableLoyalty;
	}
	public boolean isFeedBackAlerted() {
		return feedBackAlerted;
	}
	public void setFeedBackAlerted(boolean feedBackAlerted) {
		this.feedBackAlerted = feedBackAlerted;
	}
	public boolean isFeedBackCaptured() {
		return feedBackCaptured;
	}
	public void setFeedBackCaptured(boolean feedBackCaptured) {
		this.feedBackCaptured = feedBackCaptured;
	}
	public double getGrossAmount() {
		return grossAmount;
	}
	public void setGrossAmount(double grossAmount) {
		this.grossAmount = grossAmount;
	}
	public boolean isAskForFeedBack() {
		return askForFeedBack;
	}
	public void setAskForFeedBack(boolean askForFeedBack) {
		this.askForFeedBack = askForFeedBack;
	}
	
	
	
	
		
}
