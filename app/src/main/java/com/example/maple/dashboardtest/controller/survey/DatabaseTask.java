package com.example.maple.dashboardtest.controller.survey;

import android.os.AsyncTask;


import com.example.maple.dashboardtest.database.DaoHelper;
import com.example.maple.dashboardtest.model.survey.SurveySelectedAnswer;

import java.util.List;

/**
 * @author Chun-Chieh Liang on 3/22/18.
 */

public class DatabaseTask extends AsyncTask<Void, Void, Void> {
    private DaoHelper daoHelper;
    private List<SurveySelectedAnswer> surveySelectedAnswerList;

    public DatabaseTask(DaoHelper daoHelper, List<SurveySelectedAnswer> surveySelectedAnswerList){
        this.daoHelper = daoHelper;
        this.surveySelectedAnswerList = surveySelectedAnswerList;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        new SurveySelectedAnswerCollector(daoHelper, surveySelectedAnswerList)
                .saveResult();
        return null;
    }
}
