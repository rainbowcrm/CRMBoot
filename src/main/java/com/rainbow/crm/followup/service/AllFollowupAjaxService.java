package com.rainbow.crm.followup.service;

import com.rainbow.crm.common.CRMConstants;
import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.common.CommonUtil;
import com.rainbow.crm.common.SpringObjectFactory;
import com.rainbow.crm.followup.model.Followup;
import com.techtrade.rads.framework.context.IRadsContext;
import com.techtrade.rads.framework.controller.abstracts.IAjaxLookupService;
import com.techtrade.rads.framework.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AllFollowupAjaxService implements IAjaxLookupService{

    @Override
    public String lookupValues(Map<String, String> map, IRadsContext iRadsContext) {
        IFollowupService followupService = (IFollowupService) SpringObjectFactory.INSTANCE.getInstance("IFollowupService");
        Date fromDate =  new java.util.Date(new java.util.Date().getTime() - ( 6l * 30l * 24l * 3600l * 1000l ));
        Date toDate =  new java.util.Date(new java.util.Date().getTime() + ( 6l * 30l * 24l * 3600l * 1000l ));
        List<Followup> followupList= followupService.getFollowupsforDateRange(fromDate,toDate,((CRMContext)iRadsContext));
        if(!Utils.isNullList(followupList)) {
            StringBuffer buffer = new StringBuffer("");
            followupList.forEach(followup ->   {
                if (buffer.length()  >3  )
                    buffer.append(",\n") ;
                buffer.append("\n{\n");
                buffer.append("\"id\":" + followup.getId() + ",\n");
                buffer.append("\"title\":\"-" + followup.getLead().getCustomer().getFullName() + "\",\n");
                buffer.append("\"start\":\"" + CommonUtil.getFollowupTimeAsString(followup,"yyyy-MM-dd","HH:mm") + "\",\n");
                buffer.append("\"allDay\":false,\n");
                if(followup.getStatus().getCode().equalsIgnoreCase(CRMConstants.FOLLOWUP_STATUS.SCHEDULED)  )
                    buffer.append("\"className\":\"scheduled\"\n");
                else if(followup.getStatus().getCode().equalsIgnoreCase(CRMConstants.FOLLOWUP_STATUS.COMPLETED) )
                    buffer.append("\"className\":\"completed\"\n");
                else if(followup.getStatus().getCode().equalsIgnoreCase(CRMConstants.FOLLOWUP_STATUS.ABORT) )
                    buffer.append("\"className\":\"cancelled\"\n");
                else if(followup.getStatus().getCode().equalsIgnoreCase(CRMConstants.FOLLOWUP_STATUS.OPEN))
                    buffer.append("\"className\":\"pending\"\n");
                buffer.append("\n}");

            });
            return buffer.toString();
        }

        return null;
    }

    @Override
    public IRadsContext generateContext(HttpServletRequest httpServletRequest) {
        return CommonUtil.generateContext(httpServletRequest,null);
    }
}
