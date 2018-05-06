package com.rainbow.crm.address.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class Address  extends CRMBusinessModelObject{
	
	Customer customer;
	FiniteValue addressType;
	String recipient ;
	String address1;
	String address2;
	String zipcode;
	String city;
	String state;
	String country;
	String phone;
	String email;
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public FiniteValue getAddressType() {
		return addressType;
	}
	public void setAddressType(FiniteValue addressType) {
		this.addressType = addressType;
	}
	@RadsPropertySet(isBK=true)
	public String getRecipient() {
		return recipient;
	}
	@RadsPropertySet(isBK=true)
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	@RadsPropertySet(isBK=true)
	public String getAddress1() {
		return address1;
	}
	@RadsPropertySet(isBK=true)
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	@RadsPropertySet(isBK=true)
	public String getAddress2() {
		return address2;
	}
	@RadsPropertySet(isBK=true)
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	@RadsPropertySet(isBK=true)
	public String getZipcode() {
		return zipcode;
	}
	@RadsPropertySet(isBK=true)
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	@RadsPropertySet(isBK=true)
	public String getCity() {
		return city;
	}
	@RadsPropertySet(isBK=true)
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
	
	
}
