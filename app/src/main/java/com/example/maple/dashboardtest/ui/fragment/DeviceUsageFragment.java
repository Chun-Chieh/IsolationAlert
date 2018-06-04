package com.example.maple.dashboardtest.ui.fragment;


import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.maple.dashboardtest.R;
import com.example.maple.dashboardtest.controller.DeviceUsageCollector;
import com.example.maple.dashboardtest.model.device.CallsSummary;
import com.example.maple.dashboardtest.model.device.DeviceUsage;
import com.example.maple.dashboardtest.ui.widget.GridSpacingItemDecoration;
import com.example.maple.dashboardtest.ui.adapter.DeviceUsageAdapter;
import com.example.maple.dashboardtest.ui.adapter.SectionedGridRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.maple.dashboardtest.controller.DeviceUsageCollector.CALL_TYPE_INCOMING;
import static com.example.maple.dashboardtest.controller.DeviceUsageCollector.CALL_TYPE_MISSED;
import static com.example.maple.dashboardtest.controller.DeviceUsageCollector.CALL_TYPE_OUTGOING;

/**
 * Device Usage Fragment
 */
public class DeviceUsageFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private SectionedGridRecyclerViewAdapter mSectionedAdapter;
    private DeviceUsageAdapter mAdapter;
    private DeviceUsageCollector mDeviceUsageCollector;
    private List<DeviceUsage> mDeviceUsageList;

    public DeviceUsageFragment() {
    }

    public static Fragment newInstance() {
        return new DeviceUsageFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_usage, container, false);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView = view.findViewById(R.id.recycler_view_device_usage);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(getContext(), R.dimen.item_offset));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUsageStats();
        updateFragmentUI();
    }

    private void updateFragmentUI() {
        mAdapter = new DeviceUsageAdapter(mDeviceUsageList);
        mAdapter.notifyDataSetChanged();
//        mRecyclerView.setAdapter(mAdapter);

        // Set the sectioned grid
        List<SectionedGridRecyclerViewAdapter.Section> sections = new ArrayList<>();

        // Set sections titles
        sections.add(new SectionedGridRecyclerViewAdapter.Section(0, "Call"));
        sections.add(new SectionedGridRecyclerViewAdapter.Section(4, "SMS"));
        sections.add(new SectionedGridRecyclerViewAdapter.Section(8, "Media"));

        // Add the base adapter to the sectionAdapter
        SectionedGridRecyclerViewAdapter.Section[] dummy = new SectionedGridRecyclerViewAdapter.Section[sections.size()];
        mSectionedAdapter = new SectionedGridRecyclerViewAdapter(getContext(), R.layout.list_item_section_header, R.id.text_view_section_title, mRecyclerView, mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));
        mRecyclerView.setAdapter(mSectionedAdapter);
    }

    protected void getUsageStats() {
        mDeviceUsageList = new ArrayList<>();
        mDeviceUsageCollector = new DeviceUsageCollector(getContext(), CallLog.Calls.CONTENT_URI);
        // section 0
        mDeviceUsageList.add(new DeviceUsage(new CallsSummary(0, mDeviceUsageCollector.getTodayCallsTotalDuration())));
        mDeviceUsageList.add(new DeviceUsage(mDeviceUsageCollector.getTodayCallsDetail(CALL_TYPE_INCOMING)));
        mDeviceUsageList.add(new DeviceUsage(mDeviceUsageCollector.getTodayCallsDetail(CALL_TYPE_OUTGOING)));
        mDeviceUsageList.add(new DeviceUsage(mDeviceUsageCollector.getTodayCallsDetail(CALL_TYPE_MISSED)));
        // section 4
        mDeviceUsageList.add(new DeviceUsage("Conversation", mDeviceUsageCollector.getTodayConversationCount(), "people"));
        mDeviceUsageList.add(new DeviceUsage("Total", mDeviceUsageCollector.getTodayAllMessagesCount(), "message"));
        mDeviceUsageList.add(new DeviceUsage("Received", mDeviceUsageCollector.getTodayInboxMessagesCount(), "message"));
        mDeviceUsageList.add(new DeviceUsage("Sent", mDeviceUsageCollector.getTodaySentMessagesCount(), "message"));
        // section 8
        mDeviceUsageList.add(new DeviceUsage("Image", mDeviceUsageCollector.getTodayPhotoCount(), "piece"));
        mDeviceUsageList.add(new DeviceUsage("Video", mDeviceUsageCollector.getTodayVideoCount(), "clip"));
    }


}
