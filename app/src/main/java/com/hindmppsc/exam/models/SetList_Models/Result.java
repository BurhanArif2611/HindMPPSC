package com.hindmppsc.exam.models.SetList_Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("set_paper")
    @Expose
    private String setPaper;
    @SerializedName("material_type_id")
    @Expose
    private Integer materialTypeId;
    @SerializedName("exam_type")
    @Expose
    private Integer examType;
    @SerializedName("paper")
    @Expose
    private String paper;
    @SerializedName("url")
    @Expose
    private String url;

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper;
    }

    public String getSetPaper() {
        return setPaper;
    }

    public void setSetPaper(String setPaper) {
        this.setPaper = setPaper;
    }

    public Integer getMaterialTypeId() {
        return materialTypeId;
    }

    public void setMaterialTypeId(Integer materialTypeId) {
        this.materialTypeId = materialTypeId;
    }

    public Integer getExamType() {
        return examType;
    }

    public void setExamType(Integer examType) {
        this.examType = examType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
