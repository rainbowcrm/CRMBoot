package com.rainbow.crm.common;

public class CRMConstants {

	public final static String FV_INDUSTRY_TYPE = "INDUSTRY";
	public final static String FV_DIVISION_TYPE = "DIVTYPE";
	public final static String FV_BUSINESS_TYPE = "BUSINESS";
	public final static String FV_ITEMCLASS_TYPE = "ITEMCLASS";
	public final static String FV_ROLE_TYPE = "ROLE";
	public final static String FV_UOM_TYPE = "UOMTYPE";
	public final static String FV_WISHLIST_REASONTYPE = "WISHREASON";
	public final static String FV_SALESCYCLE_STATUS = "SLCYCSTS";
	public final static String FV_CONTACT_TYPE = "CONTTYPE";
	public final static String FV_COMMUNICATION_MODE = "COMMMODE";
	public final static String FV_DELIVERY_MODE = "DLVMODE";
	public final static String FV_ORDERTYPE = "SLORDTYPE";
	public final static String FV_CONFIDENCE_LEVEL = "CONFLEVEL";
	public final static String FV_FOLLOWUP_RESULT = "FLPRESULT";
	public final static String FV_SUCCESS_REASON = "SUCCREAS";
	public final static String FV_FAILURE_REASON = "FAILREAS";
	public final static String FV_ALERT_TYPE = "ALTTYPE";
	public final static String FV_ALERT_STATUS = "ALTSTATUS";
	public final static String FV_EVALDATE = "EVALDATE";
	public final static String FV_EVALCRIT = "EVALCRT";
	public final static String FV_CONFVALTYPE = "CONFVALTYP";
	public final static String FV_SPFTYPE = "SPFTYPE";
	public final static String FV_EXP_VOUCHER_STATUS = "EXPSTATUS";
	public final static String FV_ENQUIRY_TYPE = "ENQTYPE";
	public final static String FV_ENQUIRY_SOURCE = "ENQSRC";
	public final static String FV_ENQUIRY_STATUS = "ENQSTATUS";
	public final static String FV_SPITCH_TYPE = "PITCHTYPE";
	public final static String FV_DOC_TYPE = "DOCTYPE";;
	public final static String FV_PROMO_TYPE = "PROMOTYPE";;
	public final static String FV_SALES_ORDER_ASSOCIATION="SLSORDASS";
	public final static String FV_REASON_TYPE="REASTYPE";
	public final static String FV_USRPRIV_TYPE= "USRPRIVTYP";
	public final static String FV_FACTOR_TYPE= "FACTTYPE";
	public final static String FV_FEEDBACK_ON =  "FDBACKON";
	public final static String FV_ORIENTATION= "BUSORIEN";
	public final static String FV_PRICESOURCE= "PRICSRC";
	public final static String FV_BUNDLEPRICING= "BUNDPRC";
	public final static String FV_SERVICESTATUS= "BUNDPRC";


	public final static class SERVICE_STATUS {
		public final static String REGISTERED ="SRVSTREG";
		public final static String ASSIGNED ="SRVSTASSN";
		public final static String IN_PROGRESS ="SRVSTIN";
		public final static String COMPLETED ="SRVSTCMP";
		public final static String REJECTED ="SRVSTRJC";

	}

	public final static class BUNDLE_PRICING {
		public final static String FIXED_PRICE ="FIXBDPRC";
		public final static String MASTER_PRICE ="MSTBDPRC";
	}
	
	public final static class PRICE_SOURCES {
		public final static String ITEM ="ITEMPRC";
		public final static String SKU ="SKUPRC";
	}
	public final static class BUSINESS_ORIENTATION {
		public final static String POSITIVE ="POSIT";
		public final static String NEGATIVE ="NEGAT";
		public final static String NOT_APPLICABLE ="NOTAPP";
	}
	
	public final static class FEEDBACK_ON {
		public final static String SALES_LINE ="FDBKSLSLN";
		public final static String SALES_ASSOCIATE ="FDBKSLASS";
		public final static String OVERALL ="FDBKOVRLL";
	}
	
	public final static class REASON_TYPE {
		public final static String SALESLEAD_REASON ="SLSLDREAS";
		public final static String FEEDBACK_REASON ="FDBREAS";
		public final static String SERVICE_REQUEST_REASON ="SRVRQREAS";
		public final static String EXCESS_DISC_REASON ="DISCREAS";
		public final static String EXPENSE_REJECTION_REASON ="EXPRJREAS";
		public final static String EXPENSE_APPROVAL_REASON ="EXPAPREAS";
		public final static String ENQUIRY_PROCESS_REASON ="ENQREAS";
	}
	
	public final static class FACTOR_TYPE {
		public final static String INTERNAL ="INTFACT";
		public final static String EXTERNAL ="EXTFACT";
		public final static String NOT_APPLICABLE ="NAFACT";
	}
	
	public final static class SALESORDER_ASSOCIATION {
		public final static String ORDER_HEADER ="ORDHDR";
		public final static String ORDER_LINE ="ORDLINE";
		public final static String ORDER_HEAD_LINE ="ORDHDLN";
	}
	
	public final static class PROMOTYPE {
		public final static String CROSSSELL ="CRSSSELL";
		public final static String BUNDLING ="BNDL";
		public final static String UPSELLING ="UPSELL";
		public final static String PLAINDISCOUNT ="PLNDISC";
	}
	
	public final static class RELDATE {
		public final static String TODAY ="TODAY";
		public final static String LASTWEEK ="LSTWEEK";
		public final static String LASTMONTH ="LSTMNTH";
		public final static String LASTTWOMONTH ="LSTTWOMNTH";
		public final static String LASTTHREEMONTH ="LSTTHRMNTH";
		public final static String LASTSIXMONTH ="LSTSIXMNTH";
		public final static String LASTYEAR ="LSTYR";
		public final static String LASTTWOYEAR ="LSTTWOYR";
		public final static String LASTTHREEYEAR ="LSTTHRYR";
	}
	
	public final static class ROLETYPE {
		public final static String SYSADMIN = "SYSADMIN";
		public final static String CORPADMIN = "CORPADMIN";
		public final static String MANAGER = "MANAGER";
		public final static String SALESASSOCIATE = "SALESASS";
		public final static String OFFICEASSOCIATE = "OFFASSOC";
		public final static String FLOORWORK = "FLOORWRK";
	}
		
	public final static class USERTYPE {
		public final static String ALLUSERS = "ALLUSR";
		public final static String MANAGERUSERS = "MGRUSR";
		public final static String ADMINUSERS = "ADMUSR";
		
	}
	public final static class SALESPFTYPE{
		public final static String CATEGORY = "SPFCATG";
		public final static String PRODUCT = "SPFPROD";
		public final static String BRAND = "SPFBRAND";
		public final static String ITEM = "SPFITEM";
	}
	
	public final static class SALESCYCLE_STATUS {
		public final static String INITIATED = "INIT";
		public final static String ASSIGNED = "ASSGND";
		public final static String IN_PROGRESS = "INPRG";
		public final static String NEGOTIATING = "NGTD";
		public final static String CLOSED = "CLSD";
		public final static String FAILED = "FLD";
	}
	
	public final static class FOLLOWUP_RESULT {
		public final static String SOLD = "SOLD";
		public final static String PENDING = "SLPENDING";
		public final static String NOSALE = "NOSALE";
		public final static String PARTSALE = "PARTSALE";
	}
	
	public final static class ITEM_CLASS {
		public final static String TOP_END = "TOPEND";
		public final static String UPPER_MEDIUM = "UPMEDIUM";
		public final static String LOWER_MEDIUM = "LWMEDIUM";
		public final static String ECONOMIC = "ECONOMIC";
	}
	
	public final static class ALERT_STATUS {
		public final static String OPEN = "ALTOPN";
		public final static String ACKNOWLEDGED = "ALTACK";
		public final static String VOID = "ALTVOID";
		public final static String EXPIRED = "ALTEXPRD";
	}
	
	public final static class ALERT_TYPE {
		public final static String SALESLEAD = "ALTSLSLEAD";
		public final static String FOLLOWUP = "ALTFLLWUP";
		public final static String INVSHORTAGE = "ALTINVSHRT";
		public final static String APPOINTMENT = "ALTAPP";
		public final static String SLSPERSTART = "ALTSPBEGIN";
		public final static String SLSPEREND = "ALTSPEND";
		public final static String DISTOR = "ALTDO";
		public final static String FEEDBACK = "ALTFDBK";
	}
	
	public final static class ADDRESS_TYPE {
		public final static String BILLING = "BLLNG";
		public final static String PRIMARY_SHIPPING = "SHPPRM";
		public final static String SECONDARY_SHIPPING = "SHPSEC";
	}
	
	public final static class VALUE_TYPE {
		public final static String NUMERIC = "NUMER";
		public final static String STRING = "STR";
		public final static String FINITE_VALUE = "FINVAL";
		public final static String BOOLEAN = "BOOL";
	}
	
	public final static class DO_STATUS {
		public final static String RELEASED = "RLSD";
		public final static String PICKING = "PICKNG";
		public final static String PICKED = "PICKD";
		public final static String PACKING = "PACKNG";
		public final static String PACKD = "PACKD";
		public final static String SHIPPING = "SHPPNG";
		public final static String SHIPPD = "SHPPD";
		public final static String DELIVERED = "DLVD";
	}
	
	public final static class EXP_VOUCHER_STATUS { 
		public final static String REQUESTED = "EXPREQ";
		public final static String COUNTERED = "EXPCONTRD";
		public final static String APPROVED = "EXPAPPR";
		public final static String REJECTED = "EXPREJEC";
		public final static String PENDING = "EXPPEN";
		public final static String REREQUESTED = "EXPREREQ";
		public final static String CLOSED = "EXPPDCLSD";
		public final static String REJECTEDCLOSED = "EXPRJCLSD";

	}
	
	public final static class ENQUIRY_SOURCE {
		public final static String COMPANY_WEBSITE = "ENQWBS";
		public final static String STORE_WALKIN = "ENQSTR";
		public final static String TELEPHONE = "ENQTELP";
		public final static String ASSOCIATE_CONTACT = "ENQASSCNT";
		
	}
	
	public final static class ENQUIRY_STATUS {
		public final static String OPEN = "ENQOPEN";
		public final static String ASSIGNED = "ENQASSND";
		public final static String INVALID = "ENQINVLD";
		public final static String LEAD_GENERATED = "ENQLDGNRT";
		
	}
	
}

