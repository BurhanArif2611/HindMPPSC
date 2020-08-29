package com.hindmppsc.exam.models.MockTest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Option implements Serializable {
    @SerializedName("serial")
    @Expose
    private Integer serial;
    @SerializedName("option")
    @Expose
    private String option;

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
