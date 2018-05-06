package com.rainbow.crm.corpsalesperiod.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.brand.model.Brand;
import com.rainbow.crm.brand.service.IBrandService;
import com.rainbow.crm.category.model.Category;
import com.rainbow.crm.category.service.ICategoryService;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.AbstractionTransactionService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.Externalize;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.messaging.CRMMessageSender;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.inventory.model.InventoryUpdateObject;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.IItemService;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.service.IProductService;
import com.rainbow.crm.corpsalesperiod.dao.CorpSalesPeriodDAO;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriod;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodBrand;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodCategory;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodDivision;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodLine;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodProduct;
import com.rainbow.crm.corpsalesperiod.validator.CorpSalesPeriodErrorCodes;
import com.rainbow.crm.corpsalesperiod.validator.CorpSalesPeriodValidator;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.territory.service.ITerritoryService;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.rainbow.framework.nextup.NextUpGenerator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

@Transactional
public class CorpSalesPeriodService extends AbstractionTransactionService implements ICorpSalesPeriodService{

	
	@Override
	protected String getTableName() {
		return "CorpSalesPeriod";
	}
	

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("CorpSalesPeriod", from, to, whereCondition, context,sortCriteria);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((CorpSalesPeriod)object).setCompany(company);
		CorpSalesPeriodValidator validator = new CorpSalesPeriodValidator(context);
		List<RadsError> errors = validator.validateforCreate(object);
		if(Utils.isNullList(errors) &&  errors.size() >1 )
			addBlankRowBack((CorpSalesPeriod) object);
		return errors;
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((CorpSalesPeriod)object).setCompany(company);
		CorpSalesPeriodValidator validator = new CorpSalesPeriodValidator(context);
		List<RadsError> errors = validator.validateforUpdate(object);
		if(Utils.isNullList(errors) &&  errors.size() >1 )
			addBlankRowBack((CorpSalesPeriod) object);
		return errors;
	}

	@Override
	protected ORMDAO getDAO() {
//	return new CorpSalesPeriodDAO();
	return (CorpSalesPeriodDAO) SpringObjectFactory.INSTANCE.getInstance("CorpSalesPeriodDAO");
	}

	
	
	@Override
	public List<RadsError> adaptfromUI(CRMContext context, ModelObject object) {
		return adaptfromUI(context, (CorpSalesPeriod)object);
	}

	private List<RadsError> adaptfromUI(CRMContext context,CorpSalesPeriod object) {
		ICompanyService compService = (ICompanyService) SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		object.setCompany(company);
				
		List<RadsError> ans = new ArrayList<RadsError>();
		
		Externalize externalize = new Externalize(); ;
		exlcudeBlankRows(object);
		
		if(!Utils.isNullSet(object.getCorpSalesPeriodLines())){
			int lineNo=1;
			for (CorpSalesPeriodLine line: object.getCorpSalesPeriodLines()) {
				line.setCompany(company);
				line.setPeriod(object.getPeriod());
				line.setLineNumber(lineNo ++);
				if(line.getItem() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), CorpSalesPeriodErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Item")));
				}else {
					String itemName = line.getItem().getName() ;
					IItemService itemService = (IItemService)SpringObjectFactory.INSTANCE.getInstance("IItemService");
					Item item = itemService.getByName(object.getCompany().getId(), itemName);
					line.setItem(item);
				}
			}
		}
		
		
		if(!Utils.isNullSet(object.getCorpSalesPeriodCategories())){
			int lineNo=1;
			for (CorpSalesPeriodCategory line: object.getCorpSalesPeriodCategories()) {
				line.setCompany(company);
				line.setPeriod(object.getPeriod());
				line.setLineNumber(lineNo ++);
				if(line.getCategory() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), CorpSalesPeriodErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Category")));
				}else {
					ICategoryService clientService = (ICategoryService)SpringObjectFactory.INSTANCE.getInstance("ICategoryService");
					Category client  = (Category)clientService.getByName(context.getLoggedinCompany(), line.getCategory().getName());
					line.setCategory(client);
				}
			}
		}
		
		if(!Utils.isNullSet(object.getCorpSalesPeriodBrands())){
			int lineNo=1;
			for (CorpSalesPeriodBrand line: object.getCorpSalesPeriodBrands()) {
				line.setCompany(company);
				line.setPeriod(object.getPeriod());
				line.setLineNumber(lineNo ++);
				if(line.getBrand() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), CorpSalesPeriodErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Brand")));
				}else {
					IBrandService clientService = (IBrandService)SpringObjectFactory.INSTANCE.getInstance("IBrandService");
					Brand client  = (Brand)clientService.getByName(context.getLoggedinCompany(), line.getBrand().getName());
					line.setBrand(client);
				}
			}
		}
		
		if(!Utils.isNullSet(object.getCorpSalesPeriodDivisions())){
			int lineNo=1;
			for (CorpSalesPeriodDivision line: object.getCorpSalesPeriodDivisions()) {
				line.setCompany(company);
				line.setPeriod(object.getPeriod());
				line.setLineNumber(lineNo ++);
				if(line.getDivision() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), CorpSalesPeriodErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Division")));
				}else {
					Division client = CommonUtil.getDivisionObect(context, line.getDivision());
					line.setDivision(client);
				}
			}
		}
		
		if(!Utils.isNullSet(object.getCorpSalesPeriodBrands())){
			int lineNo=1;
			for (CorpSalesPeriodBrand line: object.getCorpSalesPeriodBrands()) {
				line.setCompany(company);
				line.setPeriod(object.getPeriod());
				line.setLineNumber(lineNo ++);
				if(line.getBrand() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), CorpSalesPeriodErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Brand")));
				}else {
					IBrandService clientService = (IBrandService)SpringObjectFactory.INSTANCE.getInstance("IBrandService");
					Brand client  = (Brand)clientService.getByName(context.getLoggedinCompany(), line.getBrand().getName());
					line.setBrand(client);
				}
			}
		}
		if(!Utils.isNullSet(object.getCorpSalesPeriodProducts())){
			int lineNo=1;
			for (CorpSalesPeriodProduct line: object.getCorpSalesPeriodProducts()) {
				line.setCompany(company);
				line.setPeriod(object.getPeriod());
				line.setLineNumber(lineNo ++);
				if(line.getProduct() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), CorpSalesPeriodErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Product")));
				}else {
					IProductService clientService = (IProductService)SpringObjectFactory.INSTANCE.getInstance("IProductService");
					Product client  = (Product)clientService.getByName(context.getLoggedinCompany(), line.getProduct().getName());
					line.setProduct(client);
				}
			}
		}
		if(ans.size() > 0 )
			addBlankRowBack(object);
		return ans;
	}

	private void addBlankRowBack(CorpSalesPeriod corpSalesPeriod)
	{
		if(Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodLines())){
			corpSalesPeriod.addCorpSalesPeriodLine(new CorpSalesPeriodLine());
		}
		if(Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodDivisions())){
			corpSalesPeriod.addCorpSalesPeriodDivision(new CorpSalesPeriodDivision());
		}
		if(Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodCategories())){
			corpSalesPeriod.addCorpSalesPeriodCategory(new CorpSalesPeriodCategory()); 
		}
		if(Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodProducts())){
			corpSalesPeriod.addCorpSalesPeriodProduct(new CorpSalesPeriodProduct());
		}
		if(Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodBrands())){
			corpSalesPeriod.addCorpSalesPeriodBrand(new CorpSalesPeriodBrand());
		}
	}
	
	private void exlcudeBlankRows(CorpSalesPeriod corpSalesPeriod)
	{
		Double maxSum = 0d;
		if(!Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodLines())){
			Double currentSum = 0d ;
			for (CorpSalesPeriodLine line: corpSalesPeriod.getCorpSalesPeriodLines()) {
				if(line.isNullContent()) {
					corpSalesPeriod.getCorpSalesPeriodLines().remove(line);
				}
			}
			if(currentSum > maxSum)
				maxSum = currentSum;
		}
		
		if(!Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodDivisions())){
			Double currentSum = 0d ;
			for (CorpSalesPeriodDivision line: corpSalesPeriod.getCorpSalesPeriodDivisions()) {
				if(line.isNullContent()) {
					corpSalesPeriod.getCorpSalesPeriodDivisions().remove(line);
				}else
					currentSum += line.getLineTotal();
			}
			if(currentSum > maxSum)
				maxSum = currentSum;
		}
		
		if(!Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodBrands())){
			Double currentSum = 0d ;
			for (CorpSalesPeriodBrand line: corpSalesPeriod.getCorpSalesPeriodBrands()) {
				if(line.isNullContent()) {
					corpSalesPeriod.getCorpSalesPeriodBrands().remove(line);
				}else
					currentSum += line.getLineTotal();
			}
			if(currentSum > maxSum)
				maxSum = currentSum;
		}
		if(!Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodCategories())){
			Double currentSum = 0d ;
			for (CorpSalesPeriodCategory line: corpSalesPeriod.getCorpSalesPeriodCategories()) {
				if(line.isNullContent()) {
					corpSalesPeriod.getCorpSalesPeriodCategories().remove(line);
				}else
					currentSum += line.getLineTotal();
			}
			if(currentSum > maxSum)
				maxSum = currentSum;
		}
		if(!Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodProducts())){
			Double currentSum = 0d ;
			for (CorpSalesPeriodProduct line: corpSalesPeriod.getCorpSalesPeriodProducts()) {
				if(line.isNullContent()) {
					corpSalesPeriod.getCorpSalesPeriodProducts().remove(line);
				}else
					currentSum += line.getLineTotal();
			}
			if(currentSum > maxSum)
				maxSum = currentSum;
		}
		corpSalesPeriod.setTotalTarget(maxSum + corpSalesPeriod.getAdditionalTarget());

	}
	
	@Override
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		CorpSalesPeriod corpSalesPeriod = (CorpSalesPeriod)object ;
			int pk = GeneralSQLs.getNextPKValue("CorpSalesPeriods") ;
			corpSalesPeriod.setId(pk);
			for (CorpSalesPeriodLine  line : corpSalesPeriod.getCorpSalesPeriodLines()) {
				int linePK = GeneralSQLs.getNextPKValue( "CorpSalesPeriod_Lines") ;
				line.setId(linePK);
				line.setCorpSalesPeriodDoc(corpSalesPeriod);
			}
			for (CorpSalesPeriodDivision  line : corpSalesPeriod.getCorpSalesPeriodDivisions()) {
				int linePK = GeneralSQLs.getNextPKValue( "CorpSalesPeriod_Divisions") ;
				line.setId(linePK);
				line.setCorpSalesPeriodDoc(corpSalesPeriod);
			}
			for (CorpSalesPeriodBrand  line : corpSalesPeriod.getCorpSalesPeriodBrands()) {
				int linePK = GeneralSQLs.getNextPKValue( "CorpSalesPeriod_Brands") ;
				line.setId(linePK);
				line.setCorpSalesPeriodDoc(corpSalesPeriod);
			}
			for (CorpSalesPeriodCategory  line : corpSalesPeriod.getCorpSalesPeriodCategories()) {
				int linePK = GeneralSQLs.getNextPKValue( "CorpSalesPeriod_Categories") ;
				line.setId(linePK);
				line.setCorpSalesPeriodDoc(corpSalesPeriod);
			}
			for (CorpSalesPeriodProduct  line : corpSalesPeriod.getCorpSalesPeriodProducts()) {
				int linePK = GeneralSQLs.getNextPKValue( "CorpSalesPeriod_Products") ;
				line.setId(linePK);
				line.setCorpSalesPeriodDoc(corpSalesPeriod);
			}
		TransactionResult result= super.create(object, context);
		return result; 
	}

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		CorpSalesPeriod corpSalesPeriod = (CorpSalesPeriod)object ;
		CorpSalesPeriod oldObject = (CorpSalesPeriod)getById(corpSalesPeriod.getPK());
		if (!Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodLines())) {
			int  ct = 0;
			Iterator it = oldObject.getCorpSalesPeriodLines().iterator() ;
			for (CorpSalesPeriodLine  line : corpSalesPeriod.getCorpSalesPeriodLines()) {
				CorpSalesPeriodLine oldLine = null ;
				if (it.hasNext()) {
					oldLine= (CorpSalesPeriodLine) it.next() ;
				}
				line.setCorpSalesPeriodDoc(corpSalesPeriod);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "CorpSalesPeriod_Lines") ;
					line.setId(linePK);
				}
			}
			while (it.hasNext()) {
				CorpSalesPeriodLine oldLine= (CorpSalesPeriodLine) it.next() ;
				oldLine.setVoided(true);
				corpSalesPeriod.addCorpSalesPeriodLine(oldLine);
			}
		}
		
		
		
		if (!Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodCategories())) {
			int  ct = 0;
			Iterator it = null;
			if (!Utils.isNullSet(oldObject.getCorpSalesPeriodCategories()))
					it = oldObject.getCorpSalesPeriodCategories().iterator() ;
			for (CorpSalesPeriodCategory  line : corpSalesPeriod.getCorpSalesPeriodCategories()) {
				CorpSalesPeriodCategory oldLine = null ;
				if ( it != null && it.hasNext()) {
					oldLine= (CorpSalesPeriodCategory) it.next() ;
				}
				line.setCorpSalesPeriodDoc(corpSalesPeriod);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "CorpSalesPeriod_Categories") ;
					line.setId(linePK);
				}
			}
			if (it != null ) {
				while (it.hasNext()) {
					CorpSalesPeriodCategory oldLine= (CorpSalesPeriodCategory) it.next() ;
					oldLine.setVoided(true);
					corpSalesPeriod.addCorpSalesPeriodCategory(oldLine);
				}
			}
		}
		
		if (!Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodDivisions())) {
			int  ct = 0;
			Iterator it = null;
			if (!Utils.isNullSet(oldObject.getCorpSalesPeriodDivisions()))
					it = oldObject.getCorpSalesPeriodDivisions().iterator() ;
			for (CorpSalesPeriodDivision  line : corpSalesPeriod.getCorpSalesPeriodDivisions()) {
				CorpSalesPeriodDivision oldLine = null ;
				if ( it != null && it.hasNext()) {
					oldLine= (CorpSalesPeriodDivision) it.next() ;
				}
				line.setCorpSalesPeriodDoc(corpSalesPeriod);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "CorpSalesPeriod_Divisions") ;
					line.setId(linePK);
				}
			}
			if (it != null ) {
				while (it.hasNext()) {
					CorpSalesPeriodDivision oldLine= (CorpSalesPeriodDivision) it.next() ;
					oldLine.setVoided(true);
					corpSalesPeriod.addCorpSalesPeriodDivision(oldLine);
				}
			}
		}
		
		if (!Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodBrands())) {
			int  ct = 0;
			Iterator it = null;
			if (!Utils.isNullSet(oldObject.getCorpSalesPeriodBrands()))
					it = oldObject.getCorpSalesPeriodBrands().iterator() ;
			for (CorpSalesPeriodBrand  line : corpSalesPeriod.getCorpSalesPeriodBrands()) {
				CorpSalesPeriodBrand oldLine = null ;
				if ( it != null && it.hasNext()) {
					oldLine= (CorpSalesPeriodBrand) it.next() ;
				}
				line.setCorpSalesPeriodDoc(corpSalesPeriod);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "CorpSalesPeriod_Brands") ;
					line.setId(linePK);
				}
			}
			if (it != null ) {
				while (it.hasNext()) {
					CorpSalesPeriodBrand oldLine= (CorpSalesPeriodBrand) it.next() ;
					oldLine.setVoided(true);
					corpSalesPeriod.addCorpSalesPeriodBrand(oldLine);
				}
			}
		}
		
		if (!Utils.isNullSet(corpSalesPeriod.getCorpSalesPeriodProducts())) {
			int  ct = 0;
			Iterator it = null;
			if (!Utils.isNullSet(oldObject.getCorpSalesPeriodProducts()))
					it = oldObject.getCorpSalesPeriodProducts().iterator() ;
			for (CorpSalesPeriodProduct  line : corpSalesPeriod.getCorpSalesPeriodProducts()) {
				CorpSalesPeriodProduct oldLine = null ;
				if ( it != null && it.hasNext()) {
					oldLine= (CorpSalesPeriodProduct) it.next() ;
				}
				line.setCorpSalesPeriodDoc(corpSalesPeriod);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "CorpSalesPeriod_Products") ;
					line.setId(linePK);
				}
			}
			if (it != null ) {
				while (it.hasNext()) {
					CorpSalesPeriodProduct oldLine= (CorpSalesPeriodProduct) it.next() ;
					oldLine.setVoided(true);
					corpSalesPeriod.addCorpSalesPeriodProduct(oldLine);
				}
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
	public List<CorpSalesPeriod> getStartingCorpSalesPeriodsforAlerts(Date startDt) {
		CorpSalesPeriodDAO dao = (CorpSalesPeriodDAO) getDAO();
		return dao.getStartingCorpSalesPeriodsforAlerts(startDt);
	}

	@Override
	public List<CorpSalesPeriod> getEndCorpSalesPeriodsforAlerts(Date endDt) {
		CorpSalesPeriodDAO dao = (CorpSalesPeriodDAO) getDAO();
		return dao.getEndingCorpSalesPeriodsforAlerts(endDt);
	}

	
	@Override
	public CorpSalesPeriod getActiveCorpSalesPeriod( Date date) {
		CorpSalesPeriodDAO dao = (CorpSalesPeriodDAO) getDAO();
		return dao.getActiveCorpSalesPeriodforDivision(date);
	}
	
	
	
	
}
