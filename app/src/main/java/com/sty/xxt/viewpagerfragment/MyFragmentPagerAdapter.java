package com.sty.xxt.viewpagerfragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private String[] titles;

    public MyFragmentPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragments, String[] titles) {
//        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        super(fm, BEHAVIOR_SET_USER_VISIBLE_HINT);
        this.fragmentList = fragments;
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
