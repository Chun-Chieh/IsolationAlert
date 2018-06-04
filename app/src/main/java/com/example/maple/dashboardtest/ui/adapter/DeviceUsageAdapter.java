package com.example.maple.dashboardtest.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maple.dashboardtest.R;
import com.example.maple.dashboardtest.model.device.DeviceUsage;

import java.util.List;

/**
 * @author Chun-Chieh Liang on 5/8/18.
 */
public class DeviceUsageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DeviceUsage> mDataset;

    public DeviceUsageAdapter(List<DeviceUsage> mDataset) {
        this.mDataset = mDataset;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_device_usage, parent, false);
        return new DeviceUsageViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final DeviceUsage deviceUsage = mDataset.get(position);
        DeviceUsageViewHolder viewHolder = (DeviceUsageViewHolder) holder;
        viewHolder.mTextViewUsageTitle.setText(deviceUsage.getUsageTitle());
        viewHolder.mTextViewUsageQuantity.setText(String.valueOf(deviceUsage.getUsageQuantity()));
        viewHolder.mTextViewUsageUnit.setText(deviceUsage.getUsageUnit());
        viewHolder.mImageViewUsageIcon.setImageResource(deviceUsage.getUsageImageSrc());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public static class DeviceUsageViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewUsageTitle, mTextViewUsageQuantity, mTextViewUsageUnit;
        ImageView mImageViewUsageIcon;

        public DeviceUsageViewHolder(View itemView) {
            super(itemView);
            mTextViewUsageTitle = itemView.findViewById(R.id.text_view_device_title);
            mTextViewUsageQuantity = itemView.findViewById(R.id.text_view_device_quantity);
            mTextViewUsageUnit = itemView.findViewById(R.id.text_view_device_unit);
            mImageViewUsageIcon = itemView.findViewById(R.id.image_device_icon);
        }
    }
}
