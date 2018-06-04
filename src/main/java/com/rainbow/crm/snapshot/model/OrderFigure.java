package com.rainbow.crm.snapshot.model;

import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class OrderFigure extends ModelObject {

    double targetAmount;
    double achievedAmount;
    double achievedPercent;

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

    public double getAchievedPercent() {
        return Math.round(achievedAmount * 100d / targetAmount );
    }

    public void setAchievedPercent(double achievedPercent) {
   //     this.achievedPercent = achievedPercent;
    }
}
