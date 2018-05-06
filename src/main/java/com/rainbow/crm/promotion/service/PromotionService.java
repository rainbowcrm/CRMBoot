package com.rainbow.crm.promotion.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.transaction.annotation.Transactional;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.DatabaseException;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.saleslead.service.ISalesLeadService;
import com.rainbow.crm.salesportfolio.model.SalesPortfolio;
import com.rainbow.crm.salesportfolio.model.SalesPortfolioLine;
import com.rainbow.crm.promotion.dao.PromotionDAO;
import com.rainbow.crm.promotion.model.Promotion;
import com.rainbow.crm.promotion.model.PromotionLine;
import com.rainbow.crm.promotion.sql.PromotionSQLs;
import com.rainbow.crm.promotion.validator.PromotionValidator;
import com.rainbow.crm.custcategory.model.CustCategory;
import com.rainbow.crm.custcategory.service.ICustCategoryService;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.framework.query.model.Query;
import com.rainbow.framework.query.model.QueryCondition;
import com.rainbow.framework.query.model.QueryRecord;
import com.rainbow.framework.query.model.QueryReport;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class PromotionService extends AbstractService implements
		IPromotionService {

	@Override
	protected String getTableName() {
		return "Promotion";
	}
	
	
	@Override
	public Object getById(Object PK) {
		Promotion category = (Promotion) getDAO().getById(PK);
		fetchDetails(category,null);
		return category;
	}


	
	@Override
	public List<Promotion> getAllPromotionsforType(FiniteValue promoType,
			Date date, CRMContext context) {
		PromotionDAO dao =(PromotionDAO) getDAO();
		return dao.getPromotionsforType(context.getLoggedinCompany(), date, promoType.getCode()) ;
	}


	private void fetchDetails(Promotion promotion,CRMContext context)
 {
		if (promotion == null || Utils.isNullSet(promotion.getPromotionLines()))
			return;

		promotion.getPromotionLines().forEach(
				promotionLine -> {
					if (promotionLine.getMasterPortFolioType() != null
							&& !Utils.isNullString(promotionLine
									.getMasterPortFolioKey())) {
						String masterValue = CommonUtil.getSalesPortfolioValue(
								promotionLine.getMasterPortFolioType(),
								promotionLine.getMasterPortFolioKey());
						promotionLine.setMasterPortFolioValue(masterValue);
					}
					if (promotionLine.getChildPortFolioType() != null
							&& !Utils.isNullString(promotionLine
									.getChildPortFolioKey())) {
						String childValue = CommonUtil.getSalesPortfolioValue(
								promotionLine.getChildPortFolioType(),
								promotionLine.getChildPortFolioKey());
						promotionLine.setChildPortFolioValue(childValue);
					}

				});
	}
	
	
	@Override
	public PromotionLine getPromotionforSKU(Sku sku, Date date) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Promotion getAllItemPromotion(Date date, Division division) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PromotionLine isIncentivizedSku(Sku sku, Date date) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Promotion", from, to, whereCondition, context,
				sortCriteria);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		PromotionValidator validator = new PromotionValidator(context);
		return validator.validateforCreate(object);
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService) SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company) compService.getById(context.getLoggedinCompany());
		((Promotion) object).setCompany(company);
		PromotionValidator validator = new PromotionValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	public List<RadsError> adaptfromUI(CRMContext context, ModelObject object) {
		Promotion promotion = (Promotion) object;
		ICompanyService compService = (ICompanyService) SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company) compService.getById(context	.getLoggedinCompany());
		Division division = CommonUtil.getDivisionObect(context,promotion.getDivision());
		promotion.setDivision(division);
		((Promotion) object).setCompany(company);
		if(promotion.getCustCategory() != null ) {
			ICustCategoryService custCategService = (ICustCategoryService)SpringObjectFactory.INSTANCE.getInstance("ICustCategoryService");
			CustCategory custCategory =(CustCategory) custCategService.getByBusinessKey(promotion.getCustCategory(),context);
			promotion.setCustCategory(custCategory);
		}
		if (promotion.getItemClass() != null) {
			if("-1".equals(promotion.getItemClass().getCode())) {
				promotion.setItemClass(null);
			}
		}
		
		AtomicInteger lineNumber = new AtomicInteger(1);
		if(promotion.getPromotionLines() != null ) {
			promotion.getPromotionLines().forEach( promotionLine ->  { 
				promotionLine.setCompany(promotion.getCompany());
				promotionLine.setLineNumber(lineNumber.getAndIncrement());
				if(promotionLine.getMasterPortFolioType()!= null &&  !"-1".equals(promotionLine.getMasterPortFolioType().getCode()) ) {
					String key =  CommonUtil.getSalesPortfolioKey(promotionLine.getMasterPortFolioType(), promotionLine.getMasterPortFolioValue(), context);
					promotionLine.setMasterPortFolioKey(key);
				}else {
					promotionLine.setMasterPortFolioType(null);
				}
				if(promotionLine.getChildPortFolioType()!= null &&  !"-1".equals(promotionLine.getChildPortFolioType().getCode()) ) {
					String key =  CommonUtil.getSalesPortfolioKey(promotionLine.getChildPortFolioType(), promotionLine.getChildPortFolioValue(), context);
					promotionLine.setChildPortFolioKey(key);
				}else {
					promotionLine.setChildPortFolioType(null);
				}
				promotionLine.setPromotion(promotion);
				
			} );
		}
		return null;
	}

	@Override
	public List<RadsError> adaptToUI(CRMContext context, ModelObject object) {
		return null;
	}

	@Override
	protected ORMDAO getDAO() {
		return (PromotionDAO) SpringObjectFactory.INSTANCE
				.getInstance("PromotionDAO");
	}

	
	
	@Override
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		Promotion promotion = (Promotion) object;
		if (!Utils.isNullSet(promotion.getPromotionLines())) {
			int pk = GeneralSQLs.getNextPKValue("Promotions");
			promotion.setId(pk);
			for (PromotionLine line : promotion.getPromotionLines()) {
				int linePK = GeneralSQLs.getNextPKValue("Promotion_Lines");
				line.setId(linePK);
				line.setPromotion(promotion);
			}
		}
		
		return super.create(object, context);
	}

	@Transactional
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		Promotion promotion = (Promotion) object;
		Promotion oldObject = (Promotion) getById(promotion
				.getPK());
		if (!Utils.isNullSet(promotion.getPromotionLines())) {
			int ct = 0;
			Iterator it = oldObject.getPromotionLines().iterator();
			for (PromotionLine line : promotion
					.getPromotionLines()) {
				PromotionLine oldLine = null;
				if (it.hasNext()) {
					oldLine = (PromotionLine) it.next();
				}
				line.setPromotion(promotion);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				} else {
					int linePK = GeneralSQLs
							.getNextPKValue("Promotion_Lines");
					line.setId(linePK);
				}
			}
			while (it.hasNext()) {
				PromotionLine oldLine = (PromotionLine) it.next();
				oldLine.setDeleted(true);
				promotion.addPromotionLine(oldLine);
			}
		}
		return super.update(object, context);
	}

}
