package com.hindmppsc.exam.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.adapter.NewPurchase_Adapter;
import com.hindmppsc.exam.adapter.NewPurchase_CurrentAffaire_Adapter;
import com.hindmppsc.exam.adapter.NewPurchase_OtherCourse_Adapter;
import com.hindmppsc.exam.adapter.NewPurchase_OtherPaper_Adapter;
import com.hindmppsc.exam.database.UserProfileHelper;
import com.hindmppsc.exam.models.NewPurchase_Models.Example;
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

public class MyPurchaseActivity extends BaseActivity {

    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.previous_class_rcv)
    RecyclerView previousClassRcv;
    @BindView(R.id.no_data_found_tv)
    TextView noDataFoundTv;
    @BindView(R.id.other_paper_rcv)
    RecyclerView otherPaperRcv;
    @BindView(R.id.other_course_rcv)
    RecyclerView otherCourseRcv;
    @BindView(R.id.current_affaire_rcv)
    RecyclerView currentAffaireRcv;

    @Override
    protected int getContentResId() {
        return R.layout.activity_my_purchase;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        titleTextTv.setText("My Purchase");
        MyPurchaseOnServer();

    }

    private void MyPurchaseOnServer() {
        if (NetworkUtil.isNetworkAvailable(MyPurchaseActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(MyPurchaseActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("GetLiveClassOnServer>" + SavedData.getIMEI_Number() + ">>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            Call<ResponseBody> call = apiService.new_order_history(SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            Gson gson = new Gson();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("GetSubjectOnServer" + object.toString());
                            ErrorMessage.T(MyPurchaseActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("comes in if cond" + object.toString());
                                Example example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    noDataFoundTv.setVisibility(View.GONE);
                                    previousClassRcv.setVisibility(View.VISIBLE);
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(MyPurchaseActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    previousClassRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    NewPurchase_Adapter firstR_v_adapter = new NewPurchase_Adapter(MyPurchaseActivity.this, example.getResult());
                                    previousClassRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                    previousClassRcv.setNestedScrollingEnabled(false);
                                }
                                if (example.getOrderPaper().size() > 0) {
                                    noDataFoundTv.setVisibility(View.GONE);
                                    otherPaperRcv.setVisibility(View.VISIBLE);
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(MyPurchaseActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    otherPaperRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    NewPurchase_OtherPaper_Adapter firstR_v_adapter = new NewPurchase_OtherPaper_Adapter(MyPurchaseActivity.this, example.getOrderPaper());
                                    otherPaperRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                    otherPaperRcv.setNestedScrollingEnabled(false);
                                }
                                if (example.getOtherCourse().size() > 0) {
                                    noDataFoundTv.setVisibility(View.GONE);
                                    otherCourseRcv.setVisibility(View.VISIBLE);
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(MyPurchaseActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    otherCourseRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    NewPurchase_OtherCourse_Adapter firstR_v_adapter = new NewPurchase_OtherCourse_Adapter(MyPurchaseActivity.this, example.getOtherCourse());
                                    otherCourseRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                    otherCourseRcv.setNestedScrollingEnabled(false);
                                }
                                if (example.getCurrentAffair().size() > 0) {
                                    noDataFoundTv.setVisibility(View.GONE);
                                    currentAffaireRcv.setVisibility(View.VISIBLE);
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(MyPurchaseActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    currentAffaireRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    NewPurchase_CurrentAffaire_Adapter firstR_v_adapter = new NewPurchase_CurrentAffaire_Adapter(MyPurchaseActivity.this, example.getCurrentAffair());
                                    currentAffaireRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                    currentAffaireRcv.setNestedScrollingEnabled(false);
                                } else {
                                    noDataFoundTv.setVisibility(View.VISIBLE);
                                    previousClassRcv.setVisibility(View.GONE);
                                }


                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(MyPurchaseActivity.this, object.getString("message"));
                                noDataFoundTv.setVisibility(View.VISIBLE);
                                previousClassRcv.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                            noDataFoundTv.setVisibility(View.VISIBLE);
                            previousClassRcv.setVisibility(View.GONE);
                        }
                    } else {
                        materialDialog.dismiss();
                        noDataFoundTv.setVisibility(View.VISIBLE);
                        previousClassRcv.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();
                    noDataFoundTv.setVisibility(View.VISIBLE);
                    previousClassRcv.setVisibility(View.GONE);
                }
            });
        } else {
            ErrorMessage.T(MyPurchaseActivity.this, "No Internet");
        }
    }
}
