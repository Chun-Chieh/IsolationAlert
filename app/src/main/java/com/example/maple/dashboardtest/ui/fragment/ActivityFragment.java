package com.example.maple.dashboardtest.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maple.dashboardtest.R;
import com.example.maple.dashboardtest.model.activity.ActivityData;
import com.example.maple.dashboardtest.ui.adapter.ActivityAdapter;
import com.example.maple.dashboardtest.ui.adapter.SectionedGridRecyclerViewAdapter;
import com.example.maple.dashboardtest.ui.widget.GridSpacingItemDecoration;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private SectionedGridRecyclerViewAdapter mSectionedAdapter;
    private ActivityAdapter mAdapter;
    private List<ActivityData> mActivityDataList;


    public ActivityFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new ActivityFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container, false);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView = view.findViewById(R.id.recycler_view_activity);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(getContext(), R.dimen.item_offset));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivityStats();
        updateFragmentUI();
    }

    private void updateFragmentUI() {
        mAdapter = new ActivityAdapter(mActivityDataList);
        mAdapter.notifyDataSetChanged();
//        mRecyclerView.setAdapter(mAdapter);

        // Set the sectioned grid
        List<SectionedGridRecyclerViewAdapter.Section> sections = new ArrayList<>();

        // Set sections titles
        sections.add(new SectionedGridRecyclerViewAdapter.Section(0, "Activity Statistics"));

        // Add the base adapter to the sectionAdapter
        SectionedGridRecyclerViewAdapter.Section[] dummy = new SectionedGridRecyclerViewAdapter.Section[sections.size()];
        mSectionedAdapter = new SectionedGridRecyclerViewAdapter(getContext(), R.layout.list_item_section_header, R.id.text_view_section_title, mRecyclerView, mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));
        mRecyclerView.setAdapter(mSectionedAdapter);
    }

    protected void getActivityStats() {
        mActivityDataList = new ArrayList<>();
        mActivityDataList.add(new ActivityData(DetectedActivity.IN_VEHICLE, 42, 1));
        mActivityDataList.add(new ActivityData(DetectedActivity.ON_BICYCLE, 31, 2));
        mActivityDataList.add(new ActivityData(DetectedActivity.RUNNING, 2331, 4));
        mActivityDataList.add(new ActivityData(DetectedActivity.WALKING, 23231, 5));
        mActivityDataList.add(new ActivityData(DetectedActivity.STILL, 131, 3));
//        SparseArray<ActivityData> activityDataMap = new SparseArray<>();
//        activityDataMap.put(ad1.getActivityType(), ad1);
//        activityDataMap.put(ad1.getActivityType(), ad2);
//        activityDataMap.put(ad1.getActivityType(), ad3);
//        activityDataMap.put(ad1.getActivityType(), ad4);
//        activityDataMap.put(ad1.getActivityType(), ad5);

    }
}
