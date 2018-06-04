package com.example.maple.dashboardtest.ui.adapter.survey;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maple.dashboardtest.R;
import com.example.maple.dashboardtest.model.survey.SurveySelectedAnswer;

import java.util.List;

import static com.example.maple.dashboardtest.util.formatMillisecondToString;


/**
 * The adapter for showing the chosen answer in database
 *
 * @author Chun-Chieh Liang on 2/3/18.
 */

public class SurveyAnswerRecordAdapter extends RecyclerView.Adapter<SurveyAnswerRecordAdapter.SurveyAnswerRecordViewHolder> {
    private List<SurveySelectedAnswer> selectedAnswers;

    public SurveyAnswerRecordAdapter(List<SurveySelectedAnswer> selectedAnswers) {
        this.selectedAnswers = selectedAnswers;
    }

    @Override
    public SurveyAnswerRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_survey_answer_record, parent, false);
        return new SurveyAnswerRecordViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SurveyAnswerRecordViewHolder holder, int position) {
        final SurveySelectedAnswer surveySelectedAnswer = selectedAnswers.get(position);
        holder.mTimeStamp.setText(formatMillisecondToString(surveySelectedAnswer.getTimeStamp()));
        holder.mQuestionId.setText(surveySelectedAnswer.getQuestionId());
        holder.mAnswer.setText(surveySelectedAnswer.getAnswer());
    }

    @Override
    public int getItemCount() {
        return selectedAnswers.size();
    }

    public static class SurveyAnswerRecordViewHolder extends RecyclerView.ViewHolder {
        TextView mTimeStamp;
        TextView mQuestionId;
        TextView mAnswer;

        SurveyAnswerRecordViewHolder(View itemView) {
            super(itemView);
            mTimeStamp = itemView.findViewById(R.id.text_view_survey_answer_timestamp);
            mQuestionId = itemView.findViewById(R.id.text_view_survey_answer_question_id);
            mAnswer = itemView.findViewById(R.id.text_view_survey_answer_selected);
        }
    }
}
