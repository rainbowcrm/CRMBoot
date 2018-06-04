package com.rainbow.crm.snapshot.service;


import com.rainbow.crm.common.CRMContext;
import com.rainbow.crm.snapshot.model.SnapShot;
import com.rainbow.crm.snapshot.sqls.SnapShotSQLs;
import com.techtrade.rads.framework.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SnapShotService    {

    @Autowired
    SnapShotSQLs snapShotSQLs;

    /*@Autowired
    TargetService targetService  ;


   @Autowired
    AppointmentService appointmentService  ;
*/
    public SnapShot getSnapShot(CRMContext context , Date currentDate)
    {
       /* SnapShot snapShot  = new SnapShot() ;
        snapShot.setOrderFigure(new OrderFigure());
        snapShot.setPobFigure(new POBFigure());
        User user =  context.getLoggedInUser();
        Location location =null  ;
        if (user.getUserPortfolio().getAccessLevel().equals(FVConstants.USER_ACCESS.LOCATION)) {
            location = user.getUserPortfolio().getLocation() ;
        }

        Target target =  targetService.getTargetforDate(location,currentDate,context);
        if(target != null  )  {
            snapShot.setPeriodFrom(target.getFromDate());
            snapShot.setPeriodTo(target.getToDate());
            snapShot.setPeriod(target.getPeriod());

        }*/
       /* List<String> lastFeedBacks   =  snapShotSQLs.getFeedBacksForLocation(location.getId(),context.getLoggedinCompany() );if
        snapShot.setFeedbackDetailList(lastFeedBacks);*/

       /* List<FeedbackDetail> lastFeedBacks = new ArrayList<>();
        OrderFigure  orderFigure  = new OrderFigure() ;
        List<Appointment> completedAppointmentsForLocation =  appointmentService.getAllCompletedAppointmentsForLocation(location,target.getFromDate(),target.getToDate(),context);
        if (!Utils.isNullCollection(completedAppointmentsForLocation)) {
            snapShot.setVisitCount(completedAppointmentsForLocation.size());
            snapShot.setFeedbackCount(completedAppointmentsForLocation.size());
            completedAppointmentsForLocation.forEach( appointment ->   {
                FeedbackDetail feedbackDetail  = new FeedbackDetail();
                feedbackDetail.setGivenBy(appointment.getPartyNameWithType());
                feedbackDetail.setDate(appointment.getApptDate());
                feedbackDetail.setFeedback(appointment.getFeedBack());
                for (AgentSaleTarget agentSaleTarget : target.getAgentSaleTargets()) {
                    snapShot.getOrderFigure().setTargetAmount( snapShot.getOrderFigure().getTargetAmount() +  agentSaleTarget.getTargettedAmount() );
                }

                lastFeedBacks.add(feedbackDetail);
                if   (!Utils.isNullCollection(appointment.getStockistVisitOrderLines())) {
                    appointment.getStockistVisitOrderLines().forEach( stockistVisitOrderLine ->   {
                       double lineTotal = stockistVisitOrderLine.getRate() * stockistVisitOrderLine.getQty()  ;
                       snapShot.getOrderFigure().setAchievedAmount(snapShot.getOrderFigure().getAchievedAmount() + lineTotal) ;
                    });
                }
                if   (!Utils.isNullCollection(appointment.getOrderLines())) {
                    appointment.getOrderLines().forEach( orderLine ->   {
                        double lineTotal = orderLine.getRate() * orderLine.getQty()  ;
                        snapShot.getPobFigure().setAchievedAmount(snapShot.getPobFigure().getAchievedAmount() + lineTotal);

                    });

                }
            });
        }


        List<Appointment> upcomingAppointments =  appointmentService.getAllRecentAppointmentsForLocation(location,currentDate, new java.util.Date(currentDate.getTime() + (120 * 3600 * 1000)),context);
        if (upcomingAppointments!=  null)  {
            upcomingAppointments.forEach( appt ->  {
                RecentAppointment recentAppointment =  new RecentAppointment(appt);
                snapShot.addRecentAppointment(recentAppointment);
            });
        }
*///        snapShot.setFeedbackDetailList(lastFeedBacks);
      //  snapShot.setOrderFigure(orderFigure);
/*
        List<ItemSale> itemSales =  snapShotSQLs.getAllItemSales(location.getId(),context.getLoggedinCompany(),target.getFromDate(),target.getToDate());
        snapShot.setItemSales(itemSales);
        List<Appointment> recentAppointments =  new ArrayList<>();

        List<StoreSale> storeSales =  snapShotSQLs.getAllStoreSales(location.getId(),context.getLoggedinCompany(),target.getFromDate(),target.getToDate());
        snapShot.setStoreSales(storeSales);*/
        //snapShot.set


        return null ;
    }
}
