package com.hindmppsc.exam.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.hindmppsc.exam.BuildConfig;
import com.hindmppsc.exam.Firebase.Config;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.Live_Classes.SubjectActivity;
import com.hindmppsc.exam.activity.Live_Classes.TaskForYou_InterviewsActivity;
import com.hindmppsc.exam.activity.Live_Classes.TermsConditionActivity;
import com.hindmppsc.exam.activity.Prelims_Video_Course.MaterialListActivity;
import com.hindmppsc.exam.adapter.Dashboard_Adapter;
import com.hindmppsc.exam.database.UserProfileHelper;
import com.hindmppsc.exam.models.Dashboard.Example;
import com.hindmppsc.exam.models.Dashboard.Result;
import com.hindmppsc.exam.utility.AppConfig;
import com.hindmppsc.exam.utility.ErrorMessage;
import com.hindmppsc.exam.utility.LoadInterface;
import com.hindmppsc.exam.utility.NetworkUtil;
import com.hindmppsc.exam.utility.SavedData;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    @BindView(R.id.live_course_layout)
    LinearLayout liveCourseLayout;
    @BindView(R.id.drawer_img_btn)
    ImageButton drawerImgBtn;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.user_profile_img_btn)
    ImageButton userProfileImgBtn;
    @BindView(R.id.notificans_btn)
    ImageButton notificans_btn;
    @BindView(R.id.my_purchase_tv)
    TextView myPurchaseTv;
    @BindView(R.id.logoutItemNav)
    TextView logoutItemNav;
    @BindView(R.id.home_tv)
    TextView homeTv;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    @BindView(R.id.imageView)
    CircleImageView imageView;
    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.user_email_tv)
    TextView userEmailTv;
    @BindView(R.id.badge_notification_1)
    TextView badgeNotification1;
    @BindView(R.id.share_tv)
    TextView shareTv;
    @BindView(R.id.help_tv)
    TextView helpTv;
    @BindView(R.id.update_your_self_tv)
    CardView updateYourSelfTv;
    @BindView(R.id.today_gs_tv)
    CardView todayGsTv;
    @BindView(R.id.task_for_interview_card)
    CardView taskForInterviewCard;
    @BindView(R.id.task_for_mains_card)
    CardView taskForMainsCard;
    @BindView(R.id.prelims_video_course_cardview)
    CardView prelimsVideoCourseCardview;
    @BindView(R.id.dashboard_rcv)
    RecyclerView dashboardRcv;
    @BindView(R.id.helpItemNav)
    TextView helpItemNav;
    @BindView(R.id.about_tv)
    TextView aboutTv;
    @BindView(R.id.faq_tv)
    TextView faqTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(DashboardActivity.this, drawerLayout, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        SavedData.saveFCM_ID("123");
        swiperefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetDataOnServer();
            }
        });
        if (UserProfileHelper.getInstance().getUserProfileModel().size() > 0) {
            userNameTv.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getDisplayName());
            userEmailTv.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getEmaiiId());
            if (!UserProfileHelper.getInstance().getUserProfileModel().get(0).getProfile_pic().equals("")) {
                Picasso.with(DashboardActivity.this).load(UserProfileHelper.getInstance().getUserProfileModel().get(0).getProfile_pic()).placeholder(R.drawable.ic_defult_user).into(imageView);
            }
        }
        if (!SavedData.getAddToCart_Count().equals("0")) {
            badgeNotification1.setVisibility(View.VISIBLE);
            badgeNotification1.setText(SavedData.getAddToCart_Count());
        } else {
            badgeNotification1.setVisibility(View.GONE);
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(NewRequest, new IntentFilter(Config.NewRequest));
        GetDataOnServer();
    }

    @OnClick({R.id.drawer_img_btn, R.id.live_course_layout, R.id.user_profile_img_btn, R.id.my_purchase_tv, R.id.logoutItemNav, R.id.home_tv, R.id.notificans_btn, R.id.share_tv, R.id.help_tv, R.id.update_your_self_tv, R.id.today_gs_tv, R.id.task_for_interview_card, R.id.task_for_mains_card, R.id.prelims_video_course_cardview, R.id.helpItemNav, R.id.about_tv,R.id.faq_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.drawer_img_btn:
                drawerLayout.isDrawerOpen(GravityCompat.START);
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.live_course_layout:
                ErrorMessage.I(DashboardActivity.this, SubjectActivity.class, null);
                break;
                case R.id.faq_tv:
                ErrorMessage.I(DashboardActivity.this, FaqActivity.class, null);
                break;
            case R.id.user_profile_img_btn:
                ErrorMessage.I(DashboardActivity.this, UpdateProfileActivity.class, null);
                break;
            case R.id.my_purchase_tv:
                ErrorMessage.I(DashboardActivity.this, MyPurchaseActivity.class, null);
                break;
            case R.id.helpItemNav:
                Bundle bundle = new Bundle();
                bundle.putString("Check", "terms");
                ErrorMessage.I(DashboardActivity.this, TermsConditionActivity.class, bundle);
                break;
            case R.id.about_tv:
                Bundle bundle1 = new Bundle();
                bundle1.putString("Check", "about");
                ErrorMessage.I(DashboardActivity.this, TermsConditionActivity.class, bundle1);
                break;
            case R.id.logoutItemNav:
                drawerLayout.closeDrawers();
                Logout_PopUP();
                break;
            case R.id.home_tv:
                drawerLayout.closeDrawers();
                break;
            case R.id.notificans_btn:
                ErrorMessage.I(DashboardActivity.this, NotificationActivity.class, null);
                break;
            case R.id.help_tv:
                ErrorMessage.I(DashboardActivity.this, SupportActivity.class, null);
                break;
            case R.id.update_your_self_tv:
                ErrorMessage.I(DashboardActivity.this, Update_Your_SelfActivity.class, null);
                break;
            case R.id.today_gs_tv:
                ErrorMessage.I(DashboardActivity.this, TodayGSActivity.class, null);
                break;
            case R.id.task_for_interview_card:
                ErrorMessage.I(DashboardActivity.this, TaskForYou_InterviewsActivity.class, null);
                break;
            case R.id.task_for_mains_card:
                ErrorMessage.I(DashboardActivity.this, TaskForMains_Activity.class, null);
                break;
            case R.id.prelims_video_course_cardview:
                ErrorMessage.I(DashboardActivity.this, MaterialListActivity.class, null);
                break;
            case R.id.share_tv:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "HIND MPPSC");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
                break;
        }
    }

    public void Logout_PopUP() {
        final Dialog dialog = new Dialog(DashboardActivity.this);
        dialog.setContentView(R.layout.logout_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final Button submit_btn = (Button) dialog.findViewById(R.id.submit_btn);
        final Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                UserProfileHelper.getInstance().delete();
                ErrorMessage.I_clear(DashboardActivity.this, LoginActivity.class, null);
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    private BroadcastReceiver NewRequest = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (!SavedData.getAddToCart_Count().equals("0")) {
                    badgeNotification1.setVisibility(View.VISIBLE);
                    badgeNotification1.setText(SavedData.getAddToCart_Count());
                } else {
                    badgeNotification1.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                ErrorMessage.E("Exception outer" + e.toString());
            }


        }
    };

    @Override
    protected void onPause() {
        try {
            if (!SavedData.getAddToCart_Count().equals("0")) {
                badgeNotification1.setVisibility(View.VISIBLE);
                badgeNotification1.setText(SavedData.getAddToCart_Count());
            } else {
                badgeNotification1.setVisibility(View.GONE);
            }
        } catch (Exception e) {
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        try {
            if (!SavedData.getAddToCart_Count().equals("0")) {
                badgeNotification1.setVisibility(View.VISIBLE);
                badgeNotification1.setText(SavedData.getAddToCart_Count());
            } else {
                badgeNotification1.setVisibility(View.GONE);
            }
        } catch (Exception e) {
        }
        super.onResume();
    }

    private void GetDataOnServer() {
        if (NetworkUtil.isNetworkAvailable(DashboardActivity.this)) {
            Dialog materialDialog = ErrorMessage.initProgressDialog(DashboardActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.exam_type();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("GetDataOnServer" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            swiperefresh.setRefreshing(false);
                            Gson gson = new Gson();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("GetDataOnServer" + object.toString());
                            //ErrorMessage.T(MockTestPrelimsActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.E("prv_paper_prelims" + object.toString());
                                Example example = gson.fromJson(object.toString(), Example.class);
                                ArrayList<Result> resultArrayList=new ArrayList<>();
                                 if (example.getResult().size() > 0) {
                                  for (int i=0;i<example.getResult().size();i++){
                                      if (example.getResult().get(i).getExam().equals("Live Class")){
                                          Result result=new Result();
                                          result.setId(example.getResult().get(i).getId());
                                          result.setExam(example.getResult().get(i).getExam());
                                          result.setImage(example.getResult().get(i).getImage());
                                          resultArrayList.add(result);
                                      }
                                  }for (int i=0;i<example.getResult().size();i++){
                                      if (!example.getResult().get(i).getExam().equals("Live Class")){
                                          Result result=new Result();
                                          result.setId(example.getResult().get(i).getId());
                                          result.setExam(example.getResult().get(i).getExam());
                                          result.setImage(example.getResult().get(i).getImage());
                                          resultArrayList.add(result);
                                      }
                                  }
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(DashboardActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    dashboardRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    Dashboard_Adapter firstR_v_adapter = new Dashboard_Adapter(DashboardActivity.this, resultArrayList);
                                    dashboardRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                }
                            } else if (object.getString("status").equals("403")) {
                                ErrorMessage.T(DashboardActivity.this, object.getString("message"));
                                UserProfileHelper.getInstance().delete();
                                ErrorMessage.I_clear(DashboardActivity.this, LoginActivity.class, null);
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(DashboardActivity.this, object.getString("message"));

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
            ErrorMessage.T(DashboardActivity.this, "No Internet");
            swiperefresh.setRefreshing(false);
        }
    }
}
