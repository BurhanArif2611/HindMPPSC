package com.hindmppsc.exam.activity.Live_Classes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.DashboardActivity;
import com.hindmppsc.exam.activity.LoginActivity;
import com.hindmppsc.exam.activity.SupportActivity;
import com.hindmppsc.exam.database.UserProfileHelper;
import com.hindmppsc.exam.utility.AppConfig;
import com.hindmppsc.exam.utility.ErrorMessage;
import com.hindmppsc.exam.utility.LoadInterface;
import com.hindmppsc.exam.utility.NetworkUtil;
import com.hindmppsc.exam.utility.SavedData;
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends BaseActivity {

    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.paytm_img)
    ImageButton paytmImg;
    @BindView(R.id.paytm_cardview)
    CardView paytmCardview;
    @BindView(R.id.gpay_img)
    ImageButton gpayImg;
    @BindView(R.id.gpay_cardview)
    CardView gpayCardview;
    @BindView(R.id.phone_pay_img)
    ImageButton phonePayImg;
    @BindView(R.id.phone_pay_cardview)
    CardView phonePayCardview;
    @BindView(R.id.proceed_pay_btn)
    Button proceedPayBtn;

    String package_id = "", paid_amount = "", Payment_mood = "";
    @BindView(R.id.cod_img)
    ImageButton codImg;
    @BindView(R.id.cod_cardview)
    CardView codCardview;
    private String transactionId = "";
    private String Order_id = "", Customer_id = "";
    private String exam_type_id = "", Material_type_id = "", Paper_id = "", Check = "";

    @Override
    protected int getContentResId() {
        return R.layout.activity_payment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        titleTextTv.setText("Payment");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Check = bundle.getString("fromActivity");
            if (bundle.getString("fromActivity").equals("Payment")) {
                package_id = bundle.getString("id");
                bundle.getString("exam_type");
                paid_amount = bundle.getString("price");
                proceedPayBtn.setText("₹ " + paid_amount + " Proceed To Pay");
            } else if (bundle.getString("fromActivity").equals("Prelims_Video_Course_PackegeActivity")) {
                package_id = bundle.getString("id");
                exam_type_id = bundle.getString("exam_type");
                Material_type_id = bundle.getString("Material_type_id");
                Paper_id = bundle.getString("Paper_id");
                paid_amount = bundle.getString("price");
                proceedPayBtn.setText("₹ " + paid_amount + " Proceed To Pay");
            } else if (bundle.getString("fromActivity").equals("preivious_paper")) {
                package_id = bundle.getString("id");
                exam_type_id = bundle.getString("exam_type");
              /*  Material_type_id=bundle.getString("Material_type_id");
                Paper_id=bundle.getString("Paper_id");*/
                paid_amount = bundle.getString("price");
                ErrorMessage.E("Payment"+paid_amount);
                proceedPayBtn.setText("₹ " + paid_amount + " Proceed To Pay");
            } else if (bundle.getString("fromActivity").equals("MockTest")) {
                package_id = bundle.getString("id");
                exam_type_id = bundle.getString("exam_type");
              /*  Material_type_id=bundle.getString("Material_type_id");
                Paper_id=bundle.getString("Paper_id");*/
                paid_amount = bundle.getString("price");
                ErrorMessage.E("Payment"+paid_amount);
                proceedPayBtn.setText("₹ " + paid_amount + " Proceed To Pay");
            }else if (bundle.getString("fromActivity").equals("Current_Affairs_Complete_course")) {
                package_id = bundle.getString("id");
                exam_type_id = bundle.getString("exam_type");
              /*  Material_type_id=bundle.getString("Material_type_id");
                Paper_id=bundle.getString("Paper_id");*/
                paid_amount = bundle.getString("price");
                ErrorMessage.E("Payment"+paid_amount);
                proceedPayBtn.setText("₹ " + paid_amount + " Proceed To Pay");
            }else if (bundle.getString("fromActivity").equals("Current_Affairs_Month")) {
                package_id = bundle.getString("id");
                exam_type_id = bundle.getString("exam_type");

              /*  Material_type_id=bundle.getString("Material_type_id");
                Paper_id=bundle.getString("Paper_id");*/
                paid_amount = bundle.getString("price");
                ErrorMessage.E("Payment"+paid_amount);
                proceedPayBtn.setText("₹ " + paid_amount + " Proceed To Pay");
            }
        }
    }

    @OnClick({R.id.paytm_cardview, R.id.gpay_cardview, R.id.phone_pay_cardview, R.id.proceed_pay_btn, R.id.paytm_img, R.id.gpay_img, R.id.phone_pay_img, R.id.cod_img, R.id.cod_cardview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.paytm_cardview:
                paytmImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                gpayImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                phonePayImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                codImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                Payment_mood = "Paytm";
                break;
            case R.id.gpay_cardview:
                paytmImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                gpayImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                phonePayImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                codImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                Payment_mood = "G Pay";
                break;
            case R.id.phone_pay_cardview:
                paytmImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                gpayImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                phonePayImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                codImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                Payment_mood = "Phone Pay";
                break;
            case R.id.proceed_pay_btn:
                if (!Payment_mood.equals("")) {
                    if (Payment_mood.equals("Phone Pay")) {
                        Payment(paid_amount, "8878215170@ybl", "Surendra Kumar Patel");
                    } else if (Payment_mood.equals("G Pay")) {
                        Payment(paid_amount, "mppschindclass@okaxis", "HIND MPPSC CLASS");
                    } else if (Payment_mood.equals("Paytm")) {
                        Payment(paid_amount, "8878215170@paytm", "HIND MPPSC CLASS");
                    } else if (Payment_mood.equals("COD")) {
                        transactionId = "123";
                        if (Check.equals("Payment")) {
                            PaymentOnServer();
                        } else if (Check.equals("Prelims_Video_Course_PackegeActivity")) {
                            Prelims_Video_Course_PaymentOnServer();
                        }else if (Check.equals("preivious_paper")) {
                            Previous_Paper_PaymentOnServer();
                        }else if (Check.equals("MockTest")) {
                            Previous_Paper_PaymentOnServer();
                        }else if (Check.equals("Current_Affairs_Complete_course")) {
                            Current_Affairs_Complete_coursePaymentOnServer();
                        }else if (Check.equals("Current_Affairs_Month")) {
                            Current_Affairs_Month();
                        }
                    }
                } else {
                    ErrorMessage.T(PaymentActivity.this, "Please Select Payment Mode !");
                }
                break;
            case R.id.paytm_img:
                paytmImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                gpayImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                phonePayImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                codImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                Payment_mood = "Paytm";
                break;
            case R.id.gpay_img:
                paytmImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                gpayImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                phonePayImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                codImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                Payment_mood = "G Pay";
                break;
            case R.id.phone_pay_img:
                paytmImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                gpayImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                phonePayImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                codImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                Payment_mood = "Phone Pay";
                break;
            case R.id.cod_img:
                paytmImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                gpayImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                phonePayImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                codImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                Payment_mood = "COD";
                break;
            case R.id.cod_cardview:
                paytmImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                gpayImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                phonePayImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_uncheck));
                codImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                Payment_mood = "COD";
                break;
        }
    }

    private void PaymentOnServer() {
        if (NetworkUtil.isNetworkAvailable(PaymentActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(PaymentActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("GetSubjectOnServer>" + "4" + ">>" + SavedData.getIMEI_Number() + ">>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            Call<ResponseBody> call = apiService.order_live_class(package_id, UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), transactionId, paid_amount, Payment_mood, "", paid_amount, "", "", SavedData.getIMEI_Number());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("GetPackegeOnServer" + object.toString());
                            ErrorMessage.T(PaymentActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("comes in if cond" + object.toString());
                                if (Payment_mood.equals("COD")) {
                                    ALert_PopUP();
                                } else {
                                    Intent returnIntent = new Intent();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("fromActivity", "Payment");
                                    returnIntent.putExtras(bundle);
                                    setResult(Activity.RESULT_OK, returnIntent);
                                    finish();
                                }
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(PaymentActivity.this, object.getString("message"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("JsonException" + e);
                        } catch (Exception e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                        }
                    } else {
                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();
                }
            });
        } else {
            ErrorMessage.T(PaymentActivity.this, "No Internet");
        }
    }

    private void Prelims_Video_Course_PaymentOnServer() {
        if (NetworkUtil.isNetworkAvailable(PaymentActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(PaymentActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("Prelims_Video_Course_PaymentOnServer>" + "4" + ">>" + SavedData.getIMEI_Number() + ">>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            Call<ResponseBody> call = apiService.order_paper_package(package_id, exam_type_id, Material_type_id, Paper_id, UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), transactionId, paid_amount, Payment_mood, "", paid_amount, "", "", SavedData.getIMEI_Number());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("Prelims_Video_Course_PaymentOnServer" + object.toString());
                            ErrorMessage.T(PaymentActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("comes in if cond" + object.toString());
                                if (Payment_mood.equals("COD")) {
                                    ALert_PopUP();
                                } else {
                                    Intent returnIntent = new Intent();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("fromActivity", "Payment");
                                    returnIntent.putExtras(bundle);
                                    setResult(Activity.RESULT_OK, returnIntent);
                                    finish();
                                }
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(PaymentActivity.this, object.getString("message"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("JsonException" + e);
                        } catch (Exception e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                        }
                    } else {
                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();
                }
            });
        } else {
            ErrorMessage.T(PaymentActivity.this, "No Internet");
        }
    }
    private void Previous_Paper_PaymentOnServer() {
        if (NetworkUtil.isNetworkAvailable(PaymentActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(PaymentActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("Previous_Paper_PaymentOnServer>" + package_id + ">>" + SavedData.getIMEI_Number() + ">>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id()+">>"+paid_amount);
            Call<ResponseBody> call = apiService.order_other_material(package_id, UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), transactionId, paid_amount, Payment_mood, SavedData.getIMEI_Number());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("Prelims_Video_Course_PaymentOnServer" + object.toString());
                            ErrorMessage.T(PaymentActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("comes in if cond" + object.toString());
                                if (Payment_mood.equals("COD")) {
                                    ALert_PopUP();
                                } else {
                                    Intent returnIntent = new Intent();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("fromActivity", "Payment");
                                    bundle.putString("id",package_id);
                                    bundle.putString("exam_type",exam_type_id);
                                    returnIntent.putExtras(bundle);
                                    setResult(Activity.RESULT_OK, returnIntent);
                                    finish();
                                }
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(PaymentActivity.this, object.getString("message"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("JsonException" + e);
                        } catch (Exception e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                        }
                    } else {
                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();
                }
            });
        } else {
            ErrorMessage.T(PaymentActivity.this, "No Internet");
        }
    }
    private void Current_Affairs_Complete_coursePaymentOnServer() {
        if (NetworkUtil.isNetworkAvailable(PaymentActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(PaymentActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("Previous_Paper_PaymentOnServer>" + package_id + ">>" + SavedData.getIMEI_Number() + ">>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id()+">>"+paid_amount);
            Call<ResponseBody> call = apiService.order_other_material(package_id, UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), transactionId, paid_amount, Payment_mood, SavedData.getIMEI_Number());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("Prelims_Video_Course_PaymentOnServer" + object.toString());
                            ErrorMessage.T(PaymentActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("comes in if cond" + object.toString());
                                if (Payment_mood.equals("COD")) {
                                    ALert_PopUP();
                                } else {
                                    Intent returnIntent = new Intent();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("fromActivity", "Payment");
                                    bundle.putString("id",package_id);
                                    bundle.putString("exam_type",exam_type_id);
                                    returnIntent.putExtras(bundle);
                                    setResult(Activity.RESULT_OK, returnIntent);
                                    finish();
                                }
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(PaymentActivity.this, object.getString("message"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("JsonException" + e);
                        } catch (Exception e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                        }
                    } else {
                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();
                }
            });
        } else {
            ErrorMessage.T(PaymentActivity.this, "No Internet");
        }
    }
    private void Current_Affairs_Month() {
        if (NetworkUtil.isNetworkAvailable(PaymentActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(PaymentActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("Current_Affairs_Month>" + package_id + ">>" + SavedData.getIMEI_Number() + ">>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id()+">>"+paid_amount);
            Call<ResponseBody> call = apiService.order_current_affair_month(package_id, UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), transactionId, paid_amount, Payment_mood, SavedData.getIMEI_Number());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("Current_Affairs_Month" + object.toString());
                            ErrorMessage.T(PaymentActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("comes in if cond" + object.toString());
                                if (Payment_mood.equals("COD")) {
                                    ALert_PopUP();
                                } else {
                                    Intent returnIntent = new Intent();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("fromActivity", "Payment");
                                    bundle.putString("id",package_id);
                                    bundle.putString("exam_type",exam_type_id);
                                    returnIntent.putExtras(bundle);
                                    setResult(Activity.RESULT_OK, returnIntent);
                                    finish();
                                }
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(PaymentActivity.this, object.getString("message"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("JsonException" + e);
                        } catch (Exception e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                        }
                    } else {
                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();
                }
            });
        } else {
            ErrorMessage.T(PaymentActivity.this, "No Internet");
        }
    }

    public void Payment(String amount, String UPI_id, String Name) {
        Order_id = generateString();
        Customer_id = generateString();
        ErrorMessage.E("Amount>>" + amount + ".00");
        final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder().with(this).setPayeeVpa(UPI_id).setPayeeName(Name).setTransactionId(Order_id).setTransactionRefId(Customer_id).setDescription("Purchase Suscribtion").setAmount(amount + ".00").build();
        easyUpiPayment.startPayment();
        easyUpiPayment.setPaymentStatusListener(new PaymentStatusListener() {
            @Override
            public void onTransactionCompleted(TransactionDetails transactionDetails) {
                ErrorMessage.E("onTransactionCompleted onTransactionCompleted" + transactionDetails.toString());
                transactionId = transactionDetails.getTransactionId();

            }

            @Override
            public void onTransactionSuccess() {
                ErrorMessage.E("onTransactionSuccess onTransactionSuccess");
                if (Check.equals("Payment")) {
                    PaymentOnServer();
                } else if (Check.equals("Prelims_Video_Course_PackegeActivity")) {
                    Prelims_Video_Course_PaymentOnServer();
                }else if (Check.equals("preivious_paper")) {
                    Previous_Paper_PaymentOnServer();
                }else if (Check.equals("MockTest")) {
                    Previous_Paper_PaymentOnServer();
                }else if (Check.equals("Current_Affairs_Complete_course")) {
                    Current_Affairs_Complete_coursePaymentOnServer();
                }
            }

            @Override
            public void onTransactionSubmitted() {
                ErrorMessage.E("onTransactionSubmitted onTransactionSubmitted");
            }

            @Override
            public void onTransactionFailed() {
                ErrorMessage.E("onTransactionFailed onTransactionFailed");
                ErrorMessage.T(PaymentActivity.this, "Transaction Failed");
            }

            @Override
            public void onTransactionCancelled() {
                ErrorMessage.E("onTransactionCancelled onTransactionCancelled");
                ErrorMessage.T(PaymentActivity.this, "Transaction Cancelled");
            }
        });
        //easyUpiPayment.detachListener();
    }

    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    public void ALert_PopUP() {
        final Dialog dialog = new Dialog(PaymentActivity.this);
        dialog.setContentView(R.layout.alert_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final Button submit_btn = (Button) dialog.findViewById(R.id.submit_btn);
        final Button done_btn = (Button) dialog.findViewById(R.id.done_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("Check", "Payment");
                ErrorMessage.I_clear(PaymentActivity.this, SupportActivity.class, bundle);
            }
        });
        done_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
             /*   Bundle bundle=new Bundle();
                bundle.putString("Check","Payment");*/
                ErrorMessage.I_clear(PaymentActivity.this, DashboardActivity.class, null);
            }
        });
        dialog.show();
    }
}
