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
import com.example.maple.dashboardtest.model.social.SocialAppUsage;
import com.example.maple.dashboardtest.ui.widget.VerticalBarChartView;

import java.util.List;

import static com.example.maple.dashboardtest.util.formatMilliSecToHMS;

/**
 * @author Chun-Chieh Liang on 2/3/18.
 */

public class SocialAppAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SocialAppUsage> mSocialAppUsageList;
    private long mTotalUsageTime;

    public SocialAppAdapter(List<SocialAppUsage> socialAppUsageUsageList, long totalUsageTime) {
        this.mSocialAppUsageList = socialAppUsageUsageList;
        this.mTotalUsageTime = totalUsageTime;
    }

    @Override
    public SocialAppsUsageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_social_app, parent, false);
        return new SocialAppsUsageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final SocialAppUsage socialAppUsage = mSocialAppUsageList.get(position);
        SocialAppsUsageViewHolder viewHolder = (SocialAppsUsageViewHolder) holder;
        viewHolder.mTextViewAppName.setText(socialAppUsage.getAppName());
        Pair<Long, String> durationPair = formatMilliSecToHMS(socialAppUsage.getAppUsageTime());
        viewHolder.mTextViewAppUsageTime.setText(String.valueOf(durationPair.first));
        viewHolder.mTextViewAppTimeUnit.setText(durationPair.second);
        viewHolder.mAppIcon.setImageDrawable(socialAppUsage.getAppIcon());
        viewHolder.mBarChart.setData((int) socialAppUsage.getAppUsageTime(), (int) mTotalUsageTime);
    }

    @Override
    public int getItemCount() {
        return mSocialAppUsageList.size();
    }

    public static class SocialAppsUsageViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewAppName, mTextViewAppUsageTime, mTextViewAppTimeUnit;
        ImageView mAppIcon;
        VerticalBarChartView mBarChart;

        SocialAppsUsageViewHolder(View itemView) {
            super(itemView);
            mTextViewAppName = itemView.findViewById(R.id.text_view_social_app_name);
            mTextViewAppUsageTime = itemView.findViewById(R.id.text_view_social_app_time_usage);
            mTextViewAppTimeUnit = itemView.findViewById(R.id.text_view_social_app_time_unit);
            mAppIcon = itemView.findViewById(R.id.image_social_app_icon);
            mBarChart = itemView.findViewById(R.id.bar_chart_social_app);
        }
    }
}
