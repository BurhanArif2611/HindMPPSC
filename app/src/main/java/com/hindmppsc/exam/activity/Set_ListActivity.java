package com.hindmppsc.exam.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;

import com.hindmppsc.exam.adapter.Material_Type_Adapter;
import com.hindmppsc.exam.adapter.SetList_Adapter;
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

public class Set_ListActivity extends BaseActivity {

    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.set_list_rcv)
    RecyclerView setListRcv;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    private String Material_type_id = "", Exam_type_id = "",Exam_type="";

    @Override
    protected int getContentResId() {
        return R.layout.activity_set__list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Exam_type_id = bundle.getString("exam_type");
            Material_type_id = bundle.getString("id");
            Exam_type = bundle.getString("Exam_type");

            titleTextTv.setText(bundle.getString("material"));
            ErrorMessage.E("Exam_type_Set_ListActivity>>" + bundle.getString("Exam_type"));

            if (bundle.getString("Exam_type").contains("Prelims")) {
                GetSetListOnServer();
            }
            else if (bundle.getString("Exam_type").contains("Mains")) {
                GetMainsSetListOnServer();
            }
            else {
                GetMainsSetListOnServer();
            }
        }


        swiperefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (bundle.getString("Exam_type").toLowerCase().trim().contains("prelims")) {
                    GetSetListOnServer();
                }
                else if (bundle.getString("Exam_type").contains("Mains")) {
                    GetMainsSetListOnServer();
                }
                else {
                    GetMainsSetListOnServer();
                }
            }
        });
    }

    private void GetSetListOnServer() {
        if (NetworkUtil.isNetworkAvailable(Set_ListActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(Set_ListActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("GetSetListOnServer>" + "4" + ">>" + SavedData.getIMEI_Number() + ">>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            Call<ResponseBody> call = apiService.cresh_prelims_and_prelims_set(Exam_type_id, Material_type_id, SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
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
                            ErrorMessage.E("GetSetListOnServer" + object.toString());
                            //ErrorMessage.T(Set_ListActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                Example example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(Set_ListActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    setListRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    SetList_Adapter firstR_v_adapter = new SetList_Adapter(Set_ListActivity.this, example.getResult(), Exam_type);
                                    setListRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                }
                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(Set_ListActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(Set_ListActivity.this, LoginActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(Set_ListActivity.this, object.getString("message"));

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
            ErrorMessage.T(Set_ListActivity.this, "No Internet");
        }
    }
    private void GetMainsSetListOnServer() {
        if (NetworkUtil.isNetworkAvailable(Set_ListActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(Set_ListActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("GetMainsSetListOnServer>" + "4" + ">>" + SavedData.getIMEI_Number() + ">>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            Call<ResponseBody> call = apiService.cresh_mains_and_mains_and_interview_set(Exam_type_id, Material_type_id, SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
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
                            ErrorMessage.E("GetMainsSetListOnServer" + object.toString());
                            //ErrorMessage.T(Set_ListActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                Example example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(Set_ListActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    setListRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    SetList_Adapter firstR_v_adapter = new SetList_Adapter(Set_ListActivity.this, example.getResult(), Exam_type);
                                    setListRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                }
                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(Set_ListActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(Set_ListActivity.this, LoginActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(Set_ListActivity.this, object.getString("message"));

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
            ErrorMessage.T(Set_ListActivity.this, "No Internet");
        }
    }
}
