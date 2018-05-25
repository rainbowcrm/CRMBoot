package com.rainbow.crm.hibernate;

import java.util.List;

import javax.servlet.ServletContext;

import com.rainbow.crm.servicerequest.model.ServiceRequest;
import com.rainbow.crm.servicerequest.model.ServiceRequestLine;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.rainbow.crm.abstratcs.model.CRMModelObject;
import com.rainbow.crm.address.model.Address;
import com.rainbow.crm.alert.model.Alert;
import com.rainbow.crm.brand.model.Brand;
import com.rainbow.crm.carrier.model.Carrier;
import com.rainbow.crm.category.model.Category;
import com.rainbow.crm.common.finitevalue.FiniteValue;
import com.rainbow.crm.company.model.Company;
import com.rainbow.crm.contact.model.Contact;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriod;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodBrand;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodCategory;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodDivision;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodLine;
import com.rainbow.crm.corpsalesperiod.model.CorpSalesPeriodProduct;
import com.rainbow.crm.custcategory.model.CustCategory;
import com.rainbow.crm.customer.model.Customer;
import com.rainbow.crm.division.model.Division;
import com.rainbow.crm.document.model.Document;
import com.rainbow.crm.enquiry.model.Enquiry;
import com.rainbow.crm.enquiry.model.EnquiryLine;
import com.rainbow.crm.expensehead.model.ExpenseHead;
import com.rainbow.crm.expensevoucher.model.ExpenseVoucher;
import com.rainbow.crm.expensevoucher.model.ExpenseVoucherLine;
import com.rainbow.crm.feedback.model.FeedBack;
import com.rainbow.crm.feedback.model.FeedBackLine;
import com.rainbow.crm.filter.model.CRMFilter;
import com.rainbow.crm.filter.model.CRMFilterDetails;
import com.rainbow.crm.followup.model.Followup;
import com.rainbow.crm.inventory.model.Inventory;
import com.rainbow.crm.item.model.Item;
import com.rainbow.crm.item.model.ItemAttribute;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.loyalty.model.Loyalty;
import com.rainbow.crm.product.model.Product;
import com.rainbow.crm.product.model.ProductAttribute;
import com.rainbow.crm.product.model.ProductFAQ;
import com.rainbow.crm.product.model.ProductPriceRange;
import com.rainbow.crm.promotion.model.Promotion;
import com.rainbow.crm.promotion.model.PromotionLine;
import com.rainbow.crm.purchase.model.Purchase;
import com.rainbow.crm.purchase.model.PurchaseLine;
import com.rainbow.crm.reasoncode.model.ReasonCode;
import com.rainbow.crm.sales.model.Sales;
import com.rainbow.crm.sales.model.SalesLine;
import com.rainbow.crm.saleslead.model.SalesLead;
import com.rainbow.crm.saleslead.model.SalesLeadLine;
import com.rainbow.crm.salesperiod.model.SalesPeriod;
import com.rainbow.crm.salesperiod.model.SalesPeriodAssociate;
import com.rainbow.crm.salesperiod.model.SalesPeriodBrand;
import com.rainbow.crm.salesperiod.model.SalesPeriodCategory;
import com.rainbow.crm.salesperiod.model.SalesPeriodLine;
import com.rainbow.crm.salesperiod.model.SalesPeriodProduct;
import com.rainbow.crm.salesperiod.model.SalesPeriodTerritory;
import com.rainbow.crm.salespitch.model.Salespitch;
import com.rainbow.crm.salesportfolio.model.SalesPortfolio;
import com.rainbow.crm.salesportfolio.model.SalesPortfolioLine;
import com.rainbow.crm.territory.model.Territory;
import com.rainbow.crm.territory.model.TerritoryLine;
import com.rainbow.crm.topic.model.Topic;
import com.rainbow.crm.topic.model.TopicLine;
import com.rainbow.crm.uom.model.UOM;
import com.rainbow.crm.user.model.User;
import com.rainbow.crm.vendor.model.Vendor;
import com.rainbow.crm.wishlist.model.WishList;
import com.rainbow.crm.wishlist.model.WishListLine;
import com.rainbow.framework.query.model.QueryCondition;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.utils.Utils;

public  abstract class HibernateDAO  extends ORMDAO{
	
    private static SessionFactory sessionFactory = null;

    public  SessionFactory getSessionFactory() {
		return sessionFactory;
	}
    
    public static SessionFactory getHibernateSessionFactory() 
    {
    	return sessionFactory;
    }

	public  void setSessionFactory(SessionFactory sessionFactory) {
		//this.sessionFactory = sessionFactory;
	}
    
    protected void closeSession(Session session,boolean commitTransaction) {
    	if (commitTransaction && session.getTransaction() !=null ) {
    		session.getTransaction().commit();
    	} else if (!commitTransaction && session.getTransaction() !=null ) {
    		session.getTransaction().rollback();
    	} 
    	session.close();
    }
     
    protected Session openSession(boolean newTransaction) {
    	Session session = sessionFactory.openSession() ;
    	if (newTransaction) {
    		session.beginTransaction();
    	}
    	return session;
    }

    protected Session getCurrentSession() {
    	return sessionFactory.getCurrentSession();
    }
    
    public static void instantiate(ServletContext ctx) {
    	if (sessionFactory == null) {
    		String hibernatePath = ctx.getInitParameter("hibernateConfig");
    		Configuration  configuration = new Configuration().configure( );
    		configuration.addClass(FiniteValue.class).addResource("com/rainbow/crm/common/finitevalue/FiniteValue.hbm.xml");
    		configuration.addClass(User.class).addResource("com/rainbow/crm/user/model/User.hbm.xml");
    		configuration.addClass(Company.class).addResource("com/rainbow/crm/company/model/Company.hbm.xml");
    		configuration.addClass(CRMFilter.class).addResource("com/rainbow/crm/filter/model/CRMFilter.hbm.xml");
    		configuration.addClass(CRMFilterDetails.class).addResource("com/rainbow/crm/filter/model/CRMFilterDetails.hbm.xml");
    		configuration.addClass(Division.class).addResource("com/rainbow/crm/division/model/Division.hbm.xml");
    		configuration.addClass(Brand.class).addResource("com/rainbow/crm/brand/model/Brand.hbm.xml");
    		configuration.addClass(Category.class).addResource("com/rainbow/crm/category/model/Category.hbm.xml");
    		configuration.addClass(ExpenseHead.class).addResource("com/rainbow/crm/expensehead/model/ExpenseHead.hbm.xml");
    		configuration.addClass(Product.class).addResource("com/rainbow/crm/product/model/Product.hbm.xml");
    		configuration.addClass(ProductFAQ.class).addResource("com/rainbow/crm/product/model/ProductFAQ.hbm.xml");
    		configuration.addClass(ProductPriceRange.class).addResource("com/rainbow/crm/product/model/ProductPriceRange.hbm.xml");
    		configuration.addClass(ProductAttribute.class).addResource("com/rainbow/crm/product/model/ProductAttribute.hbm.xml");
    		configuration.addClass(UOM.class).addResource("com/rainbow/crm/uom/model/UOM.hbm.xml");
    		configuration.addClass(Sku.class).addResource("com/rainbow/crm/item/model/Sku.hbm.xml");
    		configuration.addClass(Item.class).addResource("com/rainbow/crm/item/model/Item.hbm.xml");
    		configuration.addClass(ItemAttribute.class).addResource("com/rainbow/crm/item/model/ItemAttribute.hbm.xml");
    		configuration.addClass(Vendor.class).addResource("com/rainbow/crm/vendor/model/Vendor.hbm.xml");
    		configuration.addClass(Carrier.class).addResource("com/rainbow/crm/carrier/model/Carrier.hbm.xml");
    		configuration.addClass(Customer.class).addResource("com/rainbow/crm/customer/model/Customer.hbm.xml");
    		configuration.addClass(Address.class).addResource("com/rainbow/crm/address/model/Address.hbm.xml");
    		configuration.addClass(Inventory.class).addResource("com/rainbow/crm/inventory/model/Inventory.hbm.xml");
    		configuration.addClass(Purchase.class).addResource("com/rainbow/crm/purchase/model/Purchase.hbm.xml");
    		configuration.addClass(PurchaseLine.class).addResource("com/rainbow/crm/purchase/model/PurchaseLine.hbm.xml");
    		configuration.addClass(com.rainbow.framework.query.model.Query.class).addResource("com/rainbow/framework/query/model/Query.hbm.xml");
    		configuration.addClass(QueryCondition.class).addResource("com/rainbow/framework/query/model/QueryCondition.hbm.xml");
    		configuration.addClass(CorpSalesPeriod.class).addResource("com/rainbow/crm/corpsalesperiod/model/CorpSalesPeriod.hbm.xml");
    		configuration.addClass(CorpSalesPeriodLine.class).addResource("com/rainbow/crm/corpsalesperiod/model/CorpSalesPeriodLine.hbm.xml");
    		configuration.addClass(CorpSalesPeriodDivision.class).addResource("com/rainbow/crm/corpsalesperiod/model/CorpSalesPeriodDivision.hbm.xml");
    		configuration.addClass(CorpSalesPeriodBrand.class).addResource("com/rainbow/crm/corpsalesperiod/model/CorpSalesPeriodBrand.hbm.xml");
    		configuration.addClass(CorpSalesPeriodCategory.class).addResource("com/rainbow/crm/corpsalesperiod/model/CorpSalesPeriodCategory.hbm.xml");
    		configuration.addClass(CorpSalesPeriodProduct.class).addResource("com/rainbow/crm/corpsalesperiod/model/CorpSalesPeriodProduct.hbm.xml");
    		configuration.addClass(SalesPeriod.class).addResource("com/rainbow/crm/salesperiod/model/SalesPeriod.hbm.xml");
    		configuration.addClass(SalesPeriodLine.class).addResource("com/rainbow/crm/salesperiod/model/SalesPeriodLine.hbm.xml");
    		configuration.addClass(SalesPeriodAssociate.class).addResource("com/rainbow/crm/salesperiod/model/SalesPeriodAssociate.hbm.xml");
    		configuration.addClass(SalesPeriodTerritory.class).addResource("com/rainbow/crm/salesperiod/model/SalesPeriodTerritory.hbm.xml");
    		configuration.addClass(SalesPeriodBrand.class).addResource("com/rainbow/crm/salesperiod/model/SalesPeriodBrand.hbm.xml");
    		configuration.addClass(SalesPeriodCategory.class).addResource("com/rainbow/crm/salesperiod/model/SalesPeriodCategory.hbm.xml");
    		configuration.addClass(SalesPeriodProduct.class).addResource("com/rainbow/crm/salesperiod/model/SalesPeriodProduct.hbm.xml");
    		configuration.addClass(SalesPortfolio.class).addResource("com/rainbow/crm/salesportfolio/model/SalesPortfolio.hbm.xml");
    		configuration.addClass(SalesPortfolioLine.class).addResource("com/rainbow/crm/salesportfolio/model/SalesPortfolioLine.hbm.xml");
    		configuration.addClass(Sales.class).addResource("com/rainbow/crm/sales/model/Sales.hbm.xml");
    		configuration.addClass(SalesLine.class).addResource("com/rainbow/crm/sales/model/SalesLine.hbm.xml");
    		configuration.addClass(FeedBack.class).addResource("com/rainbow/crm/feedback/model/FeedBack.hbm.xml");
    		configuration.addClass(FeedBackLine.class).addResource("com/rainbow/crm/feedback/model/FeedBackLine.hbm.xml");
			configuration.addClass(ServiceRequest.class).addResource("com/rainbow/crm/servicerequest/model/ServiceRequest.hbm.xml");
			configuration.addClass(ServiceRequestLine.class).addResource("com/rainbow/crm/servicerequest/model/ServiceRequestLine.hbm.xml");
    		configuration.addClass(Topic.class).addResource("com/rainbow/crm/topic/model/Topic.hbm.xml");
    		configuration.addClass(TopicLine.class).addResource("com/rainbow/crm/topic/model/TopicLine.hbm.xml");
    		configuration.addClass(WishList.class).addResource("com/rainbow/crm/wishlist/model/WishList.hbm.xml");
    		configuration.addClass(WishListLine.class).addResource("com/rainbow/crm/wishlist/model/WishListLine.hbm.xml");
    		configuration.addClass(Promotion.class).addResource("com/rainbow/crm/promotion/model/Promotion.hbm.xml");
    		configuration.addClass(PromotionLine.class).addResource("com/rainbow/crm/promotion/model/PromotionLine.hbm.xml");
    		configuration.addClass(SalesLead.class).addResource("com/rainbow/crm/saleslead/model/SalesLead.hbm.xml");
    		configuration.addClass(SalesLeadLine.class).addResource("com/rainbow/crm/saleslead/model/SalesLeadLine.hbm.xml");
    		configuration.addClass(ExpenseVoucher.class).addResource("com/rainbow/crm/expensevoucher/model/ExpenseVoucher.hbm.xml");
    		configuration.addClass(ExpenseVoucherLine.class).addResource("com/rainbow/crm/expensevoucher/model/ExpenseVoucherLine.hbm.xml");
    		configuration.addClass(Territory.class).addResource("com/rainbow/crm/territory/model/Territory.hbm.xml");
    		configuration.addClass(TerritoryLine.class).addResource("com/rainbow/crm/territory/model/TerritoryLine.hbm.xml");
    		configuration.addClass(Sales.class).addResource("com/rainbow/crm/distributionorder/model/DistributionOrder.hbm.xml");
    		configuration.addClass(SalesLine.class).addResource("com/rainbow/crm/distributionorder/model/DistributionOrderLine.hbm.xml");
    		configuration.addClass(Followup.class).addResource("com/rainbow/crm/followup/model/Followup.hbm.xml");
    		configuration.addClass(Enquiry.class).addResource("com/rainbow/crm/enquiry/model/Enquiry.hbm.xml");
    		configuration.addClass(EnquiryLine.class).addResource("com/rainbow/crm/enquiry/model/EnquiryLine.hbm.xml");
    		configuration.addClass(Document.class).addResource("com/rainbow/crm/document/model/Document.hbm.xml");
    		configuration.addClass(Salespitch.class).addResource("com/rainbow/crm/salespitch/model/Salespitch.hbm.xml");
    		configuration.addClass(CustCategory.class).addResource("com/rainbow/crm/custcategory/model/CustCategory.hbm.xml");
    		configuration.addClass(CustCategory.class).addResource("com/rainbow/crm/custcategory/model/CustCategoryCondition.hbm.xml");
    		configuration.addClass(Contact.class).addResource("com/rainbow/crm/contact/model/Contact.hbm.xml");
    		configuration.addClass(Alert.class).addResource("com/rainbow/crm/alert/model/Alert.hbm.xml");
    		configuration.addClass(ReasonCode.class).addResource("com/rainbow/crm/reasoncode/model/ReasonCode.hbm.xml");
    		configuration.addClass(Loyalty.class).addResource("com/rainbow/crm/loyalty/model/Loyalty.hbm.xml");
    		sessionFactory = configuration.configure().buildSessionFactory();
    		
    		//sessionFactory.getAllClassMetadata().put(key, value)
    	}
    }
    
    	
}