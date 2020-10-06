package com.hindmppsc.exam.activity.Prelims_Video_Course;

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
import com.hindmppsc.exam.activity.Complete_Current_AffairListActivity;
import com.hindmppsc.exam.activity.Current_Affairs_MonthActivity;
import com.hindmppsc.exam.activity.InterviewPreviousVideoActivity;
import com.hindmppsc.exam.activity.Live_Classes.LiveClassActivity;
import com.hindmppsc.exam.activity.Live_Classes.PaymentActivity;
import com.hindmppsc.exam.activity.LoginActivity;
import com.hindmppsc.exam.activity.PDFViewerActivity;
import com.hindmppsc.exam.activity.Set_ListActivity;
import com.hindmppsc.exam.activity.TermsConditionActivity;
import com.hindmppsc.exam.adapter.Material_Type_Adapter;
import com.hindmppsc.exam.adapter.Subject_Adapter;
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

public class MaterialListActivity extends BaseActivity {

    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.paper_list_rcv)
    RecyclerView paperListRcv;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    private String Material_type = "";
    private String Exam_type = "";
    private String Exam_type_id = "";
    private String Suscribe_type = "";

    @Override
    protected int getContentResId() {
        return R.layout.activity_paper_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Exam_type = bundle.getString("exam_type");
            Exam_type_id = bundle.getString("id");
            titleTextTv.setText(bundle.getString("exam_type"));
        }
        GetMaterialListOnServer();

        swiperefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetMaterialListOnServer();
            }
        });
    }

    private void GetMaterialListOnServer() {
        if (NetworkUtil.isNetworkAvailable(MaterialListActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(MaterialListActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("GetSubjectOnServer>" + Exam_type_id + ">>" + SavedData.getIMEI_Number() + ">>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
            Call<ResponseBody> call = apiService.exam_material_type(Exam_type_id, SavedData.getIMEI_Number(), UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
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
                            //ErrorMessage.T(MaterialListActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("comes in if cond" + object.toString());
                                Example example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(MaterialListActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    paperListRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    Material_Type_Adapter firstR_v_adapter = new Material_Type_Adapter(MaterialListActivity.this, example.getResult(), titleTextTv.getText().toString());
                                    paperListRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                }
                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(MaterialListActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(MaterialListActivity.this, LoginActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(MaterialListActivity.this, object.getString("message"));

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
            ErrorMessage.T(MaterialListActivity.this, "No Internet");
        }
    }

    public void getpreivious_paper(Result listCoupon) {
        Material_type = listCoupon.getMaterialType();
        Suscribe_type = listCoupon.getSubscribe();
        if (listCoupon.getSubscribe().equals("subscribe")) {
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(listCoupon.getId()));
            bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
            bundle.putString("material", listCoupon.getMaterialType());
            bundle.putString("Video Course", Exam_type);
            ErrorMessage.I(MaterialListActivity.this, PrelimsPreviousPaperActivity.class, bundle);
        } else if (listCoupon.getPrice().equals("0") || listCoupon.getPrice().equals("")) {
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(listCoupon.getId()));
            bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
            bundle.putString("material", listCoupon.getMaterialType());
            bundle.putString("Video Course", Exam_type);
            ErrorMessage.I(MaterialListActivity.this, PrelimsPreviousPaperActivity.class, bundle);
        } else {
            Intent i = new Intent(MaterialListActivity.this, TermsConditionActivity.class);
            i.putExtra("id", String.valueOf(listCoupon.getId()));
            i.putExtra("exam_type", String.valueOf(listCoupon.getExamType()));
            i.putExtra("price", listCoupon.getPrice());
            i.putExtra("fromActivity", "Payment");
            startActivityForResult(i, 1);
        }
    }

    public void getsyllabus(Result listCoupon) {
        Material_type = listCoupon.getMaterialType();
        Exam_type = listCoupon.getMaterialType();
        Suscribe_type = listCoupon.getSubscribe();

        if (listCoupon.getSubscribe().equals("subscribe")) {
            /*Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(listCoupon.getId()));
            bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
            bundle.putString("material", listCoupon.getMaterialType());
            bundle.putString("Video Course", listCoupon.getMaterialType());
            ErrorMessage.I(MaterialListActivity.this, MaterialListActivity.class, bundle);*/
            GetSyllabusListOnServer(String.valueOf(listCoupon.getId()));
        } else {
            if (listCoupon.getPrice().equals("0") || listCoupon.getPrice().equals("")) {
              /*  Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(listCoupon.getId()));
                bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
                bundle.putString("material", listCoupon.getMaterialType());
                bundle.putString("Video Course", listCoupon.getMaterialType());
                ErrorMessage.I(MaterialListActivity.this, MaterialListActivity.class, bundle);*/
                GetSyllabusListOnServer(String.valueOf(listCoupon.getId()));
            } else {
                Intent i = new Intent(MaterialListActivity.this, TermsConditionActivity.class);
                i.putExtra("id", String.valueOf(listCoupon.getId()));
                i.putExtra("exam_type", String.valueOf(listCoupon.getExamType()));
                i.putExtra("price", listCoupon.getPrice());
                i.putExtra("fromActivity", "Payment");
                startActivityForResult(i, 13);
            }
        }
    }

    public void getInterviewEBook(Result listCoupon) {
        Material_type = listCoupon.getMaterialType();
        Suscribe_type = listCoupon.getSubscribe();
        if (listCoupon.getSubscribe().equals("subscribe")) {
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(listCoupon.getId()));
            bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
            bundle.putString("material", listCoupon.getMaterialType());
            bundle.putString("Video Course", Exam_type);
            ErrorMessage.I(MaterialListActivity.this, MaterialListActivity.class, bundle);
        } else {
            Intent i = new Intent(MaterialListActivity.this, TermsConditionActivity.class);
            i.putExtra("id", String.valueOf(listCoupon.getId()));
            i.putExtra("exam_type", String.valueOf(listCoupon.getExamType()));
            i.putExtra("price", listCoupon.getPrice());
            i.putExtra("fromActivity", "Payment");
            startActivityForResult(i, 7);
        }
    }

    public void getCurrent_Affairs_Complete_course(Result listCoupon) {
        Material_type = listCoupon.getMaterialType();
        Suscribe_type = listCoupon.getSubscribe();
        if (listCoupon.getSubscribe().equals("subscribe")) {
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(listCoupon.getId()));
            bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
            bundle.putString("material", listCoupon.getMaterialType());
            bundle.putString("Exam_type", Exam_type);
            ErrorMessage.I(MaterialListActivity.this, Complete_Current_AffairListActivity.class, bundle);
        } else {
            Intent i = new Intent(MaterialListActivity.this, TermsConditionActivity.class);
            i.putExtra("id", String.valueOf(listCoupon.getId()));
            i.putExtra("exam_type", String.valueOf(listCoupon.getExamType()));
            i.putExtra("price", listCoupon.getPrice());
            i.putExtra("fromActivity", "Payment");
            startActivityForResult(i, 9);
        }
    }

    public void getCurrent_Affairs_Month(Result listCoupon) {
        Material_type = listCoupon.getMaterialType();

        Bundle bundle = new Bundle();
        bundle.putString("id", String.valueOf(listCoupon.getId()));
        bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
        bundle.putString("material", listCoupon.getMaterialType());
        bundle.putString("Exam_type", Exam_type);
        ErrorMessage.I(MaterialListActivity.this, Current_Affairs_MonthActivity.class, bundle);


    }

    public void getMockTest(Result listCoupon) {
        Material_type = listCoupon.getMaterialType();
        Suscribe_type = listCoupon.getSubscribe();
        if (listCoupon.getSubscribe().equals("subscribe")) {
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(listCoupon.getId()));
            bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
            bundle.putString("material", listCoupon.getMaterialType());
            bundle.putString("Exam_type", Exam_type);
            ErrorMessage.I(MaterialListActivity.this, Set_ListActivity.class, bundle);
            //ErrorMessage.I(MaterialListActivity.this, MockTestPrelimsActivity.class, bundle);
        } else {
            Intent i = new Intent(MaterialListActivity.this, TermsConditionActivity.class);
            i.putExtra("id", String.valueOf(listCoupon.getId()));
            i.putExtra("exam_type", String.valueOf(listCoupon.getExamType()));
            i.putExtra("price", listCoupon.getPrice());
            i.putExtra("fromActivity", "Payment");
            startActivityForResult(i, 3);
        }
    }

    public void getInterviewPreviousVideo(Result listCoupon) {

        // GetInterviewPreviousVideoOnServer(String.valueOf(listCoupon.getExamType()), String.valueOf(listCoupon.getId()));
        Suscribe_type = listCoupon.getSubscribe();
        if (listCoupon.getSubscribe().equals("subscribe")) {
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(listCoupon.getId()));
            bundle.putString("exam_type", String.valueOf(listCoupon.getExamType()));
            bundle.putString("material", listCoupon.getMaterialType());
            bundle.putString("Exam_type", Exam_type);
            ErrorMessage.I(MaterialListActivity.this, InterviewPreviousVideoActivity.class, bundle);
        } else {
            Intent i = new Intent(MaterialListActivity.this, TermsConditionActivity.class);
            i.putExtra("id", String.valueOf(listCoupon.getId()));
            i.putExtra("exam_type", String.valueOf(listCoupon.getExamType()));
            i.putExtra("price", listCoupon.getPrice());
            i.putExtra("fromActivity", "Payment");
            startActivityForResult(i, 5);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                ErrorMessage.E("MaterialListActivity" + data.getStringExtra("price"));
                Intent i = new Intent(MaterialListActivity.this, PaymentActivity.class);
                i.putExtra("id", data.getStringExtra("id"));
                i.putExtra("exam_type", data.getStringExtra("exam_type"));
                i.putExtra("price", data.getStringExtra("price"));
                i.putExtra("Suscribe_type", Suscribe_type);
             /*   i.putExtra("Material_type_id", Material_type_id);
                i.putExtra("Paper_id", Paper_id);*/
                i.putExtra("fromActivity", "preivious_paper");
                startActivityForResult(i, 2);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        if (requestCode == 13) {
            if (resultCode == Activity.RESULT_OK) {
                ErrorMessage.E("MaterialListActivity" + data.getStringExtra("price"));
                Intent i = new Intent(MaterialListActivity.this, PaymentActivity.class);
                i.putExtra("id", data.getStringExtra("id"));
                i.putExtra("exam_type", data.getStringExtra("exam_type"));
                i.putExtra("price", data.getStringExtra("price"));
                i.putExtra("Suscribe_type", Suscribe_type);
             /*   i.putExtra("Material_type_id", Material_type_id);
                i.putExtra("Paper_id", Paper_id);*/
                i.putExtra("fromActivity", "preivious_paper");
                startActivityForResult(i, 14);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == 9) {
            if (resultCode == Activity.RESULT_OK) {
                ErrorMessage.E("MaterialListActivity" + data.getStringExtra("price"));
                Intent i = new Intent(MaterialListActivity.this, PaymentActivity.class);
                i.putExtra("id", data.getStringExtra("id"));
                i.putExtra("exam_type", data.getStringExtra("exam_type"));
                i.putExtra("price", data.getStringExtra("price"));
                i.putExtra("Suscribe_type", Suscribe_type);
             /*   i.putExtra("Material_type_id", Material_type_id);
                i.putExtra("Paper_id", Paper_id);*/
                i.putExtra("fromActivity", "Current_Affairs_Complete_course");
                startActivityForResult(i, 10);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == 11) {
            if (resultCode == Activity.RESULT_OK) {
                ErrorMessage.E("MaterialListActivity" + data.getStringExtra("price"));
                Intent i = new Intent(MaterialListActivity.this, PaymentActivity.class);
                i.putExtra("id", data.getStringExtra("id"));
                i.putExtra("exam_type", data.getStringExtra("exam_type"));
                i.putExtra("price", data.getStringExtra("price"));
                i.putExtra("Suscribe_type", Suscribe_type);
             /*   i.putExtra("Material_type_id", Material_type_id);
                i.putExtra("Paper_id", Paper_id);*/
                i.putExtra("fromActivity", "Current_Affairs_Month");
                startActivityForResult(i, 10);
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
                bundle.putString("Video Course", Exam_type);
                ErrorMessage.I(MaterialListActivity.this, PrelimsPreviousPaperActivity.class, bundle);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {
                Intent i = new Intent(MaterialListActivity.this, PaymentActivity.class);
                i.putExtra("id", data.getStringExtra("id"));
                i.putExtra("exam_type", data.getStringExtra("exam_type"));
                i.putExtra("price", data.getStringExtra("price"));
                i.putExtra("Suscribe_type", Suscribe_type);
             /*   i.putExtra("Material_type_id", Material_type_id);
                i.putExtra("Paper_id", Paper_id);*/
                i.putExtra("fromActivity", "MockTest");
                startActivityForResult(i, 4);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == 4) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = new Bundle();
                bundle.putString("id", data.getStringExtra("id"));
                bundle.putString("exam_type", data.getStringExtra("exam_type"));
                bundle.putString("material", Material_type);
                bundle.putString("Exam_type", Exam_type);
                ErrorMessage.I(MaterialListActivity.this, Set_ListActivity.class, bundle);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == 5) {
            if (resultCode == Activity.RESULT_OK) {
                ErrorMessage.E("MaterialListActivity" + data.getStringExtra("price"));
                Intent i = new Intent(MaterialListActivity.this, PaymentActivity.class);
                i.putExtra("id", data.getStringExtra("id"));
                i.putExtra("exam_type", data.getStringExtra("exam_type"));
                i.putExtra("price", data.getStringExtra("price"));
                i.putExtra("Suscribe_type", Suscribe_type);
             /*   i.putExtra("Material_type_id", Material_type_id);
                i.putExtra("Paper_id", Paper_id);*/
                i.putExtra("fromActivity", "preivious_paper");
                startActivityForResult(i, 6);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == 6) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = new Bundle();
                bundle.putString("id", data.getStringExtra("id"));
                bundle.putString("exam_type", data.getStringExtra("exam_type"));
                bundle.putString("material", Material_type);
                bundle.putString("Exam_type", Exam_type);
                ErrorMessage.I(MaterialListActivity.this, InterviewPreviousVideoActivity.class, bundle);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == 7) {
            if (resultCode == Activity.RESULT_OK) {
                ErrorMessage.E("MaterialListActivity" + data.getStringExtra("price"));
                Intent i = new Intent(MaterialListActivity.this, PaymentActivity.class);
                i.putExtra("id", data.getStringExtra("id"));
                i.putExtra("exam_type", data.getStringExtra("exam_type"));
                i.putExtra("price", data.getStringExtra("price"));
                i.putExtra("Suscribe_type", Suscribe_type);
             /*   i.putExtra("Material_type_id", Material_type_id);
                i.putExtra("Paper_id", Paper_id);*/
                i.putExtra("fromActivity", "preivious_paper");
                startActivityForResult(i, 8);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == 8) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = new Bundle();
                bundle.putString("id", data.getStringExtra("id"));
                bundle.putString("exam_type", data.getStringExtra("exam_type"));
                bundle.putString("material", Material_type);
                bundle.putString("Video Course", Exam_type);
                ErrorMessage.I(MaterialListActivity.this, MaterialListActivity.class, bundle);


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == 10) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = new Bundle();
                bundle.putString("id", data.getStringExtra("id"));
                bundle.putString("exam_type", data.getStringExtra("exam_type"));
                bundle.putString("material", Material_type);
                bundle.putString("Exam_type", Exam_type);
                ErrorMessage.I(MaterialListActivity.this, Complete_Current_AffairListActivity.class, bundle);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == 14) {
            if (resultCode == Activity.RESULT_OK) {
               /* Bundle bundle = new Bundle();
                bundle.putString("id", data.getStringExtra("id"));
                bundle.putString("exam_type", data.getStringExtra("exam_type"));
                bundle.putString("material", Material_type);
                bundle.putString("Video Course", Exam_type);
                ErrorMessage.I(MaterialListActivity.this, MaterialListActivity.class, bundle);*/
                GetSyllabusListOnServer(data.getStringExtra("id"));

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void GetSyllabusListOnServer(String Material_type_id) {
        if (NetworkUtil.isNetworkAvailable(MaterialListActivity.this)) {
            Dialog materialDialog = ErrorMessage.initProgressDialog(MaterialListActivity.this);
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
                            //ErrorMessage.T(MaterialListActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("GetSyllabusListOnServer" + object.toString());
                                com.hindmppsc.exam.models.GetSyallabus.Example example = gson.fromJson(object.toString(), com.hindmppsc.exam.models.GetSyallabus.Example.class);


                                Bundle bundle = new Bundle();
                                bundle.putString("image", example.getResult().getUrl());
                                bundle.putString("title", "Syllabus");
                                ErrorMessage.I(MaterialListActivity.this, PDFViewerActivity.class, bundle);


                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(MaterialListActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(MaterialListActivity.this, LoginActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(MaterialListActivity.this, object.getString("message"));

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
            ErrorMessage.T(MaterialListActivity.this, "No Internet");
        }
    }
}
