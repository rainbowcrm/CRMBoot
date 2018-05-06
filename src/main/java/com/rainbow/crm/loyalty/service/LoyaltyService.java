
package com.rainbow.crm.loyalty.service;

import java.util.Iterator;
import java.util.List;














import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.customer.service.ICustomerService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.service.ISalesService;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.rainbow.crm.loyalty.dao.LoyaltyDAO;
import com.rainbow.crm.loyalty.model.Loyalty;
import com.rainbow.crm.loyalty.validator.LoyaltyValidator;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class LoyaltyService extends AbstractService implements ILoyaltyService{

	
	@Override
	protected String getTableName() {
		return "Loyalty";
	}
	
	
	

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Loyalty", from, to, whereCondition, context,sortCriteria);
	}

	
	
	@Override
	public Loyalty getBySalesBill(String billNumber, CRMContext context) {
		LoyaltyDAO dao  =(LoyaltyDAO)getDAO();
		return dao.findBySalesBill(context.getLoggedinCompany(), billNumber, false) ;	
	}

	@Override
	public void editLoyalty(String salesDoc,CRMContext context) {
		ISalesService salesService = (ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		Sales sales = new Sales();
		sales.setBillNumber(salesDoc);
		sales = (Sales) salesService.getByBusinessKey(sales, context);
		Loyalty existingRecord = getBySalesBill(salesDoc, context);
		existingRecord.setPoints( existingRecord.getPoints() * -1 ) ;
		addToCustomerRecords(existingRecord, context);
		if(sales.getCustomer() == null) return;
		Double amount  = sales.getNetAmount() ;
		String amountforLoyaltySTR = ConfigurationManager.getConfig(ConfigurationManager.SLS_AMOUNT_UNIT_LOYALTY,context);
		if (Utils.isPositiveInt(amountforLoyaltySTR))   {
			Double  amountforLoyalty = Double.parseDouble(amountforLoyaltySTR);
			Double loyatyforSale = amount/amountforLoyalty;
			existingRecord.setDivision(sales.getDivision());
			existingRecord.setCustomer(sales.getCustomer());
			if(sales.isReturn())  {
				existingRecord.setPoints(loyatyforSale *  -1 );
			}else
				existingRecord.setPoints(loyatyforSale);
			existingRecord.setRedeemed(false);
			update(existingRecord, context);
			addToCustomerRecords(existingRecord,context);
		}
	}

	@Override
	public void addToLoyalty(String salesDoc,CRMContext context) {
		ISalesService salesService = (ISalesService)SpringObjectFactory.INSTANCE.getInstance("ISalesService");
		Sales sales = new Sales();
		sales.setBillNumber(salesDoc);
		sales = (Sales) salesService.getByBusinessKey(sales, context);
		if(sales.getCustomer() == null) return;
		
		Double amount  = sales.getNetAmount() ;
		String amountforLoyaltySTR = ConfigurationManager.getConfig(ConfigurationManager.SLS_AMOUNT_UNIT_LOYALTY,context);
		if (Utils.isPositiveInt(amountforLoyaltySTR))   {
			Double  amountforLoyalty = Double.parseDouble(amountforLoyaltySTR);
			Double loyatyforSale = amount/amountforLoyalty;
			Loyalty loyalty = new Loyalty();
			loyalty.setCompany(sales.getCompany());
			loyalty.setDivision(sales.getDivision());
			loyalty.setCustomer(sales.getCustomer());
			loyalty.setSales(sales);
			if(sales.isReturn())  {
				loyalty.setPoints(loyatyforSale *  -1 );
			}else
				loyalty.setPoints(loyatyforSale);
			if (sales.getLoyaltyDiscount() ==null ||  sales.getLoyaltyDiscount() < 0 )
				loyalty.setRedeemed(false);
			else {
				loyalty.setRedeemed(true);
				double redeemedLoyalty = sales.getLoyaltyRedeemed();
				loyalty.setPoints(loyalty.getPoints() - redeemedLoyalty);
			}
			create(loyalty, context);
			addToCustomerRecords(loyalty,context);
		}
	}
	
	
	private void addToCustomerRecords(Loyalty loyalty,CRMContext context)
	{
		ICustomerService service = (ICustomerService) SpringObjectFactory.INSTANCE.getInstance("ICustomerService");
		Customer customer = (Customer)service.getById(loyalty.getCustomer().getId());
		customer.setLoyaltyPoint((customer.getLoyaltyPoint()==null?0:customer.getLoyaltyPoint().doubleValue()) +  loyalty.getPoints());
		service.update(customer, context) ;
		
	}

	
	
	@Override
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		Loyalty loyalty = (Loyalty) object ;
		
		return super.create(object, context);
	}

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		return super.update(object, context);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Loyalty)object).setCompany(company);
		LoyaltyValidator validator = new LoyaltyValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Loyalty)object).setCompany(company);
		LoyaltyValidator validator = new LoyaltyValidator(context);
		return validator.validateforUpdate(object);
	}


	

	@Override
	protected ORMDAO getDAO() {
		return (LoyaltyDAO) SpringObjectFactory.INSTANCE.getInstance("LoyaltyDAO");
	}

	
	


	
	

}
