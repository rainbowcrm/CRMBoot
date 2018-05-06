package com.rainbow.crm.enquiry.model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.contact.model.Contact;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

@RadsPropertySet(jsonTag="Enquiry",xmlTag="Enquiry")
public class Enquiry extends CRMBusinessModelObject{
	
	String docNumber;
	Division division;
	Territory territory;
	FiniteValue enquiryType;
	FiniteValue enquirySource;
	FiniteValue enquiryStatus;
	Contact contact;
	Customer customer;
	ReasonCode reason;
	String firstName;
	String lastName;
	String phone;
	String email;
	String address1;
	String address2;
	String city;
	String zipcode;
	
	
	
	User salesAssociate;
	String enquiry;
	Date enqDate;
	Item nearestItem;
	Sku nearestSku;
	String comments;
	Set<EnquiryLine> enquiryLines;
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getEnqDate() {
		return enqDate;
	}
	public void setEnqDate(Date enqDate) {
		this.enqDate = enqDate;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public Division getDivision() {
		return division;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setDivision(Division division) {
		this.division = division;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public Territory getTerritory() {
		return territory;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setTerritory(Territory territory) {
		this.territory = territory;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public FiniteValue getEnquiryStatus() {
		return enquiryStatus;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setEnquiryStatus(FiniteValue enquiryStatus) {
		this.enquiryStatus = enquiryStatus;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public FiniteValue getEnquiryType() {
		return enquiryType;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setEnquiryType(FiniteValue enquiryType) {
		this.enquiryType = enquiryType;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public FiniteValue getEnquirySource() {
		return enquirySource;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setEnquirySource(FiniteValue enquirySource) {
		this.enquirySource = enquirySource;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public Contact getContact() {
		return contact;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public User getSalesAssociate() {
		return salesAssociate;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setSalesAssociate(User salesAssociate) {
		this.salesAssociate = salesAssociate;
	}
	
	public String getEnquiry() {
		return enquiry;
	}
	public void setEnquiry(String enquiry) {
		this.enquiry = enquiry;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public Item getNearestItem() {
		return nearestItem;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setNearestItem(Item nearestItem) {
		this.nearestItem = nearestItem;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public Sku getNearestSku() {
		return nearestSku;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setNearestSku(Sku nearestSku) {
		this.nearestSku = nearestSku;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public Customer getCustomer() {
		return customer;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public ReasonCode getReason() {
		return reason;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setReason(ReasonCode reason) {
		this.reason = reason;
	}
	public Set<EnquiryLine> getEnquiryLines() {
		return enquiryLines;
	}
	public void addEnquiryLine(EnquiryLine enquiryLine) {
		if(enquiryLines == null)
			enquiryLines = new LinkedHashSet<EnquiryLine>();
			enquiryLines.add(enquiryLine) ;
	}
	public void setEnquiryLines(Set<EnquiryLine> enquiryLines) {
		this.enquiryLines = enquiryLines;
	}
	public String getDocNumber() {
		return docNumber;
	}
	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	
	

}
