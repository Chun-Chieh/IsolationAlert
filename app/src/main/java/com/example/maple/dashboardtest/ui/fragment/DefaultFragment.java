package com.example.maple.dashboardtest.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maple.dashboardtest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DefaultFragment extends Fragment {
    private static final String ARGS_PAGE = "args_page";

    public DefaultFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        DefaultFragment fragment = new DefaultFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_default, container, false);
    }

}
