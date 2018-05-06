package com.rainbow.crm.product.service;

import java.util.ArrayList;
import java.util.List;











import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractionTransactionService;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.common.Externalize;
import com.rainbow.crm.common.ItemUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.product.dao.ProductAttributeDAO;
import com.rainbow.crm.product.dao.ProductDAO;
import com.rainbow.crm.product.dao.ProductFAQDAO;
import com.rainbow.crm.product.dao.ProductPriceRangeDAO;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.model.ProductAttribute;
import com.rainbow.crm.product.model.ProductFAQ;
import com.rainbow.crm.product.model.ProductFAQSet;
import com.rainbow.crm.product.model.ProductPriceRange;
import com.rainbow.crm.product.validator.ProductErrorCodes;
import com.rainbow.crm.user.model.User;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.model.transaction.TransactionResult.Result;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class ProductFAQService extends AbstractionTransactionService implements IProductFAQService{

	
	
	
	
	@Override
	public ProductAttribute getAttribute(ProductAttribute prodAttribute,
			CRMContext context) {
		if  (prodAttribute != null && prodAttribute.getId() > 0 ) {
			return (ProductAttribute) getProductAttributeDAO().getById(prodAttribute.getId());
		} else if (prodAttribute != null &&  Utils.isNullString(prodAttribute.getAttribute() )) {
			return (ProductAttribute) getByBusinessKey(prodAttribute, context);
		}
		return null;
	}

	@Override
	public List<ProductAttribute> getAllAttributes(Product product,
			CRMContext context) {
		List<ProductAttribute> productAttributes =  getProductAttributeDAO().getByProductId(product.getId()) ;
		return productAttributes;
	}

	@Override
	public ProductFAQSet getByProduct(Product product, CRMContext context) {
		ProductFAQDAO  dao = (ProductFAQDAO) getDAO();
		IProductService productService = (IProductService) SpringObjectFactory.INSTANCE.getInstance("IProductService");
		Company company = CommonUtil.getCompany(context.getLoggedinCompany());
		product = (Product)productService.getByBusinessKey(product, context);
		ProductFAQSet faqSet = new ProductFAQSet();
		List<ProductFAQ> faqs =   dao.getByProductId(product.getId()) ;
		faqSet.setProductFAQs(faqs);
		faqSet.setCompany(company);
		faqSet.setProduct(product);
		
		List<ProductPriceRange > priceRanges =  getPriceRangeDAO().getByProductId(product.getId());
		if(Utils.isNullList(priceRanges)) {
			FiniteValue econ = GeneralSQLs.getFiniteValue(CRMConstants.ITEM_CLASS.ECONOMIC);
			ProductPriceRange econRange = new ProductPriceRange();
			econRange.setItemClass(econ);
			econRange.setCompany(company);
			
			FiniteValue topEndFV = GeneralSQLs.getFiniteValue(CRMConstants.ITEM_CLASS.TOP_END);
			ProductPriceRange topEnd = new ProductPriceRange();
			topEnd.setItemClass(topEndFV);
			topEnd.setCompany(company);
			
			FiniteValue upMedFV = GeneralSQLs.getFiniteValue(CRMConstants.ITEM_CLASS.UPPER_MEDIUM);
			ProductPriceRange upMed = new ProductPriceRange();
			upMed.setItemClass(upMedFV);
			upMed.setCompany(company);
			
			FiniteValue lowMedFV = GeneralSQLs.getFiniteValue(CRMConstants.ITEM_CLASS.LOWER_MEDIUM);
			ProductPriceRange lowMed = new ProductPriceRange();
			lowMed.setItemClass(lowMedFV);
			lowMed.setCompany(company);
			
			faqSet.addProductPriceRange(econRange);
			faqSet.addProductPriceRange(lowMed);
			faqSet.addProductPriceRange(upMed);
			faqSet.addProductPriceRange(topEnd);
		}else {
			faqSet.setProductPriceRanges(priceRanges);
		}
		
		List<ProductAttribute> productAttributes =  getProductAttributeDAO().getByProductId(product.getId()) ;
		faqSet.setProductAttributes(productAttributes);
		return faqSet;
	}

	@Override
	protected String getTableName() {
		return null;
	}

	@Override
	protected ORMDAO getDAO() {
		return (ProductFAQDAO) SpringObjectFactory.INSTANCE.getInstance("ProductFAQDAO");
	}
	
	private ProductPriceRangeDAO  getPriceRangeDAO() 
	{
		return (ProductPriceRangeDAO) SpringObjectFactory.INSTANCE.getInstance("ProductPriceRangeDAO");
	}

	private ProductAttributeDAO  getProductAttributeDAO() 
	{
		return (ProductAttributeDAO) SpringObjectFactory.INSTANCE.getInstance("ProductAttributeDAO");
	}
	
	@Override
	public List<RadsError> adaptfromUI(CRMContext context, ModelObject object) {
		List<RadsError> errors= new ArrayList<RadsError> ();
		Externalize externalize = new Externalize();
		ProductFAQSet productFAQSet = (ProductFAQSet) object ;
		Product product = ItemUtil.getProduct(context, productFAQSet.getProduct());
		if (!Utils.isNullList(productFAQSet.getProductFAQs()) ) {
			productFAQSet.getProductFAQs().forEach(productFAQ ->  {
				if (!productFAQ.isNullContent()) {
				productFAQ.setCompany(CommonUtil.getCompany(context.getLoggedinCompany()));
				if (product == null) {
					errors.add(CRMValidator.getErrorforCode(CommonErrorCodes.FIELD_NOT_VALID,externalize.externalize(context, "Product"))) ;
				}else 
					productFAQ.setProduct(product);
				
				User user = CommonUtil.getUser(context, productFAQ.getAuthor());
				if (user == null) {
					errors.add(CRMValidator.getErrorforCode(CommonErrorCodes.FIELD_NOT_VALID,externalize.externalize(context, "Author"))) ;
				}else
					productFAQ.setAuthor(user);
				}else  {
					productFAQSet.setProductFAQs(null);
				}
			}  );
		}
		
		if (!Utils.isNullList(productFAQSet.getProductAttributes()) ) {
			productFAQSet.getProductAttributes().forEach(productAttribute ->   {
				if (!productAttribute.isNullContent() ) {
					productAttribute.setCompany(CommonUtil.getCompany(context.getLoggedinCompany()));
					if (product == null) {
						errors.add(CRMValidator.getErrorforCode(CommonErrorCodes.FIELD_NOT_VALID,externalize.externalize(context, "Product"))) ;
					}else 
						productAttribute.setProduct(product);
					if (Utils.isNullString(productAttribute.getAttribute())) {
						errors.add(CRMValidator.getErrorforCode(CommonErrorCodes.FIELD_EMPTY,externalize.externalize(context, "Attribute"))) ;
					}
					
					FiniteValue value = GeneralSQLs.getFiniteValue(productAttribute.getValueType().getCode());
					if (value == null)  {
						errors.add(CRMValidator.getErrorforCode(CommonErrorCodes.FIELD_NOT_VALID,externalize.externalize(context, "Value_Type"))) ;
					}else
						productAttribute.setValueType(value);
				}else {
					productFAQSet.setProductAttributes(null);
				}
				
			} );
		}
		
		ProductPriceRange econ =null ;
		ProductPriceRange topEnd = null ;
		ProductPriceRange upMed = null ;
		ProductPriceRange lowMed = null ;
		for (ProductPriceRange productPriceRange :  productFAQSet.getProductPriceRanges()) {
			productPriceRange.setCompany(CommonUtil.getCompany(context.getLoggedinCompany()));
			if (product == null) {
				errors.add(CRMValidator.getErrorforCode(CommonErrorCodes.FIELD_NOT_VALID,externalize.externalize(context, "Product"))) ;
			}else 
				productPriceRange.setProduct(product);
			
			FiniteValue value = GeneralSQLs.getFiniteValueFromDescription(productPriceRange.getItemClass().getDescription());
			if (value == null)  {
				errors.add(CRMValidator.getErrorforCode(CommonErrorCodes.FIELD_NOT_VALID,externalize.externalize(context, "Item_Class"))) ;
			}else {
				if (CRMConstants.ITEM_CLASS.TOP_END.equals(value.getCode())) {
					topEnd = productPriceRange;
					productPriceRange.setMaxPrice(null);
				}else if (CRMConstants.ITEM_CLASS.ECONOMIC.equals(value.getCode())) {
					econ = productPriceRange;
					productPriceRange.setMinPrice(null);
				}else if (CRMConstants.ITEM_CLASS.UPPER_MEDIUM.equals(value.getCode())) {
					upMed = productPriceRange;
				}else if (CRMConstants.ITEM_CLASS.LOWER_MEDIUM.equals(value.getCode())) {
					lowMed = productPriceRange;
				}
				productPriceRange.setItemClass(value);
				if (productPriceRange.getMaxPrice() == null && !CRMConstants.ITEM_CLASS.TOP_END.equals(value.getCode())) 
				{
					errors.add(CRMValidator.getErrorforCode(ProductErrorCodes.MAX_NULL_FORTOPEND)) ;
				} 
				if (productPriceRange.getMinPrice() == null && !CRMConstants.ITEM_CLASS.ECONOMIC.equals(value.getCode())) 
				{
					errors.add(CRMValidator.getErrorforCode(ProductErrorCodes.MIN_NULL_FORECON)) ;
				}
				
			}
		}  
		
		if (Utils.isNullList(errors)) {
			if(econ != null  && lowMed != null && lowMed.getMinPrice() < econ.getMaxPrice()) 
			{
				errors.add(CRMValidator.getErrorforCode(ProductErrorCodes.MIN_SHOULDNOTEXCEED_MAX,externalize.externalize(context,"Lower_Medium"),
						externalize.externalize(context,"Economic"))) ;
			}else if (econ.getMaxPrice() < lowMed.getMinPrice() -1  ){
				errors.add(CRMValidator.getErrorforCode(ProductErrorCodes.GAP_BETWEEN_CLASSES,externalize.externalize(context,"Lower_Medium"),
						externalize.externalize(context,"Economic"))) ;
			}
			if(upMed != null  && lowMed != null && upMed.getMinPrice() < lowMed.getMaxPrice()) 
			{
				errors.add(CRMValidator.getErrorforCode(ProductErrorCodes.MIN_SHOULDNOTEXCEED_MAX,externalize.externalize(context,"Upper_Medium"),
						externalize.externalize(context,"Lower_Medium"))) ;
			}else if (lowMed.getMaxPrice() < upMed.getMinPrice() -1  ){
				errors.add(CRMValidator.getErrorforCode(ProductErrorCodes.GAP_BETWEEN_CLASSES,externalize.externalize(context,"Upper_Medium"),
						externalize.externalize(context,"Lower_Medium"))) ;
			}
			if(upMed != null  && topEnd != null && topEnd.getMinPrice() < upMed.getMaxPrice()) 
			{
				errors.add(CRMValidator.getErrorforCode(ProductErrorCodes.MIN_SHOULDNOTEXCEED_MAX,externalize.externalize(context,"Top_End"),
						externalize.externalize(context,"Upper_Medium"))) ;
			}else if (upMed.getMaxPrice() < topEnd.getMinPrice() -1  ){
				errors.add(CRMValidator.getErrorforCode(ProductErrorCodes.GAP_BETWEEN_CLASSES,externalize.externalize(context,"Top_End"),
						externalize.externalize(context,"Upper_Medium"))) ;
			}
		}
		
		return errors;
	}

	@Override
	public List<RadsError> adaptToUI(CRMContext context, ModelObject object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getTotalRecordCount(CRMContext context, String whereCondition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getById(Object PK) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CRMModelObject getByBusinessKey(CRMModelObject object,
			CRMContext context) {
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
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		ProductFAQSet fset = (ProductFAQSet) object ;
		adaptfromUI(context, fset);
		ProductFAQSet oldObject = getByProduct(fset.getProduct(), context);
		 //batchUpdateFAQS( (fset.getProductFAQs()),context);
		 updateFAQ(oldObject,(fset.getProductFAQs()),context);
	  	 batchUpdatePriceRange(oldObject,(fset.getProductPriceRanges()), context);
	     batchUpdateProductAttributes(oldObject,(fset.getProductAttributes()), context);
	  	 object = getByProduct(fset.getProduct(), context);
	  	 TransactionResult result = new TransactionResult();
	  	 result.setResult(Result.SUCCESS);
	  	 result.setObject(object);
		 return result;
	}

	private TransactionResult updateFAQ(ProductFAQSet oldObject, List<ProductFAQ> objects, CRMContext context)
	{
		List<RadsError> errors  = new ArrayList<RadsError>(); 
		TransactionResult.Result result = TransactionResult.Result.SUCCESS;
		int lineNo = 1 ; 
		try {
			if(oldObject == null ||  Utils.isNullList(oldObject.getProductFAQs())) {
				if(!Utils.isNullList(objects)) {
					for (CRMModelObject object : objects ) {
						int id = GeneralSQLs.getNextPKValue( "Product_FAQs") ;
						((ProductFAQ)object).setId(id);
						((ProductFAQ)object).setLineNumber(lineNo ++ );
						object.setLastUpdateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
						object.setLastUpdateUser(context.getUser());
					}
					getDAO().batchUpdate(objects);
				}
			}else {
				for (ProductFAQ object : oldObject.getProductFAQs() ) {
					ProductFAQ newLine = null ;
					if(!Utils.isNullList(objects)) {
						for  ( ProductFAQ enteredLine : objects )  {
							if (enteredLine.getQuestion().equalsIgnoreCase(object.getQuestion())) {
								newLine = enteredLine;
								object.setAnswer(enteredLine.getAnswer());
								object.setAuthor(enteredLine.getAuthor());
								object.setComments(enteredLine.getComments());
								object.setLineNumber(lineNo ++ );
								objects.remove(enteredLine);
								break ;
							}
						}
						if (newLine == null)
							object.setDeleted(true);
						
						((ProductFAQ)object).setLineNumber(lineNo ++ );
						object.setLastUpdateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
						object.setLastUpdateUser(context.getUser());
					}
				}
				if(!Utils.isNullList(objects)) {
					for (CRMModelObject object : objects ) {
						int id = GeneralSQLs.getNextPKValue( "Product_FAQs") ;
						((ProductFAQ)object).setId(id);
						((ProductFAQ)object).setLineNumber(lineNo ++ );
						object.setLastUpdateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
						object.setLastUpdateUser(context.getUser());
						oldObject.addProductFAQ((ProductFAQ)object);
					}
				}
				
				getDAO().batchUpdate(oldObject.getProductFAQs());
			}
		}catch(DatabaseException ex) {
			RadsError error = CRMValidator.getErrorforCode(context.getLocale(),CRMDBException.ERROR_DIRTY_READ);
			errors.add(error);
			result = TransactionResult.Result.FAILURE ;
			throw new CRMDBException(error) ;
		}
		return new TransactionResult(result,errors);
		
	}
	
	public TransactionResult batchUpdateProductAttributes(ProductFAQSet oldObject ,List<ProductAttribute> objects,
			CRMContext context) {
		List<RadsError> errors  = new ArrayList<RadsError>(); 
		TransactionResult.Result result = TransactionResult.Result.SUCCESS;
		int lineNo = 1 ; 
		try {
			if(oldObject == null ||  Utils.isNullList(oldObject.getProductAttributes())) {
				if(!Utils.isNullList(objects)) {
					for (CRMModelObject object : objects ) {
						int id = GeneralSQLs.getNextPKValue( "Product_Attributes") ;
						((ProductAttribute)object).setId(id);
						object.setLastUpdateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
						object.setLastUpdateUser(context.getUser());
					}
					getPriceRangeDAO().batchUpdate(objects);
				}
			}else {
				for(ProductAttribute prodAttribute : oldObject.getProductAttributes() ) {
					ProductAttribute newLine = null;
					if(!Utils.isNullList(objects)) {
						for  (ProductAttribute enteredRange : objects ) {
							if(enteredRange.getAttribute().equalsIgnoreCase(prodAttribute.getAttribute())) {
								if (prodAttribute.getId() <= 0 ) {
									int id = GeneralSQLs.getNextPKValue( "Product_Attributes") ;
									prodAttribute.setId(id);
									prodAttribute.setProduct(oldObject.getProduct());
								}
								newLine = enteredRange ;
								prodAttribute.setComments(enteredRange.getComments());
								prodAttribute.setValueType(enteredRange.getValueType());
								objects.remove(enteredRange);
								break ;
							}
						}
					}
					if (newLine == null)
						prodAttribute.setDeleted(true);
					
					prodAttribute.setLastUpdateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
					prodAttribute.setLastUpdateUser(context.getUser());
				}
				if(!Utils.isNullList(objects)) {
					for (CRMModelObject object : objects ) {
						int id = GeneralSQLs.getNextPKValue( "Product_Attributes") ;
						((ProductAttribute)object).setId(id);
						object.setLastUpdateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
						object.setLastUpdateUser(context.getUser());
						oldObject.addProductAttribute((ProductAttribute)object);
					}
				}
				getProductAttributeDAO().batchUpdate(oldObject.getProductAttributes());
			}
			
		}catch(DatabaseException ex) {
			RadsError error = CRMValidator.getErrorforCode(context.getLocale(),CRMDBException.ERROR_DIRTY_READ);
			errors.add(error);
			result = TransactionResult.Result.FAILURE ;
			throw new CRMDBException(error) ;
		}
		return new TransactionResult(result,errors);
	}
	
	public TransactionResult batchUpdatePriceRange(ProductFAQSet oldObject ,List<ProductPriceRange> objects,
			CRMContext context) {
		List<RadsError> errors  = new ArrayList<RadsError>(); 
		TransactionResult.Result result = TransactionResult.Result.SUCCESS;
		int lineNo = 1 ; 
		try {
			if(oldObject == null ||  Utils.isNullList(oldObject.getProductPriceRanges())) {
				for (CRMModelObject object : objects ) {
					int id = GeneralSQLs.getNextPKValue( "Product_Price_Ranges") ;
					((ProductPriceRange)object).setId(id);
					object.setLastUpdateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
					object.setLastUpdateUser(context.getUser());
				}
				getPriceRangeDAO().batchUpdate(objects);
			}else {
				for(ProductPriceRange prodPriceRange : oldObject.getProductPriceRanges() ) {
					for  (ProductPriceRange enteredRange : objects ) {
						if(enteredRange.getItemClass().getCode().equalsIgnoreCase(prodPriceRange.getItemClass().getCode())) {
							if (prodPriceRange.getId() <= 0 ) {
								int id = GeneralSQLs.getNextPKValue( "Product_Price_Ranges") ;
								prodPriceRange.setId(id);
								prodPriceRange.setProduct(oldObject.getProduct());
							}
							prodPriceRange.setComments(enteredRange.getComments());
							prodPriceRange.setMinPrice(enteredRange.getMinPrice());
							prodPriceRange.setMaxPrice(enteredRange.getMaxPrice());
						}
					}
				}
				getPriceRangeDAO().batchUpdate(oldObject.getProductPriceRanges());
			}
			
		}catch(DatabaseException ex) {
			RadsError error = CRMValidator.getErrorforCode(context.getLocale(),CRMDBException.ERROR_DIRTY_READ);
			errors.add(error);
			result = TransactionResult.Result.FAILURE ;
			throw new CRMDBException(error) ;
		}
		return new TransactionResult(result,errors);
	}
	
	
	public TransactionResult batchUpdateFAQS(List<? extends CRMModelObject> objects,
			CRMContext context) {
		
		List<RadsError> errors  = new ArrayList<RadsError>(); 
		TransactionResult.Result result = TransactionResult.Result.SUCCESS;
		int lineNo = 1 ; 
		try {
		//	List<ProductFAQ> oldObjects =   getByProduct(product, context);
			for (CRMModelObject object : objects ) {
				if(((ProductFAQ)object).getId() > 0  ) {
					int id = GeneralSQLs.getNextPKValue( "Product_FAQs") ;
					((ProductFAQ)object).setId(id);
				}else {
					
				}
				((ProductFAQ)object).setLineNumber(lineNo ++ );
				object.setLastUpdateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
				object.setLastUpdateUser(context.getUser());
			}
			getDAO().batchUpdate(objects);
		}catch(DatabaseException ex) {
			RadsError error = CRMValidator.getErrorforCode(context.getLocale(),CRMDBException.ERROR_DIRTY_READ);
			errors.add(error);
			result = TransactionResult.Result.FAILURE ;
			throw new CRMDBException(error) ;
		}
		return new TransactionResult(result,errors);
	}
	
	
	@Override
	public TransactionResult batchUpdate(List<CRMModelObject> objects,
			CRMContext context) {
		List<RadsError> errors  = new ArrayList<RadsError>(); 
		TransactionResult.Result result = TransactionResult.Result.SUCCESS;
		try {
			for (CRMModelObject object : objects ) {
				object.setLastUpdateDate(new java.sql.Timestamp(new java.util.Date().getTime()));
				object.setLastUpdateUser(context.getUser());
			}
			getDAO().batchUpdate(objects);
		}catch(DatabaseException ex) {
			RadsError error = CRMValidator.getErrorforCode(context.getLocale(),CRMDBException.ERROR_DIRTY_READ);
			errors.add(error);
			result = TransactionResult.Result.FAILURE ;
			throw new CRMDBException(error) ;
		}
		return new TransactionResult(result,errors);
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

	

	
	
}
