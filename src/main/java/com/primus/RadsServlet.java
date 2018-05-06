package com.primus;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.primus.ApplicationManager;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.DataSheetController;
import com.techtrade.rads.framework.controller.abstracts.GeneralController;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;
import com.techtrade.rads.framework.controller.abstracts.IAjaxUpdateService;
import com.techtrade.rads.framework.controller.abstracts.IExternalizeFacade;
import com.techtrade.rads.framework.controller.abstracts.ListController;
import com.techtrade.rads.framework.controller.abstracts.CRUDController;
import com.techtrade.rads.framework.controller.abstracts.TransactionController;
import com.techtrade.rads.framework.controller.abstracts.ViewController;
import com.techtrade.rads.framework.exceptions.RadsAuthenticationException;
import com.techtrade.rads.framework.exceptions.RadsAuthorizationException;
import com.techtrade.rads.framework.exceptions.RadsException;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.model.simple.LookupObject;
import com.techtrade.rads.framework.model.transaction.TransactionResult;
import com.techtrade.rads.framework.ui.abstracts.ILookupService;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.ui.components.UIDataSheetPage;
import com.techtrade.rads.framework.ui.components.UIGeneralPage;
import com.techtrade.rads.framework.ui.components.UIListPage;
import com.techtrade.rads.framework.ui.components.UILookupPage;
import com.techtrade.rads.framework.ui.components.UITable;
import com.techtrade.rads.framework.ui.config.AjaxServiceConfig;
import com.techtrade.rads.framework.ui.config.AppConfig;
import com.techtrade.rads.framework.ui.config.LookupConfig;
import com.techtrade.rads.framework.ui.config.PageConfig;
import com.techtrade.rads.framework.ui.constants.FixedAction;
import com.techtrade.rads.framework.ui.readers.HTMLAlternativeReader;
import com.techtrade.rads.framework.ui.readers.HTMLReader;

import com.techtrade.rads.framework.ui.servlets.PageGenerator;
import com.techtrade.rads.framework.ui.writers.BootstrapWriter;
import com.techtrade.rads.framework.ui.writers.HTMLWriter;
import com.techtrade.rads.framework.utils.Utils;

import javax.servlet.annotation.MultipartConfig;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;


@MultipartConfig
@WebServlet(urlPatterns = "/controller/*")
public class RadsServlet extends HttpServlet {

    @Autowired
    ApplicationManager applicationManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("inside doGet");
        process(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("inside doPosr");
        // TODO Auto-generated method stub
        process(req, resp);
    }



    @Override
    protected void doOptions(HttpServletRequest arg0, HttpServletResponse arg1)
            throws ServletException, IOException {

        System.out.println("inside doOptions");
        //The following are CORS headers. Max age informs the
        //browser to keep the results of this call for 1 day.
        arg1.setHeader("Access-Control-Allow-Origin", "*");
        arg1.setHeader("Access-Control-Allow-Methods", "GET, POST");
        arg1.setHeader("Access-Control-Allow-Headers", "Content-Type");
        //Tell the browser what requests we allow.
        arg1.setHeader("Allow", "GET, HEAD, POST, TRACE, OPTIONS");

    }


    public RadsServlet() {
        super();
    }

     private String getServletRootPath( )
     {
         return applicationManager.getRadsResourcePath() ;
     }



    protected void ajaxServiceRequest (HttpServletRequest req, HttpServletResponse resp, String ajaxServiceKey)
            throws ServletException, IOException,Exception {
        AjaxServiceConfig config = AppConfig.APPCONFIG.getAjaxServiceConfig(getServletRootPath(), ajaxServiceKey);
        resp.setContentType("application/json");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        if (IAjaxLookupService.class.isAssignableFrom(Class.forName(config.getServiceClass()))  ) {
            List<String > keys = config.getKeys() ;
            BufferedReader reader = req.getReader();
            StringBuffer jb = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null)
                jb.append(line);
            JSONTokener tokener = new JSONTokener(jb.toString());
            if(jb.length() > 1 && jb.toString().contains("{")) {
                JSONObject root = new JSONObject(tokener);
                String authToken =  root.optString("authToken");
                req.setAttribute("authToken", authToken);
            }
            Map<String,String> mp = new HashMap<String,String>();
            if(!Utils.isNullList(keys)) {
                for (String key : keys) {
                    String value =	req.getParameter(key) ;
                    if (!Utils.isNull(key) )
                        mp.put(key, value);
                }
            }
            IAjaxLookupService srv = (IAjaxLookupService) Class.forName(config.getServiceClass()).newInstance() ;
            IRadsContext ctx = srv.generateContext(req);
            String val = srv.lookupValues(mp,ctx);
            if(!Utils.isNullString(val))
                resp.getWriter().write(val);
        } else if (IAjaxUpdateService.class.isAssignableFrom(Class.forName(config.getServiceClass()))  ) {

            StringBuffer jb = new StringBuffer();
            String line = null;
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
            JSONTokener tokener = new JSONTokener(jb.toString());
            JSONObject root = new JSONObject(tokener);
            String authToken =  root.optString("authToken");
            req.setAttribute("authToken", authToken);
            IAjaxUpdateService srv = (IAjaxUpdateService) Class.forName(config.getServiceClass()).newInstance();
            IRadsContext ctx = srv.generateContext(req);
            String val = srv.update(root.toString(), ctx);
            resp.getWriter().write(val);
        }

    }
    protected UIPage reloadPage(UIPage page, ModelObject object, HttpServletRequest req,HttpServletResponse resp,ViewController.Mode mode) throws Exception {
        PageConfig config = AppConfig.APPCONFIG.getPageConfig(getServletRootPath(), page.getPageKey());
        UIPage newPage = PageGenerator.getPagefromKey(config,object,req,mode,resp,getServletRootPath());
        IRadsContext ctx = newPage.getViewController().generateContext(req,resp,newPage);
        if ( ctx == null || !ctx.isAuthenticated()) {
            //throw new ServletException("Authentication Failed- Rads Level");
            throw new RadsAuthenticationException();
        }else if(! ctx.isAuthorized()) {
            throw new RadsAuthorizationException();
        }
        newPage.getViewController().setContext(ctx);
        if (newPage instanceof UIDataSheetPage  && page instanceof UIDataSheetPage) {
            UITable tabNew = ((UIDataSheetPage) newPage).getTable();
            UITable tabOld = ((UIDataSheetPage) page).getTable();
            tabNew.setInnerRows(tabOld.getInnerRows());
            ((UIDataSheetPage) newPage).setFilter(((UIDataSheetPage) page).getFilter());
            ((UIDataSheetPage) newPage).setFilterSet(((UIDataSheetPage) page).getFilterSet());
        }
        return newPage;
    }

    protected void displayNextPage(UIPage page, String nextPageKey, ModelObject object, HttpServletRequest req,HttpServletResponse resp,ViewController.Mode mode) throws Exception {
        PageConfig config = AppConfig.APPCONFIG.getPageConfig(getServletRootPath(), nextPageKey);

        UIPage newPage = PageGenerator.getPagefromKey(config,object,req,mode,resp,getServletRootPath());
        newPage.setPageKey(nextPageKey);

        if (newPage.getViewController() instanceof CRUDController){
            if (object == null )
                object = ((CRUDController)newPage.getViewController()).getObject() ;
            else
                ((CRUDController)newPage.getViewController()).setObject(object);
        }else if (newPage.getViewController() instanceof TransactionController)
            object = ((TransactionController)newPage.getViewController()).getObject() ;
        else if (newPage.getViewController() instanceof GeneralController)
            object = ((GeneralController)newPage.getViewController()).getObject() ;
        else if (newPage.getViewController() instanceof ListController)
            object = null;

        IRadsContext ctx = newPage.getViewController().generateContext(req,resp,newPage);
        if ( ctx == null || !ctx.isAuthenticated()) {
            //throw new ServletException("Authentication Failed- Rads Level");
            throw new RadsAuthenticationException();
        }else if(! ctx.isAuthorized()) {
            throw new RadsAuthorizationException();
        }
        newPage.getViewController().setContext(ctx);
        newPage.submit();
        write(resp,newPage,page,object);

    }

    protected void write(HttpServletResponse response, UIPage newPage,UIPage oldPage,ModelObject object)  throws Exception{

		/* String appURL = AppConfig.APPCONFIG.getAppURL(getServletContext().getRealPath("/"));
		 IExternalizeFacade externalizeFacade = AppConfig.APPCONFIG.getExternalizeFacade() ;
		 if (externalizeFacade != null ) {
			 newPage.setExternalizeFacade(externalizeFacade);
		 }
		 HTMLWriter writer  =new HTMLWriter(response.getWriter());
		 writer.setContext(oldPage.getViewController().getContext());
		 writer.setAppURL(appURL);
		 writer.write(newPage, object);*/

        String appURL = AppConfig.APPCONFIG.getAppURL(getServletRootPath());
        String bootstrapFolder = AppConfig.APPCONFIG.getBootstrapFolder(getServletRootPath());
        Boolean useGoogleGraphs = AppConfig.APPCONFIG.useGoogleGraphs(getServletRootPath());
        String portalPrefix = AppConfig.APPCONFIG.getPortalPrefix(getServletRootPath());
        String controllerName =AppConfig.APPCONFIG.getControllerName(getServletRootPath());

        BootstrapWriter writer  =new BootstrapWriter(response.getWriter());
        IExternalizeFacade externalizeFacade = AppConfig.APPCONFIG.getExternalizeFacade() ;
        if (externalizeFacade != null ) {
            newPage.setExternalizeFacade(externalizeFacade);
        }
        writer.setContext(oldPage.getViewController().getContext());
        writer.setAppURL(appURL);
        writer.setPortalPrefix(portalPrefix);
        writer.setControllerName(controllerName);
        writer.setBootstrapPath(bootstrapFolder);
        writer.setUseGoogleforGraphs(useGoogleGraphs);
        writer.write(newPage, object);

    }

    protected void writeLookupPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String lookupType  = req.getParameter("lookupType");
        String dialogId= req.getParameter("dialogId");
        String returnAsJSON = req.getParameter("returnAsJSON");
        UIPage page = null ;
        //ModelObject object = null ;
        try {
            LookupConfig lookupConfig = AppConfig.APPCONFIG.getLookupConfig(getServletRootPath(), lookupType);
            String lookupPageID =  lookupConfig.getLookupPage() ;
            PageConfig config = AppConfig.APPCONFIG.getPageConfig(getServletRootPath(), lookupPageID);
            LookupObject object =(LookupObject)PageGenerator.readObjectfromPageConfig(config);
            String  lookupServiceClass = lookupConfig.getService() ;
            UILookupPage lookupPage =  (UILookupPage) PageGenerator.getPagefromKey(config,object,req,null,resp,getServletRootPath());
            lookupPage.setDialogId(dialogId);
            ILookupService  lookupService = (ILookupService) Class.forName(lookupServiceClass).newInstance() ;
            lookupPage.setLookupSevice(lookupService);
            page = lookupPage;
            HTMLReader reader = new  HTMLReader(req) ;
            reader.read(page,object,null);
            String searchString  ;
            if("true".equalsIgnoreCase(returnAsJSON)){
                searchString   =  req.getParameter("searchString");
            }else
                searchString   =  String.valueOf(lookupPage.getSearchValue());
            String additionalParam = req.getParameter("additionalParam");
            object.setAdditionalParam(additionalParam);
            String additionalFields = req.getParameter("additionalFields");
            object.setAdditionalFields(additionalFields);
            String additionalControls = req.getParameter("additionalControls");
            object.setAdditionalControls(additionalControls);
            List<String> listAddFields = Utils.getListfromStringtokens(additionalFields, ",");
            int from  =lookupPage.getFrom() ;
            int noRecords = lookupPage.getNoRecords() ;
            IRadsContext ctx = lookupService.generateContext(req,lookupPage);
            Map<String,String> mapValues = lookupService.lookupData(ctx,searchString, from, noRecords,additionalParam,listAddFields);
            if("true".equalsIgnoreCase(returnAsJSON)){
                JSONArray array = new JSONArray();
                JSONObject json = new JSONObject();
                AtomicInteger index = new AtomicInteger(0);
                if(mapValues != null) {
                    mapValues.keySet().forEach( key  ->   {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("key", key);
                            jsonObject.put("value", mapValues.get(key));
                            array.put(index.getAndIncrement(), jsonObject);
                        }catch(Exception ex) {
                            ex.printStackTrace();
                        }
                    } );
                }
                json.put("lookupValues", array);
                resp.getWriter().write(json.toString());
                return ;
            }
            lookupPage.applyMapValues(mapValues);

            String appURL = AppConfig.APPCONFIG.getAppURL(getServletRootPath());
            IExternalizeFacade externalizeFacade = AppConfig.APPCONFIG.getExternalizeFacade() ;
            if (externalizeFacade != null ) {
                page.setExternalizeFacade(externalizeFacade);
            }
            HTMLWriter writer  =new HTMLWriter(resp.getWriter());
            writer.setContext(ctx);
            writer.setAppURL(appURL);
            writer.write(page, object);
            return ;
        }catch(Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }




    protected void process(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pageID  = req.getParameter("currentpage") ;;
        if (Utils.isNullString(pageID))
            pageID = req.getParameter("page");
        String modeKey  =req.getParameter("currentmode") ;;
        ViewController.Mode initialMode = PageGenerator.getModeFromString(modeKey);
        String ajaxService = req.getParameter("ajxService") ;
        ModelObject object = null ;
        UIPage page =null  ;
        if("Lookup".equals(pageID)) {
            String contentType = req.getContentType();
            if (!Utils.isNullString(contentType) && "application/json".equalsIgnoreCase(contentType)) {
                JSONProcessor.processLookupRequest(req, getServletContext(), resp,getServletRootPath());
                return;
            }else {
                writeLookupPage(req, resp);
                return ;
            }
        }else {
            try {
                if (!Utils.isNullString(ajaxService)) {
                    ajaxServiceRequest(req,resp,ajaxService);
                    return ;
                }
            }catch(Exception ex) {
                ex.printStackTrace();
                throw new ServletException(ex);
            }
        }
        try {
            String contentType = req.getContentType();
            if (!Utils.isNullString(contentType) && "application/json".equalsIgnoreCase(contentType)) {
                JSONProcessor.processRequest(req, resp,getServletContext(),getServletRootPath());
                return;
            } else {
                PageConfig config = AppConfig.APPCONFIG.getPageConfig(getServletRootPath(), pageID);
                object =PageGenerator.readObjectfromPageConfig(config);
                page = PageGenerator.getPagefromKey(config,object,req,initialMode,resp,getServletRootPath());
                page.setPageKey(pageID);
                page.setAccessCode(config.getAccessCode());
                page.getViewController().init(req);
                IRadsContext ctx  = page.getViewController().generateContext(req,resp,page);
                if ( (ctx == null || !ctx.isAuthenticated()) && config.isAuthRequired() ) {
                    //throw new ServletException("Authentication Failed- Rads Level");
                    throw new RadsAuthenticationException();
                }else if(! ctx.isAuthorized()) {
                    throw new RadsAuthorizationException();
                }
                page.getViewController().setContext(ctx);
                HTMLAlternativeReader reader = new  HTMLAlternativeReader(req) ;
                reader.read(page,object,null);
            }
		/*HTMLReader reader = new  HTMLReader(req) ;
		 reader.read(page,object,null);*/
            String curKey = page.getPageKey() ;
            if (page.getViewController() != null ) {
                if (page.getViewController() instanceof CRUDController || page.getViewController() instanceof TransactionController) {
                    if (page.getViewController() instanceof CRUDController)
                        ((CRUDController)page.getViewController()).setObject(object);
                    else
                        ((TransactionController)page.getViewController()).setObject(object);
                    if (page.getFixedAction() != null ) {
                        PageResult pageResult = page.applyFixedAction();
                        if (pageResult !=null && pageResult.getResponseAction().equals(PageResult.ResponseAction.FILEDOWNLOAD)) {
                            return;
                        }
                        if (pageResult !=null && pageResult.getResponseAction().equals(PageResult.ResponseAction.FULLRELOAD)) {
                            write(resp,page,page,object) ;
                            return;
                        }
                        String nextPageKey = pageResult.getNextPageKey();
                        if (!Utils.isNullString(nextPageKey) && !nextPageKey.equals(curKey)) {
                            if(pageResult.getObject() != null)
                                object = pageResult.getObject();
                            ViewController.Mode mode =null ;
                            if (page.getFixedAction() == FixedAction.ACTION_GOEDITMODE ) {
                                mode =ViewController.Mode.UPDATE;
                            }
                            if (page.getFixedAction() == FixedAction.ACTION_GOADDMODE ) {
                                mode =ViewController.Mode.CREATE;
                            }
                            if(mode == null) {
                                mode = pageResult.getMode();
                            }
                            displayNextPage(page, nextPageKey, object,req,resp,mode);
                            return ;
                        }else {
                            if(pageResult.getObject() != null)
                                object = pageResult.getObject();
                            if(!pageResult.getResult().equals(TransactionResult.Result.SUCCESS)) {
                                FixedAction fixedAction = page.getFixedAction();
                                if (fixedAction == FixedAction.ACTION_CREATE)
                                    page.getViewController().setCreateMode();
                                else if (fixedAction == FixedAction.ACTION_UPDATE)
                                    page.getViewController().setUpdateMode();
                                else if (fixedAction == FixedAction.ACTION_DELETE)
                                    page.getViewController().setDeleteMode();
                            }
                            PageConfig config = AppConfig.APPCONFIG.getPageConfig(getServletRootPath(), curKey);
                            page = PageGenerator.getPagefromKey(config,object,req,page.getViewController().getMode(),resp,getServletRootPath());
                            page.setPageKey(curKey);
                            page.setErrors(pageResult.getErrors());
                        }
                    }else {
                        String navAction = req.getParameter("navAction") ;
                        if (!Utils.isNullString(navAction) && "loadfromPK".equalsIgnoreCase(navAction)) {
                            if (page.getViewController() instanceof CRUDController){
                                object = ((CRUDController)page.getViewController()).populateFullObjectfromPK(object);
                                ((CRUDController)page.getViewController()).setObject(object);
                            }else{
                                object = ((TransactionController)page.getViewController()).populateFullObjectfromPK(object);
                                ((TransactionController)page.getViewController()).setObject(object);
                            }
                            displayNextPage(page, page.getPageKey(), object,req,resp,null);
                            return ;

                        }else  if (!Utils.isNullString(navAction) && "loadfromBK".equalsIgnoreCase(navAction)) {
                            if (page.getViewController() instanceof CRUDController)  {
                                ((CRUDController)page.getViewController()).read();
                                object =((CRUDController)page.getViewController()).getObject() ;
                            } else {
                                ((TransactionController)page.getViewController()).read();
                                object =((TransactionController)page.getViewController()).getObject() ;
                            }
                            displayNextPage(page, page.getPageKey(), object,req,resp,null);
                            return ;

                        }

                    }

                }else if (page.getViewController() instanceof GeneralController) {
                    ((UIGeneralPage)page).setObject(object);
                    ((GeneralController)page.getViewController()).setObject(object);
                    PageResult res = null ;
                    FixedAction fixedAction = page.getFixedAction();
                    if (fixedAction != null) {
                        res = page.applyFixedAction() ;
                    }else if (!Utils.isNullString(page.getSubmitAction()))
                        res =page.submit(page.getSubmitAction());
                    else
                        res = page.submit() ;
                    String nextPageKey = res!=null?res.getNextPageKey():"";
                    if (res !=null && res.getResponseAction().equals(PageResult.ResponseAction.FILEDOWNLOAD)) {
                        return;
                    }else  if (res != null && !Utils.isNullString(res.getNextPageKey()) && !nextPageKey.equals(curKey) ) {
                        if(res.getObject() != null)
                            object = res.getObject();
                        displayNextPage(page, nextPageKey, object,req,resp,res.getMode());
                        return ;
                    } else  {
                        if(res.getObject() != null)
                            object = res.getObject();
                        page.setErrors(res.getErrors());
                    }
                }else  if (page.getViewController() instanceof ListController && page instanceof UIListPage) {
                    PageResult res = null;
                    ViewController.Mode mode =null ;
                    if (page.getFixedAction() != null) {
                        if  ( page instanceof UIDataSheetPage) {
                            ((DataSheetController) page.getViewController()).setObjects(((UIDataSheetPage)page).getModelObjects());
                        }
                        res = page.applyFixedAction();
                        if (res !=null && res.getResponseAction().equals(PageResult.ResponseAction.FILEDOWNLOAD)) {
                            return;
                        }
                        if (!res.hasErrors() &&  page.getFixedAction() == FixedAction.ACTION_GOEDITMODE) {
                            mode =ViewController.Mode.UPDATE;
                        }
                        else if (!res.hasErrors() && page.getFixedAction() == FixedAction.ACTION_GOADDMODE ) {
                            mode =ViewController.Mode.CREATE;
                        }

                    }else {
                        res =page.submit() ;
                    }
                    String nextPageKey = res.getNextPageKey();
                    if (res != null &&  !res.hasErrors() && !Utils.isNullString(res.getNextPageKey()) && !nextPageKey.equals(curKey) ) {
                        if(res.getObject() != null)
                            object = res.getObject();
                        if(mode ==null)
                            mode= res.getMode();
                        displayNextPage(page, nextPageKey, object,req,resp,mode);
                        return ;
                    } else if (!Utils.isNullList(res.getErrors())) {
                        page.setErrors(res.getErrors());
                    }else {
                        if(mode ==null)
                            mode= res.getMode();
                        if (res.getResponseAction() == PageResult.ResponseAction.RELOADSTATICCONTS) {
                            page = reloadPage(page, object, req, resp, mode);
                        }else if (res.getResponseAction() == PageResult.ResponseAction.FULLRELOAD) {
                            displayNextPage(page, curKey, object,req,resp,mode);
                            return;
                        }else if (res.getResponseAction() == PageResult.ResponseAction.NEWPAGE) {
                            displayNextPage(page, nextPageKey, object,req,resp,mode);
                            return;
                        }


                    }
                }
            }

            write(resp,page,page,object) ;
        } catch(RadsAuthenticationException ex){
            resp.sendError(401);
        }catch(RadsAuthorizationException ex){
            resp.sendError(403);
        }catch (RadsException ex) {
            ex.printStackTrace();
            throw new ServletException(ex.getMessage());
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex.getMessage());
        }

    }

	/*protected UIPage getNextPage (PageResult pageResult,ModelObject object,HttpServletRequest req) throws Exception{
		String nextPageKey = pageResult.getNextPageKey();
		 if (!Utils.isNullString(nextPageKey) ) {
			 PageConfig config = AppConfig.APPCONFIG.getPageConfig(getServletContext().getRealPath("/"), nextPageKey);
			 UIPage page = getPagefromKey(config,object,req,null);
			 page.setPageKey(nextPageKey);
			 page.setErrors(pageResult.getErrors());
			 return page ;
		 }

		 return null;
	}*/


}
