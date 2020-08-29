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

public class SignUpActivity extends AppCompatActivity {

    private static final String TODO = "";
    @BindView(R.id.sign_up_layout)
    LinearLayout signUpLayout;
    @BindView(R.id.signup_btn)
    Button signupBtn;
    @BindView(R.id.user_name_etv)
    EditText userNameEtv;
    @BindView(R.id.email_id_etv)
    EditText emailIdEtv;
    @BindView(R.id.mobile_etv)
    EditText mobileEtv;
    @BindView(R.id.password_etv)
    EditText passwordEtv;
    @BindView(R.id.confirm_password_etv)
    EditText confirmPasswordEtv;
    @BindView(R.id.help_tv)
    TextView helpTv;
    private String IMEI_Number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        IMEI_Number = getIMEI(SignUpActivity.this);
    }


    @OnClick({R.id.signup_btn, R.id.sign_up_layout, R.id.help_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_btn:
                if (UserAccount.isEmpty(userNameEtv, emailIdEtv, mobileEtv, passwordEtv, confirmPasswordEtv)) {
                    if (UserAccount.isEmailValid(emailIdEtv)) {
                        if (UserAccount.isPhoneNumberLength(mobileEtv)) {
                            if (UserAccount.isPasswordLength(passwordEtv)) {
                                if (passwordEtv.getText().toString().equals(confirmPasswordEtv.getText().toString())) {
                                    if (!IMEI_Number.equals("")) {
                                        SavedData.saveIMEI_Number(IMEI_Number);
                                        RegistrationOnServer();
                                    } else {
                                        IMEI_Number = getIMEI(SignUpActivity.this);
                                    }
                                } else {
                                    confirmPasswordEtv.setError("Password Mismatch !");
                                    confirmPasswordEtv.requestFocus();
                                }
                            } else {
                                UserAccount.EditTextPointer.setError("Password is very Easy !");
                                UserAccount.EditTextPointer.requestFocus();
                            }
                        } else {
                            UserAccount.EditTextPointer.setError("Mobile Number Invalid !");
                            UserAccount.EditTextPointer.requestFocus();
                        }
                    } else {
                        UserAccount.EditTextPointer.setError("Email-ID Invalid !");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                } else {
                    UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                    UserAccount.EditTextPointer.requestFocus();
                }
                break;
            case R.id.sign_up_layout:
                ErrorMessage.I(SignUpActivity.this, LoginActivity.class, null);
                break;
            case R.id.help_tv:
                ErrorMessage.I(SignUpActivity.this, SupportActivity.class, null);
                break;
        }
    }

    private void RegistrationOnServer() {
        if (NetworkUtil.isNetworkAvailable(SignUpActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(SignUpActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);/*SavedData.getFCM_ID()*/
            Call<ResponseBody> call = apiService.userRagistration(userNameEtv.getText().toString(), mobileEtv.getText().toString(), emailIdEtv.getText().toString(), passwordEtv.getText().toString(), SavedData.getFCM_ID(), IMEI_Number);
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
                                ErrorMessage.T(SignUpActivity.this, object.getString("message"));
                                UserProfileModel userProfileModel = new UserProfileModel();
                                //userProfileModel.setId(jsonObject1.getString("user_id"));
                                userProfileModel.setDisplayName(jsonObject1.getString("full_name"));
                                //userProfileModel.setUser_id(jsonObject1.getString("last_name"));
                                userProfileModel.setUser_id(jsonObject1.getString("token"));
                                userProfileModel.setEmaiiId(jsonObject1.getString("email"));
                                userProfileModel.setUserPhone(jsonObject1.getString("contact"));
                                userProfileModel.setProfile_pic("");
                                UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);
                                /*  if (Check.equals("")) {*/
                                ErrorMessage.I(SignUpActivity.this, DashboardActivity.class, null);
                               /* } else {
                                    Intent returnIntent = new Intent();
                                    setResult(Activity.RESULT_OK, returnIntent);
                                    finish();
                                }
*/
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(SignUpActivity.this, object.getString("message"));

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
            ErrorMessage.T(SignUpActivity.this, "No Internet");
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
            String android_id = Settings.Secure.getString(SignUpActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
            return android_id;
        } else {
            return telephonyManager.getDeviceId();
        }
    }
}
