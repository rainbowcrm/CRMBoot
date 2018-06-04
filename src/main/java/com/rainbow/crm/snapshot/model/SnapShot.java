package com.rainbow.crm.snapshot.model;


import com.techtrade.rads.framework.model.abstracts.ModelObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SnapShot extends ModelObject {

    Date periodFrom;
    Date periodTo;

    String  location;
    String  period;

    int visitCount;

    SalesFigure salesFigure ;
    OrderFigure orderFigure ;
    POBFigure pobFigure ;

    List<FeedbackDetail> feedbackDetailList  ;
    List<RecentAppointment> recentAppointments ;
    List<ItemSale> itemSales ;
    List<StoreSale> storeSales ;
    int feedbackCount;

    public Date getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(Date periodFrom) {
        this.periodFrom = periodFrom;
    }

    public Date getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(Date periodTo) {
        this.periodTo = periodTo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public SalesFigure getSalesFigure() {
        return salesFigure;
    }

    public void setSalesFigure(SalesFigure salesFigure) {
        this.salesFigure = salesFigure;
    }

    public OrderFigure getOrderFigure() {
        return orderFigure;
    }

    public void setOrderFigure(OrderFigure orderFigure) {
        this.orderFigure = orderFigure;
    }

    public POBFigure getPobFigure() {
        return pobFigure;
    }

    public void setPobFigure(POBFigure pobFigure) {
        this.pobFigure = pobFigure;
    }

    public List<FeedbackDetail> getFeedbackDetailList() {
        return feedbackDetailList;
    }

    public void setFeedbackDetailList(List<FeedbackDetail> feedbackDetailList) {
        this.feedbackDetailList = feedbackDetailList;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public int getFeedbackCount() {
        return feedbackCount;
    }

    public void setFeedbackCount(int feedbackCount) {
        this.feedbackCount = feedbackCount;
    }

    public List<RecentAppointment> getRecentAppointments() {
        return recentAppointments;
    }

    public void setRecentAppointments(List<RecentAppointment> recentAppointments) {
        this.recentAppointments = recentAppointments;
    }

    public void addRecentAppointment(RecentAppointment recentAppointment) {
        if (recentAppointments == null)
            recentAppointments = new ArrayList<RecentAppointment>();
        recentAppointments.add(recentAppointment);
    }

    public List<ItemSale> getItemSales() {
        return itemSales;
    }

    public void setItemSales(List<ItemSale> itemSales) {
        this.itemSales = itemSales;
    }

    public void addItemSale (ItemSale itemSale) {
        if (itemSales == null)
            itemSales = new ArrayList<>() ;
        itemSales.add(itemSale);
    }

    public List<StoreSale> getStoreSales() {
        return storeSales;
    }

    public void setStoreSales(List<StoreSale> storeSales) {
        this.storeSales = storeSales;
    }

    public void addStoreSale(StoreSale storeSale)
    {
        if (storeSales == null)
            storeSales = new ArrayList<>();
        storeSales.add(storeSale);
    }
}


