package com.rainbow.crm.snapshot.controller;


import com.rainbow.crm.common.CommonUtil;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.ViewController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;
import com.techtrade.rads.framework.ui.abstracts.UIPage;
import com.techtrade.rads.framework.ui.components.UIStaticHTMLPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SnapShotController extends ViewController
{
    @Override
    public IRadsContext generateContext(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, UIPage uiPage) {
        return CommonUtil.generateContext(httpServletRequest,uiPage);
    }

    @Override
    public IRadsContext generateContext(String s, UIPage uiPage) {
        return CommonUtil.generateContext(s,uiPage);
    }
}
