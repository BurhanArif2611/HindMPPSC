package com.hindmppsc.exam.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.adapter.Faq_Adapter;
import com.hindmppsc.exam.models.Faq_Model.Example;
import com.hindmppsc.exam.utility.AppConfig;
import com.hindmppsc.exam.utility.ErrorMessage;
import com.hindmppsc.exam.utility.NetworkUtil;


import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqActivity extends BaseActivity {

    @BindView(R.id.faq_rcv)
    RecyclerView faqRcv;
    @BindView(R.id.title_text_tv)
    TextView titleTextTv;

    @Override
    protected int getContentResId() {
        return R.layout.activity_faq;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        titleTextTv.setText("FAQ");
        GetFaq_Data();
    }

    private void GetFaq_Data() {
        if (NetworkUtil.isNetworkAvailable(FaqActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(FaqActivity.this);
            Call<ResponseBody> call = AppConfig.getLoadInterface().getfaq();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = null;
                        Gson gson = new Gson();
                        try {
                            jsonObject = new JSONObject(response.body().string());
                            ErrorMessage.E("GetPaperList_Data" + jsonObject.toString());
                            materialDialog.dismiss();
                            if (jsonObject.getString("status").equals("200")) {
                                String responseData = jsonObject.toString();
                                Example example = gson.fromJson(responseData, Example.class);
                                if (example.getResult().size() > 0) {
                                    Faq_Adapter side_rv_adapter = new Faq_Adapter(FaqActivity.this, example.getResult());
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(FaqActivity.this);
                                    faqRcv.setLayoutManager(layoutManager);
                                    faqRcv.setItemAnimator(new DefaultItemAnimator());
                                    faqRcv.setAdapter(side_rv_adapter);
                                    side_rv_adapter.notifyDataSetChanged();
                                }
                            }
                        } catch (JSONException e) {
                            //noDataFoundTv.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                            ErrorMessage.T(FaqActivity.this, "Server Error");
                            materialDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            //noDataFoundTv.setVisibility(View.VISIBLE);
                        }


                    } else {
                        // noDataFoundTv.setVisibility(View.VISIBLE);
                        ErrorMessage.T(FaqActivity.this, "Response not successful");
                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ErrorMessage.T(FaqActivity.this, "Response Fail");
                    materialDialog.dismiss();

                }
            });

        } else {
            ErrorMessage.T(FaqActivity.this, "Internet Not Found!");
        }
    }
}
