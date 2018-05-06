package com.primus;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.techtrade.rads.framework.ui.servlets.PageGenerator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.CRUDController;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.controller.abstracts.IExternalizeFacade;
import com.techtrade.rads.framework.controller.abstracts.ListController;
import com.techtrade.rads.framework.controller.abstracts.TransactionController;
import com.techtrade.rads.framework.controller.abstracts.ViewController;
import com.techtrade.rads.framework.filter.Filter;
import com.techtrade.rads.framework.filter.FilterNode;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.abstracts.RadsError;
import com.techtrade.rads.framework.model.simple.LookupObject;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.ui.components.SortCriteria;
import com.techtrade.rads.framework.ui.components.UIGeneralPage;
import com.techtrade.rads.framework.ui.components.UIListPage;
import com.techtrade.rads.framework.ui.components.UILookupPage;
import com.techtrade.rads.framework.ui.config.AppConfig;
import com.techtrade.rads.framework.ui.config.LookupConfig;
import com.techtrade.rads.framework.ui.config.PageConfig;
import com.techtrade.rads.framework.ui.constants.FixedAction;
import com.techtrade.rads.framework.ui.constants.RadsControlConstants;
import com.techtrade.rads.framework.ui.readers.HTMLReader;
import com.techtrade.rads.framework.ui.writers.HTMLWriter;
import com.techtrade.rads.framework.utils.Utils;

public class JSONProcessor {

    private static void populateContextParams(JSONObject contextParameters, IRadsContext pageContext)
    {
        if (contextParameters != null && pageContext != null ) {
            Iterator itkeys = contextParameters.keys() ;
            if (itkeys == null ) return ;
            while(itkeys.hasNext()) {
                String token = String.valueOf(itkeys.next()) ;
                String value = contextParameters.optString(token);
                pageContext.addProperty(token, value);
            }
        }

    }

    public static void processLookupRequest(HttpServletRequest req, ServletContext context , HttpServletResponse resp, String rootPath)
    {

        //String dialogId= req.getParameter("dialogId");
        //UIPage page = null ;
        //ModelObject object = null ;
        try {
            String line = null;

            StringBuffer jb = new StringBuffer();
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
            JSONTokener  tokener = new JSONTokener(jb.toString());
            JSONObject root = new JSONObject(tokener);
            String lookupType  = root.optString("lookupType");
            LookupConfig lookupConfig = AppConfig.APPCONFIG.getLookupConfig(rootPath, lookupType);
            String lookupPageID =  lookupConfig.getLookupPage() ;
            PageConfig config = AppConfig.APPCONFIG.getPageConfig(rootPath, lookupPageID);
            LookupObject object =(LookupObject)PageGenerator.readObjectfromPageConfig(config);
            String  lookupServiceClass = lookupConfig.getService() ;
			/*UILookupPage lookupPage =  (UILookupPage)PageGenerator.getPagefromKey(config,object,req,null,resp,context);
			lookupPage.setDialogId(dialogId);*/
            ILookupService  lookupService = (ILookupService) Class.forName(lookupServiceClass).newInstance() ;
			/*lookupPage.setLookupSevice(lookupService);
			page = lookupPage;*/
            //HTMLReader reader = new  HTMLReader(req) ;
            //reader.read(page,object,null);
            String authToken =  root.optString("authToken");
            req.setAttribute("authToken", authToken);
            String searchString   =  root.optString("searchString") ;
            String additionalParam = root.optString("additionalParam");
            object.setAdditionalParam(additionalParam);
            String additionalFields = root.optString("additionalFields");
            object.setAdditionalFields(additionalFields);
            String additionalControls = root.optString("additionalControls");
            object.setAdditionalControls(additionalControls);
            List<String> listAddFields = Utils.getListfromStringtokens(additionalFields, ",");
            int from  =root.optInt("from", 0);
            int noRecords = root.optInt("noRecords", 20);
            IRadsContext ctx = lookupService.generateContext(req,null);
            Map mapValues = lookupService.lookupData(ctx,searchString, from, noRecords,additionalParam,listAddFields);
            writeLookupOutput(resp, mapValues, null, authToken, new PageResult());
        }catch(Exception ex) {
            ex.printStackTrace();

        }

    }


    public static void processRequest (HttpServletRequest request , HttpServletResponse response, ServletContext context, String rootPath )
    {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
            JSONTokener  tokener = new JSONTokener(jb.toString());
            JSONObject root = new JSONObject(tokener);
            System.out.println("input request =" + root.toString());
            String authToken =  root.optString("authToken");
            String pageID = root.optString("pageID");
            String modeKey  =root.optString("currentmode") ;
            String fixedActionStr = root.optString("fixedAction") ;
            String pageNumber = root.optString("hdnPage");
            String submitAction  = root.optString("submitAction");
            String sortField = root.optString(RadsControlConstants.SORT_FIELD);
            String sortDirection = root.optString(RadsControlConstants.SORT_DIRECTION);
            String selectedRecords = root.optString(RadsControlConstants.JSON_SELECTEDIDS);
            JSONObject contextParameters =root.optJSONObject("contextParameters") ;
            JSONObject json =root.optJSONObject("dataObject") ;
            JSONArray filterArray = root.optJSONArray("filter")	;
            ViewController.Mode initialMode = PageGenerator.getModeFromString(modeKey);
            PageConfig config = AppConfig.APPCONFIG.getPageConfig(rootPath, pageID);
            ModelObject object =PageGenerator.readObjectfromPageConfig(config);
            request.setAttribute("authToken", authToken);
            UIPage page = PageGenerator.getPagefromKey(config,object,request,initialMode,response,rootPath);
            page.setPageKey(pageID);
            page.getViewController().init(request);
            IRadsContext pageContext = page.getViewController().generateContext(authToken,page);
            populateContextParams(contextParameters,pageContext);
            if (page.getViewController() != null ) {
                page.getViewController().setContext(pageContext);
                if(json !=null && !Utils.isNullString(json.toString())) {
                    object = object.instantiateObjectfromJSON(json.toString(), object.getClass().getName(), pageContext);
                }
                if (page.getViewController() instanceof CRUDController || page.getViewController() instanceof TransactionController) {
                    if (page.getViewController() instanceof CRUDController)
                        ((CRUDController)page.getViewController()).setObject(object);
                    else
                        ((TransactionController)page.getViewController()).setObject(object);

                }else  if (page.getViewController() instanceof ListController && page instanceof UIListPage) {
                    if (Utils.isPositiveInt(pageNumber))
                        ((UIListPage) page).setPageNumber(Integer.parseInt(pageNumber));
                    if(!Utils.isNullString(sortField)  && !Utils.isNullString(sortDirection)){
                        SortCriteria sortCriteria = new SortCriteria(sortField,SortCriteria.DIRECTION.valueOf(sortDirection));
                        ((UIListPage) page).setSortCriteria(sortCriteria);
                    }
                    if(!Utils.isNullString(selectedRecords))
                    {
                        List<String >selRows = new ArrayList<String> ();
                        StringTokenizer tokenizer = new StringTokenizer(selectedRecords,",");
                        while(tokenizer.hasMoreTokens()) {
                            selRows.add(tokenizer.nextToken()) ;
                        }
                        ((UIListPage) page).setSelectedRows(selRows);
                    }
                    writeListPage((UIListPage) page, fixedActionStr,submitAction,response,authToken,filterArray);
                    return;
                } else  if (page.getViewController() instanceof GeneralController){
                    ((GeneralController)page.getViewController()).setObject(object);
                    ((UIGeneralPage)page).setObject(object);
                }
                if (!Utils.isNull(fixedActionStr)) {
                    FixedAction fixedAction = FixedAction.getFixedAction(fixedActionStr);
                    page.setFixedAction(fixedAction);
                    PageResult result =  page.applyFixedAction() ;
                    if (result.getResult().equals(TransactionResult.Result.SUCCESS)) {
                        object = result.getObject() ;
                    }
                    writeOutput(response, object, result.getErrors(), authToken, result);
                }else {
                    PageResult result;
                    if( Utils.isNullString( submitAction)) {
                        result =  page.submit() ;
                    }else
                        result = page.submit(submitAction);
                    if (result.getResult().equals(TransactionResult.Result.SUCCESS)) {
                        object = result.getObject() ;
                    }
                    writeOutput(response, object, result.getErrors(), authToken, result);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeListPage (UIListPage page, String fixedActionStr, String submitAction,HttpServletResponse response , String authToken, JSONArray filterArray) throws Exception
    {
        Filter filter = new Filter();
        if (filterArray != null) {
            for (int  i = 0 ; i  < filterArray.length() ; i++ ) {
                JSONObject filterNode = (JSONObject)filterArray.get(i);
                String field = filterNode.optString("field");
                String operator = filterNode.optString("operator");
                String value =  filterNode.optString("value");
                FilterNode node = new FilterNode();
                node.setField(field);
                node.setValue(value);
                if (">".equals(operator))
                    node.setOperater(FilterNode.Operator.GREATER_THAN);
                else if (">=".equals(operator))
                    node.setOperater(FilterNode.Operator.GREATER_THAN_EQUAL);
                else if ("<".equals(operator))
                    node.setOperater(FilterNode.Operator.LESS_THAN);
                else if ("<=".equals(operator))
                    node.setOperater(FilterNode.Operator.LESS_THAN_EQUAL);
                else if ("EQUALS".equals(operator))
                    node.setOperater(FilterNode.Operator.EQUALS);
                else if ("IN".equals(operator))
                    node.setOperater(FilterNode.Operator.IN);
                else if ("NOT IN".equals(operator))
                    node.setOperater(FilterNode.Operator.NOT_IN);
                filter.addNode(node);
            }
        }
        page.setFilter(filter);
        if (!Utils.isNull(fixedActionStr)) {
            FixedAction fixedAction = FixedAction.getFixedAction(fixedActionStr);
            page.setFixedAction(fixedAction);
            PageResult result =  page.applyFixedAction() ;
            if (result.getResult().equals(TransactionResult.Result.SUCCESS)) {
                writeOutput(response, page.getObjects(), result.getErrors(), authToken, result);
            }

        }else {
            page.setSubmitAction(submitAction);
            PageResult result =  page.submit() ;
            if (result.getResult().equals(TransactionResult.Result.SUCCESS)) {
                writeOutput(response, page.getObjects(), result.getErrors(), authToken, result);
            }
        }

    }

    private  static void writeOutput(HttpServletResponse response, List<ModelObject> objects , List<RadsError> errors, String authToken, PageResult result) {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            JSONObject json = new JSONObject();
            json.put("authToken", authToken) ;
            if (result.getResult().equals(TransactionResult.Result.SUCCESS)) {
                json.put("result", "success") ;
                json.put("availableRecords", result.getAvailableRecords());
                json.put("fetchedRecords", result.getFetchedRecords());
            } else  {
                json.put("result", "failure") ;
                int index = 0 ;
                JSONArray array = new JSONArray();
                for (RadsError error : errors) {
                    array.put(index ++ , error.getMessage()) ;
                }
            }
            JSONArray array = new JSONArray();
            if(!Utils.isNullList(objects)) {
                for (ModelObject object : objects) {
                    JSONObject objJSON = new JSONObject(object.toJSON());
                    array.put(objJSON);
                }
            }
            json.put("dataObject", array ) ;
            response.getWriter().write(json.toString());
        }catch(Exception ex) {
            ex.printStackTrace();
        }

    }


    private  static void writeLookupOutput(HttpServletResponse response, Map values , List<RadsError> errors, String authToken, PageResult result) {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            JSONObject json = new JSONObject();
            json.put("authToken", authToken) ;
            if (result.getResult().equals(TransactionResult.Result.SUCCESS)) {
                json.put("result", "success") ;
            } else  {
                json.put("result", "failure") ;
                int index = 0 ;
                JSONArray array = new JSONArray();
                for (RadsError error : errors) {
                    array.put(index ++ , error.getMessage()) ;
                }
            }
			 /*JSONObject root = new JSONObject();
			 JSONArray lookedUps = new JSONArray();
			 lookedUps.put(values);
			 root.put("lookups", lookedUps);
			 if(values != null ) {
				 values.keySet().forEach( key ->  {
					 Object value = values.get(key) ;
					 root.put("", value);

				 } );
			}*/

            JSONArray array = new JSONArray();
            if(values != null ) {
                values.keySet().forEach( key ->  {
                    JSONObject objJSON = new JSONObject();
                    Object value = values.get(key) ;
                    try  {
                        objJSON.put("code", key);
                        objJSON.put("value", value);
                    }catch(Exception ex) {
                        ex.printStackTrace();
                    }
                    array.put(objJSON);
                }	);
            }
            json.put("dataObject", array ) ;
            response.getWriter().write(json.toString());
        }catch(Exception ex) {
            ex.printStackTrace();
        }

    }
    private  static void writeOutput(HttpServletResponse response, ModelObject object , List<RadsError> errors, String authToken, PageResult result) {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            JSONObject json = new JSONObject();
            json.put("authToken", authToken) ;
            if (result.getResult().equals(TransactionResult.Result.SUCCESS)) {
                json.put("result", "success") ;
            } else  {
                json.put("result", "failure") ;
                int index = 0 ;
                JSONArray array = new JSONArray();
                for (RadsError error : errors) {
                    array.put(index ++ , error.getMessage()) ;
                }
            }
            if(null != object){
                JSONObject root = new JSONObject(object.toJSON());
                json.put("dataObject", root ) ;
            }
            response.getWriter().write(json.toString());
        }catch(Exception ex) {
            ex.printStackTrace();
        }

    }
}
