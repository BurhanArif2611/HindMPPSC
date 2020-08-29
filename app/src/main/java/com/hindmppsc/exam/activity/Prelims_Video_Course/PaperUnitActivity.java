package com.hindmppsc.exam.activity.Prelims_Video_Course;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.LoginActivity;
import com.hindmppsc.exam.adapter.PaperList_Adapter;
import com.hindmppsc.exam.adapter.PaperUnitList_Adapter;
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

public class PaperUnitActivity extends BaseActivity {

    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.paper_unit_rcv)
    RecyclerView paperUnitRcv;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    String exam_type_id="",material_type_id="",paper_id="";
    @Override
    protected int getContentResId() {
        return R.layout.activity_paper_unit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        Bundle bundle=getIntent().getExtras();
        if (bundle !=null){
            exam_type_id=  bundle.getString("exam_type");
            material_type_id= bundle.getString("material_type_id");
            paper_id=bundle.getString("paper_id");

        }
        GetPaperUnitListOnServer();

        swiperefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetPaperUnitListOnServer();

            }
        });
    }
    private void GetPaperUnitListOnServer() {
        if (NetworkUtil.isNetworkAvailable(PaperUnitActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(PaperUnitActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.exam_paper_unit(exam_type_id,material_type_id,paper_id, SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
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
                            ErrorMessage.E("GetPaperUnitListOnServer" + object.toString());
                            //ErrorMessage.T(PaperUnitActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("comes in if cond" + object.toString());
                                Example example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(PaperUnitActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    paperUnitRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    PaperUnitList_Adapter firstR_v_adapter = new PaperUnitList_Adapter(PaperUnitActivity.this, example.getResult());
                                    paperUnitRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                }


                            }else if (object.getString("status").equals("403")){
                                ErrorMessage.T(PaperUnitActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(PaperUnitActivity.this, LoginActivity.class,null);
                            }
                            else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(PaperUnitActivity.this, object.getString("message"));

                            }
                        }  catch (Exception e) {
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
            ErrorMessage.T(PaperUnitActivity.this, "No Internet");
        }
    }
}
