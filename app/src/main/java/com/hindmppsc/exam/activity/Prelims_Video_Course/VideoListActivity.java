package com.hindmppsc.exam.activity.Prelims_Video_Course;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.LoginActivity;
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

public class VideoListActivity extends BaseActivity {

    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.video_list_rcv)
    RecyclerView videoListRcv;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    @BindView(R.id.no_data_found_tv)
    TextView noDataFoundTv;
    String Unit_id="",paper_id="",exam_type="",material_type_id="";
    @Override
    protected int getContentResId() {
        return R.layout.activity_video_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Unit_id=  bundle.getString("Unit_id");
            paper_id=bundle.getString("paper_id");
            exam_type= bundle.getString("exam_type");
            material_type_id=  bundle.getString("material_type_id");
            titleTextTv.setText(bundle.getString("material"));
            if (bundle.getString("material").toLowerCase().equals("video course")){
                GetLiveClassOnServer();
            }else {
                GetLiveClassOnServer_Pdf();
            }
        }

        swiperefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (titleTextTv.getText().toString().toLowerCase().equals("video course")){
                    GetLiveClassOnServer();
                }else {
                    GetLiveClassOnServer_Pdf();
                }

            }
        });
    }

    private void GetLiveClassOnServer() {
        if (NetworkUtil.isNetworkAvailable(VideoListActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(VideoListActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("GetLiveClassOnServer"+exam_type+">>"+ material_type_id+">>"+paper_id+">>"+Unit_id+">>"+ SavedData.getIMEI_Number()+">>"+ UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            Call<ResponseBody> call = apiService.prelims_exam_video(exam_type, material_type_id,paper_id,Unit_id, SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            swiperefresh.setRefreshing(false);
                            Gson gson = new Gson();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("GetLiveClassOnServer" + object.toString());
                            // ErrorMessage.T(VideoListActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("comes in if cond" + object.toString());
                                Example example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(VideoListActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    videoListRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    LiveClass_Adapter firstR_v_adapter = new LiveClass_Adapter(VideoListActivity.this, example.getResult(), "Previous");
                                    videoListRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                } else {
                                    noDataFoundTv.setVisibility(View.VISIBLE);
                                    videoListRcv.setVisibility(View.GONE);
                                }


                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(VideoListActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(VideoListActivity.this, LoginActivity.class, null);
                            } else {
                                noDataFoundTv.setVisibility(View.VISIBLE);
                                videoListRcv.setVisibility(View.GONE);
                                ErrorMessage.E("comes in else");
                                // ErrorMessage.T(VideoListActivity.this, object.getString("message"));

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                            noDataFoundTv.setVisibility(View.VISIBLE);
                            videoListRcv.setVisibility(View.GONE);
                            swiperefresh.setRefreshing(false);
                        }
                    } else {
                        materialDialog.dismiss();
                        noDataFoundTv.setVisibility(View.VISIBLE);
                        videoListRcv.setVisibility(View.GONE);
                        swiperefresh.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();
                    noDataFoundTv.setVisibility(View.VISIBLE);
                    videoListRcv.setVisibility(View.GONE);
                    swiperefresh.setRefreshing(false);
                }
            });
        } else {
            swiperefresh.setRefreshing(false);
            ErrorMessage.T(VideoListActivity.this, "No Internet");
        }
    }
    private void GetLiveClassOnServer_Pdf() {
        if (NetworkUtil.isNetworkAvailable(VideoListActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(VideoListActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.prelims_exam_ebook(exam_type, material_type_id,paper_id,Unit_id, SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            swiperefresh.setRefreshing(false);
                            Gson gson = new Gson();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("GetLiveClassOnServer_Pdf" + object.toString());
                            // ErrorMessage.T(VideoListActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("comes in if cond" + object.toString());
                                Example example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(VideoListActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    videoListRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    LiveClass_Adapter firstR_v_adapter = new LiveClass_Adapter(VideoListActivity.this, example.getResult(), "Previous");
                                    videoListRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                } else {
                                    noDataFoundTv.setVisibility(View.VISIBLE);
                                    videoListRcv.setVisibility(View.GONE);
                                }


                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(VideoListActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(VideoListActivity.this, LoginActivity.class, null);
                            } else {
                                noDataFoundTv.setVisibility(View.VISIBLE);
                                videoListRcv.setVisibility(View.GONE);
                                ErrorMessage.E("comes in else");
                                // ErrorMessage.T(VideoListActivity.this, object.getString("message"));

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                            noDataFoundTv.setVisibility(View.VISIBLE);
                            videoListRcv.setVisibility(View.GONE);
                            swiperefresh.setRefreshing(false);
                        }
                    } else {
                        materialDialog.dismiss();
                        noDataFoundTv.setVisibility(View.VISIBLE);
                        videoListRcv.setVisibility(View.GONE);
                        swiperefresh.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();
                    noDataFoundTv.setVisibility(View.VISIBLE);
                    videoListRcv.setVisibility(View.GONE);
                    swiperefresh.setRefreshing(false);
                }
            });
        } else {
            swiperefresh.setRefreshing(false);
            ErrorMessage.T(VideoListActivity.this, "No Internet");
        }
    }
}
