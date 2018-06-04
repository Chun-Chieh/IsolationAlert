package com.example.maple.dashboardtest.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maple.dashboardtest.R;

public class ConversationFragment extends Fragment {

    public ConversationFragment() {
        // Required empty public constructor
    }
    public static Fragment newInstance() {
        return new ConversationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_conversation, container, false);
    }
}
