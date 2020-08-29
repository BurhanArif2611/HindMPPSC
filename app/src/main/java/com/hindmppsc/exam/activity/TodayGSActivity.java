package com.hindmppsc.exam.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.adapter.TodayGs_Adapter;
import com.hindmppsc.exam.adapter.Upload_Your_Self_Adapter;
import com.hindmppsc.exam.database.UserProfileHelper;
import com.hindmppsc.exam.models.Live_class_subjects.Example;
import com.hindmppsc.exam.utility.AppConfig;
import com.hindmppsc.exam.utility.ErrorMessage;
import com.hindmppsc.exam.utility.LoadInterface;
import com.hindmppsc.exam.utility.NetworkUtil;
import com.hindmppsc.exam.utility.SavedData;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodayGSActivity extends BaseActivity {

    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.today_gs_rcv)
    RecyclerView todayGsRcv;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    @Override
    protected int getContentResId() {
        return R.layout.activity_today_gs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        GetTodayGSOnServer();
        swiperefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetTodayGSOnServer();

            }
        });
    }
    private void GetTodayGSOnServer() {
        if (NetworkUtil.isNetworkAvailable(TodayGSActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(TodayGSActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("GetSubjectOnServer>" + "4" + ">>" + SavedData.getIMEI_Number() + ">>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            Call<ResponseBody> call = apiService.today_gs(SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            swiperefresh.setRefreshing(false);
                            materialDialog.dismiss();
                            Gson gson = new Gson();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("GetUploadYourSelfOnServer" + object.toString());
                            //ErrorMessage.T(TodayGSActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("comes in if cond" + object.toString());
                                Example example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(TodayGSActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    todayGsRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    TodayGs_Adapter firstR_v_adapter = new TodayGs_Adapter(TodayGSActivity.this, example.getResult());
                                    todayGsRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                }


                            }else if (object.getString("status").equals("403")){
                                ErrorMessage.T(TodayGSActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(TodayGSActivity.this, LoginActivity.class,null);
                            }
                            else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(TodayGSActivity.this, object.getString("message"));

                            }
                        }  catch (Exception e) {
                            swiperefresh.setRefreshing(false);
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                        }
                    } else {
                        swiperefresh.setRefreshing(false);
                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();
                    swiperefresh.setRefreshing(false);
                }
            });
        } else {
            swiperefresh.setRefreshing(false);
            ErrorMessage.T(TodayGSActivity.this, "No Internet");
        }
    }
}
