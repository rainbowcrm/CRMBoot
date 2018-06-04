package com.rainbow.crm.snapshot.model;

import com.techtrade.rads.framework.model.abstracts.ModelObject;

public class ItemSale extends ModelObject {

    String item;
    double value;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
