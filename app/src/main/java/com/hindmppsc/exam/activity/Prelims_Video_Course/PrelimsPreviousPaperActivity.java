package com.hindmppsc.exam.activity.Prelims_Video_Course;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;

import com.hindmppsc.exam.activity.LoginActivity;
import com.hindmppsc.exam.adapter.LiveClass_Adapter;
import com.hindmppsc.exam.adapter.PaperList_Adapter;
import com.hindmppsc.exam.adapter.Previous_paper_Adapter;
import com.hindmppsc.exam.database.UserProfileHelper;
import com.hindmppsc.exam.models.MaterialType_models.Example;
import com.hindmppsc.exam.models.MaterialType_models.Result;
import com.hindmppsc.exam.utility.AppConfig;
import com.hindmppsc.exam.utility.ErrorMessage;
import com.hindmppsc.exam.utility.LoadInterface;
import com.hindmppsc.exam.utility.NetworkUtil;
import com.hindmppsc.exam.utility.SavedData;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrelimsPreviousPaperActivity extends BaseActivity {
    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.paper_list_rcv)
    RecyclerView paperListRcv;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    private String Material_type_id = "", Exam_type_id = "", Exam_type = "";

    @Override
    protected int getContentResId() {
        return R.layout.activity_prelims_previous_paper;
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
            Exam_type = bundle.getString("Video Course");
            titleTextTv.setText(bundle.getString("material"));
        }
       /* if (Exam_type.equals("Interview")){
            GetInterviewPaperListOnServer();
        }else*/
       if (Exam_type !=null) {
           if (Exam_type.equals("Interview")) {
               if (bundle.getString("material").equals("Ebook")) {
                   GetInterviewPaperListOnServer();
               } else if (bundle.getString("material").equals("Video Course")) {
                   GetInterviewPreviousVideoOnServer();
               }else if (bundle.getString("material").equals("Preivious Paper")) {
                   GetPaperListOnServer();
               }
           } else if (Exam_type.equals("Syllabus")) {
               GetSyllabusListOnServer();
           } else {
               GetPaperListOnServer();
           }
       }
        swiperefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Exam_type.equals("Interview")) {
                    if (titleTextTv.getText().toString().equals("Ebook")) {
                        GetInterviewPaperListOnServer();
                    } else if (titleTextTv.getText().toString().equals("Video Course")) {
                        GetInterviewPreviousVideoOnServer();
                    }else if (titleTextTv.getText().toString().equals("Preivious Paper")) {
                        GetPaperListOnServer();
                    }

                } else if (Exam_type.equals("Syllabus")) {
                    GetSyllabusListOnServer();
                } else {
                    GetPaperListOnServer();
                }

            }
        });
    }

    private void GetPaperListOnServer() {
        if (NetworkUtil.isNetworkAvailable(PrelimsPreviousPaperActivity.this)) {
            Dialog materialDialog = ErrorMessage.initProgressDialog(PrelimsPreviousPaperActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.prv_paper_prelims(Exam_type_id, Material_type_id, SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
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
                            ErrorMessage.E("prv_paper_prelims" + object.toString());
                            //ErrorMessage.T(PrelimsPreviousPaperActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("prv_paper_prelims" + object.toString());
                                Example example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(PrelimsPreviousPaperActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    paperListRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    Previous_paper_Adapter firstR_v_adapter = new Previous_paper_Adapter(PrelimsPreviousPaperActivity.this, example.getResult(), Exam_type);
                                    paperListRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                }


                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(PrelimsPreviousPaperActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(PrelimsPreviousPaperActivity.this, LoginActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(PrelimsPreviousPaperActivity.this, object.getString("message"));

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
            ErrorMessage.T(PrelimsPreviousPaperActivity.this, "No Internet");
        }
    }

    private void GetInterviewPaperListOnServer() {
        if (NetworkUtil.isNetworkAvailable(PrelimsPreviousPaperActivity.this)) {
            Dialog materialDialog = ErrorMessage.initProgressDialog(PrelimsPreviousPaperActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("GetInterviewPaperListOnServer" + Exam_type_id + ">>" + Material_type_id + ">>" + SavedData.getIMEI_Number() + ">>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            Call<ResponseBody> call = apiService.interview_ebook_course(Exam_type_id, Material_type_id, SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
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
                            ErrorMessage.E("GetInterviewPaperListOnServer" + object.toString());
                            //ErrorMessage.T(PrelimsPreviousPaperActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("GetInterviewPaperListOnServer" + object.toString());
                                Example example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(PrelimsPreviousPaperActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    paperListRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    Previous_paper_Adapter firstR_v_adapter = new Previous_paper_Adapter(PrelimsPreviousPaperActivity.this, example.getResult(), Exam_type);
                                    paperListRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                }


                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(PrelimsPreviousPaperActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(PrelimsPreviousPaperActivity.this, LoginActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(PrelimsPreviousPaperActivity.this, object.getString("message"));

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
            ErrorMessage.T(PrelimsPreviousPaperActivity.this, "No Internet");
        }
    }

    private void GetSyllabusListOnServer() {
        if (NetworkUtil.isNetworkAvailable(PrelimsPreviousPaperActivity.this)) {
            Dialog materialDialog = ErrorMessage.initProgressDialog(PrelimsPreviousPaperActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.syllabus_list_new(Exam_type_id, Material_type_id, SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
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
                            ErrorMessage.E("GetSyllabusListOnServer" + object.toString());
                            //ErrorMessage.T(PrelimsPreviousPaperActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("GetSyllabusListOnServer" + object.toString());
                                JSONObject jsonObject = object.getJSONObject("result");

                                ArrayList<Result> resultArrayList = new ArrayList<>();
                                Result result = new Result();
                                result.setId(Integer.valueOf(jsonObject.getString("id")));
                                result.setExamType(Integer.valueOf(jsonObject.getString("exam_type")));
                                result.setMaterial_type_id(Integer.valueOf(jsonObject.getString("material_type_id")));
                                result.setUrl((jsonObject.getString("url")));
                                result.setTitle(Exam_type);
                                resultArrayList.add(result);
                                if (resultArrayList.size() > 0) {
                                   LinearLayoutManager gridLayoutManager = new LinearLayoutManager(PrelimsPreviousPaperActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    paperListRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    Previous_paper_Adapter firstR_v_adapter = new Previous_paper_Adapter(PrelimsPreviousPaperActivity.this, resultArrayList, Exam_type);
                                    paperListRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();

                                }


                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(PrelimsPreviousPaperActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(PrelimsPreviousPaperActivity.this, LoginActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(PrelimsPreviousPaperActivity.this, object.getString("message"));

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
            ErrorMessage.T(PrelimsPreviousPaperActivity.this, "No Internet");
        }
    }
    private void GetInterviewPreviousVideoOnServer() {
        if (NetworkUtil.isNetworkAvailable(PrelimsPreviousPaperActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(PrelimsPreviousPaperActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.interview_exam_video(Exam_type_id, Material_type_id, SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
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
                            ErrorMessage.E("GetInterviewPreviousVideoOnServer" + object.toString());
                            //ErrorMessage.T(PrelimsPreviousPaperActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("comes in if cond" + object.toString());
                                com.hindmppsc.exam.models.LiveClass_models.Example example = gson.fromJson(object.toString(), com.hindmppsc.exam.models.LiveClass_models.Example.class);
                                if (example.getResult().size() > 0) {
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(PrelimsPreviousPaperActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    paperListRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    LiveClass_Adapter firstR_v_adapter = new LiveClass_Adapter(PrelimsPreviousPaperActivity.this, example.getResult(), "Previous");
                                    paperListRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                }
                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(PrelimsPreviousPaperActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(PrelimsPreviousPaperActivity.this, LoginActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(PrelimsPreviousPaperActivity.this, object.getString("message"));

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
            ErrorMessage.T(PrelimsPreviousPaperActivity.this, "No Internet");
        }
    }
}
