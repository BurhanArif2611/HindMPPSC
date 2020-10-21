package com.hindmppsc.exam.activity.Live_Classes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.LoginActivity;
import com.hindmppsc.exam.activity.TermsConditionActivity;
import com.hindmppsc.exam.adapter.Live_class_packege_adapter;
import com.hindmppsc.exam.database.UserProfileHelper;
import com.hindmppsc.exam.models.Packege_models.Example;
import com.hindmppsc.exam.utility.AppConfig;
import com.hindmppsc.exam.utility.ErrorMessage;
import com.hindmppsc.exam.utility.LoadInterface;
import com.hindmppsc.exam.utility.NetworkUtil;
import com.hindmppsc.exam.utility.SavedData;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackegeListActivity extends BaseActivity {

    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.packege_list_rcv)
    RecyclerView packegeListRcv;
    String subject_id = "", exam_id = "";
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    private String ExamType="";
    private String Suscribe_type="";

    @Override
    protected int getContentResId() {
        return R.layout.activity_packege_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        titleTextTv.setText("Join");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            subject_id = bundle.getString("id");
            exam_id = bundle.getString("exam_type");
            bundle.getString("subject");
            GetPackegeOnServer();
        }
        swiperefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetPackegeOnServer();

            }
        });

    }

    private void GetPackegeOnServer() {
        if (NetworkUtil.isNetworkAvailable(PackegeListActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(PackegeListActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("GetSubjectOnServer>" + "4" + ">>" + SavedData.getIMEI_Number() + ">>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            Call<ResponseBody> call = apiService.live_class_package(subject_id, exam_id, SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
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
                            ErrorMessage.E("GetPackegeOnServer" + object.toString());
                            // ErrorMessage.T(PackegeListActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                Example example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(PackegeListActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    packegeListRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    Live_class_packege_adapter firstR_v_adapter = new Live_class_packege_adapter(PackegeListActivity.this, example.getResult(),subject_id);
                                    packegeListRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                }


                            }else if (object.getString("status").equals("403")){
                                ErrorMessage.T(PackegeListActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(PackegeListActivity.this, LoginActivity.class,null);
                            }
                            else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(PackegeListActivity.this, object.getString("message"));

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            swiperefresh.setRefreshing(false);
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
            ErrorMessage.T(PackegeListActivity.this, "No Internet");
        }
    }
    public void GO_TO_NextPage(String id,String Exam_type,String Price,String Suscribe){
        ExamType=Exam_type;
        Suscribe_type=Suscribe;
        Intent i = new Intent(PackegeListActivity.this, TermsConditionActivity.class);
        i.putExtra("id", id);
        i.putExtra("exam_type", Exam_type);
        i.putExtra("price", Price);
        i.putExtra("fromActivity", "Payment");
        startActivityForResult(i, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Intent i = new Intent(PackegeListActivity.this, PaymentActivity.class);
                i.putExtra("id", data.getStringExtra("id"));
                i.putExtra("exam_type", data.getStringExtra("exam_type"));
                i.putExtra("price", data.getStringExtra("price"));
                i.putExtra("fromActivity", "Payment");
                i.putExtra("Suscribe_type", Suscribe_type);
                startActivityForResult(i, 2);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = new Bundle();
                bundle.putString("id", subject_id);
                bundle.putString("exam_type", ExamType);
                ErrorMessage.I(PackegeListActivity.this, LiveClassActivity.class, bundle);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
