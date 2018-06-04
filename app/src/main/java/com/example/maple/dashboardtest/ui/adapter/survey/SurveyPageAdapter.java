package com.example.maple.dashboardtest.ui.adapter.survey;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.maple.dashboardtest.ui.fragment.SurveyQuestionFragment;

import java.util.ArrayList;

import static com.example.maple.dashboardtest.controller.survey.SurveyQuestionParser.questionList;
import static com.example.maple.dashboardtest.util.ARG_OPTIONS;
import static com.example.maple.dashboardtest.util.ARG_QUESTION;


/**
 * @author Chun-Chieh Liang on 3/17/18.
 */

public class SurveyPageAdapter extends FragmentStatePagerAdapter {

    public SurveyPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new SurveyQuestionFragment();

        Bundle args = new Bundle();
        //args.putInt(ARG_PAGE, position + 1);
        args.putString(ARG_QUESTION, questionList.get(position).getQuestion());
        args.putStringArrayList(ARG_OPTIONS, (ArrayList<String>) questionList.get(position).getOptions());

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return questionList.size();
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
