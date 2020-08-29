package com.hindmppsc.exam.models.NewPurchase_Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Example implements Serializable {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("other_course")
    @Expose
    private List<OtherCourse> otherCourse = null;
    @SerializedName("current_affair")
    @Expose
    private List<CurrentAffair> currentAffair = null;
    @SerializedName("order_paper")
    @Expose
    private List<OrderPaper> orderPaper = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public List<OtherCourse> getOtherCourse() {
        return otherCourse;
    }

    public void setOtherCourse(List<OtherCourse> otherCourse) {
        this.otherCourse = otherCourse;
    }

    public List<CurrentAffair> getCurrentAffair() {
        return currentAffair;
    }

    public void setCurrentAffair(List<CurrentAffair> currentAffair) {
        this.currentAffair = currentAffair;
    }

    public List<OrderPaper> getOrderPaper() {
        return orderPaper;
    }

    public void setOrderPaper(List<OrderPaper> orderPaper) {
        this.orderPaper = orderPaper;
    }

}
