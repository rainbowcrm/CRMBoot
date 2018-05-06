package com.rainbow.crm.topic.service;

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
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.Externalize;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
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
import com.rainbow.crm.lookups.LookupBrands;
import com.rainbow.crm.lookups.LookupCategories;
import com.rainbow.crm.lookups.LookupItems;
import com.rainbow.crm.lookups.LookupProducts;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.service.IProductService;
import com.rainbow.crm.topic.dao.TopicDAO;
import com.rainbow.crm.topic.model.Topic;
import com.rainbow.crm.topic.model.TopicLine;
import com.rainbow.crm.topic.validator.TopicErrorCodes;
import com.rainbow.crm.topic.validator.TopicValidator;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.rainbow.framework.nextup.NextUpGenerator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

@Transactional
public class TopicService extends AbstractService implements
		ITopicService {

	@Override
	public List<Topic> getUsersforItem(Sku sku, int divisionId,
			Date date) {
		List<Topic> results = new ArrayList<Topic>();
		int itemId = sku.getItem().getId();
		int productId = sku.getItem().getProduct().getId();
		int brandId = sku.getItem().getBrand().getId();
		int categoryId = sku.getItem().getProduct().getCategory().getId();
		List<Object> objects = ((TopicDAO) getDAO())
				.getPortfoliosforsku(divisionId, itemId, productId, brandId,
						categoryId);
		objects.forEach(object -> {
			Object[] objArray = (Object[]) object;
			results.add((Topic) objArray[0]);
		});
		return results;
	}

	
	@Override
	public List<Topic> getOpenTopics(CRMContext context) {
		TopicDAO topicDAO =(TopicDAO) getDAO();
		return topicDAO.getOpenTopics(context.getLoggedinCompany());
	}

	@Override
	public List<TopicLine> getUpdatedReplies(Topic topic, int replyRead,
			CRMContext context) {
		TopicDAO topicDAO =(TopicDAO) getDAO();
		return topicDAO.getUpdatedReplies(topic.getId(), replyRead) ;
	}


	
	@Override
	protected String getTableName() {
		return "Topic";
	}
	

	@Override
	public Object getById(Object PK) {
		Object object = getDAO().getById(PK);
		adaptToUI(null, (Topic) object);
		return object;
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("Topic", from, to, whereCondition,
				context,sortCriteria);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService) SpringObjectFactory.INSTANCE
				.getInstance("ICompanyService");
		Company company = (Company) compService.getById(context
				.getLoggedinCompany());
		((Topic) object).setCompany(company);
		((Topic) object).setOwner(context.getLoggedInUser());
		if (context.getLoggedInUser().getDivision()  == null ) {
			((Topic) object).setDivision(CommonUtil.getDefaultDivision(context));
		}else {
			((Topic) object).setDivision(context.getLoggedInUser().getDivision());
		}
		((Topic) object).setClosed(false);
		((Topic) object).setTopicDate(new java.util.Date());
		List<RadsError> erros = adaptfromUI(context, object);
		if (!Utils.isNullList(erros) ) {
			return erros;
		}
		TopicValidator validator = new TopicValidator(context);
		return validator.validateforCreate(object);
		
	}

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService) SpringObjectFactory.INSTANCE
				.getInstance("ICompanyService");
		Company company = (Company) compService.getById(context
				.getLoggedinCompany());
		((Topic) object).setCompany(company);
		TopicValidator validator = new TopicValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	protected ORMDAO getDAO() {
		// return new TopicDAO();
		return (TopicDAO) SpringObjectFactory.INSTANCE
				.getInstance("TopicDAO");
	}

	@Override
	public List<RadsError> adaptToUI(CRMContext context, ModelObject object) {
		Topic topic = (Topic) object;
			String value = getTopicValue(topic.getPortfolioType(),
					topic.getPortfolioKey());
		return null;
	}

	@Override
	public List<RadsError> adaptfromUI(CRMContext context, ModelObject object) {
		return adaptfromUI(context, (Topic) object);
	}

	private List<RadsError> adaptfromUI(CRMContext context,
			Topic object) {
		ICompanyService compService = (ICompanyService) SpringObjectFactory.INSTANCE
				.getInstance("ICompanyService");
		Company company = (Company) compService.getById(context
				.getLoggedinCompany());
		object.setCompany(company);

		List<RadsError> ans = new ArrayList<RadsError>();
		/*if (object.getDivision() != null) {
			int divisionId = object.getDivision().getId();
			IDivisionService divisionService = (IDivisionService) SpringObjectFactory.INSTANCE
					.getInstance("IDivisionService");
			Division division = null;
			if (divisionId > 0)
				division = (Division) divisionService.getById(divisionId);
			else
				division = (Division) divisionService.getByBusinessKey(
						object.getDivision(), context);
			if (division == null) {
				ans.add(CRMValidator.getErrorforCode(context.getLocale(),
						TopicErrorCodes.FIELD_NOT_VALID, "Division"));
			} else {
				object.setDivision(division);
			}
		}*/
		Externalize externalize = new Externalize();
		;
		if (object.getOwner() != null
				&& !Utils.isNullString(object.getOwner().getUserId())) {
			IUserService userService = (IUserService) SpringObjectFactory.INSTANCE
					.getInstance("IUserService");
			User user = (User) userService
					.getById(object.getOwner().getUserId());
			object.setOwner(user);
		} else {
			ans.add(CRMValidator.getErrorforCode(context.getLocale(),
					TopicErrorCodes.FIELD_EMPTY,
					externalize.externalize(context, "User")));
		}

		if (object.getPortfolioType() == null
				|| object.getPortfolioType().getCode() == null) {
			ans.add(CRMValidator.getErrorforCode(context.getLocale(),
					TopicErrorCodes.FIELD_EMPTY,
					externalize.externalize(context, "Type")));
		}
		if (Utils.isNullString(object.getPortfolioValue())) {
			ans.add(CRMValidator.getErrorforCode(context.getLocale(),
					TopicErrorCodes.FIELD_EMPTY,
					externalize.externalize(context, "Value")));
		} else {

			String portfolioKey = getTopicKey(
					object.getPortfolioType(), object.getPortfolioValue(),
					context);
			if ("-1".equalsIgnoreCase(portfolioKey)) {
				ans.add(CRMValidator.getErrorforCode(
						context.getLocale(),
						TopicErrorCodes.VALUE_NOT_FOUND,
						externalize.externalize(context, "Value")));
			} else
				object.setPortfolioKey(portfolioKey);
		}

		if (!Utils.isNullSet(object.getTopicLines())) {
			int lineNo = 1;
			for (TopicLine line : object.getTopicLines()) {
				line.setCompany(company);
				line.setLineNumber(lineNo++);
			}
		}
		return ans;
	}

	private String getTopicKey(FiniteValue type, String value,
			CRMContext context) {
		if (CRMConstants.SALESPFTYPE.CATEGORY.equals(type.getCode())) {
			ICategoryService service = (ICategoryService) SpringObjectFactory.INSTANCE
					.getInstance("ICategoryService");
			Category object = new Category();
			object.setName(value);
			object = (Category) service.getByBusinessKey(object, context);
			if (object != null)
				return String.valueOf(object.getId());
			else
				return "-1";
		}
		if (CRMConstants.SALESPFTYPE.BRAND.equals(type.getCode())) {
			IBrandService service = (IBrandService) SpringObjectFactory.INSTANCE
					.getInstance("IBrandService");
			Brand object = new Brand();
			object.setName(value);
			object = (Brand) service.getByBusinessKey(object, context);
			if (object != null)
				return String.valueOf(object.getId());
			else
				return "-1";
		}
		if (CRMConstants.SALESPFTYPE.PRODUCT.equals(type.getCode())) {
			IProductService service = (IProductService) SpringObjectFactory.INSTANCE
					.getInstance("IProductService");
			Product object = new Product();
			object.setName(value);
			object = (Product) service.getByBusinessKey(object, context);
			if (object != null)
				return String.valueOf(object.getId());
			else
				return "-1";
		}
		if (CRMConstants.SALESPFTYPE.ITEM.equals(type.getCode())) {
			IItemService service = (IItemService) SpringObjectFactory.INSTANCE
					.getInstance("IItemService");
			Item object = new Item();
			object.setName(value);
			object = (Item) service.getByBusinessKey(object, context);
			if (object != null)
				return String.valueOf(object.getId());
			else
				return "-1";

		}
		return "-1";

	}

	
	
	@Override
	public List<RadsError> addNewReply(Topic topic, String reply,
			CRMContext context) {
		topic =(Topic) getDAO().getById(topic.getPK());
		TopicLine line= new TopicLine();
		line.setCompany(topic.getCompany());
		line.setRepliedBy(context.getLoggedInUser());
		line.setReply(reply);
		line.setLineNumber(topic.getTopicLines().size() + 1 );
		line.setReplyDate(new java.util.Date());
		int linePK = GeneralSQLs.getNextPKValue("Topic_Lines");
		line.setId(linePK);
		line.setTopic(topic);
		topic.addTopicLine(line);
		getDAO().update(topic);
		return null;
	}


	@Override
	public List<RadsError> closeTopic(Topic topic, CRMContext context) {
		topic =(Topic) getDAO().getById(topic.getPK());
		if(!context.getUser().equalsIgnoreCase(topic.getOwner().getUserId())  && !CommonUtil.isManagerRole(context.getLoggedInUser()) ){
			List<RadsError> errors = new ArrayList<>();
			CRMValidator validator = new TopicValidator(context);
			RadsError error  =validator.getErrorforCode(TopicErrorCodes.CANNOT_CLOSE_TOPIC);
			errors.add(error);
			return errors;
		}else {
			topic.setClosed(true);
			getDAO().update(topic);
		}
			
		return null;
	}


	private String getTopicValue(FiniteValue type, String id) {
		if (CRMConstants.SALESPFTYPE.CATEGORY.equals(type.getCode())) {
			ICategoryService service = (ICategoryService) SpringObjectFactory.INSTANCE
					.getInstance("ICategoryService");
			Category object = new Category();
			object.setId(Integer.parseInt(id));
			object = (Category) service.getById(object.getId());
			if (object != null)
				return String.valueOf(object.getName());
			else
				return "-1";
		}
		if (CRMConstants.SALESPFTYPE.BRAND.equals(type.getCode())) {
			IBrandService service = (IBrandService) SpringObjectFactory.INSTANCE
					.getInstance("IBrandService");
			Brand object = new Brand();
			object.setId(Integer.parseInt(id));
			object = (Brand) service.getById(object.getId());
			if (object != null)
				return String.valueOf(object.getName());
			else
				return "-1";
		}
		if (CRMConstants.SALESPFTYPE.PRODUCT.equals(type.getCode())) {
			IProductService service = (IProductService) SpringObjectFactory.INSTANCE
					.getInstance("IProductService");
			Product object = new Product();
			object.setId(Integer.parseInt(id));
			object = (Product) service.getById(object.getId());
			if (object != null)
				return String.valueOf(object.getName());
			else
				return "-1";
		}
		if (CRMConstants.SALESPFTYPE.ITEM.equals(type.getCode())) {
			IItemService service = (IItemService) SpringObjectFactory.INSTANCE
					.getInstance("IItemService");
			Item object = new Item();
			object.setId(Integer.parseInt(id));
			object = (Item) service.getById(object.getId());
			if (object != null)
				return String.valueOf(object.getName());
			else
				return "-1";

		}
		return "-1";

	}

	@Override
	public TransactionResult create(CRMModelObject object, CRMContext context) {
		Topic topic = (Topic) object;
		String bKey = NextUpGenerator.getNextNumber("Discussion Topics", context, topic.getDivision());
		topic.setRefNo(bKey);
		if (Utils.isNullSet(topic.getTopicLines())) {
			TopicLine line= new TopicLine();
			line.setCompany(topic.getCompany());
			line.setRepliedBy(topic.getOwner());
			line.setReply(topic.getQuestion());
			line.setLineNumber(1);
			line.setReplyDate(topic.getTopicDate());
			topic.addTopicLine(line);
		}
		if (!Utils.isNullSet(topic.getTopicLines())) {
			int pk = GeneralSQLs.getNextPKValue("Topics");
			topic.setId(pk);
			for (TopicLine line : topic
					.getTopicLines()) {
				int linePK = GeneralSQLs.getNextPKValue("Topic_Lines");
				line.setId(linePK);
				line.setTopic(topic);
			}
		}
		System.out.println(topic.toJSON()) ;
		TransactionResult result = super.create(object, context);
		return result;
	}

	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		Topic topic = (Topic) object;
		Topic oldObject = (Topic) getById(topic
				.getPK());
		if (!Utils.isNullSet(topic.getTopicLines())) {
			int ct = 0;
			Iterator it = oldObject.getTopicLines().iterator();
			for (TopicLine line : topic
					.getTopicLines()) {
				TopicLine oldLine = null;
				if (it.hasNext()) {
					oldLine = (TopicLine) it.next();
				}
				line.setTopic(topic);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				} else {
					int linePK = GeneralSQLs
							.getNextPKValue("Topic_Lines");
					line.setId(linePK);
				}
			}
			while (it.hasNext()) {
				TopicLine oldLine = (TopicLine) it.next();
				oldLine.setDeleted(true);
				topic.addTopicLine(oldLine);
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

}
