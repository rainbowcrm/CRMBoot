package com.rainbow.crm.document.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.contact.model.Contact;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

@RadsPropertySet(jsonTag="Document",xmlTag="Document")
public class Document extends CRMBusinessModelObject{
	
	FiniteValue docType;
	String docName;
	String docPath;
	Item item;
	Customer customer;
	User owner; 
	String comments;
	SalesLead lead;
	Sales sales;
	
	byte[] docData ;
	String fileName1;
	
	String fileWithLink; 
	
	public FiniteValue getDocType() {
		return docType;
	}
	public void setDocType(FiniteValue docType) {
		this.docType = docType;
	}
	
	@RadsPropertySet(isBK =true)
	public String getDocName() {
		return docName;
	}
	
	@RadsPropertySet(isBK =true)
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForMap=true,useBKForXML=true)
	public Item getItem() {
		return item;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForMap=true,useBKForXML=true)
	public void setItem(Item item) {
		this.item = item;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForMap=true,useBKForXML=true)
	public Customer getCustomer() {
		return customer;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForMap=true,useBKForXML=true)
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForMap=true,useBKForXML=true)
	public User getOwner() {
		return owner;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForMap=true,useBKForXML=true)
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForMap=true,useBKForXML=true)
	public SalesLead getLead() {
		return lead;
	}
	@RadsPropertySet(useBKForJSON=true,useBKForMap=true,useBKForXML=true)
	public void setLead(SalesLead lead) {
		this.lead = lead;
	}
	public byte[] getDocData() {
		return docData;
	}
	public void setDocData(byte[] docData) {
		this.docData = docData;
	}
	public String getFileName1() {
		return fileName1;
	}
	public void setFileName1(String fileName1) {
		this.fileName1 = fileName1;
	}
	public String getFileWithLink() {
		if(Utils.isNullString(fileWithLink)) {
			try {
			String serverURL = CRMAppConfig.INSTANCE.getProperty("doc_server");
			fileWithLink= serverURL + getDocPath() ;
			}catch(Exception ex)
			{
				Logwriter.INSTANCE.error(ex);
			}
		}
		return fileWithLink;
	}
	public void setFileWithLink(String fileWithLink) {
		this.fileWithLink = fileWithLink;
	}
	public Sales getSales() {
		return sales;
	}
	public void setSales(Sales sales) {
		this.sales = sales;
	}
	
	
	
	
	

}
