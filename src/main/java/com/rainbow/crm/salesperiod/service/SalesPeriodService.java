package com.rainbow.crm.salesperiod.service;

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
import com.rainbow.crm.common.Externalize;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.messaging.CRMMessageSender;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriod;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodBrand;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodCategory;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodDivision;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodLine;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodProduct;
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
import com.rainbow.crm.salesperiod.dao.SalesPeriodDAO;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.salesperiod.model.SalesPeriodAssociate;
import com.rainbow.crm.salesperiod.model.SalesPeriodBrand;
import com.rainbow.crm.salesperiod.model.SalesPeriodCategory;
import com.rainbow.crm.salesperiod.model.SalesPeriodLine;
import com.rainbow.crm.salesperiod.model.SalesPeriodProduct;
import com.rainbow.crm.salesperiod.model.SalesPeriodTerritory;
import com.rainbow.crm.salesperiod.validator.SalesPeriodErrorCodes;
import com.rainbow.crm.salesperiod.validator.SalesPeriodValidator;
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
public class SalesPeriodService extends AbstractionTransactionService implements ISalesPeriodService{

	
	@Override
	protected String getTableName() {
		return "SalesPeriod";
	}
	

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("SalesPeriod", from, to, whereCondition, context,sortCriteria);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((SalesPeriod)object).setCompany(company);
		SalesPeriodValidator validator = new SalesPeriodValidator(context);
		List<RadsError> errors = validator.validateforCreate(object);
		if(Utils.isNullList(errors) &&  errors.size() >1 )
			addBlankRowBack((SalesPeriod) object);
		return errors;
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((SalesPeriod)object).setCompany(company);
		SalesPeriodValidator validator = new SalesPeriodValidator(context);
		List<RadsError> errors = validator.validateforUpdate(object);
		if(Utils.isNullList(errors) &&  errors.size() >1 )
			addBlankRowBack((SalesPeriod) object);
		return errors;
	}

	@Override
	protected ORMDAO getDAO() {
//	return new SalesPeriodDAO();
	return (SalesPeriodDAO) SpringObjectFactory.INSTANCE.getInstance("SalesPeriodDAO");
	}

	
	
	@Override
	public List<RadsError> adaptfromUI(CRMContext context, ModelObject object) {
		return adaptfromUI(context, (SalesPeriod)object);
	}

	private List<RadsError> adaptfromUI(CRMContext context,SalesPeriod object) {
		ICompanyService compService = (ICompanyService) SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		object.setCompany(company);
		exlcudeBlankRows(object);
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
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesPeriodErrorCodes.FIELD_NOT_VALID , "Division"));
			}else {
				object.setDivision(division);
			}
		}
		Externalize externalize = new Externalize(); ;
		
		if(!Utils.isNullSet(object.getSalesPeriodLines())){
			int lineNo=1;
			for (SalesPeriodLine line: object.getSalesPeriodLines()) {
				line.setCompany(company);
				line.setPeriod(object.getPeriod());
				line.setLineNumber(lineNo ++);
				if(line.getItem() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesPeriodErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Item")));
				}else {
					String itemName = line.getItem().getName() ;
					IItemService itemService = (IItemService)SpringObjectFactory.INSTANCE.getInstance("IItemService");
					Item item = itemService.getByName(object.getCompany().getId(), itemName);
					line.setItem(item);
				}
			}
		}
		
		if(!Utils.isNullSet(object.getSalesPeriodAssociates())){
			int lineNo=1;
			for (SalesPeriodAssociate line: object.getSalesPeriodAssociates()) {
				line.setCompany(company);
				line.setPeriod(object.getPeriod());
				line.setLineNumber(lineNo ++);
				if(line.getUser() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesPeriodErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "User")));
				}else {
					IUserService userService = (IUserService)SpringObjectFactory.INSTANCE.getInstance("IUserService");
					User user  = (User)userService.getById(line.getUser().getUserId());
					line.setUser(user);
				}
			}
		}
		
		if(!Utils.isNullSet(object.getSalesPeriodTerritories())){
			int lineNo=1;
			for (SalesPeriodTerritory line: object.getSalesPeriodTerritories()) {
				line.setCompany(company);
				line.setPeriod(object.getPeriod());
				line.setLineNumber(lineNo ++);
				if(line.getTerritory() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesPeriodErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Territory")));
				}else {
					ITerritoryService territoryService = (ITerritoryService)SpringObjectFactory.INSTANCE.getInstance("ITerritoryService");
					Territory territory  = (Territory)territoryService.getById(line.getTerritory().getId());
					line.setTerritory(territory);
				}
			}
		}
		
		if(!Utils.isNullSet(object.getSalesPeriodCategories())){
			int lineNo=1;
			for (SalesPeriodCategory line: object.getSalesPeriodCategories()) {
				line.setCompany(company);
				line.setPeriod(object.getPeriod());
				line.setLineNumber(lineNo ++);
				if(line.getCategory() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesPeriodErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Category")));
				}else {
					ICategoryService clientService = (ICategoryService)SpringObjectFactory.INSTANCE.getInstance("ICategoryService");
					Category client  = (Category)clientService.getByName(context.getLoggedinCompany(), line.getCategory().getName());
					line.setCategory(client);
				}
			}
		}
		
		if(!Utils.isNullSet(object.getSalesPeriodBrands())){
			int lineNo=1;
			for (SalesPeriodBrand line: object.getSalesPeriodBrands()) {
				line.setCompany(company);
				line.setPeriod(object.getPeriod());
				line.setLineNumber(lineNo ++);
				if(line.getBrand() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesPeriodErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Brand")));
				}else {
					IBrandService clientService = (IBrandService)SpringObjectFactory.INSTANCE.getInstance("IBrandService");
					Brand client  = (Brand)clientService.getByName(context.getLoggedinCompany(), line.getBrand().getName());
					line.setBrand(client);
				}
			}
		}
		
		if(!Utils.isNullSet(object.getSalesPeriodProducts())){
			int lineNo=1;
			for (SalesPeriodProduct line: object.getSalesPeriodProducts()) {
				line.setCompany(company);
				line.setPeriod(object.getPeriod());
				line.setLineNumber(lineNo ++);
				if(line.getProduct() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), SalesPeriodErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Product")));
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

	private void addBlankRowBack(SalesPeriod salesPeriod)
	{
		if(Utils.isNullSet(salesPeriod.getSalesPeriodLines())){
			salesPeriod.addSalesPeriodLine(new SalesPeriodLine());
		}
		if(Utils.isNullSet(salesPeriod.getSalesPeriodCategories())){
			salesPeriod.addSalePeriodCategory(new SalesPeriodCategory()); 
		}
		if(Utils.isNullSet(salesPeriod.getSalesPeriodAssociates())){
			salesPeriod.addSalesPeriodAssociate(new SalesPeriodAssociate());
		}
		if(Utils.isNullSet(salesPeriod.getSalesPeriodTerritories())){
			salesPeriod.addSalesPeriodTerritory(new SalesPeriodTerritory());
		}
		if(Utils.isNullSet(salesPeriod.getSalesPeriodProducts())){
			salesPeriod.addSalePeriodProduct(new SalesPeriodProduct());
		}
		if(Utils.isNullSet(salesPeriod.getSalesPeriodBrands())){
			salesPeriod.addSalePeriodBrand(new SalesPeriodBrand());
		}
	}
	
	private void exlcudeBlankRows(SalesPeriod salesPeriod)
	{
		Double maxSum = 0d;
		if(!Utils.isNullSet(salesPeriod.getSalesPeriodLines())){
			Double currentSum = 0d ;
			for (SalesPeriodLine line: salesPeriod.getSalesPeriodLines()) {
				if(line.isNullContent()) {
					salesPeriod.getSalesPeriodLines().remove(line);
				}else
					currentSum += line.getLineTotal();
			}
			if(currentSum > maxSum)
				maxSum = currentSum;
		}
		if(!Utils.isNullSet(salesPeriod.getSalesPeriodAssociates())){
			Double currentSum = 0d ;
			for (SalesPeriodAssociate line: salesPeriod.getSalesPeriodAssociates()) {
				if(line.isNullContent()) {
					salesPeriod.getSalesPeriodAssociates().remove(line);
				}else
					currentSum += line.getLineTotal();
			}
			if(currentSum > maxSum)
				maxSum = currentSum;
		}
		if(!Utils.isNullSet(salesPeriod.getSalesPeriodTerritories())){
			Double currentSum = 0d ;
			for (SalesPeriodTerritory line: salesPeriod.getSalesPeriodTerritories()) {
				if(line.isNullContent()) {
					salesPeriod.getSalesPeriodTerritories().remove(line);
				}else
					currentSum += line.getLineTotal();
			}
			if(currentSum > maxSum)
				maxSum = currentSum;
		}
		if(!Utils.isNullSet(salesPeriod.getSalesPeriodBrands())){
			Double currentSum = 0d ;
			for (SalesPeriodBrand line: salesPeriod.getSalesPeriodBrands()) {
				if(line.isNullContent()) {
					salesPeriod.getSalesPeriodBrands().remove(line);
				}else
					currentSum += line.getLineTotal();
			}
			if(currentSum > maxSum)
				maxSum = currentSum;
		}
		if(!Utils.isNullSet(salesPeriod.getSalesPeriodCategories())){
			Double currentSum = 0d ;
			for (SalesPeriodCategory line: salesPeriod.getSalesPeriodCategories()) {
				if(line.isNullContent()) {
					salesPeriod.getSalesPeriodCategories().remove(line);
				}else
					currentSum += line.getLineTotal();
			}
			if(currentSum > maxSum)
				maxSum = currentSum;
		}
		if(!Utils.isNullSet(salesPeriod.getSalesPeriodProducts())){
			Double currentSum = 0d ;
			for (SalesPeriodProduct line: salesPeriod.getSalesPeriodProducts()) {
				if(line.isNullContent()) {
					salesPeriod.getSalesPeriodProducts().remove(line);
				}else
					currentSum += line.getLineTotal();
			}
			if(currentSum > maxSum)
				maxSum = currentSum;
		}
		salesPeriod.setTotalTarget(maxSum + salesPeriod.getAdditionalTarget());

	}

	
	
	@Override
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		SalesPeriod salesPeriod = (SalesPeriod)object ;
			int pk = GeneralSQLs.getNextPKValue("SalesPeriods") ;
			salesPeriod.setId(pk);
			for (SalesPeriodLine  line : salesPeriod.getSalesPeriodLines()) {
				int linePK = GeneralSQLs.getNextPKValue( "SalesPeriod_Lines") ;
				line.setId(linePK);
				line.setSalesPeriodDoc(salesPeriod);
			}
			for (SalesPeriodAssociate  line : salesPeriod.getSalesPeriodAssociates()) {
				int linePK = GeneralSQLs.getNextPKValue( "SALESPERIOD_ASSOCIATES") ;
				line.setId(linePK);
				line.setSalesPeriodDoc(salesPeriod);
			}
			for (SalesPeriodTerritory  line : salesPeriod.getSalesPeriodTerritories()) {
				int linePK = GeneralSQLs.getNextPKValue( "SALESPERIOD_TERRITORIES") ;
				line.setId(linePK);
				line.setSalesPeriodDoc(salesPeriod);
			}
			for (SalesPeriodBrand  line : salesPeriod.getSalesPeriodBrands()) {
				int linePK = GeneralSQLs.getNextPKValue( "SALESPERIOD_BRANDS") ;
				line.setId(linePK);
				line.setSalesPeriodDoc(salesPeriod);
			}
			for (SalesPeriodCategory  line : salesPeriod.getSalesPeriodCategories()) {
				int linePK = GeneralSQLs.getNextPKValue( "SALESPERIOD_CATEGORIES") ;
				line.setId(linePK);
				line.setSalesPeriodDoc(salesPeriod);
			}
			for (SalesPeriodProduct  line : salesPeriod.getSalesPeriodProducts()) {
				int linePK = GeneralSQLs.getNextPKValue( "SALESPERIOD_PRODUCTS") ;
				line.setId(linePK);
				line.setSalesPeriodDoc(salesPeriod);
			}
		TransactionResult result= super.create(object, context);
		return result; 
	}

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		SalesPeriod salesPeriod = (SalesPeriod)object ;
		SalesPeriod oldObject = (SalesPeriod)getById(salesPeriod.getPK());
		if (!Utils.isNullSet(salesPeriod.getSalesPeriodLines())) {
			int  ct = 0;
			Iterator it = oldObject.getSalesPeriodLines().iterator() ;
			for (SalesPeriodLine  line : salesPeriod.getSalesPeriodLines()) {
				SalesPeriodLine oldLine = null ;
				if (it.hasNext()) {
					oldLine= (SalesPeriodLine) it.next() ;
				}
				line.setSalesPeriodDoc(salesPeriod);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "SalesPeriod_Lines") ;
					line.setId(linePK);
				}
			}
			while (it.hasNext()) {
				SalesPeriodLine oldLine= (SalesPeriodLine) it.next() ;
				oldLine.setVoided(true);
				salesPeriod.addSalesPeriodLine(oldLine);
			}
		}
		
		if (!Utils.isNullSet(salesPeriod.getSalesPeriodAssociates())) {
			int  ct = 0;
			Iterator it = null;
			if (!Utils.isNullSet(oldObject.getSalesPeriodAssociates()))
				it = oldObject.getSalesPeriodAssociates().iterator() ;
			for (SalesPeriodAssociate  line : salesPeriod.getSalesPeriodAssociates()) {
				SalesPeriodAssociate oldLine = null ;
				if (it != null && it.hasNext()) {
					oldLine= (SalesPeriodAssociate) it.next() ;
				}
				line.setSalesPeriodDoc(salesPeriod);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "SALESPERIOD_ASSOCIATES") ;
					line.setId(linePK);
				}
			}
			if (it != null) {
				while (it.hasNext()) {
					SalesPeriodAssociate oldLine = (SalesPeriodAssociate) it.next();
					oldLine.setVoided(true);
					salesPeriod.addSalesPeriodAssociate(oldLine);
				}
			}
		}
		if (!Utils.isNullSet(salesPeriod.getSalesPeriodTerritories())) {
			int  ct = 0;
			Iterator it = null;
			if (!Utils.isNullSet(oldObject.getSalesPeriodTerritories()))
					it = oldObject.getSalesPeriodTerritories().iterator() ;
			for (SalesPeriodTerritory  line : salesPeriod.getSalesPeriodTerritories()) {
				SalesPeriodTerritory oldLine = null ;
				if ( it != null && it.hasNext()) {
					oldLine= (SalesPeriodTerritory) it.next() ;
				}
				line.setSalesPeriodDoc(salesPeriod);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "SALESPERIOD_TERRITORIES") ;
					line.setId(linePK);
				}
			}
			if (it != null ) {
				while (it.hasNext()) {
					SalesPeriodTerritory oldLine= (SalesPeriodTerritory) it.next() ;
					oldLine.setVoided(true);
					salesPeriod.addSalesPeriodTerritory(oldLine);
				}
			}
		}
		
		if (!Utils.isNullSet(salesPeriod.getSalesPeriodCategories())) {
			int  ct = 0;
			Iterator it = null;
			if (!Utils.isNullSet(oldObject.getSalesPeriodCategories()))
					it = oldObject.getSalesPeriodCategories().iterator() ;
			for (SalesPeriodCategory  line : salesPeriod.getSalesPeriodCategories()) {
				SalesPeriodCategory oldLine = null ;
				if ( it != null && it.hasNext()) {
					oldLine= (SalesPeriodCategory) it.next() ;
				}
				line.setSalesPeriodDoc(salesPeriod);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "SALESPERIOD_CATEGORIES") ;
					line.setId(linePK);
				}
			}
			if (it != null ) {
				while (it.hasNext()) {
					SalesPeriodCategory oldLine= (SalesPeriodCategory) it.next() ;
					oldLine.setVoided(true);
					salesPeriod.addSalePeriodCategory(oldLine);
				}
			}
		}
		
		if (!Utils.isNullSet(salesPeriod.getSalesPeriodBrands())) {
			int  ct = 0;
			Iterator it = null;
			if (!Utils.isNullSet(oldObject.getSalesPeriodBrands()))
					it = oldObject.getSalesPeriodBrands().iterator() ;
			for (SalesPeriodBrand  line : salesPeriod.getSalesPeriodBrands()) {
				SalesPeriodBrand oldLine = null ;
				if ( it != null && it.hasNext()) {
					oldLine= (SalesPeriodBrand) it.next() ;
				}
				line.setSalesPeriodDoc(salesPeriod);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "SALESPERIOD_BRANDS") ;
					line.setId(linePK);
				}
			}
			if (it != null ) {
				while (it.hasNext()) {
					SalesPeriodBrand oldLine= (SalesPeriodBrand) it.next() ;
					oldLine.setVoided(true);
					salesPeriod.addSalePeriodBrand(oldLine);
				}
			}
		}
		
		if (!Utils.isNullSet(salesPeriod.getSalesPeriodProducts())) {
			int  ct = 0;
			Iterator it = null;
			if (!Utils.isNullSet(oldObject.getSalesPeriodProducts()))
					it = oldObject.getSalesPeriodProducts().iterator() ;
			for (SalesPeriodProduct  line : salesPeriod.getSalesPeriodProducts()) {
				SalesPeriodProduct oldLine = null ;
				if ( it != null && it.hasNext()) {
					oldLine= (SalesPeriodProduct) it.next() ;
				}
				line.setSalesPeriodDoc(salesPeriod);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "SALESPERIOD_PRODUCTS") ;
					line.setId(linePK);
				}
			}
			if (it != null ) {
				while (it.hasNext()) {
					SalesPeriodProduct oldLine= (SalesPeriodProduct) it.next() ;
					oldLine.setVoided(true);
					salesPeriod.addSalePeriodProduct(oldLine);
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
	public List<SalesPeriod> getStartingSalesPeriodsforAlerts(Date startDt) {
		SalesPeriodDAO dao = (SalesPeriodDAO) getDAO();
		return dao.getStartingSalesPeriodsforAlerts(startDt);
	}

	@Override
	public List<SalesPeriod> getEndSalesPeriodsforAlerts(Date endDt) {
		SalesPeriodDAO dao = (SalesPeriodDAO) getDAO();
		return dao.getEndingSalesPeriodsforAlerts(endDt);
	}

	@Override
	public SalesPeriod getSalesPeriodforAssociate(String userId, Date date) {
		SalesPeriodDAO dao = (SalesPeriodDAO) getDAO();
		return dao.getActiveSalesPeriodforAssociate(userId, date);
	}

	@Override
	public SalesPeriod getActiveSalesPeriodforDivision(int divisionId, Date date) {
		SalesPeriodDAO dao = (SalesPeriodDAO) getDAO();
		return dao.getActiveSalesPeriodforDivision(divisionId, date);
	}
	
	
	
	
}
