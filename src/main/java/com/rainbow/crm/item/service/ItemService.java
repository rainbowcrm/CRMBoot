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
import com.rainbow.crm.item.dao.ItemAttributeDAO;
import com.rainbow.crm.item.dao.ItemDAO;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.ItemAttribute;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.validator.ItemValidator;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;

public class ItemService extends AbstractService implements IItemService{

	@Override
	protected String getTableName() {
		return "Item";
	}

	
	
	
	@Override
	public List<ItemAttribute> getAllItemAttributes(Item item,
			CRMContext conteext) {
		// TODO Auto-generated method stub
		List<ItemAttribute> itemAttributes = getItemAttributeDAO().getByItemId(item.getId());
		return itemAttributes;
	}




	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		 return super.listData("Item", from, to, whereCondition, context,sortCriteria);

	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Item)object).setCompany(company);
		ItemValidator validator = new ItemValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Item)object).setCompany(company);
		ItemValidator validator = new ItemValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	public Item getByCode(int company, String code) {
		return ((ItemDAO)getDAO()).findByCode(company, code);
	}

	
		
	@Override
	public Item getByName(int company, String name) {
		return ((ItemDAO)getDAO()).findByName(company, name);
	}

	private ItemAttributeDAO  getItemAttributeDAO()
	{
		return (ItemAttributeDAO) SpringObjectFactory.INSTANCE.getInstance("ItemAttributeDAO");
	}
	@Override
	protected ORMDAO getDAO() {
		return (ItemDAO) SpringObjectFactory.INSTANCE.getInstance("ItemDAO");
	}

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		boolean triggerWishList = false;
		Item oldObject =(Item)getById(((Item)object).getId());
		Item curObject = (Item) object ;
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
			itemLine.setItem(curObject);
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
	public List<Item> getAllByProduct(int company, int productId) {
		return ((ItemDAO)getDAO()).getAllByProduct(company, productId);
	}

}
