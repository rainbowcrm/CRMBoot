package com.rainbow.crm.user.model;


import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.common.finitevalue.FiniteValueManager;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.division.model.Division;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

public class User extends CRMBusinessModelObject {
	
	String userId;
	String password;
	String roleType;
	String firstName;
	String lastName;
	String address1;
	String address2;
	String zipCode;
	String city;
	String phone;
	String email;
	String photo;
	boolean isActive=true;
    Division division;
	 
    String prefix;
    String suffix;
    
    @RadsPropertySet(isBK=true)
	public String getUserId() {
		return userId;
	}
	@RadsPropertySet(isBK=true)
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String passsword) {
		this.password = passsword;
	}
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public String getRoleTypeDesc() {
		return FiniteValueManager.INSTANCE.getFiniteValueDesc(roleType);
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
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
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
	public String getPrefix() {
		if (Utils.isNullString(prefix)  && !Utils.isNullString(userId))  {
			prefix = userId.substring(0,userId.indexOf('@')-1);
		}
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public Division getDivision() {
		return division;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForXML=true,useBKForMap=true)
	public void setDivision(Division division) {
		this.division = division;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public Object getPK() {
		return null;
	}
	
	@Override
	@RadsPropertySet(excludeFromJSON=true,excludeFromMap=true,excludeFromXML=true)
	public boolean isNullContent() {
		if (Utils.isNullString(userId))
			 return true;
		else
			return false;
	}


	byte[] image;
	String fileName;
	String fileWithLink;
	String fileWithoutLink;

	String base64Image;

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileWithLink() {
		return fileWithLink;
	}

	public void setFileWithLink(String fileWithLink) {
		this.fileWithLink = fileWithLink;
	}

	public String getFileWithoutLink() {
		return fileWithoutLink;
	}

	public void setFileWithoutLink(String fileWithoutLink) {
		this.fileWithoutLink = fileWithoutLink;
	}

	public String getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
}
