package com.hindmppsc.exam.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.Live_Classes.PackegeListActivity;
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
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LiveFragment extends Fragment {
    View view;
    @BindView(R.id.live_class_rcv)
    RecyclerView liveClassRcv;
    @BindView(R.id.no_data_found_tv)
    TextView noDataFoundTv;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    private Unbinder unbinder;
    String Subject_id = "", exam_id = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_live, container, false);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Subject_id = bundle.getString("Subject_id");
            exam_id = bundle.getString("exam_id");
        }
        GetLiveClassOnServer();
        swiperefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetLiveClassOnServer();

            }
        });
        return view;
    }

    private void GetLiveClassOnServer() {
        if (NetworkUtil.isNetworkAvailable(getActivity())) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(getActivity());
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("GetSubjectOnServer>" + "4" + ">>" + SavedData.getIMEI_Number() + ">>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            Call<ResponseBody> call = apiService.live_class(exam_id, Subject_id, SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
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
                            ErrorMessage.E("GetSubjectOnServer" + object.toString());
                          //  ErrorMessage.T(getActivity(), object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("comes in if cond" + object.toString());
                                Example example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    noDataFoundTv.setVisibility(View.GONE);
                                    liveClassRcv.setVisibility(View.VISIBLE);
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity());
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    liveClassRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    LiveClass_Adapter firstR_v_adapter = new LiveClass_Adapter(getActivity(), example.getResult(), "Live");
                                    liveClassRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                } else {
                                    noDataFoundTv.setVisibility(View.VISIBLE);
                                    liveClassRcv.setVisibility(View.GONE);
                                }


                            }else if (object.getString("status").equals("403")){
                                ErrorMessage.T(getActivity(), object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(getActivity(), LoginActivity.class,null);
                            }
                            else {
                                ErrorMessage.E("comes in else");
                                //ErrorMessage.T(getActivity(), object.getString("message"));
                                noDataFoundTv.setVisibility(View.VISIBLE);
                                liveClassRcv.setVisibility(View.GONE);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                            noDataFoundTv.setVisibility(View.VISIBLE);
                            liveClassRcv.setVisibility(View.GONE);
                            swiperefresh.setRefreshing(false);
                        }
                    } else {
                        materialDialog.dismiss();
                        noDataFoundTv.setVisibility(View.VISIBLE);
                        liveClassRcv.setVisibility(View.GONE);
                        swiperefresh.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();
                    noDataFoundTv.setVisibility(View.VISIBLE);
                    liveClassRcv.setVisibility(View.GONE);
                    swiperefresh.setRefreshing(false);
                }
            });
        } else {
            ErrorMessage.T(getActivity(), "No Internet");
            swiperefresh.setRefreshing(false);
        }

    }

}
