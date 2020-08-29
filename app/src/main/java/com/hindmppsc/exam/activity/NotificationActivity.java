package com.hindmppsc.exam.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.adapter.NewPurchase_Adapter;
import com.hindmppsc.exam.adapter.Notification_Adapter;
import com.hindmppsc.exam.database.UserProfileHelper;

import com.hindmppsc.exam.models.Notifications.Example;
import com.hindmppsc.exam.utility.AppConfig;
import com.hindmppsc.exam.utility.ErrorMessage;
import com.hindmppsc.exam.utility.LoadInterface;
import com.hindmppsc.exam.utility.NetworkUtil;
import com.hindmppsc.exam.utility.SavedData;

import org.json.JSONObject;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends BaseActivity {

    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.notification_rcv)
    RecyclerView notificationRcv;
    @BindView(R.id.no_data_found_tv)
    TextView noDataFoundTv;

    @Override
    protected int getContentResId() {
        return R.layout.activity_notification;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        titleTextTv.setText("Notification");
        NotificationsOnServer();
    }
    private void NotificationsOnServer() {
        if (NetworkUtil.isNetworkAvailable(NotificationActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(NotificationActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.user_noti(SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            Gson gson = new Gson();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("NotificationsOnServer" + object.toString());
                            ErrorMessage.T(NotificationActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                SavedData.saveAddToCart_Count("0");
                                ErrorMessage.E("comes in if cond" + object.toString());
                                Example example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    Collections.reverse(example.getResult());
                                    noDataFoundTv.setVisibility(View.GONE);
                                    notificationRcv.setVisibility(View.VISIBLE);
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(NotificationActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    notificationRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    Notification_Adapter firstR_v_adapter = new Notification_Adapter(NotificationActivity.this, example.getResult());
                                    notificationRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                }else {
                                    noDataFoundTv.setVisibility(View.VISIBLE);
                                    notificationRcv.setVisibility(View.GONE);
                                }


                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(NotificationActivity.this, object.getString("message"));
                                noDataFoundTv.setVisibility(View.VISIBLE);
                                notificationRcv.setVisibility(View.GONE);
                            }
                        }  catch (Exception e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                            noDataFoundTv.setVisibility(View.VISIBLE);
                            notificationRcv.setVisibility(View.GONE);
                        }
                    } else {
                        materialDialog.dismiss();
                        noDataFoundTv.setVisibility(View.VISIBLE);
                        notificationRcv.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();
                    noDataFoundTv.setVisibility(View.VISIBLE);
                    notificationRcv.setVisibility(View.GONE);
                }
            });
        } else {
            ErrorMessage.T(NotificationActivity.this, "No Internet");
        }
    }
}
