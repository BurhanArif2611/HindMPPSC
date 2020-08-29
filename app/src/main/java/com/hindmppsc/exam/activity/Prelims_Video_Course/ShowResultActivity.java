package com.hindmppsc.exam.activity.Prelims_Video_Course;

import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.activity.DashboardActivity;
import com.hindmppsc.exam.adapter.TabsWithQuestionFragAdapter;
import com.hindmppsc.exam.fragments.AnswerFragment;
import com.hindmppsc.exam.fragments.FinalAnswerFragment;
import com.hindmppsc.exam.models.MockTest.Example;
import com.hindmppsc.exam.utility.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowResultActivity extends BaseActivity {

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    Example example;
    ArrayList<Answer_Model> myList;
    @Override
    protected int getContentResId() {
        return R.layout.activity_show_result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        titleTextTv.setText("Answer Sheet");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            example = (Example) bundle.getSerializable("AllData");
            myList = (ArrayList<Answer_Model>) getIntent().getSerializableExtra("AllQuestion_Answer");

            if (example.getResult().size() > 0) {
                List<Fragment> fragments = new ArrayList<Fragment>();
                for (int i = 0; i < example.getResult().size(); i++) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("AllData", example.getResult().get(i));
                    bundle1.putString("Postion", String.valueOf(i));
                    bundle1.putString("Size", String.valueOf(example.getResult().size()));
                    bundle1.putSerializable("AllQuestion_Answer", myList);
                    fragments.add(Fragment.instantiate(ShowResultActivity.this, FinalAnswerFragment.class.getName(), bundle1));
                }
                TabsWithQuestionFragAdapter adapter = new TabsWithQuestionFragAdapter(ShowResultActivity.this, getSupportFragmentManager(), fragments, example.getResult());
                pager.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                ErrorMessage.E("list_size" + example.getResult().size());
            }
        }

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               /* remainingSecondTv.setText(position + 1 + "/" + example.getPaid().get(0).getTest().size());
                progressBar.setProgress(position + 1);*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void preview_next(int Position) {
        pager.setCurrentItem((Position));
    }

    public void next_next(int Position) {
        pager.setCurrentItem((Position));
    }

    @Override
    public void onBackPressed() {
        ErrorMessage.I_clear(ShowResultActivity.this, DashboardActivity.class, null);
        super.onBackPressed();
    }

    public void Done_AllQuestions() {
        ErrorMessage.I_clear(ShowResultActivity.this, DashboardActivity.class, null);
    }
}
