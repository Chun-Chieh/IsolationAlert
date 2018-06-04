package com.example.maple.dashboardtest.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maple.dashboardtest.R;
import com.example.maple.dashboardtest.ui.adapter.TrainingAdapter;
import com.example.maple.dashboardtest.ui.widget.ScrollLinearLayoutManager;


public class TrainingFragment extends Fragment {
    private String[] mTrainingText;
    private RecyclerView mRecyclerView;
    private TrainingAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean isScrolling = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_training, container, false);

        // load the text for training session
        loadTrainingText();

        // initiate the views
        initView(view);

        initThread();

        return view;
    }

    private void loadTrainingText() {
        String str = getResources().getString(R.string.text_training);
        mTrainingText = str.split("\n");
    }


    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_training);

        mLayoutManager = new ScrollLinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new TrainingAdapter(mTrainingText);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                initAutoScrolling();
            }
        });

    }

    private void initAutoScrolling() {
        if(!isScrolling) {
            for (int i = 0; i < mTrainingText.length; i++) {
                mRecyclerView.smoothScrollToPosition(i);
            }
        }
    }


    private void initThread(){
        if(!isScrolling) {
            final Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    initAutoScrolling();
                    handler.postDelayed(this, 8000);
                }
            };
            handler.postDelayed(runnable, 8000);
        }
    }
}
