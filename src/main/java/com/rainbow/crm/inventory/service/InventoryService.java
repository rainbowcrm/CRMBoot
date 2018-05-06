package com.rainbow.crm.inventory.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.category.model.Category;
import com.rainbow.crm.category.validator.CategoryValidator;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.inventory.dao.InventoryDAO;
import com.rainbow.crm.inventory.model.Inventory;
import com.rainbow.crm.inventory.model.InventoryDelta;
import com.rainbow.crm.inventory.model.InventoryDeltaLine;
import com.rainbow.crm.inventory.model.InventoryUpdateObject;
import com.rainbow.crm.inventory.validator.InventoryValidator;
import com.rainbow.crm.item.model.Sku;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class InventoryService extends AbstractService implements  IInventoryService{
	
	protected ORMDAO getDAO() {
		return (InventoryDAO) SpringObjectFactory.INSTANCE.getInstance("InventoryDAO");
	}
		
	@Override
	protected String getTableName() {
		return "Inventory";
	}
	
	

	@Override
	public Object getById(Object PK) {
		return (Inventory)getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Inventory", from, to, whereCondition, context,sortCriteria);
	}

	@Override
	@Transactional
	public TransactionResult create(CRMModelObject object, CRMContext context) throws CRMDBException {
		((Inventory)object).setCurrentQty(((Inventory)object).getOpQty());
		return super.create((Inventory)object,context);
	}

	@Override
	public TransactionResult batchCreate(List<CRMModelObject> objects,
			CRMContext context) throws CRMDBException {
				return super.batchCreate(objects,context);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Inventory)object).setCompany(company);
		InventoryValidator validator = new InventoryValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((Inventory)object).setCompany(company);
		InventoryValidator validator = new InventoryValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	public Inventory getByItemandDivision(Sku item, Division division) {
		Inventory inv = ((InventoryDAO)getDAO()).getByItemandDivision(item.getId(), division.getId());
		return inv;
	}
	

	@Override
	public List<Inventory> getByItem(Sku item) {
		return ((InventoryDAO)getDAO()).getByItem(item.getId());
	}

	
	
	
	@Override
	public void updateInventory(InventoryDelta inventoryDelta) {
		if (inventoryDelta != null && !Utils.isNullList(inventoryDelta.getLines())) {
			for(InventoryDeltaLine line : inventoryDelta.getLines() ) {
				Inventory inv = getByItemandDivision( line.getSku(),line.getDivision());
				if (line.isReserve()) {
					inv.setReservedQty(inv.getReservedQty() + line.getQty());
				} else if (line.isFulFill()) {
					inv.setCurrentQty(inv.getCurrentQty() - line.getQty());
					inv.setReservedQty(inv.getReservedQty() - line.getQty());
				}else 
					inv.setCurrentQty(inv.getCurrentQty() + line.getQty());
				
				update(inv, inventoryDelta.getContext());
			}
				
			}
		
	}

	@Override
	@Transactional
	public void updateInventory(InventoryUpdateObject inventoryObject) {
		if (inventoryObject != null && !Utils.isNullSet(inventoryObject.getItemLines())) {
			for(CRMItemLine line : inventoryObject.getItemLines() ) {
				Inventory inv = getByItemandDivision( line.getSku(),inventoryObject.getDivision());
				if ( inv != null && inventoryObject.isAddition()) {
					inv.setCurrentQty(inv.getCurrentQty() + line.getQty());
					update(inv, inventoryObject.getContext());
				}else if (inv != null){
					if (inventoryObject.isReserve()) {
						inv.setReservedQty(inv.getReservedQty() + line.getQty());
					}else if (inventoryObject.isFulFilll()) {
						inv.setCurrentQty(inv.getCurrentQty() - line.getQty());
						inv.setReservedQty(inv.getReservedQty() - line.getQty());
					} else {
						inv.setCurrentQty(inv.getCurrentQty() - line.getQty());
					}
					update(inv, inventoryObject.getContext());
				} else if (inventoryObject.isAddition()){
					Inventory inventory = new Inventory();
					inventory.setCompany(inventoryObject.getCompany());
					inventory.setDivision(inventoryObject.getDivision());
					inventory.setSku(line.getSku());
					inventory.setOpQty(line.getQty());
					List<RadsError> errors = validateforCreate(inventory, inventoryObject.getContext());
					if (Utils.isNullList(errors)) {
						create(inventory, inventoryObject.getContext()) ;
					}
				}
			}
		}
		
	}
	
	
	
	




}
