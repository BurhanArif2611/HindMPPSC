package com.hindmppsc.exam.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.adapter.SetList_Adapter;
import com.hindmppsc.exam.adapter.SetPaperList_Adapter;
import com.hindmppsc.exam.database.UserProfileHelper;
import com.hindmppsc.exam.models.SetList_Models.Example;
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

public class SetPaperListActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.set_paper_list_rcv)
    RecyclerView setPaperListRcv;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    private String Material_type_id = "", Exam_type_id = "",SetPaper="",Exam_type="";
    @Override
    protected int getContentResId() {
        return R.layout.activity_set_paper_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Exam_type_id = bundle.getString("exam_type");
            SetPaper = bundle.getString("id");
            Material_type_id = bundle.getString("material");
            titleTextTv.setText(bundle.getString("Exam_type"));

            ErrorMessage.E("Exam_type_SetPaperListActivity>>" + bundle.getString("Exam_type"));

            Exam_type=bundle.getString("Exam_type");
            if (bundle.getString("Exam_type").contains("Prelims")) {
                GetSetPaperListOnServer();
            } else if (bundle.getString("Exam_type").contains("Mains")) {
                GetMainsSetPaperListOnServer();
            } else {
                GetMainsSetPaperListOnServer();
            }
        }


        swiperefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (bundle.getString("Exam_type").toLowerCase().trim().contains("prelims")) {
                    GetSetPaperListOnServer();
                } else if (bundle.getString("Exam_type").contains("Mains")) {
                    GetMainsSetPaperListOnServer();
                } else {
                    GetMainsSetPaperListOnServer();
                }
            }
        });
    }

    private void GetSetPaperListOnServer() {
        if (NetworkUtil.isNetworkAvailable(SetPaperListActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(SetPaperListActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("GetSetPaperListOnServer>" + "4" + ">>" + SavedData.getIMEI_Number() + ">>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            Call<ResponseBody> call = apiService.cresh_prelims_and_prelims_set_paper(SetPaper,Exam_type_id, Material_type_id, SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
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
                            ErrorMessage.E("GetSetPaperListOnServer" + object.toString());
                            //ErrorMessage.T(SetPaperListActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                Example example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(SetPaperListActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    setPaperListRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    SetPaperList_Adapter firstR_v_adapter = new SetPaperList_Adapter(SetPaperListActivity.this, example.getResult(), Exam_type);
                                    setPaperListRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                }
                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(SetPaperListActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(SetPaperListActivity.this, LoginActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(SetPaperListActivity.this, object.getString("message"));

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
            ErrorMessage.T(SetPaperListActivity.this, "No Internet");
        }
    }
    private void GetMainsSetPaperListOnServer() {
        if (NetworkUtil.isNetworkAvailable(SetPaperListActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(SetPaperListActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("GetMainsSetPaperListOnServer>" + "4" + ">>" + SavedData.getIMEI_Number() + ">>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            Call<ResponseBody> call = apiService.cresh_mains_and_mains_and_interview_set_paper(SetPaper,Exam_type_id, Material_type_id, SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
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
                            ErrorMessage.E("GetMainsSetPaperListOnServer" + object.toString());
                            //ErrorMessage.T(SetPaperListActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                Example example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(SetPaperListActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    setPaperListRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    SetPaperList_Adapter firstR_v_adapter = new SetPaperList_Adapter(SetPaperListActivity.this, example.getResult(), Exam_type);
                                    setPaperListRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                }
                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(SetPaperListActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(SetPaperListActivity.this, LoginActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(SetPaperListActivity.this, object.getString("message"));

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
            ErrorMessage.T(SetPaperListActivity.this, "No Internet");
        }
    }
}
