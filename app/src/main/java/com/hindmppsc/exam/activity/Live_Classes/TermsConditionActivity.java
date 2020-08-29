package com.hindmppsc.exam.activity.Live_Classes;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.LoginActivity;
import com.hindmppsc.exam.database.UserProfileHelper;
import com.hindmppsc.exam.utility.AppConfig;
import com.hindmppsc.exam.utility.ErrorMessage;
import com.hindmppsc.exam.utility.LoadInterface;
import com.hindmppsc.exam.utility.NetworkUtil;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermsConditionActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.data_tv)
    TextView dataTv;

    @Override
    protected int getContentResId() {
        return R.layout.activity_terms_condition2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        Bundle bundle=getIntent().getExtras();
        if (bundle !=null){
            if (bundle.getString("Check").equals("terms")){
                titleTextTv.setText("Terms & Condition");
                GetTermsConditionOnServer();
            }else if (bundle.getString("Check").equals("about")){
                titleTextTv.setText("About Us");
                GetAboutOnServer();
            }
        }

    }

    private void GetTermsConditionOnServer() {
        if (NetworkUtil.isNetworkAvailable(TermsConditionActivity.this)) {
            Dialog materialDialog = ErrorMessage.initProgressDialog(TermsConditionActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.term_condition();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("TermsConditionActivity" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {

                            materialDialog.dismiss();
                            Gson gson = new Gson();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("GetTermsConditionOnServer" + object.toString());
                            //ErrorMessage.T(TermsConditionActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("GetTermsConditionOnServer" + object.toString());
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    dataTv.setText(Html.fromHtml(object.getString("term"), Html.FROM_HTML_MODE_COMPACT));
                                } else {
                                    dataTv.setText(Html.fromHtml(object.getString("term")));
                                }


                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(TermsConditionActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(TermsConditionActivity.this, LoginActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(TermsConditionActivity.this, object.getString("message"));

                            }
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

            ErrorMessage.T(TermsConditionActivity.this, "No Internet");

        }
    }private void GetAboutOnServer() {
        if (NetworkUtil.isNetworkAvailable(TermsConditionActivity.this)) {
            Dialog materialDialog = ErrorMessage.initProgressDialog(TermsConditionActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.about_us();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("about_us" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {

                            materialDialog.dismiss();
                            Gson gson = new Gson();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("about_us" + object.toString());
                            //ErrorMessage.T(TermsConditionActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("GetTermsConditionOnServer" + object.toString());
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    dataTv.setText(Html.fromHtml(object.getString("term"), Html.FROM_HTML_MODE_COMPACT));
                                } else {
                                    dataTv.setText(Html.fromHtml(object.getString("term")));
                                }


                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(TermsConditionActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(TermsConditionActivity.this, LoginActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(TermsConditionActivity.this, object.getString("message"));

                            }
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

            ErrorMessage.T(TermsConditionActivity.this, "No Internet");

        }
    }
}
