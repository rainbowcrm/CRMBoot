package com.rainbow.crm.item.validator;

import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.IItemService;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.service.IProductService;
import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class SkuValidator extends CRMValidator{

	Sku sku =null;
	@Override
	protected void checkforCreateErrors(ModelObject object) {
		checkforErrors(object);
		ISkuService  service = (ISkuService) SpringObjectFactory.INSTANCE.getInstance("ISkuService");
		Sku  exist = (Sku)service.getByCode(sku.getCompany().getId(), sku.getCode());
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Item_Code"))) ;
		}
		exist = (Sku)service.getByBarCode(sku.getCompany().getId(), sku.getCode());
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Barcode"))) ;
		}
		exist = (Sku)service.getByName(sku.getCompany().getId(), sku.getName());
		if(exist != null ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Item_Name"))) ;
		}
		System.out.println(sku.toJSON());
	}

	@Override
	protected void checkforUpdateErrors(ModelObject object) {
		checkforErrors(object);
		ISkuService  service = (ISkuService) SpringObjectFactory.INSTANCE.getInstance("ISkuService");
		Sku  exist = (Sku)service.getByCode(sku.getCompany().getId(), sku.getCode());
		if(exist != null && exist.getId() != sku.getId()) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Item_Code"))) ;
		}
		exist = (Sku)service.getByBarCode(sku.getCompany().getId(), sku.getCode());
		if(exist != null && exist.getId() != sku.getId()) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Barcode"))) ;
		}
		exist = (Sku)service.getByName(sku.getCompany().getId(), sku.getName());
		if(exist != null && exist.getId() != sku.getId() ) {
			errors.add(getErrorforCode(CommonErrorCodes.UNIQUE_VAL_EXISTS,externalize.externalize(context, "Item_Name"))) ;
		}
		System.out.println("ite json=" + sku.toJSON());
	}
	
	protected void checkforErrors(ModelObject object) {
		sku = (Sku) object;
		if(sku.getCode() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Item_Code"))) ;
		}
		if(sku.getBarcode() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Barcode"))) ;
		}
		if(sku.getName() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Name"))) ;
		}
		if(sku.getProduct() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Product"))) ;
		}else {
			IProductService service =(IProductService)SpringObjectFactory.INSTANCE.getInstance("IProductService");
			Product product = service.getByName(sku.getCompany().getId(),sku.getProduct().getName());
			sku.setProduct(product);
		}
		if (sku.getItem() == null) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Item"))) ;
		}else {
			IItemService itemService  = (IItemService)SpringObjectFactory.INSTANCE.getInstance("IItemService");
			Item itemObj = itemService.getByName(sku.getCompany().getId(),sku.getItem().getName()) ;
			if (itemObj == null) {
				errors.add(getErrorforCode(CommonErrorCodes.FIELD_NOT_VALID,externalize.externalize(context, "Item"))) ;
			}else 
				sku.setItem(itemObj);
		}
		if(sku.getUomId() <= 0) {
			errors.add(getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "UOM"))) ;
		}
		if (sku.getPurchasePrice() != null && sku.getPurchasePrice() <0 ){
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_NOT_NEGATIVE,externalize.externalize(context, "Purchase_Price"))) ;
		}
		if (sku.getWholeSalePrice() != null && sku.getWholeSalePrice() <0 ){
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_NOT_NEGATIVE,externalize.externalize(context, "Wholesale_Price"))) ;
		}
		if (sku.getBreakEvenPrice() != null && sku.getBreakEvenPrice() <0 ){
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_NOT_NEGATIVE,externalize.externalize(context, "BreakEven_Price"))) ;
		}
		if (sku.getRetailPrice() != null && sku.getRetailPrice() <0 ){
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_NOT_NEGATIVE,externalize.externalize(context, "Retail_Price"))) ;
		}
		if (sku.getMaxPrice() != null && sku.getMaxPrice() <0 ){
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_NOT_NEGATIVE,externalize.externalize(context, "Max_Price"))) ;
		}
		if (sku.getPromotionPrice() != null && sku.getPromotionPrice() <0 ){
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_NOT_NEGATIVE,externalize.externalize(context, "Promotion_Price"))) ;
		}
		if (sku.getMaxPrice() != null && sku.getMaxPrice() >=0 ){
			double maxPrice = sku.getMaxPrice().doubleValue() ;
			if (sku.getPromotionPrice() != null && sku.getPromotionPrice() >0)
				checkforMaxPriceError(maxPrice, "Promotion_Price", sku.getPromotionPrice());
			if (sku.getRetailPrice() != null && sku.getRetailPrice() >0)
				checkforMaxPriceError(maxPrice, "Retail_Price", sku.getRetailPrice());
			if (sku.getBreakEvenPrice() != null && sku.getBreakEvenPrice() >0)
				checkforMaxPriceError(maxPrice, "BreakEven_Price", sku.getBreakEvenPrice());
			if (sku.getWholeSalePrice() != null && sku.getWholeSalePrice() >0)
				checkforMaxPriceError(maxPrice, "Wholesale_Price", sku.getWholeSalePrice());
			if (sku.getPurchasePrice() != null && sku.getPurchasePrice() >0)
				checkforMaxPriceError(maxPrice, "Purchase_Price", sku.getPurchasePrice());
		}
		
	}
	
	private void checkforMaxPriceError (double maxPrice, String prop , double compPrice) {
		if (compPrice > maxPrice) {
			errors.add(getErrorforCode(CommonErrorCodes.SHOULD_BE_GREATER_THAN,
					externalize.externalize(context, "Max_Price"),externalize.externalize(context, prop))) ;
		}
	}
	public SkuValidator(CRMContext context) {
		super(context);
	}
	public SkuValidator(){
		
	}


}
