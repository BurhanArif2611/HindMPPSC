package com.hindmppsc.exam.activity;

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
import com.hindmppsc.exam.adapter.Complete_current_affair_Adapter;
import com.hindmppsc.exam.adapter.PaperList_Adapter;
import com.hindmppsc.exam.database.UserProfileHelper;
import com.hindmppsc.exam.models.MaterialType_models.Example;
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

public class Complete_Current_AffairListActivity extends BaseActivity {

    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.paper_list_rcv)
    RecyclerView paperListRcv;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    @BindView(R.id.no_data_found_tv)
    TextView noDataFoundTv;
    private String Material_type_id = "", Exam_type_id = "";
    @Override
    protected int getContentResId() {
        return R.layout.activity_complete__current__affair_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Material_type_id = bundle.getString("id");
            Exam_type_id = bundle.getString("exam_type");
            titleTextTv.setText(bundle.getString("material"));
        }
        GetCurrentAffairListOnServer();
    }

    private void GetCurrentAffairListOnServer() {
        if (NetworkUtil.isNetworkAvailable(Complete_Current_AffairListActivity.this)) {
            Dialog materialDialog = ErrorMessage.initProgressDialog(Complete_Current_AffairListActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.complete_current_affair_list(Exam_type_id, Material_type_id, SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("GetCurrentAffairListOnServer" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            swiperefresh.setRefreshing(false);
                            materialDialog.dismiss();
                            Gson gson = new Gson();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("GetCurrentAffairListOnServer" + object.toString());
                            //ErrorMessage.T(Complete_Current_AffairListActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("comes in if cond" + object.toString());
                                Example example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(Complete_Current_AffairListActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    paperListRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    Complete_current_affair_Adapter firstR_v_adapter = new Complete_current_affair_Adapter(Complete_Current_AffairListActivity.this, example.getResult());
                                    paperListRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                } else {
                                    noDataFoundTv.setVisibility(View.VISIBLE);
                                    paperListRcv.setVisibility(View.GONE);
                                }


                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(Complete_Current_AffairListActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(Complete_Current_AffairListActivity.this, LoginActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(Complete_Current_AffairListActivity.this, object.getString("message"));

                            }
                        } catch (Exception e) {
                            swiperefresh.setRefreshing(false);
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                            noDataFoundTv.setVisibility(View.VISIBLE);
                            paperListRcv.setVisibility(View.GONE);
                        }
                    } else {
                        swiperefresh.setRefreshing(false);
                        materialDialog.dismiss();
                        noDataFoundTv.setVisibility(View.VISIBLE);
                        paperListRcv.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();
                    swiperefresh.setRefreshing(false);
                    noDataFoundTv.setVisibility(View.VISIBLE);
                    paperListRcv.setVisibility(View.GONE);
                }
            });
        } else {
            swiperefresh.setRefreshing(false);
            ErrorMessage.T(Complete_Current_AffairListActivity.this, "No Internet");
            noDataFoundTv.setVisibility(View.VISIBLE);
            paperListRcv.setVisibility(View.GONE);
        }
    }
}
