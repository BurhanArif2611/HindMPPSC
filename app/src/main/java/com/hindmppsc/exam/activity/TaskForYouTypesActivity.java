package com.hindmppsc.exam.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.Live_Classes.TaskForYou_InterviewsActivity;
import com.hindmppsc.exam.activity.Prelims_Video_Course.MockTestPrelimsActivity;
import com.hindmppsc.exam.adapter.TaskForMains_Adapter;
import com.hindmppsc.exam.database.UserProfileHelper;
import com.hindmppsc.exam.models.Live_class_subjects.Example;
import com.hindmppsc.exam.utility.AppConfig;
import com.hindmppsc.exam.utility.ErrorMessage;
import com.hindmppsc.exam.utility.LoadInterface;
import com.hindmppsc.exam.utility.NetworkUtil;
import com.hindmppsc.exam.utility.SavedData;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskForYouTypesActivity extends BaseActivity {

    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.prelims_layout)
    CardView prelimsLayout;
    @BindView(R.id.mains_layout)
    CardView mainsLayout;
    @BindView(R.id.interview_layout)
    CardView interviewLayout;

    @Override
    protected int getContentResId() {
        return R.layout.activity_task_for_you_types;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        Bundle bundle=getIntent().getExtras();
        if (bundle !=null){
            bundle.getString("id");
            titleTextTv.setText(bundle.getString("exam_type"));
        }

    }

    @OnClick({R.id.prelims_layout, R.id.mains_layout, R.id.interview_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.prelims_layout:
                Bundle bundle=new Bundle();
                bundle.putString("Exam_type","Task_for_you");
                ErrorMessage.I(TaskForYouTypesActivity.this, TaskForYouPrelimsActivity.class, bundle);
                break;
            case R.id.mains_layout:
                ErrorMessage.I(TaskForYouTypesActivity.this, TaskForMains_Activity.class, null);
                break;
            case R.id.interview_layout:
                ErrorMessage.I(TaskForYouTypesActivity.this, TaskForYou_InterviewsActivity.class, null);
                break;
        }
    }

}
