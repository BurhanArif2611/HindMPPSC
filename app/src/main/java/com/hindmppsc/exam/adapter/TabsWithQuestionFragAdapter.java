package com.hindmppsc.exam.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.hindmppsc.exam.models.MockTest.Result;

import java.util.List;

public class TabsWithQuestionFragAdapter extends FragmentPagerAdapter {

    public static int pos = 0;

    private List<Fragment> myFragments;
    private List<Result> categories;
    private Context context;

    public TabsWithQuestionFragAdapter(Context c, FragmentManager fragmentManager, List<Fragment> myFrags, List<Result> cats) {
        super(fragmentManager);
        myFragments = myFrags;
        this.categories = cats;
        this.context = c;
    }

    @Override
    public Fragment getItem(int position) {

        return myFragments.get(position);

    }

    @Override
    public int getCount() {

        return myFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        setPos(position);
        return categories.get(position).getQuestion();
    }

    public static int getPos() {
        return pos;
    }

    public void add(Class<Fragment> c, String title, Bundle b) {
        myFragments.add(Fragment.instantiate(context,c.getName(),b));
        Result data=new Result();
        data.setQuestion(title);
        categories.add(data);
    }

    public static void setPos(int pos) {
        TabsWithQuestionFragAdapter.pos = pos;
    }
}
