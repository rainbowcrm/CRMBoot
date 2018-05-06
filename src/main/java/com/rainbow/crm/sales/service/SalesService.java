package com.rainbow.crm.sales.service;

import java.io.File;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



















import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;



















import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.address.model.Address;
import com.rainbow.crm.address.service.IAddressService;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.AbstractionTransactionService;
import com.rainbow.crm.common.BusinessAction;
import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.Externalize;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.documents.PrintDocument;
import com.rainbow.crm.common.documents.PrintField;
import com.rainbow.crm.common.documents.PrintHeaderLine;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.common.messaging.CRMMessageSender;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.customer.service.ICustomerService;
import com.rainbow.crm.database.ConnectionCreater;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.inventory.model.InventoryDelta;
import com.rainbow.crm.inventory.model.InventoryDeltaLine;
import com.rainbow.crm.inventory.model.InventoryUpdateObject;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.validator.ProductValidator;
import com.rainbow.crm.promotion.model.Promotion;
import com.rainbow.crm.promotion.model.PromotionLine;
import com.rainbow.crm.promotion.service.IPromotionService;
import com.rainbow.crm.sales.dao.SalesDAO;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.model.SalesLine;
import com.rainbow.crm.sales.validator.SalesErrorCodes;
import com.rainbow.crm.sales.validator.SalesReturnValidator;
import com.rainbow.crm.sales.validator.SalesValidator;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.validator.SalesLeadErrorCodes;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.territory.service.ITerritoryService;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.rainbow.crm.vendor.model.Vendor;
import com.rainbow.crm.vendor.service.IVendorService;
import com.rainbow.framework.nextup.NextUpGenerator;
import com.rainbow.framework.utils.EmailComponent;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

@Transactional
public class SalesService extends AbstractionTransactionService implements ISalesService{

	
	@Override
	public List<Sales> getNonAlertedSalesFeedBack(int company, int  interval) {
		SalesDAO salesDao = (SalesDAO) getDAO();
		long currentTime =  (new java.util.Date()).getTime() ;
		long intervalTime = ((long)interval) * 24l * 60l * 60l * 1000l; 
		Date startDate = new java.util.Date( currentTime  -  intervalTime  )  ;
		return salesDao.getNonAlertedSalesFeedBack(company, startDate);
	}

	
	
	@Override
	public Long getUnitsSoldForItem(Item item, CRMContext context,
			boolean returns, Date from, Date to) {
		SalesDAO salesDao = (SalesDAO) getDAO();
		return salesDao.getUnitsSoldforItem(context.getLoggedinCompany(), item.getId(),returns, from, to);
	}



	@Override
	public Double getTotalSalesAmountForItem(Item item, CRMContext context,
			boolean returns, Date from, Date to) {
		SalesDAO salesDao = (SalesDAO) getDAO();
		return salesDao.getTotalAmountSalesLinesforItem(context.getLoggedinCompany(), item.getId(), from, to);
	}



	@Override
	public List<SalesLine> getSalesForItem(Item item, CRMContext context,
			boolean returns, Date from, Date to) {
		SalesDAO salesDao = (SalesDAO) getDAO();
		return salesDao.getSalesLinesforItem(context.getLoggedinCompany(), item.getId(), from, to);
	}

	
	
	@Override
	public Date getLastSaleDateForCustomer(Customer customer,
			CRMContext context, boolean isReturn, Date from, Date to) {
		SalesDAO salesDao = (SalesDAO) getDAO();
		return salesDao.getLastSaleDateforCustomer(context.getLoggedinCompany(), customer.getId(), from,to,isReturn);
	}



	@Override
	public Double getSalesAmountForCustomer(Customer customer,
			CRMContext context, boolean isReturn, Date from, Date to) {
		SalesDAO salesDao = (SalesDAO) getDAO();
		return salesDao.getTotalSalesAmountforCustomer(context.getLoggedinCompany(), customer.getId(), from, to,isReturn);
	}



	@Override
	public List<SalesLine> getSalesForCustomer(Customer customer,
			CRMContext context, boolean isReturn, Date from, Date to) {
		SalesDAO salesDao = (SalesDAO) getDAO();
		return salesDao.getSalesLinesforCustomer(context.getLoggedinCompany(), customer.getId(), from, to,isReturn);
	}



	@Override
	public Sales getByBillNumberforReturn(Division division, String billNumber) {
		SalesDAO salesDao = (SalesDAO) getDAO();
		Sales sale = salesDao.getByBillNumberandDivision(division, billNumber);
		return sale ;
	}

	@Override
	protected String getTableName() {
		return "Sales";
	}
	
	

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Sales", from, to, whereCondition, context,sortCriteria);
	}

	
	
	@Override
	public void reCalculateTotal(Sales sales, CRMContext contex) {
		double grossTotal = 0  ,netTotal = 0 ;
		DecimalFormat df = new DecimalFormat("#.##");
		if(sales != null &&  !Utils.isNullSet(sales.getSalesLines())) {
			for (SalesLine  line : sales.getSalesLines()) {
				if(!line.isDeleted())  {
					  
					
					if(line.getDiscPercent() > 0 ) {
						double lineDiscount =  line.getUnitPrice() * line.getQty() * line.getDiscPercent()  /100;
						line.setLineTotalDisc(Double.valueOf(df.format(lineDiscount)));
					}
					if (line.getLineTotalDisc() > 0 ){
						double lineTotal = line.getUnitPrice() * line.getQty()- line.getLineTotalDisc();
						line.setLineTotal(Double.valueOf(df.format(lineTotal)));
					}else {
						line.setLineTotal(line.getUnitPrice() * line.getQty());
					}
				grossTotal += line.getLineTotal();
					
				}
			}
			sales.setGrossAmount(Double.valueOf(df.format(grossTotal)));
			if(sales.getDiscPercent() > 0 ) {
				double transactionDisc = sales.getGrossAmount() * sales.getDiscPercent() /100;
				sales.setDiscAmount(Double.valueOf(df.format(transactionDisc)) );
			}
			if(sales.getTaxPerc() > 0 ) {
				double taxAmount = sales.getGrossAmount() * sales.getTaxPerc() /100;
				sales.setTaxAmount(Double.valueOf(df.format(taxAmount)) );
			}
			
			
			sales.setNetAmount(sales.getGrossAmount()  + sales.getDiscAmount() + sales.getTaxAmount()  - 
					((sales.getLoyaltyDiscount()==null)?0:sales.getLoyaltyDiscount().doubleValue()));
		}
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		Sales sale = (Sales)object ;
		sale.setCompany(company);
		if(!sale.isReturn()) 
		{
			SalesValidator validator = new SalesValidator(context);
			return validator.validateforCreate(object);
		}else {
			SalesReturnValidator validator = new SalesReturnValidator(context);
			return validator.validateforCreate(object);
		}
		
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		Sales sale = (Sales)object ;
		sale.setCompany(company);
		if(!sale.isReturn()) 
		{
			SalesValidator validator = new SalesValidator(context);
			return validator.validateforUpdate(object);
		}else {
			SalesReturnValidator validator = new SalesReturnValidator(context);
			return validator.validateforCreate(object);
		}
	}

	@Override
	protected ORMDAO getDAO() {
	return (SalesDAO) SpringObjectFactory.INSTANCE.getInstance("SalesDAO");
	}

	@Override
	public List<RadsError> adaptToUI(CRMContext context, ModelObject object) {
		Sales sales = (Sales) object;
		if(sales.isReturn()) {
 		sales.getSalesLines().forEach(salesLine ->  {
 			salesLine.setQty(salesLine.getQty() * -1 );
 			salesLine.setLineTotal(salesLine.getLineTotal() * -1 );
		} );
 		sales.setNetAmount(sales.getNetAmount() * -1 );
		}
		return null;
	}

	

	
	
	@Override
	public List<RadsError> adaptfromUI(CRMContext context, ModelObject object) {
		return adaptfromUI(context, (Sales)object);
	}

	private List<RadsError> adaptfromUI(CRMContext context,Sales object) {
		ICompanyService compService = (ICompanyService) SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		object.setCompany(company);
		double netAmount = 0 ;		
		double grossAmount = 0;
		List<RadsError> ans = new ArrayList<RadsError>();
		Externalize externalize = new Externalize(); ;
		if(object.getSalesMan() != null ){
			IUserService userService = (IUserService) SpringObjectFactory.INSTANCE.getInstance("IUserService");
			User user = (User)userService.getById(object.getSalesMan().getUserId());
			if (user == null ) {
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "User")));
			}
			object.setSalesMan(user);
		}
		if (object.getDivision() != null) {
			int divisionId  = object.getDivision().getId() ;
			IDivisionService divisionService =(IDivisionService) SpringObjectFactory.INSTANCE.getInstance("IDivisionService");
			Division division = null;
			if (divisionId > 0 )
				division = (Division)divisionService.getById(divisionId);
			else
				division  = (Division)divisionService.getByBusinessKey(object.getDivision(), context);
			if(division == null){
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesErrorCodes.FIELD_NOT_VALID , "Division"));
			}else {
				object.setDivision(division);
			}
		}
		if (object.getCustomer() != null) {
			String phone = object.getCustomer().getPhone();
			ICustomerService customerService = (ICustomerService) SpringObjectFactory.INSTANCE.getInstance("ICustomerService");
			Customer customer = customerService.getByPhone(context.getLoggedinCompany(), phone);
			if (customer != null)
				 object.setCustomer(customer);
			else 
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesErrorCodes.FIELD_NOT_VALID , "Customer"));
		}
		
		
		if(object.getTerritory() != null) {
			ITerritoryService territoryService = (ITerritoryService)SpringObjectFactory.INSTANCE.getInstance("ITerritoryService");
			Territory territory  = (Territory)territoryService.getById(object.getTerritory().getId());
			object.setTerritory(territory);
		}
		if (object.isReturn()) {
			eliminate0LinesFromReturns(object);
		}
		
		if(!Utils.isNullSet(object.getSalesLines())){
			int lineNo=1;
			for (SalesLine line: object.getSalesLines()) {
				line.setCompany(company);
				line.setBillNumber(object.getBillNumber());
				line.setLineNumber(lineNo ++);
				if(line.getSku() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Item")));
				}else {
					String itemName = line.getSku().getName() ;
					ISkuService itemService = (ISkuService)SpringObjectFactory.INSTANCE.getInstance("ISkuService");
					Sku item = itemService.getByName(object.getCompany().getId(), itemName);
					line.setSku(item);
				}
				if(line.getUser() != null) {
					IUserService userService = (IUserService) SpringObjectFactory.INSTANCE.getInstance("IUserService");
					User user = (User)userService.getById(line.getUser().getUserId());
					if (user == null ) {
						ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "User")));
					}
					line.setUser(user);
				}
				if (line.getDiscPercent() > 0) {
					double lineDiscAmt = ( line.getQty() * line.getUnitPrice() ) *  line.getDiscPercent() /100 ;
					line.setLineTotalDisc(lineDiscAmt);
				}
				double lineTotal =(line.getQty() * line.getUnitPrice()) - line.getLineTotalDisc(); 
				line.setLineTotal( lineTotal );
				grossAmount += lineTotal;
			}
		}
	   
		if(object.getDiscPercent() > 0 ) {
			double discAmot = (grossAmount * object.getDiscPercent() ) /100;
			object.setDiscAmount(discAmot);
		}
		if ( object.getTaxPerc() > 0) {
		   double taxAmt = (grossAmount * object.getTaxPerc() ) /100;
		   object.setTaxAmount(taxAmt);
	   }
	    
	   netAmount =grossAmount  + object.getTaxAmount() - object.getDiscAmount() ;
	   object.setNetAmount(netAmount);
		if(object.getDeliveryAddress() != null  && !object.getDeliveryAddress().isNullContent()) {
			IAddressService addService = (IAddressService)SpringObjectFactory.INSTANCE.getInstance("IAddressService");
			Address delivery =(Address) addService.getByBusinessKey(object.getDeliveryAddress(), context);
			if (delivery != null &&  !delivery.isNullContent())
				object.setDeliveryAddress(delivery);
			else  {
				object.getDeliveryAddress().setCompany(object.getCompany());
				object.getDeliveryAddress().setAddressType(new FiniteValue(CRMConstants.ADDRESS_TYPE.PRIMARY_SHIPPING));
				int pk = GeneralSQLs.getNextPKValue("Addresses") ;
				object.getDeliveryAddress().setId(pk);
			}
				
		}
		return ans;
	}

	private void eliminate0LinesFromReturns(Sales sales)
	{
		Set<SalesLine> returnLines = new LinkedHashSet<SalesLine>();
		sales.getSalesLines().forEach( salesLine ->  {
			if(salesLine.getQty() > 0 )
				  returnLines.add(salesLine) ;
		});
		sales.setSalesLines(returnLines);
	}
	
	@Override
	@Transactional
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		Sales sales = (Sales)object ;
		
		if (Utils.isNull(sales.getBillNumber())) {
			if (!sales.isReturn() ) {
				String bKey = NextUpGenerator.getNextNumber("Sales", context, sales.getDivision());
				sales.setBillNumber(bKey);
			}else {
				String bKey = NextUpGenerator.getNextNumber("SalesReturns", context, sales.getDivision());
				sales.setBillNumber(bKey);
			}
		}
		if (!Utils.isNullSet(sales.getSalesLines())) {
			int pk = GeneralSQLs.getNextPKValue("Sales") ;
			sales.setId(pk);
			for (SalesLine  line : sales.getSalesLines()) {
				int linePK = GeneralSQLs.getNextPKValue( "Sales_Lines") ;
				line.setId(linePK);
				line.setSalesDoc(sales);
				line.setBillNumber(sales.getBillNumber());
			}
		}
		TransactionResult result= super.create(object, context);
		String autoEmail = ConfigurationManager.getConfig(ConfigurationManager.AUTO_EMAIL_RECIEPTS, context);
		if("true".equalsIgnoreCase(autoEmail)) {
			emailInvoice(sales, context);
		}
		String trackString = ConfigurationManager.getConfig(ConfigurationManager.TRACK_INVENTORY, context);
		Boolean track = Utils.getBooleanValue(trackString) ;
		if (track != false ) {
			InventoryUpdateObject invObject = new InventoryUpdateObject();
			invObject.setCompany(sales.getCompany());
			invObject.setContext(context);
			invObject.setDivision(sales.getDivision());
			invObject.setAddition(false);
			invObject.setItemLines(sales.getSalesLines());
			invObject.setAction(BusinessAction.CREATE);
			if(sales.getCustomer() != null) {
				invObject.setAddLoyalty(true);
				invObject.setSalesDoc(sales.getBillNumber());
			}
			CRMMessageSender.sendMessage(invObject);
		}
		return result; 
	}
	
	private void updateInventoryDelta(Sales oldState, Sales newState , CRMContext context)
	{
		InventoryDelta delta = new InventoryDelta();
		Set<SalesLine> oldLines = oldState.getSalesLines();
		Set<SalesLine> newLines = newState.getSalesLines() ;
		if(!Utils.isNullSet(oldLines))
		{
			for (SalesLine line : oldLines) {
				InventoryDeltaLine deltaLine = new InventoryDeltaLine();
				deltaLine.setDivision(oldState.getDivision());
				deltaLine.setSku(line.getSku());
				deltaLine.setQty(line.getQty());
				delta.addLine(deltaLine);
			}
		}
		if(!Utils.isNullSet(newLines)) {
			for (SalesLine line : newLines) {
				InventoryDeltaLine deltaLine = new InventoryDeltaLine();
				deltaLine.setDivision(newState.getDivision());
				deltaLine.setSku(line.getSku());
				deltaLine.setQty(line.getQty() * -1 );
				delta.addLine(deltaLine);
			}
		}
		if(delta.hasChanged() ) {
			delta.setContext(context);
			CRMMessageSender.sendMessage(delta);
		}
	
	}
	

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		Sales sales = (Sales)object ;
		Sales oldObject = (Sales)getById(sales.getPK());
		//Sales oldInvObj = (Sales)oldObject.clone();
		if (!Utils.isNullSet(sales.getSalesLines())) {
			int  ct = 0;
			Iterator it = oldObject.getSalesLines().iterator() ;
			for (SalesLine  line : sales.getSalesLines()) {
				SalesLine oldLine = null ;
				if (it.hasNext()) {
					oldLine= (SalesLine) it.next() ;
				}
				line.setSalesDoc(sales);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "Sales_Lines") ;
					line.setId(linePK);
				}
			}
			while (it.hasNext()) {
				SalesLine oldLine= (SalesLine) it.next() ;
				oldLine.setVoided(true);
				sales.addSalesLine(oldLine);
			}
		}
		TransactionResult result = super.update(object, context);
		updateInventoryDelta(oldObject,sales, context) ;
		return result;
	}

	@Override
	public TransactionResult batchUpdate(List<CRMModelObject> objects,
			CRMContext context) throws CRMDBException {
		return super.batchUpdate(objects, context);
	}

	@Override
	public TransactionResult batchCreate(List<CRMModelObject> objects,
			CRMContext context) throws CRMDBException {
		return super.batchCreate(objects, context);
	}

	@Override
	public int getItemSaleQuantity(Item item, Date from, Date to,Division division) {
		return GeneralSQLs.getItemSoldQty(item.getId(),from,to,(division!=null)?division.getId():-1);
	}
	
	
	

	@Override
	public int getTotalSaleQuantity( Date from, Date to,Division division) {
		// TODO Auto-generated method stub
		return GeneralSQLs.getTotalSoldQty(from,to,division.getId());
	}

	@Override
	public int getSalesManSaleQuantity(User user, Date from, Date to,
			Division division) {
		return GeneralSQLs.getSalesMenSoldQty(user.getUserId(),from,to,division.getId());
	}

	@Override
	public Map getItemSoldQtyByProduct(Product product, Date from, Date to,  Division division , String itemClass) {
		return GeneralSQLs.getItemSoldQtyByProduct(product.getId(), from, to, -1,itemClass);
	}
	
	@Override
	public int getTerritorySaleQuantity(int territory, Date from, Date to,
			Division division) {
		return GeneralSQLs.getTerritorySoldQty(territory, from, to, division.getId());
	}

	
	
	@Override
	public int getBrandSaleQuantity(int brandId, Date from, Date to,
			Division division) {
		// TODO Auto-generated method stub
		return GeneralSQLs.getBrandSoldQty(brandId, from, to, (division!=null)?division.getId():-1);
	}

	@Override
	public int getProductSaleQuantity(int productId, Date from, Date to,
			Division division) {
		// TODO Auto-generated method stub
		return GeneralSQLs.getProductSoldQty(productId, from, to, (division!=null)?division.getId():-1);
	}

	
	
	@Override
	public int getDivisionSaleQuantity(Date from, Date to, Division division) {
		// TODO Auto-generated method stub
		return GeneralSQLs.getDivisionSoldQty(from, to, (division!=null)?division.getId():-1);
	}

	@Override
	public int getCategorySaleQuantity(int categoryId, Date from, Date to,
			Division division) {
		// TODO Auto-generated method stub
		return GeneralSQLs.getCategorySoldQty(categoryId, from, to, (division!=null)?division.getId():-1);
	}

	
	
	
	@Override
	public TransactionResult emailInvoice(Sales sales, CRMContext context) {
		TransactionResult result = new TransactionResult();
		try {
		if(sales.getCustomer() == null || Utils.isNullString(sales.getCustomer().getEmail()))
		{
			result.addError(CRMValidator.getErrorforCode(context.getLocale(), SalesErrorCodes.CUSTOMER_REQUIRED_FOREMAIL));
			result.setResult(Result.FAILURE);
			return result;
			
		}
		String to = sales.getCustomer().getEmail();
		byte [] bytes = printInvoice(sales, context);
		EmailComponent component = CommonUtil.getEmailSession(to) ;
		 MimeMessage message = new MimeMessage(component.getSession());
		 BodyPart messageBodyPart = new MimeBodyPart();
		 messageBodyPart.setContent("<img>", "text/html");
	     message.setFrom(new InternetAddress(component.getFrom()));
	     message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
         message.setSubject("Receipt for Sales Order : Ref - " + sales.getBillNumber());
         String msg =  " Please find the receipt for your purchased in the attachment";
         messageBodyPart.setText(msg);
         Multipart multipart = new MimeMultipart();
         multipart.addBodyPart(messageBodyPart);
         messageBodyPart = new MimeBodyPart();
         FileDataSource source = new FileDataSource(new File(sales.getBillNumber() +"+_receipt.pdf"));
			OutputStream sourceOS = source.getOutputStream();
			sourceOS.write(bytes);
			sourceOS.close();
         messageBodyPart.setDataHandler(new DataHandler(source));
         messageBodyPart.setFileName(sales.getBillNumber() +"+_receipt.pdf");
         multipart.addBodyPart(messageBodyPart);
     //    multipart.addBodyPart(part);
         message.setContent(multipart);
      //   message.setContent(msg, "text/html; charset=utf-8");
         Transport t = component.getSession().getTransport("smtps");
         t.connect(component.getHost(),component.getAuthUser(), component.getAuthPassword());
         t.sendMessage(message, message.getAllRecipients());
		
		}catch(Exception ex)  {
			Logwriter.INSTANCE.error(ex);
			result.addError(CRMValidator.getErrorforCode(context.getLocale(), SalesErrorCodes.EMAIL_FAILED));
			result.setResult(Result.FAILURE);
			return result;
		}
		return result;
	}



	@Override
	public byte[] printInvoice(Sales sales,CRMContext context) {
		try { 
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("TermsAndCond"," Goods once sold would not be taken back");
			parameters.put("FooterNote","Thank you for your purchase");
			parameters.put("HeaderNote","Sales Invoice");
			parameters.put("LeadId", sales.getId());
			Connection connection  = ConnectionCreater.getConnection() ;
			URL resource = this.getClass().getResource("/jaspertemplates/InvoiceFormat_1.jrxml");
			
			JasperDesign jasperDesign = JRXmlLoader.load(resource.getPath());
	        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign); 
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
	        byte[] output = JasperExportManager.exportReportToPdf(jasperPrint); 
	      // JasperViewer.viewReport(jasperPrint); 
	        return output; 
	        
			

		}catch(Exception ex) {
			Logwriter.INSTANCE.error(ex);
		}
		return null;
	}
	
	@Override
	public String generateInvoice(Sales sales,CRMContext context) {
		Externalize externalize = new Externalize();
        try{
        IUserService userService = (IUserService)SpringObjectFactory.INSTANCE.getInstance("IUserService");
        User user = (User)userService.getById(context.getUser());
        VelocityEngine ve = CommonUtil.getVelocityEngine();
        Template t = ve.getTemplate("salesInvoice.vm" );
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("companyName", sales.getCompany().getName());
        velocityContext.put("title", externalize.externalize(context, "Sales_Invoice"));
        velocityContext.put("billField", externalize.externalize(context, "Bill_No"));
        velocityContext.put("dateField", externalize.externalize(context, "Date"));
        velocityContext.put("custField", externalize.externalize(context, "Customer"));
        velocityContext.put("taxField", externalize.externalize(context, "Tax"));
        velocityContext.put("totalField", externalize.externalize(context, "Total"));
        velocityContext.put("deliveryField", externalize.externalize(context, "Delivery_Address"));

        velocityContext.put("billNo", sales.getBillNumber());
        velocityContext.put("saleDate", sales.getSalesDate());
        velocityContext.put("custName", sales.getCustomer()==null?"":sales.getCustomer().getFullName());
        velocityContext.put("address1", sales.getDeliveryAddress()==null?"":sales.getDeliveryAddress().getAddress1());
        velocityContext.put("address2", sales.getDeliveryAddress()==null?"":sales.getDeliveryAddress().getAddress2());
        velocityContext.put("city", sales.getDeliveryAddress()==null?"":sales.getDeliveryAddress().getCity());
        velocityContext.put("pincode", sales.getDeliveryAddress()==null?"":sales.getDeliveryAddress().getZipcode());
        velocityContext.put("phone", sales.getDeliveryAddress()==null?"":sales.getDeliveryAddress().getPhone());
        velocityContext.put("saleDate", sales.getSalesDate());
        velocityContext.put("taxAmount", sales.getTaxAmount());
        velocityContext.put("totalAmount", sales.getNetAmount());
        velocityContext.put("lines", sales.getSalesLines());

        StringWriter writer = new StringWriter();
        t.merge( velocityContext, writer );
        String content=  writer.toString();
        return content;
        
        }catch(Exception ex){
        	Logwriter.INSTANCE.error(ex);
        }

        return "";
		
	}

	private PrintDocument getPrintDocument(Sales sales,CRMContext context) throws Exception{
		PrintDocument printDocument = new PrintDocument();
		Externalize externalize = new Externalize();
		printDocument.setTitle(sales.getCompany().getName());
		printDocument.setSubTitle(externalize.externalize(context, "Sales_Invoice"));
		String billNoField =externalize.externalize(context, "Bill_No");
		String dateField =externalize.externalize(context, "Date");
		PrintHeaderLine headerLine1 = new PrintHeaderLine();
		headerLine1.addPrintField(new PrintField(billNoField,sales.getBillNumber()));
		printDocument.addHeaderLine(headerLine1);
		PrintHeaderLine headerLine2 = new PrintHeaderLine();
		headerLine2.addPrintField(new PrintField(dateField,Utils.dateToString(sales.getSalesDate(),externalize.getDateFormat())));
		printDocument.addHeaderLine(headerLine2);
		if (sales.getDeliveryAddress() != null) {
			PrintHeaderLine deliveryLine  = new PrintHeaderLine();
			deliveryLine.addPrintField(new PrintField("Delivery",""));
		}
		
		return printDocument;
	}
	
	
	
	
}
