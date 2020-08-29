package com.hindmppsc.exam.activity.Prelims_Video_Course;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.Live_Classes.LiveClassActivity;
import com.hindmppsc.exam.activity.Live_Classes.PaymentActivity;
import com.hindmppsc.exam.activity.LoginActivity;
import com.hindmppsc.exam.activity.TermsConditionActivity;
import com.hindmppsc.exam.adapter.Live_class_packege_adapter;
import com.hindmppsc.exam.adapter.Prelims_Video_Course_Packege_Adapter;
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

public class Prelims_Video_Course_PackegeActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.live_course_layout)
    LinearLayout liveCourseLayout;
    @BindView(R.id.packege_list_rcv)
    RecyclerView packegeListRcv;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    private String paper_id="",exam_id="",material_type_id="",material="";
    private String Material_type_id="",Paper_id="";

    @Override
    protected int getContentResId() {
        return R.layout.activity_prelims__video__course__packege;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        titleTextTv.setText("Join");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            paper_id=bundle.getString("paper_id");
            exam_id= bundle.getString("exam_type");
            material_type_id=bundle.getString("material_type_id");
            material= bundle.getString("material");
           
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
        if (NetworkUtil.isNetworkAvailable(Prelims_Video_Course_PackegeActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(Prelims_Video_Course_PackegeActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("GetSubjectOnServer>" + "4" + ">>" + SavedData.getIMEI_Number() + ">>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            Call<ResponseBody> call = apiService.exam_paper_package(exam_id,material_type_id,paper_id, SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
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
                            // ErrorMessage.T(Prelims_Video_Course_PackegeActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                Example example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(Prelims_Video_Course_PackegeActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    packegeListRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    Prelims_Video_Course_Packege_Adapter firstR_v_adapter = new Prelims_Video_Course_Packege_Adapter(Prelims_Video_Course_PackegeActivity.this, example.getResult());
                                    packegeListRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                }


                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(Prelims_Video_Course_PackegeActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(Prelims_Video_Course_PackegeActivity.this, LoginActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(Prelims_Video_Course_PackegeActivity.this, object.getString("message"));

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
            ErrorMessage.T(Prelims_Video_Course_PackegeActivity.this, "No Internet");
        }
    }

    public void GO_TO_NextPage(String packege_id, String exam_type, String Price, String material_type_id, String paper_id) {
       // ExamType = Exam_type;
        Material_type_id=material_type_id;
        Paper_id=paper_id;
        Intent i = new Intent(Prelims_Video_Course_PackegeActivity.this, TermsConditionActivity.class);
        i.putExtra("id", packege_id);
        i.putExtra("exam_type", exam_type);
        i.putExtra("price", Price);
        i.putExtra("fromActivity", "Payment");
        startActivityForResult(i, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Intent i = new Intent(Prelims_Video_Course_PackegeActivity.this, PaymentActivity.class);
                i.putExtra("id", data.getStringExtra("id"));
                i.putExtra("exam_type", data.getStringExtra("exam_type"));
                i.putExtra("price", data.getStringExtra("price"));
                i.putExtra("Material_type_id", Material_type_id);
                i.putExtra("Paper_id", Paper_id);
                i.putExtra("fromActivity", "Prelims_Video_Course_PackegeActivity");
                startActivityForResult(i, 2);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = new Bundle();
                //bundle.putString("id", subject_id);
                //bundle.putString("exam_type", ExamType);
                ErrorMessage.I(Prelims_Video_Course_PackegeActivity.this, LiveClassActivity.class, bundle);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
