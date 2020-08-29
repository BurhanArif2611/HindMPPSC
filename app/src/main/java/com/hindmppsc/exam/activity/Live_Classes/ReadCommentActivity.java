package com.hindmppsc.exam.activity.Live_Classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.Firebase.Config;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.adapter.Live_Class_Comment_Adapter;
import com.hindmppsc.exam.models.Live_class_comment_models.Example;
import com.hindmppsc.exam.utility.AppConfig;
import com.hindmppsc.exam.utility.ErrorMessage;
import com.hindmppsc.exam.utility.LoadInterface;
import com.hindmppsc.exam.utility.NetworkUtil;

import org.json.JSONObject;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadCommentActivity extends BaseActivity {

    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.comment_rcv)
    RecyclerView commentRcv;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    @BindView(R.id.refrence_btn)
    ImageButton refrenceBtn;
    private String Live_id = "";

    @Override
    protected int getContentResId() {
        return R.layout.activity_read_comment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        titleTextTv.setText("Read Comments");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Live_id = bundle.getString("Live_id");
            GETMessageOnServer();
        }
        swiperefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GETMessageOnServer();

            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver(Admin_Comment, new IntentFilter(Config.Admin_Comment));
        refrenceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GETMessageOnServer();
            }
        });
    }

    private void GETMessageOnServer() {
        if (NetworkUtil.isNetworkAvailable(ReadCommentActivity.this)) {
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.get_comment(Live_id);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        swiperefresh.setRefreshing(false);
                        try {
                            Gson gson = new Gson();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("GETMessageOnServer" + object.toString());
                            //ErrorMessage.T(ReadCommentActivity.this, object.getString("message"));
                            if (object.getString("status").equals("200")) {
                                Example example = gson.fromJson(object.toString(), Example.class);
                                if (example.getResult().size() > 0) {
                                    Collections.reverse(example.getResult());
                                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(ReadCommentActivity.this);
                                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                                    commentRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                                    Live_Class_Comment_Adapter firstR_v_adapter = new Live_Class_Comment_Adapter(ReadCommentActivity.this, example.getResult());
                                    commentRcv.setAdapter(firstR_v_adapter);
                                    firstR_v_adapter.notifyDataSetChanged();
                                }


                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(ReadCommentActivity.this, object.getString("message"));

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ErrorMessage.E("Exceptions" + e);
                            swiperefresh.setRefreshing(false);
                        }
                    } else {
                        swiperefresh.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    swiperefresh.setRefreshing(false);
                }
            });
        } else {
            ErrorMessage.T(ReadCommentActivity.this, "No Internet");
            swiperefresh.setRefreshing(false);
        }
    }

    private BroadcastReceiver Admin_Comment = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                GETMessageOnServer();
            } catch (Exception e) {
                e.printStackTrace();
                ErrorMessage.E("Exception outer" + e.toString());
            }


        }
    };
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_read_comment:
                GETMessageOnServer();
                break;
        }
        return true;
    }*/
}
