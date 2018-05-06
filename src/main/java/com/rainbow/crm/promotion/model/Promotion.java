package com.rainbow.crm.promotion.model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.custcategory.model.CustCategory;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

@RadsPropertySet(jsonTag="Promotion",xmlTag="Promotion")
public class Promotion extends CRMBusinessModelObject{
   
	String name ;
	Division division ;
	FiniteValue promoType;
	CustCategory custCategory ;
	Date startDt;
	Date endDt;
	FiniteValue itemClass;
	FiniteValue bundlePricing;
	Double bundlePrice; 
	
	
	Set<PromotionLine> promotionLines;
	
	Boolean isActive;
	Boolean forAll;
	
	Double requiredAmount;
	Double promotedDiscPercent;
	
	String comments;
	
	@RadsPropertySet(isBK=true)
	public String getName() {
		return name;
	}
	
	@RadsPropertySet(isBK=true)
	public void setName(String name) {
		this.name = name;
	}
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
	}
	public FiniteValue getPromoType() {
		return promoType;
	}
	public void setPromoType(FiniteValue promoType) {
		this.promoType = promoType;
	}
	public CustCategory getCustCategory() {
		return custCategory;
	}
	public void setCustCategory(CustCategory custCategory) {
		this.custCategory = custCategory;
	}
	public Date getStartDt() {
		return startDt;
	}
	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}
	public Date getEndDt() {
		return endDt;
	}
	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Set<PromotionLine> getPromotionLines() {
		return promotionLines;
	}
	public void setPromotionLines(Set<PromotionLine> promotionLines) {
		this.promotionLines = promotionLines;
	}
	public void addPromotionLine(PromotionLine promotionLine) {
		if(promotionLines == null)
			promotionLines = new LinkedHashSet<PromotionLine> ();
		this.promotionLines.add(promotionLine);
	}
	public Boolean getForAll() {
		return forAll;
	}
	public void setForAll(Boolean forAll) {
		this.forAll = forAll;
	}
	public Double getRequiredAmount() {
		return requiredAmount;
	}
	public void setRequiredAmount(Double requiredAmount) {
		this.requiredAmount = requiredAmount;
	}
	public Double getPromotedDiscPercent() {
		return promotedDiscPercent;
	}
	public void setPromotedDiscPercent(Double promotedDiscPercent) {
		this.promotedDiscPercent = promotedDiscPercent;
	}

	public FiniteValue getItemClass() {
		return itemClass;
	}

	public void setItemClass(FiniteValue itemClass) {
		this.itemClass = itemClass;
	}

	public FiniteValue getBundlePricing() {
		return bundlePricing;
	}

	public void setBundlePricing(FiniteValue bundlePricing) {
		this.bundlePricing = bundlePricing;
	}

	public Double getBundlePrice() {
		return bundlePrice;
	}

	public void setBundlePrice(Double bundlePrice) {
		this.bundlePrice = bundlePrice;
	}
	
	
		

}
