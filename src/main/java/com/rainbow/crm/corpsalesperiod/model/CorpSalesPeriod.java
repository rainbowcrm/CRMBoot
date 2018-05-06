
package com.rainbow.crm.corpsalesperiod.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.vendor.model.Vendor;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class CorpSalesPeriod extends CRMBusinessModelObject{

	String period;
	String description;
	Date fromDate ;
	Date toDate ;
	double additionalTarget;
	double totalTarget;
	boolean voided;
	boolean startAlerted;
	boolean endAlerted;
	Set<CorpSalesPeriodLine> corpSalesPeriodLines;
	Set<CorpSalesPeriodDivision> corpSalesPeriodDivisions;
	Set<CorpSalesPeriodBrand> corpSalesPeriodBrands;
	Set<CorpSalesPeriodCategory> corpSalesPeriodCategories;
	Set<CorpSalesPeriodProduct> corpSalesPeriodProducts;
	
	
	
	
	
	
	public Set<CorpSalesPeriodDivision> getCorpSalesPeriodDivisions() {
		return corpSalesPeriodDivisions;
	}
	public void setCorpSalesPeriodDivisions(
			Set<CorpSalesPeriodDivision> corpSalesPeriodDivisions) {
		this.corpSalesPeriodDivisions = corpSalesPeriodDivisions;
	}
	
	public void addCorpSalesPeriodDivision(CorpSalesPeriodDivision corpSalesPeriodDivision) {
		if (corpSalesPeriodDivisions == null )
			corpSalesPeriodDivisions = new LinkedHashSet <CorpSalesPeriodDivision> ();
		this.corpSalesPeriodDivisions.add(corpSalesPeriodDivision);
	}
	
	public Set<CorpSalesPeriodBrand> getCorpSalesPeriodBrands() {
		return corpSalesPeriodBrands;
	}
	public void setCorpSalesPeriodBrands(Set<CorpSalesPeriodBrand> corpSalesPeriodBrands) {
		this.corpSalesPeriodBrands = corpSalesPeriodBrands;
	}
	
	
	public void addCorpSalesPeriodCategory(CorpSalesPeriodCategory corpSalesPeriodCategory)
	{
		if (corpSalesPeriodCategories == null )
			corpSalesPeriodCategories = new LinkedHashSet <CorpSalesPeriodCategory> ();
		this.corpSalesPeriodCategories.add(corpSalesPeriodCategory);
		
	}
	
	public void addCorpSalesPeriodBrand(CorpSalesPeriodBrand corpSalesPeriodBrand)
	{
		if (corpSalesPeriodBrands == null )
			corpSalesPeriodBrands = new LinkedHashSet <CorpSalesPeriodBrand> ();
		this.corpSalesPeriodBrands.add(corpSalesPeriodBrand);
		
	}
	
	public void addCorpSalesPeriodProduct(CorpSalesPeriodProduct corpSalesPeriodProduct)
	{
		if (corpSalesPeriodProducts == null )
			corpSalesPeriodProducts = new LinkedHashSet <CorpSalesPeriodProduct> ();
		this.corpSalesPeriodProducts.add(corpSalesPeriodProduct);
		
	}
	
	public Set<CorpSalesPeriodCategory> getCorpSalesPeriodCategories() {
		return corpSalesPeriodCategories;
	}
	public void setCorpSalesPeriodCategories(
			Set<CorpSalesPeriodCategory> corpSalesPeriodCategories) {
		this.corpSalesPeriodCategories = corpSalesPeriodCategories;
	}
	
	
	public Set<CorpSalesPeriodProduct> getCorpSalesPeriodProducts() {
		return corpSalesPeriodProducts;
	}
	public void setCorpSalesPeriodProducts(Set<CorpSalesPeriodProduct> corpSalesPeriodProducts) {
		this.corpSalesPeriodProducts = corpSalesPeriodProducts;
	}
	
	
	@RadsPropertySet(isBK=true)
	public String getPeriod() {
		return period;
	}
	
	@RadsPropertySet(isBK=true)
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public double getTotalTarget() {
		return totalTarget;
	}
	public void setTotalTarget(double totalTarget) {
		this.totalTarget = totalTarget;
	}
	public boolean isVoided() {
		return voided;
	}
	public void setVoided(boolean voided) {
		this.voided = voided;
	}
	public Set<CorpSalesPeriodLine> getCorpSalesPeriodLines() {
		return corpSalesPeriodLines;
	}
	public void setCorpSalesPeriodLines(Set<CorpSalesPeriodLine> corpSalesPeriodLines) {
		this.corpSalesPeriodLines = corpSalesPeriodLines;
	}
	public void addCorpSalesPeriodLine(CorpSalesPeriodLine corpSalesPeriodLine) {
		if (corpSalesPeriodLines == null )
			corpSalesPeriodLines = new LinkedHashSet <CorpSalesPeriodLine> ();
		this.corpSalesPeriodLines.add(corpSalesPeriodLine);
	}
	
	public double getAdditionalTarget() {
		return additionalTarget;
	}
	public void setAdditionalTarget(double additionalTarget) {
		this.additionalTarget = additionalTarget;
	}
	public boolean isStartAlerted() {
		return startAlerted;
	}
	public void setStartAlerted(boolean startAlerted) {
		this.startAlerted = startAlerted;
	}
	public boolean isEndAlerted() {
		return endAlerted;
	}
	public void setEndAlerted(boolean endAlerted) {
		this.endAlerted = endAlerted;
	}
	

	
	
		
}
