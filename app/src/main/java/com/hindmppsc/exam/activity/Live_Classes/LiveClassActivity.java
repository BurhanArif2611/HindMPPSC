package com.hindmppsc.exam.activity.Live_Classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hindmppsc.exam.BaseActivity;
import com.hindmppsc.exam.R;
import com.hindmppsc.exam.fragments.LiveFragment;
import com.hindmppsc.exam.fragments.PreviousLiveFragment;
import com.hindmppsc.exam.utility.ErrorMessage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiveClassActivity extends BaseActivity {

    @BindView(R.id.title_text_tv)
    TextView titleTextTv;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    String Subject_id="",exam_id="";


    @Override
    protected int getContentResId() {
        return R.layout.activity_live_class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        titleTextTv.setText("ToDay Class's");
        Bundle bundle=getIntent().getExtras();
        if (bundle !=null){
            Subject_id=bundle.getString("id");
            exam_id=bundle.getString("exam_type");
        }
        ErrorMessage.E("Subject_id"+Subject_id+">>"+exam_id);
        tabs.addTab(tabs.newTab().setText("Live Class's"));
        tabs.addTab(tabs.newTab().setText("Previous Live Class's"));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        MyTabAdapter adapter = new MyTabAdapter(LiveClassActivity.this, getSupportFragmentManager(), tabs.getTabCount());
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public class MyTabAdapter extends FragmentPagerAdapter {
        private Context myContext;
        int totalTabs;

        public MyTabAdapter(Context context, FragmentManager fm, int totalTabs) {
            super(fm);
            myContext = context;
            this.totalTabs = totalTabs;
        }


        // this is for fragment tabs
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    LiveFragment homeFragment = new LiveFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("Subject_id", Subject_id);
                    bundle.putString("exam_id", exam_id);
                    homeFragment.setArguments(bundle);
                    return homeFragment;
                case 1:
                    PreviousLiveFragment sportFragment = new PreviousLiveFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("Subject_id", Subject_id);
                    bundle1.putString("exam_id", exam_id);
                    sportFragment.setArguments(bundle1);
                    return sportFragment;
                default:
                    return null;
            }
        }

        // this counts total number of tabs
        @Override
        public int getCount() {
            return totalTabs;
        }
    }


}
