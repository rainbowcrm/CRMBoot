package com.rainbow.crm.snapshot.model;

import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class AppointmentFigure extends ModelObject {

    int targettedVisit;
    int completedVisit ;


    public int getTargettedVisit() {
        return targettedVisit;
    }

    public void setTargettedVisit(int targettedVisit) {
        this.targettedVisit = targettedVisit;
    }

    public int getCompletedVisit() {
        return completedVisit;
    }

    public void setCompletedVisit(int completedVisit) {
        this.completedVisit = completedVisit;
    }
}
