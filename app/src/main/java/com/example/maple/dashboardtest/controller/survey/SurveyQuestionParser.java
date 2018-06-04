package com.example.maple.dashboardtest.controller.survey;

import android.content.Context;
import android.util.Log;

import com.example.maple.dashboardtest.model.survey.Question;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Read the json file from the assets as string
 * Parse the json string to a Question list
 *
 * @author Chun-Chieh Liang on 3/18/18.
 */

public class SurveyQuestionParser {
    public static List<Question> questionList;
    public static List<Boolean> isSubQuestionList = new ArrayList<>();

    private Context mContext;

    public SurveyQuestionParser() {
    }


    public SurveyQuestionParser(Context mContext) {
        this.mContext = mContext;
    }


    /**
     * Read the provided json file as a string
     *
     * @return string of json
     */
    public String loadJSONFromAsset(String fileName) {
        String jsonString = null;
        try {
            InputStream is = mContext.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonString;
    }

    /**
     * Parse the json string to a list of "top-level" questions
     *
     * @param jsonString the string retrieved from the json
     * @return a list of top-level questions
     */
    public List<Question> getQuestions(String jsonString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        Gson gson = gsonBuilder.create();
//        List<Question> questionList;
        questionList = new ArrayList<>(Arrays.asList(gson.fromJson(jsonString, Question[].class)));

        // all the top level questions are not sub questions
        for (int i = 0; i < questionList.size(); i++) {
            isSubQuestionList.add(false);
        }
        return questionList;
    }


    /**
     * Get Next Sub Question
     *
     * @param index
     * @param answer
     */
    public void addSubQuestionsIntoList(int index, String answer) {
        /* Read Response */
        List<Question> subQuestions;
        subQuestions = questionList.get(index).getAnswer().get(answer);
        // if there're sub questions for this answer, add the sub questions to the list

        if (subQuestions != null) {
            for (Question nextQuestion : subQuestions) {
                index++;
                questionList.add(index, nextQuestion);
                isSubQuestionList.add(true);
            }
        }
    }


    /**
     * Print the questions for testing
     */
    public void printQuestions() {
        if (questionList == null || questionList.size() == 0) {
            Log.d("QuestionLisy", "empty");
        } else {
            for (Question q : questionList) {
                Log.d("Question", "current size: " + questionList.size() +
                        "id:" + q.getId() +
                        "question: " + q.getQuestion() +
                        "options: " + q.getOptions());
            }
        }
    }
}
