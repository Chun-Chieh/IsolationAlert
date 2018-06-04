package com.example.maple.dashboardtest.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.maple.dashboardtest.ui.fragment.ActivityFragment;
import com.example.maple.dashboardtest.ui.fragment.ConversationFragment;
import com.example.maple.dashboardtest.ui.fragment.DefaultFragment;
import com.example.maple.dashboardtest.ui.fragment.DeviceUsageFragment;
import com.example.maple.dashboardtest.ui.fragment.LocationFragment;
import com.example.maple.dashboardtest.ui.fragment.SocialAppFragment;

/**
 * MainPagerAdapter
 * <p>
 * To allocate the fragments for the viewpager
 *
 * @author Chun-Chieh Liang on 5/8/18.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {
    private String[] mCategories = new String[]{"Device", "Social", "Activity", "Location", "Conversation"};

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DeviceUsageFragment.newInstance();
            case 1:
                return SocialAppFragment.newInstance();
            case 2:
                return ActivityFragment.newInstance();
            case 3:
                return LocationFragment.newInstance();
            case 4:
                return ConversationFragment.newInstance();
            default:
                return DefaultFragment.newInstance(position + 1);
        }
    }

    @Override
    public int getCount() {
        return mCategories.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mCategories[position];
    }
}
