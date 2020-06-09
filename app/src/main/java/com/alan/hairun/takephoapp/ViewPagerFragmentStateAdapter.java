package com.alan.hairun.takephoapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: Alan
 * @date: 2020/5/24 0024
 * @time: 上午 9:52
 * @deprecated:
 */
public class ViewPagerFragmentStateAdapter extends FragmentStateAdapter {
    List<Fragment> fragmentList = new ArrayList<>();

    public ViewPagerFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity,List<Fragment> fragmentList) {
        super(fragmentActivity);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
