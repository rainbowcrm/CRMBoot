package com.rainbow.crm.snapshot.service;


import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.snapshot.model.SnapShot;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class SnapShotAjaxService implements IAjaxLookupService {

    @Override
    public String lookupValues(Map<String, String> map, IRadsContext iRadsContext) {
        SnapShot snapShot = new SnapShot();

        return snapShot.toJSON();
    }

    @Override
    public IRadsContext generateContext(HttpServletRequest httpServletRequest) {
        return CommonUtil.generateContext(httpServletRequest,null);
    }
}
