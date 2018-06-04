package com.example.maple.dashboardtest.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maple.dashboardtest.R;


/**
 * @author Chun-Chieh Liang on 4/15/18.
 */

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.ViewHolder> {
    private String[] mTrainingText;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewContent;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewContent = itemView.findViewById(R.id.text_view_training);
        }
    }

    public TrainingAdapter(String[] mDataset) {
        this.mTrainingText = mDataset;
    }

    @Override
    public TrainingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_training_text, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String str = mTrainingText[position];
        holder.mTextViewContent.setText(str);
    }


    @Override
    public int getItemCount() {
        return mTrainingText.length;
    }


}
