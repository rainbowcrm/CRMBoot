package com.rainbow.crm.division.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValueManager;
import com.rainbow.crm.company.model.Company;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

public class Division extends CRMBusinessModelObject{
	String code;
	String name;
	String address1;
	String address2;
	String zipCode;
	String city;
	String phone;
	String email;
	String divisionType;
	String manager;
	String area;
	int noEmployees;

	@RadsPropertySet(isBK=true)
	public String getCode() {
		return code;
	}
	@RadsPropertySet(isBK=true)
	public void setCode(String code) {
		this.code = code;
	}
	@RadsPropertySet(isBK=true)
	public String getName() {
		return name;
	}
	@RadsPropertySet(isBK=true)
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public String getDivisionTypeDesc() {
		return FiniteValueManager.INSTANCE.getFiniteValueDesc(divisionType);
	}
	
	public String getDivisionType() {
		return divisionType;
	}
	public void setDivisionType(String divisionType) {
		this.divisionType = divisionType;
	}
	
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getNoEmployees() {
		return noEmployees;
	}
	public void setNoEmployees(int noEmployees) {
		this.noEmployees = noEmployees;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Division ) {
			Division other = (Division) obj;
			int otherId  = other.getId() ;
			return (otherId == this.getId());
		}
		return super.equals(obj);
	}
	
	@Override
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public boolean isNullContent() {
		if( id <= 0 && Utils.isNullString(name))
			return true ;
		else
			return false;
	}
	
	
}
