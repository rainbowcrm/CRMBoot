package com.rainbow.crm.item.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;







import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.common.Externalize;
import com.rainbow.crm.common.ItemUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.item.dao.ItemAttributeDAO;
import com.rainbow.crm.item.dao.ItemDAO;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.ItemAttribute;
import com.rainbow.crm.item.model.ItemAttributeSet;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.validator.ItemAttributeErrorCodes;
import com.rainbow.crm.product.model.ProductAttribute;
import com.rainbow.crm.product.model.ProductFAQ;
import com.rainbow.crm.product.service.IProductFAQService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class ItemAttributeService extends AbstractService implements IItemAttributeService{

	@Override
	public List<RadsError> adaptfromUI(CRMContext context, ModelObject object) {
		List<RadsError> errors= new ArrayList<RadsError> ();
		Externalize externalize = new Externalize();
		Company company = CommonUtil.getCompany(context.getLoggedinCompany());
		IProductFAQService productFAQService  = (IProductFAQService)SpringObjectFactory.INSTANCE.getInstance("IProductFAQService");
		ItemAttributeSet itemAttributeSet = (ItemAttributeSet)object ;
		Item item = ItemUtil.getItem(context, itemAttributeSet.getItem());
		itemAttributeSet.setItem(item);
		itemAttributeSet.getAttributes().forEach(itemAttribute -> {
			itemAttribute.setCompany(company);
			ProductAttribute attribute = productFAQService.getAttribute(itemAttribute.getAttribute(), context);
			itemAttribute.setAttribute(attribute);
			itemAttribute.setItem(item);
			if (Utils.isNullString(itemAttribute.getValue())) {
				errors.add(CRMValidator.getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Attribute"))) ;
			}
			
		});
		validate(itemAttributeSet.getAttributes(),context,errors, externalize);
		return errors;
	}

	private void  validate(List<ItemAttribute> itemAttributes,CRMContext context,List<RadsError> errors , Externalize externalize)
	{
		Set<Integer> prodAttributes = new LinkedHashSet<Integer> ();
		for  (ItemAttribute  itemAttribute : itemAttributes)
		{
			if(prodAttributes.contains(itemAttribute.getAttribute().getId())) {
				errors.add(CRMValidator.getErrorforCode(ItemAttributeErrorCodes.REPEATING_ATTRIBUTES,itemAttribute.getAttribute().getAttribute())) ;
			} else {
				prodAttributes.add(itemAttribute.getAttribute().getId());
			}
			if  ( "NUMER".equals(itemAttribute.getAttribute().getValueType().getCode())){
				if(!StringUtils.isNumeric(itemAttribute.getValue()))
					errors.add(CRMValidator.getErrorforCode(ItemAttributeErrorCodes.VALUE_MISMATCH,
							itemAttribute.getAttribute().getAttribute(), externalize.externalize(context, "Numeric"))) ;
			} else if  ( "BOOL".equals(itemAttribute.getAttribute().getValueType().getCode())){
				if(!("true".equalsIgnoreCase(itemAttribute.getValue()) || "false".equalsIgnoreCase(itemAttribute.getValue()))) 
						errors.add(CRMValidator.getErrorforCode(ItemAttributeErrorCodes.VALUE_MISMATCH,
								itemAttribute.getAttribute().getAttribute(), externalize.externalize(context, "Boolean"))) ;
			}
		}
	}
	
	@Override
	public List<RadsError> adaptToUI(CRMContext context, ModelObject object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getById(Object PK) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	private TransactionResult updateSkus(ItemAttributeSet oldObject , ItemAttributeSet newObject,CRMContext context )
	{
		List<RadsError> errors  = new ArrayList<RadsError>(); 
		TransactionResult.Result result = TransactionResult.Result.SUCCESS;
		ISkuService skuService  = (ISkuService)SpringObjectFactory.INSTANCE.getInstance("ISkuService");
		try {
			if(oldObject == null ||  Utils.isNullList(oldObject.getSkuVariants())) {
				for (CRMModelObject object : newObject.getSkuVariants() ) {
					Sku newSku = (Sku) object;
					newSku.setItem(newObject.getItem());
					newSku.setCompany(newObject.getCompany());
					skuService.create(newSku, context);
				}
				//getDAO().batchUpdate(objects);
			}else {
				for (Sku object : oldObject.getSkuVariants() ) {
					Sku newLine = null ;
					for  ( Sku enteredLine : newObject.getSkuVariants() )  {
						if (enteredLine.getId() == object.getId()) {
							newLine = enteredLine;
							newObject.getSkuVariants().remove(enteredLine);
							break ;
						}
					}
					if (newLine == null)
						object.setDeleted(true);
					
					object.setLastUpdateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
					object.setLastUpdateUser(context.getUser());
					skuService.update(object, context);
					
				}
				for (CRMModelObject object : newObject.getSkuVariants() ) {
					object.setLastUpdateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
					object.setLastUpdateUser(context.getUser());
					skuService.update(object, context);
					//oldObject.addAttribute((ItemAttribute)object);
				}
				
				//getDAO().batchUpdate(oldObject.getAttributes());
			}
		}catch(DatabaseException ex) {
			RadsError error = CRMValidator.getErrorforCode(context.getLocale(),CRMDBException.ERROR_DIRTY_READ);
			errors.add(error);
			result = TransactionResult.Result.FAILURE ;
			throw new CRMDBException(error) ;
		}
		return new TransactionResult(result,errors);
		
	}

	
	
	private TransactionResult updateAttributes(ItemAttributeSet oldObject , List<ItemAttribute> objects,CRMContext context )
	{
		List<RadsError> errors  = new ArrayList<RadsError>(); 
		TransactionResult.Result result = TransactionResult.Result.SUCCESS;
		try {
			if(oldObject == null ||  Utils.isNullList(oldObject.getAttributes())) {
				for (CRMModelObject object : objects ) {
					int id = GeneralSQLs.getNextPKValue( "Item_Attributes") ;
					((ItemAttribute)object).setId(id);
					object.setLastUpdateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
					object.setLastUpdateUser(context.getUser());
				}
				getDAO().batchUpdate(objects);
			}else {
				for (ItemAttribute object : oldObject.getAttributes() ) {
					ItemAttribute newLine = null ;
					for  ( ItemAttribute enteredLine : objects )  {
						if (enteredLine.getAttribute().getId() == object.getAttribute().getId()) {
							newLine = enteredLine;
							object.setValue(enteredLine.getValue());
							object.setComments(enteredLine.getComments());
							objects.remove(enteredLine);
							break ;
						}
					}
					if (newLine == null)
						object.setDeleted(true);
					
					object.setLastUpdateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
					object.setLastUpdateUser(context.getUser());
					
				}
				for (CRMModelObject object : objects ) {
					int id = GeneralSQLs.getNextPKValue( "Item_Attributes") ;
					((ItemAttribute)object).setId(id);
					object.setLastUpdateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
					object.setLastUpdateUser(context.getUser());
					oldObject.addAttribute((ItemAttribute)object);
				}
				
				getDAO().batchUpdate(oldObject.getAttributes());
			}
		}catch(DatabaseException ex) {
			RadsError error = CRMValidator.getErrorforCode(context.getLocale(),CRMDBException.ERROR_DIRTY_READ);
			errors.add(error);
			result = TransactionResult.Result.FAILURE ;
			throw new CRMDBException(error) ;
		}
		return new TransactionResult(result,errors);
		
	}

	
	
	@Override
	public ItemAttributeSet getByItem(Item item, CRMContext context) {
		// TODO Auto-generated method stub
		ItemAttributeSet attributeSet = new ItemAttributeSet();
		IItemService itemService = (IItemService)SpringObjectFactory.INSTANCE.getInstance("IItemService");
		item = ItemUtil.getItem(context, item);
		if (item !=null) {
			List<ItemAttribute> attributes = itemService.getAllItemAttributes(item, context);
			attributeSet.setAttributes(attributes);
			attributeSet.setItem(item);
			attributeSet.setCompany(item.getCompany());
		}
		
		ISkuService skuService = (ISkuService)SpringObjectFactory.INSTANCE.getInstance("ISkuService");
		List<Sku> skuVariants = skuService.getAllByItem(item.getCompany().getId(), item.getId());
		attributeSet.setSkuVariants(skuVariants);
		
		return attributeSet ;
	}

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		ItemAttributeSet newObject = (ItemAttributeSet)object;
		ItemAttributeSet oldObject=  getByItem(newObject.getItem(), context);
		
		return updateAttributes(oldObject,newObject.getAttributes(),context);
	}

	@Override
	protected ORMDAO getDAO() {
		return (ItemAttributeDAO) SpringObjectFactory.INSTANCE.getInstance("ItemAttributeDAO"); 
		
	}

	
}
