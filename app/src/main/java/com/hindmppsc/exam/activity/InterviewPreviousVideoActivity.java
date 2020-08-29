package com.hindmppsc.exam.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;

import com.hindmppsc.exam.adapter.LiveClass_Adapter;
import com.hindmppsc.exam.database.UserProfileHelper;
import com.hindmppsc.exam.models.LiveClass_models.Example;
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

public class InterviewPreviousVideoActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.live_class_rcv)
    RecyclerView liveClassRcv;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    @BindView(R.id.no_data_found_tv)
    TextView noDataFoundTv;
    String exam_type_id = "",id="";

    @Override
    protected int getContentResId() {
        return R.layout.activity_interview_previous_video;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");
            exam_type_id = bundle.getString("exam_type");
            titleTextTv.setText(bundle.getString("material"));
            GetInterviewPreviousVideoOnServer();
        }
    }

    private void GetInterviewPreviousVideoOnServer() {
        if (NetworkUtil.isNetworkAvailable(InterviewPreviousVideoActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(InterviewPreviousVideoActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("GetSubjectOnServer>" + id + ">>" +exam_type_id + ">>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            Call<ResponseBody> call = apiService.interview_exam_video(exam_type_id, id, SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
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
                            ErrorMessage.E("GetInterviewPreviousVideoOnServer" + object.toString());
                            //ErrorMessage.T(InterviewPreviousVideoActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("comes in if cond" + object.toString());
                                Example example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(InterviewPreviousVideoActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    liveClassRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    LiveClass_Adapter firstR_v_adapter = new LiveClass_Adapter(InterviewPreviousVideoActivity.this, example.getResult(), "Previous");
                                    liveClassRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                } else {
                                    noDataFoundTv.setVisibility(View.VISIBLE);
                                    liveClassRcv.setVisibility(View.GONE);
                                }
                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(InterviewPreviousVideoActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(InterviewPreviousVideoActivity.this, LoginActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(InterviewPreviousVideoActivity.this, object.getString("message"));

                            }
                        } catch (Exception e) {
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
            ErrorMessage.T(InterviewPreviousVideoActivity.this, "No Internet");
        }
    }
}
