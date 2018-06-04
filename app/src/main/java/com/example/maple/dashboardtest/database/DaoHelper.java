package com.example.maple.dashboardtest.database;

import android.content.Context;

import com.example.maple.dashboardtest.model.survey.DaoMaster;
import com.example.maple.dashboardtest.model.survey.DaoSession;
import com.example.maple.dashboardtest.model.survey.SurveySelectedAnswerDao;

import org.greenrobot.greendao.database.Database;

public final class DaoHelper {
    private Database db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private final SurveySelectedAnswerDao surveySelectedAnswerDao;

    private static DaoHelper instance;
    /**
     * Copy Constructor
     *
     * @param appContext Application Context
     */
    public DaoHelper(Context appContext) {
        this.db = (new DaoMaster.DevOpenHelper(appContext, "isolationalert-db")).getWritableDb();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        surveySelectedAnswerDao = daoSession.getSurveySelectedAnswerDao();
    }

    public static DaoHelper getInstance(Context context){
        if (instance == null)
             instance = new DaoHelper(context);
        return instance;
    }

    public SurveySelectedAnswerDao getSurveyAnswerDao() {
        return this.surveySelectedAnswerDao;
    }

    public void close() {
        db.close();
    }
}
