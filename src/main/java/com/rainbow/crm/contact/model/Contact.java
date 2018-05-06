package com.rainbow.crm.contact.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

@RadsPropertySet(jsonTag="Contact",xmlTag="Contact")
public class Contact extends CRMBusinessModelObject{
   
	
	String firstName;
	String lastName;
	String fullName;
	String identifierName ;
	String description;
	String address1;
	String address2;
	String zipCode;
	String city;
	String phone;
	String email;
	FiniteValue contactType;
	String landmark;
	String alternatePhone;
	
	public String getFullName() {
		if (Utils.isNullString(fullName)) {
			fullName = firstName + " " + lastName ; 
		}
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
	public String getIdentifierName() {
		if(Utils.isNullString(identifierName)) {
			identifierName = firstName + " " + lastName + " - " + phone; 
		}
		return identifierName;
	}

	public void setIdentifierName(String identifierName) {
		this.identifierName = identifierName;
	}

	@RadsPropertySet(jsonTag="FirstName",xmlTag="FirstName")
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@RadsPropertySet(isBK=true)
	public String getPhone() {
		return phone;
	}
	@RadsPropertySet(isBK=true)
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public FiniteValue getContactType() {
		return contactType;
	}
	public void setContactType(FiniteValue contactType) {
		this.contactType = contactType;
	}
	public String getLandmark() {
		return landmark;
	}
	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	public String getAlternatePhone() {
		return alternatePhone;
	}
	public void setAlternatePhone(String alternatePhone) {
		this.alternatePhone = alternatePhone;
	}
	
	

}
