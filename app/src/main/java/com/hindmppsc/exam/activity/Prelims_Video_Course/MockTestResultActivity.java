package com.hindmppsc.exam.activity.Prelims_Video_Course;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.DashboardActivity;
import com.hindmppsc.exam.database.UserProfileHelper;
import com.hindmppsc.exam.models.MockTest.Example;
import com.hindmppsc.exam.utility.ErrorMessage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MockTestResultActivity extends BaseActivity {

    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.user_pic_img)
    CircleImageView userPicImg;

    @BindView(R.id.answer_btn)
    Button answerBtn;
    Example example;
    ArrayList<Answer_Model> myList;
    @BindView(R.id.name_etv)
    TextView nameEtv;
    @BindView(R.id.total_question_etv)
    TextView totalQuestionEtv;
    @BindView(R.id.attempt_question_etv)
    TextView attemptQuestionEtv;
    @BindView(R.id.right_question_etv)
    TextView rightQuestionEtv;

    @Override
    protected int getContentResId() {
        return R.layout.activity_mock_test_result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            example = (Example) bundle.getSerializable("AllData");
            myList = (ArrayList<Answer_Model>) getIntent().getSerializableExtra("AllQuestion_Answer");
           /* paperNameTv.setText(example.getSetPaper());
            numberQuestionTv.setText("" + example.getPaid().get(0).getTest().size());*/
            attemptQuestionEtv.setText("" + myList.size());
            totalQuestionEtv.setText("" + example.getResult().size());
        }
        if (UserProfileHelper.getInstance().getUserProfileModel().size() > 0) {
            Glide.with(MockTestResultActivity.this).load(UserProfileHelper.getInstance().getUserProfileModel().get(0).getProfile_pic()).placeholder(R.drawable.ic_user).into(userPicImg);
            nameEtv.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getDisplayName());
        }
        int TotalMarks = 0;
        try {
            if (myList.size() > 0) {
                for (int i = 0; i < myList.size(); i++) {
                    ErrorMessage.E("Arraylist position" + example.getResult().get(Integer.parseInt(myList.get(i).getPosition())).getAnswer() + ">>" + myList.get(i).getAnswer());
                    if (example.getResult().get(Integer.parseInt(myList.get(i).getPosition())).getAnswer().equals(myList.get(i).getAnswer())) {
                        if (TotalMarks == 0) {
                            TotalMarks = 1;
                        } else {
                            TotalMarks = 1 + TotalMarks;
                        }
                    }
                }
            }
        } catch (Exception e) {
            ErrorMessage.E("Result Exception" + e.toString());
        }
        rightQuestionEtv.setText(""+TotalMarks);
    }

    @OnClick(R.id.answer_btn)
    public void onClick() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("AllData", example);
        bundle.putSerializable("AllQuestion_Answer", myList);
        ErrorMessage.I_clear(MockTestResultActivity.this, ShowResultActivity.class, bundle);
    }
    @Override
    public void onBackPressed() {
        ErrorMessage.I_clear(MockTestResultActivity.this, DashboardActivity.class, null);
        super.onBackPressed();
    }
}
