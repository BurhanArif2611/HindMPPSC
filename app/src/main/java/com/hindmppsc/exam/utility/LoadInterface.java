package com.hindmppsc.exam.utility;


import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface LoadInterface {


    @GET("ServiceList")
    Call<ResponseBody> ServiceList();

    @GET("get_all_product")
    Call<ResponseBody> get_all_product();

    @POST("PropertyGetTask")
    @FormUrlEncoded
    Call<ResponseBody> Pro_get_task(@Field(value = "user_id") String name);


    @POST("AddRemarkforProperty")
    @FormUrlEncoded
    Call<ResponseBody> Add_Remark_Pro(@Field(value = "user_id") String userId, @Field(value = "task_id") String taskId, @Field(value = "remark") String remark);

    @POST("AddRemarkforVehicle")
    @FormUrlEncoded
    Call<ResponseBody> Add_Remark_Vec(@Field(value = "user_id") String userId, @Field(value = "task_id") String taskId, @Field(value = "remark") String remark);

    @POST("vehicleGetTask")
    @FormUrlEncoded
    Call<ResponseBody> Veic_get_task(@Field(value = "user_id") String userId);

    @POST("payment_service_admin")
    @FormUrlEncoded
    Call<ResponseBody> payment_service_admin(@Field(value = "id") String id, @Field(value = "tranjestion_id") String tranjestion_id, @Field(value = "payment_method") String payment_method, @Field(value = "payment") String payment);

    @POST("request_by_vendor_id")
    @FormUrlEncoded
    Call<ResponseBody> request_by_vendor_id(@Field(value = "token") String token, @Field(value = "type") String type, @Field(value = "status") String status, @Field(value = "service_sub_type") String service_sub_type);


    @POST("approve_bid_list")
    @FormUrlEncoded
    Call<ResponseBody> approve_bid_list(@Field(value = "token") String token, @Field(value = "service_type") String type, @Field(value = "service_sub_type") String service_sub_type);

    @POST("get_subcategory")
    @FormUrlEncoded
    Call<ResponseBody> subcategory(@Field(value = "category_id") String category_id);

    @POST("getproductbycat")
    @FormUrlEncoded
    Call<ResponseBody> getproductbycat(@Field(value = "cat_id") String token);

    @POST("service_bid_user")
    @FormUrlEncoded
    Call<ResponseBody> service_bid_user(@Field(value = "token") String token);

    @POST("service_bid")
    @FormUrlEncoded
    Call<ResponseBody> service_bid(@Field(value = "token") String token, @Field(value = "service_id") String service_id);

    @POST("filter_product")
    @FormUrlEncoded
    Call<ResponseBody> filter_product(@Field(value = "token") String token, @Field(value = "company_id") String company_id, @Field(value = "model_id") String model_id, @Field(value = "category_id") String category_id, @Field(value = "year") String year);


    @POST("get_model")
    @FormUrlEncoded
    Call<ResponseBody> get_model(@Field(value = "company_id") String company_id);

    @POST("service_cart")
    @FormUrlEncoded
    Call<ResponseBody> service_cart(@Field(value = "auth_token") String auth_token, @Field(value = "package_id") String package_id, @Field(value = "company_id") String company_id, @Field(value = "model_id") String model_id, @Field(value = "city_id") String city_id);

    @GET("getcity")
    Call<ResponseBody> getcity();

    @GET("getservicetype")
    Call<ResponseBody> getservicetype();

    @GET("home_page_details")
    Call<ResponseBody> home_page_details();

    @GET("order_anything_category")
    Call<ResponseBody> order_anything_category();

    @GET("assigncompleteServiceList")
    Call<ResponseBody> assigncompleteServiceList();

    @GET("sale_status_history")
    Call<ResponseBody> sale_status_history();

    @POST("userlogin")
    @FormUrlEncoded
    Call<ResponseBody> user_Login(@Field(value = "email") String email,@Field(value = "password") String password, @Field(value = "device_id") String device_id, @Field(value = "imei") String imei);

    @POST("forgat_pass")
    @FormUrlEncoded
    Call<ResponseBody> forgat_pass (@Field(value = "email") String email);

    @POST("registeruser")
    @FormUrlEncoded
    Call<ResponseBody> userRagistration(@Field(value = "fullname") String fullname,@Field(value = "mobile") String mobile, @Field(value = "email") String email, @Field(value = "password") String password, @Field(value = "device_id") String device_id, @Field(value = "imei") String imei);

    @POST("live_class_subject")
    @FormUrlEncoded
    Call<ResponseBody> live_class_subject(@Field(value = "exam_id") String exam_id,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("exam_material_type")
    @FormUrlEncoded
    Call<ResponseBody> exam_material_type(@Field(value = "exam_type") String exam_type,@Field(value = "imei") String imei, @Field(value = "token") String token);


    @POST("cresh_prelims_and_prelims_set")
    @FormUrlEncoded
    Call<ResponseBody> cresh_prelims_and_prelims_set(@Field(value = "exam_type") String exam_type,@Field(value = "material_type") String material_type,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("cresh_mains_and_mains_and_interview_set")
    @FormUrlEncoded
    Call<ResponseBody> cresh_mains_and_mains_and_interview_set(@Field(value = "exam_type") String exam_type,@Field(value = "material_type") String material_type,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("cresh_prelims_and_prelims_set_paper")
    @FormUrlEncoded
    Call<ResponseBody> cresh_prelims_and_prelims_set_paper(@Field(value = "set") String set,@Field(value = "exam_type") String exam_type,@Field(value = "material_type") String material_type,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("cresh_mains_and_mains_and_interview_set_paper")
    @FormUrlEncoded
    Call<ResponseBody> cresh_mains_and_mains_and_interview_set_paper(@Field(value = "set") String set,@Field(value = "exam_type") String exam_type,@Field(value = "material_type") String material_type,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("interview_exam_video")
    @FormUrlEncoded
    Call<ResponseBody> interview_exam_video(@Field(value = "exam_type") String exam_type,@Field(value = "material_type") String material_type,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("exam_material_paper")
    @FormUrlEncoded
    Call<ResponseBody> exam_material_paper(@Field(value = "exam_type") String exam_type,@Field(value = "material_type") String material_type,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("complete_current_affair_list")
    @FormUrlEncoded
    Call<ResponseBody> complete_current_affair_list(@Field(value = "exam_type") String exam_type,@Field(value = "material_type") String material_type,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("current_affair_list_month")
    @FormUrlEncoded
    Call<ResponseBody> current_affair_list_month(@Field(value = "exam_type") String exam_type,@Field(value = "material_type") String material_type,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("month_current_affair_list")
    @FormUrlEncoded
    Call<ResponseBody> month_current_affair_list(@Field(value = "month_id") String month_id,@Field(value = "exam_type") String exam_type,@Field(value = "material_type") String material_type,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("prv_paper_prelims")
    @FormUrlEncoded
    Call<ResponseBody> prv_paper_prelims(@Field(value = "exam_type") String exam_type,@Field(value = "material_type") String material_type,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("mock_test_prelims")
    @FormUrlEncoded
    Call<ResponseBody> mock_test_prelims(@Field(value = "set") String set,@Field(value = "paper") String paper,@Field(value = "exam_type") String exam_type,@Field(value = "material_type") String material_type,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("exam_mock_test")
    @FormUrlEncoded
    Call<ResponseBody> exam_mock_test(@Field(value = "set") String set,@Field(value = "paper") String paper,@Field(value = "exam_type") String exam_type,@Field(value = "material_type") String material_type,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("interview_ebook_course")
    @FormUrlEncoded
    Call<ResponseBody> interview_ebook_course(@Field(value = "exam_type") String exam_type,@Field(value = "material_type") String material_type,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("syllabus_list_new")
    @FormUrlEncoded
    Call<ResponseBody> syllabus_list_new(@Field(value = "exam_type") String exam_type,@Field(value = "material_type") String material_type,@Field(value = "imei") String imei, @Field(value = "token") String token);


    @POST("exam_paper_unit")
    @FormUrlEncoded
    Call<ResponseBody> exam_paper_unit(@Field(value = "exam_type") String exam_type,@Field(value = "material_type") String material_type,@Field(value = "paper_id") String paper_id,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("update_your_self")
    @FormUrlEncoded
    Call<ResponseBody> update_your_self(@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("tsak_prelims")
    @FormUrlEncoded
    Call<ResponseBody> tsak_prelims(@Field(value = "set") String set,@Field(value = "paper") String paper,@Field(value = "imei") String imei, @Field(value = "token") String token, @Field(value = "date") String date);

    @POST("today_gs")
    @FormUrlEncoded
    Call<ResponseBody> today_gs(@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("task_for_mains")
    @FormUrlEncoded
    Call<ResponseBody> task_for_mains(@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("today_tsak_prelims")
    @FormUrlEncoded
    Call<ResponseBody> today_task_prelims(@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("task_for_interview")
    @FormUrlEncoded
    Call<ResponseBody> task_for_interview(@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("live_class_comment")
    @FormUrlEncoded
    Call<ResponseBody> live_class_comment(@Field(value = "live_class_id") String live_class_id,@Field(value = "comment") String comment,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("get_comment")
    @FormUrlEncoded
    Call<ResponseBody> get_comment(@Field(value = "live_class_id") String live_class_id);

    @POST("live_class")
    @FormUrlEncoded
    Call<ResponseBody> live_class(@Field(value = "exam_id") String exam_id,@Field(value = "subject_id") String subject_id,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("prv_live_class")
    @FormUrlEncoded
    Call<ResponseBody> privious_live_class(@Field(value = "exam_id") String exam_id,@Field(value = "subject_id") String subject_id,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("prelims_exam_video")
    @FormUrlEncoded
    Call<ResponseBody> prelims_exam_video(@Field(value = "exam_type") String exam_type,@Field(value = "material_type") String material_type,@Field(value = "paper_id") String paper_id,@Field(value = "unit_id") String unit_id,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("prelims_exam_ebook")
    @FormUrlEncoded
    Call<ResponseBody> prelims_exam_ebook(@Field(value = "exam_type") String exam_type,@Field(value = "material_type") String material_type,@Field(value = "paper_id") String paper_id,@Field(value = "unit_id") String unit_id,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("new_order_history")
    @FormUrlEncoded
    Call<ResponseBody> new_order_history(@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("user_noti")
    @FormUrlEncoded
    Call<ResponseBody> user_noti (@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("live_class_package")
    @FormUrlEncoded
    Call<ResponseBody> live_class_package(@Field(value = "subject_id") String subject_id,@Field(value = "exam_id") String exam_id,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("exam_paper_package")
    @FormUrlEncoded
    Call<ResponseBody> exam_paper_package(@Field(value = "exam_type") String exam_type,@Field(value = "material_type") String material_type,@Field(value = "paper_id") String paper_id,@Field(value = "imei") String imei, @Field(value = "token") String token);

    @POST("order_live_class")
    @FormUrlEncoded
    Call<ResponseBody> order_live_class(@Field(value = "package_id") String package_id, @Field(value = "token") String token, @Field(value = "tranjection_id") String tranjection_id, @Field(value = "paid_amount") String paid_amount, @Field(value = "payment_method") String payment_method, @Field(value = "coupon_name") String coupon_name, @Field(value = "total_price") String total_price, @Field(value = "discounted_price") String discounted_price, @Field(value = "coupon_discount_per") String coupon_discount_per,@Field(value = "imei") String imei);

    @POST("order_paper_package")
    @FormUrlEncoded
    Call<ResponseBody> order_paper_package(@Field(value = "package_id") String package_id,@Field(value = "exam_type") String exam_type,@Field(value = "material_type") String material_type,@Field(value = "paper_id") String paper_id, @Field(value = "token") String token, @Field(value = "tranjection_id") String tranjection_id, @Field(value = "paid_amount") String paid_amount, @Field(value = "payment_method") String payment_method, @Field(value = "coupon_name") String coupon_name, @Field(value = "total_price") String total_price, @Field(value = "discounted_price") String discounted_price, @Field(value = "coupon_discount_per") String coupon_discount_per,@Field(value = "imei") String imei);

    @POST("order_other_material")
    @FormUrlEncoded
    Call<ResponseBody> order_other_material(@Field(value = "package_id") String package_id, @Field(value = "token") String token, @Field(value = "tranjection_id") String tranjection_id, @Field(value = "paid_amount") String paid_amount, @Field(value = "payment_method") String payment_method,@Field(value = "imei") String imei);

    @POST("order_current_affair_month")
    @FormUrlEncoded
    Call<ResponseBody> order_current_affair_month(@Field(value = "package_id") String package_id, @Field(value = "token") String token, @Field(value = "tranjection_id") String tranjection_id, @Field(value = "paid_amount") String paid_amount, @Field(value = "payment_method") String payment_method,@Field(value = "imei") String imei);

    @POST("gatlatlong")
    @FormUrlEncoded
    Call<ResponseBody> gatlatlong(@Field(value = "user_id") String user_id);

    @POST("order_anything")
    @FormUrlEncoded
    Call<ResponseBody> order_anything(@Field(value = "user_id") String user_id, @Field(value = "category_name") String category_name, @Field(value = "message") String message, @Field(value = "pickup_address") String pickup_address, @Field(value = "pickup_latitude") String pickup_latitude, @Field(value = "pickup_longitude") String pickup_longitude, @Field(value = "delivery_address") String delivery_address, @Field(value = "delivery_latitude") String delivery_latitude, @Field(value = "delivery_longitude") String delivery_longitude, @Field(value = "delivery_fee") String delivery_fee, @Field(value = "payment_method") String payment_method, @Field(value = "transaction_id") String transaction_id, @Field(value = "shop_name") String shop_name);

    @POST("shop_by_cate")
    @FormUrlEncoded
    Call<ResponseBody> shop_by_cate(@Field(value = "category_id") String category_id);

    @POST("search_data")
    @FormUrlEncoded
    Call<ResponseBody> search_data(@Field(value = "search_value") String search_value);

    @POST("Get_confirm_order")
    @FormUrlEncoded
    Call<ResponseBody> Get_confirm_order(@Field(value = "user_id") String user_id);

    @POST("product_by_shopId")
    @FormUrlEncoded
    Call<ResponseBody> product_by_shopId(@Field(value = "shop_id") String shop_id);

    @POST("Get_address")
    @FormUrlEncoded
    Call<ResponseBody> Get_address(@Field(value = "user_id") String user_id);

    @POST("add_address")
    @FormUrlEncoded
    Call<ResponseBody> add_address(@Field(value = "address") String address, @Field(value = "latitude") String latitude_var, @Field(value = "longitude") String longitude_var, @Field(value = "user_id") String user_id);

    @POST("editaddress")
    @FormUrlEncoded
    Call<ResponseBody> editaddress(@Field(value = "address_id") String address_id, @Field(value = "address") String address_var, @Field(value = "latitude") String latitude_var, @Field(value = "longitude") String longitude_var, @Field(value = "user_id") String user_id);

    @POST("deleteAddress")
    @FormUrlEncoded
    Call<ResponseBody> deleteAddress(@Field(value = "address_id") String address_id);


    @POST("getCartByUserId")
    @FormUrlEncoded
    Call<ResponseBody> getCartByUserId(@Field(value = "auth_token") String auth_token);


    @POST("getservicecart")
    @FormUrlEncoded
    Call<ResponseBody> getservicecart(@Field(value = "auth_token") String auth_token);

    @POST("EmptyCart")
    @FormUrlEncoded
    Call<ResponseBody> EmptyCart(@Field(value = "auth_token") String auth_token);

    @POST("EmptyServiceCart")
    @FormUrlEncoded
    Call<ResponseBody> EmptyServiceCart(@Field(value = "auth_token") String auth_token);

    @POST("myOrder")
    @FormUrlEncoded
    Call<ResponseBody> myOrder(@Field(value = "auth_token") String auth_token);

    @POST("reviewProduct")
    @FormUrlEncoded
    Call<ResponseBody> reviewProduct(@Field(value = "auth_token") String auth_token, @Field(value = "product_id") String product_id, @Field(value = "review") String review, @Field(value = "rating") String rating);

    @POST("delete_add_to_cart")
    @FormUrlEncoded
    Call<ResponseBody> RemoveToCart(@Field(value = "user_id") String auth_token, @Field(value = "id") String product_id);

    @POST("RemoveToServiceCart")
    @FormUrlEncoded
    Call<ResponseBody> RemoveToServiceCart(@Field(value = "auth_token") String auth_token, @Field(value = "service_id") String service_id);

    @POST("confirm_order")
    @FormUrlEncoded
    Call<ResponseBody> confirmOrder(@Field(value = "user_id") String user_id, @Field(value = "delivery_fee") String delivery_fee, @Field(value = "total_amount") String total_amount, @Field(value = "paid_amount") String paid_amount, @Field(value = "payment_method") String payment_method, @Field(value = "transaction_id") String transaction_id, @Field(value = "address") String address, @Field(value = "latitude") String latitude, @Field(value = "longitude") String longitude);

    @POST("service_order_confirm")
    @FormUrlEncoded
    Call<ResponseBody> service_order_confirm(@Field(value = "auth_token") String auth_token, @Field(value = "payment_method") String payment_method, @Field(value = "transaction_id") String transaction_id, @Field(value = "address") String address, @Field(value = "date") String date, @Field(value = "time") String time, @Field(value = "select_mode") String select_mode);


    @POST("getservicebyid")
    @FormUrlEncoded
    Call<ResponseBody> getservicebyid(@Field(value = "city_id") String city_id, @Field(value = "model_id") String model_id, @Field(value = "company_id") String company_id, @Field(value = "service_cat") String service_cat);

    @POST("front_car_service")
    @FormUrlEncoded
    Call<ResponseBody> front_car_service(@Field(value = "city_id") String city_id, @Field(value = "model_id") String model_id, @Field(value = "company_id") String company_id);

    @Multipart
    @POST("PropertyImage")
    Call<ResponseBody> pro_image(@Part("user_id") RequestBody userId, @Part MultipartBody.Part image);

    @POST("update_user")
    @FormUrlEncoded
    Call<ResponseBody> UpdateProfile_WithoutImage(@Field(value = "token") String token, @Field(value = "fullname") String first_name, @Field(value = "contact") String contact, @Field(value = "email") String email);

    @Multipart
    @POST("service_request")
    Call<ResponseBody> service_request(@Query("token") String token, @Query("service_type") String service_type, @Query("type") String type, @Query("event_name") String event_name, @Query("from_date") String from_date, @Query("to_date") String to_date, @Query("city") String city, @Query("tehsil") String tehsil, @Query("no_of_member") String no_of_member, @Query("no_of_item") String no_of_item, @Part MultipartBody.Part[] file1);

    @Multipart
    @POST("register_vendor")
    Call<ResponseBody> register_vendor(@Query("fullname") String fullname, @Query("contact") String contact, @Query("state") String state, @Query("district") String district, @Query("tehsil") String tehsil, @Query("city") String city, @Query("address") String address, @Query("pincode") String pincode, @Query("password") String password, @Query("service_type[]") ArrayList<String> service_type, @Part MultipartBody.Part file1);

    //token,service_type,event_name,event_date,type,from_date,to_date,vehical_type,no_of_days,singer_dancer,pick_up_time,no_of_watercan,form_place,to_place,no_of_day,no_of_baggi_horse,no_of_member,no_of_item,vaters,decoretion_type,cantenscantens,no_of_dholno_of_dhol,service_imageservice_image
    @POST("service_request")
    @FormUrlEncoded
    Call<ResponseBody> service_request_WithoutImage(@Field(value = "token") String token, @Field(value = "service_type") String service_type, @Field(value = "type") String type, @Field(value = "event_name") String event_name, @Field(value = "event_date") String event_date, @Field(value = "city") String city, @Field(value = "tehsil") String tehsil, @Field(value = "no_of_dhol") String no_of_dhol, @Field(value = "no_of_watercan") String no_of_watercan, @Field(value = "singer_dancer") String singer_dancer, @Field(value = "no_of_member") String no_of_member, @Field(value = "no_of_item") String no_of_item, @Field(value = "decoretion_type") String decoretion_type);

    @POST("service_request")
    @FormUrlEncoded
    Call<ResponseBody> service_request_WithoutImage(@Field(value = "token") String token, @Field(value = "service_type") String service_type, @Field(value = "type") String type, @Field(value = "event_name") String event_name, @Field(value = "event_date") String event_date, @Field(value = "city") String city, @Field(value = "tehsil") String tehsil, @Field(value = "no_of_dhol") String no_of_dhol, @Field(value = "no_of_watercan") String no_of_watercan, @Field(value = "singer_dancer") String singer_dancer, @Field(value = "no_of_member") String no_of_member, @Field(value = "no_of_item") String no_of_item, @Field(value = "cantens") String cantens, @Field(value = "form_place") String form_place, @Field(value = "to_place") String to_place, @Field(value = "pick_up_time") String pick_up_time, @Field(value = "vehical_type") String vehical_type, @Field(value = "no_of_baggi_horse") String no_of_baggi_horse, @Field(value = "no_of_days") String no_of_day, @Query("to_date") String to_date, @Query("no_horse") String no_horse);

    @POST("service_request")
    @FormUrlEncoded
    Call<ResponseBody> service_request_WithoutImage(@Field(value = "token") String token, @Field(value = "service_type") String service_type, @Field(value = "type") String type, @Field(value = "event_name") String event_name, @Field(value = "event_date") String event_date, @Field(value = "city") String city, @Field(value = "tehsil") String tehsil, @Field(value = "no_of_dhol") String no_of_dhol, @Field(value = "no_of_watercan") String no_of_watercan, @Field(value = "singer_dancer") String singer_dancer, @Field(value = "no_of_member") String no_of_member, @Field(value = "no_of_item") String no_of_item, @Field(value = "cantens") String cantens, @Field(value = "form_place") String form_place, @Field(value = "to_place") String to_place, @Field(value = "pick_up_time") String pick_up_time, @Field(value = "vehical_type") String vehical_type, @Field(value = "no_of_baggi_horse") String no_of_baggi_horse, @Field(value = "no_of_day") String no_of_day, @Field(value = "drone_crean") String drone_crean, @Field(value = "pre_vid_shutting") String pre_vid_shutting, @Field(value = "led_wall") String led_wall, @Field(value = "vid_cd_hours") String vid_cd_hours);


    @POST("update_bid_by_vendorid")
    @FormUrlEncoded
    Call<ResponseBody> update_bid_by_vendorid(@Field(value = "id") String id, @Field(value = "bid_amount") String bid_amount, @Field(value = "title") String title);


    @POST("delete_history")
    @FormUrlEncoded
    Call<ResponseBody> delete_history(@Field(value = "id") String id);

    @Multipart
    @POST("UpdateProfile1")
    Call<ResponseBody> updateProfile(@Part("token") RequestBody user_id, @Part("first_name") RequestBody fname, @Part("mobile") RequestBody phone, @Part("email") RequestBody email, @Part("device_id") RequestBody device_id, @Part("imei") RequestBody imei, @Part MultipartBody.Part part);


    @POST("UpdateProfile1")
    @FormUrlEncoded
    Call<ResponseBody> updateProfile_withoutimage(@Field(value = "token") String token, @Field(value = "first_name") String fname, @Field(value = "mobile") String phone, @Field(value = "email") String email, @Field(value = "device_id") String device_id, @Field(value = "imei") String imei);


    /* @POST("add_to_cart")
     @FormUrlEncoded
     Call<ResponseBody> add_to_cart(@Field(value = "user_id") String user_id, @Field(value = "product_id") String product_id, @Field(value = "product_name") String product_name, @Field(value = "shop_name") String shop_name, @Field(value = "quantity") String quantity, @Field(value = "discounted_price") String price, @Field(value = "shop_id") String shop_id, @Field(value = "total_price") String total_price, @Field(value = "unit_id") String unit_id);
 */
    @POST("get_add_to_cart")
    @FormUrlEncoded
    Call<ResponseBody> get_add_to_cart(@Field(value = "user_id") String user_id);

    @POST("add_to_cart")
    @FormUrlEncoded
    Call<ResponseBody> add_to_cart(@Field(value = "user_id") String user_id, @Field(value = "product_id") String product_id, @Field(value = "unit_id") String unit_id, @Field(value = "quantity") String quantity, @Field(value = "coupan_name") String coupan_name, @Field(value = "coupon_discount_per") String coupon_discount_per, @Field(value = "price_id") String price_id, @Field(value = "shop_id") String shop_id);

    @POST("updateCart")
    @FormUrlEncoded
    Call<ResponseBody> updateCart(@Field(value = "user_id") String user_id, @Field(value = "product_id") String product_id, @Field(value = "unit_id") String unit_id, @Field(value = "quantity") String quantity);

    @GET("about_us")
    Call<ResponseBody> about_us();

    @GET("exam_type")
    Call<ResponseBody> exam_type();

    @GET("refund")
    Call<ResponseBody> refund();

    @GET("Privacy_Policy")
    Call<ResponseBody> Privacy_Policy();

    @GET("term_condition")
    Call<ResponseBody> term_condition();

    @GET("faq")
    Call<ResponseBody> getfaq();

    @POST("purchase_data")
    @FormUrlEncoded
    Call<ResponseBody> purchase_data(@Field(value = "token") String token, @Field(value = "tranjection_id") String tranjection_id, @Field(value = "paid_amount") String paid_amount, @Field(value = "total_amount") String total_amount, @Field(value = "payment_method") String payment_method,@Field(value = "imei") String imei);

}