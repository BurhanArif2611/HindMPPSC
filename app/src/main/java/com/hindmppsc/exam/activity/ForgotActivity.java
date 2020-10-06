package com.hindmppsc.exam.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hindmppsc.exam.R;
import com.hindmppsc.exam.database.UserProfileHelper;
import com.hindmppsc.exam.database.UserProfileModel;
import com.hindmppsc.exam.utility.AppConfig;
import com.hindmppsc.exam.utility.ErrorMessage;
import com.hindmppsc.exam.utility.LoadInterface;
import com.hindmppsc.exam.utility.NetworkUtil;
import com.hindmppsc.exam.utility.SavedData;
import com.hindmppsc.exam.utility.UserAccount;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotActivity extends AppCompatActivity {

    @BindView(R.id.email_id_etv)
    EditText emailIdEtv;
    @BindView(R.id.sign_in_btn)
    Button signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.sign_in_btn)
    public void onClick() {
        if (UserAccount.isEmpty(emailIdEtv)) {
            if (UserAccount.isEmailValid(emailIdEtv)) {
                ForgotOnServer();
            } else {
                UserAccount.EditTextPointer.setError("Email ID Invalid !");
                UserAccount.EditTextPointer.requestFocus();
            }
        } else {
            UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
            UserAccount.EditTextPointer.requestFocus();
        }
    }
    private void ForgotOnServer() {
        if (NetworkUtil.isNetworkAvailable(ForgotActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(ForgotActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);/*SavedData.getFCM_ID()*/
            Call<ResponseBody> call = apiService.forgat_pass(emailIdEtv.getText().toString());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("comes in cond" + object.toString());
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("comes in if cond" + object.toString());
                                ErrorMessage.T(ForgotActivity.this, object.getString("message"));
                                ALert_PopUP();
                               /* JSONObject jsonObject1 = object.getJSONObject("result");

                                UserProfileModel userProfileModel = new UserProfileModel();
                                userProfileModel.setDisplayName(jsonObject1.getString("fullname"));
                                userProfileModel.setUser_id(jsonObject1.getString("token"));
                                userProfileModel.setEmaiiId(jsonObject1.getString("email"));
                                userProfileModel.setUserPhone(jsonObject1.getString("contact"));
                                userProfileModel.setProfile_pic(jsonObject1.getString("pro_image"));
                                UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);
                                ErrorMessage.I(ForgotActivity.this, DashboardActivity.class, null);*/


                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(ForgotActivity.this, object.getString("message"));
                                materialDialog.dismiss();
                            }
                        }  catch (Exception e) {
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
            ErrorMessage.T(ForgotActivity.this, "No Internet");
        }
    }

    public void ALert_PopUP() {
        final Dialog dialog = new Dialog(ForgotActivity.this);
        dialog.setContentView(R.layout.alert_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final Button submit_btn = (Button) dialog.findViewById(R.id.submit_btn);
        final Button done_btn = (Button) dialog.findViewById(R.id.done_btn);
        final Button done_payment_btn = (Button) dialog.findViewById(R.id.done_payment_btn);
        final TextView content_tv = (TextView) dialog.findViewById(R.id.content_tv);

        content_tv.setText("Please check your email");

        done_btn.setVisibility(View.GONE);
        submit_btn.setVisibility(View.GONE);
        done_payment_btn.setVisibility(View.VISIBLE);

        done_payment_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }


}
