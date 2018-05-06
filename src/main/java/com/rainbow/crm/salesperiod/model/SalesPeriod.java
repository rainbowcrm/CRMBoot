
package com.rainbow.crm.salesperiod.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.vendor.model.Vendor;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class SalesPeriod extends CRMBusinessModelObject{

	Division division;
	String period;
	String description;
	Date fromDate ;
	Date toDate ;
	double additionalTarget;
	double totalTarget;
	boolean voided;
	boolean startAlerted;
	boolean endAlerted;
	Set<SalesPeriodLine> salesPeriodLines;
	Set<SalesPeriodAssociate> salesPeriodAssociates ;
	Set<SalesPeriodTerritory> salesPeriodTerritories ;

	Set<SalesPeriodBrand> salesPeriodBrands;
	Set<SalesPeriodCategory> salesPeriodCategories;
	Set<SalesPeriodProduct> salesPeriodProducts;
	
	
	
	
	public Set<SalesPeriodBrand> getSalesPeriodBrands() {
		return salesPeriodBrands;
	}
	public void setSalesPeriodBrands(Set<SalesPeriodBrand> salesPeriodBrands) {
		this.salesPeriodBrands = salesPeriodBrands;
	}
	
	
	public void addSalePeriodCategory(SalesPeriodCategory salesPeriodCategory)
	{
		if (salesPeriodCategories == null )
			salesPeriodCategories = new LinkedHashSet <SalesPeriodCategory> ();
		this.salesPeriodCategories.add(salesPeriodCategory);
		
	}
	
	public void addSalePeriodBrand(SalesPeriodBrand salesPeriodBrand)
	{
		if (salesPeriodBrands == null )
			salesPeriodBrands = new LinkedHashSet <SalesPeriodBrand> ();
		this.salesPeriodBrands.add(salesPeriodBrand);
		
	}
	
	public void addSalePeriodProduct(SalesPeriodProduct salesPeriodProduct)
	{
		if (salesPeriodProducts == null )
			salesPeriodProducts = new LinkedHashSet <SalesPeriodProduct> ();
		this.salesPeriodProducts.add(salesPeriodProduct);
		
	}
	
	public Set<SalesPeriodCategory> getSalesPeriodCategories() {
		return salesPeriodCategories;
	}
	public void setSalesPeriodCategories(
			Set<SalesPeriodCategory> salesPeriodCategories) {
		this.salesPeriodCategories = salesPeriodCategories;
	}
	public Set<SalesPeriodProduct> getSalesPeriodProducts() {
		return salesPeriodProducts;
	}
	public void setSalesPeriodProducts(Set<SalesPeriodProduct> salesPeriodProducts) {
		this.salesPeriodProducts = salesPeriodProducts;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public Division getDivision() {
		return division;
	}
	@RadsPropertySet(useBKForJSON=true, useBKForXML=true,useBKForMap=true)
	public void setDivision(Division division) {
		this.division = division;
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
	public Set<SalesPeriodLine> getSalesPeriodLines() {
		return salesPeriodLines;
	}
	public void setSalesPeriodLines(Set<SalesPeriodLine> salesPeriodLines) {
		this.salesPeriodLines = salesPeriodLines;
	}
	public void addSalesPeriodLine(SalesPeriodLine salesPeriodLine) {
		if (salesPeriodLines == null )
			salesPeriodLines = new LinkedHashSet <SalesPeriodLine> ();
		this.salesPeriodLines.add(salesPeriodLine);
	}
	public void addSalesPeriodAssociate(SalesPeriodAssociate salesPeriodAssociate) {
		if (salesPeriodAssociates == null )
			salesPeriodAssociates = new LinkedHashSet <SalesPeriodAssociate> ();
		this.salesPeriodAssociates.add(salesPeriodAssociate);
	}
	
	public Set<SalesPeriodAssociate> getSalesPeriodAssociates() {
		return salesPeriodAssociates;
	}
	public void setSalesPeriodAssociates(
			Set<SalesPeriodAssociate> salesPeriodAsssociates) {
		this.salesPeriodAssociates = salesPeriodAsssociates;
	}
		
	public Set<SalesPeriodTerritory> getSalesPeriodTerritories() {
		return salesPeriodTerritories;
	}
	public void setSalesPeriodTerritories(
			Set<SalesPeriodTerritory> salesPeriodTerritories) {
		this.salesPeriodTerritories = salesPeriodTerritories;
	}
	public void addSalesPeriodTerritory(SalesPeriodTerritory salesPeriodTerritory) {
		if (salesPeriodTerritories == null )
			salesPeriodTerritories = new LinkedHashSet <SalesPeriodTerritory> ();
		this.salesPeriodTerritories.add(salesPeriodTerritory);
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
