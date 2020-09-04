package com.hindmppsc.exam.models.NewPurchase_Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderPaper implements Serializable {
    @SerializedName("exam_id")
    @Expose
    private Integer examId;
    @SerializedName("exam")
    @Expose
    private String exam;
    @SerializedName("paper_id")
    @Expose
    private Integer paperId;
    @SerializedName("material_type_id")
    @Expose
    private Integer material_type_id;
    @SerializedName("paper")
    @Expose
    private String paper;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("tranjection_id")
    @Expose
    private String tranjectionId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTranjectionId() {
        return tranjectionId;
    }

    public void setTranjectionId(String tranjectionId) {
        this.tranjectionId = tranjectionId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMaterial_type_id() {
        return material_type_id;
    }

    public void setMaterial_type_id(Integer material_type_id) {
        this.material_type_id = material_type_id;
    }
}
