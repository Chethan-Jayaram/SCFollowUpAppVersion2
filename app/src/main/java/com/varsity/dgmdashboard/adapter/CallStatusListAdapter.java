package com.varsity.dgmdashboard.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.databinding.RowCallStatusBinding;
import com.varsity.dgmdashboard.model.DGMDashboardResponse;

import java.util.ArrayList;

public class CallStatusListAdapter extends RecyclerView.Adapter<CallStatusListAdapter.DataViewHolder> {

    private ArrayList<DGMDashboardResponse.CallStatus> list;

    public CallStatusListAdapter(ArrayList<DGMDashboardResponse.CallStatus> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowCallStatusBinding binding;
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.row_call_status, parent, false);
        return new CallStatusListAdapter.DataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CallStatusListAdapter.DataViewHolder holder, int position) {
        holder.mBinding.tvStatusName.setText(list.get(position).getCallStatusName());
        holder.mBinding.tvCount.setText("" + list.get(position).getCount());
        Log.e("COLOR", "" + list.get(position).getColourCode().trim());
        holder.mBinding.ivStatusColor.setBackgroundColor(Color.parseColor(list.get(position).getColourCode().trim()));

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;

    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        private RowCallStatusBinding mBinding;

        public DataViewHolder(RowCallStatusBinding mBinding) {
            super(mBinding.clMainView);
            this.mBinding = mBinding;

        }
    }
}
