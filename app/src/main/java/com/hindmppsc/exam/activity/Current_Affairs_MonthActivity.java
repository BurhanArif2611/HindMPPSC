package com.hindmppsc.exam.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.Live_Classes.PaymentActivity;

import com.hindmppsc.exam.activity.Prelims_Video_Course.MockTestPrelimsActivity;
import com.hindmppsc.exam.activity.Prelims_Video_Course.PrelimsPreviousPaperActivity;
import com.hindmppsc.exam.adapter.Current_Affairs_Month_Adapter;
import com.hindmppsc.exam.adapter.PaperList_Adapter;
import com.hindmppsc.exam.database.UserProfileHelper;
import com.hindmppsc.exam.models.MaterialType_models.Example;
import com.hindmppsc.exam.models.MaterialType_models.Result;
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

public class Current_Affairs_MonthActivity extends BaseActivity {

    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.paper_list_rcv)
    RecyclerView paperListRcv;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    @BindView(R.id.no_data_found_tv)
    TextView noDataFoundTv;
    private String Material_type_id = "", Exam_type_id = "";
    private String Material_type="";
    private String Exam_type="";
    private String Suscribe_type="";

    @Override
    protected int getContentResId() {
        return R.layout.activity_current__affairs__month;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Exam_type = bundle.getString("material");
            Material_type_id = bundle.getString("id");
            Exam_type_id = bundle.getString("exam_type");
            titleTextTv.setText(bundle.getString("material"));
            ErrorMessage.E("Current_Affairs_MonthActivity"+Material_type_id+">>"+Exam_type_id);
        }
        swiperefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetCurrent_Affairs_MonthOnServer();
            }
        });
        GetCurrent_Affairs_MonthOnServer();
    }
    private void GetCurrent_Affairs_MonthOnServer() {
        if (NetworkUtil.isNetworkAvailable(Current_Affairs_MonthActivity.this)) {
            Dialog materialDialog = ErrorMessage.initProgressDialog(Current_Affairs_MonthActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.current_affair_list_month(Exam_type_id, Material_type_id, SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Current_Affairs_MonthActivity" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            swiperefresh.setRefreshing(false);
                            materialDialog.dismiss();
                            Gson gson = new Gson();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("GetCurrentAffairListOnServer" + object.toString());
                            //ErrorMessage.T(Current_Affairs_MonthActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("comes in if cond" + object.toString());
                                Example example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    noDataFoundTv.setVisibility(View.GONE);
                                    paperListRcv.setVisibility(View.VISIBLE);
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(Current_Affairs_MonthActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    paperListRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    Current_Affairs_Month_Adapter firstR_v_adapter = new Current_Affairs_Month_Adapter(Current_Affairs_MonthActivity.this, example.getResult());
                                    paperListRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                } else {
                                    noDataFoundTv.setVisibility(View.VISIBLE);
                                    paperListRcv.setVisibility(View.GONE);
                                }


                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(Current_Affairs_MonthActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(Current_Affairs_MonthActivity.this, LoginActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(Current_Affairs_MonthActivity.this, object.getString("message"));

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
            ErrorMessage.T(Current_Affairs_MonthActivity.this, "No Internet");
            noDataFoundTv.setVisibility(View.VISIBLE);
            paperListRcv.setVisibility(View.GONE);
        }
    }
    public void go_subscribe(Result listCoupon) {
        Material_type = String.valueOf(listCoupon.getMaterial_type_id());
        Exam_type = String.valueOf(listCoupon.getMonth());
        Suscribe_type = listCoupon.getSubscribe();
        if (listCoupon.getSubscribe().equals("subscribe")) {
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(listCoupon.getId()));
            bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
            bundle.putString("material", String.valueOf(listCoupon.getMaterial_type_id()));
            bundle.putString("Exam_type", listCoupon.getMonth());
            ErrorMessage.I(Current_Affairs_MonthActivity.this, Current_Affairs_Month_listActivity.class, bundle);
        } else {
            Intent i = new Intent(Current_Affairs_MonthActivity.this, TermsConditionActivity.class);
            i.putExtra("id", String.valueOf(listCoupon.getId()));
            i.putExtra("exam_type", String.valueOf(listCoupon.getExamType()));
            i.putExtra("price", listCoupon.getPrice());
            i.putExtra("fromActivity", "Payment");
            startActivityForResult(i, 1);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                ErrorMessage.E("Current_Affairs_MonthActivity" + data.getStringExtra("price"));
                Intent i = new Intent(Current_Affairs_MonthActivity.this, PaymentActivity.class);
                i.putExtra("id", data.getStringExtra("id"));
                i.putExtra("exam_type", data.getStringExtra("exam_type"));
                i.putExtra("Material_type", Material_type);
                i.putExtra("price", data.getStringExtra("price"));
                i.putExtra("Suscribe_type", Suscribe_type);
             /*   i.putExtra("Material_type_id", Material_type_id);
                i.putExtra("Paper_id", Paper_id);*/
                i.putExtra("fromActivity", "Current_Affairs_Month");
                startActivityForResult(i, 2);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = new Bundle();
                bundle.putString("id", data.getStringExtra("id"));
                bundle.putString("exam_type", data.getStringExtra("exam_type"));
                bundle.putString("material", Material_type);
                bundle.putString("Exam_type", Exam_type);
                ErrorMessage.I(Current_Affairs_MonthActivity.this, Current_Affairs_Month_listActivity.class, bundle);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }


        }
    }
}
