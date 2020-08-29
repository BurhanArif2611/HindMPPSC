package com.hindmppsc.exam.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.sign_up_layout)
    LinearLayout signUpLayout;
    @BindView(R.id.sign_in_btn)
    Button signInBtn;
    String TODO = "";
    @BindView(R.id.email_id_etv)
    EditText emailIdEtv;
    @BindView(R.id.password_etv)
    EditText passwordEtv;
    @BindView(R.id.forgot_password_tv)
    TextView forgotPasswordTv;
    @BindView(R.id.help_tv)
    TextView helpTv;
    private String IMEI_Number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        IMEI_Number = getIMEI(LoginActivity.this);
    }


    @OnClick({R.id.sign_in_btn, R.id.sign_up_layout, R.id.forgot_password_tv, R.id.help_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_btn:
                if (UserAccount.isEmpty(emailIdEtv, passwordEtv)) {
                    if (UserAccount.isEmailValid(emailIdEtv)) {
                        if (UserAccount.isPasswordLength(passwordEtv)) {
                            if (!IMEI_Number.equals("")) {
                                SavedData.saveIMEI_Number(IMEI_Number);
                                LoginOnServer();
                            } else {
                                IMEI_Number = getIMEI(LoginActivity.this);
                            }
                        } else {
                            UserAccount.EditTextPointer.setError("Password Invalid !");
                            UserAccount.EditTextPointer.requestFocus();
                        }
                    } else {
                        UserAccount.EditTextPointer.setError("Email ID Invalid !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                } else {
                    UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                    UserAccount.EditTextPointer.requestFocus();
                }
                break;
            case R.id.sign_up_layout:
                ErrorMessage.I(LoginActivity.this, SignUpActivity.class, null);
                break;
            case R.id.forgot_password_tv:
                ErrorMessage.I(LoginActivity.this, ForgotActivity.class, null);
                break;
            case R.id.help_tv:
                ErrorMessage.I(LoginActivity.this, SupportActivity.class, null);
                break;
        }
    }

    public String getIMEI(Activity activity) {
        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return TODO;
            }
        }
        if (Build.VERSION.SDK_INT >= 26) {
            String android_id = Settings.Secure.getString(LoginActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
            return android_id;
        } else {
            return telephonyManager.getDeviceId();
        }

    }

    private void LoginOnServer() {
        if (NetworkUtil.isNetworkAvailable(LoginActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(LoginActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);/*SavedData.getFCM_ID()*/
            ErrorMessage.E("LoginOnServer" + SavedData.getFCM_ID());
            Call<ResponseBody> call = apiService.user_Login(emailIdEtv.getText().toString().trim(), passwordEtv.getText().toString().trim(), SavedData.getFCM_ID(), IMEI_Number);
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
                                JSONObject jsonObject1 = object.getJSONObject("result");
                                ErrorMessage.T(LoginActivity.this, object.getString("message"));
                                UserProfileModel userProfileModel = new UserProfileModel();
                                userProfileModel.setDisplayName(jsonObject1.getString("fullname"));
                                userProfileModel.setUser_id(jsonObject1.getString("token"));
                                userProfileModel.setEmaiiId(jsonObject1.getString("email"));
                                userProfileModel.setUserPhone(jsonObject1.getString("contact"));
                                userProfileModel.setProfile_pic(jsonObject1.getString("pro_image"));
                                UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);
                                ErrorMessage.I(LoginActivity.this, DashboardActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(LoginActivity.this, object.getString("message"));
                                materialDialog.dismiss();
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
            ErrorMessage.T(LoginActivity.this, "No Internet");
        }
    }
}
