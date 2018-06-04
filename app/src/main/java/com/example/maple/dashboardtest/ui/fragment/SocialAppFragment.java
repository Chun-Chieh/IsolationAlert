package com.example.maple.dashboardtest.ui.fragment;


import android.app.usage.UsageStats;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.maple.dashboardtest.R;
import com.example.maple.dashboardtest.controller.SocialAppUsageCollector;
import com.example.maple.dashboardtest.model.social.SocialAppUsage;
import com.example.maple.dashboardtest.ui.adapter.SectionedGridRecyclerViewAdapter;
import com.example.maple.dashboardtest.ui.adapter.SocialAppAdapter;
import com.example.maple.dashboardtest.ui.widget.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.maple.dashboardtest.util.formatMilliSecToHMS;


public class SocialAppFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private Button mButtonOpenSettings;
    private TextView mTextViewNoDataPrompt;
    private SectionedGridRecyclerViewAdapter mSectionedAdapter;
    private SocialAppAdapter mAdapter;
    private List<SocialAppUsage> mSocialAppUsageList;

    private SocialAppUsageCollector mSocialAppUsageCollector;
    private int mSortBy;
    private long mTotalUsageTime;

    public SocialAppFragment() {
    }

    public static Fragment newInstance() {
        return new SocialAppFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSocialAppUsageCollector = new SocialAppUsageCollector(getActivity(), getContext());
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social_app, container, false);

        mButtonOpenSettings = view.findViewById(R.id.button_social_app_permission);
        mTextViewNoDataPrompt = view.findViewById(R.id.text_view_social_app_no_data);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView = view.findViewById(R.id.recycler_view_social_app);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(getContext(), R.dimen.item_offset));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);

//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                FloatingActionButton fab = getActivity().findViewById(R.id.fab);
//                if(dy > 0){
//                    fab.hide();
//                } else{
//                    fab.show();
//                }
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getSocialAppStats();
        updateFragmentUI();
    }

    private void updateFragmentUI() {
        if (mSocialAppUsageList == null) {
            mTextViewNoDataPrompt.setVisibility(View.GONE);

        } else if (mSocialAppUsageList.size() == 0) {
            mTextViewNoDataPrompt.setVisibility(View.VISIBLE);
        } else {
            mTextViewNoDataPrompt.setVisibility(View.GONE);
//            mFab.setVisibility(View.VISIBLE);
            mAdapter = new SocialAppAdapter(mSocialAppUsageList, mTotalUsageTime);
            mAdapter.notifyDataSetChanged();

            // Set the sectioned grid
            List<SectionedGridRecyclerViewAdapter.Section> sections = new ArrayList<>();

            // Set sections titles
            sections.add(new SectionedGridRecyclerViewAdapter.Section(0, "Social App"));

            // Add the base adapter to the sectionAdapter
            SectionedGridRecyclerViewAdapter.Section[] dummy = new SectionedGridRecyclerViewAdapter.Section[sections.size()];
            mSectionedAdapter = new SectionedGridRecyclerViewAdapter(getContext(), R.layout.list_item_section_header, R.id.text_view_section_title, mRecyclerView, mAdapter);
            mSectionedAdapter.setSections(sections.toArray(dummy));
            mRecyclerView.setAdapter(mSectionedAdapter);
        }
    }

    protected void getSocialAppStats() {
        if (!mSocialAppUsageCollector.isAccessGranted()) {
            // if the permission is not granted, let the user enter the settings
            mButtonOpenSettings.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            mButtonOpenSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                }
            });

        } else {
            mButtonOpenSettings.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

            mSocialAppUsageList = new ArrayList<>();
            Map<String, UsageStats> map = mSocialAppUsageCollector.getAllUsageStatistics();
            mSocialAppUsageList = mSocialAppUsageCollector.getSocialAppUsageList(map);
            mSocialAppUsageList = mSocialAppUsageCollector.getSortedSocialAppUsageList(mSocialAppUsageList);
            mTotalUsageTime = mSocialAppUsageCollector.getAllTotalUsageTime(mSocialAppUsageList);
            Log.d("Social App", "TotalUsage: " + formatMilliSecToHMS(mTotalUsageTime));
        }
    }
}
