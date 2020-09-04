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
    private Integer paper;

    public Integer getPaper() {
        return paper;
    }

    public void setPaper(Integer paper) {
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
}
