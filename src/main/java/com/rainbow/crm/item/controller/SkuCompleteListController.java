package com.rainbow.crm.item.controller;

import java.util.ArrayList;
import java.util.List;

import com.rainbow.crm.common.CRMAppConfig;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.IBusinessService;
import com.rainbow.crm.config.service.ConfigurationManager;
import com.rainbow.crm.item.dao.ItemImageSQL;
import com.rainbow.crm.item.model.Sku;
import com.rainbow.crm.item.model.SkuComplete;
import com.rainbow.crm.logger.Logwriter;
import com.techtrade.rads.framework.filter.Filter;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.utils.Utils;

public class SkuCompleteListController extends SkuListController {
	
	@Override
	public List<ModelObject> getData(int pageNumber, Filter filter,SortCriteria  sortCriteria) {
		int from  = (pageNumber-1)*recordsPerPage ;
		 List<ModelObject> itemWithImgs=new ArrayList<> (); 
		IBusinessService serv = (IBusinessService)getService();
		List<ModelObject> results = (List)serv.listData(from,  from + recordsPerPage, getFilter(filter),(CRMContext)getContext(),sortCriteria);  
		if  (results != null)  {
			results.forEach( result ->   { 
				Sku sku = (Sku) result; 
				SkuComplete itemComplete = new SkuComplete((Sku) sku);
				List<String > imageURLs = ItemImageSQL.getAllItemImages(itemComplete.getId());
				if(!Utils.isNullList(imageURLs)) {
					/*String path = ConfigurationManager.getConfig(
							ConfigurationManager.IMAGE_SERVER_URL, (CRMContext)getContext());*/
					try  {
					String path = CRMAppConfig.INSTANCE.getProperty("doc_server");
					String companyCode = ((CRMContext) getContext()).getLoggedinCompanyCode();
					itemComplete.setImage1URL(path +"/" + companyCode+ "/" + "itemimages/"  + imageURLs.get(0).toString());
					if(imageURLs.size() > 1) 
						itemComplete.setImage2URL(path +"/" + companyCode+ "/" + "itemimages/"  + imageURLs.get(1).toString());
					if(imageURLs.size() > 2) 
						itemComplete.setImage3URL(path +"/" + companyCode+ "/" + "itemimages/"  + imageURLs.get(2).toString());
					}catch(Exception ex) {
						Logwriter.INSTANCE.error(ex);
					}
				}
				itemWithImgs.add(itemComplete);
			} );
			
		}
		return  itemWithImgs;
		
	}

}
