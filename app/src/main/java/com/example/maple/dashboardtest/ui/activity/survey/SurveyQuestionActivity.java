package com.example.maple.dashboardtest.ui.activity.survey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.maple.dashboardtest.R;
import com.example.maple.dashboardtest.controller.survey.DatabaseTask;
import com.example.maple.dashboardtest.controller.survey.SurveyQuestionParser;
import com.example.maple.dashboardtest.database.DaoHelper;
import com.example.maple.dashboardtest.model.survey.Question;
import com.example.maple.dashboardtest.model.survey.SurveySelectedAnswer;
import com.example.maple.dashboardtest.ui.activity.MainActivity;
import com.example.maple.dashboardtest.ui.adapter.survey.SurveyPageAdapter;
import com.example.maple.dashboardtest.ui.fragment.SurveyQuestionFragment;
import com.example.maple.dashboardtest.ui.widget.NonSwipeableViewPager;

import java.util.ArrayList;
import java.util.List;

import static com.example.maple.dashboardtest.controller.survey.SurveyQuestionParser.isSubQuestionList;
import static com.example.maple.dashboardtest.controller.survey.SurveyQuestionParser.questionList;
import static com.example.maple.dashboardtest.util.ARG_DONE_PROMPT;
import static com.example.maple.dashboardtest.util.JSON_FILE;


public class SurveyQuestionActivity extends AppCompatActivity {
    private LinearLayout mIndicator;
    private List<ImageView> mIndicatorImage;
    private NonSwipeableViewPager mViewPager;
    private SurveyPageAdapter mPagerAdapter;
    private Button mBtnContinue;
    private boolean isLastPage = false;

    private List<Question> mQuestionList = new ArrayList<>();
    private List<SurveySelectedAnswer> surveySelectedAnswerList = new ArrayList<>();
    private SurveyQuestionParser mParser = new SurveyQuestionParser(this);

    private int subCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_question);


    }

    @Override
    protected void onResume() {
        super.onResume();
        clearDataSet();
        initView();
    }

    private void initView() {
        mIndicator = findViewById(R.id.pager_indicator);
        mViewPager = findViewById(R.id.view_pager_survey);
        mBtnContinue = findViewById(R.id.button_survey);

        // the list of questions from json
        setQuestions();

        // the page indicator
        initPagerIndicator();

        // the pager for fragments
        initPager();

        // the next / done button
        initButton();
    }

    /**
     * Setup the questions
     */
    private void setQuestions() {
        mQuestionList = mParser.getQuestions(mParser.loadJSONFromAsset(JSON_FILE));
    }

    /**
     * Setup the indicator
     * The amount of the indicator is based on the num of top-level questions
     */
    private void initPagerIndicator() {
//        int numOfTopLevelQuestions = questionList.size();
//        mIndicatorImage = new ImageView[numOfTopLevelQuestions];
//        for (int i = 0; i < mIndicatorImage.length; i++) {
//            mIndicatorImage[i] = new ImageView(this);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 40, 1.0f);
//            params.setMargins(8, 0, 8, 0);
//            mIndicatorImage[i].setLayoutParams(params);
//            mIndicatorImage[i].setImageResource(R.drawable.survey_indicator_default);
//            mIndicator.addView(mIndicatorImage[i]);
//            mIndicator.bringToFront();
//        }
        if(mIndicator.getChildCount() == 0) {
            mIndicatorImage = new ArrayList<>();
            for (int i = 0; i < mQuestionList.size(); i++) {
                mIndicatorImage.add(new ImageView(this));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 40, 1.0f);
                params.setMargins(8, 0, 8, 0);
                mIndicatorImage.get(i).setLayoutParams(params);
                mIndicatorImage.get(i).setImageResource(R.drawable.survey_indicator_default);
                mIndicator.addView(mIndicatorImage.get(i));
                mIndicator.bringToFront();
            }
        }
    }

    /**
     * Setup the view pager for questions.
     */
    private void initPager() {
        mPagerAdapter = new SurveyPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);

        // setup the first page's indicator
        mIndicatorImage.get(0).setImageResource(R.drawable.survey_indicator_selected);

        // disable onTouch swiping
        mViewPager.setPagingEnabled(false);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // disable the button in a new page
                mBtnContinue.setEnabled(false);


                // set the indicator
                if (position + 1 < isSubQuestionList.size() && isSubQuestionList.get(position + 1)) {
                    subCount++;
                    initPagerIndicator();
                    Log.d("subount", "count: " + subCount);
                } else {
//                    mIndicatorImage[position - subCount].setImageResource(R.drawable.survey_indicator_selected);
                    mIndicatorImage.get(position - subCount).setImageResource(R.drawable.survey_indicator_selected);
                    subCount = 0;
                }
//                mIndicatorImage.get(position).setImageResource(R.drawable.survey_indicator_selected);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * Setup the button to process next page
     */
    private void initButton() {
        mBtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the current question
                String questionText = questionList.get(mViewPager.getCurrentItem()).getQuestion();

                // get the answer of the selected radio button
                String ans = SurveyQuestionFragment.selectedAnswer;

                Log.d("SubQuestion", "Size: " + questionList.size()
                        + ", Question: " + questionText
                        + ", Ans: " + ans);

                // add the sub questions to the list at specific index
                mParser.addSubQuestionsIntoList(mViewPager.getCurrentItem(), ans);
                mPagerAdapter.notifyDataSetChanged();

                // store the answer as a list for the database
                surveySelectedAnswerList.add(new SurveySelectedAnswer(System.currentTimeMillis(), "0", ans));

                // if it's the last page, save the answers and go to main activity
                if (isLastPage) {
                    DatabaseTask newTask = new DatabaseTask(new DaoHelper(SurveyQuestionActivity.this), surveySelectedAnswerList);
                    newTask.execute();

                    Intent intent = new Intent(SurveyQuestionActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                // move to next page and load new fragment
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);

                // check if this page is the last page
                // todo: the last question can't be a nested question
                isLastPage = mViewPager.getCurrentItem() == mPagerAdapter.getCount() - 1;
//                Log.d("isLastPage", "This page: " + mViewPager.getCurrentItem()
//                        + "Total:" +  mPagerAdapter.getCount());

                // setup the button text
                mBtnContinue.setText(isLastPage
                        ? R.string.button_text_done
                        : R.string.button_text_next);
            }
        });
    }

    private void clearDataSet() {
        if (questionList != null) {
            questionList.clear();
        }
        if (isSubQuestionList != null) {
            isSubQuestionList.clear();
        }
    }
}
