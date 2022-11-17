package com.varsity.dgmdashboard.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.databinding.RowNotificationBinding;
import com.varsity.dgmdashboard.model.GetNotificationListResponse;

import java.util.ArrayList;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.DataViewHolder> {

    private ArrayList<GetNotificationListResponse> list;

    public NotificationListAdapter(ArrayList<GetNotificationListResponse> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowNotificationBinding binding;
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_notification, parent, false);
        return new NotificationListAdapter.DataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationListAdapter.DataViewHolder holder, int position) {
        holder.mBinding.tvMessage.setText("" + list.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;

    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        private RowNotificationBinding mBinding;

        public DataViewHolder(RowNotificationBinding mBinding) {
            super(mBinding.clMainView);
            this.mBinding = mBinding;

        }
    }
}
