package com.rainbow.crm.customer.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

@RadsPropertySet(jsonTag="Customer",xmlTag="Customer")
public class Customer extends CRMBusinessModelObject{
   
	
	String firstName;
	String lastName;
	String fullName;
	String description;
	String address1;
	String address2;
	String zipCode;
	String city;
	String phone;
	String email;
	
	Double creditLimit;
	String landmark;
	String alternatePhone;
	
	Double loyaltyPoint;
	String photoFile;
	byte[] image;
	String fileName;
	String fileWithLink;
	String tempPhotoFile;
	
	String base64Image;
	
	boolean referencible;
	
	public String getFullName() {
		if (Utils.isNullString(fullName)) {
			fullName = firstName + " " + lastName; 
		}
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	
	
	
	public Double getCreditLimit() {
		return creditLimit;
	}
	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
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

	public Double getLoyaltyPoint() {
		return loyaltyPoint;
	}

	public void setLoyaltyPoint(Double loyaltyPoint) {
		this.loyaltyPoint = loyaltyPoint;
	}

	public String getPhotoFile() {
		return photoFile;
	}

	public void setPhotoFile(String photoFile) {
		this.photoFile = photoFile;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getFileWithLink() {
		if(Utils.isNull(fileWithLink)) {
			try {
				String serverURL = CRMAppConfig.INSTANCE.getProperty("doc_server");
				fileWithLink = serverURL + getPhotoFile();
			}catch(Exception ex) {
				Logwriter.INSTANCE.error(ex);
			}
		}
		return fileWithLink;
	}

	public void setFileWithLink(String fileWithLink) {
		this.fileWithLink = fileWithLink;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}

	public boolean isReferencible() {
		return referencible;
	}

	public void setReferencible(boolean referencible) {
		this.referencible = referencible;
	}

	public String getTempPhotoFile() {
		return tempPhotoFile;
	}

	public void setTempPhotoFile(String tempPhotoFile) {
		this.tempPhotoFile = tempPhotoFile;
	}

	
	
	
	

}
