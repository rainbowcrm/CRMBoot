package com.rainbow.crm.item.validator;

import com.rainbow.crm.brand.model.Brand;
import com.rainbow.crm.brand.service.IBrandService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.ItemUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.validator.DivisionErrorCodes;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.IItemService;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.service.IProductService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class ItemValidator extends CRMValidator{

	Item item =null;
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		IItemService  service = (IItemService) SpringObjectFactory.INSTANCE.getInstance("IItemService");
		Item  exist = (Item)service.getByCode(item.getCompany().getId(), item.getCode());
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Item_Code"))) ;
		}
		
		exist = (Item)service.getByName(item.getCompany().getId(), item.getName());
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Item_Name"))) ;
		}
		System.out.println(item.toJSON());
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		IItemService  service = (IItemService) SpringObjectFactory.INSTANCE.getInstance("IItemService");
		Item  exist = (Item)service.getByCode(item.getCompany().getId(), item.getCode());
		if(exist != null && exist.getId() != item.getId()) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Item_Code"))) ;
		}
		exist = (Item)service.getByName(item.getCompany().getId(), item.getName());
		if(exist != null && exist.getId() != item.getId() ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Item_Name"))) ;
		}
		System.out.println("ite json=" + item.toJSON());
	}
	
	protected void checkforErrors(ModelObject object) {
		item = (Item) object;
		if(item.getCode() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Item_Code"))) ;
		}
		if(item.getName() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Name"))) ;
		}
		if(item.getProduct() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Product"))) ;
		}else {
			IProductService service =(IProductService)SpringObjectFactory.INSTANCE.getInstance("IProductService");
			Product product = service.getByName(item.getCompany().getId(),item.getProduct().getName());
			item.setProduct(product);
		}
		if(item.getBrand() == null || item.getBrand().getId() <= 0 ) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Brand"))) ;
		}else {
			IBrandService service = (IBrandService)SpringObjectFactory.INSTANCE.getInstance("IBrandService");
			Brand brand = (Brand)service.getById(item.getBrand().getId());
			if (brand == null ) {
				errors.add(getErrorforCode(CommonErrorCodes.VALUE_NOT_FOUND,externalize.externalize(context, "Brand"))) ;
			}else
				item.setBrand(brand);
		}
		if(item.getUomId() <= 0) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "UOM"))) ;
		}
		if (item.getPurchasePrice() != null && item.getPurchasePrice() <0 ){
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_NOT_NEGATIVE,externalize.externalize(context, "Purchase_Price"))) ;
		}
		if (item.getWholeSalePrice() != null && item.getWholeSalePrice() <0 ){
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_NOT_NEGATIVE,externalize.externalize(context, "Wholesale_Price"))) ;
		}
		if (item.getBreakEvenPrice() != null && item.getBreakEvenPrice() <0 ){
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_NOT_NEGATIVE,externalize.externalize(context, "BreakEven_Price"))) ;
		}
		if (item.getRetailPrice() != null && item.getRetailPrice() <0 ){
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_NOT_NEGATIVE,externalize.externalize(context, "Retail_Price"))) ;
		}else {
			FiniteValue itemClass=  ItemUtil.getItemClass(context, item.getProduct(), item.getRetailPrice());
			if (itemClass != null ) {
				item.setItemClass(itemClass.getCode());
				item.setItemClassDesc(itemClass.getDescription());
			}
		}
		if (item.getMaxPrice() != null && item.getMaxPrice() <0 ){
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_NOT_NEGATIVE,externalize.externalize(context, "Max_Price"))) ;
		}
		if (item.getPromotionPrice() != null && item.getPromotionPrice() <0 ){
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_NOT_NEGATIVE,externalize.externalize(context, "Promotion_Price"))) ;
		}
		if (item.getMaxPrice() != null && item.getMaxPrice() >=0 ){
			double maxPrice = item.getMaxPrice().doubleValue() ;
			if (item.getPromotionPrice() != null && item.getPromotionPrice() >0)
				checkforMaxPriceError(maxPrice, "Promotion_Price", item.getPromotionPrice());
			if (item.getRetailPrice() != null && item.getRetailPrice() >0)
				checkforMaxPriceError(maxPrice, "Retail_Price", item.getRetailPrice());
			if (item.getBreakEvenPrice() != null && item.getBreakEvenPrice() >0)
				checkforMaxPriceError(maxPrice, "BreakEven_Price", item.getBreakEvenPrice());
			if (item.getWholeSalePrice() != null && item.getWholeSalePrice() >0)
				checkforMaxPriceError(maxPrice, "Wholesale_Price", item.getWholeSalePrice());
			if (item.getPurchasePrice() != null && item.getPurchasePrice() >0)
				checkforMaxPriceError(maxPrice, "Purchase_Price", item.getPurchasePrice());
		}
		
	}
	
	private void checkforMaxPriceError (double maxPrice, String prop , double compPrice) {
		if (compPrice > maxPrice) {
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,
					externalize.externalize(context, "Max_Price"),externalize.externalize(context, prop))) ;
		}
	}
	public ItemValidator(CRMContext context) {
		super(context);
	}
	public ItemValidator(){
		
	}
	
	

}
