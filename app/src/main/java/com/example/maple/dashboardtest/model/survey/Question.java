package com.example.maple.dashboardtest.model.survey;

import java.util.List;
import java.util.Map;

/**
 * @author Chun-Chieh Liang on 3/18/18.
 */

public class Question {
    private String id;
    private String text;
    private List<String> options;
    private Map<String, List<Question>> answer;

    public Question(String id, String text, List<String> options, Map<String, List<Question>> answer) {
        this.id = id;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setQuestion(String question) {
        this.text = question;
    }

    public void setAnswer(Map<String, List<Question>> answer) {
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public Map<String, List<Question>> getAnswer() {
        return answer;
    }
}
