package com.example.maple.dashboardtest.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maple.dashboardtest.R;
import com.example.maple.dashboardtest.model.activity.ActivityData;

import java.util.List;

import static com.example.maple.dashboardtest.util.formatActivityTypeToString;
import static com.example.maple.dashboardtest.util.getDurationPair;

/**
 * @author Chun-Chieh Liang on 5/10/18.
 */
public class ActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ActivityData> mActivityDataList;

    public ActivityAdapter(List<ActivityData> activityDataList) {
        this.mActivityDataList = activityDataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_activity, parent, false);
        return new ActivityViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ActivityData activityData = mActivityDataList.get(position);
        ActivityViewHolder viewHolder = (ActivityViewHolder) holder;
        viewHolder.mTextViewActivityTitle.setText(formatActivityTypeToString(activityData.getActivityType()));
        Pair<Long, String> durationPair = getDurationPair(activityData.getActivityDuration());
        viewHolder.mTextViewActivityQuantity.setText(String.valueOf(durationPair.first));
        viewHolder.mTextViewActivityUnit.setText(durationPair.second);
        viewHolder.mImageViewActivity.setImageResource(activityData.getActivityTypeImageSrc());

    }

    @Override
    public int getItemCount() {
        return mActivityDataList.size();
    }


    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewActivityTitle, mTextViewActivityQuantity, mTextViewActivityUnit;
        ImageView mImageViewActivity;

        public ActivityViewHolder(View itemView) {
            super(itemView);
            mTextViewActivityTitle = itemView.findViewById(R.id.text_view_title);
            mTextViewActivityQuantity = itemView.findViewById(R.id.text_view_quantity);
            mTextViewActivityUnit = itemView.findViewById(R.id.text_view_unit);
            mImageViewActivity = itemView.findViewById(R.id.image_icon);
        }
    }
}
