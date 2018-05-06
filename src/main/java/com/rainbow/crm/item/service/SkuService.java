package com.rainbow.crm.item.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.messaging.CRMMessageSender;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.inventory.model.InventoryUpdateObject;
import com.rainbow.crm.item.dao.SkuDAO;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.validator.ItemValidator;
import com.rainbow.crm.item.validator.SkuValidator;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class SkuService extends AbstractService implements ISkuService {

	
	@Override
	protected String getTableName() {
		return "Sku";
	}
	

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		 return super.listData("Sku", from, to, whereCondition, context,sortCriteria);

	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Sku)object).setCompany(company);
		SkuValidator validator = new SkuValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Sku)object).setCompany(company);
		SkuValidator validator = new SkuValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	public Sku getByCode(int company, String code) {
		return ((SkuDAO)getDAO()).findByCode(company, code);
	}

	@Override
	public Sku getByBarCode(int company, String barcode) {
		return ((SkuDAO)getDAO()).findByBarCode(company, barcode);
	}
	
		
	@Override
	public Sku getByName(int company, String name) {
		return ((SkuDAO)getDAO()).findByName(company, name);
	}

	@Override
	protected ORMDAO getDAO() {
		return (SkuDAO) SpringObjectFactory.INSTANCE.getInstance("SkuDAO");
	}

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		boolean triggerWishList = false;
		Sku oldObject =(Sku)getById(((Sku)object).getId());
		Sku curObject = (Sku) object ;
		if (oldObject.getRetailPrice()!=null && curObject.getRetailPrice() != null &&  curObject.getRetailPrice() < oldObject.getRetailPrice() ) {
			triggerWishList = true ;
		}
		TransactionResult result= super.update(object, context);
		if (triggerWishList) {
			InventoryUpdateObject invObject = new InventoryUpdateObject();
			invObject.setCompany(curObject.getCompany());
			invObject.setContext(context);
			invObject.setDivision(null);
			invObject.setAddition(true);
			CRMItemLine itemLine = new CRMItemLine();
			itemLine.setCompany(curObject.getCompany());
			itemLine.setSku(curObject);
			itemLine.setQty(0);
			Set newSet = new LinkedHashSet ();
			newSet.add(itemLine);
			invObject.setItemLines(newSet);
			CRMMessageSender.sendMessage(invObject);
		}
		return result;
	}

	@Override
	public TransactionResult batchUpdate(List<CRMModelObject> objects,
			CRMContext context) throws CRMDBException {
		
		return super.batchUpdate(objects, context);
	}

	@Override
	public List<Sku> getAllByProduct(int company, int productId) {
		return ((SkuDAO)getDAO()).getAllByProduct(company, productId);
	}


	@Override
	public List<Sku> getAllByItem(int company, int itemId) {
		// TODO Auto-generated method stub
		return ((SkuDAO)getDAO()).getAllByItem(company, itemId);
	}
	
	
	
	
	


}
