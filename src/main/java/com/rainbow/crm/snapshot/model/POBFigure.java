package com.rainbow.crm.snapshot.model;

import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class POBFigure extends ModelObject {

    double targetAmount;
    double achievedAmount;

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getAchievedAmount() {
        return achievedAmount;
    }

    public void setAchievedAmount(double achievedAmount) {
        this.achievedAmount = achievedAmount;
    }

}
