package com.rainbow.crm.distributionorder.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;










import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;











import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;











import com.rainbow.crm.abstratcs.model.CRMItemLine;
import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.address.model.Address;
import com.rainbow.crm.address.service.IAddressService;
import com.rainbow.crm.alert.model.Alert;
import com.rainbow.crm.carrier.model.Carrier;
import com.rainbow.crm.carrier.service.ICarrierService;
import com.rainbow.crm.common.AbstractService;
import com.rainbow.crm.common.AbstractionTransactionService;
import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CRMDBException;
import com.rainbow.crm.common.CRMValidator;
import com.rainbow.crm.common.CommonErrorCodes;
import com.rainbow.crm.common.Externalize;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.common.messaging.CRMMessageSender;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.company.service.ICompanyService;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.customer.service.ICustomerService;
import com.rainbow.crm.database.GeneralSQLs;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.division.service.IDivisionService;
import com.rainbow.crm.hibernate.ORMDAO;
import com.rainbow.crm.inventory.model.InventoryUpdateObject;
import com.rainbow.crm.item.dao.ItemImageSQL;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.model.ItemImage;
import com.rainbow.crm.item.service.ISkuService;
import com.rainbow.crm.logger.Logwriter;
import com.rainbow.crm.product.validator.ProductValidator;
import com.rainbow.crm.distributionorder.dao.DistributionOrderDAO;
import com.rainbow.crm.distributionorder.jdbc.DistributionOrderSQL;
import com.rainbow.crm.distributionorder.model.DistributionOrder;
import com.rainbow.crm.distributionorder.model.DistributionOrderLine;
import com.rainbow.crm.distributionorder.validator.DistributionOrderErrorCodes;
import com.rainbow.crm.distributionorder.validator.DistributionOrderValidator;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.user.service.IUserService;
import com.rainbow.crm.vendor.model.Vendor;
import com.rainbow.crm.vendor.service.IVendorService;
import com.rainbow.framework.nextup.NextUpGenerator;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

@Transactional
public class DistributionOrderService extends AbstractionTransactionService implements IDistributionOrderService{
	
	@Override
	protected String getTableName() {
		return "DistributionOrder";
	}

	

	@Override
	public Object getById(Object PK) {
		return getDAO().getById(PK);
	}

	@Override
	public List<CRMModelObject> listData(int from, int to,
			String whereCondition, CRMContext context, SortCriteria sortCriteria) {
		return super.listData("DistributionOrder", from, to, whereCondition, context,sortCriteria);
	}

	@Override
	public List<RadsError> validateforCreate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((DistributionOrder)object).setCompany(company);
		DistributionOrderValidator validator = new DistributionOrderValidator(context);
		return validator.validateforCreate(object);
	}
	
	

	@Override
	public List<RadsError> validateforUpdate(CRMModelObject object,
			CRMContext context) {
		ICompanyService compService = (ICompanyService)SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		((DistributionOrder)object).setCompany(company);
		DistributionOrderValidator validator = new DistributionOrderValidator(context);
		return validator.validateforUpdate(object);
	}

	@Override
	protected ORMDAO getDAO() {
//	return new DistributionOrderDAO();
	return (DistributionOrderDAO) SpringObjectFactory.INSTANCE.getInstance("DistributionOrderDAO");
	}

	
	@Override
	public List<RadsError> adaptfromUI(CRMContext context,ModelObject obj) {
		DistributionOrder object = (DistributionOrder) obj;
		ICompanyService compService = (ICompanyService) SpringObjectFactory.INSTANCE.getInstance("ICompanyService");
		Company company = (Company)compService.getById(context.getLoggedinCompany());
		object.setCompany(company);
				
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
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), DistributionOrderErrorCodes.FIELD_NOT_VALID , "Division"));
			}else {
				object.setDivision(division);
			}
		}
		if (object.getAddress() != null) {
			int addressId  = object.getAddress().getId() ;
			IAddressService addressSErvice =(IAddressService) SpringObjectFactory.INSTANCE.getInstance("IAddressService");
			Address address = null;
			if (addressId > 0 )
				address = (Address)addressSErvice.getById(addressId);
			else
				address  = (Address)addressSErvice.getByBusinessKey(object.getAddress(), context);
			if(address == null){
				ans.add(CRMValidator.getErrorforCode(context.getLocale(), DistributionOrderErrorCodes.FIELD_NOT_VALID , "Address"));
			}else {
				object.setAddress(address);
			}
		}
		if (object.getCustomer() != null) {
			String phone = object.getCustomer().getPhone();
			ICustomerService customerService = (ICustomerService) SpringObjectFactory.INSTANCE.getInstance("ICustomerService");
			Customer customer = customerService.getByPhone(context.getLoggedinCompany(), phone);
			if (customer != null)
				 object.setCustomer(customer);
		}
		if (object.getCarrier() != null ) {
			int carrierId = object.getCarrier().getId(); 
			ICarrierService carrierService = (ICarrierService) SpringObjectFactory.INSTANCE.getInstance("ICarrierService");
			Carrier carrier; 
			if (carrierId > 0)
				carrier =(Carrier) carrierService.getById(carrierId);
			else
				carrier =(Carrier) carrierService.getByCode(context.getLoggedinCompany(), object.getCarrier().getCode());
			if (carrier != null)
				 object.setCarrier(carrier);
		}
		
		Externalize externalize = new Externalize(); ;
		
		if(!Utils.isNullSet(object.getDistributionOrderLines())){
			int lineNo=1;
			for (DistributionOrderLine line: object.getDistributionOrderLines()) {
				line.setCompany(company);
				line.setDocNumber(object.getDocNumber());
				line.setLineNumber(lineNo ++);
				if(line.getSku() == null ) {
					ans.add(CRMValidator.getErrorforCode(context.getLocale(), DistributionOrderErrorCodes.FIELD_NOT_VALID , externalize.externalize(context, "Item")));
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
		DistributionOrder distributionOrder = (DistributionOrder)object ;
		if (Utils.isNull(distributionOrder.getDocNumber())) {
			String bKey = NextUpGenerator.getNextNumber("Distribution Order", context, distributionOrder.getDivision());
			distributionOrder.setDocNumber(bKey);
		}
		if (!Utils.isNullSet(distributionOrder.getDistributionOrderLines())) {
			int pk = GeneralSQLs.getNextPKValue("DistributionOrder") ;
			distributionOrder.setId(pk);
			for (DistributionOrderLine  line : distributionOrder.getDistributionOrderLines()) {
				int linePK = GeneralSQLs.getNextPKValue( "DistributionOrder_Lines") ;
				line.setId(linePK);
				line.setDistributionOrderDoc(distributionOrder);
				line.setDocNumber(distributionOrder.getDocNumber());
			}
		}
		TransactionResult result= super.create(object, context);
		InventoryUpdateObject invObject = new InventoryUpdateObject();
		invObject.setCompany(distributionOrder.getCompany());
		invObject.setContext(context);
		invObject.setDivision(distributionOrder.getDivision());
		invObject.setAddition(false);
		invObject.setReserve(true);
		invObject.setItemLines(distributionOrder
				.getDistributionOrderLines());
		CRMMessageSender.sendMessage(invObject);
		raiseAlert(distributionOrder, context);
		
		
		return result; 
	}

	  private void raiseAlert(DistributionOrder lead, CRMContext context) {
		  Alert alert = new Alert();
		  alert.setCompany(lead.getCompany());
		  alert.setType (new FiniteValue( CRMConstants.ALERT_TYPE.DISTOR));
		  alert.setActionDate(lead.getOrderDate());
		  alert.setDivision(lead.getDivision());
		  alert.setRaisedDate(new java.util.Date());
		  alert.setData("New Distribution Order-" +  lead.getDocNumber());
		  alert.setUrl("./rdscontroller?page=newdistributionorder&id="+lead.getId() +"&navAction=loadfromPK");
		  CRMMessageSender.sendMessage(alert);
	  }
	
	@Override
	public TransactionResult update(CRMModelObject object, CRMContext context) {
		DistributionOrder distributionOrder = (DistributionOrder)object ;
		DistributionOrder oldObject = (DistributionOrder)getById(distributionOrder.getPK());
		//DistributionOrder oldInvObj = (DistributionOrder)oldObject.clone();
		if (!Utils.isNullSet(distributionOrder.getDistributionOrderLines())) {
			int  ct = 0;
			Iterator it = oldObject.getDistributionOrderLines().iterator() ;
			for (DistributionOrderLine  line : distributionOrder.getDistributionOrderLines()) {
				DistributionOrderLine oldLine = null ;
				if (it.hasNext()) {
					oldLine= (DistributionOrderLine) it.next() ;
				}
				line.setDistributionOrderDoc(distributionOrder);
				if (oldLine != null) {
					line.setId(oldLine.getId());
					line.setObjectVersion(oldLine.getObjectVersion());
				}else {
					int linePK = GeneralSQLs.getNextPKValue( "DistributionOrder_Lines") ;
					line.setId(linePK);
				}
			}
			while (it.hasNext()) {
				DistributionOrderLine oldLine= (DistributionOrderLine) it.next() ;
				oldLine.setVoided(true);
				distributionOrder.addDistributionOrderLine(oldLine);
			}
		}
		if (distributionOrder.getStatus().equals(
				new FiniteValue(CRMConstants.DO_STATUS.PICKED))) {
			InventoryUpdateObject invObject = new InventoryUpdateObject();
			invObject.setCompany(distributionOrder.getCompany());
			invObject.setContext(context);
			invObject.setDivision(distributionOrder.getDivision());
			invObject.setAddition(false);
			invObject.setFulFilll(true);
			invObject.setItemLines(distributionOrder
					.getDistributionOrderLines());
			CRMMessageSender.sendMessage(invObject);
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
	public DistributionOrder createDOfromSalesOrder(Sales sales,
			CRMContext context) {
			Division division = findDivisionForFullInventory(sales, context);
			if(division != null ) {
				DistributionOrder order = createDOHeader(sales, division);
				Set<DistributionOrderLine> lines = createLines(sales, context);
				order.setDistributionOrderLines(lines);
				List<RadsError> errors =validateforCreate(order, context);
				if (Utils.isNullList(errors)) {
					create(order, context) ;
				}
				return order;
			}else {
				createPartialDOs(sales, context);
			}
			return null;
	}
	
	private Set<DistributionOrderLine> createLines(Sales sales,CRMContext context) {
		Set<DistributionOrderLine>  lines= new LinkedHashSet<DistributionOrderLine> ();
		AtomicInteger counter =new AtomicInteger(0) ;
		sales.getSalesLines().forEach(salesLine  -> { 
			DistributionOrderLine line = new DistributionOrderLine();
			line.setCompany(sales.getCompany());
			line.setSku(salesLine.getSku());
			line.setQty(salesLine.getQty());
			line.setLineNumber(counter.incrementAndGet());
			lines.add(line);
		});
		return lines;
	}
	
	
	private DistributionOrder createDOHeader(Sales sales, Division division) {
		DistributionOrder order = new DistributionOrder();
		order.setCompany(sales.getCompany());
		order.setDivision(division);
		order.setAddress(sales.getDeliveryAddress());
		order.setCustomer(sales.getCustomer());
		order.setSales(sales);
		order.setOrderDate(new Date());
		order.setStatus(new FiniteValue(CRMConstants.DO_STATUS.RELEASED));
		return order;
	}
	
	private void createPartialDOs(Sales sales,CRMContext context) {
		
	}

	
	private Division findDivisionForFullInventory(Sales sales,CRMContext context) {
		String allowforOtherDivStr = ConfigurationManager.getConfig(ConfigurationManager.ALLOW_SHIPPING_FROMMULTI_DIV, context);
		Boolean  allowforOtherDiv = Utils.getBooleanValue(allowforOtherDivStr);
		
		if(allowforOtherDiv) {
			int divisionId = DistributionOrderSQL.getDivisionWithInventory(sales.getId(), context.getLoggedinCompany(), sales.getSalesLines().size());
			if (divisionId > -1) {
				IDivisionService divisionService = (IDivisionService)SpringObjectFactory.INSTANCE.getInstance("IDivisionService") ;
				Division division = (Division)divisionService.getById(divisionId);
				return division;
			}
		}else
			return sales.getDivision() ;
		return null;
		
	}

	@Override
	public List<RadsError> pick(DistributionOrder order, CRMContext context) {
		DistributionOrder oldOrder = (DistributionOrder) getById(order.getPK());
		oldOrder.getDistributionOrderLines().forEach(doLine -> {
			AtomicBoolean picked= new AtomicBoolean(false);
				order.getDistributionOrderLines().forEach( innerLine ->  { 
					if (innerLine.getLineNumber() == doLine.getLineNumber() &&
							innerLine.getSku().getId() == doLine.getSku().getId() && innerLine.isPicked()) {
						picked.set(true);
					}
				} );
				if (picked.get() == true) {
					doLine.setPickDate(new Date());
				}
		});
		oldOrder.setStatus(new FiniteValue(getStatus(oldOrder)));
		update(oldOrder,context);
		return null;
	}
	
	
	
	@Override
	public List<RadsError> endShipping(DistributionOrder order,
			CRMContext context) {
		
		DistributionOrder oldOrder = (DistributionOrder) getById(order.getPK());
		DistributionOrderValidator validator = new DistributionOrderValidator(context);
		List<RadsError> errors = validator.readyToShip(order);
		if(Utils.isNullList(errors)) {
			oldOrder.setCarrier(order.getCarrier());
			oldOrder.setShipmentRefNumber(order.getShipmentRefNumber());
			oldOrder.setShippingCharges(order.getShippingCharges());
			oldOrder.setStatus(new FiniteValue(CRMConstants.DO_STATUS.SHIPPD));
			update(oldOrder,context);
		}
		return errors;
	}

	@Override
	public List<RadsError> startShipping(DistributionOrder order,
			CRMContext context) {
		DistributionOrder oldOrder = (DistributionOrder) getById(order.getPK());
		oldOrder.setShippingDate(new Date());
		oldOrder.setStatus(new FiniteValue(getStatus(oldOrder)));
		update(oldOrder,context);
		return null;
	}

	@Override
	public List<RadsError> pack(DistributionOrder order, CRMContext context) {
		DistributionOrder oldOrder = (DistributionOrder) getById(order.getPK());
		oldOrder.setPackDate(new Date());
		oldOrder.setStatus(new FiniteValue(getStatus(oldOrder)));
		update(oldOrder,context);
		return null;
	}	
	
	private String getStatus(DistributionOrder order) {
		if (order.getPackDate() == null ) {
			AtomicBoolean oneLeft = new AtomicBoolean(false);
			AtomicBoolean onePicked = new AtomicBoolean(false);
			order.getDistributionOrderLines().forEach( line ->  { 
				if(line.getPickDate()!= null)
					onePicked.set(true);
				else
					oneLeft.set(true);
			});
			if (onePicked.get() && !oneLeft.get()){
				return CRMConstants.DO_STATUS.PICKED;
			} else if (onePicked.get() && oneLeft.get()){
				return CRMConstants.DO_STATUS.PICKING;
			}else if (!onePicked.get()) {
				return CRMConstants.DO_STATUS.RELEASED;
			}
		} else if (order.getPackDate()!= null && order.getShippingDate() == null ) {
			return CRMConstants.DO_STATUS.PACKING;
		} else if (order.getPackDate()!= null && order.getShippingDate() != null ) {
			return CRMConstants.DO_STATUS.SHIPPING;
		}
		return order.getStatus().getCode() ;
		
	}

	@Override
	public String generateShippingLabel(DistributionOrder order,
			CRMContext context) {
		VelocityEngine ve = new VelocityEngine();
		Externalize externalize = new Externalize();
        try {
        IUserService userService = (IUserService)SpringObjectFactory.INSTANCE.getInstance("IUserService");
        User user = (User)userService.getById(context.getUser());
        String path = CRMAppConfig.INSTANCE.getProperty("VelocityTemplatePath");
        ve.setProperty("file.resource.loader.path", path);
        ve.init();
        Template t = ve.getTemplate("shippingLabel.vm" );
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("reciptent", order.getAddress().getRecipient());
        velocityContext.put("recaddress1", order.getAddress().getAddress1());
        velocityContext.put("recaddress2", order.getAddress().getAddress2());
        velocityContext.put("reccity", order.getAddress().getCity());
        velocityContext.put("reczipCode", order.getAddress().getZipcode());
        velocityContext.put("recphone", order.getAddress().getPhone());
        
        velocityContext.put("company",order.getCompany().getName());
        velocityContext.put("divisionName",order.getDivision().getName());
        velocityContext.put("address1", order.getDivision().getAddress1());
        velocityContext.put("address2", order.getDivision().getAddress2());
        velocityContext.put("city", order.getDivision().getCity());
        velocityContext.put("zipCode", order.getDivision().getZipCode());
        velocityContext.put("phone", order.getDivision().getPhone());
        velocityContext.put("From", externalize.externalize(context, "From"));
        velocityContext.put("To", externalize.externalize(context, "To"));
        velocityContext.put("lines", order.getDistributionOrderLines());
        StringWriter writer = new StringWriter();
        t.merge( velocityContext, writer );
        String content=  writer.toString();
        return content;
        
        }catch(Exception ex){
        	Logwriter.INSTANCE.error(ex);
        }

        return "";

	}
	
	
	
}
