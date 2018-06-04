package com.example.maple.dashboardtest.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.maple.dashboardtest.R;

import java.util.ArrayList;

import static com.example.maple.dashboardtest.util.ARG_OPTIONS;
import static com.example.maple.dashboardtest.util.ARG_QUESTION;


/**
 * @author Chun-Chieh Liang on 3/17/18.
 */

public class SurveyQuestionFragment extends Fragment {
    public static String questionId;
    public static String selectedAnswer; // the answer has to be public static due to unknown reason

    private Button activityContinueButton;
    private View rootView;
    private RadioGroup mRadioGroup;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflate properly.
        rootView = inflater.inflate(R.layout.fragment_survey_question, container, false);

        activityContinueButton = getActivity().findViewById(R.id.button_survey);

        Bundle args = getArguments();
        // Setup the page number (question number)
        // updated: question number is no longer needed
//        ((TextView) rootView.findViewById(R.id.text_view_question_num)).setText(String.format(Locale.getDefault(), "%d", args.getInt(ARG_PAGE)));

        // Setup the question
        ((TextView) rootView.findViewById(R.id.text_view_question_content)).setText(args.getString(ARG_QUESTION));


        mRadioGroup = rootView.findViewById(R.id.radio_group_options);

        // Setup the options
        addRadioButtons(args.getStringArrayList(ARG_OPTIONS));

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = rootView.findViewById(checkedId);
                selectedAnswer = radioButton.getText().toString();

                // enable the button when a button is selected
                activityContinueButton.setEnabled(true);
//                Toast.makeText(getContext(), selectedAnswer +", " + checkedId, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void addRadioButtons(ArrayList<String> options) {
        RadioGroup.LayoutParams params;
        mRadioGroup.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < options.size(); i++) {
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setId(i);
            radioButton.setText(String.valueOf(options.get(i)));
            params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 16, 0, 16);
            mRadioGroup.addView(radioButton, params);
        }
    }
}

