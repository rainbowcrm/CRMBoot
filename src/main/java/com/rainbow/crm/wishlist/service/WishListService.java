package com.rainbow.crm.wishlist.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.AbstractionTransactionService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.Externalize;
import com.rainbow.crm.common.ItemUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.messaging.CRMMessageSender;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.customer.service.ICustomerService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.inventory.model.InventoryUpdateObject;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.product.validator.ProductValidator;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.wishlist.dao.WishListDAO;
import com.rainbow.crm.wishlist.model.WishList;
import com.rainbow.crm.wishlist.model.WishListLine;
import com.rainbow.crm.wishlist.validator.WishListErrorCodes;
import com.rainbow.crm.wishlist.validator.WishListValidator;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.vendor.model.Vendor;
import com.rainbow.crm.vendor.service.IVendorService;
import com.rainbow.framework.nextup.NextUpGenerator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

@Transactional
public class WishListService extends AbstractionTransactionService implements IWishListService{

	@Override
	protected String getTableName() {
		return "WishList";
	}
	
	@Override
	public List<WishListLine> getWishesforItem(Item item, CRMContext context,
			Date fromDate, Date toDate) {
		WishListDAO dao = (WishListDAO) getDAO();
		return dao.getOpenWishesPerItem(item, fromDate, toDate);
	}
	
	@Override
	public List<WishListLine> getWishesforCustomer(Customer customer,
			CRMContext context, Date fromDate, Date toDate) {
		WishListDAO dao = (WishListDAO) getDAO();
		return dao.getOpenWishesPerCustomer(customer, fromDate, toDate);
	}

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("WishList", from, to, whereCondition, context,sortCriteria);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((WishList)object).setCompany(company);
		WishListValidator validator = new WishListValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((WishList)object).setCompany(company);
		WishListValidator validator = new WishListValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	protected ORMDAO getDAO() {
//	return new WishListDAO();
	return (WishListDAO) SpringObjectFactory.INSTANCE.getInstance("WishListDAO");
	}

	private void updateSalesLead (Map<Integer, SalesLead> custLeadsMap, CRMContext context) {
		ISalesLeadService salesLeadService = (ISalesLeadService) SpringObjectFactory.INSTANCE.getInstance("ISalesLeadService");
		Iterator keyIterator = custLeadsMap.keySet().iterator() ;
		while(keyIterator.hasNext()) {
			Integer key =(Integer) keyIterator.next() ;
			SalesLead  lead =  custLeadsMap.get(key);
			List<RadsError> error= salesLeadService.validateforCreate(lead, context);
			if (!Utils.isNull(error))
				salesLeadService.create(lead, context);
		}
	}
	
	public void generateSalesLead(InventoryUpdateObject invObject, String reason) {
		Map<Integer, SalesLead> custLeadsMap = new HashMap<Integer, SalesLead>();
		WishListDAO dao = (WishListDAO)getDAO();
		for (CRMItemLine invLine :  invObject.getItemLines()) {
			List<WishListLine> wishListLines = null;
			if ("AVLBLTY".equals(reason))
				wishListLines  = dao.getWishesPerSkuByInventory(invLine.getSku(), invObject.getDivision(), invLine.getQty(),reason) ;
			else if ("LOWPRICE".equals(reason))   {
				String toleranceLevel = ConfigurationManager.getConfig(ConfigurationManager.TOLERANCE_WISHLIST_SALESLEAD, invLine.getCompany().getId()) ;
				double retailPrice = (invLine.getSku()!=null)? ItemUtil.getRetailPrice(invLine.getSku()):ItemUtil.getRetailPrice(invLine.getItem()) ;
				double tolerantDiffe  = retailPrice * Double.parseDouble(toleranceLevel) /100;
				if(invLine.getSku() != null)
					wishListLines  = dao.getWishesPerSkuByPrice(invLine.getSku(),  retailPrice +  tolerantDiffe,reason) ;
				else if ( invLine.getItem() != null)
				wishListLines  = dao.getWishesPerItemByPrice(invLine.getItem(),  retailPrice +  tolerantDiffe,reason) ;
			}
			if (!Utils.isNullList(wishListLines))  {
				for (WishListLine line : wishListLines)  {
					WishList wishlist = (WishList)dao.getById(line.getWishListDoc().getId());
					SalesLead lead = null;
					if (custLeadsMap.containsKey(new Integer (wishlist.getCustomer().getId()))){
						lead =custLeadsMap.get(new Integer (wishlist.getCustomer().getId()));
					}else {
						lead = new SalesLead();
						lead.setCompany(line.getCompany());
						lead.setDivision(line.getDivision());
						if(wishlist.getAssociate() != null ) {
							lead.setSalesAssociate(wishlist.getAssociate().getUserId());
						}
						lead.setCustomer(wishlist.getCustomer());
						lead.setReleasedDate(new java.util.Date());
						custLeadsMap.put(wishlist.getCustomer().getId(),lead);
					}
					SalesLeadLine leadLine = new SalesLeadLine();
					leadLine.setCompany(line.getCompany());
					leadLine.setDivision(lead.getDivision());
					leadLine.setSku(line.getSku());
					leadLine.setQty(line.getQty());
					leadLine.setPrice(ItemUtil.getRetailPrice(line.getSku()));
					leadLine.setNegotiatedPrice(ItemUtil.getRetailPrice(line.getSku()));
					leadLine.setSalesLeadDoc(lead);
					leadLine.getTemporaryProperties().put("wishListLine",line );
					lead.addSalesLeadLine(leadLine);
					wishlist.setSalesLeadGenerated(true);
					update(wishlist,invObject.getContext());
				}
			}
		}
		updateSalesLead(custLeadsMap, invObject.getContext());
	}
	
	
	
	@Override
	public List<RadsError> adaptfromUI(CRMContext context, ModelObject object) {
		return adaptfromUI(context,(WishList) object);
	}

	private List<RadsError> adaptfromUI(CRMContext context,WishList object) {
		ICompanyService compService = (ICompanyService) SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		object.setCompany(company);
				
		List<RadsError> ans = new ArrayList<RadsError>();
		
		
		if (object.getDivision() != null) {
			int divisionId  = object.getDivision().getId() ;
			IDivisionService divisionService =(IDivisionService) SpringObjectFactory.INSTANCE.getInstance("IDivisionService");
			Division division = null;
			if (divisionId > 0 )
				division = (Division)divisionService.getById(divisionId);
			else
				division  = (Division)divisionService.getByBusinessKey(object.getDivision(), context);
			if(division == null){
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), WishListErrorCodes.FIELD_NOT_VALID , "Division"));
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
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), WishListErrorCodes.FIELD_NOT_VALID , "Customer"));
		}
		if(object.getAssociate() != null) {
			User associate  = CommonUtil.getUser(context, object.getAssociate());
			if(associate == null) {
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), WishListErrorCodes.FIELD_NOT_VALID , "Associate"));
			}
			object.setAssociate(associate);
		}
		Externalize externalize = new Externalize(); ;
		
		if(!Utils.isNullSet(object.getWishListLines())){
			int lineNo=1;
			for (WishListLine line: object.getWishListLines()) {
				line.setCompany(company);
				line.setDocNumber(object.getDocNumber());
				line.setDivision(object.getDivision());
				line.setLineNumber(lineNo ++);
				if(line.getSku() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), WishListErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Item")));
				}else {
					String itemName = line.getSku().getName() ;
					ISkuService itemService = (ISkuService)SpringObjectFactory.INSTANCE.getInstance("ISkuService");
					Sku item = itemService.getByName(object.getCompany().getId(), itemName);
					line.setSku(item);
				}
			}
		}
		return ans;
	}

	@Override
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		WishList wishList = (WishList)object ;
		if (Utils.isNull(wishList.getDocNumber())) {
			String bKey = NextUpGenerator.getNextNumber("WishList", context, wishList.getDivision());
			wishList.setDocNumber(bKey);
		}
		if (!Utils.isNullSet(wishList.getWishListLines())) {
			int pk = GeneralSQLs.getNextPKValue("WishList") ;
			wishList.setId(pk);
			for (WishListLine  line : wishList.getWishListLines()) {
				int linePK = GeneralSQLs.getNextPKValue( "WishList_Lines") ;
				line.setId(linePK);
				line.setWishListDoc(wishList);
				line.setDocNumber(wishList.getDocNumber());
				line.setSalesLead(wishList.getSalesLead());
			}
		}
		TransactionResult result= super.create(object, context);
		return result; 
	}

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		WishList wishList = (WishList)object ;
		WishList oldObject = (WishList)getById(wishList.getPK());
		//WishList oldInvObj = (WishList)oldObject.clone();
		if (!Utils.isNullSet(wishList.getWishListLines())) {
			int  ct = 0;
			Iterator it = oldObject.getWishListLines().iterator() ;
			for (WishListLine  line : wishList.getWishListLines()) {
				WishListLine oldLine = null ;
				if (it.hasNext()) {
					oldLine= (WishListLine) it.next() ;
				}
				line.setWishListDoc(wishList);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "WishList_Lines") ;
					line.setId(linePK);
				}
			}
			while (it.hasNext()) {
				WishListLine oldLine= (WishListLine) it.next() ;
				oldLine.setVoided(true);
				wishList.addWishListLine(oldLine);
			}
		}
		return super.update(object, context);
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
	public int getItemSaleQuantity(Sku item, Date from, Date to,Division division) {
		//WishListDAO dao = (WishListDAO)getDAO() ;
		return GeneralSQLs.getItemSoldQty(item.getId(),from,to,division.getId());
	}
	
	
	
	
	
}
