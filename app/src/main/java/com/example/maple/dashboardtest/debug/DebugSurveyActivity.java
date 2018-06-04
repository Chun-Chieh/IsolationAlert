package com.example.maple.dashboardtest.debug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.maple.dashboardtest.R;
import com.example.maple.dashboardtest.database.DaoHelper;
import com.example.maple.dashboardtest.model.survey.SurveySelectedAnswer;
import com.example.maple.dashboardtest.ui.adapter.survey.SurveyAnswerRecordAdapter;

import java.util.List;

public class DebugSurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_survey);

        Toolbar toolbar = findViewById(R.id.toolbar_debug);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_debug);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        SurveyAnswerRecordAdapter adapter= new SurveyAnswerRecordAdapter(readDb());
        recyclerView.setAdapter(adapter);
    }

    private List<SurveySelectedAnswer> readDb() {
        DaoHelper daoHelper = new DaoHelper(this);
        return daoHelper.getSurveyAnswerDao().queryBuilder().list();
    }
}
