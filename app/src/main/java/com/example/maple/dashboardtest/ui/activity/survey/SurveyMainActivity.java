package com.example.maple.dashboardtest.ui.activity.survey;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.maple.dashboardtest.R;
import com.example.maple.dashboardtest.ui.activity.MainActivity;

import static com.example.maple.dashboardtest.util.ARG_DONE_PROMPT;
import static com.example.maple.dashboardtest.util.convertDpToPixel;
import static com.example.maple.dashboardtest.util.getNavigationBarHeight;

/**
 * The main activity for the normal survey.
 * It shows the instruction and a button to start the survey.
 */
public class SurveyMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_main);

        final Button mButtonBeginSurvey = findViewById(R.id.button_survey_begin);
        setViewMargin(mButtonBeginSurvey, 16);

        mButtonBeginSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SurveyQuestionActivity.class);
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(SurveyMainActivity.this, mButtonBeginSurvey, "robot");
                startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    /**
     * Setup the margins for the views in Survey Activity in pixels
     * left: 0, right: 0, top: 0, bottom: navigation bar height + 16dp (converted to px)
     *
     * @param view that needs to setup the margins
     */
    private void setViewMargin(View view, int dp){
        int navHeight = getNavigationBarHeight(this);
        int margin = (int) convertDpToPixel(dp, this); // Converts dip into its equivalent px
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginParams.setMargins(0, 0, 0, navHeight + margin);
    }
}
