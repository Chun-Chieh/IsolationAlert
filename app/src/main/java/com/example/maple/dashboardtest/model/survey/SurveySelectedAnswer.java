package com.example.maple.dashboardtest.model.survey;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * The answers selected by the user (both micro and normal)
 *
 * @author Chun-Chieh Liang on 3/22/18.
 */

@Entity
public class SurveySelectedAnswer {
    private Long timeStamp;
    private String questionId;
    private String answer;

    @Generated(hash = 206857357)
    public SurveySelectedAnswer(Long timeStamp, String questionId, String answer) {
        this.timeStamp = timeStamp;
        this.questionId = questionId;
        this.answer = answer;
    }

    @Generated(hash = 1116647094)
    public SurveySelectedAnswer() {
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
