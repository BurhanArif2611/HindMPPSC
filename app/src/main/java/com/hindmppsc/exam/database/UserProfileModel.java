package com.hindmppsc.exam.database;

import android.database.sqlite.SQLiteDatabase;

public class UserProfileModel {
    public static final String TABLE_NAME = "UserProfile";
    public static final String KEY_ID = "_id";
    public static final String KEY_User_id = "user_id";
    public static final String KEY_NAME = "displayName";
    public static final String KEY_EmailId = "emailId";
    public static final String KEY_Profile_Pic = "profile_pic";
    public static final String KEY_Phone = "userPhone";



    public static void creteTable(SQLiteDatabase db) {
        String CREATE_STUDENTTABLE = "create table " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_User_id + " text, "
                + KEY_NAME + " text, "
                + KEY_EmailId + " text, "
                + KEY_Profile_Pic + " text, "
                + KEY_Phone + " text " +
                ")";
        db.execSQL(CREATE_STUDENTTABLE);
    }

    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
    String user_id,displayName,emaiiId,profile_pic,userPhone;



    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getEmaiiId() {
        return emaiiId;
    }

    public void setEmaiiId(String emaiiId) {
        this.emaiiId = emaiiId;
    }



    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }



    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }




}


