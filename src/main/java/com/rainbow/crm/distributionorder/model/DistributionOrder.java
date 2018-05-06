package com.rainbow.crm.distributionorder.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.address.model.Address;
import com.rainbow.crm.carrier.model.Carrier;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.vendor.model.Vendor;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class DistributionOrder extends CRMBusinessModelObject{

	Division division;
	String docNumber;
	Customer customer;
	Carrier carrier;
	Address address;
	Date orderDate;
	Date packDate ;
	Sales sales ;
	boolean voided;
	String comments;
	Set<DistributionOrderLine> distributionOrderLines;
	FiniteValue status;
	Date shippingDate;
	Double shippingCharges;
	String shipmentRefNumber;
	String user ;
	boolean alerted; 
	
	
	
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
		
	public Sales getSales() {
		return sales;
	}
	public void setSales(Sales sales) {
		this.sales = sales;
	}
	public FiniteValue getStatus() {
		return status;
	}
	public void setStatus(FiniteValue status) {
		this.status = status;
	}
	
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Customer getCustomer() {
		return customer;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public boolean isVoided() {
		return voided;
	}
	public void setVoided(boolean voided) {
		this.voided = voided;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String commments) {
		this.comments = commments;
	}
	public Set<DistributionOrderLine> getDistributionOrderLines() {
		return distributionOrderLines;
	}
	public void setDistributionOrderLines(Set<DistributionOrderLine> distributionOrderLines) {
		this.distributionOrderLines = distributionOrderLines;
	}
	public void addDistributionOrderLine(DistributionOrderLine distributionOrderLine) {
		if (distributionOrderLines == null )
			distributionOrderLines = new LinkedHashSet <DistributionOrderLine> ();
		this.distributionOrderLines.add(distributionOrderLine);
	}
	public boolean isAlerted() {
		return alerted;
	}
	public void setAlerted(boolean alerted) {
		this.alerted = alerted;
	}
	public Carrier getCarrier() {
		return carrier;
	}
	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Date getPackDate() {
		return packDate;
	}
	public void setPackDate(Date packDate) {
		this.packDate = packDate;
	}
	public Date getShippingDate() {
		return shippingDate;
	}
	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}
	public Double getShippingCharges() {
		return shippingCharges;
	}
	public void setShippingCharges(Double shippingCharges) {
		this.shippingCharges = shippingCharges;
	}
	public String getShipmentRefNumber() {
		return shipmentRefNumber;
	}
	public void setShipmentRefNumber(String shipmentRefNumber) {
		this.shipmentRefNumber = shipmentRefNumber;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	
	
}
