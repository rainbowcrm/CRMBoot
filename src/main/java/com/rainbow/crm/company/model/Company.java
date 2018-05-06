package com.rainbow.crm.company.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValueManager;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

@RadsPropertySet(jsonTag="Company",xmlTag="Company")
public class Company extends CRMModelObject{
	
	
	int id;
	String code;
	String name;
	String address1;
	String address2;
	String zipCode;
	String city;
	String phone;
	String industryType;
	String industryTypeDesc;
	
	String businessType;
	String businessTypeDesc;
	
	String state;
	String country;
	String adminName;
	Integer noDivisions;
	Integer noUsers;
	Date registrationDate ;
	Date activationDate;
	Date serviceExpiryDate;
	boolean isActive;
	
	@RadsPropertySet(isPK=true)
	public int getId() {
		return id;
	}
	
	@RadsPropertySet(isPK=true)
	public void setId(int id) {
		this.id = id;
	}
	
	@RadsPropertySet(isBK=true, jsonTag = "CompanyCode" , xmlTag ="CompanyCode")
	public String getCode() {
		return code;
	}
	@RadsPropertySet(isBK=true , jsonTag = "CompanyCode" , xmlTag ="CompanyCode")
	public void setCode(String code) {
		this.code = code;
	}
	
	@RadsPropertySet(isBK=true, jsonTag = "CompanyName" , xmlTag ="CompanyName")
	public String getName() {
		return name;
	}
	
	@RadsPropertySet(isBK=true, jsonTag = "CompanyName" , xmlTag ="CompanyName")
	public void setName(String name) {
		this.name = name;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
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
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public Integer getNoDivisions() {
		return noDivisions;
	}
	public void setNoDivisions(Integer noDivisions) {
		this.noDivisions = noDivisions;
	}
	public Integer getNoUsers() {
		return noUsers;
	}
	public void setNoUsers(Integer noUsers) {
		this.noUsers = noUsers;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public Date getActivationDate() {
		return activationDate;
	}
	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}
	public Date getServiceExpiryDate() {
		return serviceExpiryDate;
	}
	public void setServiceExpiryDate(Date serviceExpiryDate) {
		this.serviceExpiryDate = serviceExpiryDate;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
	public String getIndustryTypeDesc() {
		return FiniteValueManager.INSTANCE.getFiniteValueDesc(industryType);
	}
	public String getBusinessTypeDesc() {
		return FiniteValueManager.INSTANCE.getFiniteValueDesc(businessType);
	}
	@Override
	public Object getPK() {
		return id;
	}
	
	
	
	
}
