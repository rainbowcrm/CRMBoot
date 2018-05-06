package com.rainbow.crm.loyalty.model;

import java.util.Date;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.annotations.RadsPropertySet;
import com.techtrade.rads.framework.utils.Utils;

public class Loyalty extends CRMBusinessModelObject{
   
	Division division;
	Customer customer;
	Sales sales;
	boolean redeemed;
	double points;
	double redeemedAmount;
	String comments;
	public Division getDivision() {
		return division;
	}
	public void setDivision(Division division) {
		this.division = division;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Sales getSales() {
		return sales;
	}
	public void setSales(Sales sales) {
		this.sales = sales;
	}
	public boolean isRedeemed() {
		return redeemed;
	}
	public void setRedeemed(boolean redeemed) {
		this.redeemed = redeemed;
	}
	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}
	public double getRedeemedAmount() {
		return redeemedAmount;
	}
	public void setRedeemedAmount(double redeemedAmount) {
		this.redeemedAmount = redeemedAmount;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
	
	
	
	
}
