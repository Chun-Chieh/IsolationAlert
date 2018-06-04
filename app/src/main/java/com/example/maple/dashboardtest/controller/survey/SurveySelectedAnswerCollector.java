package com.example.maple.dashboardtest.controller.survey;


import android.os.AsyncTask;

import com.example.maple.dashboardtest.database.DaoHelper;
import com.example.maple.dashboardtest.model.survey.SurveySelectedAnswer;

import java.util.ArrayList;
import java.util.List;

/**
 * Save the chosen answers to the database
 *
 * @author Chun-Chieh Liang on 3/22/18.
 */

public class SurveySelectedAnswerCollector {
    DaoHelper mDaoHelper;
    private List<SurveySelectedAnswer> surveySelectedAnswerList = new ArrayList<>();

    public SurveySelectedAnswerCollector(DaoHelper daoHelper, List<SurveySelectedAnswer> surveySelectedAnswerList){
        this.mDaoHelper = daoHelper;
        this.surveySelectedAnswerList = surveySelectedAnswerList;
    }

    public void saveResult(){
        for (SurveySelectedAnswer answer: surveySelectedAnswerList) {
            mDaoHelper.getSurveyAnswerDao().save(answer);
        }
    }
}
