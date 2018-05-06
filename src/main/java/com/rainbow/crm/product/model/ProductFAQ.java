package com.rainbow.crm.product.model;

import com.rainbow.crm.abstratcs.model.CRMBusinessModelObject;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.annotations.RadsPropertySet;

public class ProductFAQ extends CRMBusinessModelObject{

	Product product ;
	int lineNumber;
	String question;
	String answer;
	User author;
	String comments;
	
	@RadsPropertySet(useBKForJSON =true, useBKForMap =true, useBKForXML =true)
	public Product getProduct() {
		return product;
	}
	@RadsPropertySet(useBKForJSON =true, useBKForMap =true, useBKForXML =true)
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@RadsPropertySet(useBKForJSON =true, useBKForMap =true, useBKForXML =true)
	public User getAuthor() {
		return author;
	}
	@RadsPropertySet(useBKForJSON =true, useBKForMap =true, useBKForXML =true)
	public void setAuthor(User author) {
		this.author = author;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	@Override
	public boolean isNullContent() {
		return (question == null && answer == null );
	}
	
	
	
}
