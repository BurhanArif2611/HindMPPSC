package com.hindmppsc.exam.models.MaterialType_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("exam_type")
    @Expose
    private Integer examType;
    @SerializedName("material_type")
    @Expose
    private String materialType;
    @SerializedName("paper_type")
    @Expose
    private String paper_type;
    @SerializedName("material_type_id")
    @Expose
    private Integer material_type_id;
    @SerializedName("paper_id")
    @Expose
    private Integer paper_id;
    @SerializedName("package")
    @Expose
    private String Package;
    @SerializedName("subscribe")
    @Expose
    private String Subscribe;
    @SerializedName("price")
    @Expose
    private String Price;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("material")
    @Expose
    private String material;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("ebook")
    @Expose
    private String ebook;
    @SerializedName("sr_no")
    @Expose
    private String sr_no;
    @SerializedName("title")
    @Expose
    private String title;
   @SerializedName("url")
    @Expose
    private String url;
   @SerializedName("ebook_video")
    @Expose
    private String ebook_video;
   @SerializedName("month")
    @Expose
    private String month;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getEbook_video() {
        return ebook_video;
    }

    public void setEbook_video(String ebook_video) {
        this.ebook_video = ebook_video;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSr_no() {
        return sr_no;
    }

    public void setSr_no(String sr_no) {
        this.sr_no = sr_no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEbook() {
        return ebook;
    }

    public void setEbook(String ebook) {
        this.ebook = ebook;
    }

    public Integer getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(Integer paper_id) {
        this.paper_id = paper_id;
    }

    public String getPackage() {
        return Package;
    }

    public void setPackage(String aPackage) {
        Package = aPackage;
    }

    public String getPaper_type() {
        return paper_type;
    }

    public void setPaper_type(String paper_type) {
        this.paper_type = paper_type;
    }

    public Integer getMaterial_type_id() {
        return material_type_id;
    }

    public void setMaterial_type_id(Integer material_type_id) {
        this.material_type_id = material_type_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExamType() {
        return examType;
    }

    public void setExamType(Integer examType) {
        this.examType = examType;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String  price) {
        Price = price;
    }

    public String getSubscribe() {
        return Subscribe;
    }

    public void setSubscribe(String subscribe) {
        Subscribe = subscribe;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }


}
