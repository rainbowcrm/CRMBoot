package com.rainbow.common.landing.controller;

import com.rainbow.crm.common.CRMGeneralController;
import com.techtrade.rads.framework.model.abstracts.ModelObject;
import com.techtrade.rads.framework.ui.abstracts.PageResult;

public class LandingController extends CRMGeneralController {

    @Override
    public PageResult submit(ModelObject object, String actionParam) {
        return super.submit(object, actionParam);
    }

    @Override
    public PageResult submit(ModelObject modelObject) {
        return new PageResult();
    }
}
