package com.rainbow.crm.purchase.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.product.validator.ProductValidator;
import com.rainbow.crm.purchase.dao.PurchaseDAO;
import com.rainbow.crm.purchase.model.Purchase;
import com.rainbow.crm.purchase.model.PurchaseLine;
import com.rainbow.crm.purchase.validator.PurchaseErrorCodes;
import com.rainbow.crm.purchase.validator.PurchaseValidator;
import com.rainbow.crm.vendor.model.Vendor;
import com.rainbow.crm.vendor.service.IVendorService;
import com.rainbow.framework.nextup.NextUpGenerator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

@Transactional
public class PurchaseService extends AbstractionTransactionService implements IPurchaseService{

	@Override
	protected String getTableName() {
		return "Purchase";
	}
	
	
	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Purchase", from, to, whereCondition, context,sortCriteria);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Purchase)object).setCompany(company);
		PurchaseValidator validator = new PurchaseValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Purchase)object).setCompany(company);
		PurchaseValidator validator = new PurchaseValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	protected ORMDAO getDAO() {
//	return new PurchaseDAO();
	return (PurchaseDAO) SpringObjectFactory.INSTANCE.getInstance("PurchaseDAO");
	}

	
	@Override
	public List<RadsError> adaptfromUI(CRMContext context, ModelObject obj) {
		Purchase object = (Purchase) obj;
		ICompanyService compService = (ICompanyService) SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		object.setCompany(company);
				
		List<RadsError> ans = new ArrayList<RadsError>();
		if (object.getVendor() != null) {
			String vendorName= object.getVendor().getName();
			IVendorService vendorService =(IVendorService) SpringObjectFactory.INSTANCE.getInstance("IVendorService");
			Vendor vendor  =  (Vendor)vendorService.getByName(object.getCompany().getId(), vendorName);
			if (vendor == null) {
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), PurchaseErrorCodes.FIELD_NOT_VALID , "Vendor"));
			}else {
				object.setVendor(vendor);
			}
		}
		
		if (object.getDivision() != null) {
			int divisionId  = object.getDivision().getId() ;
			IDivisionService divisionService =(IDivisionService) SpringObjectFactory.INSTANCE.getInstance("IDivisionService");
			Division division = null;
			if (divisionId > 0 )
				division = (Division)divisionService.getById(divisionId);
			else
				division  = (Division)divisionService.getByBusinessKey(object.getDivision(), context);
			if(division == null){
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), PurchaseErrorCodes.FIELD_NOT_VALID , "Division"));
			}else {
				object.setDivision(division);
			}
		}
		Externalize externalize = new Externalize(); ;
		
		if(!Utils.isNullSet(object.getPurchaseLines())){
			int lineNo=1;
			for (PurchaseLine line: object.getPurchaseLines()) {
				line.setCompany(company);
				line.setDocNumber(object.getDocNumber());
				line.setLineNumber(lineNo ++);
				if(line.getSku() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), PurchaseErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Item")));
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
		Purchase purchase = (Purchase)object ;
		if (Utils.isNull(purchase.getDocNumber())) {
			String bKey = NextUpGenerator.getNextNumber("Purchases", context, purchase.getDivision());
			purchase.setDocNumber(bKey);
		}
		if (!Utils.isNullSet(purchase.getPurchaseLines())) {
			int pk = GeneralSQLs.getNextPKValue("Purchases") ;
			purchase.setId(pk);
			for (PurchaseLine  line : purchase.getPurchaseLines()) {
				int linePK = GeneralSQLs.getNextPKValue( "Purchase_Lines") ;
				line.setId(linePK);
				line.setPurchaseDoc(purchase);
				line.setDocNumber(purchase.getDocNumber());
			}
		}
		TransactionResult result= super.create(object, context);
		if(purchase.isRealised()) {
			sendInventoryUpdate (purchase, context);
		}
		return result; 
	}
	
	private void sendInventoryUpdate (Purchase purchase,CRMContext context)
	{
		InventoryUpdateObject invObject = new InventoryUpdateObject();
		invObject.setCompany(purchase.getCompany());
		invObject.setContext(context);
		invObject.setDivision(purchase.getDivision());
		invObject.setAddition(true);
		invObject.setItemLines(purchase.getPurchaseLines());
		CRMMessageSender.sendMessage(invObject);
	}

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		Purchase purchase = (Purchase)object ;
		Purchase oldObject = (Purchase)getById(purchase.getPK());
		if (!Utils.isNullSet(purchase.getPurchaseLines())) {
			int  ct = 0;
			Iterator it = oldObject.getPurchaseLines().iterator() ;
			for (PurchaseLine  line : purchase.getPurchaseLines()) {
				PurchaseLine oldLine = null ;
				if (it.hasNext()) {
					oldLine= (PurchaseLine) it.next() ;
				}
				line.setPurchaseDoc(purchase);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "Purchase_Lines") ;
					line.setId(linePK);
				}
			}
			while (it.hasNext()) {
				PurchaseLine oldLine= (PurchaseLine) it.next() ;
				oldLine.setVoided(true);
				purchase.addPurchaseLine(oldLine);
			}
		
		}
		TransactionResult result= super.update(object, context);
		if(!oldObject.isRealised() &&  purchase.isRealised()) {
			sendInventoryUpdate (purchase, context);
		}
		return result ;
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
	
	
	
}
