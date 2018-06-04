package com.rainbow.crm.snapshot.model;

import com.techtrade.rads.framework.model.abstracts.ModelObject;

import java.util.Date;

public class RecentAppointment extends ModelObject{

    String appointmentTo;
    String partyType ;
    Date date;
    String agent;
    String status;



    public String getAppointmentTo() {
        return appointmentTo;
    }

    public void setAppointmentTo(String appointmentTo) {
        this.appointmentTo = appointmentTo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPartyType() {
        return partyType;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }
}
