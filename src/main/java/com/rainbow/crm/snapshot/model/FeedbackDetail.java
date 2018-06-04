package com.rainbow.crm.snapshot.model;

import com.techtrade.rads.framework.model.abstracts.ModelObject;

import java.util.Date;

public class FeedbackDetail extends ModelObject {

    String feedback ;
    String givenBy;
    Date date;
    String doctorClass ;

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getGivenBy() {
        return givenBy;
    }

    public void setGivenBy(String givenBy) {
        this.givenBy = givenBy;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDoctorClass() {
        return doctorClass;
    }

    public void setDoctorClass(String doctorClass) {
        this.doctorClass = doctorClass;
    }
}
